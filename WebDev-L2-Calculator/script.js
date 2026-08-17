

const display = document.getElementById("display");
const expression = document.getElementById("expression");

const numberButtons =
    document.querySelectorAll("[data-number]");

const operatorButtons =
    document.querySelectorAll("[data-operator]");

const clearButton =
    document.querySelector('[data-action="clear"]');

const deleteButton =
    document.querySelector('[data-action="delete"]');

const decimalButton =
    document.querySelector('[data-action="decimal"]');

const equalsButton =
    document.querySelector('[data-action="equals"]');


let currentValue = "0";
let previousValue = null;
let currentOperator = null;

let waitingForOperand = false;
let expressionText = "";



function updateDisplay() {

    display.textContent = currentValue;

    expression.textContent = expressionText;
}

function enterNumber(number) {


    if (waitingForOperand) {

        currentValue = number;

        waitingForOperand = false;

    } else if (currentValue === "0") {

        currentValue = number;

    } else {

        currentValue += number;
    }

    updateDisplay();
}


function enterDecimal() {

    if (waitingForOperand) {

        currentValue = "0.";
        waitingForOperand = false;

        updateDisplay();

        return;
    }


    if (currentValue.includes(".")) {

        return;
    }


    currentValue += ".";

    updateDisplay();
}


function chooseOperator(operator) {

    const inputValue = parseFloat(currentValue);


    if (!Number.isFinite(inputValue)) {

        showError("Invalid input");

        return;
    }


    if (
        currentOperator !== null &&
        waitingForOperand
    ) {

        currentOperator = operator;

        expressionText =
            `${formatNumber(previousValue)} ${getOperatorSymbol(operator)}`;

        updateDisplay();

        return;
    }


    if (previousValue === null) {

        previousValue = inputValue;

    } else if (currentOperator !== null) {

        const result = calculate(
            previousValue,
            inputValue,
            currentOperator
        );


        if (result === null) {

            return;
        }


        currentValue = formatNumber(result);

        previousValue = result;
    }


    currentOperator = operator;

    waitingForOperand = true;


    expressionText =
        `${formatNumber(previousValue)} ${getOperatorSymbol(operator)}`;

    updateDisplay();
}

function calculate(first, second, operator) {

    switch (operator) {

        case "+":
            return first + second;

        case "-":
            return first - second;

        case "*":
            return first * second;

        case "/":

            if (second === 0) {

                showError("Cannot divide by zero");

                return null;
            }

            return first / second;

        default:
            return second;
    }
}


function performCalculation() {

    if (
        currentOperator === null ||
        previousValue === null
    ) {

        return;
    }


    const secondValue =
        parseFloat(currentValue);


    if (!Number.isFinite(secondValue)) {

        showError("Invalid input");

        return;
    }


    const result = calculate(
        previousValue,
        secondValue,
        currentOperator
    );


    if (result === null) {

        return;
    }


    expressionText =
        `${formatNumber(previousValue)}
        ${getOperatorSymbol(currentOperator)}
        ${formatNumber(secondValue)} =`;


    currentValue = formatNumber(result);

    previousValue = null;
    currentOperator = null;

    waitingForOperand = true;

    updateDisplay();
}

function clearCalculator() {

    currentValue = "0";

    previousValue = null;

    currentOperator = null;

    waitingForOperand = false;

    expressionText = "";

    updateDisplay();
}

function deleteLastCharacter() {

    if (waitingForOperand) {

        return;
    }


    if (
        currentValue.length <= 1 ||
        (
            currentValue.length === 2 &&
            currentValue.startsWith("-")
        )
    ) {

        currentValue = "0";

    } else {

        currentValue =
            currentValue.slice(0, -1);
    }


    updateDisplay();
}

function formatNumber(number) {

    // Avoid extremely long decimal results
    return Number(number.toFixed(10)).toString();
}




function getOperatorSymbol(operator) {

    const symbols = {
        "+": "+",
        "-": "−",
        "*": "×",
        "/": "÷"
    };

    return symbols[operator];
}




function showError(message) {

    expressionText = "ERROR";

    currentValue = message;

    previousValue = null;

    currentOperator = null;

    waitingForOperand = true;

    updateDisplay();
}



numberButtons.forEach(button => {

    button.addEventListener("click", () => {

        enterNumber(
            button.dataset.number
        );

    });

});



operatorButtons.forEach(button => {

    button.addEventListener("click", () => {

        chooseOperator(
            button.dataset.operator
        );

    });

});



decimalButton.addEventListener(
    "click",
    enterDecimal
);


clearButton.addEventListener(
    "click",
    clearCalculator
);


deleteButton.addEventListener(
    "click",
    deleteLastCharacter
);


// Equals
equalsButton.addEventListener(
    "click",
    performCalculation
);


document.addEventListener("keydown", event => {

    const key = event.key;


  
    if (/^[0-9]$/.test(key)) {

        enterNumber(key);

        return;
    }



    if (key === ".") {

        enterDecimal();

        return;
    }



    if (
        key === "+" ||
        key === "-" ||
        key === "*" ||
        key === "/"
    ) {

        chooseOperator(key);

        return;
    }


    if (
        key === "Enter" ||
        key === "="
    ) {

        event.preventDefault();

        performCalculation();

        return;
    }



    if (key === "Backspace") {

        deleteLastCharacter();

        return;
    }



    if (key === "Escape") {

        clearCalculator();
    }

});



updateDisplay();