package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {


    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     회원가입
     */
    public Long join(Member member){
        //같은 이름이 있는 중복회원 X
        vaildateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    //ctrl+T 누른후 매서드 추출로 짠 코드 함수화 시킬수 있다.
    private void vaildateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {//값이 있으면?
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }


    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
