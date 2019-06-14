package com.example.simple.simplethink.network

import com.example.simple.simplethink.utils.URLConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class GetRetrofitServiceManager private constructor() {
    private val mRetrofit: Retrofit


    init {
        // 创建 OKHttpClient
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT.toLong(), TimeUnit.SECONDS)//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT.toLong(), TimeUnit.SECONDS)//读操作超时时间

        //set header info
        val headerInterceptor = object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .method(originalRequest.method(), originalRequest.body())
                //requestBuilder.addHeader("Authorization", "Bearer " + BaseConstant.TOKEN)//添加请求头信息，服务器进行token有效性验证
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        }
        builder.addInterceptor(headerInterceptor)

        // 创建Retrofit
        mRetrofit = Retrofit.Builder()
                .client(builder.build())
                .baseUrl(URLConstant.BASE_URL)
                .build()
    }

    private object SingletonHolder {
        val INSTANCE = GetRetrofitServiceManager()
    }

    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T>
     * @return
    </T> */
    fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }

    companion object {
        private val DEFAULT_TIME_OUT = 5//超时时间 5s
        private val DEFAULT_READ_TIME_OUT = 10

        /**
         * 获取RetrofitServiceManager
         * @return
         */
        val instance: GetRetrofitServiceManager
            get() = SingletonHolder.INSTANCE
    }

}