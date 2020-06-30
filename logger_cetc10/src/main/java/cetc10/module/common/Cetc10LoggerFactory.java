package cetc10.module.common;

import java.util.HashMap;
import java.util.Map;

public class Cetc10LoggerFactory {

    private static Map<Class, Cetc10Logger> logCache = new HashMap<>();

    private Cetc10LoggerFactory(){}

    public static Cetc10Logger getCetc10Logger(Class clazz) {
        if (logCache.containsKey(clazz)) {
            return logCache.get(clazz);
        }
        synchronized (Cetc10LoggerFactory.class) {
            if (!logCache.containsKey(clazz)) {
                Cetc10Logger logger = new Cetc10Logger(clazz);
                logCache.put(clazz, logger);
                return logger;
            }
            return logCache.get(clazz);
        }
    }
}
