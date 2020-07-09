package cetc10.module.common;

import cetc10.module.common.utils.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Cetc10Logger {

    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private Class clazz;

    private Logger logger;

    private org.apache.logging.log4j.Logger loggerj;

    private final Level OP = Level.forName("OP", 150);

    public static LoggerConfigProperties loggerConfigProperties;

    static {
        if (loggerConfigProperties == null) {
            loggerConfigProperties = new LoggerConfigProperties();
        }
    }

    public Cetc10Logger() {}

    public void init(Boolean isDebugOn, String sysName, String softwareId) {
        loggerConfigProperties.setDebugOn(isDebugOn);
        loggerConfigProperties.setSysName(sysName);
        loggerConfigProperties.setSoftwareId(softwareId);
    }

    public Cetc10Logger(Class clazz) {
        this.clazz = clazz;
        logger = LoggerFactory.getLogger(clazz);
        loggerj = LogManager.getLogger(clazz.getName());
    }

    public void sysDebug(String msg) {
        loggerj.log(Level.DEBUG, getLogMsg(LogLevel.DEBUG, msg));
    }

    public void sysInfo(String msg) {
        loggerj.log(Level.INFO, getLogMsg(LogLevel.INFO, msg));
    }

    public void sysWarn(String msg) {
        loggerj.log(Level.WARN, getLogMsg(LogLevel.WARN, msg));
    }

    public void sysError(String msg) {
        loggerj.log(Level.ERROR, getLogMsg(LogLevel.ERROR, msg));
    }

    public void debug(String msg) {
        loggerj.log(Level.DEBUG, msg);
    }

    public void info(String msg) {
        loggerj.log(Level.INFO, msg);
    }

    public void warn(String msg) {
        loggerj.log(Level.WARN, msg);
    }

    public void error(String msg) {
        loggerj.log(Level.ERROR, msg);
    }

//    public void sysDebug(String msg) {
//        logger.debug(getLogMsg(LogLevel.DEBUG, msg));
//    }
//
//    public void sysInfo(String msg) {
//        logger.info(getLogMsg(LogLevel.INFO, msg));
//    }
//
//    public void sysWarn(String msg) {
//        logger.warn(getLogMsg(LogLevel.WARN, msg));
//    }
//
//    public void sysError(String msg) {
//        logger.error(getLogMsg(LogLevel.ERROR, msg));
//    }

    public void op(String userId, String opType, String opData) {
        loggerj.log(OP, getOpLogMsg(userId, opType, opData));
    }

    private String getOpLogMsg(String userId, String opType, String opData) {
        return LogOpEntity.builder()
                .logTime(FORMAT.format(new Date()))
                .sysName(loggerConfigProperties.getSysName())
                .softwareId(loggerConfigProperties.getSoftwareId())
                .localIp(CommonUtil.getLocalHostIp())
                .logLevel(LogLevel.OP)
                .userId(userId)
                .opType(opType)
                .opData(opData)
                .build().formatLog();
    }

    private String getLogMsg(String level, String msg) {
        JSONObject runtime = CommonUtil.getRuntimeInfo(this.clazz.getName(), null);
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
