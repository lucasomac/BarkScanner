package br.com.lucolimac.barkscanner.cadastro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.lucolimac.barkscanner.R;
import br.com.lucolimac.barkscanner.local.Cidade;
import br.com.lucolimac.barkscanner.local.Estado;
import br.com.lucolimac.barkscanner.model.Usuario;

//        spinUF.setAdapter(ArrayAdapter.createFromResource(this, R.array.lista_uf, R.layout.support_simple_spinner_dropdown_item));
//        spinCidade.setAdapter(ArrayAdapter.createFromResource(this, R.array.lista_uf, R.layout.support_simple_spinner_dropdown_item));
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        database = FirebaseDatabase.getInstance();
        referencia = database.getReference();
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
        mAuth = FirebaseAuth.getInstance();
        criaUsuario = findViewById(R.id.email_register_button);
        criaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new Usuario(((TextView) findViewById(R.id.nome)).getText().toString(),
                        ((TextView) findViewById(R.id.email)).getText().toString(),
                        ((TextView) findViewById(R.id.password)).getText().toString(),
                        ((Spinner) findViewById(R.id.spinUF)).getSelectedItem().toString(),
                        ((Spinner) findViewById(R.id.spinCidade)).getSelectedItem().toString(),
                        ((TextView) findViewById(R.id.bairro)).getText().toString(),
                        ((TextView) findViewById(R.id.cpf)).getText().toString());
                referencia.child("usuarios").push().setValue(user);
                //                mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getSenha())
//                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                            }
//                        });
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
}

