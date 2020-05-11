package me.ofnullable.jpa.member.api;

import lombok.RequiredArgsConstructor;
import me.ofnullable.jpa.member.domain.Member;
import me.ofnullable.jpa.member.dto.*;
import me.ofnullable.jpa.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        var joined = memberService.join(member);
        return new CreateMemberResponse(joined.getId());
    }

    @GetMapping("/api/v2/members")
    public MembersResponse membersV2() {
        var members = memberService.findMembers().stream()
                .map(member -> new MemberResponse(member.getUsername()))
                .collect(Collectors.toList());
        return new MembersResponse(members, members.size());
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid JoinRequest dto) {
        var member = new Member();
        member.setUsername(dto.getUsername());

        var joined = memberService.join(member);
        return new CreateMemberResponse(joined.getId());
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMember(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest dto) {

        memberService.update(id, dto.getUsername());
        var res = memberService.findOne(id);
        return new UpdateMemberResponse(res.getId(), res.getUsername());
    }

}
