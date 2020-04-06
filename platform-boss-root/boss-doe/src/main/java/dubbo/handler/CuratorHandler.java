/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package dubbo.handler;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.registry.zookeeper.ZookeeperRegistry;

import com.alibaba.dubbo.registry.zookeeper.ZookeeperRegistryFactory;
import com.alibaba.dubbo.remoting.zookeeper.ZookeeperClient;
import com.alibaba.dubbo.remoting.zookeeper.curator.CuratorZookeeperTransporter;
import dubbo.dto.ConnectDTO;
import dubbo.dto.MethodModelDTO;
import dubbo.exception.DoeException;
import dubbo.model.MethodModel;
import dubbo.model.ServiceModel;
import dubbo.model.UrlModel;
import dubbo.util.MD5Util;
import dubbo.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class CuratorHandler {

    private final String protocol;
    private final String host;
    private final int port;
    private ZookeeperClient zkClient;
    private ZookeeperRegistry registry;
    private String root = "/dubbo";

    public CuratorHandler(String protocol, String host, int port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    public void doConnect() throws NoSuchFieldException, IllegalAccessException {

        CuratorZookeeperTransporter zookeeperTransporter = new CuratorZookeeperTransporter();
        URL url = new URL(protocol, host, port);

        registry = new ZookeeperRegistry(url, zookeeperTransporter);

        Field field = registry.getClass().getDeclaredField("zkClient");
        field.setAccessible(true);
        zkClient = (ZookeeperClient) field.get(registry);
        System.out.println(registry.isAvailable());

    }

    public List<ServiceModel> getInterfaces() {
        List<ServiceModel> ret = new ArrayList<>();
        System.out.println(ret);
        List<String> list = zkClient.getChildren(root);
        for (int i = 0; i < list.size(); i++) {
            ServiceModel model = new ServiceModel();
            model.setServiceName(list.get(i));
            ret.add(model);
        }

        return ret;
    }

    public List<UrlModel> getProviders(ConnectDTO dto) {

        if (null == dto) {
            throw new DoeException("dto can't be null.");
        }
        if (StringUtils.isEmpty(dto.getServiceName())) {
            throw new DoeException("service name can't be null.");
        }

        Map<String, String> map = new HashMap<>();
        map.put(Constants.INTERFACE_KEY, dto.getServiceName());

        if (StringUtils.isNotEmpty(dto.getVersion())) {
            map.put(Constants.VERSION_KEY, dto.getVersion());
        }
        if (StringUtils.isNotEmpty(dto.getGroup())) {
            map.put(Constants.GROUP_KEY, dto.getGroup());
        }

        URL url = new URL(protocol, host, port, map);
        List<URL> list = registry.lookup(url);

        List<UrlModel> ret = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            URL urling = list.get(i);
            UrlModel model = new UrlModel(i+"", url);
            ret.add(model);
        }

        return ret;
    }

    public List<MethodModelDTO> getMethods(String interfaceName) throws ClassNotFoundException {

        Class<?> clazz = Class.forName(interfaceName);
        Method[] methods = clazz.getMethods();

        List<MethodModelDTO> ret = new ArrayList<>();
        Arrays.stream(methods).forEach(m -> {

            String key = generateMethodKey(m, interfaceName);
            MethodModel model = new MethodModel(key, m);
            ret.add(new MethodModelDTO(model));
//            map.putIfAbsent(key, model); // add to cache

        });

        return ret;

    }

    public void close() {
        registry.destroy();
    }

    public boolean isAvailable() {
        return registry.isAvailable();
    }

    private static String generateMethodKey(Method method, String interfaceName) {
        return StringUtil.format("{}#{}", interfaceName, MD5Util.encrypt(method.toGenericString()));
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {

        CuratorHandler client = new CuratorHandler("zookeeper", "192.168.1.37", 2181);
        client.doConnect();
        Thread.sleep(1000);
        System.out.println("11111111111");

    }
}
