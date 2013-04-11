package org.nacao.searchapp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class FileUtil {
	
	public static File[] getLogFiles(String path){
		File file = new File(path);
		if(file.isDirectory()){
			return file.listFiles();
		}
		return null;
	}
	

	public static List<String> showFiles(String path){
		List<String> loginfo = new ArrayList<String>();
		File file = new File(path);
		BufferedReader br=null;
		try{
			if(file.isFile()){
				br = new BufferedReader(new FileReader(file));
				String lines = null;
				while((lines=br.readLine())!=null){
					loginfo.add(lines);
				}
				br.close();
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.e("FileUtil", e.getMessage());
		}
		return loginfo;
	}
}
