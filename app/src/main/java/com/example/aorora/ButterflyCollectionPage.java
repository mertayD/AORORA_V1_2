package com.example.aorora;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.aorora.adapter.GridViewAdapter;

import com.example.aorora.interfaces.OnItemClickListener;
import com.example.aorora.model.RetroPhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ButterflyCollectionPage extends AppCompatActivity implements View.OnClickListener {

    private com.example.aorora.adapter.GridViewAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    ImageButton home_button_bottombar;
    ImageButton profile_button_bottombar;
    ImageButton community_button_bottombar;
    ImageButton quest_button_bottombar;
    ImageButton back_button;
    Context butterflyCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterfly_collection_page);

        butterflyCollection = this;
        progressDoalog = new ProgressDialog(butterflyCollection);

        home_button_bottombar = (ImageButton) findViewById(R.id.home_button_bottom_bar);
        profile_button_bottombar = (ImageButton) findViewById(R.id.profile_button_bottom_bar);
        community_button_bottombar = (ImageButton) findViewById(R.id.community_button_bottom_bar);
        quest_button_bottombar = (ImageButton) findViewById(R.id.quest_button_bottom_bar);
        back_button = (ImageButton) findViewById(R.id.back_button_butterflyc_page);
        home_button_bottombar.setOnClickListener(this);
        profile_button_bottombar.setOnClickListener(this);
        community_button_bottombar.setOnClickListener(this);
        quest_button_bottombar.setOnClickListener(this);
        back_button.setOnClickListener(this);
        progressDoalog.setMessage("Friends Loading....");
        progressDoalog.show();

        com.example.aorora.network.GetDataService service = com.example.aorora.network.RetrofitClientInstance.getRetrofitInstance().create(com.example.aorora.network.GetDataService.class);

        Call<List<RetroPhoto>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<RetroPhoto>>() {

            @Override
            public void onResponse(Call<List<com.example.aorora.model.RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                progressDoalog.dismiss();
                generateDataListGrid(response.body());
            }

            @Override
            public void onFailure(Call<List<com.example.aorora.model.RetroPhoto>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(butterflyCollection, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataListGrid(List<com.example.aorora.model.RetroPhoto> photoList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        adapter = new com.example.aorora.adapter.GridViewAdapter(butterflyCollection, photoList, new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent details = new Intent(butterflyCollection, ButterflyDetailsPage.class);
                startActivity(details);
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(butterflyCollection, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        Intent to_navigate;
        if(view_id == profile_button_bottombar.getId())
        {
            to_navigate = new Intent(butterflyCollection, ProfilePage.class );
            startActivity(to_navigate);
        }
        else if(view_id == quest_button_bottombar.getId())
        {
            to_navigate = new Intent(butterflyCollection, MindfullnessSelection.class);
            startActivity(to_navigate);
        }
        else if(view_id == home_button_bottombar.getId())
        {
            to_navigate = new Intent(butterflyCollection, HomeScreen.class);
            startActivity(to_navigate);
        }
        else if(view_id == community_button_bottombar.getId())
        {
            to_navigate = new Intent(butterflyCollection, CommunityPage.class);
            startActivity(to_navigate);
        }
        else if(view_id == back_button.getId())
        {
            to_navigate = new Intent(butterflyCollection, ProfilePage.class);
            startActivity(to_navigate);
        }
    }
}
