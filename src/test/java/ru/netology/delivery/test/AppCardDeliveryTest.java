package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import lombok.val;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.TestDataGenerator;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {
    @Test
    public void shouldCreateOrder() {

        val dataGenerator = new TestDataGenerator();
        val userInfo = dataGenerator.userProfileDataDto();

        $("[data-test-id='city'] input").setValue(userInfo.getCity());

        String dateForInput = fillDateField(5);

        $("[data-test-id='name'] input").setValue(userInfo.getName());
        $("[data-test-id='phone'] input").setValue(userInfo.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        clickToButton("Запланировать");

        checkDateNotification(dateForInput);
        dateForInput = fillDateField(6);
        clickToButton("Запланировать");

        $("[data-test-id='replan-notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldHave(Condition.text("Необходимо подтверждение\nУ вас уже запланирована встреча на другую дату. Перепланировать?\n\n\nПерепланировать"));

        clickToButton("Перепланировать");
        checkDateNotification(dateForInput);
    }

    private void clickToButton(String buttonText) {
        $$("button[type='button']")
                .findBy(Condition.text(buttonText))
                .click();
    }

    private void checkDateNotification(String dateText) {
        $("[data-test-id='success-notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldHave(Condition.text("Успешно!\nВстреча успешно запланирована на " + dateText));
    }

    private String getDateForInput(int days) {
        Date dateNow = new Date();
        Date futureDate = DateUtils.addDays(dateNow, days);
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        String dateForInput = formatForDateNow.format(futureDate);
        return dateForInput;
    }

    private String fillDateField(int days) {
        String dateForInput = getDateForInput(days);
        SelenideElement dateField = $("[data-test-id='date'] input");
        dateField.sendKeys(Keys.CONTROL + "a");
        dateField.sendKeys(Keys.DELETE);
        dateField.setValue(dateForInput);
        return dateForInput;
    }

    @BeforeEach
    public void openBrowser() {
        open("http://localhost:9999");
    }
}
