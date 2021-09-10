require linux-optee.inc

SRCREV = "e2c060fedf7e2b27370b9d481840b12916e9f38a"
PV = "5.13.0+git${SRCPV}"

SRC_URI = "git://github.com/linaro-swg/linux.git;branch=optee \
           file://zone_dma_revert.patch \
           "

SRC_URI_append_qemu-optee32   = " file://qemu.conf"
SRC_URI_append_qemu-optee64   = " file://qemu.conf"

DESCRIPTION = "Kernel for OP-TEE QEMU setup"

S = "${WORKDIR}/git"
