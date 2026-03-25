package ru.stepup.selenide;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.testit.annotations.Step;

import static com.codeborne.selenide.Selenide.$x;


public class BookingManage {

    private final SelenideElement buttonBookingManage = $x("//button[contains(., 'Управление бронированием')]");
    private final SelenideElement ticketOrOrderNumber = $x("//input[@placeholder='Номер бронирования или билета']");
    private final SelenideElement clientLastName = $x("//input[@placeholder='Фамилия клиента']");
    private final SelenideElement buttonSearch = $x("//button/span[contains(text(), 'Поиск')]");

    @Step
    public boolean isTicketOrOrderNumberVisible() {
        return ticketOrOrderNumber.shouldBe(Condition.visible).isDisplayed();
    }

    @Step
    public boolean isClientSurnameVisible() {
        return clientLastName.is(Condition.visible);
    }

    @Step
    public boolean isButtonSearchVisible() {
        return buttonSearch.is(Condition.visible);
    }

    @Step
    public void scrollToManageBlock() {
        buttonBookingManage.scrollTo();
    }

    @Step
    public void clickButtonSearch() {
        buttonSearch.click();
    }

    @Step
    public void clickButtonBookingManage() {
        buttonBookingManage.shouldBe(Condition.visible).click(ClickOptions.usingJavaScript());
    }

    @Step
    public void fillSearchForm(String orderNum, String clientSurname) {
        ticketOrOrderNumber.shouldBe(Condition.visible).setValue(orderNum);
        clientLastName.shouldBe(Condition.visible).setValue(clientSurname);
    }
}