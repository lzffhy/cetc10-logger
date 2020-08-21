package cetc10.module.common;

import cetc10.module.common.utils.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.slf4j.LoggerFactory;

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

    public void sysFatal(String msg) {
        loggerj.log(Level.FATAL, getLogMsg(LogLevel.FATAL, msg));
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
        loggerj.log(Level.DEBUG, getLogMsg(LogLevel.DEBUG, msg, moduleName));
        e.printStackTrace();
    }

    public void sysInfo(String moduleName, String msg, Exception e) {
        loggerj.log(Level.INFO, getLogMsg(LogLevel.INFO, msg, moduleName));
        e.printStackTrace();
    }

    public void sysWarn(String moduleName, String msg, Exception e) {
        loggerj.log(Level.WARN, getLogMsg(LogLevel.WARN, msg, moduleName));
        e.printStackTrace();
    }

    public void sysError(String moduleName, String msg, Exception e) {
        loggerj.log(Level.ERROR, getLogMsg(LogLevel.ERROR, msg, moduleName));
        e.printStackTrace();
    }

    public void sysFatal(String moduleName, String msg, Exception e) {
        loggerj.log(Level.FATAL, getLogMsg(LogLevel.FATAL, msg, moduleName));
        e.printStackTrace();
    }


    public void op(String userId, String opType,  String opData) {
        loggerj.log(OP, getOpLogMsg(null, userId, null, opType, opData));
    }

    public void op(String userId, String userName, String opType, String opData) {
        loggerj.log(OP, getOpLogMsg(null, userId, userName, opType,  opData));
    }

    public void op(String clientIp, String userId, String userName, String opType, String opData) {
        loggerj.log(OP, getOpLogMsg(clientIp, userId, userName, opType,  opData));
    }

    private String getOpLogMsg(String clientIp, String userId, String userName, String opType, String opData) {
        return LogOpEntity.builder()
                .logTime(FORMAT.format(new Date()))
                .sysName(loggerConfigProperties.getSysName())
                .softwareId(loggerConfigProperties.getSoftwareId())
                //.localIp(CommonUtil.getLocalHostIp())
                .clientIp(clientIp)
                //.logLevel(LogLevel.OP)
                .userId(userId)
                .userName(userName)
                .opType(opType)
                .opData(opData)
                .build().formatLog();
    }

    private String getLogMsg(String level, String msg, String...moduleName) {
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
                .moduleName(moduleName.length > 0 ? moduleName[0] : null)
                .msg(msg)
                .build().formatLog();
    }
}
