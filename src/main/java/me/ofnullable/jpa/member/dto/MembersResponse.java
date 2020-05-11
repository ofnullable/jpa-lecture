package me.ofnullable.jpa.member.dto;

import lombok.Getter;
import lombok.Setter;
import me.ofnullable.jpa.common.ApiResponse;

import java.util.List;

@Getter @Setter
public class MembersResponse extends ApiResponse<List<MemberResponse>> {

    private int count;

    public MembersResponse(List<MemberResponse> members, int count) {
        super(members);
        this.count = count;
    }

}
