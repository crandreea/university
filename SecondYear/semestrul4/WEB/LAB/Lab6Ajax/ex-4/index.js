const infoPannel = document.getElementById('game-info');
const board = document.getElementById('game-board');
const symbols = ['X', 'O'];
let boardState;
let currentPlayer;

function initBoard() {
    board.innerHTML = '';
    boardState = Array.from({ length: 3 }, () => Array(3).fill(null));
    currentPlayer = Math.floor(Math.random() * 2);

    for (let i = 0; i < 3; i++) {
        const row = document.createElement('tr');
        for (let j = 0; j < 3; j++) {
            const cell = document.createElement('td');
            cell.dataset.row = i;
            cell.dataset.col = j;
            cell.addEventListener('click', onCellClick);
            row.appendChild(cell);
        }
        board.appendChild(row);
    }

    updateInfo();
    setBoardClickable(currentPlayer !== 1);

    if (currentPlayer === 1) {
        setTimeout(makeComputerMove, 1000);
    }
}

function setBoardClickable(isClickable) {
    board.style.pointerEvents = isClickable ? 'auto' : 'none';
}

function updateInfo(message) {
    infoPannel.textContent = message || `Current Player: ${symbols[currentPlayer]}`;
}

function onCellClick(e) {
    const row = +e.target.dataset.row;
    const col = +e.target.dataset.col;

    if (boardState[row][col] !== null) return;

    makeMove(row, col);

    currentPlayer = 1 - currentPlayer;
    updateInfo();
    setBoardClickable(false);

    setTimeout(makeComputerMove, 1000);
}

function makeMove(row, col) {
    boardState[row][col] = symbols[currentPlayer];
    const cell = document.querySelector(`td[data-row='${row}'][data-col='${col}']`);
    cell.textContent = symbols[currentPlayer];
    cell.style.pointerEvents = 'none';
}

function makeComputerMove() {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'computer-move.php', true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = () => {
        if (xhr.status === 200) {
            const { row, col, winner } = JSON.parse(xhr.responseText);

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
        } else {
            console.error('Request failed with status:', xhr.status);
            setBoardClickable(true);
        }
    };

    xhr.send(JSON.stringify({ board: boardState }));
}

window.onload = initBoard;
