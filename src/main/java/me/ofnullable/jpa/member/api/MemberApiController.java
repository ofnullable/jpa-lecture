package me.ofnullable.jpa.member.api;

import lombok.RequiredArgsConstructor;
import me.ofnullable.jpa.member.domain.Member;
import me.ofnullable.jpa.member.dto.CreateMember;
import me.ofnullable.jpa.member.dto.JoinRequest;
import me.ofnullable.jpa.member.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMember saveMemberV1(@RequestBody @Valid Member member) {
        Member joined = memberService.join(member);
        return new CreateMember(joined.getId());
    }

    @PostMapping("/api/v2/members")
    public CreateMember saveMemberV2(@RequestBody @Valid JoinRequest dto) {
        var member = new Member();
        member.setUsername(dto.getUsername());

        Member joined = memberService.join(member);
        return new CreateMember(joined.getId());
    }

}
