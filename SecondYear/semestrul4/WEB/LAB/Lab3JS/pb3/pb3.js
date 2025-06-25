const size = 4;
let elements = [];
let revealedCells = [];
let matchedPairs = 0;
let canSelect = true;
let useImages = true; 

const imagePaths = [
    "images/img1.png", "images/img2.png", "images/img3.png", "images/img4.png", "images/img5.png", 
    "images/img6.png", "images/img7.png", "images/img8.png"
];

function createBoard() {
    elements = [];
    revealedCells = [];
    matchedPairs = 0;
    canSelect = true;

    let board = document.getElementById("board");
    board.innerHTML = ""; 

    let values = generateShuffledPairs(size * size / 2);
    elements = values.slice();

    let index = 0;
    for (let i = 0; i < size; i++) {
        let row = board.insertRow();
        for (let j = 0; j < size; j++) {
            let cell = row.insertCell();
            cell.dataset.index = index;
            cell.onclick = () => revealCell(cell);

            if (useImages) {
                let hiddenImg = document.createElement("img");
                hiddenImg.src = "images/hidden.png"; 
                hiddenImg.classList.add("hidden");
                cell.appendChild(hiddenImg);
            } else {
                cell.textContent = ""; 
                cell.style.textAlign = "center";
                cell.style.fontSize = "24px";
                cell.style.backgroundColor = "lightgray";
            }
            index++;
        }
    }
}

function generateShuffledPairs(pairs) {
    let array = [];
    if (useImages) {
        for (let i = 0; i < pairs; i++) {
            array.push(imagePaths[i], imagePaths[i]);
        }
    } else {
        for (let i = 1; i <= pairs; i++) {
            array.push(i, i);
        }
    }
    return array.sort(() => Math.random() - 0.5);
}


function revealCell(cell) {
    if (!canSelect || revealedCells.length >= 2 || cell.classList.contains("matched") || 
        (useImages && !cell.firstChild.src.endsWith("hidden.png")) || 
        (!useImages && cell.textContent !== "")) {
        return;
    }

    let index = cell.dataset.index;

    if (useImages) {
        cell.firstChild.src = elements[index];  
    } else {
        cell.textContent = elements[index];  
        cell.style.backgroundColor = "white";
    }

    revealedCells.push(cell);

    if (revealedCells.length === 2) {
        checkMatch();
    }
}

function checkMatch() {
    canSelect = false;

    let [cell1, cell2] = revealedCells;
    let match = useImages 
        ? cell1.firstChild.src === cell2.firstChild.src 
        : cell1.textContent === cell2.textContent;

    if (match) {
        cell1.classList.add("matched");
        cell2.classList.add("matched");
        matchedPairs++;
        checkWin();
        canSelect = true;
    } else {
        cell1.classList.add("incorrect");
        cell2.classList.add("incorrect");
        setTimeout(() => {
            cell1.classList.remove("incorrect");
            cell2.classList.remove("incorrect");

            if (useImages) {
                cell1.firstChild.src = "images/hidden.png";
                cell2.firstChild.src = "images/hidden.png";
            } else {
                cell1.textContent = "";
                cell2.textContent = "";
                cell1.style.backgroundColor = "lightgray";
                cell2.style.backgroundColor = "lightgray";
            }

            canSelect = true;
        }, 1000);
    }
    revealedCells = [];
}

function checkWin() {
    if (matchedPairs === elements.length / 2) {
        setTimeout(() => {
            alert("Congratulations! You won!");
            createBoard(); 
        }, 500);
    }
}

function toggleMode() {
    useImages = !useImages;
    createBoard(); 
}

createBoard();
