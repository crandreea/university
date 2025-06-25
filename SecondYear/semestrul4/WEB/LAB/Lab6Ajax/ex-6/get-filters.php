<?php
header('Content-Type: application/json'); 

$servername = "localhost";
$username = "root";
$password = "programareweb";
$database = "laborator_ajax";

$conn = new mysqli($servername, $username, $password, $database);
if ($conn->connect_errno) {
    http_response_code(500);
    echo json_encode(["error" => "Database connection failed: " . $conn->connect_error]);
    exit();
}

function getDistinct($conn, $column) {
    $sql = "SELECT DISTINCT `$column` FROM laptops WHERE `$column` IS NOT NULL AND `$column` <> '' ORDER BY `$column`";
    $result = $conn->query($sql);
    $values = [];
    if ($result) {
        while ($row = $result->fetch_assoc()) {
            $values[] = $row[$column];
        }
    }
    return $values;
}

$response = [
    "manufacturer" => getDistinct($conn, 'manufacturer'),
    "cpu" => getDistinct($conn, 'cpu'),
    "ram" => getDistinct($conn, 'ram'),
    "gpu" => getDistinct($conn, 'gpu'),
    "persistent_memory" => getDistinct($conn, 'persistent_memory'),
];

echo json_encode($response);

$conn->close();
?>