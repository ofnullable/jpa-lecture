package me.ofnullable.jpa.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateMemberResponse {

    private Long id;

    private String username;

    public UpdateMemberResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }

}
