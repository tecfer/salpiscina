package es.tecfer.salpiscina;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    private EditText ppmInput;
    private EditText metrosCuadradosInput;
    private Button calculateButton;
    private TextView resultTextView;
    private double rangoConcentracionMin = 4;
    private double rangoConcentracionMax = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ppmInput = findViewById(R.id.ppm_input);
        metrosCuadradosInput = findViewById(R.id.metros_cuadrados_input);
        calculateButton = findViewById(R.id.calculate_button);
        resultTextView = findViewById(R.id.result_text_view);

        metrosCuadradosInput.setText("50");

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSal();
            }
        });
    }

    private void calculateSal() {
        String ppmText = ppmInput.getText().toString();
        String metrosCuadradosText = metrosCuadradosInput.getText().toString();

        // Ocultar el teclado numérico
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(calculateButton.getWindowToken(), 0);

        try {
            double ppmInicial = Double.parseDouble(ppmText);
            double metrosCuadrados = Double.parseDouble(metrosCuadradosText);

            // Convertir ppm a kg/m³
            double salInicial = ppmInicial * 0.001;

            // Cálculo de la cantidad mínima y máxima de sal en kg (redondeado a número entero)
            int minSal = (int) Math.round((rangoConcentracionMin - salInicial) * metrosCuadrados);
            int maxSal = (int) Math.round((rangoConcentracionMax  - salInicial) * metrosCuadrados);

            minSal = Math.max(minSal, 0);
            maxSal = Math.max(maxSal, 0);

            int minSacos = (int) Math.ceil(minSal / 25);
            int maxSacos = (int) Math.ceil(maxSal / 25);

            String result = "La sal a añadir es: " + minSal + " - " + maxSal + " kg\n" +
                    " Sacos de 25 Kg: " + minSacos + " - " + maxSacos + " Sacos";

            resultTextView.setText(result);
        } catch (NumberFormatException e) {
            resultTextView.setText("Error: Los valores ingresados no son válidos. Asegúrate de ingresar números.");
        }
    }
}
