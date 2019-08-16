package com.example.demo.util;

import com.relops.snowflake.Snowflake;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Random;
import java.util.UUID;

/**
 * 唯一ID生成工具
 *
 * @author donaldhan
 */
public class IdUtils {
    private final static Snowflake snowflake = new Snowflake(getNode());

    /**
     * 获取ID
     */
    public static Long getId() {
        return snowflake.next();
    }

    /**
     * 获取uuid
     */
    public  static String getUUId(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取Token
     */
    public  static String getToken(){
        return UUID.randomUUID().toString();
    }

    /**
     * 获取节点
     */
    private static int getNode() {
        //获取本地主机
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return new Random().nextInt(1024) + 1;
        }

        //获取ip地址
        String ip = address.getHostAddress();
        if (StringUtils.isEmpty(ip) || "127.0.0.1".equals(ip) || "0.0.0.0".equals(ip)) {
            try {
                ip = getIP();
            } catch (SocketException e) {
                e.printStackTrace();
                try {
                    return getNodeByMacAddress();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //随机数得到节点
                return new Random().nextInt(1024) + 1;
            }
        }

        //得到ip地址的每一位
        String[] bs = ip.split("\\.");

        //ip地址第3位
        int b3 = Integer.parseInt(bs[2]);
        //ip地址第4位
        int b4 = Integer.parseInt(bs[3]);
        //计算节点号
        int node = b3 % 4 * 256 + b4 + 1;

        return node;
    }

    /**
     * Unix下获取本地IP
     */
    private static String getIP() throws SocketException {
        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress ip = (InetAddress) addresses.nextElement();
                if (ip != null && ip instanceof Inet4Address) {
                    if (!ip.isLoopbackAddress() && ip.isSiteLocalAddress()) {
                        return ip.getHostAddress();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 通过Mac地址计算节点
     */
    private static int getNodeByMacAddress() throws UnknownHostException, SocketException {
        InetAddress ip = InetAddress.getLocalHost();
        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
        byte[] mac = network.getHardwareAddress();

        if (StringUtils.isEmpty(mac)) {
            mac = NetworkInterface.getByName("eth0").getHardwareAddress();
        }

        int sum = 0;

        for (int i = 0; i < mac.length; i++) {
            sum += mac[i] & 0xff;
        }

        int node = sum % 1024 + 1;

        return node;
    }
}