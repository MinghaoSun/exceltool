package com.aas.hss.service.impl;

import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.stereotype.Service;
import com.aas.hss.entity.OdsdmsSalePhoneBrank;
import com.aas.hss.entity.OdsdmsSalePhoneChnlRate;
import com.aas.hss.entity.OdsdmsSalePhoneCityRate;
import com.aas.hss.entity.OdsdmsSalePhoneMarktShare;
import com.aas.hss.entity.OdsdmsSalePhoneOnline;
import com.aas.hss.entity.OdsdmsSalePhoneTotal;

@Service
public interface TranslateExcelService {

	// 处理电子商务市场品牌容量发展趋势sheet页
	List<OdsdmsSalePhoneOnline> analysisDzswscpprlfzqs(HSSFSheet xssfSheet, String date);

	// 处理电子商务市场品牌销售额发展趋势sheet页
	List<OdsdmsSalePhoneOnline> analysisDzswscppxsefzqs(HSSFSheet sheetAt, String date,
			List<OdsdmsSalePhoneOnline> odsdmsSalePhoneOnlinelist);

	// 保存数据到OdsdmsSalePhoneOnline表
	void saveOdsdmsSalePhoneOnline(List<OdsdmsSalePhoneOnline> odsdmsSalePhoneOnlinelist);

	// 查询各省份各品牌线下销售数量
	List<OdsdmsSalePhoneBrank> analysisProvinceBrandQtyAtSheet3(HSSFSheet sheetAt, String date, String attrClss,String bstart,String bend);

	// 查询各省份各品牌线下销售额
	List<OdsdmsSalePhoneBrank> analysisProviceBrandAmtAtSheet4(HSSFSheet sheetAt, String date,
			List<OdsdmsSalePhoneBrank> odsdmsSalePhoneOfflinelist,String bstart,String bend);

	// 保存数据到OdsdmsSalePhoneOffline
	void saveOdsdmsSalePhoneBrank(List<OdsdmsSalePhoneBrank> odsdmsSalePhoneOfflinelist);

	List<OdsdmsSalePhoneTotal> analysisProvicePhonePropertyQtyAtSheet(HSSFSheet hSSFSheet, String date,
			String attrClsstype, String start, String end);

	List<OdsdmsSalePhoneTotal> analysisProvicePhonePropertyAmtAtSheet(HSSFSheet sheetAt, String date,
			String attrClssTotalHyjj, String string, String string2,
			List<OdsdmsSalePhoneTotal> odsdmsSalePhoneTotallist_TOTAL_HYJJ);

	void saveOdsdmsSalePhoneTotal(List<OdsdmsSalePhoneTotal> odsdmsSalePhoneTotallist_TOTAL_HYJJ);

	List<OdsdmsSalePhoneMarktShare> analysisCityBrandQtyAtSheet(HSSFSheet sheetAt, String date, String start, String end, String tyFlag,List<OdsdmsSalePhoneMarktShare> list);

	List<OdsdmsSalePhoneMarktShare> analysisCityBrandAmtAtSheet(HSSFSheet sheetAt, String date, String string,
			String string2, String marktShareTyflagDq, List<OdsdmsSalePhoneMarktShare> odsdmsSalePhoneMarktSharelist);

	void saveOdsdmsSalePhoneMarktShare(List<OdsdmsSalePhoneMarktShare> odsdmsSalePhoneMarktSharelist);

	List<OdsdmsSalePhoneCityRate> analysisCityRateQtyAtSheet(HSSFSheet sheetAt, String date, String start,
			String end,String first, String marktRateTyflagDq, List<OdsdmsSalePhoneCityRate> arrayList);

	List<OdsdmsSalePhoneCityRate> analysisCityRateAmtAtSheet(HSSFSheet sheetAt, String date, String start, String end,String first,
			String marktRateTyflagDq, List<OdsdmsSalePhoneCityRate> odsdmsSalePhoneCityRatelist);

	void saveOdsdmsSalePhoneCityRate(List<OdsdmsSalePhoneCityRate> odsdmsSalePhoneCityRatelist);

	List<OdsdmsSalePhoneChnlRate> analysisCHNLRateQtyAtSheet(HSSFSheet hSSFSheet, String date, int start,int end,
			String tyFlag, List<OdsdmsSalePhoneChnlRate> list);

	void saveOdsdmsSalePhoneChnlRate(List<OdsdmsSalePhoneChnlRate> odsdmsSalePhoneChnlRatelist);
	/**
	 * @description 统一保存EXCEL数据到数据库
	 * @param odsdmsSalePhoneOnlineList
	 * @param odsdmsSalePhoneBrankList
	 * @param odsdmsSalePhoneTotalList
	 * @param odsdmsSalePhoneMarktShareList
	 * @param odsdmsSalePhoneCityRateList
	 * @param odsdmsSalePhoneChnlRateList
	 * @return
	 */
	boolean savePhoneExcelData(List<OdsdmsSalePhoneOnline> odsdmsSalePhoneOnlineList,List<OdsdmsSalePhoneBrank> odsdmsSalePhoneBrankList,List<OdsdmsSalePhoneTotal> odsdmsSalePhoneTotalList,List<OdsdmsSalePhoneMarktShare> odsdmsSalePhoneMarktShareList,List<OdsdmsSalePhoneCityRate> odsdmsSalePhoneCityRateList,List<OdsdmsSalePhoneChnlRate> odsdmsSalePhoneChnlRateList);

}
