package com.alex.restapidemo.model;

import javax.persistence.*;



import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


@Entity
@Table(name = "useragent")
public class UserAgent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_agent_string")
    private String userAgentString;

    @Column(name = "timestamp", nullable = false)
    @UpdateTimestamp
    private Timestamp timestamp;

    public UserAgent() {
    }

    public UserAgent(String userAgentString) {
        this.userAgentString = userAgentString;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }


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
}
