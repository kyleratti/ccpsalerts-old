/**
* @author Kyle Ratti
* @version 1.0, 02/27/14
*/

package com.ccpsalerts;

import twitter4j.*;
import twitter4j.api.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAPI
{
	private static Twitter getInstance()
	{
		ConfigurationBuilder objConf = new ConfigurationBuilder();
		objConf.setDebugEnabled(true)
			.setOAuthConsumerKey("RVueQj4xf2tQr4AnvMcOw")
			.setOAuthConsumerSecret("25F6Qg4LUi86ADLX36mdWQydVYAZp4buDLcuBDyUtI")
			.setOAuthAccessToken("2342773548-9tYGuGX8j5bYCFd2WhHS57uQRFu9bHDg7400oze")
			.setOAuthAccessTokenSecret("T4AhsCc7D6qKughumKDPhPkhEL2HWngBVIkwgXCQaTTRi");

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