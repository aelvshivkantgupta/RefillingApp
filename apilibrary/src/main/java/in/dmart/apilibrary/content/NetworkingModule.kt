package `in`.dmart.apilibrary.content

import `in`.dmart.apilibrary.constant.ApiUrls
import `in`.dmart.apilibrary.constant.ConstantParameters
import `in`.dmart.apilibrary.network.ApiService
import `in`.dmart.apilibrary.util.Logger
import androidx.annotation.Nullable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier

/**
 * Created by anamika.chavan on 10-11-2017.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkingModule {

     val responseTimeOut: Int
        get() = 120
    @Provides
    fun provideBaseUrl() = ApiUrls.baseUrl

    @Provides
    @Singleton
    fun provideOkHttpClient(@Nullable
                            interceptor:Interceptor):OkHttpClient? {
        return try {
            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.addInterceptor(interceptor)
            okHttpClient.addInterceptor(Logger.loggingInceptor)
            okHttpClient.connectTimeout(responseTimeOut.toLong(), TimeUnit.SECONDS)
            okHttpClient.readTimeout(responseTimeOut.toLong(), TimeUnit.SECONDS)
            okHttpClient.writeTimeout(responseTimeOut.toLong(), TimeUnit.SECONDS)
            okHttpClient.hostnameVerifier(HostnameVerifier { _, _ -> true })
            okHttpClient.build()
        } catch (e: Exception) {
            //Log.e("NetworkAbstract", e.getMessage(), e);
            Logger.log("NetworkAbstract", e.message)
            null
        }
    }


    @Provides
    @Singleton
    fun provideInterceptor() = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val request = original.newBuilder()
            request.addHeader(ConstantParameters.CA_TOKEN, ApiUrls.headerTokenn)
            request.addHeader(ConstantParameters.MODE, ConstantParameters.SELECTED_MODE)

            /* //for dad
            request.addHeader("x-dad-key", API_urls.headerTokenn);
            request.addHeader("X-OAPI-Node-Id", API_urls.headerNodeID);*/
//            request.addHeader("user-id", "app_aab0");

            //request.addHeader("X-OAPI-Node-Id", API_urls.headerNodeID);
            return chain.proceed(request.build())
        }
    }
    @Provides
    @Singleton
    fun provideRetrofit(
        @Nullable
        okHttpClient: OkHttpClient,
        @Nullable
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(@Nullable
                          retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


}