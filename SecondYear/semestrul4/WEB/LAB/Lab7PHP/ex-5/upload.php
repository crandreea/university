<?php
session_start();
if (!isset($_SESSION['user_id'])) {
    die("Trebuie sa fii autentificat.");
}

$target_dir = "uploads/";
if (!is_dir($target_dir)) {
    mkdir($target_dir, 0755, true);
}

if (!isset($_FILES['poza']) || $_FILES['poza']['error'] !== UPLOAD_ERR_OK) {
    die("Eroare la upload. Fie fișierul este prea mare, fie nu a fost selectat.");
}

$target_file = $target_dir . basename($_FILES["poza"]["name"]);

$imageFileType = strtolower(pathinfo($target_file, PATHINFO_EXTENSION));
$allowed = ['jpg', 'jpeg', 'png'];

if (!in_array($imageFileType, $allowed)) {
    die("Tip de fisier invalid.");
}

if (move_uploaded_file($_FILES["poza"]["tmp_name"], $target_file)) {
    $servername = "localhost";
    $username = "root";
    $password = "programareweb";
    $database = "laborator_ajax";

    $conn = new mysqli($servername, $username, $password, $database);
    if ($conn->connect_errno) {
        echo json_encode(["error" => "Database connection failed: " . $conn->connect_error]);
        exit();
    }

    $stmt = $conn->prepare("INSERT INTO poze (user_id, cale) VALUES (?, ?)");
    $stmt->bind_param("is", $_SESSION['user_id'], $target_file);
    $stmt->execute();
    header("Location: profile.php");
} else {
    echo "Eroare la incarcare.";
}
?>
