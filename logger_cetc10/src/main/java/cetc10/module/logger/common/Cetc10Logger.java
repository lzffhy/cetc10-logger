package cetc10.module.logger.common;

import cetc10.module.logger.common.utils.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Cetc10Logger {

    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private Class clazz;

    private Logger logger;

    public static LoggerConfigProperties loggerConfigProperties;

    static {
        if (loggerConfigProperties == null) {
            loggerConfigProperties = new LoggerConfigProperties();
        }
    }

    public Cetc10Logger() {}

    public void init(Boolean isDebugOn, String sysName, String softwareId) throws Exception {
        loggerConfigProperties.setDebugOn(isDebugOn);
        loggerConfigProperties.setSysName(sysName);
        loggerConfigProperties.setSoftwareId(softwareId);
    }

    public Cetc10Logger(Class clazz) {
        this.clazz = clazz;
        logger = LoggerFactory.getLogger(clazz);
    }

    public LoggerConfigProperties getLoggerConfigProperties() {
        return loggerConfigProperties;
    }

    public void sysDebug(String msg) {
        logger.debug(getLogMsg(LogLevel.DEBUG, msg));
    }

    public void sysInfo(String msg) {
        logger.info(getLogMsg(LogLevel.INFO, msg));
    }

    public void sysWarn(String msg) {
        logger.warn(getLogMsg(LogLevel.WARN, msg));
    }

    public void sysError(String msg) {
        logger.error(getLogMsg(LogLevel.ERROR, msg));
    }

    public void setClazz(Class clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public void opDebug(String userId, String opType, String opData) {
        logger.debug(getOpLogMsg(userId, opType, opData));
    }

    public void opInfo(String userId, String opType, String opData) {
        logger.info(getOpLogMsg(userId, opType, opData));
    }

    public String getOpLogMsg(String userId, String opType, String opData) {
        return LogOpEntity.builder()
                .logTime(FORMAT.format(new Date()))
                .sysName(loggerConfigProperties.getSysName())
                .softwareId(loggerConfigProperties.getSoftwareId())
                .localIp(CommonUtil.getLocalHostIp())
                .userId(userId)
                .opType(opType)
                .opData(opData)
                .build().formatLog();
    }

    public String getLogMsg(String level, String msg) {
        JSONObject runtime = CommonUtil.getRuntimeInfo();
        return LogEntity.builder()
                .logTime(FORMAT.format(new Date()))
                .sysName(loggerConfigProperties.getSysName())
                .softwareId(loggerConfigProperties.getSoftwareId())
                .localIp(CommonUtil.getLocalHostIp())
                .packageClassName(runtime.getString("className"))
                .methodName(runtime.getString("methodName"))
                .lineNum(runtime.getIntValue("lineNum"))
                .logLevel(level)
                .msg(msg)
                .build().formatLog();
    }
}
