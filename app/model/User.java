package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;

@NoArgsConstructor
@SuperBuilder
@Data
@Entity
public class User extends BaseEntity{

    @Column(name = "EMAIL")
    private String email;
}
