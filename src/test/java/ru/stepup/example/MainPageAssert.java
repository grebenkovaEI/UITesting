package ru.stepup.example;

import com.codeborne.selenide.Condition;
import org.assertj.core.api.AbstractAssert;
import ru.stepup.pajeObj.MainPage;

public class MainPageAssert extends AbstractAssert<MainPageAssert, MainPage> {

    public MainPageAssert(MainPage mainPage, Class<?> selfType) {
        super(mainPage, selfType);
    }

    public MainPage page() {
        return actual;
    }

//    public MainPageAssert checkNameField(String name) {
//        actual.fieldName.shouldBe(Condition.visible)
//                .shouldHave(Condition.value(name));
//        return this;
//    }

    //сюда пишем еще разные чеки
}
