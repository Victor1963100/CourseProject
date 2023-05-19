package API;

import UI.data.DataGenerator;
import lombok.Value;

public class DataForAPI {


    @Value
    public static class BodyForApi {
        String number;
        String year;
        String month;
        String holder;
        String cvc;
    }

    public static BodyForApi getBodyForApiWithValidCard() {
        return new BodyForApi(DataGenerator.validCardNumber(), DataGenerator.generateValidYear(), DataGenerator.generateValidMonth(), DataGenerator.generateFullNameInEng(), DataGenerator.generateCVC());
    }

    public static BodyForApi getBodyForApiWithDeclinedCard() {
        return new BodyForApi(DataGenerator.declinedCardNumber(), DataGenerator.generateValidYear(), DataGenerator.generateValidMonth(), DataGenerator.generateFullNameInEng(), DataGenerator.generateCVC());
    }

    public static BodyForApi getInvalidBody () {
        return new BodyForApi("1111222233334444","39","99","467548","fgh");
    }
}
