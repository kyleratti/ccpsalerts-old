/**
* @author Kyle Ratti
* @version 1.0, 02/27/14
*/

package com.ccpsalerts;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
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
	
	private final int delay;

	/**
	* Creates a new website checker
	*
	* @param iDelay The number of seconds the checker may run before terminating
	*/
	public WebsiteChecker(int iDelay)
	{
		this.delay = iDelay;
	}

	private String getLastAnnouncement()
	{
		try
		{
			File objFile = new File(WebsiteChecker.DATA_FILE);//Thread.currentThread().getContextClassLoader().getResource(WebsiteChecker.DATA_FILE).toURI());

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
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
			Driver.println("\tChecking " + WebsiteChecker.TARGET_URL + "...");
			Document objDoc = Jsoup.connect(WebsiteChecker.TARGET_URL).timeout(1000 * this.delay).get();
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
								Driver.println("\tUpdated status: " + strText);

								this.setLastAnnouncement(strText);
							}
							catch(TwitterException e)
							{
								Driver.errorln("\tError sending status update: " + e.getMessage());
							}
						}
					}
				}
			}

			if(!bUpdated)
				Driver.println("\t...no changes found");
		}
		catch(IOException e)
		{
			Driver.errorln("\tError retrieving webpage: " + e.getMessage());
		}
	}
}