package com.github.shawnliang.register.server;

/**
 * Description :   .
 * 自我保护机制
 * @author : Phoebe
 * @date : Created in 2021/7/4
 */
public class SelfProtectPolicy {

    public static SelfProtectPolicy instance = new SelfProtectPolicy();

    /**
     * 一分钟期待的心跳次数
     * 服务实例数 * 2
     */
    private long expectedHeartBeatRate = 0L;

    /**
     * 每分钟期待的心跳次数的阈值
     */
    private long expectedHeartBeatThreshold = 0L;

    /**
     * 阈值的比例
     */
    private double heartBeatThreshold = 0.75;

    public Double getHeartBeatThreshold() {
        return heartBeatThreshold;
    }

    public static SelfProtectPolicy getInstance() {
        return instance;
    }

    public long getExpectedHeartBeatRate() {
        return expectedHeartBeatRate;
    }

    public void setExpectedHeartBeatRate(long expectedHeartBeatRate) {
        this.expectedHeartBeatRate = expectedHeartBeatRate;
    }

    public long getExpectedHeartBeatThreshold() {
        return expectedHeartBeatThreshold;
    }

    public void setExpectedHeartBeatThreshold(long expectedHeartBeatThreshold) {
        this.expectedHeartBeatThreshold = expectedHeartBeatThreshold;
    }

    /**
     * 是否需要开启自我保护机制
     * @return
     */
    public boolean isEnableSelfProtect() {
        HeartBeatMessuredRate heartBeatMessuredRate = HeartBeatMessuredRate.getInstance();
        // 拿到最近一分钟的心跳数
        long latestHeatBeatRate = heartBeatMessuredRate.getHeartBeatCount();

        return latestHeatBeatRate < expectedHeartBeatThreshold;
    }
}
