package ru.test.milkin.api;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import ru.test.milkin.model.credentials_grant.CredentialsGrantResponseData;
import ru.test.milkin.model.credentials_grant.resource_owner.ResourceOwnerPasswordCredentialsGrantRequestData;
import ru.test.milkin.model.player.CreatePlayerRequestData;
import ru.test.milkin.model.player.PlayerProfileData;

@Feature("API-Test")
public class ApiTest {
    @DataProvider(name = "newPlayerOptionalFieldsDataProvider")
    public Object[][] newPlayerOptionalFieldsDataProvider() {
        return new Object[][]{
                new Object[]{null, null, null},
                new Object[]{RandomStringUtils.randomAlphabetic(10), null, "RUB"},
                new Object[]{null, RandomStringUtils.randomAlphabetic(10), "EUR"},
                new Object[]{RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), "USD"},
                new Object[]{null, null, "CNY"}
        };
    }

    @Test(description = "Регистрация нового игрока и получение профиля",
            dataProvider = "newPlayerOptionalFieldsDataProvider",
            groups = {"api"}
    )
    public void registerNewPlayer(String name, String surname, String currencyCode) {
        //1. Получить токен гостя (Client Credentials Grant, scope — guest:default)
        CredentialsGrantResponseData guestCredentials = Request.getGuestToken();

        //2. Зарегистрировать игрока
        CreatePlayerRequestData newPlayer = createNewPlayerRequestData(name, surname, currencyCode);
        PlayerProfileData registeredPlayerData = Request.registerNewPlayer(guestCredentials.getAccessToken(), newPlayer);

        //3. Авторизоваться под созданным игроком(Resource Owner Password Credentials Grant)
        CredentialsGrantResponseData playerCredentials = Request.logInByPlayer(
                new ResourceOwnerPasswordCredentialsGrantRequestData()
                        .setUsername(newPlayer.getUsername())
                        .setPassword(newPlayer.getPasswordChange())
        );

        //4. Запросить данные профиля игрока
        PlayerProfileData playerProfileData = Request.getPlayerProfile(playerCredentials.getAccessToken(), registeredPlayerData.getId().toString());
        Assert.assertEquals(registeredPlayerData, playerProfileData, "Профиль игрока при регистрации не соответсвует полученному профилю при просмотре.");

        //5. Запросить данные другого игрока
        Request.tryToGetOtherPlayerProfile(playerCredentials.getAccessToken(), RandomStringUtils.randomNumeric(4));
    }


    @Step("Генерация данных нового игрока")
    private CreatePlayerRequestData createNewPlayerRequestData(String name, String surname, String currencyCode) {
        String username = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphanumeric(10);
        String encodedPassword = new String(Base64.getEncoder().encode(password.getBytes(StandardCharsets.UTF_8)));
        CreatePlayerRequestData newPlayer = new CreatePlayerRequestData()
                .setUsername(username)
                .setPasswordChange(encodedPassword)
                .setPasswordRepeat(encodedPassword)
                .setEmail(username + "@test.com")
                .setName(name)
                .setSurname(surname)
                .setCurrencyCode(currencyCode);

        Allure.addAttachment("new player", newPlayer.toString());
        return newPlayer;
    }
}
