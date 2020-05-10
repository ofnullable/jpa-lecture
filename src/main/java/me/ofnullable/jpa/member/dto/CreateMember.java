package me.ofnullable.jpa.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateMember {

    private Long id;

    public CreateMember(Long id) {
        this.id = id;
    }

}
