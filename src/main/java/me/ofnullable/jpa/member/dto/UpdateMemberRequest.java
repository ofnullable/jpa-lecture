package me.ofnullable.jpa.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UpdateMemberRequest {

    @NotBlank
    private String username;

}
