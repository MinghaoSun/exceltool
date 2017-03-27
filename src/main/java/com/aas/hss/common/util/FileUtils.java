package com.aas.hss.common.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @description 文件夹处理类
 * @author Minghao
 * @date 2017年3月27日16:34:12
 */
public class FileUtils {
	
	private static Logger log = Logger.getLogger(FileUtils.class);
	
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        Boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
	/**
	 * 删除文件
	 * 
	 * @param filePaths
	 *            文件路径
	 * @return 操作成功：true 操作失败：false
	 */
	public static void deleteFile(List<String> filePaths) {
		for (String filePath : filePaths) {
			File file = new File(filePath);
			if (!file.exists()) {
				log.info("待删除的文件不存在，文件路径为：" + filePath);
			} else {
				if (file.isFile()) {
					deleteFile(filePath);
				} else {
					deleteDirectory(filePath);
				}
			}
		}
	}
	
	/**
	 * 删除单个文件夹下的文件,保留该文件夹
	 * 
	 * @param filePath
	 *            待删除的文件夹
	 */
	public static void deleteDirectory(String filePath) {
		// 如果文件路径不以文件分隔符结尾，自动添加文件分隔符
		if (!filePath.endsWith(File.separator)) {
			filePath += File.separator;
		}
		
		File dirFile = new File(filePath);
		
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			log.error("删除目录失败"+filePath+"目录不存在!");
		}
		
		// 删除文件夹下的所有文件（包括子目录）
		File[] files = dirFile.listFiles();
		for (File file: files) {
			if (file.isFile()) {
				deleteFile(file.getAbsolutePath());
			} else {
				deleteDirectory(file.getAbsolutePath());
			}
		}
		
		// 删除该文件夹
//		dirFile.delete();
	}
	
	public static void outputFile(ByteArrayInputStream excelStream, String fileName, String dirFile) {
		File dir = new File(dirFile);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dirFile + "/" + fileName);
		OutputStream outPut = null;
		try {
			outPut = new FileOutputStream(file);
			byte[] buffer = new byte[1];
			while (excelStream.read(buffer) != -1) {
				outPut.write(buffer);
			}
			outPut.flush();
		} catch (FileNotFoundException e) {
			log.error("创建路径失败");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("文件读写错误1");
			e.printStackTrace();
		} finally {
			try {
				outPut.close();
			} catch (IOException e) {
				log.error("文件读写错误2");
				e.printStackTrace();
			}
		}
	}
}
