#!/bin/bash

docker run   -h kafka.smartsend -p 9092 -p 2182 -p 2888 -p 3888 -v ${PWD}/../log/kafka:/root/log/kafka -v ${PWD}/../log/hbase:/root/log/hbase -it kafka_smartsend
