<?php
require_once("TwitterAPIExchange.php");

$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, "http://www.carrollk12.org");
//curl_setopt($ch, CURLOPT_URL, "https://dl.dropboxusercontent.com/u/161991137/saved/Carroll%20County%20Public%20Schools.html");
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$strOutput = curl_exec($ch);
curl_close($ch);

$objDOM = new DOMDocument();
libxml_use_internal_errors(true);
$objDOM->loadHTML($strOutput);
libxml_clear_errors();

$objHeader = $objDOM->getElementById("headerEmergency");

if($objHeader !== null)
{
	$objAnnouncements = $objDOM->getElementById("announcements");

	if($objAnnouncements !== null)
	{
		$strText = $objAnnouncements->nodeValue;

		if(isNewTweet($strText))
		{
			send_tweet($strText);
			echo "<br/><br/>Tweet sent!";
		}
		else
		{
			echo "No new status";
		}
	}
	else
	{
		writeData("");
	}
}

function isNewTweet($strText)
{
	if(file_exists("./last_tweet.txt"))
	{
		$strData = @file_get_contents("./last_tweet.txt");

		if($strData !== FALSE && $strData === $strText)
			return FALSE;
	}

	return TRUE;
}

function writeData($strData)
{
	$fh = fopen("last_tweet.txt", 'w') or die("can't open file");
	fwrite($fh, $strData);
	fclose($fh);
}

function send_tweet($strText)
{
	$settings = array(
		'oauth_access_token' => "2342773548-9tYGuGX8j5bYCFd2WhHS57uQRFu9bHDg7400oze",
		'oauth_access_token_secret' => "T4AhsCc7D6qKughumKDPhPkhEL2HWngBVIkwgXCQaTTRi",
		'consumer_key' => "RVueQj4xf2tQr4AnvMcOw",
		'consumer_secret' => "25F6Qg4LUi86ADLX36mdWQydVYAZp4buDLcuBDyUtI"
	);

	$url = 'https://api.twitter.com/1.1/statuses/update.json';
	$postfields = array(
		'status' => substr($strText, 0, 140),
	);

	$twitter = new TwitterAPIExchange($settings);
	$response = json_decode($twitter->buildOauth($url, 'POST')
				->setPostfields($postfields)
				->performRequest());

	var_dump($response);

	writeData($strText);
}