package kz.playfootball.persistance;


import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@MappedSuperclass
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BaseEntity {


    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "created_on")
    @CreationTimestamp
    private ZonedDateTime createdOn;

    @Column(name = "updated_on")
    @UpdateTimestamp
    private ZonedDateTime updatedOn;

    @Column(name = "entity_version")
    @Version
    private Long entityVersion;
}
