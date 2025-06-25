<?php
session_start();
header("Content-Security-Policy: default-src 'self';");
header("X-Content-Type-Options: nosniff");

$servername = "localhost";
$username = "cair3510";
$password = "-TYwZGM2ND*y";
$database = "cair3510";

$conn = new mysqli($servername, $username, $password, $database);
if ($conn->connect_errno) {
    echo json_encode(["error" => "Database connection failed: " . $conn->connect_error]);
    exit();
}

function clean_input($data) {
    return htmlspecialchars(trim($data), ENT_QUOTES, 'UTF-8');
}

$plecare = clean_input($_POST['plecare'] ?? '');
$sosire = clean_input($_POST['sosire'] ?? '');
$direct = isset($_POST['direct']);

if ($direct) {
    $stmt = $conn->prepare("SELECT * FROM trenuri WHERE localitate_plecare = ? AND localitate_sosire = ?");
    $stmt->bind_param("ss", $plecare, $sosire);
    $stmt->execute();
    $rezultate = $stmt->get_result();

    echo "<h2>Rezultate căutare</h2>";
    if ($rezultate->num_rows == 0) {
        echo "<p>Niciun tren găsit.</p>";
    } else {
        echo "<ul>";
        foreach ($rezultate as $row) {
            echo "<li>";
            echo "Tren: " . htmlentities($row['nr_tren']) . " (" . htmlentities($row['tip_tren']) . ")<br>";
            echo "Plecare: " . htmlentities($row['localitate_plecare']) . " la " . htmlentities($row['ora_plecare']) . "<br>";
            echo "Sosire: " . htmlentities($row['localitate_sosire']) . " la " . htmlentities($row['ora_sosire']) . "<br>";
            echo "</li><hr>";
        }
        echo "</ul>";
    }
} else {
    $stmt = $conn->prepare("
        SELECT t1.nr_tren AS tren1, t1.tip_tren AS tip1, t1.localitate_plecare, t1.localitate_sosire AS legatura,
               t1.ora_plecare, t1.ora_sosire,
               t2.nr_tren AS tren2, t2.tip_tren AS tip2, t2.localitate_sosire AS destinatie,
               t2.ora_plecare AS plecare2, t2.ora_sosire AS sosire2
        FROM trenuri t1
        JOIN trenuri t2 ON t1.localitate_sosire = t2.localitate_plecare
        WHERE t1.localitate_plecare = ? AND t2.localitate_sosire = ?
    ");
    $stmt->bind_param("ss", $plecare, $sosire);
    $stmt->execute();
    $rezultate = $stmt->get_result();

    echo "<h2>Rezultate căutare cu legături</h2>";
    if ($rezultate->num_rows === 0) {
        echo "<p>Niciun tren cu legătură găsit.</p>";
    } else {
        echo "<ul>";
        while ($row = $rezultate->fetch_assoc()) {
            echo "<li>";
            echo "Tren 1: " . htmlentities($row['tren1'] ?? '') . " (" . htmlentities($row['tip1'] ?? '') . ")<br>";
            echo "Plecare din " . htmlentities($row['localitate_plecare'] ?? '') . " la " . htmlentities($row['ora_plecare'] ?? '') . "<br>";
            echo "Sosire în " . htmlentities($row['legatura'] ?? '') . " la " . htmlentities($row['ora_sosire'] ?? '') . "<br>";
            echo "Tren 2: " . htmlentities($row['tren2'] ?? '') . " (" . htmlentities($row['tip2'] ?? '') . ")<br>";
            echo "Plecare din " . htmlentities($row['legatura'] ?? '') . " la " . htmlentities($row['plecare2'] ?? '') . "<br>";
            echo "Sosire în " . htmlentities($row['destinatie'] ?? '') . " la " . htmlentities($row['sosire2'] ?? '') . "<br>";
            echo "</li><hr>";
        }
        echo "</ul>";
    }
}

$stmt->close();
$conn->close();
?>