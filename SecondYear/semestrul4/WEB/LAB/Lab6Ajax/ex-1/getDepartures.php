<?php
$servername = "localhost";
$username = "root";
$password = "programareweb";
$database = "laborator_ajax";

$conn = new mysqli($servername, $username, $password, $database);
if ($conn->connect_errno) {
    echo json_encode(["error" => "Database connection failed: " . $conn->connect_error]);
    exit();
}

$query = "SELECT DISTINCT start_city FROM train_stations";
$result = $conn->query($query);
if ($result->num_rows > 0) {
    $departures = [];
    while ($row = $result->fetch_assoc()) {
        $departures[] = $row['start_city'];
    }
    echo json_encode($departures);
} else {
    echo json_encode(["error" => "No departures found."]);
}
?>