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

if (isset($_GET['id'])) {
    $id = $_GET['id'];
    
    $query = "SELECT * FROM contacts WHERE id = ?";
    $stmt = $conn->prepare($query);
    $stmt->bind_param("s", $id);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($row = $result->fetch_assoc()) {
        echo json_encode($row); 
    } else {
        echo json_encode(["error" => "Contact not found."]);
    }
} else {
    echo json_encode(["error" => "No id provided."]);
}
?>
