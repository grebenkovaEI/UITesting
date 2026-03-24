package ru.stepup.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class TicketSearch {
    private final SelenideElement placeFrom = $x("//input[@placeholder='Откуда']");
    private final SelenideElement placeTo = $x("//input[@placeholder='Куда']");
    private final SelenideElement dateFrom = $x("//input[@placeholder='Туда']");
    private final SelenideElement dateTo = $x("//input[@placeholder='Обратно']");
    private final SelenideElement searchButton = $x("//button/span[contains(text(), 'Поиск')]");
    private final SelenideElement placeFromSuggest = $x("//div[contains(@class, 'dp-sgi8y9-root-suggestionName') and contains(text(), 'Москва')]");
    private final SelenideElement placeToSuggest = $x("//div[contains(@class, 'dp-sgi8y9-root-suggestionName') and contains(text(), 'Санкт-Петербург')]");
    private final SelenideElement failedDataField = $x("//div[@class='dp-1dr6zbu-root' and @data-failed='true']");

    public TicketSearch() {
    }

    @Step("Проверка видимости поля Откуда")
    public boolean isPlaceFromVisible() {
        return placeFrom.is(Condition.visible);
    }

    @Step("Проверка видимости поля Куда")
    public boolean isPlaceToVisible() {
        return placeTo.is(Condition.visible);
    }

    @Step("Проверка видимости поля Дата вылета Туда")
    public boolean isDateFromVisible() {
        return dateFrom.is(Condition.visible);
    }

    @Step("Проверка видимости поля Дата вылета Обратно")
    public boolean isDateToVisible() {
        return dateTo.is(Condition.visible);
    }

    @Step("Скролл к блоку поиска билета")
    public void scrollToSearchBlock() {
        placeFrom.scrollTo();
    }

    @Step("Заполнение формы поиска билета: откуда - {from}, куда - {to}")
    public void fillSearchForm(String from, String to) {
        placeFrom.shouldBe(Condition.visible).setValue(from);
        placeFromSuggest.shouldBe(Condition.visible).click();
        placeTo.shouldBe(Condition.visible).setValue(to);
        placeToSuggest.shouldBe(Condition.visible).click();
    }

    @Step("Нажатие кнопки поиска")
    public void clickSearchButton() {
        searchButton.click();
    }

    @Step("Получение значения обводки поля «Туда»")
    public String getFailDataBorderColor() {
        failedDataField.shouldHave(Condition.cssValue("border-bottom-color", "rgba(213, 0, 98, 1)"));
        return failedDataField.getCssValue("border-bottom-color");
    }
}