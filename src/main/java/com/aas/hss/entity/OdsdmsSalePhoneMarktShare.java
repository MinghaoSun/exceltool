package com.aas.hss.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OdsdmsSalePhoneMarktShare {
    private BigDecimal dtMonth;

    private String brankName;

    private String marktLevel;

    private BigDecimal tyFlag;

    private BigDecimal saleQtyRate;

    private BigDecimal saleAmtRate;

    private Date loadDt;

    public BigDecimal getDtMonth() {
        return dtMonth;
    }

    public void setDtMonth(BigDecimal dtMonth) {
        this.dtMonth = dtMonth;
    }

    public String getBrankName() {
        return brankName;
    }

    public void setBrankName(String brankName) {
        this.brankName = brankName == null ? null : brankName.trim();
    }

    public String getMarktLevel() {
        return marktLevel;
    }

    public void setMarktLevel(String marktLevel) {
        this.marktLevel = marktLevel == null ? null : marktLevel.trim();
    }

    public BigDecimal getTyFlag() {
        return tyFlag;
    }

    public void setTyFlag(BigDecimal tyFlag) {
        this.tyFlag = tyFlag;
    }

    public BigDecimal getSaleQtyRate() {
        return saleQtyRate;
    }

    public void setSaleQtyRate(BigDecimal saleQtyRate) {
        this.saleQtyRate = saleQtyRate;
    }

    public BigDecimal getSaleAmtRate() {
        return saleAmtRate;
    }

    public void setSaleAmtRate(BigDecimal saleAmtRate) {
        this.saleAmtRate = saleAmtRate;
    }

    public Date getLoadDt() {
        return loadDt;
    }

    public void setLoadDt(Date loadDt) {
        this.loadDt = loadDt;
    }
}