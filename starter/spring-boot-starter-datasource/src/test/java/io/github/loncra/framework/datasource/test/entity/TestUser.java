package io.github.loncra.framework.datasource.test.entity;

import java.time.Instant;

/**
 * 测试用户实体
 *
 * @author maurice.chen
 */
public class TestUser {

    private Long id;
    private String username;
    private String email;
    private Integer age;
    private Instant creationTime;
    private Instant updateTime;

    public TestUser() {
    }

    public TestUser(
            String username,
            String email,
            Integer age
    ) {
        this.username = username;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", creationTime=" + creationTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
