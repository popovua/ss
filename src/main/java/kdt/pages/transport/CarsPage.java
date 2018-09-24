package kdt.pages.transport;

import annotations.URI;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import kdt.pages.Browser;
import kdt.pages.PageManager;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;

@URI("/transport/cars")
public class CarsPage extends Browser {

    private ElementsCollection cars = $$(By.xpath("(//form[@id='filter_frm'])//h4[@class='category']/a"));

    @Step
    public PageManager selectModel(String carName) {
        cars.stream()
                .filter(car -> car.getText().toLowerCase().trim()
                        .equals(carName.toLowerCase().trim()))
                .findFirst()
                .get()
                .click();
        return pageManager;
    }

}
