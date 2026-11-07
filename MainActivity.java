
package com.example.begwisejava;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Declare components
    EditText weightInput, rateInput;
    RadioButton economyRadio, businessRadio;
    Button calculateButton;

    TextView excessWeightText;
    TextView excessChargeText;
    TextView serviceTaxText;
    TextView totalText;
    TextView baggageStatusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up Main Toolbar
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        // Hide default title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Open About Page using Flylune header
        View flyluneHeader = findViewById(R.id.flyluneHeader);

        flyluneHeader.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AboutActivity.class
            );
            startActivity(intent);
        });

        // Connect Java with XML
        weightInput = findViewById(R.id.weightInput);
        rateInput = findViewById(R.id.rateInput);

        economyRadio = findViewById(R.id.economyRadio);
        businessRadio = findViewById(R.id.businessRadio);

        calculateButton = findViewById(R.id.calculateButton);

        excessWeightText = findViewById(R.id.excessWeightText);
        excessChargeText = findViewById(R.id.excessChargeText);
        serviceTaxText = findViewById(R.id.serviceTaxText);
        totalText = findViewById(R.id.totalText);
        baggageStatusText = findViewById(R.id.baggageStatusText);

        // Calculate button action
        calculateButton.setOnClickListener(v -> {
            calculateFee();
        });

        // Open About Page using About button
        Button aboutButton = findViewById(R.id.aboutButton);

        aboutButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AboutActivity.class
            );
            startActivity(intent);
        });
    }

    // Calculate excess baggage fee
    private void calculateFee() {

        // Get input values
        String weightValue = weightInput.getText().toString().trim();
        String rateValue = rateInput.getText().toString().trim();

        // Check empty input
        if (weightValue.isEmpty() || rateValue.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please enter all values",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        double weight;
        double rate;

        // Convert input to numbers
        try {
            weight = Double.parseDouble(weightValue);
            rate = Double.parseDouble(rateValue);

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Please enter valid numbers",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // Check invalid values
        if (Double.isNaN(weight) ||
                Double.isInfinite(weight) ||
                Double.isNaN(rate) ||
                Double.isInfinite(rate) ||
                weight < 0 || rate < 0) {

            Toast.makeText(
                    this,
                    "Please enter valid non-negative values",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // Set baggage allowance
        double allowance;

        if (economyRadio.isChecked()) {
            allowance = 20;
        } else if (businessRadio.isChecked()) {
            allowance = 40;
        } else {
            Toast.makeText(
                    this,
                    "Please select a ticket class",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // Calculate excess baggage weight
        double excessWeight = weight - allowance;

        if (excessWeight < 0) {
            excessWeight = 0;
        }

        // Calculate excess baggage charge
        double excessCharge = excessWeight * rate;

        // Calculate 8% service tax
        double serviceTax = excessCharge * 0.08;

        // Calculate total payable
        double totalPayable = excessCharge + serviceTax;

        // Check calculation result
        if (Double.isNaN(totalPayable) ||
                Double.isInfinite(totalPayable)) {

            Toast.makeText(
                    this,
                    "Amount is too large",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // Display excess weight
        excessWeightText.setText(String.format(
                Locale.US,
                "Excess Weight: %.2f kg",
                excessWeight
        ));

        // Display excess charge
        excessChargeText.setText(String.format(
                Locale.US,
                "Excess Charge: RM %.2f",
                excessCharge
        ));

        // Display service tax
        serviceTaxText.setText(String.format(
                Locale.US,
                "Service Tax (8%%): RM %.2f",
                serviceTax
        ));

        // Display total payable
        totalText.setText(String.format(
                Locale.US,
                "Total Payable: RM %.2f",
                totalPayable
        ));

        // Display helpful baggage status
        baggageStatusText.setVisibility(View.VISIBLE);

        if (excessWeight == 0) {

            baggageStatusText.setText(
                    "✓ Your baggage is within the free allowance. " +
                            "No additional charges apply."
            );

            baggageStatusText.setTextColor(
                    android.graphics.Color.rgb(46, 125, 50)
            );

        } else {

            baggageStatusText.setText(
                    "✈ Your baggage exceeds the free allowance by " +
                            String.format(Locale.US, "%.2f", excessWeight) +
                            " kg. Additional charges apply."
            );

            baggageStatusText.setTextColor(
                    android.graphics.Color.rgb(103, 83, 170)
            );
        }
    }

    // Display Share button in Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(
                R.menu.main_menu,
                menu
        );

        return true;
    }

    // Share button action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_share) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");

            shareIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Flylune Application"
            );

            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out Flylune - Excess Baggage Calculator!\n" +
                            "https://github.com/luvnaa/FlyluneJava"
            );

            startActivity(Intent.createChooser(
                    shareIntent,
                    "Share Flylune Using"
            ));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
