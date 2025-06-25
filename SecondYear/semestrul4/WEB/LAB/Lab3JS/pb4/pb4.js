let sortDirections = {};

function sortTableVertical(rowIndex) {
    let table = document.getElementById("vertical-table");
    let rows = Array.from(table.rows);
    
    let colCount = rows[0].cells.length - 1;
    
    let values = [];
    for (let col = 1; col <= colCount; col++) {
        values.push({ 
            colIndex: col, 
            value: rows[rowIndex].cells[col].textContent 
        });
    }

    let isNumeric = values.every(item => !isNaN(item.value));
    let direction = sortDirections[rowIndex] = !(sortDirections[rowIndex] || false);

    values.sort((a, b) => {
        return isNumeric
            ? (direction ? a.value - b.value : b.value - a.value)
            : (direction ? a.value.localeCompare(b.value) : b.value.localeCompare(a.value));
    });

    rows.forEach(row => {
        let newRow = [row.cells[0].textContent]; 
        values.forEach(({ colIndex }) => {
            newRow.push(row.cells[colIndex].textContent);
        });

        for (let col = 1; col <= colCount; col++) {
            row.cells[col].textContent = newRow[col];
        }
    });
}

function sortTableHorizontal(columnIndex) {
    let table = document.getElementById("horizontal-table");
    let rows = Array.from(table.rows).slice(1);
    
    let isNumeric = rows.every(row => !isNaN(row.cells[columnIndex].textContent));
    let direction = sortDirections[columnIndex] = !(sortDirections[columnIndex] || false);
    
    rows.sort((a, b) => {
        let valA = a.cells[columnIndex].textContent;
        let valB = b.cells[columnIndex].textContent;
        return isNumeric 
            ? (direction ? valA - valB : valB - valA) 
            : (direction ? valA.localeCompare(valB) : valB.localeCompare(valA));
    });
    
    rows.forEach(row => table.appendChild(row));
}
