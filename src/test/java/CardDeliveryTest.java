import com.codeborne.selenide.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDeliveryTest {
    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void orderingABankCard() {
        Selenide.open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Воронеж");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(generateDate(3));
        $("[name='name']").setValue("Новикова Оксана");
        $("[name='phone']").setValue("+79150000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(text("Забронировать")).click();
        $(Selectors.withText("Успешно!"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(Selectors.withText("Встреча успешно забронирована на " + generateDate(3)));
    }

    @Test
    void choosingACityBasedOnTheFirstTwoLetters() {
        Selenide.open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Во");
        $$("div.popup__content div").find(exactText("Воронеж")).click();
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(generateDate(3));
        $("[name='name']").setValue("Новикова Оксана");
        $("[name='phone']").setValue("+79150000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(text("Забронировать")).click();
        $(Selectors.withText("Успешно!"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(Selectors.withText("Встреча успешно забронирована на " + generateDate(3)));
    }

    @Test
    void choosingADateForTheWeekAhead() {
        Selenide.open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Ли");
        $$("div.popup__content div").find(exactText("Липецк")).click();
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(generateDate(7));
        $("[name='name']").setValue("Новикова Оксана");
        $("[name='phone']").setValue("+79150000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(text("Забронировать")).click();
        $(Selectors.withText("Успешно!"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(Selectors.withText("Встреча успешно забронирована на " + generateDate(7)));
    }
}