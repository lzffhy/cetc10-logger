package cetc10.module.aop;

import cetc10.module.annotation.SysLogDebug;
import cetc10.module.common.Cetc10Logger;
import cetc10.module.common.LogEntity;
import cetc10.module.common.LogLevel;
import cetc10.module.common.utils.CommonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class SysLogDebugAspect {

    protected Logger logger = LoggerFactory.getLogger(SysLogDebugAspect.class);

    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private LogEntity logEntity = null;


    @Pointcut("@annotation(cetc10.module.annotation.SysLogDebug)")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        if (Cetc10Logger.loggerConfigProperties.isDebugOn()) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String logTime = FORMAT.format(new Date());
            String sysName = Cetc10Logger.loggerConfigProperties.getSysName();
            String softwareId = Cetc10Logger.loggerConfigProperties.getSoftwareId();
            String localIp = CommonUtil.getLocalHostIp();
            String logLevel = LogLevel.DEBUG;
            String packageClassName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String methodName =  proceedingJoinPoint.getSignature().getName();
            int lineNum = CommonUtil.getRuntimeInfo(packageClassName, methodName).getIntValue("lineNum");
            String msg = getAspectLogDescription(proceedingJoinPoint);
            logEntity = LogEntity.builder()
                    .logTime(logTime)
                    .sysName(sysName)
                    .softwareId(softwareId)
                    .localIp(localIp)
                    .logLevel(logLevel)
                    .packageClassName(packageClassName)
                    .methodName(methodName)
                    .lineNum(lineNum)
                    .msg(msg)
                    .build();
        }
        return result;
    }

    @After("webLog()")
    public void doAfter() throws Throwable {
        if (Cetc10Logger.loggerConfigProperties.isDebugOn()) {
            String msg = this.logEntity.formatLog();
            //logger.info(msg);
            logger.debug(msg);
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
