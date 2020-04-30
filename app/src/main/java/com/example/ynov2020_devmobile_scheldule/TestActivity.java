package com.example.ynov2020_devmobile_scheldule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;


public class TestActivity extends AppCompatActivity {

    private static final String TAG = "Test Activity : ";

    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private boolean isParent = false;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);
    TextView textView1;
    private static TextView textView2;
    public static TextView getTextView2() {
        return textView2;
    }
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);

        textView1 = (TextView)findViewById(R.id.msg1);
        textView1.setText(timeHour + ":" + timeMinute);
        textView2 = (TextView)findViewById(R.id.msg2);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(TestActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(TestActivity.this, 0, myIntent, 0);

        OnClickListener listener1 = new OnClickListener() {
            public void onClick(View view) {
                textView2.setText("");
                Bundle bundle = new Bundle();
                bundle.putInt(MyConstants.HOUR, timeHour);
                bundle.putInt(MyConstants.MINUTE, timeMinute);
                MyDialogFragment fragment = new MyDialogFragment(new MyHandler());
                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(fragment, MyConstants.TIME_PICKER);
                transaction.commit();
            }
        };
        Button btn1 = (Button)findViewById(R.id.button1);
        btn1.setOnClickListener(listener1);

        OnClickListener listener2 = new OnClickListener() {
            public void onClick(View view) {
                textView2.setText("");
                cancelAlarm();
            }
        };
        Button btn2 = (Button)findViewById(R.id.button2);
        btn2.setOnClickListener(listener2);
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage (Message msg){
            Bundle bundle = msg.getData();
            timeHour = bundle.getInt(MyConstants.HOUR);
            timeMinute = bundle.getInt(MyConstants.MINUTE);
            textView1.setText(timeHour + ":" + timeMinute);
            setAlarm();
        }
    }

    private void setAlarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        if (alarmManager!= null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void OnStart() {
        super.onStart();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    }

    /*public void OnClick(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(TAG, user.getUid());
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
        } else {
            Log.w(TAG, "Pas d'utilisateur connecté.");
        }
        startAlert();
    }

    public void startAlert(){

    }*/

    public void OnConfig(View view) {
        Intent intent = new Intent(TestActivity.this, ConfigActivity.class);
        startActivity(intent);
    }

    public void OnRelation(View view) {
        Intent intent = new Intent(TestActivity.this, RelationActivity.class);
        startActivity(intent);
    }
}
