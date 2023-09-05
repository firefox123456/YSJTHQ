package huangqi.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Random;

/**
 * <p>主键生成器</p>
 * 参考Twitter主键生成算法z
 * * @author "黄骐"
 * * @date 2023/08/17 23:08
 * @since 1.0
 */
@Slf4j
public class IdWorker {

    // ID生成机器的代号，机器码组成部分，可在配置文件中指明
    // 同一个datacenter下的workerId不能重复
    private final long workerId;

    // 机器码组成部分，可在配置文件中指明
    private final long datacenterId;

    // 0，并发控制
    private long sequence = 0L;

    //机器标识位数 只允许workId的范围为：1-15
    private final long workerIdBits = 4L;

    //数据中心标识位数
    private final long datacenterIdBits = 4L;

    //毫秒内自增位 sequence值控制在0-4095
    private final long sequenceBits = 12L;

    //时间毫秒左移22位
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    //最新产生id的时间戳
    private long lastTimestamp = -1L;

    public IdWorker(long workerId, long datacenterId) {
        //机器ID最大值 1023,1111111111,10位
        long maxWorkerId = ~(-1L << workerIdBits);

        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",
                    maxWorkerId));
        }
        //数据中心ID最大值
        long maxDatacenterId = ~(-1L << datacenterIdBits);
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0 %n", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        log.info(String.format(
                "worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d %n",
                timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId));
    }

    public IdWorker() {
        this.datacenterId = RandomUtils.nextInt(1, 16);
        this.workerId = RandomUtils.nextInt(1, 16);
        log.warn(String.format("IdWorker init. datacenterId:%d, workerId:%d", this.datacenterId, this.workerId));
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        //时间错误
        if (timestamp < lastTimestamp) {
            log.error(String.format("clock is moving backwards.  Rejecting requests until %d. %n", lastTimestamp));
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                            lastTimestamp - timestamp));
        }

        // 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环)，下次再使用时sequence是新值
        if (lastTimestamp == timestamp) {
            // 4095,111111111111,12位
            long sequenceMask = ~(-1L << sequenceBits);
            //当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                //当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        //记录此次产生id的时间戳
        lastTimestamp = timestamp;

        // 起始标记点，作为基准
        //private final long twepoch = 1288834974657L;
        long twepoch = 1461295327832L;
        //机器ID偏左移12位
        long workerIdShift = sequenceBits;
        //数据中心ID左移17位
        long datacenterIdShift = sequenceBits + workerIdBits;
        //ID偏移组合生成最终的ID，并返回ID
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) | sequence;
    }

    public synchronized String nextStringId() {
        return Long.toString(nextId());
    }

    /**
     * 创建带时间的NO
     *
     * @return yyyyMMddHHmmss+n位随机数
     */
    public String createNo(int n) {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + RandomStringUtils.randomNumeric(n);
    }

    /**
     * 返回0到9的字符,n位
     *
     * @return
     */
    public static String genRandom(int n) {
        Random random = new Random();
        String randomStr = "";
        for (int i = 0; i < n; i++) {
            int s1 = random.nextInt(10);
            randomStr += s1;
        }
        return randomStr;
    }

    //等待下一个毫秒的到来
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}