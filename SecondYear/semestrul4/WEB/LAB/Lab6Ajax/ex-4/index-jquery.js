const symbols = ['X', 'O'];
let boardState;
let currentPlayer;

function initBoard() {
    const $board = $('#game-board');
    $board.empty();
    boardState = Array.from({ length: 3 }, () => Array(3).fill(null));
    currentPlayer = Math.floor(Math.random() * 2);

    for (let i = 0; i < 3; i++) {
        const $row = $('<tr>');
        for (let j = 0; j < 3; j++) {
            const $cell = $('<td>')
                .attr('data-row', i)
                .attr('data-col', j)
                .on('click', onCellClick);
            $row.append($cell);
        }
        $board.append($row);
    }

    updateInfo();
    setBoardClickable(currentPlayer !== 1);

    if (currentPlayer === 1) {
        setTimeout(makeComputerMove, 1000);
    }
}

function setBoardClickable(isClickable) {
    $('#game-board').css('pointer-events', isClickable ? 'auto' : 'none');
}

function updateInfo(message) {
    $('#game-info').text(message || `Current Player: ${symbols[currentPlayer]}`);
}

function onCellClick() {
    const $cell = $(this);
    const row = +$cell.data('row');
    const col = +$cell.data('col');

    if (boardState[row][col] !== null) return;

    makeMove(row, col);

    currentPlayer = 1 - currentPlayer;
    updateInfo();
    setBoardClickable(false);

    setTimeout(makeComputerMove, 1000);
}

function makeMove(row, col) {
    boardState[row][col] = symbols[currentPlayer];
    const $cell = $(`td[data-row='${row}'][data-col='${col}']`);
    $cell.text(symbols[currentPlayer]);
    $cell.css('pointer-events', 'none');
}

function makeComputerMove() {
    $.ajax({
        url: 'computer-move.php',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ board: boardState }),
        success(response) {
            const { row, col, winner } = response;

            if (winner) {
                if (winner === 'draw') {
                    updateInfo("It's a draw!");
                } else {
                    if (row >= 0 && col >= 0 && winner === 'O') makeMove(row, col);
                    updateInfo(`${winner} wins!`);
                }
                setBoardClickable(false);
                return;
            }

            if (row >= 0 && col >= 0) {
                makeMove(row, col);
            }

            currentPlayer = 1 - currentPlayer;
            updateInfo();
            setBoardClickable(true);
        },
        error(xhr) {
            console.error('Request failed with status:', xhr.status);
            setBoardClickable(true);
        }
    });
}

$(window).on('load', initBoard);
