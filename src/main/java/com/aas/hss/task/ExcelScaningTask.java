package com.aas.hss.task;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aas.hss.common.util.Config;
import com.aas.hss.common.util.Constants;
import com.aas.hss.common.util.DateUtils;
import com.aas.hss.common.util.FileUtils;
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
	@Scheduled(cron = "0 0 9 * * ?")
	//@Scheduled(cron = "1 * * * * ?")
	public void scaning() {
		logger.info("scaning mobilephoneexcel start...");
		String pathName = Config.get("excelpath");
		File file = new File(pathName);
		if (file.exists()) {
			// 过滤文件
			MyFilenameFilter filter = new MyFilenameFilter(Config.get("filefilter"));
			File[] files = file.listFiles(filter);
			if(files.length==0){
				logger.info("目录({})下没有找到指定规则({})的文件",pathName,Config.get("filefilter"));
			}
			for (File f : files) {
			try {
				logger.info("开始处理文件，{}",f.getName());
				HSSFWorkbook hSSFWorkbook = this.getWorkbook(f);
				String date = this.getFileDate(f);
				//线上量额
				logger.info("处理线上量额...");
				List<OdsdmsSalePhoneOnline> odsdmsSalePhoneOnlinelist=translateExcelService.analysisDzswscpprlfzqs(hSSFWorkbook.getSheetAt(0), date);
				odsdmsSalePhoneOnlinelist=translateExcelService.analysisDzswscppxsefzqs(hSSFWorkbook.getSheetAt(1),date,odsdmsSalePhoneOnlinelist);
				//省份品牌量额全部范围
				logger.info("处理省份品牌量额-全部...");
				List<OdsdmsSalePhoneBrank> odsdmsSalePhoneBranklist_TOTAL=translateExcelService.analysisProvinceBrandQtyAtSheet3(hSSFWorkbook.getSheetAt(2),date,Constants.ATTR_CLSS_TOTAL,"AJ","IV");
				odsdmsSalePhoneBranklist_TOTAL=translateExcelService.analysisProviceBrandAmtAtSheet4(hSSFWorkbook.getSheetAt(3),date,odsdmsSalePhoneBranklist_TOTAL,"AJ","IV");
				//行业价格全部范围
				logger.info("处理行业价格范围-全部...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_TOTAL_HYJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(2), date, Constants.ATTR_CLSS_TOTAL_HYJJ,"G","O");
				OdsdmsSalePhoneTotallist_TOTAL_HYJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(3), date, Constants.ATTR_CLSS_TOTAL_HYJJ,"G","O",OdsdmsSalePhoneTotallist_TOTAL_HYJJ);
				//海信价格全部范围
				logger.info("处理海信价格范围-全部...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_TOTAL_HXJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(2), date, Constants.ATTR_CLSS_TOTAL_HXJJ,"P","X");
				OdsdmsSalePhoneTotallist_TOTAL_HXJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(3), date, Constants.ATTR_CLSS_TOTAL_HXJJ,"P","X",OdsdmsSalePhoneTotallist_TOTAL_HXJJ);
				//行业尺寸全部范围
				logger.info("处理行业尺寸范围-全部...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_TOTAL_HYCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(2), date, Constants.ATTR_CLSS_TOTAL_HYCC,"Y","AC");
				OdsdmsSalePhoneTotallist_TOTAL_HYCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(3), date, Constants.ATTR_CLSS_TOTAL_HYCC,"Y","AC",OdsdmsSalePhoneTotallist_TOTAL_HYCC);
				//海信尺寸全部范围
				logger.info("处理海信尺寸范围-全部...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_TOTAL_HXCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(2), date, Constants.ATTR_CLSS_TOTAL_HXCC,"AD","AH");
				OdsdmsSalePhoneTotallist_TOTAL_HXCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(3), date, Constants.ATTR_CLSS_TOTAL_HXCC,"AD","AH",OdsdmsSalePhoneTotallist_TOTAL_HXCC);
				//省份品牌量额4G范围
				logger.info("处理省份品牌量额-4G...");
				List<OdsdmsSalePhoneBrank> odsdmsSalePhoneBranklist_4G=translateExcelService.analysisProvinceBrandQtyAtSheet3(hSSFWorkbook.getSheetAt(4),date,Constants.ATTR_CLSS_4G,"AJ","IN");
				odsdmsSalePhoneBranklist_4G=translateExcelService.analysisProviceBrandAmtAtSheet4(hSSFWorkbook.getSheetAt(10),date,odsdmsSalePhoneBranklist_4G,"AJ","IN");
				//行业价格4G范围
				logger.info("处理行业价格范围-4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_4G_HYJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(4), date, Constants.ATTR_CLSS_4G_HYJJ,"G","O");
				OdsdmsSalePhoneTotallist_4G_HYJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(10), date, Constants.ATTR_CLSS_4G_HYJJ,"G","O",OdsdmsSalePhoneTotallist_4G_HYJJ);
				//海信价格4G范围
				logger.info("处理海信价格范围-4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_4G_HXJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(4), date, Constants.ATTR_CLSS_4G_HXJJ,"P","X");
				OdsdmsSalePhoneTotallist_4G_HXJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(10), date, Constants.ATTR_CLSS_4G_HXJJ,"P","X",OdsdmsSalePhoneTotallist_4G_HXJJ);
				//行业尺寸4G范围
				logger.info("处理行业尺寸范围-4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_4G_HYCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(4), date, Constants.ATTR_CLSS_4G_HYCC,"Y","AC");
				OdsdmsSalePhoneTotallist_4G_HYCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(10), date, Constants.ATTR_CLSS_4G_HYCC,"Y","AC",OdsdmsSalePhoneTotallist_4G_HYCC);
				//海信尺寸4G范围
				logger.info("处理海信尺寸范围-4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_4G_HXCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(4), date, Constants.ATTR_CLSS_4G_HXCC,"AD","AH");
				OdsdmsSalePhoneTotallist_4G_HXCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(10), date, Constants.ATTR_CLSS_4G_HXCC,"AD","AH",OdsdmsSalePhoneTotallist_4G_HXCC);
				//省份品牌量额CT4G范围
				logger.info("处理省份品牌量额-CT4G...");
				List<OdsdmsSalePhoneBrank> odsdmsSalePhoneBranklist_CT4G=translateExcelService.analysisProvinceBrandQtyAtSheet3(hSSFWorkbook.getSheetAt(5),date,Constants.ATTR_CLSS_CT4G,"AJ","CA");
				odsdmsSalePhoneBranklist_CT4G=translateExcelService.analysisProviceBrandAmtAtSheet4(hSSFWorkbook.getSheetAt(11),date,odsdmsSalePhoneBranklist_CT4G,"AJ","CA");
				//行业价格CT4G范围
				logger.info("处理行业价格范围-CT4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_CT4G_HYJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(5), date, Constants.ATTR_CLSS_CT4G_HYJJ,"G","O");
				OdsdmsSalePhoneTotallist_CT4G_HYJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(11), date, Constants.ATTR_CLSS_CT4G_HYJJ,"G","O",OdsdmsSalePhoneTotallist_CT4G_HYJJ);
				//海信价格CT4G范围
				logger.info("处理海信价格范围-CT4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_CT4G_HXJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(5), date, Constants.ATTR_CLSS_CT4G_HXJJ,"P","X");
				OdsdmsSalePhoneTotallist_CT4G_HXJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(11), date, Constants.ATTR_CLSS_CT4G_HXJJ,"P","X",OdsdmsSalePhoneTotallist_CT4G_HXJJ);
				//行业尺寸CT4G范围
				logger.info("处理行业尺寸范围-CT4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_CT4G_HYCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(5), date, Constants.ATTR_CLSS_CT4G_HYCC,"Y","AC");
				OdsdmsSalePhoneTotallist_CT4G_HYCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(11), date, Constants.ATTR_CLSS_CT4G_HYCC,"Y","AC",OdsdmsSalePhoneTotallist_CT4G_HYCC);
				//海信尺寸CT4G范围
				logger.info("处理海信尺寸范围-CT4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_CT4G_HXCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(5), date, Constants.ATTR_CLSS_CT4G_HXCC,"AD","AH");
				OdsdmsSalePhoneTotallist_CT4G_HXCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(11), date, Constants.ATTR_CLSS_CT4G_HXCC,"AD","AH",OdsdmsSalePhoneTotallist_CT4G_HXCC);
				//省份品牌量额CMCC4G范围
				logger.info("处理省份品牌量额-CMCC4G...");
				List<OdsdmsSalePhoneBrank> odsdmsSalePhoneBranklist_CMCC4G=translateExcelService.analysisProvinceBrandQtyAtSheet3(hSSFWorkbook.getSheetAt(6),date,Constants.ATTR_CLSS_CMCC4G,"AJ","GX");
				odsdmsSalePhoneBranklist_CMCC4G=translateExcelService.analysisProviceBrandAmtAtSheet4(hSSFWorkbook.getSheetAt(12),date,odsdmsSalePhoneBranklist_CMCC4G,"AJ","GX");
				//行业价格CMCC4G范围
				logger.info("处理行业价格范围-CMCC4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_CMCC4G_HYJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(6), date, Constants.ATTR_CLSS_CMCC4G_HYJJ,"G","O");
				OdsdmsSalePhoneTotallist_CMCC4G_HYJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(12), date, Constants.ATTR_CLSS_CMCC4G_HYJJ,"G","O",OdsdmsSalePhoneTotallist_CMCC4G_HYJJ);
				//海信价格CMCC4G范围
				logger.info("处理海信价格范围-CMCC4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_CMCC4G_HXJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(6), date, Constants.ATTR_CLSS_CMCC4G_HXJJ,"P","X");
				OdsdmsSalePhoneTotallist_CMCC4G_HXJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(12), date, Constants.ATTR_CLSS_CMCC4G_HXJJ,"P","X",OdsdmsSalePhoneTotallist_CMCC4G_HXJJ);
				//行业尺寸CMCC4G范围
				logger.info("处理行业尺寸范围-CMCC4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_CMCC4G_HYCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(6), date, Constants.ATTR_CLSS_CMCC4G_HYCC,"Y","AC");
				OdsdmsSalePhoneTotallist_CMCC4G_HYCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(12), date, Constants.ATTR_CLSS_CMCC4G_HYCC,"Y","AC",OdsdmsSalePhoneTotallist_CMCC4G_HYCC);
				//海信尺寸CMCC4G范围
				logger.info("处理海信尺寸范围-CMCC4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_CMCC4G_HXCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(6), date, Constants.ATTR_CLSS_CMCC4G_HXCC,"AD","AH");
				OdsdmsSalePhoneTotallist_CMCC4G_HXCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(12), date, Constants.ATTR_CLSS_CMCC4G_HXCC,"AD","AH",OdsdmsSalePhoneTotallist_CMCC4G_HXCC);
				//省份品牌量额CU4G范围
				logger.info("处理省份品牌量额-CU4G...");
				List<OdsdmsSalePhoneBrank> odsdmsSalePhoneBranklist_CU4G=translateExcelService.analysisProvinceBrandQtyAtSheet3(hSSFWorkbook.getSheetAt(7),date,Constants.ATTR_CLSS_CU4G,"AJ","BL");
				odsdmsSalePhoneBranklist_CU4G=translateExcelService.analysisProviceBrandAmtAtSheet4(hSSFWorkbook.getSheetAt(13),date,odsdmsSalePhoneBranklist_CU4G,"AJ","BL");
				//行业价格CU4G范围
				logger.info("处理行业价格范围-CU4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_CU4G_HYJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(7), date, Constants.ATTR_CLSS_CU4G_HYJJ,"G","O");
				OdsdmsSalePhoneTotallist_CU4G_HYJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(13), date, Constants.ATTR_CLSS_CU4G_HYJJ,"G","O",OdsdmsSalePhoneTotallist_CU4G_HYJJ);
				//海信价格CU4G范围
				logger.info("处理海信价格范围-CU4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_CU4G_HXJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(7), date, Constants.ATTR_CLSS_CU4G_HXJJ,"P","X");
				OdsdmsSalePhoneTotallist_CU4G_HXJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(13), date, Constants.ATTR_CLSS_CU4G_HXJJ,"P","X",OdsdmsSalePhoneTotallist_CU4G_HXJJ);
				//行业尺寸CU4G范围
				logger.info("处理行业尺寸范围-CU4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_CU4G_HYCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(7), date, Constants.ATTR_CLSS_CU4G_HYCC,"Y","AC");
				OdsdmsSalePhoneTotallist_CU4G_HYCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(13), date, Constants.ATTR_CLSS_CU4G_HYCC,"Y","AC",OdsdmsSalePhoneTotallist_CU4G_HYCC);
				//海信尺寸CU4G范围
				logger.info("处理海信尺寸范围-CU4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_CU4G_HXCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(7), date, Constants.ATTR_CLSS_CU4G_HXCC,"AD","AH");
				OdsdmsSalePhoneTotallist_CU4G_HXCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(13), date, Constants.ATTR_CLSS_CU4G_HXCC,"AD","AH",OdsdmsSalePhoneTotallist_CU4G_HXCC);
				//省份品牌量额DOUBLE4G范围
				logger.info("处理省份品牌量额-DOUBLE4G...");
				List<OdsdmsSalePhoneBrank> odsdmsSalePhoneBranklist_DOUBLE4G=translateExcelService.analysisProvinceBrandQtyAtSheet3(hSSFWorkbook.getSheetAt(8),date,Constants.ATTR_CLSS_DOUBLE4G,"AJ","CZ");
				odsdmsSalePhoneBranklist_DOUBLE4G=translateExcelService.analysisProviceBrandAmtAtSheet4(hSSFWorkbook.getSheetAt(14),date,odsdmsSalePhoneBranklist_DOUBLE4G,"AJ","CZ");
				//行业价格DOUBLE4G范围
				logger.info("处理行业价格范围-DOUBLE4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_DOUBLE4G_HYJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(8), date, Constants.ATTR_CLSS_DOUBLE4G_HYJJ,"G","O");
				OdsdmsSalePhoneTotallist_DOUBLE4G_HYJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(14), date, Constants.ATTR_CLSS_DOUBLE4G_HYJJ,"G","O",OdsdmsSalePhoneTotallist_DOUBLE4G_HYJJ);
				//海信价格DOUBLE4G范围
				logger.info("处理海信价格范围-DOUBLE4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_DOUBLE4G_HXJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(8), date, Constants.ATTR_CLSS_DOUBLE4G_HXJJ,"P","X");
				OdsdmsSalePhoneTotallist_DOUBLE4G_HXJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(14), date, Constants.ATTR_CLSS_DOUBLE4G_HXJJ,"P","X",OdsdmsSalePhoneTotallist_DOUBLE4G_HXJJ);
				//行业尺寸DOUBLE4G范围
				logger.info("处理行业尺寸范围-DOUBLE4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_DOUBLE4G_HYCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(8), date, Constants.ATTR_CLSS_DOUBLE4G_HYCC,"Y","AC");
				OdsdmsSalePhoneTotallist_DOUBLE4G_HYCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(14), date, Constants.ATTR_CLSS_DOUBLE4G_HYCC,"Y","AC",OdsdmsSalePhoneTotallist_DOUBLE4G_HYCC);
				//海信尺寸DOUBLE4G范围
				logger.info("处理海信尺寸范围-DOUBLE4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_DOUBLE4G_HXCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(8), date, Constants.ATTR_CLSS_DOUBLE4G_HXCC,"AD","AH");
				OdsdmsSalePhoneTotallist_DOUBLE4G_HXCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(14), date, Constants.ATTR_CLSS_DOUBLE4G_HXCC,"AD","AH",OdsdmsSalePhoneTotallist_DOUBLE4G_HXCC);
				//省份品牌量额TRIPLE4G范围
				logger.info("处理省份品牌量额-TRIPLE4G...");
				List<OdsdmsSalePhoneBrank> odsdmsSalePhoneBranklist_TRIPLE4G=translateExcelService.analysisProvinceBrandQtyAtSheet3(hSSFWorkbook.getSheetAt(9),date,Constants.ATTR_CLSS_TRIPLE4G,"AJ","CZ");
				odsdmsSalePhoneBranklist_TRIPLE4G=translateExcelService.analysisProviceBrandAmtAtSheet4(hSSFWorkbook.getSheetAt(15),date,odsdmsSalePhoneBranklist_TRIPLE4G,"AJ","CZ");
				//行业价格TRIPLE4G范围
				logger.info("处理行业价格范围-TRIPLE4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_TRIPLE4G_HYJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(9), date, Constants.ATTR_CLSS_TRIPLE4G_HYJJ,"G","O");
				OdsdmsSalePhoneTotallist_TRIPLE4G_HYJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(15), date, Constants.ATTR_CLSS_TRIPLE4G_HYJJ,"G","O",OdsdmsSalePhoneTotallist_TRIPLE4G_HYJJ);
				//海信价格TRIPLE4G范围
				logger.info("处理海信价格范围-TRIPLE4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_TRIPLE4G_HXJJ=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(9), date, Constants.ATTR_CLSS_TRIPLE4G_HXJJ,"P","X");
				OdsdmsSalePhoneTotallist_TRIPLE4G_HXJJ=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(15), date, Constants.ATTR_CLSS_TRIPLE4G_HXJJ,"P","X",OdsdmsSalePhoneTotallist_TRIPLE4G_HXJJ);
				//行业尺寸TRIPLE4G范围
				logger.info("处理行业尺寸范围-TRIPLE4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_TRIPLE4G_HYCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(9), date, Constants.ATTR_CLSS_TRIPLE4G_HYCC,"Y","AC");
				OdsdmsSalePhoneTotallist_TRIPLE4G_HYCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(15), date, Constants.ATTR_CLSS_TRIPLE4G_HYCC,"Y","AC",OdsdmsSalePhoneTotallist_TRIPLE4G_HYCC);
				//海信尺寸TRIPLE4G范围
				logger.info("处理海信尺寸范围-TRIPLE4G...");
				List<OdsdmsSalePhoneTotal> OdsdmsSalePhoneTotallist_TRIPLE4G_HXCC=translateExcelService.analysisProvicePhonePropertyQtyAtSheet(hSSFWorkbook.getSheetAt(9), date, Constants.ATTR_CLSS_TRIPLE4G_HXCC,"AD","AH");
				OdsdmsSalePhoneTotallist_TRIPLE4G_HXCC=translateExcelService.analysisProvicePhonePropertyAmtAtSheet(hSSFWorkbook.getSheetAt(15), date, Constants.ATTR_CLSS_TRIPLE4G_HXCC,"AD","AH",OdsdmsSalePhoneTotallist_TRIPLE4G_HXCC);
				
				List<OdsdmsSalePhoneTotal> odsdmsSalePhoneTotallist=new ArrayList<OdsdmsSalePhoneTotal>();
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_TOTAL_HYJJ);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_TOTAL_HXJJ);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_TOTAL_HYCC);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_TOTAL_HXCC);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_4G_HYJJ);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_4G_HXJJ);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_4G_HYCC);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_4G_HXCC);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_CU4G_HYJJ);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_CU4G_HXJJ);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_CU4G_HYCC);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_CU4G_HXCC);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_CT4G_HYJJ);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_CT4G_HXJJ);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_CT4G_HYCC);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_CT4G_HXCC);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_CMCC4G_HYJJ);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_CMCC4G_HXJJ);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_CMCC4G_HYCC);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_CMCC4G_HXCC);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_DOUBLE4G_HYJJ);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_DOUBLE4G_HXJJ);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_DOUBLE4G_HYCC);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_DOUBLE4G_HXCC);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_TRIPLE4G_HYJJ);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_TRIPLE4G_HXJJ);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_TRIPLE4G_HYCC);
				odsdmsSalePhoneTotallist.addAll(OdsdmsSalePhoneTotallist_TRIPLE4G_HXCC);
				
				List<OdsdmsSalePhoneBrank> odsdmsSalePhoneBranklist=new ArrayList<OdsdmsSalePhoneBrank>();
				odsdmsSalePhoneBranklist.addAll(odsdmsSalePhoneBranklist_TOTAL);
				odsdmsSalePhoneBranklist.addAll(odsdmsSalePhoneBranklist_4G);
				odsdmsSalePhoneBranklist.addAll(odsdmsSalePhoneBranklist_CMCC4G);
				odsdmsSalePhoneBranklist.addAll(odsdmsSalePhoneBranklist_CU4G);
				odsdmsSalePhoneBranklist.addAll(odsdmsSalePhoneBranklist_CT4G);
				odsdmsSalePhoneBranklist.addAll(odsdmsSalePhoneBranklist_DOUBLE4G);
				odsdmsSalePhoneBranklist.addAll(odsdmsSalePhoneBranklist_TRIPLE4G);
				//市场份额当期量
				logger.info("处理市场份额...");
				List<OdsdmsSalePhoneMarktShare> odsdmsSalePhoneMarktSharelist=translateExcelService.analysisCityBrandQtyAtSheet(hSSFWorkbook.getSheetAt(16),date,"C","I",Constants.MARKT_SHARE_TYFLAG_DQ,new ArrayList<OdsdmsSalePhoneMarktShare>());
				odsdmsSalePhoneMarktSharelist=translateExcelService.analysisCityBrandAmtAtSheet(hSSFWorkbook.getSheetAt(17), date, "C", "I",Constants.MARKT_SHARE_TYFLAG_DQ , odsdmsSalePhoneMarktSharelist);
				odsdmsSalePhoneMarktSharelist=translateExcelService.analysisCityBrandQtyAtSheet(hSSFWorkbook.getSheetAt(16),date,"J","P",Constants.MARKT_SHARE_TYFLAG_LJ,odsdmsSalePhoneMarktSharelist);
				odsdmsSalePhoneMarktSharelist=translateExcelService.analysisCityBrandAmtAtSheet(hSSFWorkbook.getSheetAt(17), date, "J", "P",Constants.MARKT_SHARE_TYFLAG_LJ , odsdmsSalePhoneMarktSharelist);
				//占有率
				logger.info("处理占有率...");
				List<OdsdmsSalePhoneCityRate> odsdmsSalePhoneCityRatelist=translateExcelService.analysisCityRateQtyAtSheet(hSSFWorkbook.getSheetAt(18), date, "U", "Z","T", Constants.MARKT_RATE_TYFLAG_DQ, new ArrayList<OdsdmsSalePhoneCityRate>());
				odsdmsSalePhoneCityRatelist=translateExcelService.analysisCityRateAmtAtSheet(hSSFWorkbook.getSheetAt(18), date, "AI", "AN", "AH",Constants.MARKT_RATE_TYFLAG_DQ, odsdmsSalePhoneCityRatelist);
				odsdmsSalePhoneCityRatelist=translateExcelService.analysisCityRateQtyAtSheet(hSSFWorkbook.getSheetAt(18), date, "AA", "AF","T", Constants.MARKT_RATE_TYFLAG_LJ,odsdmsSalePhoneCityRatelist);
				odsdmsSalePhoneCityRatelist=translateExcelService.analysisCityRateAmtAtSheet(hSSFWorkbook.getSheetAt(18), date, "AO", "AT","AH", Constants.MARKT_RATE_TYFLAG_LJ, odsdmsSalePhoneCityRatelist);
				//各业态销量比重
				logger.info("处理各业态销量比重...");
				List<OdsdmsSalePhoneChnlRate> odsdmsSalePhoneChnlRatelist=translateExcelService.analysisCHNLRateQtyAtSheet(hSSFWorkbook.getSheetAt(18),date,3,7,Constants.CHNL_RATE_TYFLAG_XL,new ArrayList<OdsdmsSalePhoneChnlRate>());
				odsdmsSalePhoneChnlRatelist=translateExcelService.analysisCHNLRateQtyAtSheet(hSSFWorkbook.getSheetAt(18),date,12,16,Constants.CHNL_RATE_TYFLAG_XSE,odsdmsSalePhoneChnlRatelist);
				//整理完数据统一保存
				//translateExcelService.savePhoneExcelData(odsdmsSalePhoneOnlinelist, odsdmsSalePhoneBranklist, odsdmsSalePhoneTotallist, odsdmsSalePhoneMarktSharelist, odsdmsSalePhoneCityRatelist, odsdmsSalePhoneChnlRatelist);
				logger.info("处理文件结束，{}",f.getName());
				//操作成功移动到success文件夹里面
				String dirFile = Config.get("excelpath")+"\\Success";
				File destFile=new File(dirFile+"\\"+f.getName());
				Files.move(f.toPath(),destFile.toPath());
				logger.info("移动文件{}到Success目录成功",f.getName());
			}catch (FileSystemException e) {
				logger.error("移动excel文件{}发生异常！"+f.getName());
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("处理excel文件{}发生异常！"+f.getName());
				e.printStackTrace();
			}
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
