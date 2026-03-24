package ru.stepup.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class ErrorPage {
    private final SelenideElement errorText = $x("//div[contains(text(), 'Заказ с указанными параметрами не найден')]");
    private final SelenideElement checkbox = $x("//input[@id='searchOrderAgreeChb']/following-sibling::span");
    private final SelenideElement searchButton = $x("//button[contains(text(), 'Найти заказ')]");

    public ErrorPage() {
    }

    @Step("Переключение на новую вкладку")
    public void switchToNewTab() {
        Selenide.switchTo().window(1);
    }

    @Step("Получение текста ошибки")
    public String getErrorText() {
        return errorText.shouldBe(Condition.visible).getText();
    }

    @Step("Нажатие на чекбокс")
    public void clickCheckbox() {
        checkbox.click();
    }

    @Step("Нажатие кнопки поиска")
    public void clickSearchButton() {
        searchButton.click();
    }
}