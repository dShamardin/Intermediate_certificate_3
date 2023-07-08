package ru.aptekaeconom.test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    public ElementsCollection tabs = $$("html > body > div:nth-of-type(3) > div:first-of-type > header > " +
            "div:nth-of-type(2) > div:nth-of-type(2) > div > div > div > div > nav > div > table > tbody > tr" +
            " > td:nth-of-type(4)");

    public ElementsCollection getSubtabs(SelenideElement tab) {
        return tab.$$("ul li");
    }

}