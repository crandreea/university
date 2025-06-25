const size = 4;
let board = [];
let emptyRow, emptyCol;

function createBoard() {
    let numbers = Array.from({ length: size * size - 1 }, (_, i) => i + 1);
    numbers.push(null);
    numbers.sort(() => Math.random() - 0.5);

    let $table = $("#puzzle");
    $table.empty();
    board = [];

    for (let i = 0; i < size; i++) {
        let $row = $("<tr></tr>");
        board.push([]);

        for (let j = 0; j < size; j++) {
            let num = numbers[i * size + j];
            let $cell = $("<td></td>");

            board[i].push(num);
            $cell.text(num !== null ? num : "");
            if (num === null) {
                emptyRow = i;
                emptyCol = j;
                $cell.addClass("empty");
            }

            $row.append($cell);
        }

        $table.append($row);
    }
}

function moveTile(row, col) {
    if (row >= 0 && row < size && col >= 0 && col < size) {
        board[emptyRow][emptyCol] = board[row][col];
        board[row][col] = null;
        emptyRow = row;
        emptyCol = col;
        renderBoard();
    }
}

function handleKeyPress(event) {
    switch (event.key) {
        case "ArrowUp": moveTile(emptyRow - 1, emptyCol); break;
        case "ArrowDown": moveTile(emptyRow + 1, emptyCol); break;
        case "ArrowLeft": moveTile(emptyRow, emptyCol - 1); break;
        case "ArrowRight": moveTile(emptyRow, emptyCol + 1); break;
    }
}

function renderBoard() {
    let $rows = $("#puzzle").find("tr");

    for (let i = 0; i < size; i++) {
        let $cells = $rows.eq(i).find("td");

        for (let j = 0; j < size; j++) {
            let val = board[i][j];
            let $cell = $cells.eq(j);
            $cell.text(val !== null ? val : "");
            $cell.toggleClass("empty", val === null);
        }
    }
}

$(document).on("keydown", handleKeyPress);
$(document).ready(createBoard);




// const size = 4; 
// let board = [];
// let emptyRow, emptyCol;

// function createBoard() {
//     let numbers = Array.from({ length: size * size - 1 }, (_, i) => i + 1);
//     numbers.push(null);
//     numbers.sort(() => Math.random() - 0.5);

//     let table = document.getElementById("puzzle");
//     table.innerHTML = "";
//     board = [];

//     for (let i = 0; i < size; i++) {
//         let row = table.insertRow();
//         board.push([]);
//         for (let j = 0; j < size; j++) {
//             let num = numbers[i * size + j];
//             let cell = row.insertCell();
//             board[i].push(num);
//             cell.textContent = num !== null ? num : "";
//             if (num === null) {
//                 emptyRow = i;
//                 emptyCol = j;
//                 cell.classList.add("empty");
//             }
//         }
//     }
// }

// function moveTile(row, col) {
//     if (row >= 0 && row < size && col >= 0 && col < size) {
//         board[emptyRow][emptyCol] = board[row][col];
//         board[row][col] = null;
//         emptyRow = row;
//         emptyCol = col;
//         renderBoard();
//     }
// }

// function handleKeyPress(event) {
//     switch (event.key) {
//         case "ArrowUp": moveTile(emptyRow - 1, emptyCol); break;
//         case "ArrowDown": moveTile(emptyRow + 1, emptyCol); break;
//         case "ArrowLeft": moveTile(emptyRow, emptyCol - 1); break;
//         case "ArrowRight": moveTile(emptyRow, emptyCol + 1); break;
//     }
// }

// function renderBoard() {
//     let table = document.getElementById("puzzle");
//     for (let i = 0; i < size; i++) {
//         for (let j = 0; j < size; j++) {
//             let cell = table.rows[i].cells[j];
//             cell.textContent = board[i][j] !== null ? board[i][j] : "";
//             cell.classList.toggle("empty", board[i][j] === null);
//         }
//     }
// }

// document.addEventListener("keydown", handleKeyPress);
// createBoard();