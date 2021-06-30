package com.github.shawnliang.register.server;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/7/1
 */
public class ServiceInstance {

    public ServiceInstance() {
        this.lease = new Lease();
    }

    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 主机名
     */
    private String hostname;
    /**
     * 端口号
     */
    private int port;
    /**
     * 服务实例id
     */
    private String serviceInstanceId;

    private Lease lease;

    /**
     * 判断一个服务实例不再存活的周期
     */
    private static final Long NOT_ALIVE_PERIOD = 90 * 1000L;

    /**
     * 服务契约
     */
    private class Lease {

        /**
         * 最近的心跳时间
         */
        private Long latestHeartBeatTime =System.currentTimeMillis();

    }

    public void renew() {
        this.lease.latestHeartBeatTime = System.currentTimeMillis();
        System.out.println("服务实例【" + serviceInstanceId + "】，进行续约：" + this.lease.latestHeartBeatTime);
    }

    public Boolean isAlive() {
        if (System.currentTimeMillis() -  this.lease.latestHeartBeatTime > NOT_ALIVE_PERIOD) {
            System.out.println("服务实例【" + serviceInstanceId + "】，不再存活");
            return false;
        }
        System.out.println("服务实例【" + serviceInstanceId + "】，存活");
        return true;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    public void setServiceInstanceId(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }
}
