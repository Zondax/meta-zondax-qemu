SUMMARY = "Trusted Firmware A"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRCREV = "b76aead1b299890ea1edacf7f008bf0f9204a1b4"
SRC_URI = "git://git.trustedfirmware.org/TF-A/trusted-firmware-a.git;branch=master \
           file://0001-qemu-increase-secure-SRAM-size.patch \
           file://0002-qemu-allocate-more-SRAM-for-BL31.patch \
           "

DEPENDS:qemu-optee32 = "u-boot-optee optee-os"
DEPENDS:qemu-optee64 = "u-boot-optee optee-os"

COMPATIBLE_MACHINE = "qemu-optee(32|64)"

ARMTF_ARGS:qemu-optee32 = " \
        BL32=${STAGING_DIR_HOST}/lib/firmware/tee-header_v2.bin \
        BL32_EXTRA1=${STAGING_DIR_HOST}/lib/firmware/tee-pager_v2.bin \
        BL32_EXTRA2=${STAGING_DIR_HOST}/lib/firmware/tee-pageable_v2.bin \
        BL33=${STAGING_DIR_HOST}/lib/firmware/u-boot.bin \
        ARM_ARCH_MAJOR=7 \
        ARCH=aarch32 \
        PLAT=qemu \
        ARM_TSP_RAM_LOCATION=tdram \
        BL32_RAM_LOCATION=tdram \
        AARCH32_SP=optee \
        DEBUG=1 \
        LOG_LEVEL=50 \
"

ARMTF_ARGS:qemu-optee64 = " \
        BL32=${STAGING_DIR_HOST}/lib/firmware/tee-header_v2.bin \
        BL32_EXTRA1=${STAGING_DIR_HOST}/lib/firmware/tee-pager_v2.bin \
        BL32_EXTRA2=${STAGING_DIR_HOST}/lib/firmware/tee-pageable_v2.bin \
        BL33=${STAGING_DIR_HOST}/lib/firmware/u-boot.bin \
        PLAT=qemu \
        ARM_TSP_RAM_LOCATION=tdram \
        BL32_RAM_LOCATION=tdram \
        SPD=opteed \
        DEBUG=1 \
        LOG_LEVEL=50 \
"

S = "${WORKDIR}/git"

inherit deploy

do_configure () {
    :
}

do_compile () {
    unset CFLAGS
    unset LDFLAGS
    unset AS
    oe_runmake \
        CROSS_COMPILE=${HOST_PREFIX} \
        V=1 \
        ${ARMTF_ARGS} \
        all fip
}

do_install () {
    :
}

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 644 ${S}/build/qemu/debug/*.bin ${DEPLOYDIR}
}

addtask deploy before do_package after do_install

