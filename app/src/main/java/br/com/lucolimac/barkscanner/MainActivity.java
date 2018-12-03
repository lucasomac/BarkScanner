package br.com.lucolimac.barkscanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import br.com.lucolimac.barkscanner.view.CachorroActivity;
import br.com.lucolimac.barkscanner.view.LatidoActivity;
import br.com.lucolimac.barkscanner.view.Sobre;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Firebase Auth  Constantes
    private static final int RC_SIGN_IN = 123;
    private static final String TAG_AUTH = "AUTH";
    //Providers
    private static final List<AuthUI.IdpConfig> PROVIDERS = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build());
    private TextView email_view;
    private TextView name_view;
    private TextView bem_vindo;
    // private ImageView foto_view;
    // Variaveis
    private FirebaseAuth mFirebaseAuth;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bem_vindo = findViewById(R.id.bem_vindo);
        //------------------------------------------------
        mFirebaseAuth = FirebaseAuth.getInstance();
        email_view = findViewById(R.id.email_text_view);
        name_view = findViewById(R.id.name_text_view);
        //foto_view = findViewById(R.id.image_mic);
        //[START Toolbar]
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //[END Toolbar]

        //[START Authentication]
        if (mFirebaseAuth.getCurrentUser() != null) {
            Log.d(TAG_AUTH, "O Úsurairo " + mFirebaseAuth.getCurrentUser().getDisplayName() + " está logado!");
//            updateInterface(mFirebaseAuth.getCurrentUser());
        } else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(PROVIDERS)
                    .setLogo(R.drawable.logo_dog_paw)      // Set logo drawable
                    .setIsSmartLockEnabled(false)
                    .setTheme(R.style.AppTheme)
                    .build(), RC_SIGN_IN);

        }
        //[END Authentication]
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
//                updateInterface(mFirebaseAuth.getCurrentUser());
                bem_vindo.setText(mFirebaseAuth.getCurrentUser().getDisplayName());
                Log.d(TAG_AUTH, "signIn:" + mFirebaseAuth.getCurrentUser().getEmail());
            } else {
                if (resultCode == RESULT_CANCELED) {
                    Log.d(TAG_AUTH, "signIn:" + "Conexão cancelada");
                    // Toast.makeText(this, "Conexão cancelada", Toast.LENGTH_SHORT).show();
                }
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Log.d(TAG_AUTH, "signIn:" + "Usuário e/ou senha inválidos");
                    Toast.makeText(null, "Usuário e/ou senha inválidos", Toast.LENGTH_LONG).show();
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Log.d(TAG_AUTH, "signIn:" + "SEM CONEXÃO COM A INTERNET");
                    Toast.makeText(null, "SEM CONEXÃO COM A INTERNET", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_latido) {
            startActivity(new Intent(this, LatidoActivity.class));
        } else if (id == R.id.nav_cachorro) {
            startActivity(new Intent(this, CachorroActivity.class));
        } else if (id == R.id.nav_share) {
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //    public void updateInterface(FirebaseUser user) {
//        Resources res = getResources();
////        String nome = String.format(res.getString(R.string.welcome_messages), username, mailCount);
////        String email = String.format(res.getString(R.string.welcome_messages), username, mailCount);
//        //Log.d("TESTE", user.getDisplayName() + "-----" + user.getEmail());
//        //Uri foto = user.getPhotoUrl();
//        email_view.setText(getString((R.string.nome), user.getDisplayName()));
//        name_view.setText(getString((R.string.email), user.getEmail()));
//        //foto_view.setImageURI(foto);
//    }
    public void shareApp(View view) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        //share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        share.putExtra(Intent.EXTRA_SUBJECT, "BarkScanner - Grave o latido do seu cachorro!");
        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=br.com.lucolimac.barkscanner");

        startActivity(Intent.createChooser(share, "BarkScanner"));
    }
}