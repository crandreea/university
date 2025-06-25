function validateForm() {
    let name = document.getElementById("name");
    let birthDate = document.getElementById("birth-date");
    let age = document.getElementById("age");
    let email = document.getElementById("email");
    let message = document.getElementById("message");

    let errors = [];

    if (name.value.trim() === "" || name.value.length < 4) {
        errors.push("'Name'")
        name.classList.add("error");
    }
    

    if (birthDate.value.trim() === "") {
        errors.push("'Birth Date'")
        birthDate.classList.add("error");
    }

    let birthYear = new Date(birthDate.value).getFullYear();
    let currentYear = new Date().getFullYear();
    let realAge = currentYear - birthYear;

    if (age.value === "" || age.value < 0 || age.value != realAge) {
        errors.push("'Age'");
        age.classList.add("error");
    }

    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (email.value.trim() === "" || !emailPattern.test(email.value)) {
        errors.push("'Email'");
        email.classList.add("error");
    }

    if (errors.length === 0) {
        message.style.color = "green";
        message.textContent = "Datele sunt completate corect";
    } else {
        message.style.color = "red";
        message.textContent = "Campurile " + errors.join(", ") + " nu sunt completate corect";
    }
}
