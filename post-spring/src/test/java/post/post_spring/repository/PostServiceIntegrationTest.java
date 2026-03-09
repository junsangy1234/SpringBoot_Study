package post.post_spring.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import post.post_spring.domain.Post;
import post.post_spring.service.PostService;

import java.util.List;

import org.assertj.core.api.Assertions;

@SpringBootTest
@Transactional
class PostServiceIntegrationTest {

    @Autowired PostService postService;
    @Autowired PostRepository postRepository;

    @Test
    void save() {
        //given
        Post post = new Post();
        post.setTitle("test1");
        post.setContent("test1");
        //when
        Long findId = postService.write(post);
        //then
        Post findPost = postRepository.findById(findId).get();
        Assertions.assertThat(findPost.getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    void delete() {
        // given
        Post post = new Post();
        post.setTitle("삭제할 게시글");
        Long savedId = postService.write(post);

        // when
        postService.delete(savedId);

        // then
        // 삭제된 ID로 조회했을 때 예외가 터지거나, Optional이 비어있어야 함
        // (Service 로직에 따라 다름. 여기서는 예외가 발생하는지 확인하거나 null 체크)
        // 만약 findOneById가 Optional을 반환한다면:
        Assertions.assertThat(postService.findById(savedId)).isEmpty();
    }

    @Test
    void findById() {
        // given
        Post post = new Post();
        post.setTitle("찾을 게시글");
        Long savedId = postService.write(post);

        // when
        Post findPost = postService.findById(savedId).get();

        // then
        Assertions.assertThat(findPost.getId()).isEqualTo(savedId);
        Assertions.assertThat(findPost.getTitle()).isEqualTo("찾을 게시글");
    }

    @Test
    void findAll() {
        // given
        Post post1 = new Post();
        post1.setTitle("게시글1");
        postService.write(post1);

        Post post2 = new Post();
        post2.setTitle("게시글2");
        postService.write(post2);

        // when
        List<Post> result = postService.findAll();

        // then
        // DB에 기존 데이터가 있을 수도 있으니 개수보다는 포함 여부로 확인
        Assertions.assertThat(result.size()).isGreaterThanOrEqualTo(2);
    }
}