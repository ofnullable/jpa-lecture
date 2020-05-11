package me.ofnullable.jpa.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberResponse {

    private String username;

    public MemberResponse(String username) {
        this.username = username;
    }

}
