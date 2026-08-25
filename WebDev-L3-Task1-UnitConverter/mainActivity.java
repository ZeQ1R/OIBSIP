package com.example.unitconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private Spinner sourceSpinner;
    private Spinner targetSpinner;

    private EditText valueInput;
    private TextView resultText;
    private Button convertButton;

    private final String[] categories = {
            "Length",
            "Weight",
            "Temperature"
    };

    private final Map<String, String[]> units =
            new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        categorySpinner =
                findViewById(R.id.categorySpinner);

        sourceSpinner =
                findViewById(R.id.sourceSpinner);

        targetSpinner =
                findViewById(R.id.targetSpinner);

        valueInput =
                findViewById(R.id.valueInput);

        resultText =
                findViewById(R.id.resultText);

        convertButton =
                findViewById(R.id.convertButton);

        setupUnits();
        setupCategorySpinner();

        convertButton.setOnClickListener(
                view -> convertValue()
        );
    }

    private void setupUnits() {

        units.put(
                "Length",
                new String[]{
                        "Centimetres",
                        "Metres",
                        "Kilometres",
                        "Inches",
                        "Feet",
                        "Miles"
                }
        );

        units.put(
                "Weight",
                new String[]{
                        "Grams",
                        "Kilograms",
                        "Pounds",
                        "Ounces"
                }
        );

        units.put(
                "Temperature",
                new String[]{
                        "Celsius",
                        "Fahrenheit",
                        "Kelvin"
                }
        );
    }

    private void setupCategorySpinner() {

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        categories
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id
                    ) {
                        updateUnitSpinners(
                                categories[position]
                        );
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent
                    ) {
                    }
                }
        );
    }

    private void updateUnitSpinners(
            String category
    ) {

        String[] categoryUnits =
                units.get(category);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        categoryUnits
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        sourceSpinner.setAdapter(adapter);
        targetSpinner.setAdapter(adapter);

        resultText.setText(
                "Your result will appear here"
        );
    }

    private void convertValue() {

        String input =
                valueInput.getText()
                        .toString()
                        .trim();

        if (input.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please enter a value",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        double value;

        try {

            value = Double.parseDouble(input);

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Please enter a valid number",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String category =
                categorySpinner
                        .getSelectedItem()
                        .toString();

        String source =
                sourceSpinner
                        .getSelectedItem()
                        .toString();

        String target =
                targetSpinner
                        .getSelectedItem()
                        .toString();

        double result;

        switch (category) {

            case "Length":
                result = convertLength(
                        value,
                        source,
                        target
                );
                break;

            case "Weight":
                result = convertWeight(
                        value,
                        source,
                        target
                );
                break;

            case "Temperature":
                result = convertTemperature(
                        value,
                        source,
                        target
                );
                break;

            default:
                return;
        }

        resultText.setText(
                String.format(
                        "%.2f %s",
                        result,
                        target
                )
        );
    }

    private double convertLength(
            double value,
            String source,
            String target
    ) {

        double metres;

        switch (source) {

            case "Centimetres":
                metres = value / 100;
                break;

            case "Metres":
                metres = value;
                break;

            case "Kilometres":
                metres = value * 1000;
                break;

            case "Inches":
                metres = value * 0.0254;
                break;

            case "Feet":
                metres = value * 0.3048;
                break;

            case "Miles":
                metres = value * 1609.344;
                break;

            default:
                metres = value;
        }

        switch (target) {

            case "Centimetres":
                return metres * 100;

            case "Metres":
                return metres;

            case "Kilometres":
                return metres / 1000;

            case "Inches":
                return metres / 0.0254;

            case "Feet":
                return metres / 0.3048;

            case "Miles":
                return metres / 1609.344;

            default:
                return metres;
        }
    }

    private double convertWeight(
            double value,
            String source,
            String target
    ) {

        double grams;

        switch (source) {

            case "Grams":
                grams = value;
                break;

            case "Kilograms":
                grams = value * 1000;
                break;

            case "Pounds":
                grams = value * 453.59237;
                break;

            case "Ounces":
                grams = value * 28.349523125;
                break;

            default:
                grams = value;
        }

        switch (target) {

            case "Grams":
                return grams;

            case "Kilograms":
                return grams / 1000;

            case "Pounds":
                return grams / 453.59237;

            case "Ounces":
                return grams / 28.349523125;

            default:
                return grams;
        }
    }

    private double convertTemperature(
            double value,
            String source,
            String target
    ) {

        double celsius;

        switch (source) {

            case "Celsius":
                celsius = value;
                break;

            case "Fahrenheit":
                celsius =
                        (value - 32) * 5 / 9;
                break;

            case "Kelvin":
                celsius =
                        value - 273.15;
                break;

            default:
                celsius = value;
        }

        switch (target) {

            case "Celsius":
                return celsius;

            case "Fahrenheit":
                return (celsius * 9 / 5) + 32;

            case "Kelvin":
                return celsius + 273.15;

            default:
                return celsius;
        }
    }
}