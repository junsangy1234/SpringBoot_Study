package post.post_spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import post.post_spring.repository.MemoryPostRepository;
import post.post_spring.repository.PostRepository;
import post.post_spring.service.PostService;

@Configuration
public class SpringConfig {

    @Bean
    public PostRepository postRepository(){
        return new MemoryPostRepository();
    }

    @Bean PostService postService(PostRepository postRepository){
        return new PostService(postRepository);
    }
}
