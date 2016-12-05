package br.edu.ufabc.padm.pocketmentaltest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;


public class MEEMLinguagem3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meem_ling3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO ajustar o menu?
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
