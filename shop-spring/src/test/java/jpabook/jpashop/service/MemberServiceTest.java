package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    void join() {
        //given
        Member member = Member.createMember("Kim", new Address("test", "test", "test"));

        //when
        Long id = memberService.join(member);

        //then
        Assertions.assertThat(id).isEqualTo(member.getId());

        Member findMember = memberRepository.findOne(id).get();
        Assertions.assertThat(member).isEqualTo(findMember);
    }

    @Test
    void validateDuplicateMember() {
        //given
        Member member1 = Member.createMember("Kim", new Address("test", "test", "test"));
        Member member2 = Member.createMember("Kim", new Address("test", "test", "test"));


        //when
        memberService.join(member1);

        //then
        Assertions.assertThatThrownBy(() -> memberService.join(member2))
                .isInstanceOf((IllegalStateException.class))
                .hasMessage("이미 존재하는 회원 입니다.");

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}