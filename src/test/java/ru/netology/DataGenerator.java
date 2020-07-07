package ru.netology;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {}

    public static String setCityRandomArray() {
        String[] cites = new String[] {"Абакан", "Архангельск", "Барнаул", "Владикавказ", "Горно-Алтайск", "Екатеринбург",
                "Йошкар-Ола", "Казань", "Калининград", "Калуга", "Кемерово", "Кострома", "Краснодар", "Красноярск",
                "Курган", "Махачкала", "Москва", "Нальчик", "Петропавловск-Камчатский", "Ростов-на-Дону", "Салехард",
                "Санкт-Петербург", "Севастополь", "Симферополь", "Смоленск", "Ставрополь", "Сыктывкар", "Тула",
                "Тюмень", "Чебоксары", "Южно-Сахалинск", "Ярославль"};
        Random random = new Random();
        int index = random.nextInt(cites.length);
        String city = cites[index];
        return city;
    }

    public static DateDeliveryKit setDateDeliveryForFixedPattern() {
        DateTimeFormatter datePointsFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        int addDays1 = 14; //  Note: should be "addDays1 >= 3"
        int addDays2 = 7; //  Note: should be "addDays2 >= 3"

        LocalDate now = LocalDate.now();
        LocalDate dateDelivery1 = now.plusDays(addDays1);
        LocalDate dateDelivery2 = now.plusDays(addDays2);
        String inputDateDelivery1 = datePointsFormatter.format(dateDelivery1);
        String inputDateDelivery2 = datePointsFormatter.format(dateDelivery2);
        return new DateDeliveryKit(
                inputDateDelivery1,
                inputDateDelivery2
        );
    }

    public static String setFakerName() {
        Faker faker = Faker.instance(new Locale("ru"), new Random());
        String passportName = StringUtils.join(faker.name().firstName(), " ", faker.name().lastName());
        return passportName;
    }

    public static String setFakerPhone() {
        Faker faker = Faker.instance(new Locale("ru"), new Random());
        return faker.regexify("+7[0-9]{10}");
    }
}
