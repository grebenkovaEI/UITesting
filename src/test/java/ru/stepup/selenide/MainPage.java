package ru.stepup.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;


public class MainPage {

    private final SelenideElement logo = $x("//header/div/a/img");
    private final SelenideElement info = $x("//div/a[@href='/information']");

    public MainPage() {
    }

    public String getMainTitle() {
        return Selenide.title();
    }

    public boolean logoVisibility() {
        return logo.is(Condition.visible);
    }

    public void hoverInfoMenu() {
        info.shouldBe(Condition.visible).hover();
    }
}
