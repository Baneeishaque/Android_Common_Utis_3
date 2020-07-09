package ndk.utils_android3;

import android.content.Context;
import android.view.View;

import org.apache.http.NameValuePair;

import java.util.ArrayList;

import ndk.utils_android1.NetworkUtils;
import ndk.utils_android1.ProgressBarUtils;
import ndk.utils_android1.ToastUtils;

public class DbInsertUpdateDeleteWrapper {

    public static void executeDbInsertUpdateDeletePostWithParameters(String taskURL, ArrayList<NameValuePair> parameters, Context context, View progressBar, View view, String applicationName, HttpApiSelectTask.AsyncResponseJSONObject asyncResponseJsonObject) {

        if (NetworkUtils.isOnline(context)) {

            ProgressBarUtils.showProgress(true, context, progressBar, view);

            DbInsertUpdateDelete dbInsertUpdateDelete = new DbInsertUpdateDelete(taskURL, parameters, context, progressBar, view, applicationName, asyncResponseJsonObject);
            dbInsertUpdateDelete.execute();

        } else {

            ToastUtils.longToast(context, "Internet is unavailable...");
        }
    }

    public static void executeDbInsertUpdateDeletePostWithParameters(String taskURL, ArrayList<NameValuePair> parameters, Context context, View progressBar, View view, String applicationName, HttpApiSelectTask.AsyncResponseJSONArray asyncResponseJsonArray) {

        if (NetworkUtils.isOnline(context)) {

            ProgressBarUtils.showProgress(true, context, progressBar, view);

            DbInsertUpdateDelete dbInsertUpdateDelete = new DbInsertUpdateDelete(taskURL, parameters, context, progressBar, view, applicationName, asyncResponseJsonArray);
            dbInsertUpdateDelete.execute();

        } else {

            ToastUtils.longToast(context, "Internet is unavailable...");
        }
    }

    public static void executeDbInsertUpdateDeletePostWithParameters(String taskURL, ArrayList<NameValuePair> parameters, Context context, View progressBar, View view, String applicationName, HttpApiSelectTask.AsyncResponse asyncResponse) {

        if (NetworkUtils.isOnline(context)) {

            ProgressBarUtils.showProgress(true, context, progressBar, view);

            DbInsertUpdateDelete dbInsertUpdateDelete = new DbInsertUpdateDelete(taskURL, parameters, context, progressBar, view, applicationName, asyncResponse);
            dbInsertUpdateDelete.execute();

        } else {

            ToastUtils.longToast(context, "Internet is unavailable...");
        }
    }
}
