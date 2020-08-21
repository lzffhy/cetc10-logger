package cetc10.module.common;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述：配置信息 实体
 */
@ConfigurationProperties(prefix = "cetc10.logger")
public class LoggerConfigProperties {

    private String sysName;

    private String softwareId;

    public LoggerConfigProperties() {}

    public LoggerConfigProperties(boolean isDebugOn, String sysName, String softwareId) {
        this.sysName = sysName;
        this.softwareId = softwareId;
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
