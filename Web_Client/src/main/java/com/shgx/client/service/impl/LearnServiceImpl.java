package com.shgx.client.service.impl;

import com.shgx.client.service.LearnService;
import com.shgx.grpc.GetReply;
import com.shgx.grpc.MachineLearningGrpc;
import com.shgx.grpc.SendRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: guangxush
 * @create: 2020/03/12
 */
@Service
@Slf4j
public class LearnServiceImpl implements LearnService {

    private final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext(true)
            .build();
    private final MachineLearningGrpc.MachineLearningBlockingStub blockingStub = MachineLearningGrpc.newBlockingStub(channel);

    @Override
    public String learn() {
        try {
            String param = "train";
            return connect(param);
        } finally {
            shutdown();
        }
    }

    public void shutdown(){
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send params to server.
     */
    public String connect(String params) {
        log.info("Will try to connect " + params + " ...");
        SendRequest request = SendRequest.newBuilder().setParam(params).build();
        GetReply response;
        try {
            response = blockingStub.startLearn(request);
        } catch (StatusRuntimeException e) {
            log.info("RPC failed: " + e.getStatus());
            return "error";
        }
        return("Response Result: " + response.getResult());
    }
}
