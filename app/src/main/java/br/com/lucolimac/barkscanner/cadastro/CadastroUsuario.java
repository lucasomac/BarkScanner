package br.com.lucolimac.barkscanner.cadastro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.lucolimac.barkscanner.R;
import br.com.lucolimac.barkscanner.local.Cidade;

public class CadastroUsuario extends AppCompatActivity {
    private Spinner spinUF, spinCidade;
    private ArrayAdapter<String> estadoArrayAdapter;
    private ArrayAdapter<Cidade> cidadeArrayAdapter;
    private ArrayList<String> estados;
    private ArrayList<String> cidades;
    private FirebaseDatabase database;
    private DatabaseReference referencia;

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
//        spinUF.setAdapter(ArrayAdapter.createFromResource(this, R.array.lista_uf, R.layout.support_simple_spinner_dropdown_item));
//        spinCidade.setAdapter(ArrayAdapter.createFromResource(this, R.array.lista_uf, R.layout.support_simple_spinner_dropdown_item));

        spinUF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                criaListaCidades();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void criaListaEstados() {
        referencia.child("estados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot estadoSnapshot : dataSnapshot.getChildren()) {
                    String estado = estadoSnapshot.child("nome").getValue(String.class);
                    estados.add(estado.toUpperCase());
                }
                estadoArrayAdapter = new ArrayAdapter<String>
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

    private void criaListaCidades() {
        referencia.child("estados").child("nome").child("cidades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // final List<String> cidade = new ArrayList<>();

                for (DataSnapshot cidadeSnapshot : dataSnapshot.getChildren()) {
                    String cidadeNome = cidadeSnapshot.child("nome").getValue(String.class);
                    cidades.add(cidadeNome.toUpperCase());
                }
                spinCidade = findViewById(R.id.spinCidade);
                ArrayAdapter<String> cidadesAdapter = new ArrayAdapter<String>
                        (CadastroUsuario.this, android.R.layout.simple_spinner_item, cidades);
                cidadesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinCidade.setAdapter(cidadesAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

