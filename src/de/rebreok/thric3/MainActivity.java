/**
 * This file is part of Thric3.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2013 Kai Körber
 */
package de.rebreok.thric3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;


public class MainActivity extends Activity
{
    public final static String GAME_MODE = "game_mode";
    public final static int MODE_TUTORIAL = -1;
    public final static int MODE_1P = 1;
    public final static int MODE_2P = 2;
    public final static int MODE_3P = 3;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
    }
    
    public void onButtonTutorial(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(MainActivity.GAME_MODE, MainActivity.MODE_TUTORIAL);
        startActivity(intent);
    }
    
    public void onButtonStartGame(View view) {
        Resources res = getResources();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] choices = {res.getString(R.string.item_1_player),
                            res.getString(R.string.item_2_players),
                            res.getString(R.string.item_3_players)};
        builder.setItems(choices, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startGame(which + 1);
                    }
                });
        builder.create().show();
    }
    
    public void startGame(int nr_of_players) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GAME_MODE, nr_of_players);
        startActivity(intent);
    }
    
    public void onButtonHelp(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}
