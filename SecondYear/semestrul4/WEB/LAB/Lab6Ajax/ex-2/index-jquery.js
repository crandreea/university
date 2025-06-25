let offset = 0;
let limit = 3;
let totalRecords = 0;
let isLoading = false; 

function loadRecords() {
    if (isLoading) return; 
    isLoading = true; 

    $.ajax({
        type: 'POST',
        url: 'getContacts.php',
        data: $.param({
            offset: offset,
            limit: limit
        }),
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        success: function(response) {
            const $tableBody = $('#table-contacts tbody');
            $tableBody.empty();

            if (response.error) {
                console.error(response.error);
                return;
            }

            if (response.records.length === 0) {
                $tableBody.append(`
                    <tr>
                        <td colspan="4">No more records to display.</td>
                    </tr>
                `);
            } else {
                response.records.forEach(record => {
                    $tableBody.append(`
                        <tr>
                            <td>${record.last_name}</td>
                            <td>${record.first_name}</td>
                            <td>${record.phone}</td>
                            <td>${record.email}</td>
                        </tr>
                    `);
                });
            }

            totalRecords = response.total;
            updateButtons();
        },
        error: function(xhr, status, error) {
            console.log("Request failed: " + error);
        },
        complete: function() {
            isLoading = false; 
        }
    });
}

function nextPage() {
    if (offset + limit < totalRecords) {
        offset += limit;
        loadRecords();
    }
}

function prevPage() {
    if (offset > 0) {
        offset = Math.max(0, offset - limit);
        loadRecords();
    }
}

function updateButtons() {
    $('#btn-prev').prop('disabled', offset === 0);
    $('#btn-next').prop('disabled', offset + limit >= totalRecords);
}

$(document).ready(function() {
    loadRecords();

    $('#btn-next').off('click').on('click', nextPage);
    $('#btn-prev').off('click').on('click', prevPage);
});
