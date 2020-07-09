package cetc10.module.aop;

import cetc10.module.annotation.SysLogDebug;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class SysLogDebugAspect {

    //protected Logger logger = LoggerFactory.getLogger(SysLogDebugAspect.class);
    org.apache.logging.log4j.Logger loggerJ = LogManager.getLogger(SysLogDebugAspect.class.getName());


    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private LogEntity logEntity = null;


    @Pointcut("@annotation(cetc10.module.annotation.SysLogDebug)")
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
        if (Cetc10Logger.loggerConfigProperties.isDebugOn()) {
            String logTime = FORMAT.format(new Date());
            String sysName = Cetc10Logger.loggerConfigProperties.getSysName();
            String softwareId = Cetc10Logger.loggerConfigProperties.getSoftwareId();
            String localIp = CommonUtil.getLocalHostIp();
            String logLevel = LogLevel.DEBUG;
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
            loggerJ.log(Level.DEBUG, msg);
            //释放该对象
            this.logEntity = null;
        }
    }

    public String getAspectLogDescription(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SysLogDebug webLog = method.getAnnotation(SysLogDebug.class);
        return webLog.description();
    }
}
