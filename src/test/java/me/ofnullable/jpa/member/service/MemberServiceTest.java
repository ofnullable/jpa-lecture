package me.ofnullable.jpa.member.service;

import me.ofnullable.jpa.member.domain.Member;
import me.ofnullable.jpa.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest // autowired
@Transactional  // rollback..
@ExtendWith(SpringExtension.class)
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void join() throws Exception {
        // given
        var member = new Member();
        member.setUsername("Lee");

        // when
        var join = memberService.join(member); // insert 실행되지 않음

        // then
        assertEquals(member, join);
    }

    @Test
    void throw_exception_when_username_duplicated() throws Exception {
        // given
        var member1 = new Member();
        member1.setUsername("Lee");

        var member2 = new Member();
        member2.setUsername("Lee");

        // when
        memberService.join(member1);
        var expected = catchThrowable(() -> memberService.join(member2)); // Exception!

        // then
        assertEquals(expected.getClass(), IllegalStateException.class);
    }

}