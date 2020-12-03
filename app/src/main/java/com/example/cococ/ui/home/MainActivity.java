package com.example.cococ.ui.home;

import android.content.Intent;
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
import com.example.cococ.data.Article;
import com.example.cococ.ui.detail.DetailActivity;
import com.example.cococ.utils.ItemClickListener;
import com.example.cococ.utils.SharedPrefs;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    private SwitchCompat switchCompat;
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

    /**
     * This is func initView
     */
    public void initView() {
        toolbar = (MaterialToolbar) findViewById(R.id.tool_bar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        MenuItem menuItem = toolbar.getMenu().findItem(R.id.menu_switch);
        switchCompat = (SwitchCompat) menuItem.getActionView();
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        tvNotFound = (TextView) findViewById(R.id.tv_not_found);

        // Setup RecyclerView
        recyclerView.setItemViewCacheSize(25);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Init ViewModel
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Init adapter
        adapter = new MainAdapter(this);
        adapter.setOnItemClickListener(listener);
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(() -> {
            isRefresh = true;
            // Get data from VNExpress
            viewModel.fetchNewsFeed();
        });
    }

    /**
     * This is func initData
     */
    public void initData() {
        // Get status mode
        boolean isDarkMode = SharedPrefs.getInstance().get("IS_DARK_MODE", Boolean.class);

        setMode(isDarkMode);
        // Set Switch by status mode
        switchCompat.setText(isDarkMode ? "Dark mode" : "Light mode");
        switchCompat.setChecked(isDarkMode);
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> handleSwitch(isChecked));

        // Listener data from api
        viewModel.getRepoNewsFeed().observe(this, rssFeed -> {
            if (rssFeed != null) {
                if (isRefresh) {
                    adapter.clear();
                }
                adapter.addAll(rssFeed.getArticleList());
                toolbar.setTitle(rssFeed.getChannelTitle());
                tvNotFound.setVisibility(View.GONE);
            } else {
                tvNotFound.setVisibility(View.VISIBLE);
            }
        });

        // Listener status loading
        viewModel.getRepoLoading().observe(this, isLoading -> refreshLayout.setRefreshing(isLoading));

        viewModel.fetchNewsFeed();
    }

    /**
     * This is func handle Switch change checked
     *
     * @param isDarkMode
     */
    private void handleSwitch(boolean isDarkMode) {
        setMode(isDarkMode);
        switchCompat.setText(isDarkMode ? "Dark mode" : "Light mode");
        // Save mode status
        SharedPrefs.getInstance().put("IS_DARK_MODE", isDarkMode);
    }

    private void setMode(boolean isNight) {
        AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    private final ItemClickListener<Article> listener = (view, position, data) -> {
        Bundle bundle = new Bundle();
        bundle.putString("url", data.getLink());

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("data", bundle);

        startActivity(intent);
    };
}
