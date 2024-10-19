import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;
import utils.RandomUtils;

import static io.qameta.allure.Allure.step;

@Tag("registrationFormTest")
@DisplayName("Страница формы регистрации студента")
public class RegFormJenkinsTest extends TestBase {

    private final RegistrationPage registrationPage = new RegistrationPage();

    String firstName = RandomUtils.getRandomFirstName(),
            lastName = RandomUtils.getRandomLastName(),
            userEmail = RandomUtils.getRandomEmail(),
            gender = RandomUtils.getRandomGender(),
            userNumber = RandomUtils.getRandomNumber(),
            yearOfBirth = RandomUtils.getRandomYearOfBirth(),
            monthOfBirth = RandomUtils.getRandomMonthOfBirth(),
            dayOfBirth = RandomUtils.getRandomDayOfBirth(monthOfBirth, yearOfBirth),
            subjects = RandomUtils.getRandomSubject(),
            hobbies = RandomUtils.getRandomHobbies(),
            picture = RandomUtils.getRandomPicture(),
            currentAddress = RandomUtils.getRandomCurrentAddress(),
            state = RandomUtils.getRandomState(),
            city = RandomUtils.getRandomCity(state);

    @DisplayName("Тест на проверку успешной регистрации студента")
    @Test
    void successfulRegistrationTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        step("Открываем страницу формы регистрации студента", () -> {
            registrationPage.openPage();
        });

        step("Заполняем форму регистрации студента", () -> {
            registrationPage.setFirstName(firstName)
                    .setLastName(lastName)
                    .setUserEmail(userEmail)
                    .setUserNumber(userNumber)
                    .setGenderWrapper(gender)
                    .setDateOfBirth(dayOfBirth, monthOfBirth, yearOfBirth)
                    .setSubjects(subjects)
                    .setHobbiesWrapper(hobbies)
                    .setPicture(picture)
                    .setCurrentAddress(currentAddress)
                    .setState(state)
                    .setCity(city)
                    .clickSubmit();
        });

        step("Проверка успешной регистрации студента", () -> {
            registrationPage.checkResult("Student Name", firstName + " " + lastName)
                    .checkResult("Student Email", userEmail)
                    .checkResult("Gender", gender)
                    .checkResult("Mobile", userNumber)
                    .checkResult("Date of Birth", dayOfBirth + " " + monthOfBirth + "," + yearOfBirth)
                    .checkResult("Subjects", subjects)
                    .checkResult("Hobbies", hobbies)
                    .checkResult("Picture", picture)
                    .checkResult("Address", currentAddress)
                    .checkResult("State and City", state + " " + city);
        });
    }

    @DisplayName("Тест на неполное заполнение формы регистрации студента")
    @Test
    void incompleteDataEntry() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        step("Открываем страницу формы регистрации студента", () -> {
            registrationPage.openPage();
        });

        step("Заполняем форму регистрации студента с минимальными данными", () -> {
            registrationPage.setFirstName(firstName)
                    .setDateOfBirth(dayOfBirth, monthOfBirth, yearOfBirth)
                    .setSubjects(subjects)
                    .setState(state)
                    .setCity(city)
                    .clickSubmit();
        });

        step("Проверка неудачной попытки зарегистрироваться", () -> {
            registrationPage.checkNegativeResult();
        });
    }
}
