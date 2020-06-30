package cetc10.module.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
public class LogEntity {
    private String logTime;//时间
    private String sysName;//系统名称
    private String softwareId;//软件标识
    private String localIp;//本机ip
    private String logLevel;//日志级别
    private String packageClassName;//包名
    private String methodName;//函数名类型
    private int lineNum;//行数
    private String msg;//日志信息

    public String formatLog() {
        JSONObject log = JSON.parseObject(JSON.toJSONString(this), JSONObject.class);
        Field[] fields = LogEntity.class.getDeclaredFields();
        return Arrays.stream(fields).map(e -> {
            if (e.getName().equals("msg")) {
                return log.getString(e.getName());
            } else {
                return  "【" + log.getString(e.getName()) + "】";
            }
        }).collect(Collectors.joining(" "));
    }
}
