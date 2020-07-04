// Remote LEC Control
// https://led-on-off-7ac0f.firebaseio.com/

package com.example.button_click_event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btn_LED_ON;
    Button btn_LED_OFF;
    TextView textView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("LED_STATUS");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.InitializeView();
        this.SetListener();
    }

    public void InitializeView() {
        btn_LED_ON = (Button)findViewById(R.id.btn1);
        btn_LED_OFF = (Button)findViewById(R.id.btn2);
        textView = (TextView)findViewById(R.id.textView);
        setTitle("LED Remote Control");
        textView.setText(myRef.getKey());
    }

    public  void SetListener() {
        // read from the Database, addValue Event Listenning
        readDB();

        // ON_Button Click
        btn_LED_ON.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                textView.setBackgroundColor(Color.YELLOW);
                // write to the Database
                myRef.setValue("ON");
            }
        });

        // OFF_Button Click
        btn_LED_OFF.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                textView.setBackgroundColor(Color.WHITE);
                // write to the Database
                myRef.setValue("OFF");
            }
        });
    }

    public void readDB() {
        // read from the Database,  addValue Event Listenning
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ledState = (String) dataSnapshot.getValue();
                //String value = dataSnapshot.getValue(String.class);

                textView.setText("LED is " + ledState);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
