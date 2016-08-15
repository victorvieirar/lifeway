package com.victorvieira.lifeway.apresentacao.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.extras.ImageManager;
import com.victorvieira.lifeway.apresentacao.fragments.HomeFragment;
import com.victorvieira.lifeway.apresentacao.fragments.HistoricFragment;

import java.io.IOException;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private LayoutInflater mInflater;
    private View nav_header;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;

    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private HistoricFragment historicFragment;
    private FrameLayout flMain;

    private NavigationView navigationView;

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onResume() {
        try {
            if (getIntent().getBooleanExtra("EXIT", false)) {
                finish();
            }
        } catch(Exception e) {
            //do nothing
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(MySingleton.getInstance().getBancoDeDados().getUsuario() == null) {
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            finish();
        }

        initViews();

    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        flMain = (FrameLayout) findViewById(R.id.flMain);

        drawer = (DrawerLayout) findViewById(R.id.dlMain);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nvMain);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        homeFragment = new HomeFragment();
        historicFragment = new HistoricFragment();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flMain, homeFragment);
        fragmentTransaction.commit();

        mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        nav_header = mInflater.inflate(R.layout.nav_header, null, false);

        TextView nome_nav_header = (TextView) nav_header.findViewById(R.id.nome_nav_header);
        TextView sobrenome_nav_header = (TextView) nav_header.findViewById(R.id.sobrenome_nav_header);

        try {
            nome_nav_header.setText(MySingleton.getBancoDeDados().getUsuario().getNome().split(" ")[0]);
            sobrenome_nav_header.setText(MySingleton.getBancoDeDados().getUsuario().getNome().split(" ")[1]);
        } catch(Exception e) {
            sobrenome_nav_header.setText("");
        }

        ImageManager imageManager = new ImageManager();
        ImageView bg_nav_header = (ImageView) nav_header.findViewById(R.id.bg_nav_header);

        try {
            Bitmap bitmap = imageManager.createBitmap(getResources(), R.drawable.man_and_woman_walking,
                    bg_nav_header.getWidth(), bg_nav_header.getHeight());

            bg_nav_header.setImageBitmap(bitmap);
            bitmap.recycle();
            bitmap = null;
            System.gc();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dlMain);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
       // try {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.nav_home:
                    homeFragment.updateFragment(false); // false = nothread; true = thread;
                    fragmentTransaction.replace(R.id.flMain, homeFragment).commit();
                    break;
                case R.id.nav_historic:
                    try {
                        fragmentTransaction.replace(R.id.flMain, historicFragment).commit();
                        historicFragment.updateFragment(true); // true = zera o horario; false = mantem o horario;
                    } catch(Exception e) {
                        //do nothing
                    }
                    break;
                default:
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), "Ainda não disponível", duration);
                    toast.show();
                    break;
            }
        /*} catch(Exception e) {
            //do nothing
        }*/
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void hideToolbar() { toolbar.setVisibility(View.GONE); }
    public void showToolbar() { toolbar.setVisibility(View.VISIBLE); }


}
