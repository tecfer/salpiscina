package es.tecfer.salpiscina;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView helpTextView = findViewById(R.id.help_textview);
        helpTextView.setText(getString(R.string.help_text));
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_about) {
            // Lógica para "Acerca de"
            Toast.makeText(this, getString(R.string.about), Toast.LENGTH_SHORT).show();
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
}