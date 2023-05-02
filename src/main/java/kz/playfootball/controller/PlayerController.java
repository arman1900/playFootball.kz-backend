package kz.playfootball.controller;

import javax.management.openmbean.KeyAlreadyExistsException;
import kz.playfootball.dto.LoginData;
import kz.playfootball.dto.player.PlayerDto;
import kz.playfootball.service.PlayerService;
import kz.playfootball.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playerService")
public class PlayerController {

    private final PlayerService playerService;
    private final SessionService sessionService;


    @PostMapping("/register")
    public ResponseEntity<?> registerPlayer(@RequestBody LoginData loginData) {
        try {
            PlayerDto player =
                playerService.registerPlayer(loginData.getPhoneNumber(), loginData.getPassword());
            return ResponseEntity.ok(player);
        } catch (KeyAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number already exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPlayer(@RequestBody LoginData loginData) {
        var session =  sessionService.getSessionAttribute("player");

        if (session != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("logout first");
        }
        boolean isAuthenticated = playerService.loginPlayer(loginData.getPhoneNumber(), loginData.getPassword());

        if (isAuthenticated) {
            return ResponseEntity.ok("Player is authenticated");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid phone number or password");
        }
    }

    @GetMapping("/current-player")
    public ResponseEntity<?> currentPlayer() {
        PlayerDto player = playerService.getCurrentPlayer();
        if (player != null) {
            return ResponseEntity.ok(player);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutPlayer() {
        var loggedOut = playerService.logoutPlayer();
        if(loggedOut) {
            return ResponseEntity.ok("Player has been logged out");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Player is not logged in");
        }
    }
}
