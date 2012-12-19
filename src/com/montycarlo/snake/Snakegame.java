package com.montycarlo.snake;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.Window;

public class Snakegame extends Activity {
	GameClient myClient;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snakegame);
    }
    @Override
    public void onStart(){
    	super.onStart();
    	myClient = (GameClient)findViewById(R.id.GameClient1);
    	myClient.setSharedPrefs(getSharedPreferences("com.montycarlo.snake", Context.MODE_PRIVATE));
    }
    @Override
    public void onResume(){
    	super.onResume();
    	myClient.onResume();
    	myClient.setClickable(true);
    	myClient.setOnTouchListener(myClient);
    }
    @Override
    public void onStop(){
    	super.onStop();
    	myClient.onStop();
    	myClient.setClickable(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_snakegame, menu);
        return true;
    }
}
