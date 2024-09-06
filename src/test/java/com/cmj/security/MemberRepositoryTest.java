package com.cmj.security;

import com.cmj.security.domain.entity.Member;
import com.cmj.security.domain.entity.Role;
import com.cmj.security.domain.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .username("test" + i)
                    .password("1234")
                    .role(Role.builder()
                            .roleName("ROLE_USER")
                            .build())
                    .build();
            memberRepository.save(member);
        }
    }

    @Test
    void findAll() {
        List<Member> memberList = memberRepository.findAll();

        Assertions.assertThat(memberList.size()).isEqualTo(10);

        for (int i = 0; i < 10; i++) {
            Assertions.assertThat(memberList.get(i).getUsername()).isEqualTo("test" + i);
        }

    }

    @Test
    void findByUsername() {
        for (int i = 0; i < 10; i++) {
            Optional<Member> members = memberRepository.findByUsername("test" + i);
            Assertions.assertThat(members.isPresent()).isTrue();
        }
    }

//    @Test
//    void update() {
//        Optional<Member> member = memberRepository.findByUsername("test1");
//        member.ifPresent(m -> {
//            m.update(Member.builder()
//                    .password("5678")
//                    .build());
//            memberRepository.save(m);
//        });
//
//        Optional<Member> updatedMember = memberRepository.findByUsername("test1");
//        updatedMember.ifPresent(m -> {
//            Assertions.assertThat(m.getPassword()).isEqualTo("5678");
//        });
//    }


}
