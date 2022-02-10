package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.apache.commons.lang3.time.DateUtils;
import ru.netology.delivery.domain.UserProfileDataDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@UtilityClass
public class TestDataGenerator {
    private Faker faker = new Faker(new Locale("ru"));

    public static UserProfileDataDto userProfileDataDto() {
        val userInfo = new UserProfileDataDto(
                faker.address().cityName(),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber()
        );

        return userInfo;
    }

    public static String getDateForInput(int days) {
        Date dateNow = new Date();
        Date futureDate = DateUtils.addDays(dateNow, days);
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        String dateForInput = formatForDateNow.format(futureDate);
        return dateForInput;
    }
}
