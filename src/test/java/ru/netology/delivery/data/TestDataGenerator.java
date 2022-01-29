package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.val;
import ru.netology.delivery.domain.UserProfileDataDto;

import java.util.Locale;

public class TestDataGenerator {
    private Faker faker;

    public TestDataGenerator()
    {
        faker = new Faker(new Locale("ru"));
    }

    public UserProfileDataDto userProfileDataDto()
    {
        val userInfo = new UserProfileDataDto();
        userInfo.setCity(faker.address().cityName());
        userInfo.setName(faker.name().fullName());
        userInfo.setPhone(faker.phoneNumber().phoneNumber());

        return userInfo;
    }
}
