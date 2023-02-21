package com.laioffer.twitch.external;

import com.laioffer.twitch.external.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TwitchService {
    final String twitchClientId;
    final String twitchSecret;
    final TwitchApiClient twitchApiClient;
    final TwitchIdentityClient twitchIdentityClient;

    TwitchToken token = null;

    public TwitchService(
            @Value("${twitch.client-id}") String twitchClientId,
            @Value("${twitch.secret}")String twitchSecret,
            TwitchApiClient twitchApiClient,
            TwitchIdentityClient twitchIdentityClient
    ) {
        this.twitchClientId = twitchClientId;
        this.twitchSecret = twitchSecret;
        this.twitchApiClient = twitchApiClient;
        this.twitchIdentityClient = twitchIdentityClient;
    }

    @Cacheable("top_games")
    public List<Game> getTopGames() {
        if (token == null) {
            token = twitchIdentityClient.requestAccessToken(twitchClientId, twitchSecret, "client_credentials");
        }
        try {
            return twitchApiClient.getTopGames(bearerToken()).data();
        } catch (WebClientResponseException.Unauthorized e) {
            token = twitchIdentityClient.requestAccessToken(twitchClientId, twitchSecret, "client_credentials");
            return twitchApiClient.getTopGames(bearerToken()).data();
        }
    }

    @Cacheable("games_by_name")
    public List<Game> getGames(String name) {
        if (token == null) {
            token = twitchIdentityClient.requestAccessToken(twitchClientId, twitchSecret, "client_credentials");
        }
        try {
            return twitchApiClient.getGames(bearerToken(), name).data();
        } catch (WebClientResponseException.Unauthorized e) {
            token = twitchIdentityClient.requestAccessToken(twitchClientId, twitchSecret, "client_credentials");
            return twitchApiClient.getGames(bearerToken(), name).data();
        }
    }

    public List<Stream> getStreams(List<String> gameIds, int first) {
        if (token == null) {
            token = twitchIdentityClient.requestAccessToken(twitchClientId, twitchSecret, "client_credentials");
        }
        try {
            return twitchApiClient.getStreams(bearerToken(), gameIds, first).data();
        } catch (WebClientResponseException.Unauthorized e) {
            token = twitchIdentityClient.requestAccessToken(twitchClientId, twitchSecret, "client_credentials");
            return twitchApiClient.getStreams(bearerToken(), gameIds, first).data();
        }
    }

    public List<Video> getVideos(String gameId, int first) {
        if (token == null) {
            token = twitchIdentityClient.requestAccessToken(twitchClientId, twitchSecret, "client_credentials");
        }
        try {
            return twitchApiClient.getVideos(bearerToken(), gameId, first).data();
        } catch (WebClientResponseException.Unauthorized e) {
            token = twitchIdentityClient.requestAccessToken(twitchClientId, twitchSecret, "client_credentials");
            return twitchApiClient.getVideos(bearerToken(), gameId, first).data();
        }
    }

    public List<Clip> getClips(String gameId, int first) {
        if (token == null) {
            token = twitchIdentityClient.requestAccessToken(twitchClientId, twitchSecret, "client_credentials");
        }
        try {
            return twitchApiClient.getClips(bearerToken(), gameId, first).data();
        } catch (WebClientResponseException.Unauthorized e) {
            token = twitchIdentityClient.requestAccessToken(twitchClientId, twitchSecret,
                    "client_credentials");
            return twitchApiClient.getClips(bearerToken(), gameId, first).data();
        }
    }

    public List<String> getTopGameIds() {
        List<String> topGameIds = new ArrayList<>();
        for (Game game : getTopGames()) {
            topGameIds.add(game.id());
        }
        return topGameIds;
    }

    private String bearerToken() {
        return "Bearer " + token.accessToken();
    }
}
