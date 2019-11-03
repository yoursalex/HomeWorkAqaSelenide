import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CardDeliveryTestsNegativeDate {

    @Test
    @DisplayName("Не должен бронировать карту если дата меньше 3 дней с текущей")
    void shouldNotSubmitWithWrongDate() {
        LocalDate today = LocalDate.now();
        LocalDate dateToBeSet = today.plusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String stringDate = dateToBeSet.format(formatter);
        open("http://localhost:9999/");
        $("[data-test-id=city] input.input__control").setValue("Москва");
        $("[data-test-id=date] input.input__control").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input.input__control").setValue(stringDate);
        $("[data-test-id=name] input.input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] input.input__control").setValue("+79991112233");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=date] .input").shouldHave(Condition.cssClass("input_invalid"));
    }

    @Test
    @DisplayName("Не должен бронировать карту если дата заказа не указана")
    void shouldNotSubmitWithEmptyDate() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input.input__control").setValue("Москва");
        $("[data-test-id=date] input.input__control").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=name] input.input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] input.input__control").setValue("+79991112233");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=date] .input").shouldHave(Condition.cssClass("input_invalid"));
    }

}
