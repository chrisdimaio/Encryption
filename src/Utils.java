package com;

import org.json.JSONObject;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileNotFoundException;

public class Utils
{
	public static void writeJSON(String fileName, JSONObject o)
	{
		try
		{	
			OutputStream output = new BufferedOutputStream(new FileOutputStream(fileName));
			output.write(o.toString().getBytes());
			output.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static JSONObject readJSON(String fileName)
		throws FileNotFoundException{
		try
		{
			File file = new File(fileName);
			
			FileInputStream input = new FileInputStream(file);
			
			byte buffer[] = new byte[(int)file.length()];
			
			input.read(buffer);
			input.close();
			
			String tmp = new String(buffer);
			
			JSONObject o = new JSONObject(tmp);
			
			return o;
		}
		catch(FileNotFoundException fnfe){
			throw new FileNotFoundException();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}