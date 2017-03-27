package com.aas.hss.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aas.hss.common.util.Config;
import com.aas.hss.common.util.Constants;
import com.aas.hss.common.util.MyFilenameFilter;
import com.aas.hss.entity.OdsdmsSalePhoneBrank;
import com.aas.hss.entity.OdsdmsSalePhoneChnlRate;
import com.aas.hss.entity.OdsdmsSalePhoneCityRate;
import com.aas.hss.entity.OdsdmsSalePhoneMarktShare;
import com.aas.hss.entity.OdsdmsSalePhoneOnline;
import com.aas.hss.entity.OdsdmsSalePhoneTotal;
import com.aas.hss.service.impl.TranslateExcelService;

/**
 * @description 海信手机数据（excel）采集Task类
 * @author Minghao
 * @date 2017年3月24日09:07:13
 */
@Component
public class ExcelScaningTask {

	private static final Logger logger = LoggerFactory.getLogger(ExcelScaningTask.class);

	@Autowired
	private TranslateExcelService translateExcelService;

	//定时任务每天9点执行一次
	//@Scheduled(cron = "0 0 9 * * ?")
	//@Scheduled(cron = "1 * * * * ?")
	@Transactional(propagation = Propagation.REQUIRED)
	public void scaning() {
		logger.info("scaning mobilephoneexcel start...");
		String pathName = Config.get("excelpath");
		File file = new File(pathName);
		if (file.exists()) {
			// 过滤文件
			MyFilenameFilter filter = new MyFilenameFilter(Config.get("filefilter"));
			File[] files = file.listFiles(filter);
			for (File f : files) {
				logger.info("开始处理文件，{}",f.getName());
				HSSFWorkbook hSSFWorkbook = this.getWorkbook(f);
				String date = this.getFileDate(f);
				//线上量额
				/*logger.info("处理线上量额...");
				List<OdsdmsSalePhoneOnline> odsdmsSalePhoneOnlinelist=translateExcelService.analysisDzswscpprlfzqs(hSSFWorkbook.getSheetAt(0), date);
				odsdmsSalePhoneOnlinelist=translateExcelService.analysisDzswscppxsefzqs(hSSFWorkbook.getSheetAt(1),date,odsdmsSalePhoneOnlinelist);
				translateExcelService.saveOdsdmsSalePhoneOnline(odsdmsSalePhoneOnlinelist);
				//省份品牌量额
				logger.info("处理省份品牌量额...");
				List<OdsdmsSalePhoneBrank> odsdmsSalePhoneBranklist=translateExcelService.analysisProvinceBrandQtyAtSheet3(hSSFWorkbook.getSheetAt(2),date,Constants.ATTR_CLSS_TOTAL);
				odsdmsSalePhoneBranklist=translateExcelService.analysisProviceBrandAmtAtSheet4(hSSFWorkbook.getSheetAt(3),date,odsdmsSalePhoneBranklist);
				translateExcelService.saveOdsdmsSalePhoneBrank(odsdmsSalePhoneBranklist);
				//行业价格全部范围
				logger.info("处理行业价格范围...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_TOTAL_HYJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(2), date, Constants.ATTR_CLSS_TOTAL_HYJJ,"G","O");
				OdsdmsSalePhoneTotallist_TOTAL_HYJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(3), date, Constants.ATTR_CLSS_TOTAL_HYJJ,"G","O",OdsdmsSalePhoneTotallist_TOTAL_HYJJ);
				translateExcelService.saveOdsdmsSalePhoneTotal(OdsdmsSalePhoneTotallist_TOTAL_HYJJ);
				//海信价格全部范围
				logger.info("处理海信价格范围...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_TOTAL_HXJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(2), date, Constants.ATTR_CLSS_TOTAL_HXJJ,"P","X");
				OdsdmsSalePhoneTotallist_TOTAL_HXJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(3), date, Constants.ATTR_CLSS_TOTAL_HXJJ,"P","X",OdsdmsSalePhoneTotallist_TOTAL_HXJJ);
				translateExcelService.saveOdsdmsSalePhoneTotal(OdsdmsSalePhoneTotallist_TOTAL_HXJJ);
				//行业尺寸全部范围
				logger.info("处理行业尺寸范围...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_TOTAL_HYCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(2), date, Constants.ATTR_CLSS_TOTAL_HYCC,"Y","AC");
				OdsdmsSalePhoneTotallist_TOTAL_HYCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(3), date, Constants.ATTR_CLSS_TOTAL_HYCC,"Y","AC",OdsdmsSalePhoneTotallist_TOTAL_HYCC);
				translateExcelService.saveOdsdmsSalePhoneTotal(OdsdmsSalePhoneTotallist_TOTAL_HYCC);
				//海信尺寸全部范围
				logger.info("处理海信尺寸范围...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_TOTAL_HXCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(2), date, Constants.ATTR_CLSS_TOTAL_HXCC,"AD","AH");
				OdsdmsSalePhoneTotallist_TOTAL_HXCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(3), date, Constants.ATTR_CLSS_TOTAL_HXCC,"G","O",OdsdmsSalePhoneTotallist_TOTAL_HXCC);
				translateExcelService.saveOdsdmsSalePhoneTotal(OdsdmsSalePhoneTotallist_TOTAL_HXCC);
				//市场份额当期量
				logger.info("处理市场份额...");
				List<OdsdmsSalePhoneMarktShare> odsdmsSalePhoneMarktSharelist=translateExcelService.analysisCityBrandQtyAtSheet(hSSFWorkbook.getSheetAt(16),date,"C","I",Constants.MARKT_SHARE_TYFLAG_DQ,new ArrayList<OdsdmsSalePhoneMarktShare>());
				odsdmsSalePhoneMarktSharelist=translateExcelService.analysisCityBrandAmtAtSheet(hSSFWorkbook.getSheetAt(17), date, "C", "I",Constants.MARKT_SHARE_TYFLAG_DQ , odsdmsSalePhoneMarktSharelist);
				odsdmsSalePhoneMarktSharelist=translateExcelService.analysisCityBrandQtyAtSheet(hSSFWorkbook.getSheetAt(16),date,"J","P",Constants.MARKT_SHARE_TYFLAG_LJ,odsdmsSalePhoneMarktSharelist);
				odsdmsSalePhoneMarktSharelist=translateExcelService.analysisCityBrandAmtAtSheet(hSSFWorkbook.getSheetAt(17), date, "J", "P",Constants.MARKT_SHARE_TYFLAG_LJ , odsdmsSalePhoneMarktSharelist);
				translateExcelService.saveOdsdmsSalePhoneMarktShare(odsdmsSalePhoneMarktSharelist);
				//占有率
				logger.info("处理占有率...");
				List<OdsdmsSalePhoneCityRate> odsdmsSalePhoneCityRatelist=translateExcelService.analysisCityRateQtyAtSheet(hSSFWorkbook.getSheetAt(18), date, "U", "Z","T", Constants.MARKT_RATE_TYFLAG_DQ, new ArrayList<OdsdmsSalePhoneCityRate>());
				odsdmsSalePhoneCityRatelist=translateExcelService.analysisCityRateAmtAtSheet(hSSFWorkbook.getSheetAt(18), date, "AI", "AN", "AH",Constants.MARKT_RATE_TYFLAG_DQ, odsdmsSalePhoneCityRatelist);
				odsdmsSalePhoneCityRatelist=translateExcelService.analysisCityRateQtyAtSheet(hSSFWorkbook.getSheetAt(18), date, "AA", "AF","T", Constants.MARKT_RATE_TYFLAG_LJ,odsdmsSalePhoneCityRatelist);
				odsdmsSalePhoneCityRatelist=translateExcelService.analysisCityRateAmtAtSheet(hSSFWorkbook.getSheetAt(18), date, "AO", "AT","AH", Constants.MARKT_RATE_TYFLAG_LJ, odsdmsSalePhoneCityRatelist);
				translateExcelService.saveOdsdmsSalePhoneCityRate(odsdmsSalePhoneCityRatelist);
				//各业态销量比重
*/				logger.info("处理各业态销量比重...");
				List<OdsdmsSalePhoneChnlRate> odsdmsSalePhoneChnlRatelist=translateExcelService.analysisCHNLRateQtyAtSheet(hSSFWorkbook.getSheetAt(18),date,3,7,Constants.CHNL_RATE_TYFLAG_XL,new ArrayList<OdsdmsSalePhoneChnlRate>());
				odsdmsSalePhoneChnlRatelist=translateExcelService.analysisCHNLRateQtyAtSheet(hSSFWorkbook.getSheetAt(18),date,12,16,Constants.CHNL_RATE_TYFLAG_XSE,odsdmsSalePhoneChnlRatelist);
				translateExcelService.saveOdsdmsSalePhoneChnlRate(odsdmsSalePhoneChnlRatelist);
				logger.info("处理文件结束，{}",f.getName());
				//移动到success文件夹里面
			}
		} else {
			logger.error("没有找到指定路径：" + pathName);
		}
	}
	
	private HSSFWorkbook getWorkbook(File f) {
		HSSFWorkbook xssfWorkbook = null;
		;
		try {
			InputStream is = new FileInputStream(f);
			xssfWorkbook = new HSSFWorkbook(is);
		} catch (Exception e) {
			logger.error("读取excel发生错误" + f.getAbsolutePath());
			e.printStackTrace();
		}
		return xssfWorkbook;

	}

	private String getFileDate(File f) {
		String filename = f.getName();
		filename = filename.substring(0, filename.length() - 4);
		String date = filename.substring(filename.length() - 6, filename.length());
		return date;
	}

}
