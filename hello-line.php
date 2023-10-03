<?php

//$output = shell_exec('type /Users/zytk/documents/lab/php/test.txt'); // path txt file
$output = shell_exec('type test.txt'); // path txt file
define('LINE_API', "https://notify-api.line.me/api/notify");

$token = "0IEAf1pEG9sOE44lKtQjyljyVlckn9T59dFfDYxNVGj"; // ใส่ Token ที่ copy เอาไว้
//$str = "ทดสอบภาษาไทย Hello"; // ข้อความที่ต้องการส่ง สูงสุด 1000 ตัวอักษร
$str = $output;
$res = notify_message($str, $token);
print_r($res);

function notify_message($message, $token) {
    $queryData = array('message' => $message);
    $queryData = http_build_query($queryData, '', '&'); // แก้ไขรูปแบบของเครื่องหมาย
    $headerOptions = array(
        'http' => array(
            'method' => 'POST',
            'header' => "Content-Type: application/x-www-form-urlencoded\r\n" .
                "Authorization: Bearer " . $token . "\r\n" .
                "Content-Length: " . strlen($queryData) . "\r\n",
            'content' => $queryData,
        ),
    );
    $context = stream_context_create($headerOptions);
    //$result = file_get_contents(LINE_API, FALSE, $context);
    $result = file_get_contents(LINE_API, FALSE, $context);
        if ($result === FALSE) {
            echo "Error: " . error_get_last()['message'];
        } else {
            $res = json_decode($result);
            print_r($res);
        }
    $res = json_decode($result);
    return $res;
}
?>
