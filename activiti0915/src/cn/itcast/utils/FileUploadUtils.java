package cn.itcast.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

/**
 * 文件上传的工具类
 * @author zhaoqx
 *
 */
public class FileUploadUtils {
	/**
	 * 完成将文件上传的临时文件保存到指定的目录中并返回文件的存储路径
	 * @param resource:临时文件
	 * @return
	 */
	public  static String uploadFile(File resource) {
		//根据当前日期动态创建日期的目录结构
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd");//2015/01/05
		File dir = new File("d:/uploadFiles" + sdf.format(date));
		//如果当前目录结构不存在，再创建
		if(!dir.exists()){
			dir.mkdirs();
		}
		//使用UUID作为上传的文件名称
		String fileName = UUID.randomUUID().toString();
		
		//目标文件
		File destFile = new File(dir.getPath() + "/" + fileName);
		try {
			FileUtils.copyFile(resource, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.getPath();
	}
}
