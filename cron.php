<?php
$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, "http://www.carrollk12.org");
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$strOutput = curl_exec($ch);
curl_close($ch);

$objDOM = new DOMDocument();
libxml_use_internal_errors(true);
$objDOM->loadHTML($strOutput);
libxml_clear_errors();

echo $objDOM->getElementById("announcements");