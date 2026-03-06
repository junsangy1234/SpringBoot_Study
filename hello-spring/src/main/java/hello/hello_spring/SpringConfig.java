package hello.hello_spring;
import hello.hello_spring.repository.JpaMemberRepository;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import hello.hello_spring.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;
@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired //생성자 하나인 경우는 @Autowired 생략 가능하긴 함
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //JpaMemberRepository 사용
    /*@PersistenceContext
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }*/

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    //JpaMemberRepository 사용
    /*@Bean
    public MemberRepository memberRepository() {
        //return new MemoryMemberRepository();
        //return new JpaMemberRepository(em);
    }*/
}