package kz.playfootball.dto.player;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 7156526077883281623L;

    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Double rating;

}
