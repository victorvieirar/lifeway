package com.victorvieira.lifeway.apresentacao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.dialogs.MyDialogFragment;
import com.victorvieira.lifeway.apresentacao.fragments.HomeFragment;
import com.victorvieira.lifeway.apresentacao.fragments.HistoricFragment;
import com.victorvieira.lifeway.apresentacao.fragments.MyFragment;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabAddAlimento;

    private boolean mIsLargeLayout;

    private int[] tabIcons = {
            R.drawable.ic_home_24dp,
            R.drawable.ic_restore_24dp,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Início");

        if(MySingleton.getInstance().getBancoDeDados().getUsuario() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        initViews();

        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);

        setupListeners();



    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons('i');

        fabAddAlimento = (FloatingActionButton) findViewById(R.id.fabAddAlimentoFast);
        fabAddAlimento.setRippleColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    private void setupListeners() {
        fabAddAlimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogAlimento();
            }
        });

    }

    private void createDialogAlimento() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        MyDialogFragment dialogFragment = new MyDialogFragment();

        if (mIsLargeLayout) {
            dialogFragment.show(fragmentManager, "dialog");
        } else {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(android.R.id.content, dialogFragment).addToBackStack(null).commit();
        }


    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void setupTabIcons(char state) {
        switch(state) {
            case 'i':
                tabIcons[0] = R.drawable.ic_home_selected_24dp;
                tabIcons[1] = R.drawable.ic_restore_24dp;
                tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                break;
            case 'h':
                tabIcons[0] = R.drawable.ic_home_24dp;
                tabIcons[1] = R.drawable.ic_restore_selected_24dp;
                tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                break;
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        final ViewPagerAdapter ADAPTER = new ViewPagerAdapter(getSupportFragmentManager());
        ADAPTER.addFrag(new HomeFragment(), "Início");
        ADAPTER.addFrag(new HistoricFragment(), "Histórico");
        viewPager.setAdapter(ADAPTER);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch(position) {
                    case 0: setupTabIcons('i'); setTitle("Início"); break;
                    case 1:
                        setupTabIcons('h');
                        if(MySingleton.getBancoDeDados().getUsuario().getConsumo() != null) {
                            ADAPTER.getItem(position).updateFragment();
                        }
                        setTitle("Histórico"); break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                //do nothing
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<MyFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public MyFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(MyFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            // return null to display only the icon
            return null;
        }


    }

}
