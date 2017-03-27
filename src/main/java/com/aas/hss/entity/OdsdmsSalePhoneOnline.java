package com.aas.hss.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OdsdmsSalePhoneOnline {
    private BigDecimal dtMonth;

    private BigDecimal onlineFlag;

    private String brandName;

    private BigDecimal saleQty;

    private BigDecimal saleAmt;

    private Date loadDt;

    public BigDecimal getDtMonth() {
        return dtMonth;
    }

    public void setDtMonth(BigDecimal dtMonth) {
        this.dtMonth = dtMonth;
    }

    public BigDecimal getOnlineFlag() {
        return onlineFlag;
    }

    public void setOnlineFlag(BigDecimal onlineFlag) {
        this.onlineFlag = onlineFlag;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public BigDecimal getSaleQty() {
        return saleQty;
    }

    public void setSaleQty(BigDecimal saleQty) {
        this.saleQty = saleQty;
    }

    public BigDecimal getSaleAmt() {
        return saleAmt;
    }

    public void setSaleAmt(BigDecimal saleAmt) {
        this.saleAmt = saleAmt;
    }

    public Date getLoadDt() {
        return loadDt;
    }

    public void setLoadDt(Date loadDt) {
        this.loadDt = loadDt;
    }
}