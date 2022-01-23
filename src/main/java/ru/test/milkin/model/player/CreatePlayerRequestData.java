package ru.test.milkin.model.player;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePlayerRequestData {
    private String username;
    @JsonProperty("password_change")
    private String passwordChange; //base64 encoded password
    @JsonProperty("password_repeat")
    private String passwordRepeat; //base64 encoded password
    private String email;
    private String name; //optional
    private String surname; //optional
    @JsonProperty("currency_code")
    private String currencyCode; //optional
}
