package com.example.cococ.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cococ.data.RssFeed;
import com.example.cococ.remote.APIClient;
import com.example.cococ.remote.APIInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<RssFeed> repoRssFeed = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoading = new MutableLiveData<>();

    public LiveData<RssFeed> getRepoNewsFeed() {
        return repoRssFeed;
    }

    public MutableLiveData<Boolean> getRepoLoading() {
        return repoLoading;
    }

    public void fetchNewsFeed() {
        repoLoading.postValue(true);

        Call<RssFeed> call = APIClient.getClient().create(APIInterface.class).getFeed();
        call.enqueue(new Callback<RssFeed>() {
            @Override
            public void onResponse(@NotNull Call<RssFeed> rssFeedCall, @NotNull Response<RssFeed> response) {
                repoLoading.postValue(false);
                repoRssFeed.postValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<RssFeed> call, @NotNull Throwable t) {
                call.cancel();
                repoLoading.postValue(false);
                repoRssFeed.postValue(null);
                Log.d("DevDebug", "fetchNewsFeed fail " + t.getMessage());
            }
        });
    }
}
