package ndk.utils_android3;

import android.content.Context;
import android.view.View;

import ndk.utils_android1.NetworkUtils;
import ndk.utils_android1.ProgressBarUtils;
import ndk.utils_android1.ToastUtils;

public class DbSelectWrapper {

    public static void executeDbSelectHttpGet(String taskURL, Context context, View progressBar, View view, String applicationName, HttpApiSelectTask3.AsyncResponseJSONArray asyncResponseJsonArray) {

        executeDbSelectHttpGet(context, progressBar, view, new DbSelect(taskURL, context, progressBar, view, applicationName, asyncResponseJsonArray));
    }

    public static void executeDbSelectHttpGet(String taskURL, Context context, View progressBar, View view, String applicationName, HttpApiSelectTask3.AsyncResponseJSONObject asyncResponseJsonObject) {

        executeDbSelectHttpGet(context, progressBar, view, new DbSelect(taskURL, context, progressBar, view, applicationName, asyncResponseJsonObject));
    }

    public static void executeDbSelectHttpGet(Context context, View progressBar, View view, DbSelect dbSelect) {

        if (NetworkUtils.isOnline(context)) {

            ProgressBarUtils.showProgress(true, context, progressBar, view);
            dbSelect.execute();

        } else {

            ToastUtils.longToast(context, "Internet is unavailable...");
        }
    }
}
