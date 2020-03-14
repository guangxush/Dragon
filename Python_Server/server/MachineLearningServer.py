# -*- coding: utf-8 -*-

import time

import grpc
from concurrent import futures
from example import MachineLearning_pb2, MachineLearning_pb2_grpc
from learn.main import train, test

_ONE_DAY_IN_SECONDS = 60 * 60 * 24


# python服务端代码
class MachineLearning(MachineLearning_pb2_grpc.MachineLearningServicer):

    def StartLearn(self, request, context):
        if request.name == 'train':
            train()
        elif request.name == 'test':
            test()
        else:
            pass
        return MachineLearning_pb2.GetReply(message='The model is %sing!' % request.name)


def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    MachineLearning_pb2_grpc.add_MachineLearningServicer_to_server(MachineLearning(), server)
    server.add_insecure_port('[::]:50052')
    server.start()
    try:
        while True:
            time.sleep(_ONE_DAY_IN_SECONDS)
    except KeyboardInterrupt:
        server.stop(0)


if __name__ == '__main__':
    serve()
