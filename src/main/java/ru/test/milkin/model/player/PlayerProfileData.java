package ru.test.milkin.model.player;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class PlayerProfileData {
    @NonNull private Long id;
    @JsonProperty("country_id")
    private Object countryId;
    @JsonProperty("timezone_id")
    private Object timezoneId;
    @NonNull private String username;
    @NonNull private String email;
    private String name;
    private String surname;
    private String gender;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("birthdate")
    private Object birthDate;
    @JsonProperty("bonuses_allowed")
    private Boolean bonusesAllowed;
    @JsonProperty("is_verified")
    private Boolean isVerified;
}
