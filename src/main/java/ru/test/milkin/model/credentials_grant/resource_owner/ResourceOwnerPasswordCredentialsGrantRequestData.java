package ru.test.milkin.model.credentials_grant.resource_owner;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResourceOwnerPasswordCredentialsGrantRequestData {
    @JsonProperty("grant_type")
    private String grantType = "password";
    private String username;
    private String password;
}
