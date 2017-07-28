package com.houkcorp.locationflickr.util;

import android.util.Log;

import com.houkcorp.locationflickr.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

/*FIXME: THis is old and definitely needs to be updated.*/
public class NetworkUtilities {
    public NetworkUtilities(){
    }

    public static InputStream handleHttpGet(URL url) {
        InputStream inputStream = null;
        try {
            HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(Constants.SERVER_TIME_OUT);
            urlConnection.setReadTimeout(Constants.SERVER_TIME_OUT);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, new SecureRandom());
            urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());

            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200) {
                inputStream = urlConnection.getInputStream();
            }
        } catch(IOException | NoSuchAlgorithmException | KeyManagementException ioException) {
            Log.e("NetworkUtils", "HttpGet", ioException);
        }

        return inputStream;
    }
}