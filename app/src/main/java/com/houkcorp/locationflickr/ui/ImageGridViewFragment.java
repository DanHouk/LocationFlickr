package com.houkcorp.locationflickr.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.houkcorp.locationflickr.Constants;
import com.houkcorp.locationflickr.R;
import com.houkcorp.locationflickr.model.FlickrImage;
import com.houkcorp.locationflickr.model.FlickrImageHolder;
import com.houkcorp.locationflickr.model.LocationHolder;
import com.houkcorp.locationflickr.model.PhotosData;
import com.houkcorp.locationflickr.model.handler.FlickrImageHandler;
import com.houkcorp.locationflickr.util.ImageViewAdapter;
import com.houkcorp.locationflickr.util.LocationTracker;
import com.houkcorp.locationflickr.util.NetworkUtilities;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class ImageGridViewFragment extends Fragment {
    private ArrayList<FlickrImage> mFlickrImages;
    private ImageViewAdapter imageViewAdapter;
    private int pageNumber = 1;
    private FlickrImageHolder mFlickrImageHolder;
    private boolean loadMoreCalled = false;
    private TaskCallbacks mCallbacks;
    private SendBackTask mTask;

    interface TaskCallbacks {
        void onPreExecute();
        void onProgressUpdate(int percent);
        void onCancelled();
        void onPostExecute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            mFlickrImageHolder = savedInstanceState.getParcelable(Constants.FLICKR_IMAGE);
            if(mFlickrImageHolder != null) {
                mFlickrImages = mFlickrImageHolder.getPhotos();
            }

        } else {
            mFlickrImages = new ArrayList<>();
        }


        setRetainInstance(true);
        mTask = new SendBackTask();
        mTask.execute();

        handleFetchImages();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View gridLayoutView = inflater.inflate(R.layout.fragment_image_grid_view, container, false);
        imageViewAdapter =
                new ImageViewAdapter(getActivity(), android.R.layout.simple_list_item_1,
                        mFlickrImages);

        final GridView imageGridView = (GridView)gridLayoutView.findViewById(R.id.image_grid_view_id);
        imageGridView.setAdapter(imageViewAdapter);

        imageGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mFlickrImageHolder != null && (firstVisibleItem + visibleItemCount) >=
                        mFlickrImageHolder.getPhotos().size() && !loadMoreCalled) {
                    loadMoreCalled = true;
                    handleFetchImages();
                }
            }
        });

        imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FlickrImage selectedImage = mFlickrImageHolder.getPhotos().get(position);
                if (selectedImage != null) {
                    Intent detailIntent = new Intent(getActivity(), ImageDetailView.class);
                    detailIntent.putExtra(Constants.FLICKR_IMAGE, selectedImage);

                    startActivity(detailIntent);
                }
            }
        });

        return gridLayoutView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (TaskCallbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.FLICKR_IMAGE, mFlickrImageHolder);
    }

    private URL buildImageURL() {
        LocationTracker locationTracker = new LocationTracker(getActivity());
        LocationHolder locationHolder = locationTracker.getLocation();

        URL url = null;
        try {
            url = new URL(Constants.PHOTOS_SEARCH_URL + "&bbox=" +
                    Double.toString(locationHolder.longitude - 0.2) + "," +
                    Double.toString(locationHolder.latitude - 0.2) + "," +
                    Double.toString(locationHolder.longitude + 0.2) + "," +
                    Double.toString(locationHolder.latitude + 0.2) + "&page=" +
                    pageNumber);
        } catch(MalformedURLException malformedException) {
            Toast.makeText(getActivity(), R.string.url_creation_failed, Toast.LENGTH_LONG).show();
        }

        return url;
    }

    private void fetchImagesInBackground(final URL url) throws IOException, NoSuchAlgorithmException,
            KeyManagementException, XmlPullParserException {
        new AsyncTask<Void, Void, FlickrImageHolder>() {
            @Override
            protected FlickrImageHolder doInBackground(Void... params) {
                FlickrImageHolder flickrImageHolder = null;
                try {
                    InputStream inputStream = NetworkUtilities.handleHttpGet(url);
                    if(inputStream != null) {
                        FlickrImageHandler flickrImageHandler = new FlickrImageHandler();
                        flickrImageHolder = flickrImageHandler.parse(inputStream);
                        inputStream.close();
                    }
                } catch(IOException | XmlPullParserException | SAXException |
                        ParserConfigurationException ioException) {
                    Log.e("Image Grid View", "doInBackground", ioException);
                }

                return flickrImageHolder;
            }

            @Override
            protected void onPostExecute(FlickrImageHolder flickrImageHolder) {
                super.onPostExecute(flickrImageHolder);
                for(FlickrImage flickrImage : flickrImageHolder.getPhotos()) {
                    fetchThumbnailsInBackground(flickrImage);
                    PhotosData photosData = flickrImageHolder.getPhotosData();
                    if(pageNumber < photosData.getPage() && pageNumber <= photosData.getPages()) {
                        pageNumber++;
                    } else if(photosData.getPages() == pageNumber) {
                        Toast.makeText(getActivity(), R.string.last_page, Toast.LENGTH_LONG)
                                .show();
                    }
                }

                mFlickrImageHolder = flickrImageHolder;
                loadMoreCalled = false;
            }
        }.execute(null, null, null);
    }

    private void fetchThumbnailsInBackground(final FlickrImage flickrImage) {
        new AsyncTask<Void, Void, FlickrImage>() {
            @Override
            protected FlickrImage doInBackground(Void... params) {
                try {
                    URL url = buildURL(flickrImage);
                    InputStream inputStream = NetworkUtilities.handleHttpGet(url);
                    if(inputStream != null) {
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        flickrImage.setBitmap(BitmapFactory.decodeStream(bufferedInputStream));
                        bufferedInputStream.close();
                        inputStream.close();
                    }
                } catch(IOException ioException) {
                    Toast.makeText(getActivity(), R.string.thumbnail_failed, Toast.LENGTH_LONG)
                            .show();
                }

                return flickrImage;
            }

            @Override
            protected void onPostExecute(FlickrImage image) {
                super.onPostExecute(image);
                mFlickrImageHolder.addPhoto(flickrImage);

                imageViewAdapter.clear();
                imageViewAdapter.addAll(mFlickrImageHolder.getPhotos());
                imageViewAdapter.notifyDataSetChanged();
            }
        }.execute(null, null, null);
    }

    private URL buildURL(FlickrImage flickrImage) {
        URL url = null;
        try {
            String stringURL = String.format(Constants.DEFAULT_IMAGE_URL, flickrImage.getFarm(),
                    flickrImage.getServer(), flickrImage.getId(), flickrImage.getSecret(), "t");
            url = new URL(stringURL);
        } catch(MalformedURLException malformedURLException) {
            Toast.makeText(getActivity(), R.string.no_thumbnails, Toast.LENGTH_LONG).show();
        }

        return url;
    }

    private void handleFetchImages() {
        URL url = buildImageURL();
        try {
            fetchImagesInBackground(url);
        } catch(IOException ioException) {
            Toast.makeText(getActivity(), R.string.failed_connection, Toast.LENGTH_LONG).show();
        } catch(NoSuchAlgorithmException | KeyManagementException mixedException) {
            Toast.makeText(getActivity(), R.string.connection_error, Toast.LENGTH_LONG)
                    .show();
        } catch(XmlPullParserException pullParserException) {
            Toast.makeText(getActivity(), R.string.failed_to_retrieve_data,
                    Toast.LENGTH_LONG).show();
        }
    }

    private class SendBackTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            if(mCallbacks != null) {
                super.onPreExecute();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            for(int i = 0; !isCancelled() && i < 100; i++) {
                SystemClock.sleep(100);
                publishProgress(i);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(mCallbacks != null) {
                mCallbacks.onProgressUpdate(values[0]);
            }
        }

        @Override
        protected void onCancelled() {
            if(mCallbacks != null) {
                super.onCancelled();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(mCallbacks != null) {
                super.onPostExecute(aVoid);
            }
        }
    }

    public void clearListAndReQuery() {
        if(mCallbacks == null) {
            Toast.makeText(getActivity(), R.string.refreshing_please_wait, Toast.LENGTH_LONG).show();
            mFlickrImages = new ArrayList<>();
            mFlickrImageHolder = new FlickrImageHolder();
            pageNumber = 1;
            imageViewAdapter.clear();

            handleFetchImages();
        } else {
            Toast.makeText(getActivity(), R.string.sync_in_progress, Toast.LENGTH_LONG).show();
        }
    }
}