require linux-optee.inc

SRCREV = "dbeb6ea978fc33bddcca50e416790dd590038961"
PV = "5.16.0+git${SRCPV}"

SRC_URI = "git://github.com/linaro-swg/linux.git;branch=optee;protocol=https \
           file://zone_dma_revert.patch \
           "

SRC_URI:append:qemu-optee32   = " file://qemu.conf"
SRC_URI:append:qemu-optee64   = " file://qemu.conf"

DESCRIPTION = "Kernel for OP-TEE QEMU setup"

S = "${WORKDIR}/git"
