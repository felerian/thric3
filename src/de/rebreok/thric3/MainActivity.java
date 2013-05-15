package de.rebreok.thric3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity
{
    public final static String GAME_MODE = "game_mode";
    public final static int MODE_TUTORIAL = -1;
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
    
    public void onButtonTutorial(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GAME_MODE, MODE_TUTORIAL);
        startActivity(intent);
    }
    
    public void onButtonSolitaire(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GAME_MODE, MODE_1P);
        startActivity(intent);
    }
    
    public void onButtonMultiplayer(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title_number_of_players);
        String[] choices = {"2", "3", "4"};
        builder.setItems(choices, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startMultiplayer(which + 2);
                    }
                });
        builder.create().show();
    }
    
    public void startMultiplayer(int nr_of_players) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GAME_MODE, nr_of_players);
        startActivity(intent);
    }
    
    public void onButtonVsAi(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GAME_MODE, MODE_VS_AI);
        startActivity(intent);
    }
    
    public void onButtonHelp(View view) {
        
    }
    
    public void onButtonAbout(View view) {
        
    }
}
