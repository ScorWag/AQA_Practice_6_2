package ru.netology.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.MoneyTransferPage;
import ru.netology.page.VerificationPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateSteps {
    private static LoginPage loginPage;
    private static VerificationPage verificationPage;
    private static DashboardPage dashboardPage;
    private static MoneyTransferPage moneyTransferPage;

    @Пусть("Пользователь залогинен с именем {string} и паролем {string};")
    public void authorization(String login, String password) {
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        verificationPage = loginPage.validLogin(login, password);
    }

    @И("Ввел корректный проверочный код из смс {string};")
    public void verification(String code) {
        dashboardPage = verificationPage.validVerify(code);
    }

    @Когда("Пользователь переводит {int} рублей с карты с номером {string} на свою {int} карту с главной страницы;")
    public void moneyTransfer(int amount, String cardNumberFor, int cardIndexTo) {
        moneyTransferPage = dashboardPage.transfer(cardIndexTo);
        dashboardPage = moneyTransferPage.moneyTransfer(amount, cardNumberFor);
    }

    @Тогда("баланс его {int} карты из списка на главной странице должен стать {int} рублей.;")
    public void checkBalance(int cardIndex, int expectedAmount) {
        int actualBalance = dashboardPage.getCardBalance(cardIndex);

        assertEquals(expectedAmount, actualBalance);
    }
}

