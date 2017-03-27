package com.aas.hss.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OdsdmsSalePhoneBrank {
    private BigDecimal dtMonth;

    private String province;

    private String attrClss;

    private String brankName;

    private BigDecimal saleQty;

    private BigDecimal saleAmt;

    private Date loadDt;

    public BigDecimal getDtMonth() {
        return dtMonth;
    }

    public void setDtMonth(BigDecimal dtMonth) {
        this.dtMonth = dtMonth;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getAttrClss() {
        return attrClss;
    }

    public void setAttrClss(String attrClss) {
        this.attrClss = attrClss == null ? null : attrClss.trim();
    }

    public String getBrankName() {
        return brankName;
    }

    public void setBrankName(String brankName) {
        this.brankName = brankName == null ? null : brankName.trim();
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