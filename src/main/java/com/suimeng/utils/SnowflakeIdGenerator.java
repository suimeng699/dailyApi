package com.suimeng.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class SnowflakeIdGenerator {
        // 起始时间戳（2021-01-01 00:00:00, UTC）
        private static final long START_TIME = 1609459200000L;

        // 机器节点 ID 占用的位数
        private static final long NODE_ID_BITS = 10L;

        // 序列号占用的位数
        private static final long SEQUENCE_BITS = 12L;

        // 机器节点 ID 的最大值
        private static final long MAX_NODE_ID = (1L << NODE_ID_BITS) - 1;

        // 序列号的最大值
        private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;

        // 时间戳左移位数（序列号位数 + 机器节点 ID 位数）
        private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + NODE_ID_BITS;

        // 机器节点 ID 左移位数（序列号位数）
        private static final long NODE_ID_LEFT_SHIFT = SEQUENCE_BITS;

        // 机器节点 ID
        private final long nodeId = 0L;

        // 序列号
        private final AtomicLong sequence = new AtomicLong(0);

        // 上一次的时间戳
        private volatile long lastTimestamp = -1L;

//        /**
//         * 构造函数
//         * @param nodeId 机器节点 ID（0 到 1023）
//         */
//        public SnowflakeIdGenerator() {
//            if (nodeId < 0 || nodeId > MAX_NODE_ID) {
//                throw new IllegalArgumentException("Node ID must be between 0 and " + MAX_NODE_ID);
//            }
//            this.nodeId = nodeId;
//        }

        /**
         * 生成下一个 ID
         * @return 雪花算法生成的 ID
         */
        public synchronized long nextId() {
            long timestamp = System.currentTimeMillis();

            // 如果当前时间小于上一次的时间戳，说明发生了时钟回拨，抛出异常
            if (timestamp < lastTimestamp) {
                throw new RuntimeException("Clock moved backwards. Refusing to generate id for " +
                        (lastTimestamp - timestamp) + " milliseconds");
            }

            // 如果是同一时间戳，序列号加 1
            if (timestamp == lastTimestamp) {
                long seq = sequence.getAndIncrement();
                if (seq > MAX_SEQUENCE) {
                    // 序列号溢出，等待下一毫秒
                    timestamp = tilNextMillis(lastTimestamp);
                    sequence.set(0);
                }
            } else {
                // 时间戳改变，重置序列号
                sequence.set(0);
            }

            // 更新上一次的时间戳
            lastTimestamp = timestamp;

            // 按位拼接生成最终的 ID
            return ((timestamp - START_TIME) << TIMESTAMP_LEFT_SHIFT) |
                    (nodeId << NODE_ID_LEFT_SHIFT) |
                    sequence.get();
        }

        /**
         * 等待下一毫秒
         * @param lastTimestamp 上一次的时间戳
         * @return 下一毫秒的时间戳
         */
        private long tilNextMillis(long lastTimestamp) {
            long timestamp = System.currentTimeMillis();
            while (timestamp <= lastTimestamp) {
                timestamp = System.currentTimeMillis();
            }
            return timestamp;
        }
}
