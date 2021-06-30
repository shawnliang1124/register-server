package com.github.shawnliang.register.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/7/1
 */
public class Registry {


    /**
     * 注册表是一个单例
     */
    private static Registry instance = new Registry();

    private Registry() {

    }

    private Map<String, Map<String, ServiceInstance>> registry
            = new HashMap<String, Map<String, ServiceInstance>>();

    public void register(ServiceInstance serviceInstance) {
        String serviceName = serviceInstance.getServiceName();
        Map<String, ServiceInstance> serviceInstanceMap = registry.get(serviceName);

        if (serviceInstanceMap == null) {
            serviceInstanceMap = new HashMap<String, ServiceInstance>();
            registry.put(serviceName, serviceInstanceMap);
        }

        serviceInstanceMap.put(serviceInstance.getServiceInstanceId(), serviceInstance);

        System.out.println("服务实例【" + serviceInstance + "】，完成注册......");
        System.out.println("注册表：" + registry);
    }

    /**
     * 获取实例
     * @param serviceName
     * @param serviceInstance
     * @return
     */
    public ServiceInstance getServiceInstance(String serviceName, String serviceInstance) {
        return Optional.ofNullable(registry.get(serviceName))
                .map(x -> x.get(serviceInstance))
                .orElse(null);
    }

    /**
     * 摘除实例
     * @param serviceName
     * @param serviceInstance
     */
    public void removeInstance(String serviceName, String serviceInstance) {
        System.out.println("从服务实例中摘除" + serviceInstance);

        Optional.ofNullable(registry.get(serviceName))
                .ifPresent(x -> x.remove(serviceInstance));

    }

    public static Registry getInstance() {
        return instance;
    }


    /**
     * 获取整个注册表
     * @return
     */
    public Map<String, Map<String, ServiceInstance>> getRegistry() {
        return registry;
    }


}
