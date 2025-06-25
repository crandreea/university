<?php
session_start();
if (!isset($_SESSION['user_id'])) {
    die("Acces interzis.");
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

$poza_id = intval($_POST['poza_id']);

$stmt = $conn->prepare("SELECT cale FROM poze WHERE id = ? AND user_id = ?");
$stmt->bind_param("ii", $poza_id, $_SESSION['user_id']);
$stmt->execute();
$result = $stmt->get_result();

if ($row = $result->fetch_assoc()) {
    unlink($row['cale']);
    $stmt = $conn->prepare("DELETE FROM poze WHERE id = ?");
    $stmt->bind_param("i", $poza_id);
    $stmt->execute();
    header("Location: profile.php");
} else {
    echo "Nu ai permisiunea sa stergi.";
}
?>
