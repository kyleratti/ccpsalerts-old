package com.ccpsalerts.twitter;

import twitter4j.TwitterException;

/**
 * @author Kyle
 * @version 1.0, 4/23/2014
 */
public class TweetTooLongException extends TwitterException {
	public TweetTooLongException(String message) {
		super(message);
	}
}
