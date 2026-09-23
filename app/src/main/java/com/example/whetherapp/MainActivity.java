package com.example.whetherapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText textBox = findViewById(R.id.editTextText3);
        DisplayInput displayInput = new DisplayInput();
        textBox.setText(displayInput.recomendation);// ändrar så klassen DisplayInput används.

        EditText textBox2 = findViewById(R.id.editTextText4);
        DisplayInput displayInput2 = new DisplayInput();
        textBox2.setText(displayInput2.Weather);// ändrar så klassen DisplayInput används.
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
