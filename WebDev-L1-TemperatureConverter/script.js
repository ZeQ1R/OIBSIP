const form = document.getElementById("converterForm");

const temperatureInput =
    document.getElementById("temperature");

const inputUnit =
    document.getElementById("inputUnit");

const inputError =
    document.getElementById("inputError");

const results =
    document.getElementById("results");

const absoluteZeroError =
    document.getElementById("absoluteZeroError");

const celsiusResult =
    document.getElementById("celsiusResult");

const fahrenheitResult =
    document.getElementById("fahrenheitResult");

const kelvinResult =
    document.getElementById("kelvinResult");


form.addEventListener("submit", function (event) {

    event.preventDefault();

    inputError.textContent = "";
    results.classList.add("hidden");
    absoluteZeroError.classList.add("hidden");


    const value = temperatureInput.value.trim();

    const unit = inputUnit.value;

    if (value === "") {

        inputError.textContent =
            "Please enter a temperature.";

        return;
    }


    const temperature = Number(value);


    if (!Number.isFinite(temperature)) {

        inputError.textContent =
            "Please enter a valid numeric temperature.";

        return;
    }

    let celsius;


    if (unit === "celsius") {

        celsius = temperature;

    } else if (unit === "fahrenheit") {

        celsius = (temperature - 32) * 5 / 9;

    } else if (unit === "kelvin") {

        celsius = temperature - 273.15;
    }

    if (celsius < -273.15) {

        absoluteZeroError.classList.remove("hidden");

        return;
    }

    const fahrenheit =
        (celsius * 9 / 5) + 32;

    const kelvin =
        celsius + 273.15;

    celsiusResult.textContent =
        formatTemperature(celsius);

    fahrenheitResult.textContent =
        formatTemperature(fahrenheit);

    kelvinResult.textContent =
        formatTemperature(kelvin);


    results.classList.remove("hidden");

});

function formatTemperature(value) {

    return Number(value.toFixed(2));
}