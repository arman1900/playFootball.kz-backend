package kz.playfootball.controller;

import graphql.ExecutionResult;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLIgnore;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import kz.playfootball.dto.LoginData;
import kz.playfootball.dto.player.PlayerDto;
import kz.playfootball.service.GraphQLService;
import kz.playfootball.service.PlayerService;
import kz.playfootball.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final SessionService sessionService;
    private final GraphQLService graphQLService;

    @PostMapping("/graphql")
    public ResponseEntity<?> graphQL(@RequestBody String query) {
        ExecutionResult executionResult = graphQLService.getGraphQL().execute(query);
        return ResponseEntity.ok(executionResult);
    }

    @GraphQLApi
    @RequiredArgsConstructor
    public class PlayerMutation  {

        private final PlayerService playerService;

        @GraphQLMutation(name = "registerPlayer")
        public PlayerDto registerPlayer(@GraphQLArgument(name = "loginData") LoginData loginData) {
            return playerService.registerPlayer(loginData.getPhoneNumber(), loginData.getPassword());
        }

        @GraphQLMutation(name = "loginPlayer")
        public boolean loginPlayer(@GraphQLArgument(name = "loginData") LoginData loginData) {
            return playerService.loginPlayer(loginData.getPhoneNumber(), loginData.getPassword());
        }

        @GraphQLMutation(name = "logoutPlayer")
        public boolean logoutPlayer(@GraphQLArgument(name = "something") String something) {
            playerService.logoutPlayer();
            return true;
        }
    }
}
