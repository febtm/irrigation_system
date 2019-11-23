<?php



$request = 'http://api.openweathermap.org/data/2.5/forecast/daily?lat=13.059537&lon=80.242479&units=metric&cnt=1&appid=228a0735d5437d1e6fe3078844cebafc'; //Nungambakkam

//$request = 'http://api.openweathermap.org/data/2.5/forecast/daily?lat=12.990808&lon=80.233903&units=metric&cnt=1&appid=228a0735d5437d1e6fe3078844cebafc'; //IIT Madras



$response  = file_get_contents($request);



$jsonobj  = json_decode($response, true);



$id = $jsonobj['list'][0]['weather'][0]['id'];


   if (($id >= 200 && $id <= 230) || ($id >= 300 && $id <= 321) || ($id >= 500 && $id <= 531) || ($id >= 600 && $id <= 622))

   echo 2512; //Rainfall

   else

   echo 2412; //No Rainfall

?>