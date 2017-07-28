package com.houkcorp.locationflickr.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.houkcorp.locationflickr.R;
import com.houkcorp.locationflickr.activities.ImageDetailActivity;
import com.houkcorp.locationflickr.adapters.ImageBaseViewAdapter;
import com.houkcorp.locationflickr.databinding.FragmentImageGridViewBinding;
import com.houkcorp.locationflickr.model.FlickrImageSearchResults;
import com.houkcorp.locationflickr.model.ImageBasicInfo;
import com.houkcorp.locationflickr.model.FlickrPhoto;
import com.houkcorp.locationflickr.model.LocationHolder;
import com.houkcorp.locationflickr.service.PhotoService;
import com.houkcorp.locationflickr.service.ServiceFactory;
import com.houkcorp.locationflickr.util.LocationTracker;

import java.util.ArrayList;

public class ImageListViewFragment extends Fragment {
    private GridView mImageGridView;
    private ProgressBar mProgressBar;

    public static ImageListViewFragment newInstance() {
        return new ImageListViewFragment();
    }

    private ArrayList<FlickrPhoto> mFlickrImages;
    private ImageBaseViewAdapter mImageBaseViewAdapter;
    private int mPageNumber = 1;
    private FlickrImageSearchResults mFlickrImageSearchResults;
    private boolean mLoadMoreCalled = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            mFlickrImages = new ArrayList<>();
        }

        handleFetchImages();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentImageGridViewBinding binding =
                FragmentImageGridViewBinding.inflate(inflater, container, false);
        mImageBaseViewAdapter =
                new ImageBaseViewAdapter(getActivity(), mFlickrImages);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        RecyclerView mRecyclerView = binding.imageGridRv;
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mImageGridView.setAdapter(mImageBaseViewAdapter);
        mProgressBar = binding.imageGridPb;

        /*FIXME: Make this get the new stuff properly with refresh.*/
        mImageGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(mFlickrImageSearchResults != null && (firstVisibleItem + visibleItemCount) >=
                        mFlickrImageSearchResults.getPhotos().getPhoto().size() && !mLoadMoreCalled) {
                    mLoadMoreCalled = true;
                    handleFetchImages();
                }
            }
        });

        /*FIXME:Setup a view model with the proper stuff to make this happen from the View*/
        mImageGridView.setOnItemClickListener((parent, view, position, id) -> {
            FlickrPhoto selectedImage = mFlickrImageSearchResults.getPhotos().getPhoto().get(position);
            if (selectedImage != null) {
               Intent detailIntent = ImageDetailActivity.newIntent(getContext(), selectedImage);

                startActivity(detailIntent);
            }
        });

        return binding.getRoot();
    }

    /*FIXME: Is this needed?*/
    @Override
    public void onDetach() {
        super.onDetach();
    }

    /*FIXME: Is this needed?*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelable(Constants.FLICKR_IMAGE, mFlickrImageHolder);
    }

    private void handleFetchImages() {
        if(mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        if(mImageGridView != null) {
            mImageGridView.setVisibility(View.GONE);
        }

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
                .subscribe(new Subscriber<FlickrImageSearchResults>() {
                    @Override
                    public void onCompleted() {
                        /*FIXME: This is broke.*/
                    }

                    @Override
                    public void onError(Throwable e) {
                        /*FIXME: This is broke.*/
                    }

                    @Override
                    public void onNext(FlickrImageSearchResults flickrImageSearchResults) {
                        displayImages(flickrImageSearchResults);
                    }
                });
    }

    private void displayImages(FlickrImageSearchResults flickrImageSearchResults) {
        if(mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }

        if(mImageGridView != null) {
            mImageGridView.setVisibility(View.VISIBLE);
        }

        ImageBasicInfo imageBasicInfo = flickrImageSearchResults.getPhotos();
            if(mPageNumber < imageBasicInfo.getPage() && mPageNumber <= imageBasicInfo.getPages()) {
                mPageNumber++;
            } else if(imageBasicInfo.getPages() == mPageNumber) {
                Toast.makeText(getActivity(), R.string.last_page, Toast.LENGTH_LONG)
                        .show();
            }
            mImageBaseViewAdapter.clearArray();
            mImageBaseViewAdapter.addFlickrImages(imageBasicInfo.getPhoto());
            mImageBaseViewAdapter.notifyDataSetChanged();

        mFlickrImageSearchResults = flickrImageSearchResults;
        mLoadMoreCalled = false;
    }

    /*FIXME: Is this needed?.*/
    public void clearListAndReQuery() {
        /*if(mCallbacks == null) {
            Toast.makeText(getActivity(), R.string.refreshing_please_wait, Toast.LENGTH_LONG).show();
            mFlickrImages = new ArrayList<>();
            mPageNumber = 1;
            mImageBaseViewAdapter.clearArray();
            mImageBaseViewAdapter.notifyDataSetChanged();

            handleFetchImages();
        } else {
            Toast.makeText(getActivity(), R.string.sync_in_progress, Toast.LENGTH_LONG).show();
        }*/
    }
}