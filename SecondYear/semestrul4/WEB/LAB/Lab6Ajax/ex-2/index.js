let offset = 0;
let limit = 3;
let totalRecors = 0;

function loadRecords() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'getContacts.php?offset=' + offset + "&limit=" + limit, true);
    xhr.onload = function() {
        if (this.status == 200) {
            const response = JSON.parse(this.responseText);
            const tableBody = document.getElementById('table-contacts').querySelector('tbody');
            tableBody.innerHTML = '';

            response.records.forEach(record => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${record.last_name}</td>
                    <td>${record.first_name}</td>
                    <td>${record.phone}</td>
                    <td>${record.email}</td>
                `;
                tableBody.appendChild(row);
            });

            totalRecors = response.total;
            updateButtons();
        }
    };
    xhr.send();
}

function nextPage() {
    offset += limit;
    loadRecords();
}

function prevPage() {
    offset = Math.max(0, offset - limit);
    loadRecords();
}

function updateButtons() {
    document.getElementById('btn-prev').disabled = offset === 0;
    document.getElementById('btn-next').disabled = offset + limit >= totalRecors;
}

window.onload = loadRecords;
