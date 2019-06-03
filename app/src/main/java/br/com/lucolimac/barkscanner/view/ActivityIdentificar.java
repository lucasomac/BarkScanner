package br.com.lucolimac.barkscanner.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.lucolimac.barkscanner.R;

public class ActivityIdentificar extends AppCompatActivity {
    private TextView em_breve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identificar);
        em_breve = findViewById(R.id.msg_em_breve);
        em_breve.setText(R.string.em_breve);
    }
}
