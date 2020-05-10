package me.ofnullable.jpa.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class JoinRequest {

    @NotEmpty
    private String username;

}
