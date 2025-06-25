<?php
session_start();
header("Content-Security-Policy: default-src 'self';");
header("X-Content-Type-Options: nosniff"); 

$servername = "localhost";
$username = "root";
$password = "programareweb";
$database = "laborator_ajax";

$conn = new mysqli($servername, $username, $password, $database);
if ($conn->connect_errno) {
    echo json_encode(["error" => "Database connection failed: " . $conn->connect_error]);
    exit();
}

function clean_input($data) {
    return htmlspecialchars(trim($data), ENT_QUOTES, 'UTF-8');
}

$token = $_GET['token'] ?? '';

if ($token) {
    $stmt = $conn->prepare("UPDATE utilizatori SET confirmat = TRUE, token_confirmare = NULL WHERE token_confirmare = ?");
    $stmt->bind_param("s", $token);
    $stmt->execute();

    if ($stmt->affected_rows > 0) {
        echo "Cont confirmat cu succes. Poti acum sa te autentifici.";
        header("Location: login.html");
        exit();
    } else {
        echo "Token invalid sau cont deja confirmat.";
    }
} else {
    echo "Token lipsa.";
}

?>