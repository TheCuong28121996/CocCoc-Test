package com.example.cococ.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cococ.R;
import com.example.cococ.utils.SharedPrefs;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    private SwitchCompat switchCompat;
    private RecyclerView recyclerView;
    private MaterialToolbar toolbar;
    private MainAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private TextView tvNotFound;
    private MainViewModel viewModel;
    private boolean isRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();
    }

    public void initView() {
        toolbar = (MaterialToolbar) findViewById(R.id.tool_bar);
        MenuItem menuItem = toolbar.getMenu().findItem(R.id.menu_switch);
        switchCompat = (SwitchCompat) menuItem.getActionView();
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        tvNotFound = (TextView) findViewById(R.id.tv_not_found);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setItemViewCacheSize(25);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        adapter = new MainAdapter(this);
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(() ->{
            isRefresh = true;
            viewModel.getNewsFeed();
        });
    }

    public void initData() {
        boolean isMode = SharedPrefs.getInstance().get("IS_MODE", Boolean.class);
        setMode(isMode);

        switchCompat.setText(isMode ? "Dark mode" : "Light mode");
        switchCompat.setChecked(isMode);
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> handleSwitch(isChecked));

        viewModel.getNewsFeed().observe(this, rssFeed ->{
            if(isRefresh){
                adapter.clear();
            }

            if(rssFeed != null){
                adapter.addAll(rssFeed.getArticleList());
                toolbar.setTitle(rssFeed.getChannelTitle());
                tvNotFound.setVisibility(View.GONE);
            }else {
                tvNotFound.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getLoading().observe(this, isLoading -> refreshLayout.setRefreshing(isLoading));
    }

    private void handleSwitch(boolean isMode){
        setMode(isMode);
        switchCompat.setText(isMode ? "Dark mode" : "Light mode");
        SharedPrefs.getInstance().put("IS_MODE", isMode);
    }

    private void setMode(boolean isNight){
        AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }
}
