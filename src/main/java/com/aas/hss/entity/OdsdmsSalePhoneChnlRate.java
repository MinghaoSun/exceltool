package com.aas.hss.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OdsdmsSalePhoneChnlRate {
    private BigDecimal dtMonth;

    private String chnlTyp;

    private String kpiTyp;

    private BigDecimal kpiValM;

    private BigDecimal kpiValLym;

    private BigDecimal kpiValY;

    private BigDecimal kpiValLy;

    private Date loadDt;

    public BigDecimal getDtMonth() {
        return dtMonth;
    }

    public void setDtMonth(BigDecimal dtMonth) {
        this.dtMonth = dtMonth;
    }

    public String getChnlTyp() {
        return chnlTyp;
    }

    public void setChnlTyp(String chnlTyp) {
        this.chnlTyp = chnlTyp == null ? null : chnlTyp.trim();
    }

    public String getKpiTyp() {
        return kpiTyp;
    }

    public void setKpiTyp(String kpiTyp) {
        this.kpiTyp = kpiTyp == null ? null : kpiTyp.trim();
    }

    public BigDecimal getKpiValM() {
        return kpiValM;
    }

    public void setKpiValM(BigDecimal kpiValM) {
        this.kpiValM = kpiValM;
    }

    public BigDecimal getKpiValLym() {
        return kpiValLym;
    }

    public void setKpiValLym(BigDecimal kpiValLym) {
        this.kpiValLym = kpiValLym;
    }

    public BigDecimal getKpiValY() {
        return kpiValY;
    }

    public void setKpiValY(BigDecimal kpiValY) {
        this.kpiValY = kpiValY;
    }

    public BigDecimal getKpiValLy() {
        return kpiValLy;
    }

    public void setKpiValLy(BigDecimal kpiValLy) {
        this.kpiValLy = kpiValLy;
    }

    public Date getLoadDt() {
        return loadDt;
    }

    public void setLoadDt(Date loadDt) {
        this.loadDt = loadDt;
    }
}