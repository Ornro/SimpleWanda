package fr.irit.wanda.tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ATAPIrequestTests {
	public static void main(String[] args)
 	{
		doList();
 	}
	
	public static void doList() {
		try {
			String adress = "http://localhost:8888/ATAPI?action=list";
			URL url = new URL(adress);
			URLConnection yc = url.openConnection();
			String answer = "", temp = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			while ((temp = in.readLine()) != null)
			{
				answer = answer + temp;
			}
				in.close();
				System.out.println(answer);
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	public static void doGetVideo() {
		try {
			String adress = "http://localhost:8888/ATAPI?action=download&video=/videoname.flv";
			URL url = new URL(adress);
			URLConnection yc = url.openConnection();
			String answer = "", temp = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			while ((temp = in.readLine()) != null)
			{
				answer = answer + temp;
			}
				in.close();
				System.out.println(answer);
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
}

