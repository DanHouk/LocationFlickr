package com.houkcorp.locationflickr.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.houkcorp.locationflickr.Constants;
import com.houkcorp.locationflickr.R;
import com.houkcorp.locationflickr.activities.ImageDetailActivity;
import com.houkcorp.locationflickr.model.FlickrImageSearchPhoto;
import com.houkcorp.locationflickr.model.ImageMetaData;
import com.houkcorp.locationflickr.service.MetaDataService;
import com.houkcorp.locationflickr.service.ServiceFactory;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ImageDetailViewFragment extends Fragment {
    private Bitmap mBitmap;
    private FlickrImageSearchPhoto mFlickrImageSearchPhoto;
    private ImageView mImageView;
    private ImageMetaData mImageMetaData;

    private TextView imageTitleTextView;
    private TextView originalSecretTextView;
    private TextView postedDateTextView;
    private TextView realNameTextView;
    private TextView secretTextView;
    private TextView takenDateTextView;
    private TextView userNameTextView;

    public static ImageDetailViewFragment newInstance(FlickrImageSearchPhoto flickrImageSearchPhoto) {
        ImageDetailViewFragment imageDetailViewFragment = new ImageDetailViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(ImageDetailActivity.EXTRA_FLICKR_PHOTO, flickrImageSearchPhoto);
        imageDetailViewFragment.setArguments(args);
        return imageDetailViewFragment;
    }

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
            mFlickrImageSearchPhoto = extras.getParcelable(ImageDetailActivity.EXTRA_FLICKR_PHOTO);
            Picasso.with(getContext())
                    .load(String.format(Locale.getDefault(), Constants.DEFAULT_IMAGE_URL,
                            mFlickrImageSearchPhoto.getFarm(), mFlickrImageSearchPhoto.getServer(),
                            mFlickrImageSearchPhoto.getId(), mFlickrImageSearchPhoto.getSecret(), "t"))
                    .into(mImageView);
        } else if(savedInstanceState != null) {
            *//*mFlickrImage = savedInstanceState.getParcelable(Constants.FLICKR_IMAGE);
            mBitmap = savedInstanceState.getParcelable("bitmap");
            mImageMetaData = savedInstanceState.getParcelable("image_meta_data");*//*
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

        imageTitleTextView = (TextView)view.findViewById(R.id.image_title_text_view_id);
        originalSecretTextView = (TextView)view.findViewById(R.id.original_secret_text_view_id);
        postedDateTextView = (TextView)view.findViewById(R.id.posted_date_text_view_id);
        realNameTextView = (TextView)view.findViewById(R.id.real_name_text_view_id);
        secretTextView = (TextView)view.findViewById(R.id.secret_text_view_id);
        takenDateTextView = (TextView)view.findViewById(R.id.taken_date_text_view_id);
        userNameTextView = (TextView)view.findViewById(R.id.user_name_text_view_id);

        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null) {
            mFlickrImageSearchPhoto = extras.getParcelable(ImageDetailActivity.EXTRA_FLICKR_PHOTO);
            Picasso.with(getContext())
                    .load(String.format(Locale.getDefault(), Constants.DEFAULT_IMAGE_URL,
                            mFlickrImageSearchPhoto.getFarm(), mFlickrImageSearchPhoto.getServer(),
                            mFlickrImageSearchPhoto.getId(), mFlickrImageSearchPhoto.getSecret(), "z"))
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
        Observable<ImageMetaData> observable = metaDataService.getMetaData(mFlickrImageSearchPhoto.getId());
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ImageMetaData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ImageMetaData imageMetaData) {

                    }
                });
        /*new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bitmap = null;
                try {
                    String stringURL = String.format(Locale.getDefault(), Constants.DEFAULT_IMAGE_URL,
                            flickrImage.getFarm(),
                            flickrImage.getServer(), flickrImage.getId(),
                            flickrImage.getSecret(), "z");
                    URL url = new URL(stringURL);

                    InputStream inputStream = NetworkUtilities.handleHttpGet(url);
                    if(inputStream != null) {
                        BufferedInputStream bufferedInputStream =
                                new BufferedInputStream(inputStream);
                        bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                        bufferedInputStream.close();
                        inputStream.close();
                    }
                } catch(IOException ioException) {
                    Toast.makeText(getActivity(), R.string.thumbnail_failed,
                            Toast.LENGTH_LONG).show();
                }

                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                mBitmap = bitmap;
                mImageView.setImageBitmap(bitmap);
                mImageView.invalidate();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/
    }

    //private void getMetaData() {
        /*new AsyncTask<Void, Void, ImageMetaData>() {
            @Override
            protected ImageMetaData doInBackground(Void... params) {
                *//*ImageMetaData imageMetaData = null;
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
            protected void onPostExecute(ImageMetaData imageMetaData) {
                super.onPostExecute(imageMetaData);
                mImageMetaData = imageMetaData;
                //setViewData();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/
    //}

    /*private void setViewData() {
        if(!TextUtils.isEmpty(mImageMetaData.getTitle())) {
            imageTitleTextView.setText(mImageMetaData.getTitle());
        }

        if(!TextUtils.isEmpty(mImageMetaData.getOriginalSecret())) {
            originalSecretTextView.setText(mImageMetaData.getOriginalSecret());
        }

        if(!TextUtils.isEmpty(mImageMetaData.getPostedDateString())) {
            postedDateTextView.setText(mImageMetaData.getPostedDateString());
        }

        if(!TextUtils.isEmpty(mImageMetaData.getRealName())) {
            realNameTextView.setText(mImageMetaData.getRealName());
        }

        if(!TextUtils.isEmpty(mImageMetaData.getSecret())) {
            secretTextView.setText(mImageMetaData.getSecret());
        }

        if(!TextUtils.isEmpty(mImageMetaData.getTakenDate())) {
            takenDateTextView.setText(mImageMetaData.getTakenDate());
        }

        if(!TextUtils.isEmpty(mImageMetaData.getUserName())) {
            userNameTextView.setText(mImageMetaData.getUserName());
        }
    }*/
}