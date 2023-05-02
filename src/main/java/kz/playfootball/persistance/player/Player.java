package kz.playfootball.persistance.player;

import javax.persistence.Entity;
import kz.playfootball.persistance.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Player extends BaseEntity {

    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Double rating;

}
