package com.houkcorp.locationflickr.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.houkcorp.locationflickr.Constants;
import com.houkcorp.locationflickr.R;
import com.houkcorp.locationflickr.activities.ImageDetailActivity;
import com.houkcorp.locationflickr.model.FlickrPhoto;
import com.houkcorp.locationflickr.model.ImageMetaDataResults;
import com.houkcorp.locationflickr.model.PhotoMetaData;
import com.houkcorp.locationflickr.service.MetaDataService;
import com.houkcorp.locationflickr.service.ServiceFactory;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*FIXME: Remove anything no longer needed.*/
public class ImageDetailViewFragment extends Fragment {
    private Bitmap mBitmap;
    private FlickrPhoto mFlickrPhoto;

    private ImageView mImageView;
    private TextView mImageTitleTextView;
    private TextView mPostedDateTextView;
    private TextView mLocationTextView;
    private TextView mTakenDateTextView;
    private TextView mUserNameTextView;
    private LinearLayout mUserNameLayout;
    private LinearLayout mLocationLayout;
    private LinearLayout mViewsLayout;
    private TextView mViewCountTextView;
    private LinearLayout mDescriptionLayout;
    private TextView mDescriptionTextView;
    private LinearLayout mTakenDateLayout;
    private LinearLayout mPostedDateLayout;
    private LinearLayout mImageDetailLayout;
    private ProgressBar mImageDetailProgressBar;

    public static ImageDetailViewFragment newInstance(FlickrPhoto flickrPhoto) {
        ImageDetailViewFragment imageDetailViewFragment = new ImageDetailViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(ImageDetailActivity.EXTRA_FLICKR_PHOTO, flickrPhoto);
        imageDetailViewFragment.setArguments(args);
        return imageDetailViewFragment;
    }

    /*FIXME: Add logiv\c or remove.*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null) {
            *//*mFlickrImage = extras.getParcelable(Constants.FLICKR_IMAGE);
            if(mFlickrImage != null) {
                getImageInBackground(mFlickrImage);
                getMetaData();
            }*//*
            mFlickrPhoto = extras.getParcelable(ImageDetailActivity.EXTRA_FLICKR_PHOTO);
            Picasso.with(getContext())
                    .load(String.format(Locale.getDefault(), Constants.DEFAULT_IMAGE_URL,
                            mFlickrPhoto.getFarm(), mFlickrPhoto.getServer(),
                            mFlickrPhoto.getId(), mFlickrPhoto.getSecret(), "t"))
                    .into(mImageView);
        } else if(savedInstanceState != null) {
            *//*mFlickrImage = savedInstanceState.getParcelable(Constants.FLICKR_IMAGE);
            mBitmap = savedInstanceState.getParcelable("bitmap");
            mImageMetaDataResults = savedInstanceState.getParcelable("image_meta_data");*//*
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_detail_view, container, false);

        mImageView = (ImageView)view.findViewById(R.id.image_detail_image_view_id);
        if(mBitmap != null) {
            mImageView.setImageBitmap(mBitmap);
        }

        /*mImageDetailProgressBar = (ProgressBar)view.findViewById(R.id.image_grid_progressbar);*/

        mImageDetailLayout = (LinearLayout)view.findViewById(R.id.image_detail_layout);
        mUserNameLayout = (LinearLayout)view.findViewById(R.id.user_name_layout);
        mLocationLayout = (LinearLayout)view.findViewById(R.id.location_layout);
        mViewsLayout = (LinearLayout)view.findViewById(R.id.views_layout);
        mDescriptionLayout = (LinearLayout)view.findViewById(R.id.description_layout);
        mTakenDateLayout = (LinearLayout)view.findViewById(R.id.taken_date_layout);
        mPostedDateLayout = (LinearLayout)view.findViewById(R.id.posted_date_layout);

        mImageTitleTextView = (TextView)view.findViewById(R.id.image_title_text_view_id);
        mViewCountTextView = (TextView)view.findViewById(R.id.view_count_text_view);
        mUserNameTextView = (TextView)view.findViewById(R.id.user_name_text_view_id);
        mLocationTextView = (TextView)view.findViewById(R.id.location_text_view_id);
        mDescriptionTextView = (TextView)view.findViewById(R.id.description_text_view_id);
        mPostedDateTextView = (TextView)view.findViewById(R.id.posted_date_text_view);
        mTakenDateTextView = (TextView)view.findViewById(R.id.taken_date_text_view);

        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null) {
            mFlickrPhoto = extras.getParcelable(ImageDetailActivity.EXTRA_FLICKR_PHOTO);
            Picasso.with(getContext())
                    .load(String.format(Locale.getDefault(), Constants.DEFAULT_IMAGE_URL,
                            mFlickrPhoto.getFarm(), mFlickrPhoto.getServer(),
                            mFlickrPhoto.getId(), mFlickrPhoto.getSecret(), "z"))
                    .into(mImageView);

            getMetaData();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
       // outState.putParcelable(Constants.FLICKR_IMAGE, mFlickrImage);
        outState.putParcelable("bitmap", mBitmap);

        super.onSaveInstanceState(outState);
    }

    private void getMetaData() {
        MetaDataService metaDataService = ServiceFactory.getMetaDataService();
        Observable<ImageMetaDataResults> observable = metaDataService.getMetaData(mFlickrPhoto.getId());
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ImageMetaDataResults>() {
                    @Override
                    public void onCompleted() {
                        /*FIXME: This is broke.*/
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("This is where we are at: onError: " + e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(ImageMetaDataResults imageMetaDataResults) {
                        displayMetaData(imageMetaDataResults);
                    }
                });
        }

    private void displayMetaData(ImageMetaDataResults imageMetaDataResults) {
        PhotoMetaData photoData = imageMetaDataResults.getPhoto();
        if (photoData.getTitle() != null && !TextUtils.isEmpty(photoData.getTitle().getContent())) {
            mImageTitleTextView.setText(photoData.getTitle().getContent());
        } else {
            mImageTitleTextView.setText("N/A");
        }

        if (!TextUtils.isEmpty(photoData.getViews())) {
            mViewCountTextView.setText(photoData.getViews());
            mViewsLayout.setVisibility(View.VISIBLE);
        } else {
            mViewCountTextView.setText("");
            mViewsLayout.setVisibility(View.GONE);
        }

        if (photoData.getOwner() != null && !TextUtils.isEmpty(photoData.getOwner().getUserName())) {
            mUserNameTextView.setText(photoData.getOwner().getUserName());
            mUserNameLayout.setVisibility(View.VISIBLE);
        } else {
            mUserNameTextView.setText("");
            mUserNameLayout.setVisibility(View.GONE);
        }

        if (photoData.getOwner() != null && !TextUtils.isEmpty(photoData.getOwner().getLocation())) {
            mLocationTextView.setText(photoData.getOwner().getLocation());
            mLocationLayout.setVisibility(View.VISIBLE);
        } else {
            mLocationTextView.setText("");
            mLocationLayout.setVisibility(View.GONE);
        }

        if (photoData.getDescription() != null && !TextUtils.isEmpty(photoData.getDescription().getContent())) {
            mDescriptionTextView.setText(photoData.getDescription().getContent());
            mDescriptionLayout.setVisibility(View.VISIBLE);
        } else {
            mDescriptionTextView.setText("");
            mDescriptionLayout.setVisibility(View.GONE);
        }

        if (photoData.getDates() != null && !TextUtils.isEmpty(photoData.getDates().getPosted())) {
            //Convert to date and change names of the fields.
            Date newDate = new Date(Integer.parseInt(photoData.getDates().getPosted()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
            mPostedDateTextView.setText(dateFormat.format(newDate));
            mPostedDateLayout.setVisibility(View.VISIBLE);
        } else {
            mPostedDateTextView.setText("");
            mPostedDateLayout.setVisibility(View.GONE);
        }

        if (photoData.getDates() != null && photoData.getDates().getTaken() != null) {
            //Format this date and change names of the fields.
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
            mTakenDateTextView.setText(dateFormat.format(photoData.getDates().getTaken()));
            mTakenDateLayout.setVisibility(View.VISIBLE);
        } else {
            mTakenDateTextView.setText("");
            mTakenDateLayout.setVisibility(View.GONE);
        }

        /*mImageDetailProgressBar.setVisibility(View.GONE);*/
        mImageDetailLayout.setVisibility(View.VISIBLE);
        //getComments() content, List getTags()
    }

    //private void getMetaData() {
        /*new AsyncTask<Void, Void, ImageMetaDataResults>() {
            @Override
            protected ImageMetaDataResults doInBackground(Void... params) {
                *//*ImageMetaDataResults imageMetaData = null;
                try {
                    String stringURL = String.format(Constants.META_DATA_URL, mFlickrImage.getId());
                    URL url = new URL(stringURL);

                    InputStream inputStream = NetworkUtilities.handleHttpGet(url);
                    if(inputStream != null) {
                        FlickrMetaDataHandler flickrMetaDataHandler = new FlickrMetaDataHandler();
                        imageMetaData = flickrMetaDataHandler.parse(inputStream);
                        inputStream.close();
                    }
                } catch(IOException | SAXException | ParserConfigurationException ioException) {
                    Toast.makeText(getActivity(), R.string.thumbnail_failed,
                            Toast.LENGTH_LONG).show();
                }*//*

                MetaDataService metaDataService = ServiceFactory.getMetaDataService();

                return metaDataService.getMetaData(mFlickrImage.getId());
            }

            @Override
            protected void onPostExecute(ImageMetaDataResults imageMetaData) {
                super.onPostExecute(imageMetaData);
                mImageMetaDataResults = imageMetaData;
                //setViewData();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/
    //}

    /*private void setViewData() {
        if(!TextUtils.isEmpty(mImageMetaDataResults.getTitle())) {
            imageTitleTextView.setText(mImageMetaDataResults.getTitle());
        }

        if(!TextUtils.isEmpty(mImageMetaDataResults.getOriginalSecret())) {
            originalSecretTextView.setText(mImageMetaDataResults.getOriginalSecret());
        }

        if(!TextUtils.isEmpty(mImageMetaDataResults.getPostedDateString())) {
            postedDateTextView.setText(mImageMetaDataResults.getPostedDateString());
        }

        if(!TextUtils.isEmpty(mImageMetaDataResults.getRealName())) {
            realNameTextView.setText(mImageMetaDataResults.getRealName());
        }

        if(!TextUtils.isEmpty(mImageMetaDataResults.getSecret())) {
            secretTextView.setText(mImageMetaDataResults.getSecret());
        }

        if(!TextUtils.isEmpty(mImageMetaDataResults.getTakenDate())) {
            takenDateTextView.setText(mImageMetaDataResults.getTakenDate());
        }

        if(!TextUtils.isEmpty(mImageMetaDataResults.getUserName())) {
            userNameTextView.setText(mImageMetaDataResults.getUserName());
        }
    }*/
}