<?php
require 'vendor/autoload.php';
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

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

$email = clean_input($_POST['email'] ?? '');
$parola = $_POST['parola'] ?? '';

if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
    die("Email invalid.");
}

$parola_hash = password_hash($parola, PASSWORD_DEFAULT);
$token = bin2hex(random_bytes(32));

$stmt = $conn->prepare("INSERT INTO utilizatori (email, parola, token_confirmare) VALUES (?, ?, ?)");
$stmt->bind_param("sss", $email, $parola_hash, $token);

if ($stmt->execute()) {
    try {
        $phpmailer = new PHPMailer();
        $phpmailer->isSMTP();
        $phpmailer->Host = 'sandbox.smtp.mailtrap.io';
        $phpmailer->SMTPAuth = true;
        $phpmailer->Port = 2525;
        $phpmailer->Username = 'cb559d670ffe30';
        $phpmailer->Password = '6e5765bd8ee64d';


        $phpmailer->setFrom('no-reply@situl-tau.com', 'Site Trenuri');
        $phpmailer->addAddress($email);

        $phpmailer->isHTML(true);
        $phpmailer->Subject = 'Confirmare inregistrare cont';
        $link = "http://localhost:8000/confirmare.php?token=" . $token;
        $phpmailer->Body = "
            <h3>Bine ai venit!</h3>
            <p>Confirma inregistrarea contului tau accesand urmatorul link:</p>
            <a href='$link'>$link</a>
            <br><br>
        ";

        if ($phpmailer->send()) {
            echo "Se asteapta confirmarea contului ...";
        } else {
            echo "Contul a fost creat, dar emailul nu a putut fi trimis.";
        }
        
    } catch (Exception $e) {
        echo "Emailul nu a putut fi trimis. Eroare: " . $phpmailer->ErrorInfo;
    }

} else {
    echo "Eroare: adresa de email este deja inregistrata sau nu s-a putut salva.";

}
?>