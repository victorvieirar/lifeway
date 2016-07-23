package com.victorvieira.lifeway.apresentacao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.github.fabtransitionactivity.SheetLayout;
import com.victorvieira.lifeway.MySingleton;
import com.victorvieira.lifeway.R;
import com.victorvieira.lifeway.apresentacao.fragments.HomeFragment;
import com.victorvieira.lifeway.apresentacao.fragments.HistoricFragment;
import com.victorvieira.lifeway.apresentacao.fragments.MyFragment;


public class MainActivity extends BaseActivity implements SheetLayout.OnFabAnimationEndListener {

    private SheetLayout mSheetLayout;
    private FloatingActionButton mFab;

    private static final int REQUEST_CODE = 1;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

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

        mSheetLayout = (SheetLayout) findViewById(R.id.bottom_sheet);
        mFab = (FloatingActionButton) findViewById(R.id.fabAddAlimentoFast);

        mSheetLayout.setFab(mFab);
        mSheetLayout.setFabAnimationEndListener(this);
    }

    @Override
    public void onFabAnimationEnd() {
        Intent intent = new Intent(this, AddAlimentoActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            mSheetLayout.contractFab();
        }
    }

    public void showFloatingActionButton() {
        mFab.show();
    }

    public void hideFloatingActionButton() {
        mFab.hide();
    }

    private void setupListeners() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSheetLayout.expandFab();
            }
        });
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
                    case 0: setupTabIcons('i'); setTitle("Início"); showFloatingActionButton(); break;
                    case 1:
                        setupTabIcons('h');
                        hideFloatingActionButton();
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
