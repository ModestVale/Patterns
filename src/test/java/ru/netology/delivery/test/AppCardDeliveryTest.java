package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.TestDataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {
    @Test
    public void shouldCreateOrder() {

        val userInfo = TestDataGenerator.userProfileDataDto();

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

    private String fillDateField(int days) {
        String dateForInput = TestDataGenerator.getDateForInput(days);
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
