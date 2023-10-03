<?php

$output = shell_exec('type D:\test.txt'); // path txt file

define('LINE_API', "https://notify-api.line.me/api/notify");

$token = "0IEAf1pEG9sOE44lKtQjyljyVlckn9T59dFfDYxNVGj"; //ใส่Token ที่copy เอาไว้
$str = $output;

$res = notify_message($str, $token);
print_r($res);
function notify_message($message, $token)
{
    $queryData = array('message' => $message);
    $queryData = http_build_query($queryData, '', '&');
    $headerOptions = array(
        'http' => array(
            'method' => 'POST',
            'header' => "Content-Type: application/x-www-form-urlencoded\r\n"
            . "Authorization: Bearer " . $token . "\r\n"
            . "Content-Length: " . strlen($queryData) . "\r\n",
            'content' => $queryData
        ),
    );
    $context = stream_context_create($headerOptions);
    $result = file_get_contents(LINE_API, FALSE, $context);
    $res = json_decode($result);
    return $res;
}

?>
