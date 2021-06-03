package com.pcdeng.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                text = findViewById(R.id.editText);
                showNotification(text.getText().toString());
            }
        });

    }

    public void sendMessage(View view) {
        // Do something in response to button click
        // showNotification();
    }

    private void showNotification(String value) {
        Application a = getApplication();
        Context context = a.getApplicationContext();

        // init notification channel
        NotificationProvider.initTaskChannel(context);
        NotificationProvider.initChatChannel(context);

        // show notification
        NotificationContent content = new NotificationContent();
        content.setTitle(value);
        content.setContent(value);
        content.setSound("quantum_bell");
        content.setChannelId("TASK_CHANNEL_ID");
        NotificationProvider.showNotification(context, content);
    }
}
