package br.com.lucolimac.barkscanner.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.lucolimac.barkscanner.MainActivity;
import br.com.lucolimac.barkscanner.R;
import br.com.lucolimac.barkscanner.adapter.LatidoAdapter;
import br.com.lucolimac.barkscanner.cadastro.CadastroCachorro;
import br.com.lucolimac.barkscanner.cadastro.Gravador;
import br.com.lucolimac.barkscanner.model.Latido;

public class LatidoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG_AUTH = "AUTH";
    private static final String TAG_LATIDO = "LATIDOS";
    private LatidoAdapter latidoAdapter;
    private RecyclerView latidosRecyclerView;
    private LinearLayoutManager layoutManagerLatido = new LinearLayoutManager(this);
    private List<Latido> latidos = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latido);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("latidos/" + currentUser.getUid() + "/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Latido latido = snap.getValue(Latido.class);
                    latidos.add(latido);
                    Log.d(TAG_LATIDO, "Latido é:\n" + latido);
                    //layoutManagerLatido = new LinearLayoutManager(this);
                }
                System.out.println("O tamanho do vetor de latidos é: " + latidos.size());
                latidosRecyclerView = findViewById(R.id.latido_recyclerview);
                latidosRecyclerView.setHasFixedSize(true);
                latidoAdapter = new LatidoAdapter(latidos);
                latidosRecyclerView.setLayoutManager(layoutManagerLatido);
                latidosRecyclerView.setAdapter(latidoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));
//        latidos.add(new Latido(new Cachorro("Buchecha", "Chow-Chow", new Date(), "grande"), "https://google.com.br", "Comendo"));


        SpeedDialView fab = findViewById(R.id.speed_main);
        fab.inflate(R.menu.speed);
        fab.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.action_latido:
                        startActivity(new Intent(LatidoActivity.this, Gravador.class));
                        return true;
                    case R.id.action_cachorro:
                        startActivity(new Intent(LatidoActivity.this, CadastroCachorro.class));
                        return true;
                    case R.id.action_share:
                        shareApp();
                        return true;
                }
                return false;
            }
        });

        DrawerLayout drawer = findViewById(R.id.latido_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.latido_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.latido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_inicio) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.nav_latido) {
            finish();
            startActivity(new Intent(this, LatidoActivity.class));
        } else if (id == R.id.nav_idetificar) {
            //finish();
            startActivity(new Intent(this, ActivityIdentificar.class));
        } else if (id == R.id.nav_cachorro) {
            finish();
            startActivity(new Intent(this, CachorroActivity.class));
        } else if (id == R.id.nav_share) {
            shareApp();
        } else if (id == R.id.nav_sobre) {
            startActivity(new Intent(this, Sobre.class));
        } else if (id == R.id.nav_sair) {
            finish();
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            Log.d(TAG_AUTH, "Úsuaurio saiu do sistema!");
//                            finish();
                        }
                    });
            startActivity(new Intent(this, MainActivity.class));
        }
        DrawerLayout drawer = findViewById(R.id.latido_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void shareApp() {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TITLE, "BarkScanner - Grave o latido do seu cachorro!");
        share.putExtra(Intent.EXTRA_SUBJECT, "BarkScanner - Grave o latido do seu cachorro!");
        share.putExtra(Intent.EXTRA_TEXT, "BarkScanner - Grave o latido do seu cachorro!\n" +
                "Acesse: https://play.google.com/store/apps/details?id=br.com.lucolimac.barkscanner");
        share.setType("text/plain");
        startActivity(share);
    }
}
