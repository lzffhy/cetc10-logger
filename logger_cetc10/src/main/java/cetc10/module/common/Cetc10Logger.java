package cetc10.module.common;

import cetc10.module.common.utils.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Cetc10Logger {

    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

    private Class clazz;

    private org.apache.logging.log4j.Logger loggerj;

    private final Level OP = Level.forName("OP", 50);

    public static LoggerConfigProperties loggerConfigProperties;

    static {
        if (loggerConfigProperties == null) {
            loggerConfigProperties = new LoggerConfigProperties();
        }
    }

    public Cetc10Logger() {}

    public void init(String sysName, String softwareId) {
        loggerConfigProperties.setSysName(sysName);
        loggerConfigProperties.setSoftwareId(softwareId);
    }

    public Cetc10Logger(Class clazz) {
        this.clazz = clazz;
        loggerj = LogManager.getLogger(clazz.getName());
    }

    public void sysDebug(String moduleName, String msg) {
        loggerj.log(Level.DEBUG, getLogMsg(LogLevel.DEBUG, msg, moduleName));
    }

    public void sysInfo(String moduleName, String msg) {
        loggerj.log(Level.INFO, getLogMsg(LogLevel.INFO, msg, moduleName));
    }

    public void sysWarn(String moduleName, String msg) {
        loggerj.log(Level.WARN, getLogMsg(LogLevel.WARN, msg, moduleName));
    }

    public void sysError(String moduleName, String msg) {
        loggerj.log(Level.ERROR, getLogMsg(LogLevel.ERROR, msg, moduleName));
    }

    public void sysFatal(String moduleName, String msg) {
        loggerj.log(Level.FATAL, getLogMsg(LogLevel.FATAL, msg, moduleName));
    }

    public void sysDebug( String msg) {
        loggerj.log(Level.DEBUG, getLogMsg(LogLevel.DEBUG, msg, null));
    }

    public void sysInfo( String msg) {
        loggerj.log(Level.INFO, getLogMsg(LogLevel.INFO, msg, null));
    }

    public void sysWarn(String msg) {
        loggerj.log(Level.WARN, getLogMsg(LogLevel.WARN, msg, null));
    }

    public void sysError(String msg) {
        loggerj.log(Level.ERROR, getLogMsg(LogLevel.ERROR, msg, null));
    }

    public void sysFatal(String msg) {
        loggerj.log(Level.FATAL, getLogMsg(LogLevel.FATAL, msg, null));
    }

    public void sysDebug(String msg, Exception e) {
        this.sysDebug(msg);
        e.printStackTrace();
    }

    public void sysInfo(String msg, Exception e) {
        this.sysInfo(msg);
        e.printStackTrace();
    }

    public void sysWarn(String msg, Exception e) {
        this.sysWarn(msg);
        e.printStackTrace();
    }

    public void sysError(String msg, Exception e) {
        this.sysError(msg);
        e.printStackTrace();
    }

    public void sysFatal(String msg, Exception e) {
       this.sysFatal(msg);
       e.printStackTrace();
    }

    public void sysDebug(String moduleName, String msg, Exception e) {
        this.sysDebug(moduleName, msg);
        e.printStackTrace();
    }

    public void sysInfo(String moduleName, String msg, Exception e) {
        this.sysInfo(moduleName, msg);
        e.printStackTrace();
    }

    public void sysWarn(String moduleName, String msg, Exception e) {
        this.sysWarn(moduleName, msg);
        e.printStackTrace();
    }

    public void sysError(String moduleName, String msg, Exception e) {
        this.sysError(moduleName, msg);
        e.printStackTrace();
    }

    public void sysFatal(String moduleName, String msg, Exception e) {
        this.sysFatal(moduleName, msg);
        e.printStackTrace();
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

    public void fatal(String msg) {
        loggerj.log(Level.FATAL, msg);
    }

    public void debug(String msg, Exception e) {
        this.debug(msg);
        e.printStackTrace();
    }

    public void info(String msg, Exception e) {
        this.info(msg);
        e.printStackTrace();
    }

    public void warn(String msg, Exception e) {
        this.warn(msg);
        e.printStackTrace();
    }

    public void error(String msg, Exception e) {
        this.error(msg);
        e.printStackTrace();
    }

    public void fatal(String msg, Exception e) {
        this.fatal(msg);
        e.printStackTrace();
    }

    public void op(String userId, String opType,  String opData) {
        loggerj.log(OP, getOpLogMsg(null, userId, null, opType, opData));
    }

    public void op(String userIp, String userId, String userName, String opType, String opData) {
        loggerj.log(OP, getOpLogMsg(userIp, userId, userName, opType,  opData));
    }

    private String getOpLogMsg(String userIp, String userId, String userName, String opType, String opData) {
        return LogOpEntity.builder()
                .logTime(FORMAT.format(new Date()))
                .sysName(loggerConfigProperties.getSysName())
                .softwareId(loggerConfigProperties.getSoftwareId())
                //.localIp(CommonUtil.getLocalHostIp())
                .clientIp(userIp)
                //.logLevel(LogLevel.OP)
                .userId(userId)
                .userName(userName)
                .opType(opType)
                .opData(opData)
                .build().formatLog();
    }

    private String getLogMsg(String level, String msg, String moduleName) {
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
                .moduleName(StringUtils.isBlank(moduleName) ? "模块名" : moduleName)
                .msg(msg)
                .build().formatLog();
    }
}
