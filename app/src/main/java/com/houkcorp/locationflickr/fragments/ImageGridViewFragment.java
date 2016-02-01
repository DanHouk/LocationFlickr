package com.houkcorp.locationflickr.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.houkcorp.locationflickr.model.FlickrImageSearch;
import com.houkcorp.locationflickr.model.FlickrImageSearchBasicInfo;
import com.houkcorp.locationflickr.model.FlickrImageSearchPhoto;
import com.houkcorp.locationflickr.model.LocationHolder;
import com.houkcorp.locationflickr.service.PhotoService;
import com.houkcorp.locationflickr.service.ServiceFactory;
import com.houkcorp.locationflickr.util.LocationTracker;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ImageGridViewFragment extends Fragment {
    private GridView mImageGridView;
    private ProgressBar mProgressBar;

    public static ImageGridViewFragment newInstance() {
        return new ImageGridViewFragment();
    }

    private ArrayList<FlickrImageSearchPhoto> mFlickrImages;
    private ImageBaseViewAdapter mImageBaseViewAdapter;
    private int mPageNumber = 1;
    private FlickrImageSearch mFlickrImageSearch;
    private boolean mLoadMoreCalled = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if(savedInstanceState != null) {
            mFlickrImageHolder = savedInstanceState.getParcelable(Constants.FLICKR_IMAGE);
            if(mFlickrImageHolder != null) {
                mFlickrImages = mFlickrImageHolder.getPhotos();
            }

        } else*/
        if(savedInstanceState == null) {
            mFlickrImages = new ArrayList<>();
        }

        setRetainInstance(true);
        handleFetchImages();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View gridLayoutView = inflater.inflate(R.layout.fragment_image_grid_view, container, false);
        mImageBaseViewAdapter =
                new ImageBaseViewAdapter(getActivity(), mFlickrImages);
        mImageGridView = (GridView)gridLayoutView.findViewById(R.id.image_grid_view);
        mImageGridView.setAdapter(mImageBaseViewAdapter);

        mProgressBar = (ProgressBar) gridLayoutView.findViewById(R.id.image_grid_progressbar);

        mImageGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(mFlickrImageSearch != null && (firstVisibleItem + visibleItemCount) >=
                        mFlickrImageSearch.getPhotos().getPhoto().size() && !mLoadMoreCalled) {
                    mLoadMoreCalled = true;
                    handleFetchImages();
                }
            }
        });

        mImageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FlickrImageSearchPhoto selectedImage = mFlickrImageSearch.getPhotos().getPhoto().get(position);
                if (selectedImage != null) {
                   Intent detailIntent = ImageDetailActivity.newIntent(getContext(), selectedImage);

                    startActivity(detailIntent);
                }
            }
        });

        return gridLayoutView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelable(Constants.FLICKR_IMAGE, mFlickrImageHolder);
    }

    private void handleFetchImages() {
        LocationTracker locationTracker = new LocationTracker(getActivity());
        LocationHolder locationHolder = locationTracker.getLocation();
        String bbox = Double.toString(locationHolder.longitude - 0.2) + "," +
                Double.toString(locationHolder.latitude - 0.2) + "," +
                Double.toString(locationHolder.longitude + 0.2) + "," +
                Double.toString(locationHolder.latitude + 0.2);
        PhotoService photoService = ServiceFactory.getPhotoService();
        Observable<FlickrImageSearch> observable = photoService.getFlickrImage(bbox, mPageNumber);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FlickrImageSearch>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(FlickrImageSearch flickrImageSearch) {
                        displayImages(flickrImageSearch);
                    }
                });
    }

    private void displayImages(FlickrImageSearch flickrImageSearch) {
        if(mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }

        if(mImageGridView != null) {
            mImageGridView.setVisibility(View.VISIBLE);
        }

        FlickrImageSearchBasicInfo flickrImageSearchBasicInfo = flickrImageSearch.getPhotos();
            if(mPageNumber < flickrImageSearchBasicInfo.getPage() && mPageNumber <= flickrImageSearchBasicInfo.getPages()) {
                mPageNumber++;
            } else if(flickrImageSearchBasicInfo.getPages() == mPageNumber) {
                Toast.makeText(getActivity(), R.string.last_page, Toast.LENGTH_LONG)
                        .show();
            }
            mImageBaseViewAdapter.clearArray();
            mImageBaseViewAdapter.addFlickrImages(flickrImageSearchBasicInfo.getPhoto());
            mImageBaseViewAdapter.notifyDataSetChanged();

        mFlickrImageSearch = flickrImageSearch;
        mLoadMoreCalled = false;
    }

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