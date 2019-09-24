package com.undefinedlabs.nikeriposteintegration.functionaltest.scope;

import com.undefinedlabs.nikeriposteintegration.functionaltest.scope.utils.AbstractConcurrentIT;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class RiposteProxyRouterEndpointIT extends AbstractConcurrentIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(RiposteProxyRouterEndpointIT.class);

    @Test
    public void should_invoke_riposte_proxy_router_endpoint_concurrently() throws IOException {
        //Given
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .build();

        final String concurrentReqsStr = System.getenv("CONCURRENT_REQUESTS");
        int concurrentReqs = (StringUtils.isNotEmpty(concurrentReqsStr) && StringUtils.isNumeric(concurrentReqsStr)) ? Integer.parseInt(concurrentReqsStr) : 5;

        LOGGER.info("--- Testing Proxy Router Endpoint --> Sending " + concurrentReqs + " requests concurrently");

        //When
        final List<Future<Response>> futureResponses = IntStream.range(0, concurrentReqs).mapToObj((num) -> makeRequest(okHttpClient, new Request.Builder().url(props.nikeriposteintegrationHost+"/exampleProxy?num="+num).build())).collect(Collectors.toList());
        final CompletableFuture<List<Response>> futureListResponses = CompletableFuture.allOf(futureResponses.toArray(new CompletableFuture[0])).thenApply(v -> futureResponses.stream().map(futureResponse -> {
            try {
                return futureResponse.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList()));

        //Then
        try {
            final List<Response> responses = futureListResponses.get();
            for(Response response : responses) {
                assertThat(response.isSuccessful()).isTrue();
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
