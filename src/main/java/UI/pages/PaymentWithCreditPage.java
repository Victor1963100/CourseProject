package UI.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class PaymentWithCreditPage {
    //Заголовок
    private SelenideElement headPaymentWithCard = $(Selectors.withText("Кредит по данным карты"));

    public PaymentWithCreditPage() {
        headPaymentWithCard.shouldBe(Condition.visible);
    }
}
