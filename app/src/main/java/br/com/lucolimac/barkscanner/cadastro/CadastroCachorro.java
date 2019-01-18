package br.com.lucolimac.barkscanner.cadastro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    private TextInputEditText nascimento_dog;
    private TextInputEditText nome_veter;
    private TextInputEditText crmv_veter;
    private Button cadastrar;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private Cachorro dog;
    private DatePickerDialog dataPickerDialog;
    private Calendar calendar;
    private final String myFormat = "dd/MM/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Date dataSelecionada = new Date();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cachorro);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        constroiSpinners();
        nome_dog = findViewById(R.id.dog_name_card);
        nascimento_dog = findViewById(R.id.dog_nascimento);
        nome_veter = findViewById(R.id.nome_veterinario);
        crmv_veter = findViewById(R.id.crmv_veterinario);
        cadastrar = findViewById(R.id.grava_dog);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("cachorros/" + currentUser.getUid() + "/");

        nascimento_dog.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                calendar = Calendar.getInstance();
//                int day;
//                int month;
//                int year;

                dataPickerDialog = new DatePickerDialog(CadastroCachorro.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                calendar.set(Calendar.YEAR, year);
//                                day = dayOfMonth;
//                                month = month;
//                                year = year;
//                                nascimento_dog.setText(dayOfMonth + "/" + month + "/" + year);
                                updateLabel();
                            }
                        }, calendar.getTime().getMonth(), calendar.getTime().getDate(), calendar.getTime().getYear());
                dataPickerDialog.show();
                return true;
            }
        });
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dog = new Cachorro(nome_dog.getText().toString(), spinnerRaca.getSelectedItem().toString()
                        , new Date(nascimento_dog.getText().toString())
                        , spinnerPorte.getSelectedItem().toString(), nome_veter.getText().toString()
                        , crmv_veter.getText().toString());
                databaseReference.push().setValue(dog).toString();
//                Toast.makeText(null, "Cachorro cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                Log.d(DOG, dog + " adicionado com sucesso!");
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
        spinnerPorte.setSelected(true);
        spinnerRaca.setSelected(true);
//        [END Spiners]
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
        nascimento_dog.setText(sdf.format(calendar.getTime()));
    }
}
