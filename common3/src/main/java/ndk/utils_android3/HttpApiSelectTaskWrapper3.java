package ndk.utils_android3;

import android.content.Context;
import android.view.View;

import androidx.core.util.Pair;

import ndk.utils_android1.NetworkUtils;
import ndk.utils_android1.ProgressBarUtils;
import ndk.utils_android1.ToastUtils;

public class HttpApiSelectTaskWrapper3 {

    public static void execute(String task_URL, Context context, View progressView, View loginFormView, String applicationName, Pair[] nameValuePairs, HttpApiSelectTask3.AsyncResponseJSONArray asyncResponseJSONArray) {

        if (NetworkUtils.isOnline(context)) {

            ProgressBarUtils.showProgress(true, context, progressView, loginFormView);
            new HttpApiSelectTask3(task_URL, context, progressView, loginFormView, applicationName, nameValuePairs, asyncResponseJSONArray).execute();

        } else {

            ToastUtils.longToast(context, "Internet is unavailable...");
        }
    }
}
