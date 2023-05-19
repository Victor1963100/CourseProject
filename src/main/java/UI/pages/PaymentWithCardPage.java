package UI.pages;

import UI.data.DataGenerator;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class PaymentWithCardPage {
    //Заголовок
    private SelenideElement headPaymentWithCard = $(Selectors.withText("Оплата по карте"));
    //Кнопки
    private SelenideElement buttonContinue = $(Selectors.withText("Продолжить"));
    //Номер карты
    private SelenideElement cardNumber = $("[placeholder=\"0000 0000 0000 0000\"]");
    //Месяц
    private SelenideElement month = $("[placeholder=\"08\"]");
    //Год
    private SelenideElement year = $("[placeholder=\"22\"]");
    //Владелец
    private SelenideElement owner = $("div:nth-child(3) span:nth-child(1) span.input__box input");
    //CVC
    private SelenideElement cvcCvv = $("[placeholder=\"999\"]");

    public PaymentWithCardPage() {
        headPaymentWithCard.shouldBe(Condition.visible);
    }

    public void validCardNumber() {
        cardNumber.setValue(DataGenerator.validCardNumber());
    }

    public void validMonth() {
        month.setValue(DataGenerator.generateValidMonth());
    }

    public void validYear() {
        year.setValue(DataGenerator.generateValidYear());
    }

    public void validOwner() {
        owner.setValue(DataGenerator.generateFullNameInEng());
    }

    public void validCVC() {
        cvcCvv.setValue(DataGenerator.generateCVC());
    }

    public void allFieldsAreValid() {
        validCardNumber();
        validMonth();
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void latinLettersInCardNumberField() {
        cardNumber.setValue("ddddvvvvnnnnxxxx");
        validMonth();
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void cyrillicLettersInCardNumberField() {
        cardNumber.setValue("ккккрррррнннноооо");
        validMonth();
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void symbolsInCardNumberField() {
        cardNumber.setValue("%%%%????;;;;####");
        validMonth();
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void cardNumberFieldIsEmpty() {
        validMonth();
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void numberOfDeclinedCard() {
        cardNumber.setValue(DataGenerator.declinedCardNumber());
        validMonth();
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void invalidMonthFormat() {
        validCardNumber();
        month.setValue(DataGenerator.generateInvalidMonthFormat());
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void notExistedMonth13() {
        validCardNumber();
        month.setValue("13");
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void notExistedMonth0() {
        validCardNumber();
        month.setValue("0");
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void latinLettersInMonthField() {
        validCardNumber();
        month.setValue("sd");
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void cyrillicLettersInMonthField() {
        validCardNumber();
        month.setValue("ва");
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void symbolsInMonthField() {
        validCardNumber();
        month.setValue("%%");
        validYear();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void yearMoreThan5() {
        validCardNumber();
        validMonth();
        year.setValue("27");
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void pastYear() {
        validCardNumber();
        validMonth();
        year.setValue(DataGenerator.generatePastYear());
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void emptyYearField() {
        validCardNumber();
        validMonth();
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void latinLettersInYearField() {
        validCardNumber();
        validMonth();
        year.setValue("ff");
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void cyrillicLettersInYearField() {
        validCardNumber();
        validMonth();
        year.setValue("нн");
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void symbolsInYearField() {
        validCardNumber();
        validMonth();
        year.setValue("%$");
        validOwner();
        validCVC();
        buttonContinue.click();
    }

    public void cyrillicLettersInOwnerField() {
        validCardNumber();
        validMonth();
        validYear();
        owner.setValue(DataGenerator.generateFullNameInRus());
        validCVC();
        buttonContinue.click();
    }

    public void symbolsInOwnerField() {
        validCardNumber();
        validMonth();
        validYear();
        owner.setValue("%^$#");
        validCVC();
        buttonContinue.click();
    }

    public void emptyOwnerField() {
        validCardNumber();
        validMonth();
        validYear();
        validCVC();
        buttonContinue.click();
    }

    public void figuresInOwnerField() {
        validCardNumber();
        validMonth();
        validYear();
        owner.setValue(DataGenerator.generateNumber());
        validCVC();
        buttonContinue.click();
    }

    public void twoFiguresInCVCField() {
        validCardNumber();
        validMonth();
        validYear();
        validOwner();
        cvcCvv.setValue(DataGenerator.generateTwoFigures());
        buttonContinue.click();
    }

    public void oneFiguresInCVCField() {
        validCardNumber();
        validMonth();
        validYear();
        validOwner();
        cvcCvv.setValue(DataGenerator.generateOneFigure());
        buttonContinue.click();
    }

    public void cyrillicLettersInCVCField() {
        validCardNumber();
        validMonth();
        validYear();
        validOwner();
        cvcCvv.setValue("ррр");
        buttonContinue.click();
    }

    public void latinLettersInCVCField() {
        validCardNumber();
        validMonth();
        validYear();
        validOwner();
        cvcCvv.setValue("ddd");
        buttonContinue.click();
    }

    public void symbolsInCVCField() {
        validCardNumber();
        validMonth();
        validYear();
        validOwner();
        cvcCvv.setValue("#@%");
        buttonContinue.click();
    }

    public void emptyCVCField() {
        validCardNumber();
        validMonth();
        validYear();
        validOwner();
        buttonContinue.click();
    }

}
