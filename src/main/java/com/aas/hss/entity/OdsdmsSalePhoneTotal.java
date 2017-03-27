package com.aas.hss.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OdsdmsSalePhoneTotal {
    private BigDecimal dtMonth;

    private String province;

    private String attrClss;

    private String attrName;

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

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName == null ? null : attrName.trim();
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

	@Override
	public String toString() {
		return "OdsdmsSalePhoneTotal [dtMonth=" + dtMonth + ", province=" + province + ", attrClss=" + attrClss
				+ ", attrName=" + attrName + ", saleQty=" + saleQty + ", saleAmt=" + saleAmt + ", loadDt=" + loadDt
				+ "]";
	}
}