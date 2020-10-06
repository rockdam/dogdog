package com.makeus.dogdog.config;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.makeus.dogdog.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.makeus.dogdog.src.ApplicationClass.sSharedPreferences;

public class XAccessTokenInterceptor implements Interceptor {

    @Override
    @NonNull
    public Response intercept(@NonNull final Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();

        final String jwtToken = sSharedPreferences.getString(X_ACCESS_TOKEN, null);
        if (jwtToken != null) {
            builder.addHeader("X-ACCESS-TOKEN", jwtToken);
        }
        return chain.proceed(builder.build());
    }
}
