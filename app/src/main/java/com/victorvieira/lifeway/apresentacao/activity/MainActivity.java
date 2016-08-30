package com.victorvieira.lifeway.apresentacao.activity;

import android.content.res.Resources;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.extras.ImageManager;
import com.victorvieira.lifeway.apresentacao.fragments.HomeFragment;
import com.victorvieira.lifeway.apresentacao.fragments.HistoricFragment;
import com.victorvieira.lifeway.apresentacao.fragments.SettingsFragment;
import com.victorvieira.lifeway.persistencia.ControladorBD;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private int toolbarY;

    private int bgWidth;
    private int bgHeight;

    private ControladorBD bd;
    private LayoutInflater mInflater;
    private View nav_header;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;

    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private HistoricFragment historicFragment;
    private SettingsFragment settingsFragment;
    private FrameLayout flMain;

    private NavigationView navigationView;

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onResume() {
        try {
            if (getIntent().getBooleanExtra("EXIT", false)) {
                finish();
            }
            if(homeFragment != null) {
                homeFragment.updateFragment(false);
            }
            if(historicFragment != null) {
                historicFragment.updateFragment(false);
            }
            if(settingsFragment != null) {
                settingsFragment.updateFragment(false);

                TextView nome_nav_header = (TextView) nav_header.findViewById(R.id.nome_nav_header);
                TextView sobrenome_nav_header = (TextView) nav_header.findViewById(R.id.sobrenome_nav_header);

                try {
                    nome_nav_header.setText(bd.getUsuario().getNome().split(" ")[0]);
                    sobrenome_nav_header.setText(bd.getUsuario().getNome().split(" ")[1]);
                } catch(Exception e) {
                    sobrenome_nav_header.setText("");
                }
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

        bd = new ControladorBD(this);

        if(!(bd.hasUser())) {
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            finish();
        }
        if(bd.hasUser()) {
            initViews();
        }

    }

    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbarY = (int) toolbar.getY();

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
        settingsFragment = new SettingsFragment();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flMain, homeFragment);
        fragmentTransaction.commit();

        nav_header = navigationView.getHeaderView(0);

        TextView nome_nav_header = (TextView) nav_header.findViewById(R.id.nome_nav_header);
        TextView sobrenome_nav_header = (TextView) nav_header.findViewById(R.id.sobrenome_nav_header);

        try {
            nome_nav_header.setText(bd.getUsuario().getNome().split(" ")[0]);
            sobrenome_nav_header.setText(bd.getUsuario().getNome().split(" ")[1]);
        } catch(Exception e) {
            sobrenome_nav_header.setText("");
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        bgWidth = navigationView.getWidth();
        bgHeight = (int) (160 * Resources.getSystem().getDisplayMetrics().density);

        ImageManager imageManager = new ImageManager();
        ImageView bg_nav_header = (ImageView) nav_header.findViewById(R.id.bg_nav_header);

        try {
            Bitmap bitmap = imageManager.createBitmap(getResources(), R.drawable.man_and_woman_walking,
                    bgWidth, bgHeight);

            bg_nav_header.setImageBitmap(bitmap);
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
                        /** do nothing */
                    }
                    break;
                case R.id.nav_settings:
                    try {
                        fragmentTransaction.replace(R.id.flMain, settingsFragment).commit();
                    } catch(Exception e) {
                        /** do nothing */
                    }
                    break;
                default:
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), "Ainda não disponível", duration);
                    toast.show();
                    break;
            }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void hideToolbar(int scroll, int posY, int bgHeight) {
        if(posY == (bgHeight - toolbar.getHeight())) {
            toolbar.setY((float) scroll);
        }
    }
    public void showToolbar(int posY, int bgHeight) {
        if(posY < (bgHeight - toolbar.getHeight())) {
            toolbar.setY((float) toolbarY);
        }
    }


}
