SUMMARY = "Kernel loadable module for NXP WiFi chip"
LICENSE = "CLOSED"

inherit module

RPROVIDES_${PN}_append_interface-diversity-sd-sd = " kernel-module-sd8xxx kernel-module-mlan "
RPROVIDES_${PN}_append_interface-diversity-usb-usb = " kernel-module-usb8xxx kernel-module-mlan "
RPROVIDES_${PN}_append_interface-diversity-pcie-usb = " kernel-module-pcie8xxx kernel-module-mlan "

KERNEL_MODULE_WIFI_INTERFACE_interface-diversity-sd-sd = "sd8xxx"
KERNEL_MODULE_WIFI_INTERFACE_interface-diversity-usb-usb = "usb8xxx"
KERNEL_MODULE_WIFI_INTERFACE_interface-diversity-pcie-usb = "pcie8xxx"

KERNEL_MODULE_PROBECONF_append_interface-diversity-sd-sd = " ${KERNEL_MODULE_WIFI_INTERFACE} "
module_conf_sd8xxx_interface-diversity-sd-sd = "options sd8xxx cal_data_cfg=nxp/cal_data.conf cfg80211_wext=12"
module_conf_sd8xxx_interface-diversity-sd-sd_mfg-mode = "options sd8xxx cal_data_cfg=none cfg80211_wext=0xf mfg_mode=1 fw_name=nxp/sdio8997_sdio_combo.bin"

KERNEL_MODULE_PROBECONF_append_interface-diversity-usb-usb = " ${KERNEL_MODULE_WIFI_INTERFACE} "
module_conf_usb8xxx_interface-diversity-usb-usb = "options usb8xxx cal_data_cfg=nxp/cal_data.conf cfg80211_wext=12"
module_conf_usb8xxx_interface-diversity-usb-usb_mfg-mode = "options usb8xxx cal_data_cfg=none cfg80211_wext=0xf mfg_mode=1 fw_name=nxp/usb8997_usb_combo.bin"

KERNEL_MODULE_PROBECONF_append_interface-diversity-pcie-usb = " ${KERNEL_MODULE_WIFI_INTERFACE} "
module_conf_pcie8xxx_interface-diversity-pcie-usb = "options pcie8xxx cal_data_cfg=nxp/cal_data.conf cfg80211_wext=12"
module_conf_pcie8xxx_interface-diversity-pcie-usb_mfg-mode = "options pcie8xxx cal_data_cfg=none cfg80211_wext=0xf mfg_mode=1 fw_name=nxp/pcie8997_usb_combo.bin"

SRC_URI = "\
    file://cal_data.conf \
    file://0001-makefile.patch \
"

SRC_URI_append_interface-diversity-usb-usb = " \
    file://0001-Fix-kernel-version-logic-related-to-pm_usage_cnt.patch \
"

S = "${WORKDIR}/wlan_src"

DEPENDS += "bc-native"
RDEPENDS_${PN} += "toradex-wifi-config"

do_install_append() {
    install -d ${D}${base_libdir}/firmware/nxp
    install -m 0644 ${WORKDIR}/cal_data.conf ${D}${base_libdir}/firmware/nxp
}

FILES_${PN} += "${base_libdir}/firmware/nxp"

COMPATIBLE_MACHINE = "(colibri-imx6ull|colibri-imx8x|verdin-imx8mm|verdin-imx8mp|apalis-imx8|apalis-imx8x)"

addtask nxp_driver_unpack before do_patch after do_unpack
do_nxp_driver_unpack() {
    :
}

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
#                                         SD-UAPSTA-8997-U16-MMC-W16.68.10.p162-C4X16693_V4-app-src.tgz \
#                                         SD-UAPSTA-8997-U16-MMC-W16.68.10.p162-C4X16693_V4-mlan-src.tgz \
#                                         SD-UAPSTA-8997-U16-MMC-W16.68.10.p162-C4X16693_V4-MGPL-src.tgz
#    for i in SD-UAPSTA-8997-U16-MMC-W16.68.10.p162-C4X16693_V4-app-src.tgz \
#             SD-UAPSTA-8997-U16-MMC-W16.68.10.p162-C4X16693_V4-mlan-src.tgz \
#             SD-UAPSTA-8997-U16-MMC-W16.68.10.p162-C4X16693_V4-MGPL-src.tgz; do
#        tar --strip-components=1 -C ${WORKDIR} \
#            -xf ${WORKDIR}/archive.sd-sd/$i \
#            SD-UAPSTA-BT-8997-U16-MMC-W16.68.10.p162-16.26.10.p162-C4X16693_V4-MGPL/wlan_src
#    done
#}

SRC_URI_append_interface-diversity-usb-usb = " ${NXP_PROPRIETARY_DRIVER_LOCATION}/${NXP_PROPRIETARY_DRIVER_FILENAME};name=usb-usb-driver;subdir=archive.usb-usb "
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
#    tar -C ${WORKDIR}/archive.usb-usb/ -xf ${WORKDIR}/archive.usb-usb/USB-WLAN-BT-8997-U16-X86-W16.68.10.p136-16.26.10.p136-C4X16687_V4-MGPL.tar \
#                                           USB-UAPSTA-8997-U16-X86-W16.68.10.p136-C4X16687_V4-app-src.tgz \
#                                           USB-UAPSTA-8997-U16-X86-W16.68.10.p136-C4X16687_V4-MGPL-src.tgz \
#                                           USB-UAPSTA-8997-U16-X86-W16.68.10.p136-C4X16687_V4-mlan-src.tgz
#    for i in USB-UAPSTA-8997-U16-X86-W16.68.10.p136-C4X16687_V4-app-src.tgz \
#             USB-UAPSTA-8997-U16-X86-W16.68.10.p136-C4X16687_V4-MGPL-src.tgz \
#             USB-UAPSTA-8997-U16-X86-W16.68.10.p136-C4X16687_V4-mlan-src.tgz; do
#        tar --strip-components=1 -C ${WORKDIR} \
#            -xf ${WORKDIR}/archive.usb-usb/$i \
#            USB-UAPSTA-BT-8997-U16-X86-W16.68.10.p136-16.26.10.p136-C4X16687_V4-MGPL/wlan_src
#    done
#}

#NXP_PROPRIETARY_DRIVER_FILENAME_interface-diversity-pcie-usb = "PCIE-WLAN-USB-BT-8997-U16-X86-W16.88.10.p173-16.26.10.p173-C4X16698_V4-MGPL.zip"

SRC_URI_append_interface-diversity-pcie-usb = " ${NXP_PROPRIETARY_DRIVER_LOCATION}/${NXP_PROPRIETARY_DRIVER_FILENAME};name=pcie-usb-driver;subdir=archive.pcie-usb "
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
#    DRVNAME=$(basename ${NXP_PROPRIETARY_DRIVER_FILENAME} | sed 's/zip/tar/')
#    tar -C ${WORKDIR}/archive.pcie-usb/ -xf ${WORKDIR}/archive.pcie-usb/$DRVNAME
#    for i in `ls ${WORKDIR}/archive.pcie-usb/*-src.tgz`; do
#        tar --strip-components=1 -C ${WORKDIR} \
#            -xf $i
#    done
#}
