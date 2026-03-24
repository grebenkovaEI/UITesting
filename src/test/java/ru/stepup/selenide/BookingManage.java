package ru.stepup.selenide;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class BookingManage {

    private final SelenideElement buttonBookingManage = $x("//button[contains(., 'Управление бронированием')]");
    private final SelenideElement ticketOrOrderNumber = $x("//input[@placeholder='Номер бронирования или билета']");
    private final SelenideElement clientLastName = $x("//input[@placeholder='Фамилия клиента']");
    private final SelenideElement buttonSearch = $x("//button/span[contains(text(), 'Поиск')]");

    @Step("Проверка видимости поля «Номер заказа или билета»")
    public boolean isTicketOrOrderNumberVisible() {
        return ticketOrOrderNumber.shouldBe(Condition.visible).isDisplayed();
    }

    @Step("Проверка видимости поля «Фамилия клиента»")
    public boolean isClientSurnameVisible() {
        return clientLastName.is(Condition.visible);
    }

    @Step("Проверка видимости кнопки поиска")
    public boolean isButtonSearchVisible() {
        return buttonSearch.is(Condition.visible);
    }

    @Step("Скролл страницы до блока управления бронированием")
    public void scrollToManageBlock() {
        buttonBookingManage.scrollTo();
    }

    @Step("Нажатие кнопки поиска")
    public void clickButtonSearch() {
        buttonSearch.click();
    }

    @Step("Нажатие на пункт «Управление бронированием»")
    public void clickButtonBookingManage() {
        buttonBookingManage.shouldBe(Condition.visible).click(ClickOptions.usingJavaScript());
    }

    @Step("Заполнение формы: номер бронирования - {orderNum}, фамилия клиента - {clientSurname}")
    public void fillSearchForm(String orderNum, String clientSurname) {
        ticketOrOrderNumber.shouldBe(Condition.visible).setValue(orderNum);
        clientLastName.shouldBe(Condition.visible).setValue(clientSurname);
    }
}