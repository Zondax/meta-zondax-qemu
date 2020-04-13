HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PE = "1"

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRC_URI = "git://github.com/u-boot/u-boot.git;protocol=http;branch=master \
           file://0001-qemu_arm64-enable-semihosting.patch \
           file://0002-qemu_arm-enable-semihosting.patch \
           "

SRCREV = "36fec02b1f90b92cf51ec531564f9284eae27ab4"

S = "${WORKDIR}/git"
