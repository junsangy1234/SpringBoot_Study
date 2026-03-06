package post.post_spring.service;

import post.post_spring.domain.Post;
import post.post_spring.repository.PostRepository;

import java.util.List;
import java.util.Optional;


public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Long write(Post post){
        postRepository.save(post);

        return post.getId();
    }

    public void edit(Long id, String title, String content){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물은 없습니다."));

        if(post.getTitle() != null && !post.getTitle().trim().isEmpty()){
            post.setTitle(title);
        }
        if(post.getContent() != null && !post.getContent().trim().isEmpty()){
            post.setContent(content);
        }
    }

    public void delete(Long id){
        postRepository.delete(id);
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public Optional<Post> findById(Long id){
        return postRepository.findById(id);
    }
}
