package post.post_spring;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import post.post_spring.repository.JpaPostRepository;
import post.post_spring.repository.MemoryPostRepository;
import post.post_spring.repository.PostRepository;
import post.post_spring.service.PostService;

@Configuration
public class SpringConfig {

    @PersistenceContext
    private EntityManager em;

    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public PostRepository postRepository(){
        //return new MemoryPostRepository();
        return new JpaPostRepository(em);
    }

    @Bean PostService postService(PostRepository postRepository){
        return new PostService(postRepository);
    }
}
