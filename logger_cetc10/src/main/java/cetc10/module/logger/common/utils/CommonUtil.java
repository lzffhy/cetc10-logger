package cetc10.module.logger.common.utils;

import cetc10.module.logger.common.LogLevel;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CommonUtil {

    public static JSONObject getRuntimeInfo()
    {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        JSONObject json = new JSONObject();
        StackTraceElement element = stack[stack.length - 1];
        json.put("className", element.getClassName());
        json.put("methodName", element.getMethodName());
        json.put("lineNum", element.getLineNumber());
        return json;
    }

    public static String getClientRealIp(HttpServletRequest request) {
        String realIp = null;
        realIp = request.getHeader("X-Forwarded-For");
        if (!checkRealIp(realIp)) {
            realIp = request.getHeader("Proxy-Client-Ip");
            if (!checkRealIp(realIp)) {
                realIp = request.getHeader("WL-Proxy-Client-Ip");
                if (!checkRealIp(realIp)) {
                    realIp = request.getRemoteAddr();
                    if (realIp.equals("127.0.0.1") || realIp.endsWith("0:0:0:0:0:0:1")) {
                        try {
                            realIp = InetAddress.getLocalHost().getHostAddress();
                        } catch (UnknownHostException e) {
                        }
                    }
                }
            }
        }
        if (realIp != null && realIp.length() > 15) {
            if (realIp.indexOf(",") > 0) {
                realIp = realIp.substring(0, realIp.indexOf(","));
            }
        }
        return realIp;
    }

    /**
     * 获取本机ip
     *
     * @return
     * @throws Exception
     */
    public static String getLocalHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        return null;
    }

    /**
     * getClientRealIp使用
     *
     * @param realIp
     * @return
     */
    private static boolean checkRealIp(String realIp) {
        return realIp == null || realIp.length() == 0 || realIp.equalsIgnoreCase("unKnown") ? false : true;
    }
}
