package com.albanfontaine.mynews.Utils;

import java.lang.ref.WeakReference;

public class NetworkAsyncTask extends  android.os.AsyncTask<String, Void, String> {

    public interface Listeners{
        void onPreExecute();
        void doInBackground();
        void onPostExecute(String success);
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        this.mCallback.get().onPreExecute();
    }

    @Override
    protected String doInBackground(String... url) {
        this.mCallback.get().doInBackground();
        return MyHttpUrlConnection.startHttpRequestion(url[0]);
    }
}
