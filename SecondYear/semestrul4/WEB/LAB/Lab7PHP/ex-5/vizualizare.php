<?php
if (session_status() === PHP_SESSION_NONE) {
    session_start();
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

$utilizatori = $conn->query("SELECT id, email FROM utilizatori WHERE confirmat = 1");
?>

<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <title>Alti utilizatori</title>
</head>
<body>
    <h2>Profilele altor utilizatori</h2>

    <form action="profile.php" method="get" style="margin-bottom: 20px;">
        <button type="submit">Profilul meu</button>
    </form>

    <?php while ($user = $utilizatori->fetch_assoc()): ?>
        <?php
        if ($user['id'] == $_SESSION['user_id']) continue;

        $stmt = $conn->prepare("SELECT cale FROM poze WHERE user_id = ?");
        $stmt->bind_param("i", $user['id']);
        $stmt->execute();
        $poze = $stmt->get_result();
        ?>

        <h3><?= htmlspecialchars($user['email']) ?></h3>

        <?php if ($poze->num_rows === 0): ?>
            <p>Nu are poze.</p>
        <?php else: ?>
            <?php while ($poza = $poze->fetch_assoc()): ?>
                <img src="<?= htmlspecialchars($poza['cale']) ?>" width="150" style="margin:5px;">
            <?php endwhile; ?>
        <?php endif; ?>

        <hr>
    <?php endwhile; ?>
</body>
</html>