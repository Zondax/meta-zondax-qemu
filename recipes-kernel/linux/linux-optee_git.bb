require linux-optee.inc

SRCREV = "1ad01d3482219ee7fcc5a4529f8f06570878bf72"
PV = "5.5.0+git${SRCPV}"

SRC_URI = "git://github.com/linaro-swg/linux.git;branch=optee"

SRC_URI_append_qemu-optee32   = " file://qemu.conf"
SRC_URI_append_qemu-optee64   = " file://qemu.conf"

DESCRIPTION = "Kernel for OP-TEE QEMU setup"

S = "${WORKDIR}/git"
