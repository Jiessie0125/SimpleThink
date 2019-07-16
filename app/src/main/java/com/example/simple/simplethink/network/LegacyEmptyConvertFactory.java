package com.example.simple.simplethink.network;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


public class LegacyEmptyConvertFactory extends Converter.Factory {
    public static LegacyEmptyConvertFactory create() {
        return new LegacyEmptyConvertFactory();
    }


    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody body) throws IOException {
                String content = body.string();
                if (content.equals("[]")) {
                    return delegate.convert(ResponseBody.create(MediaType.parse("application/json; charset=UTF-8"), "{}"));
                } else {
                    return delegate.convert(ResponseBody.create(MediaType.parse("application/json; charset=UTF-8"), content));
                }
            }
        };
    }
}
