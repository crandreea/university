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

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $email = clean_input($_POST['email'] ?? '');
    $parola = $_POST['parola'] ?? '';

    if (empty($email) || empty($parola)) {
        echo "Completează toate campurile...";
        exit;
    }

    $stmt = $conn->prepare("SELECT id, parola, confirmat FROM utilizatori WHERE email = ?");
    $stmt->bind_param("s", $email);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($user = $result->fetch_assoc()) {
        if (!$user['confirmat']) {
            echo "Contul nu a fost confirmat. Verifica emailul.";
        }

        if (password_verify($parola, $user['parola'])) {
            $_SESSION['user_id'] = $user['id'];
            header("Location: vizualizare.php");
            exit();
        } else {
            echo "Parola gresita.";
        }
    } else {
        echo "Email inexistent.";
    }
        
}

?>
