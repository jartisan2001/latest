package com.github.jartisan.latest.global.utils;

/***
 *     单位可读性转换
 * @author jalen
 *
 */
public class UnitUtil {
	
	 /**
     * Method getSize
     *
     * @param long num
     * @return object of class java.lang.String
     */
    public static String getSize(long num) {
        int flooring, numlength;
        String[] unitname = {"B", "kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
        numlength = String.valueOf(num).length();
        flooring = (int) Math.floor((numlength - 1) / (double)3);
        String size = unitname[flooring];
        java.text.DecimalFormat dfmt = new java.text.DecimalFormat("###.## " + size);
        return dfmt.format((double) num / Math.pow(1024, flooring));
    }
	
	public static void main(String[] args) {
		System.out.println(getSize(65456492));
		System.out.println(getSize(65456492899L));
		System.out.println(getSize(6545649289965L));
	} 
}
