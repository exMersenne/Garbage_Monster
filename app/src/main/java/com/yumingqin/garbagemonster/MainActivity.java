package com.yumingqin.garbagemonster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    String message = "java\com\yumingqin\garbagemonster\MainActivity.java";
    String message = "com.yumingq.garbagemonster.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        Button startGame = (Button) findViewById(R.id.playGameButton);
        Toast.makeText(getApplicationContext(), "Game Start Testing", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, Game.class);
        intent.putExtra(message, "Game Start!");
        startActivity(intent);
    }
}
