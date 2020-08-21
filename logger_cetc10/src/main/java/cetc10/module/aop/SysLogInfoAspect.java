package cetc10.module.aop;

import cetc10.module.annotation.SysLogInfo;
import cetc10.module.common.Cetc10Logger;
import cetc10.module.common.LogEntity;
import cetc10.module.common.LogLevel;
import cetc10.module.common.utils.CommonUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class SysLogInfoAspect {

    org.apache.logging.log4j.Logger loggerJ = LogManager.getLogger(SysLogInfoAspect.class.getName());


    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

    private LogEntity logEntity = null;


    @Pointcut("@annotation(cetc10.module.annotation.SysLogInfo)")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore() throws Throwable {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return proceedingJoinPoint.proceed();
    }

    @After("webLog()")
    public void doAfter(JoinPoint joinPoint) throws Throwable {
        String logTime = FORMAT.format(new Date());
        String sysName = Cetc10Logger.loggerConfigProperties.getSysName();
        String softwareId = Cetc10Logger.loggerConfigProperties.getSoftwareId();
        String localIp = CommonUtil.getLocalHostIp();
        String logLevel = LogLevel.INFO;
        String packageClassName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName =  joinPoint.getSignature().getName();
        int lineNum = CommonUtil.getRuntimeInfo(packageClassName, methodName).getIntValue("lineNum");
        String description = getAspectLogDescription(joinPoint);
        logEntity = LogEntity.builder()
                .logTime(logTime)
                .sysName(sysName)
                .softwareId(softwareId)
                .localIp(localIp)
                .logLevel(logLevel)
                .packageClassName(packageClassName)
                .methodName(methodName)
                .lineNum(lineNum)
                .msg(description)
                .build();
        String msg = logEntity.formatLog();
        loggerJ.log(Level.INFO, msg);
        //释放该对象
        this.logEntity = null;
    }

    public String getAspectLogDescription(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SysLogInfo webLog = method.getAnnotation(SysLogInfo.class);
        return webLog.description();
    }
}
