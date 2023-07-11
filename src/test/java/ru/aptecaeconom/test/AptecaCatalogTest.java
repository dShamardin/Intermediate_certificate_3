package ru.aptecaeconom.test;

import com.codeborne.selenide.*;
import com.codeborne.selenide.impl.SelenideElementDescriber;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.aptekaeconom.test.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class AptecaCatalogTest extends WebTest {

    public MainPage mainPage = new MainPage();
    public ConfirmRegionPopup confirmRegionPopup = new ConfirmRegionPopup();

    public CatalogPage catalogPage = new CatalogPage();

    public SearchField searchField = new SearchField();

    public Busket busket = new Busket();


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
    }
    @Test
    @DisplayName("Поиск товаров только по полному совпадению слова или словосочетания")
    @Feature("Поиск")
    public void ShouldCheckSearchField(){
        SelenideElement searchfield = searchField.search.setValue("Глицин форте").pressEnter();

        step("В поисковой выдаче отображается по 5 товаров на странице", () -> {
            WebDriverRunner.url().equals("https://aptekaeconom.com/catalog/?q=Глицин форте&s=Найти");
            $$(".price_matrix_wrapper").get(0).shouldBe(exist);
            $$(".price_matrix_wrapper").get(1).shouldBe(exist);
            $$(".price_matrix_wrapper").get(2).shouldBe(exist);
            $$(".price_matrix_wrapper").get(3).shouldBe(exist);
            $$(".price_matrix_wrapper").get(4).shouldBe(exist);
        });
    }

    @Test
    @DisplayName("Товар, который есть в наличии можно отложить, нажав на кнопку с иконкой сердечка ")
    @Feature("Корзина")
    public void ShouldCheckPostponedGoods(){
        $("html > body > div:nth-of-type(3) > div:first-of-type > header > " +
                "div:nth-of-type(2) > div:nth-of-type(2) > div > div > div > div > nav > div > table > tbody > tr" +
                " > td:nth-of-type(4)").click();
        step("Переход на страницу Лекарства и БАДы", () ->{
            WebDriverRunner.url().equals("https://aptekaeconom.com/catalog/lekarstva-i-bady/");

        });

        step("Выбор подкатегории Антибактериальные препараты в категории Лекартсва и БАДы", () ->{
            $("#bx_1847241719_365").hover().click();
        });

        step("Наведение курсора на первый товар из каталога предложенных товаров", () ->{
        SelenideElement Good =catalogPage.good.hover();
        });

        step("Кликнуть на первый товар", () ->{
        $("#right_block_ajax > div > div:nth-of-type(3) > div:first-of-type > div > " +
                "div:first-of-type > div").click();
        });

        step("Нажать на кнопку с иконкой сердечка и текстом `Отложить`", () ->{
            $("#bx_117848907_112619 > div:first-of-type > div:nth-of-type(2) > div:first-of-type > div")
                    .hover().click();
        });

        step("Перейти в корзину и проверить пометку `Товар отложен`", () ->{
            $("#header > div:nth-of-type(2) > div:first-of-type > div > div > div > div:nth-of-type(4) > " +
                    "div:nth-of-type(2) > a").click();
            WebDriverRunner.url().equals("https://aptekaeconom.com/basket/");
            SelenideElement busketpage = busket.Busket.shouldBe(visible);

        });

        step("Отложенный товар не учитывается в итоговой сумме заказа", () ->{
            $("#basket-root > div:first-of-type > div > div > div > div > div:nth-of-type(2)" +
                    " > div > div").shouldHave(visible);
        });

    }
}


