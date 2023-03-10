package com.example.intracer.Fr_groups;
import com.example.intracer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QrActivity extends AppCompatActivity {

    private Button Back;
    private ImageView qrCodeIV;
    private Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String currenttime;
    String savetime;

    private FirebaseDatabase db;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        SimpleDateFormat SDD = new SimpleDateFormat("yyyyMMdd");

        qrCodeIV = findViewById(R.id.QrImage);

        currenttime = sdf.format(new Date());
        savetime = SDD.format(new Date());


        generateQrBtn = findViewById(R.id.idBtnGenerateQR);
        generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;

                int dimen = width < height ? width : height;
                dimen = dimen * 3 / 4;

                qrgEncoder = new QRGEncoder(currenttime, null, QRGContents.Type.TEXT, dimen);
                try {
                    // getting our qrcode in the form of bitmap.
                    bitmap = qrgEncoder.encodeAsBitmap();
                    // the bitmap is set inside our image
                    // view using .setimagebitmap method.
                    qrCodeIV.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    // this method is called for
                    // exception handling.
                    System.out.println("*****************\n"+ e.toString());
                }

            }

        });

       // time();

        Back =  findViewById(R.id.back);
        Back.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                atras();
            }
        });
    }


    public void onBackPressed(){
        super.onBackPressed();
        atras();

    }

    public void atras(){
        Intent intent = new Intent(QrActivity.this, GroupActivity.class);
        startActivity(intent);
        finish();
    }
}