/**
* @author Kyle Ratti
* @version 1.0, 02/27/14
*/

package com.ccpsalerts.twitter;

import com.ccpsalerts.Driver;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAPI
{
	private static Twitter getInstance()
	{
		ConfigurationBuilder objConf = new ConfigurationBuilder();
		objConf.setDebugEnabled(true)
			.setOAuthConsumerKey("redacted")
			.setOAuthConsumerSecret("redacted")
			.setOAuthAccessToken("redacted")
			.setOAuthAccessTokenSecret("redacted");

		TwitterFactory objTF = new TwitterFactory(objConf.build());
		return objTF.getInstance();
	}

	/**
	* Update the status
	*
	* @param strStatus The string to set the status to
	* @throws TwitterException
	*/
	public static void updateStatus(String strStatus) throws TwitterException
	{
		if(Driver.DEBUGGING)
			Driver.println("Sending tweet\n\tContent: " + strStatus);
		else
			TwitterAPI.getInstance().updateStatus(strStatus);
	}
}