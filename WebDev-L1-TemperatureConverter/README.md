# 🌡️ Temperature Converter

## OIBSIP Web Development Internship — Task 3

A responsive temperature conversion website built using **HTML5, CSS3, and Vanilla JavaScript**.

The application allows users to enter a temperature, select the input unit, and instantly convert the value into **Celsius, Fahrenheit, and Kelvin**.

---

## 📌 Project Overview

The Temperature Converter is an interactive web tool designed to demonstrate fundamental frontend development and JavaScript skills.

Users can:

- Enter a temperature value
- Select Celsius, Fahrenheit, or Kelvin as the input unit
- Convert the temperature
- View the equivalent values in all three units
- Receive validation messages for invalid input
- Receive an error when a temperature is below absolute zero

---

## 🛠️ Technologies Used

- **HTML5** — Structure and semantic markup
- **CSS3** — Styling, responsive design, Flexbox, Grid
- **JavaScript** — Temperature conversion and input validation

No frameworks or external JavaScript libraries are used.

---

## ✨ Features

### Temperature Input

Users can enter a numeric temperature value.

The application validates the input and displays an error message if the value is missing or invalid.

### Unit Selection

The user can select the input temperature unit:

- Celsius (°C)
- Fahrenheit (°F)
- Kelvin (K)

### Temperature Conversion

After clicking the **Convert Temperature** button, the application displays the converted value in all three units.

### Input Validation

The application handles:

- Empty input
- Invalid numeric values
- Temperatures below absolute zero

### Absolute Zero Protection

The application prevents physically invalid temperatures below absolute zero.

Absolute zero is:

```text
-273.15 °C
-459.67 °F
0 K