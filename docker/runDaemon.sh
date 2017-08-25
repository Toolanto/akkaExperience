#!/bin/bash

docker run   -h kafka.smartsend -p 9092 -p 2182 -v ${PWD}/../log/kafka:/root/log/kafka -v ${PWD}/../log/hbase:/root/log/hbase -t kafka_smartsend
