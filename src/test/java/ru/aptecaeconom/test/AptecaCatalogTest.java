package ru.aptecaeconom.test;

import com.codeborne.selenide.*;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.aptekaeconom.test.CatalogPage;
import ru.aptekaeconom.test.MainPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import ru.aptekaeconom.test.ConfirmRegionPopup;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class AptecaCatalogTest extends WebTest {

    public MainPage mainPage = new MainPage();
    public ConfirmRegionPopup confirmRegionPopup = new ConfirmRegionPopup();

    public CatalogPage catalogPage = new CatalogPage();


    @BeforeEach
    public void setSelenide() throws InterruptedException {
        open("https://aptekaeconom.com/");
        Selenide.webdriver().driver().getWebDriver().manage().addCookie(
                new Cookie("current_region", "103006"));
        refresh();
        Thread.sleep(5000);

        confirmRegionPopup.modal.shouldNotBe(Condition.visible);
    }

    @Test
    @DisplayName("Переход по подкатегориям в каталоге товаров")
    @Feature("Каталог товаров")
    @Story("Подкатегории")
    public void shouldOpenCatalogTab() throws InterruptedException, IndexOutOfBoundsException{
//    SelenideElement tab = mainPage.tabs.filter(text("Лекарства и БАДы")).get(3).shouldBe(visible);

                $("html > body > div:nth-of-type(3) > div:first-of-type > header > " +
                "div:nth-of-type(2) > div:nth-of-type(2) > div > div > div > div > nav > div > table > tbody > tr" +
                " > td:nth-of-type(4)").click();
       step("Переход на страницу Лекарства и БАДы", () ->{
           WebDriverRunner.url().equals("https://aptekaeconom.com/catalog/lekarstva-i-bady/");

       });

       step("Выбор подкатегории Антибактериальные препараты в категории Лекартсва и БАДы", () ->{
        $("#bx_1847241719_365").hover().click();
        });

        step("Проверить, что произошел переход на страницу товаров категории", () ->{
                    catalogPage.header.shouldHave(visible);
                });

        step("Проверить, что хлебные крошки отображаются корректно", () -> {
            WebDriverRunner.url().equals
                    ("https://aptekaeconom.com/catalog/lekarstva-i-bady/antibakterialnye-preparaty/");
            $("#bx_breadcrumb_1 > a > span").shouldBe(visible);
            $("#bx_breadcrumb_2 > a > span").shouldBe(visible);
            $("#navigation > span:nth-of-type(4) > span > span").shouldBe(visible);
        });






//    step("Кликнуть на появившуюся категорию", () -> {
//        ElementsCollection subtabs = mainPage.getSubtabs(tab);
//        subtabs.filter(text("Антибактериальные препараты")).get(0).click();
//    });






    }
}


