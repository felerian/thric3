package de.rebreok.thric3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity
{
    public final static String GAME_MODE = "game_mode";
    public final static int MODE_VS_AI = 0;
    public final static int MODE_1P = 1;
    public final static int MODE_2P = 2;
    public final static int MODE_3P = 3;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onButton1Player(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GAME_MODE, MODE_1P);
        startActivity(intent);
    }
    
    public void onButton2Players(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GAME_MODE, MODE_2P);
        startActivity(intent);
    }
    
    public void onButton3Players(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GAME_MODE, MODE_3P);
        startActivity(intent);
    }
    
    public void onButtonHelp(View view) {
        
    }
    
    public void onButtonAbout(View view) {
        
    }
}
