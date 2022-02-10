package ru.netology.delivery.domain;

import lombok.Value;

@Value
public class UserProfileDataDto {
    public UserProfileDataDto(String city, String name, String phone) {
        this.city = city;
        this.name = name;
        this.phone = phone;
    }

    String city;
    String name;
    String phone;
}
