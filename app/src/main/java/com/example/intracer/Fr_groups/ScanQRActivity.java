package com.example.intracer.Fr_groups;
import com.example.intracer.MenuActivity;
import com.example.intracer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScanQRActivity extends AppCompatActivity {

    private Button Back;
    private ScannerLiveView camera;
    String date;

    private FirebaseDatabase db;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qractivity);

        if (checkPermission()) {
            // if permission is already granted display a toast message
            //Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
        camera = (ScannerLiveView) findViewById(R.id.camview);

        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();


        camera.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                // method is called when scanner is started
                //Toast.makeText(ScanQRActivity.this, "Scanner Started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                // method is called when scanner is stoped.
                atras();
            }

            @Override
            public void onScannerError(Throwable err) {
                // method is called when scanner gives some error.
                //Toast.makeText(ScanQRActivity.this, "Scanner Error: " + err.getMessage(), Toast.LENGTH_SHORT).show();
                atras();
            }

            @Override
            public void onCodeScanned(String data) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                String adt = data.substring(0,4)+"-"+data.substring(5,6)+"-"+data.substring(6,8)+" "+data.substring(8,10)+":"+data.substring(10,12);

                date = sdf.format(new Date());



                String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String mail = FirebaseAuth.getInstance().getCurrentUser().getEmail();



                myRef.child("Groups").child(ActualGroup.Guid).child("QR").child(data).child(Uid).child("Email").setValue(mail);
                myRef.child("Groups").child(ActualGroup.Guid).child("QR").child(data).child(Uid).child("DateSc").setValue(date);
                myRef.child("Groups").child(ActualGroup.Guid).child("QR").child(data).child(Uid).child("DateQR").setValue(adt);

                Toast.makeText(ScanQRActivity.this, "Code Scanned", Toast.LENGTH_SHORT).show();

                atras();

            }
        });

        Back =  findViewById(R.id.back);
        Back.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                atras();
            }
        });
    }
    private void requestPermission() {
        // this method is to request
        // the runtime permission.
        int PERMISSION_REQUEST_CODE = 200;
        ActivityCompat.requestPermissions(this, new String[]{CAMERA, VIBRATE}, PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission() {
        // here we are checking two permission that is vibrate
        // and camera which is granted by user and not.
        // if permission is granted then we are returning
        // true otherwise false.
        int camera_permission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int vibrate_permission = ContextCompat.checkSelfPermission(getApplicationContext(), VIBRATE);
        return camera_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    protected void onPause() {
        // on app pause the
        // camera will stop scanning.
        camera.stopScanner();
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();
        // 0.5 is the area where we have
        // to place red marker for scanning.
        decoder.setScanAreaPercent(0.8);
        // below method will set secoder to camera.
        camera.setDecoder(decoder);
        camera.startScanner();
    }
    public void onBackPressed(){
        super.onBackPressed();
        atras();

    }

    public void atras(){
        Intent intent = new Intent(ScanQRActivity.this, GroupActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // this method is called when user
        // allows the permission to use camera.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            boolean cameraaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean vibrateaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if (cameraaccepted && vibrateaccepted) {
                //Toast.makeText(this, "Permission granted..", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(this, "Permission Denined \n You cannot use app without providing permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}