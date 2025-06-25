<?php
header('Content-Type: application/json');

$basePath = rtrim(__DIR__, '/');

$input = json_decode(file_get_contents('php://input'), true);

if ($input === null) {
    http_response_code(400);
    echo json_encode(['error' => 'Invalid or missing JSON input']);
    exit;
}

if (isset($input['path'])) {
    $relativePath = $input['path'];
    $fullPath = realpath($basePath . '/' . $relativePath);

    if ($fullPath === false || strpos(rtrim($fullPath, '/'), $basePath) !== 0 || !is_dir($fullPath)) {
        http_response_code(400);
        echo json_encode(['error' => 'Invalid path']);
        exit;
    }

    $items = array_diff(scandir($fullPath), ['.', '..']);
    $result = [];

    foreach ($items as $item) {
        $itemPath = $fullPath . '/' . $item;
        $relativeItemPath = ltrim($relativePath . '/' . $item, '/');
        $result[] = [
            'name' => $item,
            'type' => is_dir($itemPath) ? 'directory' : 'file',
            'path' => $relativeItemPath
        ];
    }

    echo json_encode($result);

} elseif (isset($input['file'])) {
    $filePath = $input['file'];
    $fullFilePath = realpath($basePath . '/' . $filePath);

    if (strpos($fullFilePath, $basePath) !== 0 || !is_file($fullFilePath)) {
        http_response_code(400);
        echo json_encode(['error' => 'Invalid file']);
        exit;
    }

    header('Content-Type: text/plain');
    echo file_get_contents($fullFilePath);

} else {
    http_response_code(400);
    echo json_encode(['error' => 'Invalid request']);
}
?>