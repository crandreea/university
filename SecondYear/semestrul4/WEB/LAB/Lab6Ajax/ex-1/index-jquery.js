function loadDepartures() {
    $.ajax({
        type: 'GET',
        url: 'getDepartures.php',
        dataType: 'json',
        success: function(departures) {
            const $departuresSelect = $('#departure');
            $departuresSelect.empty();

            if (departures.error) {
                console.error(departures.error);
                return;
            }

            departures.forEach(city => {
                $('<option>', {
                    value: city,
                    text: city
                }).appendTo($departuresSelect);
            });
        },
        error: function(xhr, status, error) {
            console.log("Request failed: ", error);
        }
    });
}

function loadArrivals() {
    const departureCity = $('#departure').val();

    $.ajax({
        type: 'GET',
        url: 'getArrivals.php',
        data: {
            city: departureCity
        },
        dataType: 'json',
        success: function(arrivals) {
            const $arrivalsSelect = $('#arrival');
            $arrivalsSelect.empty();

            if (arrivals.error) {
                console.error(arrivals.error);
                return;
            }

            arrivals.forEach(city => {
                $('<option>', {
                    value: city,
                    text: city
                }).appendTo($arrivalsSelect);
            });
        },
        error: function(xhr, status, error) {
            console.log("Request failed: ", error);
        }
    });
}
