package com.jy.utils;

public class FileUtils {
	public static String getFileType(final String fileName)
	{
		System.out.println(fileName);
		return fileName.substring(fileName.lastIndexOf("."));
	}

}
