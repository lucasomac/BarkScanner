package br.com.lucolimac.barkscanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Base64;
import java.util.List;

import br.com.lucolimac.barkscanner.model.Cachorro;
import br.com.lucolimac.barkscanner.model.Porte;
import br.com.lucolimac.barkscanner.model.Usuario;

public class ActivityCachorro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cachorro);
        RecyclerView view_cachorro = findViewById(R.id.view_cachorro);
        Usuario dono = new Usuario("Lucas", "lucasomac@outlook.com", Base64.getUrlEncoder("72816053"), "SE", "Aracaju", "Santos Dumont", "04806586501");
        Cachorro dog = new Cachorro("Toto", "PUG", 56, dono, Porte.PEQUENO);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cachorro");
        myRef.setValue(dog);
        List<Cachorro> list;
    }
}
