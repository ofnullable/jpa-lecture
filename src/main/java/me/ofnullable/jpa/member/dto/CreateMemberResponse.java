package me.ofnullable.jpa.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateMemberResponse {

    private Long id;

    public CreateMemberResponse(Long id) {
        this.id = id;
    }

}
