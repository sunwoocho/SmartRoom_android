package com.example.swcho.smartroomproject;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class ContextManagementActivity extends AppCompatActivity {

    private RoomContextHttpManager manager;
    private RoomContextState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        RequestQueue queue = Volley.newRequestQueue(this);
        manager = new RoomContextHttpManager(queue, ContextManagementActivity.this);
        state = new RoomContextState("0","0","ON",0,0,0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.buttonCheck)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String room = ((EditText) findViewById(R.id.editText1))
                        .getText().toString();
                manager.retrieveRoomContextState(room);
            }
        });
    }

    public void switchLight(View view) throws JSONException {
        manager.switchLight(state,state.getRoom());
        manager.retrieveRoomContextState(state.getRoom());
    }

    public void switchRinger(View view) {

        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }


        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int mode = audioManager.getRingerMode();
        if (mode == AudioManager.RINGER_MODE_SILENT)
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        else {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onUpdate(RoomContextState context){
        this.state.setLight(context.getLight());
        this.state.setRe(context.getRe());
        this.state.setRoom(context.getRoom());
        this.state.setStatus(context.getStatus());
        updateContextView();


    }

    public void onUpdateTemp(int temp){
        this.state.setTemp(temp);
        updateContextView();


    }

    public void onUpdateHum(int hum){
        this.state.setHum(hum);
        updateContextView();

    }

    private void updateContextView() {
                ImageView image = ((ImageView) findViewById(R.id.imageView1));
            View  contextView = ((View) findViewById(R.id.contextLayout));
            if (this.state != null) {
                contextView.setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.textViewRoomIdValue)).setText(state.getRe());
                ((TextView) findViewById(R.id.textViewLightValue)).setText(Integer
                        .toString(state.getLight()));
                ((TextView) findViewById(R.id.textViewTempValue)).setText(Integer
                        .toString(state.getTemp()));
                ((TextView) findViewById(R.id.textViewHumValue)).setText(Integer
                        .toString(state.getHum()));
                ((TextView) findViewById(R.id.textViewRoomValue)).setText(state.getRoom());
                if (state.getStatus().equals("ON"))
                    image.setImageResource(R.drawable.ic_bulb_on);
                else
                    image.setImageResource(R.drawable.ic_bulb_off);
            } else
                contextView.setVisibility(View.INVISIBLE);

    }
}
