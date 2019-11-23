<?php

require_once('init.php');


$sql1 = "SELECT user_val FROM esp_user ORDER BY entry_id DESC LIMIT 1";

$result1 = mysqli_query($con, $sql1);

if(mysqli_num_rows($result1)>0){

$row = mysqli_fetch_array($result1);

$user_val = $row['user_val'];

echo $user_val;

}

else
echo 99999;

?>