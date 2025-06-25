<?php
header('Content-Type: application/json');

$servername = "localhost";
$username = "root";
$password = "programareweb";
$database = "laborator_ajax";

$conn = new mysqli($servername, $username, $password, $database);
if ($conn->connect_errno) {
    echo json_encode(["success" => false, "error" => "Database connection failed: " . $conn->connect_error]);
    exit();
}

if (isset($_POST['last_name'], $_POST['first_name'], $_POST['phone'], $_POST['email'])) {
    $last_name = $_POST['last_name'];
    $first_name = $_POST['first_name'];
    $phone = $_POST['phone'];
    $email = $_POST['email'];

    $stmt = $conn->prepare("INSERT INTO contacts (last_name, first_name, phone, email) VALUES (?, ?, ?, ?)");
    $stmt->bind_param("ssss", $last_name, $first_name, $phone, $email);

    if ($stmt->execute()) {
        echo json_encode(["success" => true]);
    } else {
        echo json_encode(["success" => false, "error" => $stmt->error]);
    }
} else {
    echo json_encode(["success" => false, "error" => "Missing fields"]);
}
?>
