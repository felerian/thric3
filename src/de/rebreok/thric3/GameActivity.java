package de.rebreok.thric3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        switch (getIntent().getIntExtra(MainActivity.GAME_MODE, -1))
        {
            case 1:
            setContentView(R.layout.game1);
            break;
        }
    }
    
}
