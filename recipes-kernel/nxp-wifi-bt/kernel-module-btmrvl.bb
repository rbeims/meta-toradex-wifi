SUMMARY = "Kernel loadable module for NXP Bluetooth chip"
LICENSE = "CLOSED"

inherit module

DEPENDS="kernel-module-wifimrvl"
RPROVIDES_${PN}_append_interface-diversity-sd-sd = " kernel-module-bt8xxx "
RPROVIDES_${PN}_append_interface-diversity-usb-usb = " kernel-module-bt8xxx "
RPROVIDES_${PN}_append_interface-diversity-pcie-usb = " kernel-module-bt8xxx "

KERNEL_MODULE_PROBECONF_append_interface-diversity-sd-sd = " bt8xxx "
module_conf_bt8xxx_interface-diversity-sd-sd_mfg-mode = "options bt8xxx fw_name=nxp/sdio8997_sdio_combo.bin"

KERNEL_MODULE_PROBECONF_append_interface-diversity-usb-usb = " bt8xxx "
module_conf_bt8xxx_interface-diversity-usb-usb_mfg-mode = "options bt8xxx fw_name=nxp/usb8997_usb_combo.bin"

KERNEL_MODULE_PROBECONF_append_interface-diversity-pcie-usb = " bt8xxx "
module_conf_bt8xxx_interface-diversity-pcie-usb_mfg-mode = "options bt8xxx fw_name=nxp/pcie8997_usb_combo.bin"

SRC_URI = " \
    file://0001-makefile.patch \
"

S = "${WORKDIR}/mbt_src"

RDEPENDS_${PN} += "toradex-wifi-config"

COMPATIBLE_MACHINE = "(colibri-imx6ull|colibri-imx8x|verdin-imx8mm|verdin-imx8mp|apalis-imx8|apalis-imx8x)"

addtask nxp_driver_unpack before do_patch after do_unpack
do_nxp_driver_unpack() {
    :
}

#SRC_URI_append_interface-diversity-sd-sd = " ${NXP_PROPRIETARY_DRIVER_LOCATION}/SD-WLAN-SD-BT-8997-U16-MMC-W16.68.10.p162-16.26.10.p162-C4X16693_V4-MGPL.zip;name=sd-sd-driver;subdir=archive.sd-sd "
SRC_URI_append_interface-diversity-sd-sd = " ${NXP_PROPRIETARY_DRIVER_LOCATION}/${NXP_PROPRIETARY_DRIVER_FILENAME};name=sd-sd-driver;subdir=archive.sd-sd "
SRC_URI[sd-sd-driver.sha256sum] = "${NXP_PROPRIETARY_DRIVER_SHA1}"

do_nxp_driver_unpack_interface-diversity-sd-sd() {
    DRVNAME=$(basename ${NXP_PROPRIETARY_DRIVER_FILENAME} | sed 's/zip/tar/')
    tar -C ${WORKDIR}/archive.sd-sd/ -xf ${WORKDIR}/archive.sd-sd/$DRVNAME
    for i in `ls ${WORKDIR}/archive.sd-sd/*-src.tgz`; do
        tar --strip-components=1 -C ${WORKDIR} \
            -xf $i
    done
}

#do_nxp_driver_unpack_interface-diversity-sd-sd() {
#    tar -C ${WORKDIR}/archive.sd-sd/ -xf ${WORKDIR}/archive.sd-sd/SD-WLAN-SD-BT-8997-U16-MMC-W16.68.10.p162-16.26.10.p162-C4X16693_V4-MGPL.tar \
#                                         SD-BT-8997-U16-MMC-16.26.10.p162-C4X14114_V4-GPL-src.tgz
#    tar --strip-components=1 -C ${WORKDIR} \
#        -xf ${WORKDIR}/archive.sd-sd/SD-BT-8997-U16-MMC-16.26.10.p162-C4X14114_V4-GPL-src.tgz \
#        SD-UAPSTA-BT-8997-U16-MMC-W16.68.10.p162-16.26.10.p162-C4X16693_V4-MGPL/mbt_src
#}

SRC_URI_append_interface-diversity-usb-usb = " ${NXP_PROPRIETARY_DRIVER_LOCATION}/${NXP_PROPRIETARY_DRIVER_FILENAME};name=usb-usb-driver;subdir=archive.usb-usb "
#SRC_URI_append_interface-diversity-usb-usb = " ${NXP_PROPRIETARY_DRIVER_LOCATION}/USB-WLAN-BT-8997-U16-X86-W16.68.10.p136-16.26.10.p136-C4X16687_V4-MGPL.zip;name=usb-usb-driver;subdir=archive.usb-usb "
SRC_URI[usb-usb-driver.sha256sum] = "${NXP_PROPRIETARY_DRIVER_SHA1}"
do_nxp_driver_unpack_interface-diversity-usb-usb() {
    DRVNAME=$(basename ${NXP_PROPRIETARY_DRIVER_FILENAME} | sed 's/zip/tar/')
    tar -C ${WORKDIR}/archive.usb-usb/ -xf ${WORKDIR}/archive.usb-usb/$DRVNAME
    for i in `ls ${WORKDIR}/archive.usb-usb/*-src.tgz`; do
        tar --strip-components=1 -C ${WORKDIR} \
            -xf $i
    done
}

#do_nxp_driver_unpack_interface-diversity-usb-usb() {
#    tar -C ${WORKDIR}/archive.usb-usb/ \
#       -xf ${WORKDIR}/archive.usb-usb/USB-WLAN-BT-8997-U16-X86-W16.68.10.p136-16.26.10.p136-C4X16687_V4-MGPL.tar \
#           USB-BT-8997-U16-X86-16.26.10.p136-C4X14114_V4-GPL-src.tgz
#    tar --strip-components=1 \
#         -C ${WORKDIR} \
#        -xf ${WORKDIR}/archive.usb-usb/USB-BT-8997-U16-X86-16.26.10.p136-C4X14114_V4-GPL-src.tgz
#}

SRC_URI_append_interface-diversity-pcie-usb = " ${NXP_PROPRIETARY_DRIVER_LOCATION}/${NXP_PROPRIETARY_DRIVER_FILENAME};name=pcie-usb-driver;subdir=archive.pcie-usb "
#SRC_URI_append_interface-diversity-pcie-usb = " ${NXP_PROPRIETARY_DRIVER_LOCATION}/PCIE-WLAN-USB-BT-8997-U16-X86-W16.88.10.p70-16.26.10.p70-C4X16672_V4-GPL.zip;name=pcie-usb-driver;subdir=archive.pcie-usb "
SRC_URI[pcie-usb-driver.sha256sum] = "${NXP_PROPRIETARY_DRIVER_SHA1}"
do_nxp_driver_unpack_interface-diversity-pcie-usb() {
    DRVNAME=$(basename ${NXP_PROPRIETARY_DRIVER_FILENAME} | sed 's/zip/tar/')
    tar -C ${WORKDIR}/archive.pcie-usb/ -xf ${WORKDIR}/archive.pcie-usb/$DRVNAME
    for i in `ls ${WORKDIR}/archive.pcie-usb/*-src.tgz`; do
        tar --strip-components=1 -C ${WORKDIR} \
            -xf $i
    done
}

#do_nxp_driver_unpack_interface-diversity-pcie-usb() {
#    tar -C ${WORKDIR}/archive.pcie-usb/ \
#       -xf ${WORKDIR}/archive.pcie-usb/PCIE-WLAN-USB-BT-8997-U16-X86-W16.88.10.p70-16.26.10.p70-C4X16672_V4-GPL.tar \
#           USB-BT-8997-U16-X86-16.26.10.p70-C4X14114_V4-GPL-src.tgz
#    tar --strip-components=1 \
#         -C ${WORKDIR} \
#        -xf ${WORKDIR}/archive.pcie-usb/USB-BT-8997-U16-X86-16.26.10.p70-C4X14114_V4-GPL-src.tgz
#}
