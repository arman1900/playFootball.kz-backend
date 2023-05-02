package kz.playfootball.service.impl;

import static kz.playfootball.util.PasswordEncryptionUtil.checkPassword;
import static kz.playfootball.util.PasswordEncryptionUtil.hashPassword;

import javax.management.openmbean.KeyAlreadyExistsException;
import kz.playfootball.dto.player.PlayerDto;
import kz.playfootball.persistance.player.Player;
import kz.playfootball.repository.PlayerRepository;
import kz.playfootball.service.PlayerService;
import kz.playfootball.service.SessionService;
import kz.playfootball.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final SessionService sessionService;

    @Override
    public PlayerDto registerPlayer(String phoneNumber, String password) {
        if (playerRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new KeyAlreadyExistsException("Phone number already exists!");
        }

        Player player = new Player();
        player.setPhoneNumber(phoneNumber);
        player.setPassword(hashPassword(password));
        player.setRating(Constants.DEFAULT_RATING);

        var newPlayer = playerRepository.save(player);


        var playerDto = PlayerDto.builder()
            .phoneNumber(newPlayer.getPhoneNumber())
            .firstName(newPlayer.getFirstName())
            .lastName(newPlayer.getLastName())
            .rating(newPlayer.getRating())
            .build();

        sessionService.setSessionAttribute("player", playerDto);

        return playerDto;
    }

    @Override
    public boolean loginPlayer(String phoneNumber, String password) {
        var optPlayer = playerRepository.findByPhoneNumber(phoneNumber);

        if (optPlayer.isEmpty()) {
            return false;
        }

        var player = optPlayer.get();

        var playerDto = PlayerDto.builder()
            .phoneNumber(player.getPhoneNumber())
            .firstName(player.getFirstName())
            .lastName(player.getLastName())
            .rating(player.getRating())
            .build();

        var isAuth = checkPassword(password, optPlayer.get().getPassword());
        sessionService.setSessionAttribute("player", playerDto);

        return isAuth;
    }

    @Override
    public PlayerDto getCurrentPlayer() {
        return (PlayerDto) sessionService.getSessionAttribute("player");
    }

    @Override
    public boolean logoutPlayer() {
        PlayerDto player = (PlayerDto) sessionService.getSessionAttribute("player");
        if (player != null) {
            sessionService.setSessionAttribute("player", null);
            return true;
        } else {
            return false;
        }
    }
}
