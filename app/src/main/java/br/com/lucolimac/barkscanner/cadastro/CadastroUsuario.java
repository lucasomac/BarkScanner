package br.com.lucolimac.barkscanner.cadastro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import br.com.lucolimac.barkscanner.R;

public class CadastroUsuario extends AppCompatActivity {
    private Spinner spinnerUf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        spinnerUf = findViewById(R.id.uf);
        String ufs[] = getResources().getStringArray(R.array.lista_uf);
        spinnerUf.setAdapter(new ArrayAdapter<String>(getBaseContext(), R.layout.activity_cadastro_usuario, ufs));
    }
}
