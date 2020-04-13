require linux-optee.inc

SRCREV = "1149e7d06a009e5dacf6aaa29bd087da64bfbed2"
PV = "5.5.0+git${SRCPV}"

SRC_URI = "git://github.com/linaro-swg/linux.git;branch=optee"

SRC_URI_append_qemu-optee32   = " file://qemu.conf"
SRC_URI_append_qemu-optee64   = " file://qemu.conf"

DESCRIPTION = "Kernel for OP-TEE QEMU setup"

S = "${WORKDIR}/git"
