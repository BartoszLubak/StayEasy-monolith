package com.example.stayeasymonolith.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Name {
    private String firstName;
    private String lastName;
    private Type type = Type.getDefault();

}
