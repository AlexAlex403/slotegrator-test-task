package ru.test.milkin.api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.specification.RequestSpecification;
import ru.test.milkin.api.specifications.Specification;
import ru.test.milkin.model.credentials_grant.CredentialsGrantResponseData;
import ru.test.milkin.model.credentials_grant.client.ClientCredentialsGrantRequestData;
import ru.test.milkin.model.credentials_grant.resource_owner.ResourceOwnerPasswordCredentialsGrantRequestData;
import ru.test.milkin.model.player.CreatePlayerRequestData;
import ru.test.milkin.model.player.PlayerProfileData;

import static io.restassured.RestAssured.given;
import static ru.test.milkin.api.specifications.Specification.getRequestSpecGuestBasicAuth;
import static ru.test.milkin.api.specifications.Specification.getRequestSpecProtectedOauth2;
import static ru.test.milkin.api.specifications.Specification.getResponseSpecCreated201;
import static ru.test.milkin.api.specifications.Specification.getResponseSpecNotFound404;
import static ru.test.milkin.api.specifications.Specification.getResponseSpecOK200;

public class Request {

    /**
     * Получить токен гостя (Client Credentials Grant, scope — guest:default)
     *
     * @return CredentialsGrantResponseData
     */
    @Step("Получение токена гостя (Client Credentials Grant, scope — guest:default)")
    public static CredentialsGrantResponseData getGuestToken() {
        Specification.installSpecification(getRequestSpecGuestBasicAuth(), getResponseSpecOK200());
        return givenWithLogEnabled()
                .when()
                .body(new ClientCredentialsGrantRequestData())
                .post("/v2/oauth2/token")
                .then()
                .log().all().and()
                .extract().body().as(CredentialsGrantResponseData.class);
    }

    /**
     * Зарегистрировать игрока
     *
     * @param accessToken - токен авторизации
     * @param newPlayer   - данные нового регистрируемого игрока
     * @return PlayerResponseData
     */
    @Step("Регистрация нового игрока")
    public static PlayerProfileData registerNewPlayer(String accessToken, CreatePlayerRequestData newPlayer) {
        Specification.installSpecification(getRequestSpecProtectedOauth2(accessToken), getResponseSpecCreated201());
        return givenWithLogEnabled()
                .when()
                .body(newPlayer)
                .post("/v2/players")
                .then()
                .log().all().and()
                .extract().body().as(PlayerProfileData.class);
    }

    /**
     * Авторизоваться под созданным игроком(Resource Owner Password Credentials Grant)
     *
     * @param credentials - данные для авторизации
     * @return CredentialsGrantResponseData
     */
    @Step("Авторизация под созданным игроком(Resource Owner Password Credentials Grant)")
    public static CredentialsGrantResponseData logInByPlayer(ResourceOwnerPasswordCredentialsGrantRequestData credentials) {
        Specification.installSpecification(getRequestSpecGuestBasicAuth(), getResponseSpecOK200());
        return givenWithLogEnabled()
                .when()
                .body(credentials)
                .post("/v2/oauth2/token")
                .then()
                .log().all().and()
                .extract().body().as(CredentialsGrantResponseData.class);
    }

    /**
     * Запросить данные профиля игрока
     *
     * @param accessToken - токен авторизации
     * @param playerId    - id профиля игрока
     * @return PlayerProfileData
     */
    @Step("Запрос данных профиля игрока")
    public static PlayerProfileData getPlayerProfile(String accessToken, String playerId) {
        Specification.installSpecification(getRequestSpecProtectedOauth2(accessToken), getResponseSpecOK200());
        return givenWithLogEnabled()
                .when()
                .get("/v2/players/" + playerId)
                .then()
                .log().all().and()
                .extract().body().as(PlayerProfileData.class);
    }

    /**
     * Запросить данные другого игрока
     *
     * @param accessToken - токен авторизации
     * @param playerId    - id профиля другого игрока
     */
    @Step("Запрос данных другого игрока")
    public static void tryToGetOtherPlayerProfile(String accessToken, String playerId) {
        Specification.installSpecification(getRequestSpecProtectedOauth2(accessToken), getResponseSpecNotFound404());
        givenWithLogEnabled()
                .when()
                .get("/v2/players/" + playerId)
                .then()
                .log().all();
    }

    private static RequestSpecification givenWithLogEnabled() {
        return given()
                .filter(new AllureRestAssured())
                .config(RestAssured.config().logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()));
    }
}
