package kz.playfootball.service;

import kz.playfootball.dto.player.PlayerDto;

public interface PlayerService {

    PlayerDto registerPlayer(String phoneNumber, String password);

    boolean loginPlayer(String phoneNumber, String password);

    PlayerDto getCurrentPlayer();

    boolean logoutPlayer();
}
