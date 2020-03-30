#! /bin/bash

BUILD_DIR=${PWD}/../build
ROOT_NATIVE=$BUILD_DIR/tmp/work/qemu_optee32-poky-linux-gnueabi/core-image-minimal/1.0-r0/recipe-sysroot-native/
DEPLOY_DIR=$BUILD_DIR/tmp/deploy/images/qemu-optee32/
SOC_TERM=$ROOT_NATIVE/usr/bin/soc_term

nc -z  127.0.0.1 54320 || \
gnome-terminal -x ${SOC_TERM} 54320

nc -z  127.0.0.1 54321 || \
gnome-terminal -x ${SOC_TERM} 54321

sleep 2

cd $DEPLOY_DIR && $ROOT_NATIVE/usr/bin/qemu-system-arm \
	-s -S \
	-nographic \
	-machine virt,secure=on \
	-cpu cortex-a15 \
	-bios bl1.bin \
	-serial tcp:localhost:54320 -serial tcp:localhost:54321 \
	-m 1058 \
	-d unimp -semihosting-config enable,target=native \
	-object rng-random,filename=/dev/urandom,id=rng0 \
	-device virtio-rng-pci,rng=rng0,max-bytes=1024,period=1000
