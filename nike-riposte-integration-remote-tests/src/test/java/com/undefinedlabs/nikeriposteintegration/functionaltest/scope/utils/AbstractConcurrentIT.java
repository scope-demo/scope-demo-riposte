package com.undefinedlabs.nikeriposteintegration.functionaltest.scope.utils;

import com.undefinedlabs.nikeriposteintegration.functionaltest.PropertiesHelper;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.Future;

public abstract class AbstractConcurrentIT {

    protected final PropertiesHelper props = PropertiesHelper.getInstance();

    protected Future<Response> makeRequest(OkHttpClient client, Request request) {
        final Call call = client.newCall(request);
        final OkHttpResponseFuture result = new OkHttpResponseFuture();
        call.enqueue(result);
        return result.future;
    }
}
