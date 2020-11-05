package com.lwp.website.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * ip工具类
 */
public class IPKit {
    /**
     * 通过request请求获取ip地址
     * @param request
     * @return
     */
    public static String getIpAddrByRequest(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0|| "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(ip == null || ip.length() ==0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getRealIp() throws SocketException{
        String localip = null;//本地IP，如果没有配置外网ip 则返回它
        String netip = null;//外网ip

        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;//是否找到外网IP
        while(netInterfaces.hasMoreElements() && !finded){
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()){
                ip = address.nextElement();
                if(!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")){
                    //外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                }else if(ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")){
                    //内网IP
                    localip = ip.getHostAddress();
                }
            }
        }
        if(netip != null && !"".equals(netip)){
            return netip;
        }else {
            return localip;
        }
    }

}
