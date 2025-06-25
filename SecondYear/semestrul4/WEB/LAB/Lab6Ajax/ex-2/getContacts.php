<?php
header('Content-Type: application/json');

$servername = "localhost";
$username = "root";
$password = "programareweb";
$database = "laborator_ajax";

$conn = new mysqli($servername, $username, $password, $database);
if ($conn->connect_errno) {
    echo json_encode(["error" => "Database connection failed: " . $conn->connect_error]);
    exit();
}

if (isset($_POST['offset']) && isset($_POST['limit'])) {
    $offset = (int)$_POST['offset'];
    $limit = (int)$_POST['limit'];

    $countQuery = "SELECT COUNT(*) as total FROM contacts";
    $countResult = $conn->query($countQuery);
    $total = $countResult->fetch_assoc()['total'];

    $recordsQuery = "SELECT last_name, first_name, phone, email FROM contacts LIMIT $limit OFFSET $offset";
    $recordResult = $conn->query($recordsQuery);

    $contacts = $recordResult->fetch_all(MYSQLI_ASSOC);

    echo json_encode([
        "records" => $contacts,
        "total" => $total
    ]);
    
} else {
    echo json_encode(["error" => "No offset or limit provided."]);
}
?>
