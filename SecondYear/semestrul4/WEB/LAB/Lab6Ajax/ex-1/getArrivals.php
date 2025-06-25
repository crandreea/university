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

if (isset($_GET['city'])) {
    $city = $_GET['city'];

    $query = "SELECT DISTINCT end_city FROM train_stations WHERE start_city = ?";
    $stmt = $conn->prepare($query);
    $stmt->bind_param("s", $city);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $arrivals = [];
        while ($row = $result->fetch_assoc()) {
            $arrivals[] = $row['end_city'];
        }
        echo json_encode($arrivals);
    } else {
        echo json_encode(["error" => "No arrivals found for the city."]);
    }

    $stmt->close();
} else {
    echo json_encode(["error" => "No city provided."]);
}
?>