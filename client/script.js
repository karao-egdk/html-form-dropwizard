const form = document.getElementById("form");
const nameInput = document.getElementById("name");
const ageInput = document.getElementById("age");
const mobileInput = document.getElementById("mobile");
const emailInput = document.getElementById("email");
const secondaryPhone = document.getElementById("secondary-mobile");
const genderInput = document.querySelectorAll('input[name="gender"]');
const favTechInput = document.getElementById("fav-technology");
const secondaryGroup = document.getElementsByClassName(
    "secondary-phone-container"
);

let isSecondaryRadioChecked = false;

const errors = document.getElementsByClassName("error");
const allErrorElVisibility = () => {
    for (let i = 0; i < errors.length; i++) {
        errors[i].style.display = "none";
    }
};

const errorElVisibility = (elId, value) => {
    const errorEl = document.getElementById(elId);
    errorEl.style.display = value;
};

const getData = () => {
    let gender;
    genderInput.forEach((el) => {
        if (el.checked) gender = el.value;
    });

    const data = {
        name: nameInput.value,
        age: parseInt(ageInput.value),
        mobile: mobileInput.value,
        email: emailInput.value,
        hasSecondaryPhone: isSecondaryRadioChecked,
        secondaryMobile:
            isSecondaryRadioChecked === true ? secondaryPhone.value : null,
        gender: gender.toUpperCase(),
        favTechnology: favTechInput.value.toUpperCase(),
    };

    return data;
};

const addDataLocalStorage = () => {
    const data = getData();
    localStorage.setItem("form-data", JSON.stringify(data));
};

// Validation for each input
const isNameValid = () => {
    const nameRegex = /^[a-z]+$/i;

    const isValid = nameRegex.test(nameInput.value);
    if (!isValid) errorElVisibility("error-name", "block");
    else errorElVisibility("error-name", "none");

    return isValid;
};

const isAgeValid = () => {
    const isValid = ageInput.value > 0;
    if (!isValid) errorElVisibility("error-age", "block");
    else errorElVisibility("error-age", "none");

    return isValid;
};

const isMobileValid = () => {
    const isValid = mobileInput.value.toString().length === 10;
    if (!isValid) errorElVisibility("error-mobile", "block");
    else errorElVisibility("error-mobile", "none");

    return isValid;
};

const isEmailValid = () => {
    const emailRegex = /^[\w\-\.]+@([\w]+\.)[\w]{2,4}(\.[\w]{2,4})*$/g;
    const isValid = emailRegex.test(emailInput.value);
    if (!isValid) errorElVisibility("error-email", "block");
    else errorElVisibility("error-email", "none");

    return isValid;
};

const isSecondaryPhoneValid = () => {
    if (!isSecondaryRadioChecked) return true;
    const isValid = secondaryPhone.value.toString().length === 10;
    if (!isValid) errorElVisibility("error-secondary-mobile", "block");
    else errorElVisibility("error-secondary-mobile", "none");

    return isValid;
};

const isGenderSelected = () => {
    let isValid = false;
    genderInput.forEach((el) => {
        if (el.checked) isValid = true;
    });
    if (!isValid) errorElVisibility("error-gender", "block");
    else errorElVisibility("error-gender", "none");
    return isValid;
};

const isValidForm = () => {
    allErrorElVisibility();
    let isValid = true;
    if (!isNameValid()) isValid = false;
    if (!isAgeValid()) isValid = false;
    if (!isMobileValid()) isValid = false;
    if (!isEmailValid()) isValid = false;
    if (!isSecondaryPhoneValid()) isValid = false;
    if (!isGenderSelected()) isValid = false;
    return isValid;
};

// Handler
const secondaryRadioHandler = (radioBtn) => {
    isSecondaryRadioChecked = radioBtn.value === "yes" ? true : false;
    if (isSecondaryRadioChecked) {
        secondaryGroup[0].style.display = "block";
    } else {
        secondaryGroup[0].style.display = "none";
    }
};

// Event Listener
nameInput.addEventListener("input", () => {
    isNameValid();
});

ageInput.addEventListener("input", () => {
    isAgeValid();
});

mobileInput.addEventListener("input", () => {
    isMobileValid();
});

emailInput.addEventListener("input", () => {
    isEmailValid();
});

secondaryPhone.addEventListener("input", () => {
    if (isSecondaryRadioChecked) isSecondaryPhoneValid();
});

form.addEventListener("submit", (e) => {
    e.preventDefault();
    if (!isValidForm()) return;

    const data = getData();

    fetch("http://localhost:8080/form/add", {
        mode: "cors",
        method: "POST",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => response.json())
        .then((data) => {
            addDataLocalStorage();
            alert(data.message);
            form.reset();
            secondaryGroup[0].style.display = "none";
            isSecondaryRadioChecked = false;
        })
        .catch((err) => {
            console.log(err);
        });
});

allErrorElVisibility();
