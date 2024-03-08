package com.freightfox.meetingassistant.entity;

public class UserDto {
    Long userId;
    String name;
    String address;

    public UserDto() {
    }

    public UserDto(Long userId, String name, String address) {
        this.userId = userId;
        this.name = name;
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
