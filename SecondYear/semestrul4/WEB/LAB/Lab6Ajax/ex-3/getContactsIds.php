<?php
$servername = "localhost";
$username = "root";
$password = "programareweb";
$database = "laborator_ajax";

$conn = new mysqli($servername, $username, $password, $database);
if ($conn->connect_errno) {
    echo json_encode(["error" => "Database connection failed: " . $conn->connect_error]);
    exit();
}

$idsQuery = "SELECT id FROM contacts";
$idsResult = $conn->query($idsQuery);

$ids = [];
while ($row = $idsResult->fetch_assoc()) {
    $ids[] = $row['id'];
}

echo json_encode(["ids" => $ids]);
?>
