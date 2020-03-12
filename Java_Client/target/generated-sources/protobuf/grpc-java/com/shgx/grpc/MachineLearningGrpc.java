package com.shgx.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * The machine learning service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: MachineLearning.proto")
public final class MachineLearningGrpc {

  private MachineLearningGrpc() {}

  public static final String SERVICE_NAME = "example.MachineLearning";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.shgx.grpc.SendRequest,
      com.shgx.grpc.GetReply> getStartLearnMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StartLearn",
      requestType = com.shgx.grpc.SendRequest.class,
      responseType = com.shgx.grpc.GetReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.shgx.grpc.SendRequest,
      com.shgx.grpc.GetReply> getStartLearnMethod() {
    io.grpc.MethodDescriptor<com.shgx.grpc.SendRequest, com.shgx.grpc.GetReply> getStartLearnMethod;
    if ((getStartLearnMethod = MachineLearningGrpc.getStartLearnMethod) == null) {
      synchronized (MachineLearningGrpc.class) {
        if ((getStartLearnMethod = MachineLearningGrpc.getStartLearnMethod) == null) {
          MachineLearningGrpc.getStartLearnMethod = getStartLearnMethod = 
              io.grpc.MethodDescriptor.<com.shgx.grpc.SendRequest, com.shgx.grpc.GetReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "example.MachineLearning", "StartLearn"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.shgx.grpc.SendRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.shgx.grpc.GetReply.getDefaultInstance()))
                  .setSchemaDescriptor(new MachineLearningMethodDescriptorSupplier("StartLearn"))
                  .build();
          }
        }
     }
     return getStartLearnMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MachineLearningStub newStub(io.grpc.Channel channel) {
    return new MachineLearningStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MachineLearningBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MachineLearningBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MachineLearningFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MachineLearningFutureStub(channel);
  }

  /**
   * <pre>
   * The machine learning service definition.
   * </pre>
   */
  public static abstract class MachineLearningImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a request
     * </pre>
     */
    public void startLearn(com.shgx.grpc.SendRequest request,
        io.grpc.stub.StreamObserver<com.shgx.grpc.GetReply> responseObserver) {
      asyncUnimplementedUnaryCall(getStartLearnMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getStartLearnMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.shgx.grpc.SendRequest,
                com.shgx.grpc.GetReply>(
                  this, METHODID_START_LEARN)))
          .build();
    }
  }

  /**
   * <pre>
   * The machine learning service definition.
   * </pre>
   */
  public static final class MachineLearningStub extends io.grpc.stub.AbstractStub<MachineLearningStub> {
    private MachineLearningStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MachineLearningStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MachineLearningStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MachineLearningStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a request
     * </pre>
     */
    public void startLearn(com.shgx.grpc.SendRequest request,
        io.grpc.stub.StreamObserver<com.shgx.grpc.GetReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getStartLearnMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The machine learning service definition.
   * </pre>
   */
  public static final class MachineLearningBlockingStub extends io.grpc.stub.AbstractStub<MachineLearningBlockingStub> {
    private MachineLearningBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MachineLearningBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MachineLearningBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MachineLearningBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a request
     * </pre>
     */
    public com.shgx.grpc.GetReply startLearn(com.shgx.grpc.SendRequest request) {
      return blockingUnaryCall(
          getChannel(), getStartLearnMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The machine learning service definition.
   * </pre>
   */
  public static final class MachineLearningFutureStub extends io.grpc.stub.AbstractStub<MachineLearningFutureStub> {
    private MachineLearningFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MachineLearningFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MachineLearningFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MachineLearningFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a request
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.shgx.grpc.GetReply> startLearn(
        com.shgx.grpc.SendRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getStartLearnMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_START_LEARN = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MachineLearningImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MachineLearningImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_START_LEARN:
          serviceImpl.startLearn((com.shgx.grpc.SendRequest) request,
              (io.grpc.stub.StreamObserver<com.shgx.grpc.GetReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class MachineLearningBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MachineLearningBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.shgx.grpc.MachineLearningServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MachineLearning");
    }
  }

  private static final class MachineLearningFileDescriptorSupplier
      extends MachineLearningBaseDescriptorSupplier {
    MachineLearningFileDescriptorSupplier() {}
  }

  private static final class MachineLearningMethodDescriptorSupplier
      extends MachineLearningBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MachineLearningMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MachineLearningGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MachineLearningFileDescriptorSupplier())
              .addMethod(getStartLearnMethod())
              .build();
        }
      }
    }
    return result;
  }
}
