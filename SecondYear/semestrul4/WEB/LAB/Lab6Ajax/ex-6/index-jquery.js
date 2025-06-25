const $tableContent = $('#table-content');

const filters = {
    manufacturer: $('#manufacturer'),
    cpu: $('#cpu'),
    ram: $('#ram'),
    gpu: $('#gpu'),
    persistentMemory: $('#persistentMemory'),
};

loadFilterOptions();
fetchData();

$.each(filters, function (_, $select) {
    $select.on('change', fetchData);
});

function loadFilterOptions() {
    $.getJSON('get-filters.php', function (data) {
        populateSelect(filters.manufacturer, data.manufacturer);
        populateSelect(filters.cpu, data.cpu);
        populateSelect(filters.ram, data.ram);
        populateSelect(filters.gpu, data.gpu);
        populateSelect(filters.persistentMemory, data.persistent_memory);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.error('Error loading filter options:', textStatus, errorThrown);
    });
}

function populateSelect($select, options) {
    $select.find('option:not(:first)').remove();
    $.each(options, function (_, opt) {
        $select.append($('<option>', {
            value: opt,
            text: opt
        }));
    });
}

function fetchData() {
    const selectedFilters = {};
    $.each(filters, function (key, $select) {
        const val = $select.val();
        if (val) {
            selectedFilters[key] = val;
        }
    });

    $.ajax({
        url: 'filter-laptops.php',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(selectedFilters),
        dataType: 'json',
        success: function (data) {
            renderTable(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error('Error fetching data:', textStatus, errorThrown);
        }
    });
}

function renderTable(data) {
    $tableContent.empty();

    if (!data.length) {
        $tableContent.append(`
            <tr>
                <td colspan="5" style="text-align: center;">No results found.</td>
            </tr>
        `);
        return;
    }

    $.each(data, function (_, element) {
        $tableContent.append(`
            <tr>
                <td>${element.manufacturer}</td>
                <td>${element.cpu}</td>
                <td>${element.ram}</td>
                <td>${element.gpu}</td>
                <td>${element.persistent_memory}</td>
            </tr>
        `);
    });
}