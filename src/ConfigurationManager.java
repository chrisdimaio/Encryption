package com;

import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.lang.Integer;

public class ConfigurationManager
{
	public static final boolean DISPLAY_ALL_RECENT_FILES = true;

	private static final String RECENT_FILE_COUNT = "recentFileCount";
	private static final String CONFIG_FILE = "config.cfg";

	public static void persistRecentFiles(ArrayList<String> recentFileList)
	{
		JSONObject jo = new JSONObject();
		try
		{
			int recentFileCount = recentFileList.size();
			jo.put(RECENT_FILE_COUNT, String.valueOf(recentFileCount));
			for(int i = 0;i < recentFileCount;i++)
			{
				jo.put(String.valueOf(i), recentFileList.get(i));
			}
			Utils.writeJSON(CONFIG_FILE, jo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println(jo.toString());
	}
	
	public static ArrayList<String> loadRecentFiles()
		throws FileNotFoundException{
		JSONObject jo = Utils.readJSON(CONFIG_FILE);
		ArrayList files = new ArrayList<String>();
		try
		{
			int count = new Integer((String)jo.get(RECENT_FILE_COUNT)).intValue();
			for(int i = 0;i < count;i++)
			{
				files.add(jo.get(String.valueOf(i)));
			}
			//System.out.println(files.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return files;
	}
}