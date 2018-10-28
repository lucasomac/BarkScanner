package br.com.lucolimac.barkscanner.cadastro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import br.com.lucolimac.barkscanner.MainActivity;
import br.com.lucolimac.barkscanner.R;
import br.com.lucolimac.barkscanner.local.Cidade;
import br.com.lucolimac.barkscanner.local.Estado;
import br.com.lucolimac.barkscanner.model.Usuario;
import br.com.lucolimac.barkscanner.util.Validacao;

public class CadastroUsuario extends AppCompatActivity implements View.OnClickListener {
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
        updateUI(currentUser);
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

    private void createAccount(Usuario user) {
        Log.d(TAG, "createAccount:" + user.getEmail());
        if (!Validacao.isValidEmail(user.getEmail()) || !Validacao.isValidCPF(user.getCpf())) {
            return;
        }

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser userLoged = mAuth.getCurrentUser();
                            updateUI(userLoged);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastroUsuario.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_register_button) {
            user = new Usuario(((TextView) findViewById(R.id.nome)).getText().toString(),
                    ((TextView) findViewById(R.id.email)).getText().toString(),
                    ((TextView) findViewById(R.id.password)).getText().toString(),
                    ((Spinner) findViewById(R.id.spinUF)).getSelectedItem().toString(),
                    ((Spinner) findViewById(R.id.spinCidade)).getSelectedItem().toString(),
                    ((TextView) findViewById(R.id.bairro)).getText().toString(),
                    ((TextView) findViewById(R.id.cpf)).getText().toString());
            createAccount(user);
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, "Usuário ou senha invalídos!", Toast.LENGTH_LONG);
        }
    }
}

