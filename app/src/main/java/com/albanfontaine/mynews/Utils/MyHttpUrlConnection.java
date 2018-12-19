package com.albanfontaine.mynews.Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// Handles connections
public class MyHttpUrlConnection {

    public static String startHttpRequest(String urlString){
        StringBuilder stringBuilder = new StringBuilder();

        try{
            // Declare URL connection
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Open InputStream to connection
            connection.connect();
            InputStream in = connection.getInputStream();
            // Download and decode the string response
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
            in.close();
        } catch (MalformedURLException e){
            Log.e("Malformed URL",  e.getMessage());
        } catch (IOException e){
            Log.e("IO Exception", e.getMessage());
        } catch (Exception e){
            Log.e("Exception", e.getMessage());
        }

        return stringBuilder.toString();
    }
}
