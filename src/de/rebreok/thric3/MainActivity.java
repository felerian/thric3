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
    public final static int MODE_4P = 4;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onButtonSolitaire(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GAME_MODE, MODE_1P);
        startActivity(intent);
    }
    
    public void onButtonVsAi(View view) {
        
    }
    
    public void onButtonMultiplayer(View view) {
        
    }
    
    public void onButtonHelp(View view) {
        
    }
    
    public void onButtonAbout(View view) {
        
    }
}
