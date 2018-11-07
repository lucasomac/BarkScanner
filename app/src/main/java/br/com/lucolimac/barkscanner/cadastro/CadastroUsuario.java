package br.com.lucolimac.barkscanner.cadastro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import br.com.lucolimac.barkscanner.MainActivity;
import br.com.lucolimac.barkscanner.R;
import br.com.lucolimac.barkscanner.local.Cidade;
import br.com.lucolimac.barkscanner.local.Estado;
import br.com.lucolimac.barkscanner.model.Usuario;

public class CadastroUsuario extends AppCompatActivity {
    private Spinner spinUF, spinCidade;
    private ArrayAdapter<Estado> estadoArrayAdapter;
    private ArrayAdapter<Cidade> cidadeArrayAdapter;
    private ArrayList<Estado> estados;
    private ArrayList<Cidade> cidades;
    private static final String TAG = "EmailPassword";
    private Button criaUsuario;
    //Firebase
    private Usuario user;
    private FirebaseDatabase database;
    private DatabaseReference referencia;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        criaUsuario = findViewById(R.id.email_register_button);
        //Banco
        database = FirebaseDatabase.getInstance();
        referencia = database.getReference();
        //UserAuth
        mAuth = FirebaseAuth.getInstance();
        //Spins
        spinUF = findViewById(R.id.spinUF);
        spinCidade = findViewById(R.id.spinCidade);
        estados = new ArrayList<>();
        cidades = new ArrayList<>();
        criaListaEstados();
        spinUF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinCidade.setEnabled(true);
                criaListaCidades();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinCidade.setEnabled(false);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        user = new Usuario(currentUser.getDisplayName(), currentUser.getEmail(),
                ((Spinner) findViewById(R.id.spinUF)).getSelectedItem().toString(),
                ((Spinner) findViewById(R.id.spinCidade)).getSelectedItem().toString(),
                ((TextView) findViewById(R.id.bairro)).getText().toString(),
                ((TextView) findViewById(R.id.cpf)).getText().toString());
        updateUI(currentUser);
        criaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referencia.child("usuarios").push().setValue(user);
            }
        });
    }

    private void criaListaCidades() {
        cidades = ((Estado) spinUF.getSelectedItem()).getCidades();
        cidadeArrayAdapter = new ArrayAdapter<Cidade>
                (CadastroUsuario.this, android.R.layout.simple_spinner_item, cidades);
        cidadeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCidade.setAdapter(cidadeArrayAdapter);
    }

    private void criaListaEstados() {
        referencia.child("estados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot estadoSnapshot : dataSnapshot.getChildren()) {
                    Estado estado = estadoSnapshot.getValue(Estado.class);
                    estados.add(estado);
                }
                estadoArrayAdapter = new ArrayAdapter<>
                        (CadastroUsuario.this, android.R.layout.simple_spinner_item, estados);
                estadoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinUF.setAdapter(estadoArrayAdapter);
                spinUF.setSelection(24);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, "Usuário ou senha invalídos!", Toast.LENGTH_LONG);
        }
    }
}

