package com.aas.hss.common.util;

/**
 * Deal with Excel column indexToStr and strToIndex
 * @author Stephen.Huang
 * @version 2015-7-8
 * @see http://blog.csdn.net/u010571844
 */
public class ExcelColumnTools {
	
	public static void main(String[] args) {
		/* String colstr = "IV";
	     int colIndex = excelColStrToNum(colstr, colstr.length());
	     System.out.println("'" + colstr + "' column index of " + colIndex);*/
	     System.out.println(excelColIndexToStr(61));
	}
	
	/**
     * Excel column index begin 1
     * @param colStr
     * @param length
     * @return
     */
    public static int excelColStrToNum(String colStr, int length) {
        int num = 0;
        int result = 0;
        for(int i = 0; i < length; i++) {
            char ch = colStr.charAt(length - i - 1);
            num = (int)(ch - 'A' + 1) ;
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }

    /**
     * Excel column index begin 1
     * @param columnIndex
     * @return
     */
    public static String excelColIndexToStr(int columnIndex) {
        if (columnIndex <= 0) {
            return null;
        }
        String columnStr = "";
        columnIndex--;
        do {
            if (columnStr.length() > 0) {
                columnIndex--;
            }
            columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
            columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
        } while (columnIndex > 0);
        return columnStr;
    }

}
