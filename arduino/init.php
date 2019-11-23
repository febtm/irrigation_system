<?php

$db_name = "u691283964_ics";
$mysql_user = "u691283964_ics";
$mysql_pass = "hugh#007";
$server_name = "mysql.hostinger.in";

$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);

if(!$con)
{
//echo "Connection Error ... ".mysqli_connect_error();
}
else
{
//echo "<h3>Database Connection Success ... </h3>";
}

?>