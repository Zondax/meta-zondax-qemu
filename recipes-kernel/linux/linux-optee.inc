DESCRIPTION = "Linux kernel with optee patches"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit kernel

def get_optee_configs(d):
    machine = d.getVar("MACHINE", True)
    if machine == "qemu-optee32":
        return ("arch/arm/configs/vexpress_defconfig " +
            "{0}/qemu.conf").format(d.getVar("WORKDIR", True))
    elif machine == "qemu-optee64":
        return ("arch/arm64/configs/defconfig " +
            "{0}/qemu.conf").format(d.getVar("WORKDIR", True))
    else:
        bb.fatal("Unsupported machine")

# Lets see what happens.
COMPATIBLE_MACHINE = "qemu-optee(32|64)"
KERNEL_IMAGETYPE ?= "Image"

# Workaround to include modules.builtin.modinfo

FILES_${KERNEL_PACKAGE_NAME}-base += " ${nonarch_base_libdir}/modules/${KERNEL_VERSION}/modules.builtin.modinfo "

KERNEL_CONFIG_COMMAND () {
    cd ${S}
    ./scripts/kconfig/merge_config.sh \
        -O ${B} \
        ${@get_optee_configs(d)} || die "Unable to configure"

    # oe_runmake -C ${S} O=${B} vexpress_defconfig ${WORKDIR}/qemu.conf
}
