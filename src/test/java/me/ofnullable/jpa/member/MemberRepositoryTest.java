package me.ofnullable.jpa.member;

import me.ofnullable.jpa.member.domain.Member;
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
    @Rollback(false)
    void save_member() throws Exception {
        // given
        var member = new Member();
        member.setUsername("tester");

        // when
        Long saved = memberRepository.save(member);
        var result = memberRepository.find(saved);

        // then
        assertThat(member.getId()).isEqualTo(result.getId());
        assertThat(member.getUsername()).isEqualTo(result.getUsername());

        assertThat(member).isEqualTo(result);
    }

}