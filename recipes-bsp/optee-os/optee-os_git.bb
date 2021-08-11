SUMMARY = "OP-TEE Trusted OS"
DESCRIPTION = "OPTEE OS"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c1f21c4f72f372ef38a5a4aee55ec173"

PV="3.14.0+git${SRCPV}"

inherit deploy python3native

DEPENDS = "python3-pycrypto-native python3-pyelftools-native python3-pycryptodomex-native"

python () {
     machine = d.getVar("MACHINE", True)
     if machine == "qemu-optee32":
         d.setVar("EXTRA_OEMAKE",
             ("PLATFORM=vexpress-qemu_virt" +
              " CFG_ARM64_core=n" +
              " CROSS_COMPILE_core={0}" +
              " CROSS_COMPILE_ta_arm32={0}" +
              " ta-targets=ta_arm32").format(d.getVar("HOST_PREFIX", True)))
         d.setVar("OPTEE_SHORT_MACHINE", "vexpress")
         d.setVar("OPTEE_ARCH", "arm32")
     elif machine == "qemu-optee64":
         d.setVar("EXTRA_OEMAKE",
             ("PLATFORM=vexpress-qemu_armv8a" +
              " CFG_ARM64_core=y" +
              " CFG_ARM32_core=n" +
              " CROSS_COMPILE_core={0}" +
              " CROSS_COMPILE_ta_arm64={0}" +
              " ta-targets=ta_arm64").format(d.getVar("HOST_PREFIX", True)))
         d.setVar("OPTEE_SHORT_MACHINE", "vexpress")
         d.setVar("OPTEE_ARCH", "arm64")
     else:
         bb.fatal("optee-os doesn't recognize this MACHINE")
}

SRC_URI = "git://github.com/OP-TEE/optee_os.git \
           file://0006-allow-setting-sysroot-for-libgcc-lookup.patch \
"

SRCREV = "d21befa5e53eae9db469eba1685f5aa5c6f92c2f"

S = "${WORKDIR}/git"

do_compile() {
    unset LDFLAGS
    export CFLAGS="${CFLAGS} --sysroot=${STAGING_DIR_HOST}"
    oe_runmake all CFG_TEE_TA_LOG_LEVEL=4 CFG_TEE_CORE_LOG_LEVEL=4 DEBUG=1
}

do_install() {
    #install core on boot directory
    install -d ${D}${nonarch_base_libdir}/firmware/

    install -m 644 ${B}/out/arm-plat-${OPTEE_SHORT_MACHINE}/core/*.bin ${D}${nonarch_base_libdir}/firmware/
    #install TA devkit
    install -d ${D}/usr/include/optee/export-user_ta/

    for f in  ${B}/out/arm-plat-${OPTEE_SHORT_MACHINE}/export-ta_${OPTEE_ARCH}/* ; do
        cp -aR  $f  ${D}/usr/include/optee/export-user_ta/
    done
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_deploy() {
    install -d ${DEPLOYDIR}/optee
    for f in ${D}${nonarch_base_libdir}/firmware/*; do
        install -m 644 $f ${DEPLOYDIR}/optee/
    done
}

addtask deploy before do_build after do_install

FILES_${PN} = "${nonarch_base_libdir}/firmware/"
FILES_${PN}-dev = "/usr/include/optee"

INSANE_SKIP_${PN}-dev = "staticdev"

INHIBIT_PACKAGE_STRIP = "1"
