package id.chaerul.scannerproject.ScanCode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import id.chaerul.scannerproject.MainActivity;
import id.chaerul.scannerproject.R;

public class CameraScan extends AppCompatActivity {
    private static final int camera_permission_request_code = 200;
    private TextView hasil;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scancode);

        FirebaseApp.initializeApp(this);
        databaseRef = FirebaseDatabase.getInstance().getReference("scancode");


        hasil = (TextView)findViewById(R.id.hasil);


        //check permission camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //jika belom di izinkan, akan di minta izin lagi
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, camera_permission_request_code);
        }else{
           //jika sudah di izinkan
            initiateScanner();
        }
    }

    private void initiateScanner() {
        //inisialis pemindaian intentintegrator
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == camera_permission_request_code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //izin camera di berikan
                initiateScanner();
            } else {
                Toast.makeText(this, "berikan izin camera!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //tangani hasil pemindaian
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                //pemindaian di batalkan
                System.out.println("pemindaian di batalkan");
                finish();
            }else{
                //Dapatkan data hasil pemindaian
                String scanResult = intentResult.getContents();
                //Dapatkan format barcode
                String barcodeFormat = intentResult.getFormatName();
                String output = "Hasil Scan: "+scanResult+"\nFormat: "+barcodeFormat;
                System.out.println(output);
                Toast.makeText(this, scanResult, Toast.LENGTH_SHORT).show();
                hasil.setText(output);
                databaseRef.setValue(scanResult);
            }
        }
    }
}
