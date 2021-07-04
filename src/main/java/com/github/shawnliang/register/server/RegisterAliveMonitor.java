package com.github.shawnliang.register.server;

import java.util.Map;

/**
 * Description :   .
 *心跳检查线程， 检查我的服务是否是存活的
 * @author : Phoebe
 * @date : Created in 2021/7/1
 */
public class RegisterAliveMonitor {

    /**
     * 检查服务实例是否存活的间隔
     */
    private static final Long CHECK_ALIVE_INTERVAL = 60 * 1000L;

    private Daemon daemon;

    public RegisterAliveMonitor() {
        daemon = new Daemon();
        daemon.setDaemon(true);
        daemon.setName("keep-alive-thread");
    }

    public void init() {
        daemon.start();
    }


    private static class Daemon extends Thread {
        private final Registry registry = Registry.getInstance();

        @Override
        public void run() {
            while (true) {
                // 判断是否开启了自我保护机制
                SelfProtectPolicy instance = SelfProtectPolicy.getInstance();
                if (instance.isEnableSelfProtect()) {
                    System.out.println("开启了自我保护机制...不摘除服务实例");
                    try {
                        Thread.sleep(CHECK_ALIVE_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                Map<String, Map<String, ServiceInstance>> registryRegistry = this.registry.getRegistry();

                if (registryRegistry != null && registryRegistry.size() > 0) {
                    registryRegistry.forEach((key, serviceInstanceMap) -> {
                        Map<String, ServiceInstance> instanceMap = registryRegistry.get(key);

                        if (instanceMap != null) {
                            for (ServiceInstance serviceInstance : instanceMap.values()) {
                                if (serviceInstance.isAlive()) {
                                    registry.removeInstance(key, serviceInstance.getServiceInstanceId());

                                    // 更新自我保护机制的阈值
                                    synchronized (SelfProtectPolicy.class) {
                                        SelfProtectPolicy selfProtectPolicy = SelfProtectPolicy.getInstance();
                                        selfProtectPolicy.setExpectedHeartBeatRate(selfProtectPolicy.getExpectedHeartBeatRate() - 2);
                                        selfProtectPolicy.setExpectedHeartBeatThreshold(
                                                (long) (selfProtectPolicy.getExpectedHeartBeatRate() *
                                                        selfProtectPolicy.getHeartBeatThreshold())
                                        );
                                    }
                                }
                            }
                        }
                    });
                   try {
                        Thread.sleep(CHECK_ALIVE_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
