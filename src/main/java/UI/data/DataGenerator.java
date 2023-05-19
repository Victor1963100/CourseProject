package UI.data;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    //СОЗДАНИЕ ДАННЫХ ДЛЯ ПОЗИТИВНЫХ СЦЕНАРИЕВ

    public static String generateValidMonth() {
        String[] monthes = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        Random index = new Random();
        int indexInt = index.nextInt(monthes.length);
        String randomMonth = monthes[indexInt];
        return randomMonth;
    }

    public static String generateValidYear() {
        String[] years = {"22", "23", "24", "25", "26"};
        Random index = new Random();
        int indexInt = index.nextInt(years.length);
        String randomYear = years[indexInt];
        return randomYear;
    }

    public static String generateCVC() {
        String[] cvcCodes = {"151", "262", "233", "274", "215", "296", "589", "999", "242", "147", "856", "854"};
        Random index = new Random();
        int indexInt = index.nextInt(cvcCodes.length);
        String randomCVC = cvcCodes[indexInt];
        return randomCVC;
    }

    public static String validCardNumber() {
        String validCardNumber = "1111 2222 3333 4444";
        return validCardNumber;
    }

    public static String generateFullNameInEng() {
        Faker faker = new Faker();
        String fullName = faker.name().fullName();
        return fullName;
    }

    // СОЗДАНИЕ ДАННЫХ ДЛЯ НЕГАТИВНЫХ СЦЕНАРИЕВ

    public static String declinedCardNumber() {
        String invalidCardNumber = "5555 6666 7777 8888";
        return invalidCardNumber;
    }


    public static String generateFullNameInRus() {
        Faker faker = new Faker(new Locale("ru"));
        String fullName = faker.name().fullName();
        return fullName;
    }

    public static String generateNumber() {
        Faker faker = new Faker();
        int numberBetween = faker.number().numberBetween(122, 9999);
        return String.valueOf(numberBetween);
    }

    public static String generatePastYear() {
        String[] years = {"16", "17", "18", "19", "20"};
        Random index = new Random();
        int indexInt = index.nextInt(years.length);
        String randomPastYear = years[indexInt];
        return randomPastYear;
    }

    public static String generateInvalidMonthFormat() {
        String[] monthes = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        Random index = new Random();
        int indexInt = index.nextInt(monthes.length);
        String randomMonth = monthes[indexInt];
        return randomMonth;
    }

    public static String generateOneFigure() {
        String[] monthes = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        Random index = new Random();
        int indexInt = index.nextInt(monthes.length);
        String oneFigure = monthes[indexInt];
        return oneFigure;
    }

    public static String generateTwoFigures() {
        Faker faker = new Faker();
        int twoFigures = faker.number().numberBetween(11, 99);
        return String.valueOf(twoFigures);
    }

}