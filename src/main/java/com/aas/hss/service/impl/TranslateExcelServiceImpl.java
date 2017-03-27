package com.aas.hss.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aas.hss.common.util.Constants;
import com.aas.hss.common.util.ExcelColumnTools;
import com.aas.hss.common.util.StringHelper;
import com.aas.hss.entity.OdsdmsSalePhoneBrank;
import com.aas.hss.entity.OdsdmsSalePhoneChnlRate;
import com.aas.hss.entity.OdsdmsSalePhoneCityRate;
import com.aas.hss.entity.OdsdmsSalePhoneMarktShare;
import com.aas.hss.entity.OdsdmsSalePhoneOnline;
import com.aas.hss.entity.OdsdmsSalePhoneTotal;
import com.aas.hss.mapper.OdsdmsSalePhoneBrankMapper;
import com.aas.hss.mapper.OdsdmsSalePhoneChnlRateMapper;
import com.aas.hss.mapper.OdsdmsSalePhoneCityRateMapper;
import com.aas.hss.mapper.OdsdmsSalePhoneMarktShareMapper;
import com.aas.hss.mapper.OdsdmsSalePhoneOnlineMapper;
import com.aas.hss.mapper.OdsdmsSalePhoneTotalMapper;

@Service("translateExcelService")
public class TranslateExcelServiceImpl implements TranslateExcelService {

	@Autowired
	private OdsdmsSalePhoneOnlineMapper odsdmsSalePhoneOnlineMapper;

	@Autowired
	private OdsdmsSalePhoneBrankMapper odsdmsSalePhoneBrankMapper;

	@Autowired
	private OdsdmsSalePhoneTotalMapper odsdmsSalePhoneTotalMapper;

	@Autowired
	private OdsdmsSalePhoneMarktShareMapper odsdmsSalePhoneMarktShareMapper;
	
	@Autowired
	private OdsdmsSalePhoneCityRateMapper odsdmsSalePhoneCityRateMapper;
	
	@Autowired
	private OdsdmsSalePhoneChnlRateMapper osdsdmsSalePhoneChnlRateMapper;

	/**
	 * @description 获取电子商务市场品牌容量
	 * @return
	 */
	@Override
	public List<OdsdmsSalePhoneOnline> analysisDzswscpprlfzqs(HSSFSheet xssfSheet, String date) {
		// 从第五行开始
		List<OdsdmsSalePhoneOnline> list = new ArrayList<OdsdmsSalePhoneOnline>();
		for (int rowNum = 4; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
			HSSFRow xssfRow = xssfSheet.getRow(rowNum);
			// B(1) 品牌 O(14)销量
			if (xssfRow.getCell(1) == null) {
				continue;
			}
			xssfRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String brand = xssfRow.getCell(1).getStringCellValue();
			if (StringUtils.isNotBlank(brand) && !"总计".equals(brand)) {
				OdsdmsSalePhoneOnline odsdmsSalePhoneOnline = new OdsdmsSalePhoneOnline();
				xssfRow.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
				String amount = xssfRow.getCell(14).getStringCellValue();
				amount = StringHelper.emptyValidate(amount, "0");
				odsdmsSalePhoneOnline.setBrandName(brand);
				// 单位千
				odsdmsSalePhoneOnline
						.setSaleQty(new BigDecimal(amount).multiply(new BigDecimal(Constants.UNIT_THOUSAND)));
				odsdmsSalePhoneOnline.setOnlineFlag(new BigDecimal(1));
				odsdmsSalePhoneOnline.setLoadDt(new Date());
				odsdmsSalePhoneOnline.setDtMonth(new BigDecimal(date));
				list.add(odsdmsSalePhoneOnline);
				// odsdmsSalePhoneOnlineMapper.insertSelective(odsdmsSalePhoneOnline);
			}
		}
		return list;
	}

	@Override
	public List<OdsdmsSalePhoneOnline> analysisDzswscppxsefzqs(HSSFSheet hSSFSheet, String date,
			List<OdsdmsSalePhoneOnline> odsdmsSalePhoneOnlinelist) {
		// 从第五行开始
		for (int rowNum = 4; rowNum <= hSSFSheet.getLastRowNum(); rowNum++) {
			HSSFRow xssfRow = hSSFSheet.getRow(rowNum);
			// B(1) 品牌 O(14)销量
			if (xssfRow.getCell(1) == null) {
				continue;
			}
			xssfRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String brand = xssfRow.getCell(1).getStringCellValue();
			if (StringUtils.isNotBlank(brand) && !"总计".equals(brand)) {
				xssfRow.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
				String qty = xssfRow.getCell(14).getStringCellValue();
				qty = StringHelper.emptyValidate(qty, "0");
				// 暂定容量表和销售额表的品牌都是一样的
				for (OdsdmsSalePhoneOnline odsdmsSalePhoneOnline : odsdmsSalePhoneOnlinelist) {
					if (brand.toUpperCase().trim().equals(odsdmsSalePhoneOnline.getBrandName().toUpperCase().trim())) {
						// 单位百万
						odsdmsSalePhoneOnline
								.setSaleAmt(new BigDecimal(qty).multiply(new BigDecimal(Constants.UNIT_MILLION)));
						break;
					}
				}
			}
		}
		return odsdmsSalePhoneOnlinelist;
	}

	@Override
	public void saveOdsdmsSalePhoneOnline(List<OdsdmsSalePhoneOnline> odsdmsSalePhoneOnlinelist) {
		for (OdsdmsSalePhoneOnline OdsdmsSalePhoneOnline : odsdmsSalePhoneOnlinelist) {
			odsdmsSalePhoneOnlineMapper.insertSelective(OdsdmsSalePhoneOnline);
		}
	}

	@Override
	public List<OdsdmsSalePhoneBrank> analysisProvinceBrandQtyAtSheet3(HSSFSheet hSSFSheet, String date,
			String attrClss,String bstart,String bend) {
		// 先取出各品牌和其对应的cell号
		List<OdsdmsSalePhoneBrank> list = new ArrayList<OdsdmsSalePhoneBrank>();
		HSSFRow hSSFRow3 = hSSFSheet.getRow(3);
		Map<Integer, String> brandMap = new HashMap<Integer, String>();
		/*int brandstart = ExcelColumnTools.excelColStrToNum("AJ", 2);
		int brandstop = ExcelColumnTools.excelColStrToNum("IV", 2);*/
		int brandstart = ExcelColumnTools.excelColStrToNum(bstart, 2);
		int brandstop = ExcelColumnTools.excelColStrToNum(bend, 2);
		for (int i = brandstart - 1; i <= brandstop - 1; i++) {
			hSSFRow3.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
			String brand = hSSFRow3.getCell(i).getStringCellValue();
			brandMap.put(new Integer(i), brand);
		}
		for (int rowNum = 4; rowNum <= hSSFSheet.getLastRowNum(); rowNum++) {
			HSSFRow xssfRow = hSSFSheet.getRow(rowNum);
			if (xssfRow.getCell(1) == null) {
				continue;
			}
			xssfRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String provice = xssfRow.getCell(1).getStringCellValue();
			if (StringUtils.isBlank(provice) || "总计".equals(provice)) {
				continue;
			}
			xssfRow.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
			String totalAmount = xssfRow.getCell(3).getStringCellValue();
			for (int i = brandstart - 1; i <= brandstop - 1; i++) {
				String percent = "0";
				if (xssfRow.getCell(i) != null) {
					xssfRow.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
					percent = xssfRow.getCell(i).getStringCellValue();
				}
				percent = StringHelper.emptyValidate(percent, "0");
				BigDecimal b_percent = new BigDecimal(percent);
				String brand = brandMap.get(new Integer(i));
				OdsdmsSalePhoneBrank odsdmsSalePhoneBrank = new OdsdmsSalePhoneBrank();
				odsdmsSalePhoneBrank.setAttrClss(attrClss);
				odsdmsSalePhoneBrank.setBrankName(brand);
				odsdmsSalePhoneBrank.setDtMonth(new BigDecimal(date));
				odsdmsSalePhoneBrank.setLoadDt(new Date());
				odsdmsSalePhoneBrank.setProvince(provice);
				// 单位是千注意转换
				odsdmsSalePhoneBrank.setSaleQty(new BigDecimal(totalAmount)
						.multiply(new BigDecimal(Constants.UNIT_THOUSAND)).multiply(b_percent));
				list.add(odsdmsSalePhoneBrank);
			}
		}
		return list;
	}

	@Override
	public List<OdsdmsSalePhoneBrank> analysisProviceBrandAmtAtSheet4(HSSFSheet hSSFSheet, String date,
			List<OdsdmsSalePhoneBrank> odsdmsSalePhoneOfflinelist,String bstart,String bend) {
		HSSFRow hSSFRow3 = hSSFSheet.getRow(3);
		Map<Integer, String> brandMap = new HashMap<Integer, String>();
		// AJ-IV
		int brandstart = ExcelColumnTools.excelColStrToNum(bstart, 2);
		int brandstop = ExcelColumnTools.excelColStrToNum(bend, 2);
		for (int i = brandstart - 1; i <= brandstop - 1; i++) {
			hSSFRow3.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
			String brand = hSSFRow3.getCell(i).getStringCellValue();
			brandMap.put(new Integer(i), brand);
		}
		for (int rowNum = 4; rowNum <= hSSFSheet.getLastRowNum(); rowNum++) {
			HSSFRow xssfRow = hSSFSheet.getRow(rowNum);
			if (xssfRow.getCell(1) == null) {
				continue;
			}
			xssfRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String provice = xssfRow.getCell(1).getStringCellValue();
			if (StringUtils.isBlank(provice) || "总计".equals(provice)) {
				continue;
			}
			xssfRow.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
			String totalAmt = xssfRow.getCell(3).getStringCellValue();
			for (int i = brandstart - 1; i <= brandstop - 1; i++) {
				String percent = "0";
				if (xssfRow.getCell(i) != null) {
					xssfRow.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
					percent = xssfRow.getCell(i).getStringCellValue();
				}
				percent = StringHelper.emptyValidate(percent, "0");
				BigDecimal b_percent = new BigDecimal(percent);
				String brand = brandMap.get(new Integer(i));
				// 根据省份和品牌筛选
				for (OdsdmsSalePhoneBrank odsdmsSalePhoneOffline : odsdmsSalePhoneOfflinelist) {
					String p_provice = odsdmsSalePhoneOffline.getProvince();
					String p_brand = odsdmsSalePhoneOffline.getBrankName();
					if (p_provice.equals(provice) && p_brand.equals(brand)) {
						odsdmsSalePhoneOffline.setSaleAmt(new BigDecimal(totalAmt)
								.multiply(new BigDecimal(Constants.UNIT_MILLION)).multiply(b_percent));
						break;
					}
				}
			}
		}
		return odsdmsSalePhoneOfflinelist;
	}

	@Override
	public void saveOdsdmsSalePhoneBrank(List<OdsdmsSalePhoneBrank> odsdmsSalePhoneBranklist) {
		for (OdsdmsSalePhoneBrank odsdmsSalePhoneBrank : odsdmsSalePhoneBranklist) {
			odsdmsSalePhoneBrankMapper.insertSelective(odsdmsSalePhoneBrank);
		}
	}

	@Override
	public List<OdsdmsSalePhoneTotal> analysisProvicePhonePropertyQtyAtSheet(HSSFSheet hSSFSheet, String date,
			String attrClsstype, String start, String end) {
		// 先取出各品牌和其对应的cell号
		List<OdsdmsSalePhoneTotal> list = new ArrayList<OdsdmsSalePhoneTotal>();
		HSSFRow hSSFRow3 = hSSFSheet.getRow(3);
		Map<Integer, String> brandMap = new HashMap<Integer, String>();
		// AJ-IV
		int brandstart = ExcelColumnTools.excelColStrToNum(start, start.length());
		int brandstop = ExcelColumnTools.excelColStrToNum(end, end.length());
		for (int i = brandstart - 1; i <= brandstop - 1; i++) {
			hSSFRow3.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
			String brand = hSSFRow3.getCell(i).getStringCellValue();
			brandMap.put(new Integer(i), brand);
		}
		for (int rowNum = 4; rowNum <= hSSFSheet.getLastRowNum(); rowNum++) {
			HSSFRow xssfRow = hSSFSheet.getRow(rowNum);
			if (xssfRow.getCell(1) == null) {
				continue;
			}
			xssfRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String provice = xssfRow.getCell(1).getStringCellValue();
			if (StringUtils.isBlank(provice) || "总计".equals(provice)) {
				continue;
			}
			xssfRow.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
			String totalAmount = xssfRow.getCell(3).getStringCellValue();
			for (int i = brandstart - 1; i <= brandstop - 1; i++) {
				String percent = "0";
				if (xssfRow.getCell(i) != null) {
					xssfRow.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
					percent = xssfRow.getCell(i).getStringCellValue();
				}
				percent = StringHelper.emptyValidate(percent, "0");
				BigDecimal b_percent = new BigDecimal(percent);
				String property = brandMap.get(new Integer(i));
				OdsdmsSalePhoneTotal odsdmsSalePhoneTotal = new OdsdmsSalePhoneTotal();
				odsdmsSalePhoneTotal.setAttrClss(attrClsstype);
				odsdmsSalePhoneTotal.setAttrName(property);
				odsdmsSalePhoneTotal.setDtMonth(new BigDecimal(date));
				odsdmsSalePhoneTotal.setLoadDt(new Date());
				odsdmsSalePhoneTotal.setProvince(provice);
				// 单位是千注意转换
				odsdmsSalePhoneTotal.setSaleQty(new BigDecimal(totalAmount)
						.multiply(new BigDecimal(Constants.UNIT_THOUSAND)).multiply(b_percent));
				list.add(odsdmsSalePhoneTotal);
			}
		}
		return list;
	}

	@Override
	public List<OdsdmsSalePhoneTotal> analysisProvicePhonePropertyAmtAtSheet(HSSFSheet hSSFSheet, String date,
			String attrClsstype, String start, String end,
			List<OdsdmsSalePhoneTotal> odsdmsSalePhoneTotallist_TOTAL_HYJJ) {
		HSSFRow hSSFRow3 = hSSFSheet.getRow(3);
		Map<Integer, String> brandMap = new HashMap<Integer, String>();
		// AJ-IV
		int brandstart = ExcelColumnTools.excelColStrToNum(start, start.length());
		int brandstop = ExcelColumnTools.excelColStrToNum(end, end.length());
		for (int i = brandstart - 1; i <= brandstop - 1; i++) {
			hSSFRow3.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
			String brand = hSSFRow3.getCell(i).getStringCellValue();
			brandMap.put(new Integer(i), brand);
		}
		for (int rowNum = 4; rowNum <= hSSFSheet.getLastRowNum(); rowNum++) {
			HSSFRow xssfRow = hSSFSheet.getRow(rowNum);
			if (xssfRow.getCell(1) == null) {
				continue;
			}
			xssfRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String provice = xssfRow.getCell(1).getStringCellValue();
			if (StringUtils.isBlank(provice) || "总计".equals(provice)) {
				continue;
			}
			xssfRow.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
			String totalAmt = xssfRow.getCell(3).getStringCellValue();
			for (int i = brandstart - 1; i <= brandstop - 1; i++) {
				String percent = "0";
				if (xssfRow.getCell(i) != null) {
					xssfRow.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
					percent = xssfRow.getCell(i).getStringCellValue();
				}
				percent = StringHelper.emptyValidate(percent, "0");
				BigDecimal b_percent = new BigDecimal(percent);
				String brand = brandMap.get(new Integer(i));
				// 根据省份和品牌筛选
				for (OdsdmsSalePhoneTotal odsdmsSalePhoneTotal : odsdmsSalePhoneTotallist_TOTAL_HYJJ) {
					String p_provice = odsdmsSalePhoneTotal.getProvince();
					String p_brand = odsdmsSalePhoneTotal.getAttrName();
					if (p_provice.equals(provice) && p_brand.toUpperCase().trim().equals(brand.toUpperCase().trim())) {
						odsdmsSalePhoneTotal.setSaleAmt(new BigDecimal(totalAmt)
								.multiply(new BigDecimal(Constants.UNIT_MILLION)).multiply(b_percent));
						break;
					}
				}
			}
		}
		return odsdmsSalePhoneTotallist_TOTAL_HYJJ;
	}

	@Override
	public void saveOdsdmsSalePhoneTotal(List<OdsdmsSalePhoneTotal> odsdmsSalePhoneTotallist) {
		for (OdsdmsSalePhoneTotal odsdmsSalePhoneTotal : odsdmsSalePhoneTotallist) {
			odsdmsSalePhoneTotalMapper.insertSelective(odsdmsSalePhoneTotal);

		}
	}

	@Override
	public List<OdsdmsSalePhoneMarktShare> analysisCityBrandQtyAtSheet(HSSFSheet hSSFSheet, String date, String start,
			String end, String tyFlag, List<OdsdmsSalePhoneMarktShare> list) {
		// 先取出各品牌和其对应的cell号
		HSSFRow hSSFRow3 = hSSFSheet.getRow(3);
		Map<Integer, String> brandMap = new HashMap<Integer, String>();
		// AJ-IV
		int brandstart = ExcelColumnTools.excelColStrToNum(start, start.length());
		int brandstop = ExcelColumnTools.excelColStrToNum(end, end.length());
		for (int i = brandstart - 1; i <= brandstop - 1; i++) {
			hSSFRow3.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
			String brand = hSSFRow3.getCell(i).getStringCellValue();
			brandMap.put(new Integer(i), brand);
		}
		for (int rowNum = 4; rowNum <= hSSFSheet.getLastRowNum(); rowNum++) {
			HSSFRow xssfRow = hSSFSheet.getRow(rowNum);
			if (xssfRow.getCell(1) == null) {
				continue;
			}
			xssfRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String brand = xssfRow.getCell(1).getStringCellValue();
			if (StringUtils.isBlank(brand) || "总计".equals(brand)) {
				continue;
			}
			for (int i = brandstart - 1; i <= brandstop - 1; i++) {
				String percent = "0";
				if (xssfRow.getCell(i) != null) {
					xssfRow.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
					percent = xssfRow.getCell(i).getStringCellValue();
				}
				percent = StringHelper.emptyValidate(percent, "0");
				BigDecimal b_percent = new BigDecimal(percent);
				String property = brandMap.get(new Integer(i));
				OdsdmsSalePhoneMarktShare odsdmsSalePhoneMarktShare = new OdsdmsSalePhoneMarktShare();
				odsdmsSalePhoneMarktShare.setDtMonth(new BigDecimal(date));
				odsdmsSalePhoneMarktShare.setLoadDt(new Date());
				odsdmsSalePhoneMarktShare.setBrankName(brand);
				odsdmsSalePhoneMarktShare.setMarktLevel(property);
				odsdmsSalePhoneMarktShare.setSaleQtyRate(b_percent);
				odsdmsSalePhoneMarktShare.setTyFlag(new BigDecimal(tyFlag));
				list.add(odsdmsSalePhoneMarktShare);
			}
		}
		return list;
	}

	@Override
	public List<OdsdmsSalePhoneMarktShare> analysisCityBrandAmtAtSheet(HSSFSheet hSSFSheet, String date, String start,
			String end, String tyFlag, List<OdsdmsSalePhoneMarktShare> odsdmsSalePhoneMarktSharelist) {
		HSSFRow hSSFRow3 = hSSFSheet.getRow(3);
		Map<Integer, String> brandMap = new HashMap<Integer, String>();
		int brandstart = ExcelColumnTools.excelColStrToNum(start, start.length());
		int brandstop = ExcelColumnTools.excelColStrToNum(end, end.length());
		for (int i = brandstart - 1; i <= brandstop - 1; i++) {
			hSSFRow3.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
			String brand = hSSFRow3.getCell(i).getStringCellValue();
			brandMap.put(new Integer(i), brand);
		}
		for (int rowNum = 4; rowNum <= hSSFSheet.getLastRowNum(); rowNum++) {
			HSSFRow xssfRow = hSSFSheet.getRow(rowNum);
			if (xssfRow.getCell(1) == null) {
				continue;
			}
			xssfRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String provice = xssfRow.getCell(1).getStringCellValue();
			if (StringUtils.isBlank(provice) || "总计".equals(provice)) {
				continue;
			}
			for (int i = brandstart - 1; i <= brandstop - 1; i++) {
				String percent = "0";
				if (xssfRow.getCell(i) != null) {
					xssfRow.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
					percent = xssfRow.getCell(i).getStringCellValue();
				}
				percent = StringHelper.emptyValidate(percent, "0");
				BigDecimal b_percent = new BigDecimal(percent);
				String property = brandMap.get(new Integer(i));
				// 根据省份和品牌筛选
				for (OdsdmsSalePhoneMarktShare odsdmsSalePhoneMarktShare : odsdmsSalePhoneMarktSharelist) {
					String p_brankName = odsdmsSalePhoneMarktShare.getBrankName();
					String p_marketLevel = odsdmsSalePhoneMarktShare.getMarktLevel();
					String p_typeFlag = odsdmsSalePhoneMarktShare.getTyFlag().toString();
					if (p_brankName.toUpperCase().trim().equals(provice.toUpperCase().trim())
							&& p_marketLevel.toUpperCase().trim().equals(property.toUpperCase().trim())
							&& p_typeFlag.toUpperCase().trim().equals(tyFlag)) {
						odsdmsSalePhoneMarktShare.setSaleAmtRate(b_percent);
						break;
					}
				}
			}
		}
		return odsdmsSalePhoneMarktSharelist;
	}

	@Override
	public void saveOdsdmsSalePhoneMarktShare(List<OdsdmsSalePhoneMarktShare> odsdmsSalePhoneMarktSharelist) {
		for (OdsdmsSalePhoneMarktShare odsdmsSalePhoneMarktShare : odsdmsSalePhoneMarktSharelist) {
			odsdmsSalePhoneMarktShareMapper.insertSelective(odsdmsSalePhoneMarktShare);
		}

	}

	@Override
	public List<OdsdmsSalePhoneCityRate> analysisCityRateQtyAtSheet(HSSFSheet hSSFSheet, String date, String start,
			String end,String first, String tyFlag, List<OdsdmsSalePhoneCityRate> list) {
		// 先取出各品牌和其对应的cell号
		HSSFRow hSSFRow3 = hSSFSheet.getRow(3);
		Map<Integer, String> brandMap = new HashMap<Integer, String>();
		int brandstart = ExcelColumnTools.excelColStrToNum(start, start.length());
		int brandstop = ExcelColumnTools.excelColStrToNum(end, end.length());
		int first_= ExcelColumnTools.excelColStrToNum(first, first.length());
		for (int i = brandstart - 1; i <= brandstop - 1; i++) {
			hSSFRow3.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
			String brand = hSSFRow3.getCell(i).getStringCellValue();
			brandMap.put(new Integer(i), brand);
		}
		for (int rowNum = 4; rowNum <= hSSFSheet.getLastRowNum(); rowNum++) {
			HSSFRow xssfRow = hSSFSheet.getRow(rowNum);
			if (xssfRow.getCell(first_-1) == null) {
				continue;
			}
			xssfRow.getCell(first_-1).setCellType(Cell.CELL_TYPE_STRING);
			String brand = xssfRow.getCell(first_-1).getStringCellValue();
			if (StringUtils.isBlank(brand) || "总计".equals(brand)) {
				continue;
			}
			for (int i = brandstart - 1; i <= brandstop - 1; i++) {
				String percent = "0";
				if (xssfRow.getCell(i) != null) {
					xssfRow.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
					percent = xssfRow.getCell(i).getStringCellValue();
				}
				percent = StringHelper.emptyValidate(percent, "0");
				BigDecimal b_percent = new BigDecimal(percent);
				String property = brandMap.get(new Integer(i));
				OdsdmsSalePhoneCityRate odsdmsSalePhoneCityRate = new OdsdmsSalePhoneCityRate();
				odsdmsSalePhoneCityRate.setDtMonth(new BigDecimal(date));
				odsdmsSalePhoneCityRate.setLoadDt(new Date());
				odsdmsSalePhoneCityRate.setBrankName(brand);
				odsdmsSalePhoneCityRate.setChnlTyp(property);
				odsdmsSalePhoneCityRate.setSaleQtyRate(b_percent);
				odsdmsSalePhoneCityRate.setTyFlag(new BigDecimal(tyFlag));
				list.add(odsdmsSalePhoneCityRate);
			}
		}
		return list;
	}

	@Override
	public List<OdsdmsSalePhoneCityRate> analysisCityRateAmtAtSheet(HSSFSheet hSSFSheet, String date, String start, String end,
			String first,String tyFlag, List<OdsdmsSalePhoneCityRate> list) {
		HSSFRow hSSFRow3 = hSSFSheet.getRow(3);
		Map<Integer, String> brandMap = new HashMap<Integer, String>();
		int brandstart = ExcelColumnTools.excelColStrToNum(start, start.length());
		int brandstop = ExcelColumnTools.excelColStrToNum(end, end.length());
		int first_ = ExcelColumnTools.excelColStrToNum(first, first.length());
		for (int i = brandstart - 1; i <= brandstop - 1; i++) {
			hSSFRow3.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
			String brand = hSSFRow3.getCell(i).getStringCellValue();
			brandMap.put(new Integer(i), brand);
		}
		for (int rowNum = 4; rowNum <= hSSFSheet.getLastRowNum(); rowNum++) {
			HSSFRow xssfRow = hSSFSheet.getRow(rowNum);
			if (xssfRow.getCell(first_-1) == null) {
				continue;
			}
			xssfRow.getCell(first_-1).setCellType(Cell.CELL_TYPE_STRING);
			String provice = xssfRow.getCell(first_-1).getStringCellValue();
			if (StringUtils.isBlank(provice) || "总计".equals(provice)) {
				continue;
			}
			for (int i = brandstart - 1; i <= brandstop - 1; i++) {
				String percent = "0";
				if (xssfRow.getCell(i) != null) {
					xssfRow.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
					percent = xssfRow.getCell(i).getStringCellValue();
				}
				percent = StringHelper.emptyValidate(percent, "0");
				BigDecimal b_percent = new BigDecimal(percent);
				String property = brandMap.get(new Integer(i));
				// 根据省份和品牌筛选
				for (OdsdmsSalePhoneCityRate odsdmsSalePhoneCityRate : list) {
					String p_brankName = odsdmsSalePhoneCityRate.getBrankName();
					String p_marketLevel = odsdmsSalePhoneCityRate.getChnlTyp();
					String p_typeFlag = odsdmsSalePhoneCityRate.getTyFlag().toString();
					if (p_brankName.toUpperCase().trim().equals(provice.toUpperCase().trim())
							&& p_marketLevel.toUpperCase().trim().equals(property.toUpperCase().trim())
							&& p_typeFlag.toUpperCase().trim().equals(tyFlag)) {
						odsdmsSalePhoneCityRate.setSaleAmtRate(b_percent);
						break;
					}
				}
			}
		}
		return list;
		
	}

	@Override
	public void saveOdsdmsSalePhoneCityRate(List<OdsdmsSalePhoneCityRate> odsdmsSalePhoneCityRatelist) {
		for(OdsdmsSalePhoneCityRate odsdmsSalePhoneCityRate:odsdmsSalePhoneCityRatelist){
			odsdmsSalePhoneCityRateMapper.insertSelective(odsdmsSalePhoneCityRate);
		}
		
	}

	@Override
	public List<OdsdmsSalePhoneChnlRate> analysisCHNLRateQtyAtSheet(HSSFSheet hSSFSheet, String date, int start,int end,
			String tyFlag, List<OdsdmsSalePhoneChnlRate> list) {
		for (int rowNum = start; rowNum <= end; rowNum++) {
			HSSFRow xssfRow = hSSFSheet.getRow(rowNum);
			if (xssfRow.getCell(1) == null) {
				continue;
			}
			xssfRow.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String brand = xssfRow.getCell(1).getStringCellValue();
			if (StringUtils.isBlank(brand)) {
				continue;
			}
			OdsdmsSalePhoneChnlRate odsdmsSalePhoneChnlRate=new OdsdmsSalePhoneChnlRate();
			int kpiValMnum = ExcelColumnTools.excelColStrToNum("O", 1);
			int kpiValLymnum = ExcelColumnTools.excelColStrToNum("C", 1);
			int kpiValYnum = ExcelColumnTools.excelColStrToNum("R", 1);
			int kpiValLynum = ExcelColumnTools.excelColStrToNum("Q", 1);
			xssfRow.getCell(kpiValMnum-1).setCellType(Cell.CELL_TYPE_STRING);
			String kpiValM = xssfRow.getCell(kpiValMnum-1).getStringCellValue();
			xssfRow.getCell(kpiValLymnum-1).setCellType(Cell.CELL_TYPE_STRING);
			String kpiValLym = xssfRow.getCell(kpiValLymnum-1).getStringCellValue();
			xssfRow.getCell(kpiValYnum-1).setCellType(Cell.CELL_TYPE_STRING);
			String kpiValY = xssfRow.getCell(kpiValYnum-1).getStringCellValue();
			xssfRow.getCell(kpiValLynum-1).setCellType(Cell.CELL_TYPE_STRING);
			String kpiValLy = xssfRow.getCell(kpiValLynum-1).getStringCellValue();
			odsdmsSalePhoneChnlRate.setChnlTyp(brand);
			odsdmsSalePhoneChnlRate.setDtMonth(new BigDecimal(date));
			odsdmsSalePhoneChnlRate.setKpiTyp(tyFlag);
			odsdmsSalePhoneChnlRate.setKpiValLy(new BigDecimal(kpiValLy));
			odsdmsSalePhoneChnlRate.setKpiValLym(new BigDecimal(kpiValLym));
			odsdmsSalePhoneChnlRate.setKpiValM(new BigDecimal(kpiValM));
			odsdmsSalePhoneChnlRate.setKpiValY(new BigDecimal(kpiValY));
			odsdmsSalePhoneChnlRate.setLoadDt(new Date());
			list.add(odsdmsSalePhoneChnlRate);
		}
		return list;
		
	}

	@Override
	public void saveOdsdmsSalePhoneChnlRate(List<OdsdmsSalePhoneChnlRate> odsdmsSalePhoneChnlRatelist) {
		for(OdsdmsSalePhoneChnlRate odsdmsSalePhoneChnlRate:odsdmsSalePhoneChnlRatelist){
			osdsdmsSalePhoneChnlRateMapper.insertSelective(odsdmsSalePhoneChnlRate);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean savePhoneExcelData(List<OdsdmsSalePhoneOnline> odsdmsSalePhoneOnlineList,
			List<OdsdmsSalePhoneBrank> odsdmsSalePhoneBrankList, List<OdsdmsSalePhoneTotal> odsdmsSalePhoneTotalList,
			List<OdsdmsSalePhoneMarktShare> odsdmsSalePhoneMarktShareList,
			List<OdsdmsSalePhoneCityRate> odsdmsSalePhoneCityRateList,
			List<OdsdmsSalePhoneChnlRate> odsdmsSalePhoneChnlRateList) {
		//挨个list遍历保存
		for(OdsdmsSalePhoneOnline odsdmsSalePhoneOnline:odsdmsSalePhoneOnlineList){
			odsdmsSalePhoneOnlineMapper.insertSelective(odsdmsSalePhoneOnline);
		}
		for(OdsdmsSalePhoneTotal odsdmsSalePhoneTotal:odsdmsSalePhoneTotalList){
			odsdmsSalePhoneTotalMapper.insertSelective(odsdmsSalePhoneTotal);
		}
		for(OdsdmsSalePhoneMarktShare odsdmsSalePhoneMarktShare:odsdmsSalePhoneMarktShareList){
			odsdmsSalePhoneMarktShareMapper.insertSelective(odsdmsSalePhoneMarktShare);
		}
		for(OdsdmsSalePhoneBrank odsdmsSalePhoneBrank:odsdmsSalePhoneBrankList){
			odsdmsSalePhoneBrankMapper.insertSelective(odsdmsSalePhoneBrank);
		}
		for(OdsdmsSalePhoneCityRate odsdmsSalePhoneCityRate:odsdmsSalePhoneCityRateList){
			odsdmsSalePhoneCityRateMapper.insertSelective(odsdmsSalePhoneCityRate);
		}
		for(OdsdmsSalePhoneChnlRate odsdmsSalePhoneChnlRate:odsdmsSalePhoneChnlRateList){
			osdsdmsSalePhoneChnlRateMapper.insertSelective(odsdmsSalePhoneChnlRate);
		}
		return true;
	}

}
