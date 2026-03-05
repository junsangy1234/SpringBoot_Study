package post.post_spring.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import post.post_spring.domain.Post;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemoryPostRepositoryTest {

    MemoryPostRepository memoryPostRepository = new MemoryPostRepository();

    @AfterEach
    public void afterEach(){memoryPostRepository.ClearStore();}

    @Test
    void delete() {
        //given
        Post post = new Post();
        memoryPostRepository.save(post);
        Long postId = post.getId();
        //when
        memoryPostRepository.delete(post.getId());
        //then
        Optional<Post> result = memoryPostRepository.findById(postId);
        org.assertj.core.api.Assertions.assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findById() {
        //given
        Post post = new Post();
        memoryPostRepository.save(post);
        //when
        java.util.Optional<Post> result = memoryPostRepository.findById(post.getId());
        //then
        org.assertj.core.api.Assertions.assertThat(result.isPresent()).isTrue(); //값이 존재 True
        org.assertj.core.api.Assertions.assertThat(result.get().getTitle()).isEqualTo(post.getTitle());

    }

//    @Test
//    void findBytitle() {
//        //given
//        Post post = new Post();
//        memoryPostRepository.save(post);
//        //when
//        java.util.Optional<Post> result = memoryPostRepository.findBytitle("test");
//        //then
//        org.assertj.core.api.Assertions.assertThat(result.isPresent()).isTrue(); //값 존재하는지 부터 체크
//
//        org.assertj.core.api.Assertions.assertThat(result.get()).isEqualTo(post); //객체 비교
//
//        org.assertj.core.api.Assertions.assertThat(result.get().getTitle()).isEqualTo("test"); //제목 재확인
//    }

    @Test
    void findAll() {
        //given
        Post post1 = new Post();
        Post post2 = new Post();
        memoryPostRepository.save(post1);
        memoryPostRepository.save(post2);
        //when
        List<Post> result = memoryPostRepository.findAll();
        //then
        org.assertj.core.api.Assertions.assertThat(result.size()).isEqualTo(2);
    }

}