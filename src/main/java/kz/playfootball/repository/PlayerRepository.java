package kz.playfootball.repository;

import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;

import java.util.Optional;
import javax.persistence.QueryHint;
import kz.playfootball.persistance.player.Player;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    @QueryHints(@QueryHint(name = HINT_CACHEABLE, value = "true"))
    Optional<Player> findByPhoneNumber(String phoneNumber);
}
