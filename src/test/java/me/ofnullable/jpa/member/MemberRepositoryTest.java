package me.ofnullable.jpa.member;

import me.ofnullable.jpa.member.domain.Member;
import me.ofnullable.jpa.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void save_member() throws Exception {
        // given
        var member = new Member();
        member.setUsername("tester");

        // when
        Member saved = memberRepository.save(member);
        var result = memberRepository.findOne(saved.getId());

        // then
        assertThat(member).isEqualTo(saved);
        assertThat(member.getUsername()).isEqualTo(result.getUsername());

        assertThat(member).isEqualTo(result);
    }

}