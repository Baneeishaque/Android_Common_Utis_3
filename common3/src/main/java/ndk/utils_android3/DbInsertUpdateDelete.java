package ndk.utils_android3;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ndk.utils_android1.ExceptionUtils;
import ndk.utils_android1.HttpPost;
import ndk.utils_android1.LogUtilsWrapper;
import ndk.utils_android1.NetworkUtils;
import ndk.utils_android1.ProgressBarUtils;
import ndk.utils_android1.ToastUtils;

public class DbInsertUpdateDelete extends AsyncTask<Void, Void, String[]> {

    boolean isJsonObjectResponse;
    boolean isJsonArrayResponse;
    boolean isForeGround = true;
    boolean isSplashScreen;
    boolean checkError;
    TemporaryLogUtilsWrapper temporaryLogUtilsWrapper = new TemporaryLogUtilsWrapper();
    private boolean isResponse = true;
    private String URL;
    private ArrayList<NameValuePair> parameters;

    private String TAG;
    private Context context;
    private View progressBar;
    private View form;

    private HttpApiSelectTask3.AsyncResponseJSONObject asyncResponseJsonObject = null;
    private HttpApiSelectTask3.AsyncResponseJSONArray asyncResponseJsonArray = null;
    private HttpApiSelectTask3.AsyncResponse asyncResponse = null;

    private boolean isProgressBarPresent = true;

    // Post With Parameters - Returns JSON object
    public DbInsertUpdateDelete(String URL, ArrayList<NameValuePair> parameters, Context context, View progressBar, View form, String TAG, HttpApiSelectTask3.AsyncResponseJSONObject asyncResponseJsonObject) {

        this.URL = URL;
        this.parameters = parameters;

        this.context = context;

        this.progressBar = progressBar;
        this.form = form;

        this.TAG = TAG;

        this.asyncResponseJsonObject = asyncResponseJsonObject;

        isResponse = false;
        isJsonObjectResponse = true;
    }

    // Post With Parameters - Returns JSON array
    public DbInsertUpdateDelete(String URL, ArrayList<NameValuePair> parameters, Context context, View progressBar, View form, String TAG, HttpApiSelectTask3.AsyncResponseJSONArray asyncResponseJsonArray) {

        this.URL = URL;
        this.parameters = parameters;

        this.context = context;

        this.progressBar = progressBar;
        this.form = form;

        this.TAG = TAG;

        this.asyncResponseJsonArray = asyncResponseJsonArray;

        isResponse = false;
        isJsonArrayResponse = true;
    }

    // Post With Parameters - Returns Response
    public DbInsertUpdateDelete(String URL, ArrayList<NameValuePair> parameters, Context context, View progressBar, View form, String TAG, HttpApiSelectTask3.AsyncResponse asyncResponse) {

        this.URL = URL;
        this.parameters = parameters;

        this.context = context;

        this.progressBar = progressBar;
        this.form = form;

        this.TAG = TAG;

        this.asyncResponse = asyncResponse;

    }

    @Override
    protected String[] doInBackground(Void... params) {

        temporaryLogUtilsWrapper.debug("URL is " + URL);
        return HttpPost.performPostWithParameters(URL, parameters);
    }

    protected void onPostExecute(String[] networkActionResponseArray) {

        if (isProgressBarPresent) {
            ProgressBarUtils.showProgress(false, context, progressBar, form);
        }

        temporaryLogUtilsWrapper.debug("Network Action status is " + networkActionResponseArray[0]);
        temporaryLogUtilsWrapper.debug("Network Action response is " + networkActionResponseArray[1]);

        if (isResponse) {

            if (networkActionResponseArray[0].equals("1")) {

                asyncResponse.processFinish("exception");
                informError(networkActionResponseArray[1]);

            } else {
                asyncResponse.processFinish(networkActionResponseArray[1]);
            }

        } else if (isJsonObjectResponse) {

            if (networkActionResponseArray[0].equals("1")) {

                informError(networkActionResponseArray[1]);

            } else {

                try {

                    JSONObject jsonObject = new JSONObject(networkActionResponseArray[1]);
                    asyncResponseJsonObject.processFinish(jsonObject);

                } catch (JSONException e) {

                    ExceptionUtils.handleException(isForeGround, context, TAG, e);
                }
            }

        } else if (isJsonArrayResponse) {

            if (networkActionResponseArray[0].equals("1")) {

                informError(networkActionResponseArray[1]);

            } else {

                try {

                    JSONArray jsonArray = new JSONArray(networkActionResponseArray[1]);

                    if (checkError) {

                        if (jsonArray.getJSONObject(0).getString("status").equals("1")) {

                            if (isForeGround) {

                                ToastUtils.longToast(context, "No Entries...");

                            }
                            temporaryLogUtilsWrapper.debug("No Entries...");

                        } else if (jsonArray.getJSONObject(0).getString("status").equals("0")) {
                            this.asyncResponseJsonArray.processFinish(jsonArray);
                        }
                    } else {
                        asyncResponseJsonArray.processFinish(jsonArray);
                    }
                } catch (JSONException e) {

                    ExceptionUtils.handleException(isForeGround, context, TAG, e);
                }
            }
        }
    }

    private void informError(String error) {

        processNetworkError(error);
        handleSplashScreen();
    }

    private void handleSplashScreen() {
        if (isSplashScreen) {
            ((AppCompatActivity) context).finish();
        }
    }

    private void processNetworkError(String errorMessage) {

        //                if (isForeGround) {
////                        TODO : Short Toast
//                    Toast_Utils.longToast(context, "Error...");
//                }

        NetworkUtils.displayFriendlyExceptionMessage(context, errorMessage);
        //TODO : Add more error scenarios
        temporaryLogUtilsWrapper.debug("Error...");

    }

    protected void onCancelled() {
        if (isProgressBarPresent) {
            ProgressBarUtils.showProgress(false, this.context, this.progressBar, this.form);
        }
    }

    //    TODO : Implement as Interface with default methods
    class TemporaryLogUtilsWrapper extends LogUtilsWrapper {

        @Override
        public String configureTAG() {
            return TAG;
        }
    }
}
