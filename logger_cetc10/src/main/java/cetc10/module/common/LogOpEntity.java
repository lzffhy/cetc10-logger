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
public class LogOpEntity {
    private String logTime;//时间
    private String sysName;//系统名称
    private String softwareId;//软件标识
    private String localIp;//本机ip
    private String logLevel;
    private String userId;
    private String opType;
    private String opData;

    public String formatLog() {
        JSONObject log = JSON.parseObject(JSON.toJSONString(this), JSONObject.class);
        Field[] fields = LogOpEntity.class.getDeclaredFields();
        return Arrays.stream(fields).map(e -> {
            if (e.getName().equals("opData")) {
                return log.getString(e.getName());
            } else {
                return  "【" + log.getString(e.getName()) + "】";
            }
        }).collect(Collectors.joining(" "));
    }
}
