import com.codeborne.selenide.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.files.DownloadActions.click;

public class cardDeliveryTest {
    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @Test
    void orderingABankCard() {
        Selenide.open("http://localhost:9999");
        $$("input");
        $("[placeholder='Город']").setValue("Воронеж");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(generateDate(4));
        $("[name='name']").setValue("Новикова Оксана");
        $("[name='phone']").setValue("+79150000000");
        $("[data-test-id='agreement']").click();
        $$("button").find(text("Забронировать")).click();
        $(Selectors.withText("Успешно!"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(Selectors.withText("Встреча успешно забронирована на " + generateDate(4)));
    }
}