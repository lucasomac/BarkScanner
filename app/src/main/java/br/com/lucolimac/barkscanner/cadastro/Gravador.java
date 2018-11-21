package br.com.lucolimac.barkscanner.cadastro;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import br.com.lucolimac.barkscanner.R;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Gravador extends AppCompatActivity {
    //Random random;
    //String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    private Button buttonRecord, buttonStop, buttonPlayLastRecordAudio, buttonStopPlayingRecording;
    private Spinner spinnerSituacoes;
    private ArrayAdapter<String> situacoesArrayAdapter;
    private String[] situacoes;
    private String path = null;
    private MediaRecorder latido;
    MediaPlayer mediaPlayer;
    // private Cachorro cao;
    private FirebaseAuth auth;
    private String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravador);

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        userEmail = currentUser.getEmail();
        //cao = new Cachorro();


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
        // random = new Random();

        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/Android/data/br.com.lucolimac.barkscanner/files/" + userEmail + "/" +
                            spinnerSituacoes.getSelectedItem().toString() + "/" +
                            DateFormat.getDateInstance().format(new Date()) + "-" +
                            "Latido.aac";
//                    CreateRandomAudioFileName(5) +
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
        latido.setAudioEncoder(MediaRecorder.OutputFormat.AAC_ADTS);
        latido.setOutputFile(path);
    }

//    public String CreateRandomAudioFileName(int string) {
//        StringBuilder stringBuilder = new StringBuilder(string);
//        int i = 0;
//        while (i < string) {
//            stringBuilder.append(RandomAudioFileName.charAt(random.nextInt(RandomAudioFileName.length())));
//            i++;
//        }
//        return stringBuilder.toString();
//    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(Gravador.this, new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);

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
