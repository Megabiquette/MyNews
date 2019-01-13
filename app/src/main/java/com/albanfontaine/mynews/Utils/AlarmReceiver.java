package com.albanfontaine.mynews.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.albanfontaine.mynews.Controllers.MainActivity;
import com.albanfontaine.mynews.Models.ApiResponseSearch;
import com.albanfontaine.mynews.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class AlarmReceiver extends BroadcastReceiver {

    // Keys for the Bundle
    private static final String QUERY = "query";
    private static final String CATEGORY = "category";

    private Disposable mDisposable;

    @Override
    public void onReceive(Context context, Intent intent) {
        String query = intent.getStringExtra(QUERY);
        String category = intent.getStringExtra(CATEGORY);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = format.format(calendar.getTime());

        this.mDisposable = NYTimesStreams.streamFetchSearchArticles(query, category, date, date)
                .subscribeWith(new DisposableObserver<ApiResponseSearch>(){
                    int numberArticles;
                    @Override
                    public void onNext(ApiResponseSearch apiResponseSearch) {
                        numberArticles = apiResponseSearch.getResponse().getDocs().size();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        if(numberArticles != 0) {
                            sendNotification(context, numberArticles);
                        }
                    }
                });
    }

    private void sendNotification(Context context, int numberArticles){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("New articles!")
                .setContentText(numberArticles + " new articles corresponding to your criteria published")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(0, builder.build());
    }


}
