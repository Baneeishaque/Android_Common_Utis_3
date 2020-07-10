package ndk.utils_android3;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ndk.utils_android1.LogUtils;
import ndk.utils_android1.NetworkUtils;
import ndk.utils_android1.ProgressBarUtils;
import ndk.utils_android1.ToastUtils;

// TODO : Integrate with ndk.utils_android3.DbSelect

public class HttpApiSelectTask3 extends AsyncTask<Void, Void, String[]> {

    boolean background_flag = false;
    boolean error_flag = true;
    AsyncResponseJSONArray asyncResponseJSONArray;
    AsyncResponseJSONObject asyncResponseJSONObject = null;
    AsyncResponse asyncResponse = null;
    private String url;
    private String TAG;
    private Context context;
    private View progressBar;
    private View form;
    private Pair[] name_value_pair;
    private int progressFlag = 0;
    private int responseFlag = 0;
    private int splashFlag = 0;

    public HttpApiSelectTask3(String url, Context context, View progressBar, View form, String TAG, Pair[] name_value_pair, AsyncResponseJSONArray asyncResponseJSONArray) {
        this.url = url;
        this.context = context;
        this.progressBar = progressBar;
        this.form = form;
        this.TAG = TAG;
        this.name_value_pair = name_value_pair;
        this.asyncResponseJSONArray = asyncResponseJSONArray;
    }

    @Override
    protected String[] doInBackground(Void... params) {

        LogUtils.debug(TAG,"url is " + this.url);
        return NetworkUtils.performHttpClientPostTask(this.url, this.name_value_pair);
    }

    protected void onPostExecute(String[] networkActionResponseArray) {

        if (this.progressFlag == 0) {

            ProgressBarUtils.showProgress(false, this.context, this.progressBar, this.form);
        }

        if (this.responseFlag == 1) {

            Log.d(this.TAG, "Network Action status is " + networkActionResponseArray[0]);
            Log.d(this.TAG, "Network Action response is " + networkActionResponseArray[1]);
            if (networkActionResponseArray[0].equals("1")) {

                NetworkUtils.displayFriendlyExceptionMessage(this.context, networkActionResponseArray[1]);
                Log.d(this.TAG, "Network Action response is " + networkActionResponseArray[1]);
                this.asyncResponse.processFinish("exception");
            } else {
                this.asyncResponse.processFinish(networkActionResponseArray[1]);
            }
        } else if (this.responseFlag == 2) {
            Log.d(this.TAG, "Network Action status is " + networkActionResponseArray[0]);
            Log.d(this.TAG, "Network Action response is " + networkActionResponseArray[1]);
            if (networkActionResponseArray[0].equals("1")) {

                NetworkUtils.displayFriendlyExceptionMessage(this.context, networkActionResponseArray[1]);
                Log.d(this.TAG, "Network Action response is " + networkActionResponseArray[1]);
            } else {
                try {
                    JSONObject json_object = new JSONObject(networkActionResponseArray[1]);
                    this.asyncResponseJSONObject.processFinish(json_object);
                } catch (JSONException var3) {
                    ToastUtils.longToast(context, "Error...");
                    Log.d(this.TAG, "Error : " + var3.getLocalizedMessage());
                }
            }
        } else {
            Log.d(this.TAG, "Network Action Response Array 0 : " + networkActionResponseArray[0]);
            Log.d(this.TAG, "Network Action Response Array 1 : " + networkActionResponseArray[1]);
            if (networkActionResponseArray[0].equals("1")) {
                if (this.background_flag) {
                    Log.d(this.TAG, "Error...");
                } else {
                    ToastUtils.longToast(context, "Error...");
                }

                Log.d(this.TAG, "Network Action Response Array 1 : " + networkActionResponseArray[1]);
                if (this.splashFlag == 1) {
                    ((AppCompatActivity) this.context).finish();
                }
            } else {
                try {
                    JSONArray json_array = new JSONArray(networkActionResponseArray[1]);
                    if (this.splashFlag != 1 && this.error_flag) {
                        switch (json_array.getJSONObject(0).getString("status")) {
                            case "1":
                                ToastUtils.longToast(context, "Error...");
                                LogUtils.debug(TAG,"Error : " + json_array.getJSONObject(0).getInt("error_number") + ", " + json_array.getJSONObject(0).getInt("error"));
                                break;
                            case "2":
                                if (this.background_flag) {
                                    LogUtils.debug(TAG,"No Entries...");
                                } else {
                                    ToastUtils.longToast(context, "No Entries...");
                                }
                                break;
                            case "0":
                                this.asyncResponseJSONArray.processFinish(json_array);
                                break;
                            default:
                                ToastUtils.longToast(context, "Error...");
                                LogUtils.debug(TAG,"Response : " + json_array);
                                break;
                        }
                    } else {
                        this.asyncResponseJSONArray.processFinish(json_array);
                    }
                } catch (JSONException e) {
                    ToastUtils.longToast(context, "Error...");
                    LogUtils.debug(TAG,"Error : " + e.getLocalizedMessage());
                }
            }
        }
    }

    protected void onCancelled() {
        if (this.progressFlag == 0) {
            ProgressBarUtils.showProgress(false, this.context, this.progressBar, this.form);
        }
    }

    public interface AsyncResponseJSONArray {
        void processFinish(JSONArray jsonArray);
    }

    public interface AsyncResponseJSONObject {
        void processFinish(JSONObject var1);
    }

    public interface AsyncResponse {
        void processFinish(String var1);
    }
}
