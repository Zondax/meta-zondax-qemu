require linux-optee.inc

SRCREV = "f8e3503c8f3646c9d0aaf1aae4efa7fd3b6f2908"
PV = "5.14.0+git${SRCPV}"

SRC_URI = "git://github.com/linaro-swg/linux.git;branch=optee \
           file://zone_dma_revert.patch \
           "

SRC_URI_append_qemu-optee32   = " file://qemu.conf"
SRC_URI_append_qemu-optee64   = " file://qemu.conf"

DESCRIPTION = "Kernel for OP-TEE QEMU setup"

S = "${WORKDIR}/git"
