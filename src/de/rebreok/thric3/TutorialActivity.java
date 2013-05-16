package de.rebreok.thric3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class TutorialActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);
    }
    
    public void onButtonStartGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(MainActivity.GAME_MODE, MainActivity.MODE_TUTORIAL);
        startActivity(intent);
    }
}
