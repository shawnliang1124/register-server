package com.github.shawnliang.register.server;


import java.util.Optional;

/**
 * Description :   .
 * 注册中心的入口
 * @author : Phoebe
 * @date : Created in 2021/7/1
 */
public class RegisterServerController {

    private Registry registry = Registry.getInstance();

    /**
     * 像注册中心发送请求注册
     * @param registerReq
     * @return
     */
    public RegisterResp register(RegisterReq registerReq) {
        RegisterResp registerResp = new RegisterResp();

        try {
            ServiceInstance serviceInstance = new ServiceInstance();

            serviceInstance.setHostname(registerReq.getHostName());
            serviceInstance.setIp(registerReq.getIp());
            serviceInstance.setPort(registerReq.getPort());
            serviceInstance.setServiceInstanceId(registerReq.getInstanceId());
            serviceInstance.setServiceName(registerReq.getServiceName());

            registerResp.setStatus(RegisterResp.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return registerResp;
    }

    /**
     * 发送心跳请求
     * @param heartBeatReq
     * @return
     */
    public HeartBeatResp sendHeartBeat(HeartBeatReq heartBeatReq) {
        HeartBeatResp heartBeatResp = new HeartBeatResp();

        try {
            ServiceInstance serviceInstance = registry
                    .getServiceInstance(heartBeatReq.getServiceName(),
                            heartBeatReq.getInstanceId());
            Optional.ofNullable(serviceInstance)
                    .ifPresent(ServiceInstance::renew);

            heartBeatResp.setStatus(HeartBeatResp.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            heartBeatResp.setStatus(HeartBeatResp.FAILURE);
        }

        return heartBeatResp;
    }

}
