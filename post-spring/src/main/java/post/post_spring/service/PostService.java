package post.post_spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import post.post_spring.domain.Post;
import post.post_spring.repository.MemoryPostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    MemoryPostRepository memoryPostRepository;

    @Autowired
    public PostService(MemoryPostRepository memoryPostRepository) {
        this.memoryPostRepository = memoryPostRepository;
    }

    public Long write(Post post){
        memoryPostRepository.save(post);

        return post.getId();
    }

    public void edit(Long id, String title, String content){
        Post post = memoryPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물은 없습니다."));

        if(post.getTitle() != null && !post.getTitle().trim().isEmpty()){
            post.setTitle(title);
        }
        if(post.getContent() != null && !post.getContent().trim().isEmpty()){
            post.setContent(content);
        }
    }

    public void delete(Long id){
        memoryPostRepository.delete(id);
    }

    public List<Post> findAll(){
        return memoryPostRepository.findAll();
    }

    public Optional<Post> findById(Long id){
        return memoryPostRepository.findById(id);
    }
}
