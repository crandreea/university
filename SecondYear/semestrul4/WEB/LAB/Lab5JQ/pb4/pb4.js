let sortDirections = {};

function sortTableVertical(rowIndex) {
    let $table = $("#vertical-table");
    let $rows = $table.find("tr");
    let colCount = $rows.first().find("td, th").length - 1;

    let values = [];
    for (let col = 1; col <= colCount; col++) {
        let cellValue = $rows.eq(rowIndex).find("td, th").eq(col).text().trim();
        values.push({
            colIndex: col,
            value: cellValue
        });
    }

    let isNumeric = values.every(item => !isNaN(item.value));
    let direction = sortDirections[rowIndex] = !(sortDirections[rowIndex] || false);

    values.sort((a, b) => {
        return isNumeric
            ? (direction ? a.value - b.value : b.value - a.value)
            : (direction ? a.value.localeCompare(b.value) : b.value.localeCompare(a.value));
    });

    $rows.each(function () {
        let $cells = $(this).find("td, th");
        let newRow = [$cells.eq(0).text()];
        values.forEach(({ colIndex }) => {
            newRow.push($cells.eq(colIndex).text());
        });

        for (let col = 1; col <= colCount; col++) {
            $cells.eq(col).text(newRow[col]);
        }
    });
}

function sortTableHorizontal(columnIndex) {
    let $table = $("#horizontal-table");
    let $rows = $table.find("tr").slice(1);

    let isNumeric = $rows.toArray().every(row => !isNaN($(row).find("td").eq(columnIndex).text().trim()));
    let direction = sortDirections[columnIndex] = !(sortDirections[columnIndex] || false);

    let sortedRows = $rows.toArray().sort((a, b) => {
        let valA = $(a).find("td").eq(columnIndex).text().trim();
        let valB = $(b).find("td").eq(columnIndex).text().trim();

        return isNumeric
            ? (direction ? valA - valB : valB - valA)
            : (direction ? valA.localeCompare(valB) : valB.localeCompare(valA));
    });

    $table.append(sortedRows);
}


// let sortDirections = {};

// function sortTableVertical(rowIndex) {
//     let table = document.getElementById("vertical-table");
//     let rows = Array.from(table.rows);
    
//     let colCount = rows[0].cells.length - 1;
    
//     let values = [];
//     for (let col = 1; col <= colCount; col++) {
//         values.push({ 
//             colIndex: col, 
//             value: rows[rowIndex].cells[col].textContent 
//         });
//     }

//     let isNumeric = values.every(item => !isNaN(item.value));
//     let direction = sortDirections[rowIndex] = !(sortDirections[rowIndex] || false);

//     values.sort((a, b) => {
//         return isNumeric
//             ? (direction ? a.value - b.value : b.value - a.value)
//             : (direction ? a.value.localeCompare(b.value) : b.value.localeCompare(a.value));
//     });

//     rows.forEach(row => {
//         let newRow = [row.cells[0].textContent]; 
//         values.forEach(({ colIndex }) => {
//             newRow.push(row.cells[colIndex].textContent);
//         });

//         for (let col = 1; col <= colCount; col++) {
//             row.cells[col].textContent = newRow[col];
//         }
//     });
// }

// function sortTableHorizontal(columnIndex) {
//     let table = document.getElementById("horizontal-table");
//     let rows = Array.from(table.rows).slice(1);
    
//     let isNumeric = rows.every(row => !isNaN(row.cells[columnIndex].textContent));
//     let direction = sortDirections[columnIndex] = !(sortDirections[columnIndex] || false);
    
//     rows.sort((a, b) => {
//         let valA = a.cells[columnIndex].textContent;
//         let valB = b.cells[columnIndex].textContent;
//         return isNumeric 
//             ? (direction ? valA - valB : valB - valA) 
//             : (direction ? valA.localeCompare(valB) : valB.localeCompare(valA));
//     });
    
//     rows.forEach(row => table.appendChild(row));
// }
