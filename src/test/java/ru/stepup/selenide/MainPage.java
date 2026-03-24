package ru.stepup.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;


public class MainPage {

    private final SelenideElement logo = $x("//header/div/a/img");
    private final SelenideElement info = $x("//div/a[@href='/information']");

    public MainPage() {
    }

    @Step("Получение заголовка страницы")
    public String getMainTitle() {
        return Selenide.title();
    }

    @Step("Проверка видимости логотипа")
    public boolean logoVisibility() {
        return logo.is(Condition.visible);
    }

    @Step("Наведение мышки на пункт «Информация»")
    public void hoverInfoMenu() {
        info.shouldBe(Condition.visible).hover();
    }
}
