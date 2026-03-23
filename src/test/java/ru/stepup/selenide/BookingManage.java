package ru.stepup.selenide;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

public class BookingManage {

    private final SelenideElement buttonBookingManage = $x("//button[contains(., 'Управление бронированием')]");
    private final SelenideElement ticketOrOrderNumber = $x("//input[@placeholder='Номер бронирования или билета']");
    private final SelenideElement clientLastName = $x("//input[@placeholder='Фамилия клиента']");
    private final SelenideElement buttonSearch = $x("//button/span[contains(text(), 'Поиск')]");

    public boolean isTicketOrOrderNumberVisible() {
        return ticketOrOrderNumber.shouldBe(Condition.visible).isDisplayed();
    }

    public boolean isClientSurnameVisible() {
        return clientLastName.is(Condition.visible);
    }

    public boolean isButtonSearchVisible() {
        return buttonSearch.is(Condition.visible);
    }

    public void scrollToManageBlock() {
        buttonBookingManage.scrollTo();
    }

    public void clickButtonSearch() {
        buttonSearch.click();
    }

    public void clickButtonBookingManage() {
        buttonBookingManage.shouldBe(Condition.visible).click(ClickOptions.usingJavaScript());
    }

    public void fillSearchForm(String orderNum, String clientSurname) {
        ticketOrOrderNumber.shouldBe(Condition.visible).setValue(orderNum);
        clientLastName.shouldBe(Condition.visible).setValue(clientSurname);
    }
}