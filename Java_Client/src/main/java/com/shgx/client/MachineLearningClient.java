package com.shgx.client;

import com.shgx.grpc.MachineLearningGrpc;
import com.shgx.grpc.GetReply;
import com.shgx.grpc.SendRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * RPC 调用Server程序
 * @author: guangxush
 * @create: 2020/01/03
 */
public class MachineLearningClient {

    private static final Logger logger = Logger.getLogger(MachineLearningClient.class.getName());

    private final ManagedChannel channel;
    private final MachineLearningGrpc.MachineLearningBlockingStub blockingStub;

    /**
     * Construct client connecting to MachineLearning server at {@code host:port}.
     */
    public MachineLearningClient(String host, int port) {

        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build();
        blockingStub = MachineLearningGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Send params to server.
     */
    public void connect(String params) {
        logger.info("Will try to connect " + params + " ...");
        SendRequest request = SendRequest.newBuilder().setParam(params).build();
        GetReply response;
        try {
            response = blockingStub.startLearn(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Response Result: " + response.getResult());
    }

    /**
     * Machine Learning server. If provided, the first element of {@code args} is the param to send to server.
     */
    public static void main(String[] args) throws Exception {
        MachineLearningClient client = new MachineLearningClient("localhost", 50052);
        try {

            String param = "train";
            if (args.length > 0) {
                param = args[0];
            }
            client.connect(param);
        } finally {
            client.shutdown();
        }
    }
}
