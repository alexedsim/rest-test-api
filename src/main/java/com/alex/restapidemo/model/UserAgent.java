package com.alex.restapidemo.model;
import javax.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Entity
@Table(name = "useragent")
public class UserAgent {
    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    */
    @Id
    @Column(name = "user_agent_hash")
    private String userAgentHash;
    @Column(name = "user_agent_string")
    private String userAgentString;

    @Column(name = "timestamp", nullable = false)
    @UpdateTimestamp
    private Timestamp timestamp;

    @Column(name = "browser")
    private String browser;

    @Column(name = "os")
    private String os;

    @Column(name = "device_type")
    private String deviceType;


    public UserAgent() {
    }

    public UserAgent( String userAgentHash, String userAgentString) {
        this.userAgentHash = userAgentHash;
        this.userAgentString = userAgentString;

    }

    // Getters and setters
    /*
    public Long getId() {
        return id;
    }

     */

    public String getUserAgentString() {
        return userAgentString;
    }

    public void setUserAgentString(String userAgentString) {
        this.userAgentString = userAgentString;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setHash(String userAgentHash) {
        this.userAgentHash = userAgentHash;
    }

    public String getHash() {
        return userAgentHash;
    }
}