function loadDepartures() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'getDepartures.php', true);

    xhr.onload = function() {
        try {
            const departures = JSON.parse(this.responseText);

            const departuresSelect = document.getElementById('departure');
            departuresSelect.innerHTML = '';

            if (departuresSelect.error) {
                console.log(departures.error);
                return;
            }

            departures.forEach(city => {
                const option = document.createElement('option');
                option.text = city;
                option.value = city;
                departuresSelect.add(option);
            });
        } catch (e) {
            console.error("Failed to parse JSON:", e, "Response:", this.responseText);
        }
    }

    xhr.onerror = function() {
        console.error("Request failed. Please check your network or server.");
    };
    
    xhr.send();
}

function loadArrivals() {
    const departureCity = document.getElementById('departure').value;
    
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'getArrivals.php?city=' + departureCity, true);
    
    xhr.onload = function() {
        try {
            const arrivals = JSON.parse(this.responseText);
            
            const arrivalsSelect = document.getElementById('arrival');
            arrivalsSelect.innerHTML = '';
            
            if (arrivals.error) {
                console.error(arrivals.error); 
                return;
            }
            
            arrivals.forEach(city => {
                const option = document.createElement('option');
                option.text = city;
                arrivalsSelect.add(option);
            });
        } catch (e) {
            console.error("Failed to parse JSON:", e, "Response:", this.responseText);
        }
    }
    
    xhr.onerror = function() {
        console.error("Request failed. Please check your network or server.");
    };
    
    xhr.send();
}
