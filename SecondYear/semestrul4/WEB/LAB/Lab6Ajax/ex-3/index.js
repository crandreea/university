const form = document.getElementById('form-contact');
const btnSave = document.getElementById('btn-save');
const selectIds = document.getElementById('select-contact-id');
const inputLastName = document.getElementById('input-last-name');
const inputFirstName = document.getElementById('input-first-name');
const inputPhone = document.getElementById('input-phone');
const inputEmail = document.getElementById('input-email');

let originalData = {};
let formChanged = false;

function loadContactsIds() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'getContactsIds.php', true);
    xhr.onload = function() {
        if (this.status === 200) {
            const response = JSON.parse(this.responseText);

            response.ids.forEach(id => {
                const option = document.createElement('option');
                option.value = id;
                option.text = id;
                selectIds.add(option);
            });

            if (selectIds.options.length > 0) {
                selectIds.selectedIndex = 0;
                loadContactData(selectIds.value);
            }
        }
    };
    xhr.send();
}

function loadContactData(id) {
    if (formChanged) {
        const confirmLeave = confirm('Changes not saved. Save before switching?');
        if (confirmLeave) {
            saveContact(null, () => loadContactData(select.value));
            return;
        }
    }

    if (!id || id === "Select an option...") {
        inputLastName.value = '';
        inputFirstName.value = '';
        inputPhone.value = '';
        inputEmail.value = '';
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'getContactById.php?id=' + encodeURIComponent(id), true);
    xhr.onload = function() {
        if (this.status === 200) {
            const response = JSON.parse(this.responseText);
            console.log(response);

            inputLastName.value = response.last_name;
            inputFirstName.value = response.first_name;
            inputPhone.value = response.phone;
            inputEmail.value = response.email;
        }
    };
    xhr.send();
}

function saveContact(event, callback = null) {
    if (event) event.preventDefault();

    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'saveContact.php', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onload = function() {
        if (xhr.status === 200) {
            const response = JSON.parse(xhr.responseText);

            if (response.success) {
                alert('Contact saved successfully!');

                inputLastName.value = '';
                inputFirstName.value = '';
                inputPhone.value = '';
                inputEmail.value = '';

                formChanged = false;
                btnSave.disabled = true;

                if (callback) callback();
            } else {
                alert('Error saving contact: ' + response.error);
            }
        } else {
            console.error('Server error: ' + xhr.status);
        }
    };

    const data = `id=${encodeURIComponent(selectIds.value)}&` +
        `last_name=${encodeURIComponent(inputLastName.value)}&` +
        `first_name=${encodeURIComponent(inputFirstName.value)}&` +
        `phone=${encodeURIComponent(inputPhone.value)}&` +
        `email=${encodeURIComponent(inputEmail.value)}`;

    xhr.send(data);
}    

function onFormChange() {
    formChanged = (
        inputLastName.value !== originalData.last_name ||
        inputFirstName.value !== originalData.first_name ||
        inputPhone.value !== originalData.phone ||
        inputEmail.value !== originalData.email
    );
    btnSave.disabled = !formChanged;
  }

window.onload = () => {
    loadContactsIds();

    selectIds.addEventListener('change', function () {
        loadContactData(this.value);
    });
    
    const inputs = document.querySelectorAll('#form-contact input');
    inputs.forEach(input => {
        input.addEventListener('input', onFormChange);
    });
    
    btnSave.addEventListener('click', saveContact);
};