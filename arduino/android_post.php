<?php

require_once('init.php');

$user_val = $_POST["user_val"];

$sql1 = "INSERT INTO esp_user (user_val) VALUES ('$user_val')";

if(mysqli_query($con, $sql1))
echo "Posting Successful !";

else
echo "Posting Failed !";

?>	