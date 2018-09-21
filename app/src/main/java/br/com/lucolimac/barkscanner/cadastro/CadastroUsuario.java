package br.com.lucolimac.barkscanner.cadastro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import br.com.lucolimac.barkscanner.R;

public class CadastroUsuario extends AppCompatActivity {
    private Spinner spinUF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        spinUF = findViewById(R.id.spinUF);
        //String uf_br[] = getResources().getStringArray(R.array.lista_uf);
        spinUF.setAdapter(ArrayAdapter.createFromResource(this, R.array.lista_uf, R.layout.support_simple_spinner_dropdown_item));
        spinUF.setSelection(24);
    }
}
