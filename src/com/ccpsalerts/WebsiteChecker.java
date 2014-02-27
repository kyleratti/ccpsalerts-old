/**
* @author Kyle Ratti
* @version 1.0, 02/27/14
*/

package com.ccpsalerts;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;

public class WebsiteChecker extends Thread
{
	//private static final String TARGET_URL = "http://carrollk12.org";
	private static final String TARGET_URL = "https://dl.dropboxusercontent.com/u/161991137/saved/Carroll%20County%20Public%20Schools.html";

	@Override
	public void run()
	{
		try
		{
			Document objDoc = Jsoup.connect(WebsiteChecker.TARGET_URL).get();
			Element objHeader = objDoc.getElementById("headerEmergency");

			if(objHeader != null)
			{
				Element objAnnouncements = objHeader.getElementById("announcements");

				if(objAnnouncements != null)
				{
					String strText = objAnnouncements.text();
					
					if(strText.length() > 0)
					{
						TwitterAPI.updateStatus("Testing: " + strText);
					}
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}