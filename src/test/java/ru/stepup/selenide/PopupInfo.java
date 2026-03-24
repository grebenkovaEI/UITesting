package ru.stepup.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class PopupInfo {

    private final SelenideElement infoFlight = $x("//div/a[@href='/information#flight']");
    private final SelenideElement infoUseful = $x("//div/a[@href='/information#useful']");
    private final SelenideElement infoCompany = $x("//div/a[@href='/information#company']");

    public PopupInfo() {
    }

    @Step("Проверка видимости заголовка «Подготовка к полету»")
    public boolean isInfoFlightVisible() {
        return infoFlight.is(Condition.visible);
    }

    @Step("Проверка видимости заголовка «Полезная информация»")
    public boolean isInfoUsefulVisible() {
        return infoUseful.is(Condition.visible);
    }

    @Step("Проверка видимости заголовка «О компании»")
    public boolean isInfoCompanyVisible() {
        return infoCompany.is(Condition.visible);
    }
}