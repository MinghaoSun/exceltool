package com.aas.hss.common.util;

import java.io.File;
import java.io.FilenameFilter;

public class MyFilenameFilter implements FilenameFilter{
	private String type;  
	
	public MyFilenameFilter(String type) {
		this.type = type;
	}

	public boolean accept(File dir, String name) {
		return name.startsWith(type);
	}

}
