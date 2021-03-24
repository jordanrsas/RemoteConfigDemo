package com.dtse.remote.config.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huawei.agconnect.remoteconfig.AGConnectConfig;
import com.huawei.agconnect.remoteconfig.ConfigValues;
import com.huawei.hmf.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private static final String GREETING_KEY = "GREETING_KEY";
    private static final String SET_BOLD_KEY = "SET_BOLD_KEY";
    private AGConnectConfig config;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.greeting);
        Button button = findViewById(R.id.fetch_button);

        config = AGConnectConfig.getInstance();
        config.applyDefault(R.xml.remote_config);

        textView.setText(config.getValueAsString(GREETING_KEY));
        Boolean isBold = config.getValueAsBoolean(SET_BOLD_KEY);
        if (isBold){
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }

        button.setOnClickListener(view -> {
            fetchAndApply();
        });
    }

    private void fetchAndApply(){
        config.fetch(0).addOnSuccessListener(configValues -> {
            config.apply(configValues);
            updateUI();
        }).addOnFailureListener(exception ->{
            textView.setText("fetch setting failed: " + exception.getMessage());
        });
    }

    private void updateUI(){
        String text = config.getValueAsString(GREETING_KEY);
        Boolean isBold = config.getValueAsBoolean(SET_BOLD_KEY);
        textView.setText(text);
        if (isBold){
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    }
}