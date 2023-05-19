package UI;

import static com.codeborne.selenide.Selenide.$;

import SQL.SQLMethods;
import UI.pages.ChoiceOfPaymentPage;
import UI.pages.PaymentWithCardPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;

import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.selenide.AllureSelenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

public class UIPaymentWithCardTests {
    // Месяц
    private SelenideElement errorOfCardValidity = $(Selectors.withText("Неверно указан срок действия карты"));
    //Год
    private SelenideElement ValidityError = $(Selectors.withText("Истёк срок действия карты"));
    //Владелец
    private SelenideElement obligatoryOwnerField = $(Selectors.withText("Поле обязательно для заполнения"));
    private SelenideElement onlyLatinLetters = $(Selectors.withText("Допустимо использовать только латинские буквы"));
    //Неверный формат
    private SelenideElement notValidFormat = $(Selectors.withText("Неверный формат"));
    //Сообщения банка
    private SelenideElement successMessage = $(Selectors.withText("Успешно"));
    private SelenideElement approveMessage = $(Selectors.withText("Операция одобрена Банком."));
    private SelenideElement bankError = $(Selectors.withText("Ошибка"));
    private SelenideElement bankRejected = $(Selectors.withText("Ошибка! Банк отказал в проведении операции."));

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void openSetUp() {
        open("http://localhost:8080");
    }

    long numberOfRawsFromPaymentEntity() {
        SQLMethods sqlMethods = new SQLMethods();
        long numberOfRawsFromPaymentEntity = sqlMethods.getNumberOfRawsFromPaymentEntity();
        return numberOfRawsFromPaymentEntity;
    }

    String statusAfterSendingDataToServer() {
        SQLMethods sqlMethods = new SQLMethods();
        String statusAfterSendingDataToServer = sqlMethods.getStatusFromPaymentEntity();
        return statusAfterSendingDataToServer;
    }

    void checkUIThatSuccessIsVisible() {
        successMessage.shouldBe(Condition.visible, Duration.ofSeconds(15));
        approveMessage.shouldBe(Condition.visible);
    }

    void checkUIThatSuccessIsNotVisible() {
        successMessage.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
        approveMessage.shouldNotBe(Condition.visible);
    }

    void checkUIThatBankRejectedIsVisible() {
        bankError.shouldBe(Condition.visible, Duration.ofSeconds(15));
        bankRejected.shouldBe(Condition.visible);
    }

    void checkUIThatBankRejectedIsNotVisible() {
        bankError.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
        bankRejected.shouldNotBe(Condition.visible);
    }

    void ckeckNumberOfRawsInPaymentEntity(long initialNumberOfRawsFromPaymentEntity, int x) {
        long finalNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        assertEquals(initialNumberOfRawsFromPaymentEntity + x, finalNumberOfRawsFromPaymentEntity);
    }

    PaymentWithCardPage choicePaymentByCard() {
        ChoiceOfPaymentPage choiceOfPaymentPage = new ChoiceOfPaymentPage();
        var withCardWay = choiceOfPaymentPage.selectPaymentByCard();
        return withCardWay;

    }

    @Test
    @DisplayName("Successful Payment")
    public void shouldBeSuccessfulPayment() {
        // количество записей в БД до покупки тура
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        // выбор способа оплаты
        var withCard = choicePaymentByCard();
        // выбор метода, который заполняет поля определенным способом
        withCard.allFieldsAreValid();
        // проверка UI
        checkUIThatSuccessIsVisible();
        // статус оплаты после заполнения формы
        String statusAfterSendingDataToServer = statusAfterSendingDataToServer();
        // проверка количества записей в БД
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 1);
        // проверка статуса оплаты в БД
        assertEquals("APPROVED", statusAfterSendingDataToServer);
    }

    @Test
    @DisplayName("Latin Letters In Card Number Field")
    public void shouldErrorIfLatinLettersInCardNumberField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.latinLettersInCardNumberField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatBankRejectedIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Cyrillic Letters In Card Number Field")
    public void shouldErrorIfCyrillicLettersInCardNumberField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.cyrillicLettersInCardNumberField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatBankRejectedIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Symbols In Card Number Field")
    public void shouldErrorIfSymbolsInCardNumberField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.symbolsInCardNumberField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatBankRejectedIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Empty Card Number Field")
    public void shouldErrorIfCardNumberFieldIsEmpty() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.cardNumberFieldIsEmpty();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatBankRejectedIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Bank Rejection If Number Of Declined Card")
    public void shouldBeBankRejectionIfNumberOfDeclinedCard() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.numberOfDeclinedCard();
        String statusAfterSendingDataToServer = statusAfterSendingDataToServer();
        checkUIThatBankRejectedIsVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 1);
        assertEquals("DECLINED", statusAfterSendingDataToServer);
    }

    @Test
    @DisplayName("Invalid Month Format")
    public void shouldErrorIfInvalidMonthFormat() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.invalidMonthFormat();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Not Existed Month 13")
    public void shouldErrorIfNotExistedMonth13() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.notExistedMonth13();
        errorOfCardValidity.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Not Existed Month 0")
    public void shouldErrorIfNotExistedMonth0() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.notExistedMonth0();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Latin Letters In Month Field")
    public void shouldErrorIfLatinLettersInMonthField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.latinLettersInMonthField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Cyrillic Letters In Month Field")
    public void shouldErrorIfCyrillicLettersInMonthField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.cyrillicLettersInMonthField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Symbols In Month Field")
    public void shouldErrorIfSymbolsInMonthField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.symbolsInMonthField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Year More Than 5")
    public void shouldErrorIfYearMoreThan5() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.yearMoreThan5();
        errorOfCardValidity.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Past Year")
    public void shouldErrorIfPastYear() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.pastYear();
        ValidityError.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Empty Year Field")
    public void shouldErrorIfEmptyYearField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.emptyYearField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Latin Letters In Year Field")
    public void shouldErrorIfLatinLettersInYearField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.latinLettersInYearField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Cyrillic Letters In Year Field")
    public void shouldErrorIfCyrillicLettersInYearField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.cyrillicLettersInYearField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Symbols In Year Field")
    public void shouldErrorIfSymbolsInYearField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.symbolsInYearField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Cyrillic Letters In Owner Field")
    public void shouldErrorIfCyrillicLettersInOwnerField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.cyrillicLettersInOwnerField();
        onlyLatinLetters.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Symbols In Owner Field")
    public void shouldErrorIfSymbolsInOwnerField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.symbolsInOwnerField();
        onlyLatinLetters.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Empty Owner Field")
    public void shouldErrorIfEmptyOwnerField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.emptyOwnerField();
        obligatoryOwnerField.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Figures In Owner Field")
    public void shouldErrorIfFiguresInOwnerField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.figuresInOwnerField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("2 Figures In CVC Field")
    public void shouldErrorIf2FiguresInCVCField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.twoFiguresInCVCField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("1 Figure In CVC Field")
    public void shouldErrorIf1FiguresInCVCField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.oneFiguresInCVCField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Cyrillic Letters In CVC Field")
    public void shouldErrorIfСyrillicLettersInCVCField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.cyrillicLettersInCVCField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Latin Letters In CVC Field")
    public void shouldErrorIfLatinLettersInCVCField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.latinLettersInCVCField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Symbols In CVC Field")
    public void shouldErrorIfSymbolsInCVCField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.symbolsInCVCField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

    @Test
    @DisplayName("Empty CVC Field")
    public void shouldErrorIfEmptyCVCField() {
        long initialNumberOfRawsFromPaymentEntity = numberOfRawsFromPaymentEntity();
        var withCard = choicePaymentByCard();
        withCard.emptyCVCField();
        notValidFormat.shouldBe(Condition.visible);
        checkUIThatSuccessIsNotVisible();
        ckeckNumberOfRawsInPaymentEntity(initialNumberOfRawsFromPaymentEntity, 0);
    }

}
