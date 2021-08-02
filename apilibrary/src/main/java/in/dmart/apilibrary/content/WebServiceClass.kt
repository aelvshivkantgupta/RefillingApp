package `in`.dmart.apilibrary.content

import `in`.dmart.apilibrary.constant.ApiUrls
import `in`.dmart.apilibrary.constant.ConstantParameters
import `in`.dmart.apilibrary.dialog.CustomDialog
import `in`.dmart.apilibrary.lib.GSONUtil
import `in`.dmart.apilibrary.model.AuthError
import `in`.dmart.apilibrary.model.response_model.ErrorResponseModel
import `in`.dmart.apilibrary.network.ApiService
import `in`.dmart.apilibrary.util.Logger
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.Nullable
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Shivkant on 10-11-2017.
 */
@Singleton
class WebServiceClass @Inject constructor(@ApplicationContext val context: Context, @Nullable val apiService: ApiService,
                                          ) {

    val isProgressDialogEnable:Boolean=true
    val loginClass: Class<*>?=null

    private val tag = "WebServiceClass"
    private var progressDialog: CustomDialog? = null
    private val headerMap =  HashMap<String, String>()
        get(){
            field[ConstantParameters.CONTENT_TYPE] = "application/json"
            return field
        }


    fun <T:Any>postData(url: String, reqBody: Any?, c: Class<T>, apiResponse: ApiResponse<T,Throwable>, header: Map<String, String> = headerMap) {
        showProgressDialog()
        val call: Call<ResponseBody> = apiService.postData(url, header, reqBody)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                triggerSuccessResponse(url, reqBody, response, apiResponse, c)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Logger.log(tag, "-----onFailure--------")
                hideProgressDialog()
                failureHandler?.onFailure(url, reqBody, t)
                apiResponse.onFailure(t)
            }
        })
    }


    fun  <T:Any>getData(url: String, reqBody: Any?, c: Class<T>,
                    apiResponse: ApiResponse<T,Throwable>,header: Map<String, String> = headerMap) {
        showProgressDialog()
        val call: Call<ResponseBody> = if (reqBody != null) {
            apiService.getData(url, header, pojoToMap(reqBody))
        } else {
            apiService.getData(url, header)
        }

        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                triggerSuccessResponse(url, reqBody, response, apiResponse, c)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Logger.log(tag, "-----onFailure--------")
                hideProgressDialog()
                apiResponse.onFailure(t)
                failureHandler?.onFailure(url, reqBody, t)
            }
        })
    }

    fun  <T:Any>postQuery(url: String, reqBody: Any,  c: Class<T>,
                      apiResponse: ApiResponse<T,Throwable>,header: Map<String, String> = headerMap) {
        showProgressDialog()
        val call: Call<ResponseBody> = apiService.postQuery(url, header, pojoToMap(reqBody))
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                triggerSuccessResponse(url, reqBody, response, apiResponse, c)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Logger.log(tag, "-----onFailure--------")
                hideProgressDialog()
                apiResponse.onFailure(t)
                failureHandler?.onFailure(url, reqBody, t)
            }
        })
    }

    fun <T:Any>login(url: String, reqBody: Any?, c: Class<T>, apiResponse: ApiResponse<T,Throwable>) {
        val headerMap = HashMap<String, String>()
        headerMap[ConstantParameters.CONTENT_TYPE] = "application/json"
        headerMap[ConstantParameters.TIMESTAMP] = ""
        postData(url, reqBody, c, apiResponse)
    }


    //--------------Util methods -----------------------//
    private fun<T:Any> triggerSuccessResponse(url: String, reqBody: Any?, response: Response<ResponseBody?>, apiResponse: ApiResponse<T,Throwable>, c: Class<T>) {
        try {
            if (response.isSuccessful && response.body() != null) {
                val strResponse = response.body()?.string() ?: ""
                Logger.log(tag, "------------------ Sucess response - $strResponse")
                headerToken(response)
                hideProgressDialog()
                apiResponse.onSuccess( GSONUtil.fromJson<T>(strResponse,c))
                checkEmptyRes(url, reqBody, strResponse, true)
            } else {
                Logger.log(tag, "------------------ FAIL response - " + response.body())
                headerToken(response)
                hideProgressDialog()
                failurResponseCheckIn(response, url, reqBody)
                apiResponse.onFailure(null)
            }
        } catch (e: Exception) {
            Logger.log(tag, e.message)
        }
    }

    private fun checkEmptyRes(url: String, reqBody: Any?, json: String, logNonFatalError: Boolean) {
        try {
            val jsonObject = JSONObject(json)
            var success = true
            //  String msg = "";
            /* if (jsonObject.has("returnMessage")) {
                msg = jsonObject.getString("returnMessage");
            } else if (jsonObject.has("message")) {
                msg = jsonObject.getString("message");
            }
            // failureHandler.onFailure(url,reqBody,response.code());
            showToast(msg);*/
            when {
                jsonObject.has("success") -> {
                    success = jsonObject.getBoolean("success")
                }
                jsonObject.has("isSuccess") -> {
                    success = jsonObject.getBoolean("isSuccess")
                }
                jsonObject.has("status") -> {
                    success = java.lang.Boolean.parseBoolean(jsonObject.getString("status"))
                }
            }
            if (!success) {
                  var msg = "";
                 if (jsonObject.has("returnMessage")) {
                    msg = jsonObject.getString("returnMessage");
                } else if (jsonObject.has("message")) {
                    msg = jsonObject.getString("message");
                }
                // failureHandler.onFailure(url,reqBody,response.code());
                showToast(msg);
                if (logNonFatalError && failureHandler != null) {
                    failureHandler?.onFailure(url, reqBody, json)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun failurResponseCheckIn(response: Response<*>?, url: String, reqBody: Any?) {
        try {
            if (response != null) {
                Logger.log("AEL", "--------ERROR CODE ----EXCEPTION----------" + response.code())
                var errorJson = ""
                if (failureHandler != null) {
                    val errorResponseModel = ErrorResponseModel()
                    if (response.errorBody() != null) {
                        errorJson = response.errorBody()?.string() ?: ""
                        errorResponseModel.errorResponse = errorJson
                        errorResponseModel.responseCode = response.code()
                    } else {
                        errorResponseModel.errorResponse = "Null Response Error Body"
                        errorResponseModel.responseCode = 0
                    }
                    failureHandler?.onFailure(url, reqBody, errorResponseModel)
                }
                if (response.code() == 500) {
                    showToast("Server Error . Please Retry.")
                } else if (response.code() == 401) {
                    if (loginClass == null) {
                        val authError: AuthError? = GSONUtil.fromJson(errorJson, AuthError::class.java)
                        if (authError != null && authError.status == 401L && (authError.message
                                        ?: "").isNotEmpty()) {
                            authError.message?.let { showToast(it) }
                        } else {
                            showToast("Unauthorized Login Failed.")
                        }
                    } else {
                        showToast("Unauthorized Login Failed.")
                        (context as Activity).finishAffinity()
                        val i = Intent(context, loginClass)
                        context.startActivity(i)
                    }
                } else if (response.code() == 400) {
                    showToast("Request Failed..Try again")
                } else {
                    showToast("Server Error :- ")
                }
            } else {
                showToast("No Network ")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showToast(msg: String) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showProgressDialog() {
        /*if (isProgressDialogEnable && (progressDialog == null || progressDialog?.isShowing != true)) {
            Logger.log(tag, " Progress Dialog")
            progressDialog = CustomDialog(context)
            progressDialog?.show()
        }*/

    }

    private fun hideProgressDialog() {
      /*  try {
            if (isProgressDialogEnable && progressDialog != null && progressDialog?.isShowing == true) {
                progressDialog?.dismiss()
            }
        } catch (e: Exception) {
            Logger.log(tag, e.message)
        }*/
    }

    //--------------Headers -----------------------//
    private fun headerToken(response: Response<*>?) {
        // Logger.log(HEADER_TAG, "=================================");
        response?.headers()?.let {
            Logger.log(tag, "Response" + response.body())
            if (response.headers()[ConstantParameters.CA_TOKEN] != null) {
                ApiUrls.headerTokenn = response.headers()[ConstantParameters.CA_TOKEN].toString()
                Logger.log(tag, "HeaderUrl--------" + ApiUrls.headerTokenn)
            }
        }
    } /*public void headerToken(Response response) {
       // Logger.log(HEADER_TAG, "=================================");
        Logger.log("Anamika=======","Response"+response.body());
        if (response.headers() != null) {
            if(response.headers().get("X-OAPI-Auth-Header")!=null){
                API_urls.headerTokenn  =response.headers().get("X-OAPI-Auth-Header");
                Logger.log("HeaderUrl--------","--"+ API_urls.headerTokenn);
            }
            if(response.headers().get("X-OAPI-Node-Id")!=null){
                API_urls.headerNodeID = response.headers().get("X-OAPI-Node-Id");
                Logger.log("HeaderNode--------","--"+ API_urls.headerNodeID);
            }


//            for (Header header : headerList) {
//                // Logger.log(HEADER_TAG, header.getName() + " ******** " + header.getValue());
//                if (header.getName() != null) {
//                    if (header.getName().toString().equals("X-OAPI-Auth-Header")) {//X-OAPI-Auth-Header
//                        API_urls.headerTokenn = header.getValue();
//                    }
//                    if (header.getName().toString().equals("X-OAPI-Node-Id")) {
//                        API_urls.headerNodeID = header.getValue();
//                    }
//                }
//            }

        }
    }*/

    companion object {
        var failureHandler: FailureHandler? = null

        @Suppress("SpellCheckingInspection")
        fun pojoToMap(obj: Any): HashMap<String, String> {
            var map = HashMap<String, String>()
            try {
                /* Field[] aClassFields = obj.getClass().getDeclaredFields();
            for(Field field: aClassFields){
                field.setAccessible(true);
                map.put(field.getName(),(String)field.get(obj));
            }*/
                val json: String
                json = if (obj is JSONObject) {
                    obj.toString()
                } else {
                    val gson = GsonBuilder().create()
                    gson.toJson(obj)
                }
                map = Gson().fromJson<HashMap<String, String>>(json, HashMap::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return map
        }
    }

    private fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
    fun <T:Any>getDataFromFile(fileName:String,c: Class<T>): T {
        return GSONUtil.fromJson(getJsonDataFromAsset(fileName),c)
    }

}