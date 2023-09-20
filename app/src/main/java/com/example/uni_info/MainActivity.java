package com.example.uni_info;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni_info.UniversityAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UniversityAdapter.OnWebPagesClickListener {

    private RecyclerView recyclerView;
    private UniversityAdapter adapter;
    private List<University> universityList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Start the data refresh service when the app starts
        Intent serviceIntent = new Intent(this, DataService.class);
        ContextCompat.startForegroundService(this, serviceIntent);

        // Create the Retrofit instance
        UniversityApiService apiService = RetrofitClient.getInstance().create(UniversityApiService.class);

        // Make the API request
        Call<List<University>> call = apiService.getUniversities();
        call.enqueue(new Callback<List<University>>() {
            @Override
            public void onResponse(@NonNull Call<List<University>> call, @NonNull Response<List<University>> response) {
                if (response.isSuccessful()) {
                    universityList = response.body();

                    // Create and set the adapter with the web pages click listener
                    adapter = new UniversityAdapter(universityList, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    // Handle API request error
                    int statusCode = response.code();
                    if (statusCode == 401) {
                        // Unauthorized, handle authentication error
                    } else if (statusCode == 404) {
                        // Not found, handle resource not found error
                    } else {
                        // Handle other errors
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<University>> call, @NonNull Throwable t) {
                // Handle network or request failure here
                // This is where you handle errors such as no internet connection or server issues
            }
        });
    }


    @Override
    public void onWebPagesClick(List<String> webPages) {
        // Handle web pages link click by opening them in WebViewActivity
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putStringArrayListExtra(WebViewActivity.EXTRA_WEB_PAGES, new ArrayList<>(webPages));
        startActivity(intent);
    }


//    //for refresh in 10 sec
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Start the data refresh service when the app starts
//        Intent serviceIntent = new Intent(this, DataService.class);
//        ContextCompat.startForegroundService(this, serviceIntent);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Stop the data refresh service when the app is closed
        Intent serviceIntent = new Intent(this, DataService.class);
        stopService(serviceIntent);
    }
}
