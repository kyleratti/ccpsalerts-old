<?php
$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, "http://www.carrollk12.org");
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$strOutput = curl_exec($ch);
curl_close($ch);

echo htmlentities($strOutput);