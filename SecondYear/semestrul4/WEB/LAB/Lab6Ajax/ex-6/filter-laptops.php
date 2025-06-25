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

$data = json_decode(file_get_contents('php://input'), true);

$whereClauses = [];
$params = [];
$types = '';

$map = [
    "manufacturer" => "manufacturer",
    "cpu" => "cpu",
    "ram" => "ram",
    "gpu" => "gpu",
    "persistentMemory" => "persistent_memory",
];

foreach ($map as $key => $col) {
    if (!empty($data[$key])) {
        $whereClauses[] = "`$col` = ?";
        $params[] = $data[$key];
        $types .= 's';
    }
}

$sql = "SELECT manufacturer, cpu, ram, gpu, persistent_memory FROM laptops";

if (count($whereClauses) > 0) {
    $sql .= " WHERE " . implode(' AND ', $whereClauses);
}

$stmt = $conn->prepare($sql);
if (!$stmt) {
    http_response_code(500);
    echo json_encode(["error" => "Failed to prepare statement: " . $conn->error]);
    exit();
}

if ($params) {
    $stmt->bind_param($types, ...$params);
}

$stmt->execute();

$result = $stmt->get_result();
if (!$result) {
    http_response_code(500);
    echo json_encode(["error" => "Failed to execute query: " . $stmt->error]);
    exit();
}

$data = $result->fetch_all(MYSQLI_ASSOC);
echo json_encode($data);

$stmt->close();
$conn->close();
?>