package com.laioffer.twitch.external.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TwitchToken(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") long expiresIn
) {
}