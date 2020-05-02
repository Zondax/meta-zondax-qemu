do_install_append () {
    cat >> ${D}${sysconfdir}/fstab <<EOF

host /mnt 9p trans=virtio 0 0

EOF
}
