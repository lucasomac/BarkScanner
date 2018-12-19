package br.com.lucolimac.barkscanner.cadastro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import br.com.lucolimac.barkscanner.MainActivity;
import br.com.lucolimac.barkscanner.R;
import br.com.lucolimac.barkscanner.model.Cachorro;
import br.com.lucolimac.barkscanner.view.CachorroActivity;

public class CadastroCachorro extends AppCompatActivity {
    private final String DOG = "Cachorro";
    private FirebaseUser currentUser;
    private Spinner spinnerRaca;
    private ArrayAdapter<String> racasArrayAdapter;
    private String[] racas;
    private Spinner spinnerPorte;
    private ArrayAdapter<String> portesArrayAdapter;
    private String[] portes;
    private TextInputEditText nome_dog;
    private TextInputEditText idade_dog;
    private TextInputEditText nome_veter;
    private TextInputEditText crmv_veter;
    private Button cadastrar;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Cachorro dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cachorro);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        constroiSpinners();
        nome_dog = findViewById(R.id.dog_name_card);
        idade_dog = findViewById(R.id.dog_age);
        nome_veter = findViewById(R.id.nome_veterinario);
        crmv_veter = findViewById(R.id.crmv_veterinario);
        cadastrar = findViewById(R.id.grava_dog);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("cachorros");
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dog = new Cachorro(nome_dog.getText().toString(), spinnerRaca.getSelectedItem().toString()
                        , Integer.parseInt(idade_dog.getText().toString()), currentUser.getUid()
                        , spinnerPorte.getSelectedItem().toString(), nome_veter.getText().toString()
                        , crmv_veter.getText().toString());
                databaseReference.push().setValue(dog);
//                Toast.makeText(null, "Cachorro cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                Log.d(DOG, dog.getNome() + " adicionado com sucesso!");
                finish();
                startActivity(new Intent(CadastroCachorro.this, CachorroActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:
                break;
        }
        return true;
    }

    public void constroiSpinners() {
        //        [START Spiners]
        spinnerPorte = findViewById(R.id.spinner_porte);
        spinnerRaca = findViewById(R.id.spinner_raca);
        portes = getResources().getStringArray(R.array.porte);
        racas = getResources().getStringArray(R.array.racas);
        portesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, portes);
        racasArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, racas);
        spinnerPorte.setAdapter(portesArrayAdapter);
        spinnerRaca.setAdapter(racasArrayAdapter);
        spinnerPorte.setSelection(2);
        spinnerRaca.setSelection(2);
//        [END Spiners]
    }

}
