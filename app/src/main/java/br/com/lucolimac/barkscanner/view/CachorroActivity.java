package br.com.lucolimac.barkscanner.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
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
import br.com.lucolimac.barkscanner.MainActivity;
import br.com.lucolimac.barkscanner.R;
import br.com.lucolimac.barkscanner.cadastro.CadastroCachorro;
import br.com.lucolimac.barkscanner.cadastro.Gravador;
import br.com.lucolimac.barkscanner.model.Cachorro;

import static android.R.layout.simple_list_item_1;

public class CachorroActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG_AUTH = "AUTH";
    private static final String TAG_DOG = "DOGS";
    private ListView listView_cachorro;
    private ArrayAdapter<Cachorro> adapter_cachorros;
    private List<Cachorro> cachorros = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cachorro);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("cachorros");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Cachorro dog = snap.getValue(Cachorro.class);
                    cachorros.add(dog);
                    Log.d(TAG_DOG, "Catiorro é:\n" + dog);
                    listView_cachorro = findViewById(R.id.lista_cachorro);
                    adapter_cachorros = new ArrayAdapter<Cachorro>(getApplicationContext(), simple_list_item_1, cachorros);
                    listView_cachorro.setAdapter(adapter_cachorros);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SpeedDialView fab = findViewById(R.id.speed_main);
        fab.inflate(R.menu.speed);
        fab.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.action_latido:
                        startActivity(new Intent(CachorroActivity.this, Gravador.class));
                        return true;
                    case R.id.action_cachorro:
                        startActivity(new Intent(CachorroActivity.this, CadastroCachorro.class));
                        return true;
                    case R.id.action_share:
                        shareApp();
                        return true;
                }
                return false;
            }
        });


        DrawerLayout drawer = findViewById(R.id.cachorro_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.cachorro_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cachorro, menu);
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
        } else if (id == R.id.nav_cachorro) {
            finish();
            startActivity(new Intent(this, CachorroActivity.class));
        } else if (id == R.id.nav_share) {
            shareApp();
        } else if (id == R.id.nav_sobre) {
            startActivity(new Intent(this, Sobre.class));
        } else if (id == R.id.nav_sair) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            Log.d(TAG_AUTH, "Úsuaurio saiu do sistema!");
                            finish();
                        }
                    });
        }
        DrawerLayout drawer = findViewById(R.id.cachorro_drawer_layout);
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
