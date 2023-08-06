package es.tecfer.salpiscina;



import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    private EditText ppmInput;
    private EditText metrosCuadradosInput;
    private Button b_calculate, b_copy, b_whatsapp;
    private TextView resultTextView;
    private double rangoConcentracionMin = 4;
    private double rangoConcentracionMax = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ppmInput = findViewById(R.id.ppm_input);
        metrosCuadradosInput = findViewById(R.id.metros_cuadrados_input);
        b_calculate = findViewById(R.id.b_calculate);
        b_copy = findViewById(R.id.b_copy);
        b_whatsapp = findViewById(R.id.b_whatsapp);

        resultTextView = findViewById(R.id.result_text_view);

        metrosCuadradosInput.setText("50");

        b_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateSal();
            }
        });b_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { copyText();}
        });b_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { send_whatsapp(); }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.calc) {
            // Lógica para "Calcular"
            Toast.makeText(this, getString(R.string.calc), Toast.LENGTH_SHORT).show();
            calculateSal();
            return true;
        }else if (id == R.id.action_about) {
            // Lógica para "Acerca de"
            Toast.makeText(this, getString(R.string.visiturl), Toast.LENGTH_SHORT).show();
            String url = getString(R.string.url);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        } else if (id == R.id.action_help) {
            // Lógica para "Ayuda"
            Toast.makeText(this, getString(R.string.help), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void calculateSal() {
        String ppmText = ppmInput.getText().toString();
        String metrosCuadradosText = metrosCuadradosInput.getText().toString();

        // Ocultar el teclado numérico
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(b_calculate.getWindowToken(), 0);

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
                    "Sacos de 25 Kg: " + minSacos + " - " + maxSacos + " Sacos";

            resultTextView.setText(result);
        } catch (NumberFormatException e) {
            resultTextView.setText(getString(R.string.error_calc));
        }
    }

    private void copyText()
    {
        //Copia texto a portapapeles.

        String text = String.valueOf(resultTextView.getText());
        try {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("text",  text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, getString(R.string.copy_msg), Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.copy_msg_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void send_whatsapp()
    {
         // Envia el texto a WhatsApp

        String text =   String.valueOf(resultTextView.getText());

        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
            Toast.makeText(getBaseContext(),getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }
}
