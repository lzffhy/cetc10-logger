package cetc10.module.logger.common;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述：配置信息 实体
 */
@ConfigurationProperties(prefix = "cetc10.logger")
public class LoggerConfigProperties {

    private boolean isDebugOn;

    private String sysName;

    private String softwareId;

    public LoggerConfigProperties() {}

    public LoggerConfigProperties(boolean isDebugOn, String sysName, String softwareId) {
        this.isDebugOn = isDebugOn;
        this.sysName = sysName;
        this.softwareId = softwareId;
    }

    public boolean isDebugOn() {
        return isDebugOn;
    }

    public void setDebugOn(boolean debugOn) {
        isDebugOn = debugOn;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }
}
