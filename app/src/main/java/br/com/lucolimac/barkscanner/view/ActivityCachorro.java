package br.com.lucolimac.barkscanner.view;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import br.com.lucolimac.barkscanner.R;
import br.com.lucolimac.barkscanner.model.Cachorro;
import br.com.lucolimac.barkscanner.model.Porte;
import br.com.lucolimac.barkscanner.model.Usuario;

public class ActivityCachorro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cachorro);
        RecyclerView view_cachorro = findViewById(R.id.view_cachorro);
        Usuario dono = new Usuario("Mateus", "lucasomac@outlook.com", "72816053", "SE", "Aracaju", "Santos Dumont", "04806586501");
        Cachorro dog = new Cachorro("Polo", "PUG", 56, dono, Porte.PEQUENO);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cachorro");
        myRef.setValue(dog);
        //List<Cachorro> view;
    }
}
