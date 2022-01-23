package ru.test.milkin.model.credentials_grant.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ClientCredentialsGrantRequestData {
    @JsonProperty("grant_type")
    private String grantType = "client_credentials";
    private String scope = "guest:default";
}
