package ndk.utils_android3;

import android.content.Context;
import android.view.View;

import androidx.core.util.Pair;

import ndk.utils_android1.NetworkUtils;
import ndk.utils_android1.ProgressBarUtils;
import ndk.utils_android1.ToastUtils;

public class HttpApiSelectTaskWrapper {

    public static void execute(String task_URL, Context context, View progressView, View loginFormView, String applicationName, Pair[] nameValuePairs, HttpApiSelectTask.AsyncResponseJSONArray asyncResponseJSONArray) {

        if (NetworkUtils.isOnline(context)) {

            ProgressBarUtils.showProgress(true, context, progressView, loginFormView);
            HttpApiSelectTask httpApiSelectTask = new HttpApiSelectTask(task_URL, context, progressView, loginFormView, applicationName, nameValuePairs, asyncResponseJSONArray);
            httpApiSelectTask.execute();

        } else {

            ToastUtils.longToast(context, "Internet is unavailable...");
        }
    }
}
