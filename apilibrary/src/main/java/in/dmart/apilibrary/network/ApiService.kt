package `in`.dmart.apilibrary.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by anamika.chavan on 10-11-2017.
 */
interface ApiService {
    @POST
    fun postData(@Url url: String, @HeaderMap headerMap: Map<String, String>?, @Body obj: Any?): Call<ResponseBody>

    @GET
    fun getData(@Url url: String, @HeaderMap headerMap: Map<String, String>?, @QueryMap queryMap: Map<String, String>?): Call<ResponseBody>

    @GET
    fun getData(@Url url: String, @HeaderMap headerMap: Map<String, String>?): Call<ResponseBody>

    @POST
    fun postQuery(@Url url: String, @HeaderMap headerMap: Map<String, String>?, @QueryMap queryMap: Map<String, String>?): Call<ResponseBody>
}