package com.albanfontaine.mynews.Utils;

import android.util.Log;

import java.lang.ref.WeakReference;


public class NetworkAsyncTask extends  android.os.AsyncTask<String, Void, String> {

    public interface Listeners{
        void onPreExecute();
        void doInBackground();
        void onPostExecute(String response);
    }

    private final WeakReference<Listeners> mCallback;

    public NetworkAsyncTask(Listeners callback){
        this.mCallback = new WeakReference<>(callback);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.mCallback.get().onPreExecute();
    }

    @Override
    protected String doInBackground(String... url) {
        this.mCallback.get().doInBackground();
        Log.e("TAG", "doInBackground_CLASS");
        return MyHttpUrlConnection.startHttpRequest(url[0]);
    }
    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        this.mCallback.get().onPostExecute(response);

        Log.e("TAG", "onPostExecute_CLASS");
    }
}
