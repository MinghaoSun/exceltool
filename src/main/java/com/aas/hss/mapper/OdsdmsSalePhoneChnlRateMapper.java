package com.aas.hss.mapper;

import java.util.List;

import com.aas.hss.entity.OdsdmsSalePhoneChnlRate;

public interface OdsdmsSalePhoneChnlRateMapper {
    int insert(OdsdmsSalePhoneChnlRate record);

    int insertSelective(OdsdmsSalePhoneChnlRate record);
    
    int insertList(List<OdsdmsSalePhoneChnlRate> record);
}