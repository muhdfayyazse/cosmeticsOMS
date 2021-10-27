package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import java.io.Serializable;

@NoArgsConstructor
@SuperBuilder
@Data
public class BaseEntity implements Serializable {
    @Column(name = "ID")
    private String id;
}
