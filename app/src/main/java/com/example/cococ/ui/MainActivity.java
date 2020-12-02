package com.example.cococ.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cococ.R;
import com.example.cococ.data.RssFeed;
import com.example.cococ.remote.APIClient;
import com.example.cococ.remote.APIInterface;
import com.example.cococ.utils.SharedPrefs;
import com.google.android.material.appbar.MaterialToolbar;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {

    private SwitchCompat switchCompat;
    private RecyclerView recyclerView;
    private MaterialToolbar toolbar;
    private MainAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        getNewsFeed();

        initData();
    }

    public void initView() {
        toolbar = (MaterialToolbar) findViewById(R.id.tool_bar);
        MenuItem menuItem = toolbar.getMenu().findItem(R.id.menu_switch);
        switchCompat = (SwitchCompat) menuItem.getActionView();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setItemViewCacheSize(25);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void initData() {
        boolean isMode = SharedPrefs.getInstance().get("IS_MODE", Boolean.class);
        setMode(isMode);

        switchCompat.setText(isMode ? "Dark mode" : "Light mode");
        switchCompat.setChecked(isMode);
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> handleSwitch(isChecked));
    }

    private void handleSwitch(boolean isMode){
        setMode(isMode);
        switchCompat.setText(isMode ? "Dark mode" : "Light mode");
        SharedPrefs.getInstance().put("IS_MODE", isMode);
    }

    private void setMode(boolean isNight){
        AppCompatDelegate.setDefaultNightMode(isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void getNewsFeed(){
        Call<RssFeed> call = APIClient.getClient().create(APIInterface.class).getFeed();
        call.enqueue(new Callback<RssFeed>() {
            @Override
            public void onResponse(Call<RssFeed> rssFeedCall, Response<RssFeed> response) {
                Log.d("DevDebug","getNewsFeed onResponse "+response.code());
                RssFeed rssFeed = response.body();

                toolbar.setTitle(rssFeed.getChannelTitle());
                adapter.pushData(rssFeed.getArticleList());
            }

            @Override
            public void onFailure(Call<RssFeed> call, Throwable t) {
                call.cancel();
                Log.d("DevDebug","getNewsFeed fail "+ t.getMessage());
            }
        });
    }
}
