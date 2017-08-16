package com.houkcorp.locationflickr.fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.houkcorp.locationflickr.R;
import com.houkcorp.locationflickr.adapters.ImageListRecyclerViewAdapter;
import com.houkcorp.locationflickr.databinding.FragmentImageListViewBinding;
import com.houkcorp.locationflickr.model.FlickrImageSearchResults;
import com.houkcorp.locationflickr.model.ImageBasicInfo;
import com.houkcorp.locationflickr.model.LocationHolder;
import com.houkcorp.locationflickr.service.PhotoService;
import com.houkcorp.locationflickr.service.ServiceFactory;
import com.houkcorp.locationflickr.util.LocationTracker;
import com.houkcorp.locationflickr.util.UIUtils;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * TODO: Need to hook up paging.  Need to hook up diffing.
 * Update location services.
 */
public class ImageListViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageListRecyclerViewAdapter mImageListRecyclerViewAdapter;
    private int mPageNumber = 1;
    private boolean isLoading = false;

    public static ImageListViewFragment newInstance() {
        return new ImageListViewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentImageListViewBinding binding =
                FragmentImageListViewBinding.inflate(inflater, container, false);
        mImageListRecyclerViewAdapter =
                new ImageListRecyclerViewAdapter(new ArrayList<>());

        //Recycler View
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView = binding.imageGridRv;
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mImageListRecyclerViewAdapter);

        //Progress Bar
        mProgressBar = binding.imageGridPb;

        //Swipe Refresh Layout
        mSwipeRefreshLayout = binding.imageGridSrl;
        mSwipeRefreshLayout.setOnRefreshListener(this);

        handleFetchImages();

        /*FIXME: Make this get the new stuff properly with refresh.*/
        binding.imageGridRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!binding.imageGridRv.canScrollVertically(1)) {
                    isLoading = true;
                    handleFetchImages();
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_image_grid_view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_menu:
                String displayMessage;
                try {
                    int versionCode =
                            getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionCode;
                    displayMessage = getString(R.string.about_location_flickr, versionCode);
                } catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    displayMessage = "N/A";
                }

                UIUtils.showDialogMessage(getContext(), R.string.about, displayMessage);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleFetchImages() {
        if (isLoading) {
            return;
        }

        isLoading = true;
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);

        LocationTracker locationTracker = new LocationTracker(getActivity());
        LocationHolder locationHolder = locationTracker.getLocation();
        String bbox = Double.toString(locationHolder.longitude - 0.2) + "," +
                Double.toString(locationHolder.latitude - 0.2) + "," +
                Double.toString(locationHolder.longitude + 0.2) + "," +
                Double.toString(locationHolder.latitude + 0.2);
        PhotoService photoService = ServiceFactory.getPhotoService();
        Observable<FlickrImageSearchResults> observable = photoService.getFlickrImage(bbox, mPageNumber);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayImages, error -> Log.e("ImageListView", error.getLocalizedMessage()));
    }

    private void displayImages(FlickrImageSearchResults flickrImageSearchResults) {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

        ImageBasicInfo imageBasicInfo = flickrImageSearchResults.getPhotos();
        if (mPageNumber < imageBasicInfo.getPage() && mPageNumber <= imageBasicInfo.getPages()) {
            mPageNumber++;
        } else if (imageBasicInfo.getPages() == mPageNumber) {
            Toast.makeText(getActivity(), R.string.last_page, Toast.LENGTH_LONG)
                    .show();
        }
        mImageListRecyclerViewAdapter.addFlickrImages(flickrImageSearchResults.getPhotos().getPhoto());
        mRecyclerView.setAdapter(mImageListRecyclerViewAdapter);
        mImageListRecyclerViewAdapter.notifyDataSetChanged();

        mSwipeRefreshLayout.setRefreshing(false);
        isLoading = false;
    }

    @Override
    public void onRefresh() {
        mImageListRecyclerViewAdapter.clearArray();
        handleFetchImages();
    }
}