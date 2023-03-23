package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.AddUser.*;

class AppIBankTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginValidNameValidPasswordStatusActive() {
        Registration testUser = addUserValid();
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $("h2").shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldNotLoginValidNameValidPasswordStatusBlocked() {
        Registration testUser = addUserBlocked();
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void shouldNotLoginLoginInvalid() {
        Registration testUser = addUserLoginInvalid();
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginPasswordInvalid() {
        Registration testUser = addUserPasswordInvalid();
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginUserNotRegistered() {
        Registration testUser = addUserNotRegistered();
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));
    }
}
