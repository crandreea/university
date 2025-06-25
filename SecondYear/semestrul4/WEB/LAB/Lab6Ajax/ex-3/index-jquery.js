let originalData = {};
let formChanged = false;

function loadContactsIds() {
    $.get('getContactsIds.php', function(response) {
        const data = JSON.parse(response);

        data.ids.forEach(id => {
            $('#select-contact-id').append(
                $('<option></option>').val(id).text(id)
            );
        });

        if ($('#select-contact-id option').length > 0) {
            $('#select-contact-id').prop('selectedIndex', 0);
            loadContactData($('#select-contact-id').val());
        }
    });
}

function loadContactData(id) {
    if (formChanged) {
        if (confirm('Attention: Changes not saved!')) {
            saveContact(null, () => loadContactData(id));
            return;
        }
    }

    if (!id || id === "Select an option...") {
        $('#input-last-name, #input-first-name, #input-phone, #input-email').val('');
        return;
    }

    $.get('getContactById.php', { id: id }, function(response) {
        const data = JSON.parse(response);
        console.log(data);

        $('#input-last-name').val(data.last_name);
        $('#input-first-name').val(data.first_name);
        $('#input-phone').val(data.phone);
        $('#input-email').val(data.email);

        originalData = { ...data };
        formChanged = false;
        $('#btn-save').prop('disabled', true);
    });
}

function saveContact(event, callback = null) {
    if (event) event.preventDefault();

    const data = {
        id: $('#select-contact-id').val(),
        last_name: $('#input-last-name').val(),
        first_name: $('#input-first-name').val(),
        phone: $('#input-phone').val(),
        email: $('#input-email').val()
    };

    $.post('saveContact.php', $.param(data), function(response) {
        const res = JSON.parse(response);

        if (res.success) {
            alert('Contact saved successfully!');
            $('#input-last-name, #input-first-name, #input-phone, #input-email').val('');
            formChanged = false;
            $('#btn-save').prop('disabled', true);

            if (callback) callback();
        } else {
            alert('Error saving contact: ' + res.error);
        }
    });
}

function onFormChange() {
    formChanged = (
        $('#input-last-name').val() !== originalData.last_name ||
        $('#input-first-name').val() !== originalData.first_name ||
        $('#input-phone').val() !== originalData.phone ||
        $('#input-email').val() !== originalData.email
    );

    $('#btn-save').prop('disabled', !formChanged);
}

$(document).ready(function () {
    loadContactsIds();

    $('#select-contact-id').on('change', function () {
        loadContactData($(this).val());
    });

    $('#form-contact input').on('input', onFormChange);
    $('#btn-save').on('click', saveContact);
});