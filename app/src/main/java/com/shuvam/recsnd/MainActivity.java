package com.shuvam.recsnd;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.games.Player;
import com.google.gson.Gson;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {


    RecyclerView recView;
    MyAdapter adapter;
    FloatingActionButton buttonStart, buttonStop, buttonPlayLastRecordAudio,
            buttonStopPlayingRecording;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    SlidingUpPanelLayout slidingLayout;
    Random random;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    TextView tvTimer;
    ImageView logo;
    public static final int RequestPermissionCode = 1;
   // Button btnShowDetails;
    MediaPlayer mediaPlayer;
    ArrayList<File> filesToLoad;
    RecyclerView.LayoutManager lm;
    File[] files;
    View timer;
    Animation anim;
    CountDownTimer cdt;
    CountDownTimer cdtInner;
    TextView tvTimeCount;
    File fileToUpload;
    String UPDRS;
    int isParkinson;
    int [] states = new int[3];
    private int k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        k =0;
        buttonStart = (FloatingActionButton) findViewById(R.id.btnPlay);
        buttonStop = (FloatingActionButton) findViewById(R.id.btnStop);
       // btnShowDetails = (Button) findViewById(R.id.btnShowDetails);
        slidingLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);
        final LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        logo = (ImageView)findViewById(R.id.logo);
        timer = findViewById(R.id.timer);

        tvTimeCount = (TextView)findViewById(R.id.tvTimeCount);
        //tvTimeCount.setVisibility(View.INVISIBLE);
        tvTimer = new TextView(this);
        tvTimer.setText("3");
        timer.setVisibility(View.INVISIBLE);

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(logo,"alpha",0,1).setDuration(2000);
        fadeIn.start();

        states[0]=3;
        states[1]=2;
        states[2]=1;



        anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.timer_anim);

        /*LinearLayoutManager layoutManager = (LinearLayoutManager)recView
                .getLayoutManager()*/


        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.scrollToPositionWithOffset(0, 0);

        recView = (RecyclerView)findViewById(R.id.recViewFiles);
        recView.setLayoutManager(linearLayoutManager);

      //  filesToLoad = new ArrayList<>();

        populateRecView();
        recView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Toast.makeText(MainActivity.this, "Item "+position, Toast.LENGTH_SHORT).show();
                        Log.d("The file is:",files[position].getName());
                        uploadFile(files[position]);


                    }
                })
        );
/*
        btnShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });*/

        /* buttonStop.setEnabled(false);
        buttonPlayLastRecordAudio.setEnabled(false);
        buttonStopPlayingRecording.setEnabled(false);*/

        random = new Random();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                tvTimeCount.setVisibility(View.VISIBLE);
                cdt = new CountDownTimer(4000,1000) {
                    @Override
                    public void onTick(long l) {
                        tvTimeCount.setText("Speak in: "+states[k]+"s");
                        k++;
                        Log.d("Timer:",""+l/1000);
                    }

                    @Override
                    public void onFinish() {



                        tvTimeCount.setText("Say Aaah: A simpler way to detect \nParkinson's.....");
                        k=0;

                       tvTimeCount.setText("Speak");

                        cdtInner = new CountDownTimer(11000,1000) {
                            @Override
                            public void onTick(long l) {


                            }

                            @Override
                            public void onFinish() {
                                //vaibhav patil
                                // Toast.makeText(MainActivity.this, "Stop Pressed", Toast.LENGTH_SHORT).show();
                                mediaRecorder.stop();
                                // buttonStop.setEnabled(false);
                                // buttonPlayLastRecordAudio.setEnabled(true);
                                //buttonStart.setEnabled(true);
                                //buttonStopPlayingRecording.setEnabled(false);
                                buttonStop.setVisibility(View.INVISIBLE);
                                buttonStart.setVisibility(View.VISIBLE);
                                timer.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "Recording Completed",
                                        Toast.LENGTH_SHORT).show();

                                String path = Environment.getExternalStorageDirectory().toString()+"/Hack/";
                                File directory = new File(path);
                                files = directory.listFiles();
                                Toast.makeText(MainActivity.this, "No of files: "+files.length, Toast.LENGTH_SHORT).show();
                                filesToLoad = new ArrayList<File>(Arrays.asList(files));
                                adapter = new MyAdapter(filesToLoad);
                                recView.setAdapter(adapter);
                            }
                        }.start();



                        timer.setVisibility(View.VISIBLE);
                        timer.setAnimation(anim);
                        anim.start();

                        Toast.makeText(MainActivity.this, "Start Pressed", Toast.LENGTH_SHORT).show();

                        buttonStart.setVisibility(View.INVISIBLE);
                        buttonStop.setVisibility(View.VISIBLE);
                        if(checkPermission()) {

                            AudioSavePathInDevice =
                                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/Hack/" +
                                            CreateRandomAudioFileName(5) + "AudioRecording.wav";

                            MediaRecorderReady();

                            try {
                                mediaRecorder.prepare();
                                mediaRecorder.start();
                            } catch (IllegalStateException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            // buttonStart.setEnabled(false);
                            //buttonStop.setEnabled(true);

                            Toast.makeText(MainActivity.this, "Recording started",
                                    Toast.LENGTH_LONG).show();


                        } else {
                            requestPermission();
                        }

                            cancel();
                    }
                }.start();

            }
        });



        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

      /*  buttonPlayLastRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {

                buttonStop.setEnabled(false);
                buttonStart.setEnabled(false);
                buttonStopPlayingRecording.setEnabled(true);

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(MainActivity.this, "Recording Playing",
                        Toast.LENGTH_LONG).show();
            }
        });*/

       /* buttonStopPlayingRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);

                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    MediaRecorderReady();
                }
            }
        });*/

    }

    private void populateRecView() {


        String path = Environment.getExternalStorageDirectory().toString()+"/Hack/";
        File directory = new File(path);
        files = directory.listFiles();
        // Toast.makeText(MainActivity.this, "No of files: "+files.length, Toast.LENGTH_SHORT).show();
        filesToLoad = new ArrayList<File>(Arrays.asList(files));
        adapter = new MyAdapter(filesToLoad);
        recView.setAdapter(adapter);
    }

    private void uploadFile(File file) {

       // fileToUpload = new File();
        fileToUpload = file;

        Log.d("File to upload",""+fileToUpload.getName());

        new UploadFile().execute();




    }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(MainActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    private class UploadFile extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            StringBuilder s = new StringBuilder();

            try {

                //final AppDetails globalVariable = (AppDetails)getActivity().getApplicationContext();
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost(
                        "http://139.59.62.236:8888/check");
                //File file = globalVariable.getFilesLocations().get(0);
             ///   FileBody bin = new FileBody(file);
                FileBody bin = new FileBody(fileToUpload);
                MultipartEntity reqEntity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);
              //  reqEntity.addPart("token", new StringBody(tokenStr));
                reqEntity.addPart("voice", bin);
                postRequest.setEntity(reqEntity);
                HttpResponse response = httpClient.execute(postRequest);
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent(), "UTF-8"));
                String sResponse;


                while ((sResponse = reader.readLine()) != null) {
                    s = s.append(sResponse);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return s.toString();
        }

        @Override
        protected void onPostExecute(String temp) {
            Toast.makeText(getApplicationContext(), "Upload Successful" , Toast.LENGTH_SHORT).show();

            Gson g = new Gson();
            UploadResponse p = g.fromJson(temp, UploadResponse.class);

            UPDRS = p.getUPDRS();
            isParkinson = p.getIsParkinsons();



                Log.d("Upload Response", temp);
                Log.d("Status: ", p.getStatus());


                Intent i = new Intent(getApplicationContext(), ResultActivity.class);


                Bundle mBundle = new Bundle();
                mBundle.putString("UPDRS", UPDRS);
                mBundle.putInt("isParkinsons", isParkinson);
                i.putExtras(mBundle);


                startActivity(i);



        }
    }
}
