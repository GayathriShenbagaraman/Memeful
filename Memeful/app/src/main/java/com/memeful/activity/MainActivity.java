package com.memeful.activity;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.memeful.R;
import com.memeful.adapter.ItemOffsetDecoration;
import com.memeful.adapter.MemesAdapter;
import com.memeful.components.MemesFeedViewModel;
import com.memeful.model.GalleryResponseModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LifecycleOwner {
    private MemesFeedViewModel viewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel =  ViewModelProviders.of(this).get(MemesFeedViewModel.class);

        //Defining recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);

        //Setting layout manager for recyclerview
        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        //Adding item decoration for spacing between items
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(MainActivity.this, R.dimen.itemSpace);
        recyclerView.addItemDecoration(itemDecoration);

        //Setting adapter for recycler view
        final MemesAdapter customAdapter = new MemesAdapter(new ArrayList<GalleryResponseModel>(), MainActivity.this);
        recyclerView.setAdapter(customAdapter);

        //Load data from imgur
        viewModel.loadData();

        //Observe for meme feed changes
        viewModel.getPosts().observe(this, new Observer<List<GalleryResponseModel>>() {
            @Override
            public void onChanged(@Nullable List<GalleryResponseModel> galleryResponseModels) {
                customAdapter.addItems(galleryResponseModels);
            }
        });

        //Scroll listener to call load more at end of scroll
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.loadData();
                }
            }
        });
    }
}
