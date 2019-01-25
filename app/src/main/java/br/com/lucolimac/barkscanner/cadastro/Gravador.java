package br.com.lucolimac.barkscanner.cadastro;

import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import br.com.lucolimac.barkscanner.R;
import br.com.lucolimac.barkscanner.model.Cachorro;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Gravador extends AppCompatActivity {
    public static final int RequestPermissionCode = 1;
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy:HH.mm");
    private Button buttonStop, buttonPlayLastRecordAudio, buttonStopPlayingRecording;
    private ImageButton buttonRecord;
    private Spinner spinnerSituacoes;
    private ArrayAdapter<String> situacoesArrayAdapter;
    private String[] situacoes;
    private Spinner spinnerRacas;
    private ArrayAdapter<String> racasArrayAdapter;
    private String[] racas;
    private String path = null;
    private MediaRecorder latido;
    MediaPlayer mediaPlayer;
    private Cachorro cao;
    private FirebaseAuth auth;
    private String userEmail;
    private File pasta;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    StorageReference storageRef;
    private FirebaseStorage storage;
    private List<Cachorro> cachorros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravador);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cachorros = new ArrayList<Cachorro>();
        spinnerRacas = findViewById(R.id.spinner_racas);
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        userEmail = currentUser.getEmail();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
//        databaseReference.child("cachorros/" + currentUser.getUid() + "/").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snap : dataSnapshot.getChildren()) {
//                    Cachorro dog = snap.getValue(Cachorro.class);
//                    cachorros.add(dog);
//                    System.out.println("Lucas--" + dog.toString());
//
//                }
//                racasArrayAdapter =
//                        new ArrayAdapter<>(Gravador.this, android.R.layout.simple_spinner_dropdown_item, cachorros);
//                spinnerRacas.setAdapter(racasArrayAdapter);
//                spinnerRacas.setSelection(473);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        Finds
        buttonRecord = findViewById(R.id.button_record);
        buttonStop = findViewById(R.id.button_stop);
        buttonPlayLastRecordAudio = findViewById(R.id.button_play);
        buttonStopPlayingRecording = findViewById(R.id.button_stopPlay);
        spinnerSituacoes = findViewById(R.id.spinner_situacao);
        buttonStop.setEnabled(false);
        buttonPlayLastRecordAudio.setEnabled(false);
        buttonStopPlayingRecording.setEnabled(false);
        situacoes = getResources().getStringArray(R.array.situacaoes);
        situacoesArrayAdapter = new ArrayAdapter<>(Gravador.this, android.R.layout.simple_spinner_dropdown_item, situacoes);
        spinnerSituacoes.setAdapter(situacoesArrayAdapter);
        racas = getResources().getStringArray(R.array.racas);
        spinnerRacas = findViewById(R.id.spinner_racas);
        racasArrayAdapter = new ArrayAdapter<>(Gravador.this, android.R.layout.simple_spinner_dropdown_item, racas);
        spinnerRacas.setAdapter(racasArrayAdapter);
        spinnerRacas.setSelection(473);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        //spinnerRacas;
        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkPermission()) {
                    pasta = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/Android/data/br.com.lucolimac.barkscanner/files/".concat(userEmail));
                    if (!pasta.exists()) {
                        pasta.mkdir();
                    }
                    String raca = ((Cachorro) spinnerRacas.getSelectedItem()).getRaca();
                    path = pasta.getAbsolutePath().concat("/") + spinnerSituacoes.getSelectedItem().toString()
                            + "-" + raca + "-" +
                            dateFormat.format(new Date()) + "-" + "Latido.aac";
                    MediaRecorderReady();
                    try {
                        latido.prepare();
                        latido.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    buttonRecord.setEnabled(false);
                    buttonStop.setEnabled(true);
                    Toast.makeText(Gravador.this, "Recording started", Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                latido.stop();
                buttonStop.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);
                buttonRecord.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);
                storageRef = storage.getReference("latidos");
                Uri file = Uri.fromFile(new File(path));
                StorageReference latidoRef = storageRef.child(auth.getCurrentUser().getEmail().concat("/") + file.getLastPathSegment());
                UploadTask uploadTask = latidoRef.putFile(file);
                Toast.makeText(Gravador.this, "Recording Completed", Toast.LENGTH_LONG).show();
            }
        });

        buttonPlayLastRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException, SecurityException, IllegalStateException {
                buttonStop.setEnabled(false);
                buttonRecord.setEnabled(false);
                buttonStopPlayingRecording.setEnabled(true);
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                Toast.makeText(Gravador.this, "Recording Playing", Toast.LENGTH_LONG).show();
            }
        });

        buttonStopPlayingRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonStop.setEnabled(false);
                buttonRecord.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    MediaRecorderReady();
                }
            }
        });
    }

    public void MediaRecorderReady() {
        latido = new MediaRecorder();
        latido.setAudioSource(MediaRecorder.AudioSource.MIC);
        latido.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        latido.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        latido.setOutputFile(path);
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(Gravador.this,
                new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(Gravador.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Gravador.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;

    }
}
