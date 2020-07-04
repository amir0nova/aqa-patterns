package ru.netology;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CardDeliveryTest {
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker(new Locale("ru"));
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should input the form for delivery a card (\"Happy Path\")")
    void shouldInputFormCorrectly() {
        String city = DataGenerator.setCityRandomArray();
        DateDeliveryKit dateDeliveryKit = DataGenerator.setFakerDateDelivery();
        String passportName = DataGenerator.setFakerName();
        String phone = DataGenerator.setFakerPhone();

        SelenideElement blockDate = $("[data-test-id='date']");

        // 1. Назначение встречи
        $("[data-test-id='city'] .input__control").setValue(city);
        blockDate.$(".input__control").waitUntil(exist, 15000).sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);
        blockDate.$(".input__control").setValue(dateDeliveryKit.getInputDateDelivery1());
        $("[data-test-id='name'] .input__control").setValue(passportName);
        $("[data-test-id='phone'] .input__control").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).waitUntil(visible, 15000).click();
        $("[data-test-id='success-notification']").find(withText("Успешно!")).waitUntil(visible, 15000);

        // 2. Перепланирование встречи (повторный вход пользователя)
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue(city);
        blockDate.$(".input__control").waitUntil(exist, 15000).sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);
        blockDate.$(".input__control").setValue(dateDeliveryKit.getInputDateDelivery2());
        $("[data-test-id='name'] .input__control").setValue(passportName);
        $("[data-test-id='phone'] .input__control").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).waitUntil(visible, 15000).click();
        // 3. Подтверждение
        $$("button").find(exactText("Перепланировать")).waitUntil(visible, 15000).click();
        $("[data-test-id='success-notification']").find(withText("Успешно!")).waitUntil(visible, 15000);
    }

    // * Тест и метод для ручной отладки Happy Path.
    // ** Перед каждым запуском теста необходимо изменять значения в поле(ях) ГОРОД и/или ИМЯ и/или ТЕЛЕФОН.

    /*@Test
    @DisplayName("Should input the form for delivery a card as by hand")
    void shouldInputFormCorrectlyAsByHand() {
        String city = "Воронеж";
        int addDays1 = 12;
        int addDays2 = 4;
        String pattern = "dd.MM.yyyy";
        String passportName = "Филиппов Петр";
        String phone = "+79205632583";

        SelenideElement blockDate = $("[data-test-id='date']");
        // 1. Назначение встречи
        $("[data-test-id='city'] .input__control").setValue(city);
        blockDate.$(".input__control").waitUntil(exist, 15000).sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);
        blockDate.$(".input__control").setValue(setDateDelivery(addDays1, pattern));
        $("[data-test-id='name'] .input__control").setValue(passportName);
        $("[data-test-id='phone'] .input__control").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).waitUntil(visible, 15000).click();
        $("[data-test-id='success-notification']").find(withText("Успешно!")).waitUntil(visible, 15000);
        // 2. Перепланирование встречи
        open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue(city);
        blockDate.$(".input__control").waitUntil(exist, 15000).sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);
        blockDate.$(".input__control").setValue(setDateDelivery(addDays2, pattern));
        $("[data-test-id='name'] .input__control").setValue(passportName);
        $("[data-test-id='phone'] .input__control").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).waitUntil(visible, 15000).click();
        // 3. Подтверждение
        $$("button").find(exactText("Перепланировать")).waitUntil(visible, 15000).click();
        $("[data-test-id='success-notification']").find(withText("Успешно!")).waitUntil(visible, 15000);
    }

    private String setDateDelivery(int addDays, String pattern) {
        LocalDate now = LocalDate.now();
        LocalDate dateDelivery = now.plusDays(addDays);
        DateTimeFormatter datePointsFormatter = DateTimeFormatter.ofPattern(pattern);
        String inputDateDelivery = datePointsFormatter.format(dateDelivery);
        return inputDateDelivery;
    }*/
}
