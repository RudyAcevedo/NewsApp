package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rudster on 10/31/2016.
 */

//Helper method related to requesting and receiving news data
public final class QueryUtils {

    private static final String LOG_TAG = null;

    /**
     * This class is meant to hold static variables and methods, which can be accessed
     * directly from the calss named QueryUtils
     */
    private QueryUtils(){
    }
    public static List<News> fetchNewsData(String requestUrl){
        //Create URL object
        URL url = null;
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        try {
            url = createUrl(requestUrl);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        //Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        //Extract relevant fields from teh JSON response and create a News object
        List<News> news = extratFeatureFromJson(jsonResponse);

        return news;

    }

    public static List<News> extratFeatureFromJson(String newsJson){

        //return early if JSON string is empty or null
        if (TextUtils.isEmpty(newsJson)){
            return null;
        }

        //Create an empty ArrayList that we can start adding news to
        List<News> newsAdd = new ArrayList<>();

        String data = "";
        try {

            JSONObject baseJsonResponse = new JSONObject(newsJson);

            JSONObject response = baseJsonResponse.getJSONObject("response");

            JSONArray resultsArray = response.getJSONArray("results");
            int i;

            Date dateObject;

            for (i = 0; i < resultsArray.length(); i++){
                //Get news JSONObject at position i
                JSONObject ArrayObject = resultsArray.getJSONObject(i);

                //Extract "section"
                String section = ArrayObject.getString("sectionName").toString();

                //Extract "title"
                String title = ArrayObject.getString("webTitle").toString();

                //Extract "url"
                String Url =ArrayObject.getString("webUrl").toString();

                //Extract "date"
                String publicaitonDate = ArrayObject.optString("webPublicaitonDate");

                StringBuilder formattedDate = new StringBuilder(publicaitonDate);
                for (int j = 0; j < publicaitonDate.length(); j++){
                    if (publicaitonDate.charAt(j) == 'T' || publicaitonDate.charAt(j)=='Z')
                        formattedDate.setCharAt(j, ' ');
                }
                publicaitonDate = formattedDate.toString();

                News news = new News(section, title, publicaitonDate, Url);
                newsAdd.add(news);

            }


        }catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }
        //Return the list of news
        return newsAdd;
    }

    /**
     * Returns new URL object form the given string URL
     * @param stingUrl
     * @return
     * @throws MalformedURLException
     */
    private static URL createUrl(String stingUrl) throws MalformedURLException{
        URL url = null;
        try {
            url = new URL(stingUrl);
        }catch (MalformedURLException exception){
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            //if the request was successful (response code 200), then read the imput stream
            //and parse the response
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG, "Error response code:" + urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the news results", e);
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
