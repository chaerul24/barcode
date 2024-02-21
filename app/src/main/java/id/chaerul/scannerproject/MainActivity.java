package id.chaerul.scannerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

import id.chaerul.scannerproject.DataString.DataSet;
import id.chaerul.scannerproject.ScanCode.CameraScan;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler_view_home;
    private ArrayList<DataSet> array;
    private LinearLayout linearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearlayout = (LinearLayout)findViewById(R.id.linearlayout);
        linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraScan.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                
            }
        });

        FirebaseApp.initializeApp(this);
    }
}