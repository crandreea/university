<?php
session_start();
if (!isset($_SESSION['user_id'])) {
    die("Autentificare necesara.");
}

$servername = "localhost";
$username = "root";
$password = "programareweb";
$database = "laborator_ajax";

$conn = new mysqli($servername, $username, $password, $database);
if ($conn->connect_errno) {
    echo json_encode(["error" => "Database connection failed: " . $conn->connect_error]);
    exit();
}

$user_id = $_SESSION['user_id'];
$stmt = $conn->prepare("SELECT cale, id FROM poze WHERE user_id = ?");
$stmt->bind_param("i", $user_id);
$stmt->execute();
$poze_result = $stmt->get_result();


$poze = [];
while ($row = $poze_result->fetch_assoc()) {
    $poze[] = $row;
}

include 'profile_view.php';
?>
