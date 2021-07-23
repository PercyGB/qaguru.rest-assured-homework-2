import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;

public class DemoWebShopTests {

    @Test
    void addProductToCardTest(){
        String authorizationCookie =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .body("product_attribute_74_5_26=81&product_attribute_74_6_27=83&product_attribute_74_3_28=86&addtocart_74.EnteredQuantity=4")
                .when()
                        .post("http://demowebshop.tricentis.com/addproducttocart/details/74/1")
                .then()
                        .statusCode(200)
                        .extract().cookie("Nop.customer");

        open("http://demowebshop.tricentis.com/");
        getWebDriver().manage().addCookie(new Cookie("Nop.customer", authorizationCookie));
        refresh();

        $(".cart-qty").shouldHave(text("4"));
    }
}
