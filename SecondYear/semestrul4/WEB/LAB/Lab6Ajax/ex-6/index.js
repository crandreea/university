const tableContent = document.getElementById('table-content');

const filters = {
    manufacturer: document.getElementById('manufacturer'),
    cpu: document.getElementById('cpu'),
    ram: document.getElementById('ram'),
    gpu: document.getElementById('gpu'),
    persistentMemory: document.getElementById('persistentMemory'),
};

window.onload = () => {
    loadFilterOptions();
    fetchData();

    Object.values(filters).forEach(select => {
        select.addEventListener('change', fetchData);
    });
};

function loadFilterOptions() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'get-filters.php', true);
    xhr.onload = () => {
        if (xhr.status === 200) {
            try {
                const data = JSON.parse(xhr.responseText);
                populateSelect(filters.manufacturer, data.manufacturer);
                populateSelect(filters.cpu, data.cpu);
                populateSelect(filters.ram, data.ram);
                populateSelect(filters.gpu, data.gpu);
                populateSelect(filters.persistentMemory, data.persistent_memory);
            } catch (e) {
                console.error('Error parsing filter options JSON:', e);
            }
        } else {
            console.error('Error loading filter options, status:', xhr.status);
        }
    };
    xhr.onerror = () => console.error('Network error while loading filter options');
    xhr.send();
}

function populateSelect(selectElem, options) {
    while (selectElem.options.length > 1) {
        selectElem.remove(1);
    }

    options.forEach(opt => {
        const option = document.createElement('option');
        option.value = opt;
        option.textContent = opt;
        selectElem.appendChild(option);
    });
}

function fetchData() {
    const selectedFilters = {};
    for (const [key, selectElem] of Object.entries(filters)) {
        if (selectElem.value) {
            selectedFilters[key] = selectElem.value;
        }
    }

    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'filter-laptops.php', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = () => {
        if (xhr.status === 200) {
            try {
                const data = JSON.parse(xhr.responseText);
                renderTable(data);
            } catch (e) {
                console.error('Error parsing data JSON:', e);
            }
        } else {
            console.error('Server error fetching data:', xhr.status);
        }
    };
    xhr.onerror = () => console.error('Network error while fetching data');
    xhr.send(JSON.stringify(selectedFilters));
}

function renderTable(data) {
    tableContent.innerHTML = '';

    if (!data.length) {
        const tr = document.createElement('tr');
        const td = document.createElement('td');
        td.colSpan = 5;
        td.textContent = 'No results found.';
        td.style.textAlign = "center";
        tr.appendChild(td);
        tableContent.appendChild(tr);
        return;
    }

    data.forEach(element => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${element.manufacturer}</td>
            <td>${element.cpu}</td>
            <td>${element.ram}</td>
            <td>${element.gpu}</td>
            <td>${element.persistent_memory}</td>
        `;
        tableContent.appendChild(tr);
    });
}
