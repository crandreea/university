<?php
header('Content-Type: application/json');

$input = json_decode(file_get_contents('php://input'), true);
$board = $input['board'];

function checkWinner($board) {
    $lines = [];

    for ($i = 0; $i < 3; $i++) {
        $lines[] = [$board[$i][0], $board[$i][1], $board[$i][2]];
    }

    for ($j = 0; $j < 3; $j++) {
        $lines[] = [$board[0][$j], $board[1][$j], $board[2][$j]];
    }

    $lines[] = [$board[0][0], $board[1][1], $board[2][2]];
    $lines[] = [$board[0][2], $board[1][1], $board[2][0]];

    foreach ($lines as $line) {
        if ($line[0] !== null && $line[0] === $line[1] && $line[1] === $line[2]) {
            return $line[0]; 
        }
    }

    return null; 
}

$emptyCells = [];
for ($i = 0; $i < 3; $i++) {
    for ($j = 0; $j < 3; $j++) {
        if ($board[$i][$j] === null) {
            $emptyCells[] = ['row' => $i, 'col' => $j];
        }
    }
}

if (!empty($emptyCells)) {
    $randomMove = $emptyCells[array_rand($emptyCells)];
    $board[$randomMove['row']][$randomMove['col']] = 'O';
    $winner = checkWinner($board);

    echo json_encode([
        'row' => $randomMove['row'],
        'col' => $randomMove['col'],
        'winner' => $winner ?? null
    ]);
} else {
    $winner = checkWinner($board);
    echo json_encode([
        'row' => -1,
        'col' => -1,
        'winner' => $winner ?? 'draw'
    ]);
}
?>
