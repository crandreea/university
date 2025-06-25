<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <title>Profilul Meu</title>
</head>
<body>
    <h2>Profilul meu</h2>

    <form action="upload.php" method="post" enctype="multipart/form-data">
        <input type="file" name="poza" required>
        <button type="submit">Upload</button>
    </form>

    <h3>Pozele mele:</h3>
    <?php if (empty($poze)): ?>
        <p>Nu ai incarcat nicio poza.</p>
    <?php else: ?>
        <?php foreach ($poze as $poza): ?>
            <div style="margin-bottom:10px">
                <img src="<?= htmlspecialchars($poza['cale']) ?>" width="200"><br>
                <form action="delete.php" method="POST" style="display:inline;">
                    <input type="hidden" name="poza_id" value="<?= $poza['id'] ?>">
                    <button type="submit">Delete</button>
                </form>
            </div>
        <?php endforeach; ?>
    <?php endif; ?>
</body>
</html>
