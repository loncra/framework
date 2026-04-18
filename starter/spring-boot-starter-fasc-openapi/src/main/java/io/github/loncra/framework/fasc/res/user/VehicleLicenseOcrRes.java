package io.github.loncra.framework.fasc.res.user;

import io.github.loncra.framework.fasc.bean.common.VehicleBackInfo;
import io.github.loncra.framework.fasc.bean.common.VehicleFrontInfo;

import java.util.List;

public class VehicleLicenseOcrRes {
    private String serialNo;

    VehicleFrontInfo vehicleFrontInfo;
    VehicleBackInfo vehicleBackInfo;
    private List<String> warningMsg;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public VehicleFrontInfo getVehicleFrontInfo() {
        return vehicleFrontInfo;
    }

    public void setVehicleFrontInfo(VehicleFrontInfo vehicleFrontInfo) {
        this.vehicleFrontInfo = vehicleFrontInfo;
    }

    public VehicleBackInfo getVehicleBackInfo() {
        return vehicleBackInfo;
    }

    public void setVehicleBackInfo(VehicleBackInfo vehicleBackInfo) {
        this.vehicleBackInfo = vehicleBackInfo;
    }

    public List<String> getWarningMsg() {
        return warningMsg;
    }

    public void setWarningMsg(List<String> warningMsg) {
        this.warningMsg = warningMsg;
    }
}
