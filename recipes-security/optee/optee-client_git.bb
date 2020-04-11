SUMMARY = "OPTEE Client"
HOMEPAGE = "https://github.com/OP-TEE/optee_client"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=69663ab153298557a59c67a60a743e5b"

PV = "3.8.0+git${SRCPV}"

inherit pythonnative systemd

SRC_URI = "git://github.com/OP-TEE/optee_client.git \
           file://optee"

S = "${WORKDIR}/git"

SRCREV = "29ddb4838d821f2e75f28c3e730a50f94ca7f48c"

do_install() {
    oe_runmake install

    install -D -p -m0755 ${S}/out/export/usr/sbin/tee-supplicant ${D}${sbindir}/tee-supplicant

    install -D -p -m0644 ${S}/out/export/usr/lib/libteec.so.1.0 ${D}${libdir}/libteec.so.1.0
    ln -sf libteec.so.1.0 ${D}${libdir}/libteec.so
    ln -sf libteec.so.1.0 ${D}${libdir}/libteec.so.1

    cp -a ${S}/out/export/usr/include ${D}${includedir}
    if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
	install -D -p -m0755 ${WORKDIR}/optee ${D}/etc/init.d/optee
	install -d ${D}/etc/rcS.d
	ln -sf ../init.d/optee ${D}/etc/rcS.d/S30optee
    fi
}
