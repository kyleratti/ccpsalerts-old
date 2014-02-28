/**
* @author Kyle Ratti
* @version 1.0, 02/27/14
*/

package com.ccpsalerts;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.io.File;
import java.io.Writer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.nio.charset.Charset;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;

import twitter4j.*;

public class WebsiteChecker extends Thread
{
	private static final String TARGET_URL = "http://carrollk12.org";
	//private static final String TARGET_URL = "https://dl.dropboxusercontent.com/u/161991137/saved/Carroll%20County%20Public%20Schools.html";
	private static final String DATA_FILE = "website.dat";

	private String getLastAnnouncement()
	{
		File objFile = new File(WebsiteChecker.DATA_FILE);

		if(objFile.exists())
		{
			try
			{
				List<String> arrLines = Files.readAllLines(Paths.get(WebsiteChecker.DATA_FILE), Charset.defaultCharset());

				if(arrLines.size() > 0)
					return arrLines.get(0);
			}
			catch(IOException e)
			{
				System.err.println("Error reading data file: " + e.getMessage());
			}
		}

		return null;
	}

	private void setLastAnnouncement(String strLast)
	{
		Writer objWriter = null;

		try
		{
			objWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(WebsiteChecker.DATA_FILE), "UTF-8"));
			objWriter.write(strLast);
		}
		catch(IOException e)
		{
			System.err.println("Error writing announcement: " + e.getMessage());
		}
		finally
		{
			if(objWriter != null)
			{
				try
				{
					objWriter.close();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void run()
	{
		try
		{
			Document objDoc = Jsoup.connect(WebsiteChecker.TARGET_URL).get();
			Element objHeader = objDoc.getElementById("headerEmergency");

			boolean bUpdated = false;

			if(objHeader != null)
			{
				Element objAnnounce = objHeader.getElementById("announcements");

				if(objAnnounce != null)
				{
					String strText = objAnnounce.text();
					
					if(strText.length() > 0)
					{
						String strLast = this.getLastAnnouncement();

						if(strLast == null || !strLast.equals(strText))
						{
							try
							{
								TwitterAPI.updateStatus("From carrollk12.org: " + strText);
								bUpdated = true;
								Driver.println("Updated status: " + strText);

								this.setLastAnnouncement(strText);
							}
							catch(TwitterException e)
							{
								Driver.errorln("Error sending status update: " + e.getMessage());
							}
						}
					}
				}
			}

			if(!bUpdated)
				Driver.println("Hit " + WebsiteChecker.TARGET_URL + ", no changes found");

			Driver.println("Finished WebsiteChecker");
		}
		catch(IOException e)
		{
			Driver.errorln("Error retrieving webpage: " + e.getMessage());
		}
	}
}