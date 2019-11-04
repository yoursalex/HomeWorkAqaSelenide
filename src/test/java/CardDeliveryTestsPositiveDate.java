import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.Keys;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTestsPositiveDate {

    private String stringDate;

    @BeforeEach
    void setUp() {
        LocalDate today = LocalDate.now();
        LocalDate dateToBeSet = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        stringDate = dateToBeSet.format(formatter);
    }

    @Test
    @DisplayName("Должен бронировать карту с доставкой при вводе валидных данных")
    void shouldSubmitRequest() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input.input__control").setValue("Москва");
        $("[data-test-id=date] input.input__control").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input.input__control").setValue(stringDate);
        $("[data-test-id=name] input.input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] input.input__control").setValue("+79991112233");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(withText("Успешно!")).waitUntil(Condition.visible, 15000);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/dataOptions.cvs", numLinesToSkip = 1)
    @DisplayName("Не должен бронировать доставку при невалидных данных")
    void shouldNotSubmitRequest(String city, String name, String phone) {
        open("http://localhost:9999/");
        $("[data-test-id=city] input.input__control").setValue(city);
        $("[data-test-id=date] input.input__control").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input.input__control").setValue(stringDate);
        $("[data-test-id=name] input.input__control").setValue(name);
        $("[data-test-id=phone] input.input__control").setValue(phone);
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $$("span").find(Condition.cssClass("input_invalid")).shouldHave(Condition.visible);
    }

}
