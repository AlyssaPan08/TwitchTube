package com.laioffer.twitch.external;

import com.laioffer.twitch.external.model.ClipResponse;
import com.laioffer.twitch.external.model.GameResponse;
import com.laioffer.twitch.external.model.StreamResponse;
import com.laioffer.twitch.external.model.VideoResponse;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
//Declarative HTTP Client
@HttpExchange("/helix")
public interface TwitchApiClient {
    @GetExchange("/games")
    GameResponse getGames(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("name") String name
    );

    @GetExchange( "/games/top")
    GameResponse getTopGames(@RequestHeader("Authorization") String authorization);

    @GetExchange( "/videos/")
    VideoResponse getVideos(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("game_id") String gameId,
            @RequestParam("first") int first
    );

    @GetExchange( "/clips/")
    ClipResponse getClips(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("game_id") String gameId,
            @RequestParam("first") int first
    );

    @GetExchange( "/streams/")
    StreamResponse getStreams(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("game_id") List<String> gameIds,
            @RequestParam("first") int first
    );
}
