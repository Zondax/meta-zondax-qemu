HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PE = "1"

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRC_URI = "git://github.com/linaro-swg/u-boot.git;protocol=http;branch=optee"
SRC_URI_append = " \
                file://0001-qemu_arm-add-semihosting-support.patch \
		file://0001-qemu64_arm-add-semihosting-support.patch "

SRCREV = "6c35bd586b65d4ff6a86afc6536496cdb8095c3a"

S = "${WORKDIR}/git"
