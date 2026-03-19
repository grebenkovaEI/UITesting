package ru.stepup;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {   //потом в тесте прописать extends ru.stepup.BaseTest

    @BeforeAll
    public static void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1466x768";
        Configuration.timeout = 6000;
        Configuration.pageLoadTimeout = 30_000;

    }
}
