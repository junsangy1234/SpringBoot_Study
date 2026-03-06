package post.post_spring.repository;

import org.springframework.stereotype.Repository;
import post.post_spring.domain.Post;

import java.util.*;

@Repository
public class MemoryPostRepository implements PostRepository {
    private static long sequence = 0L;
    private static Map<Long, Post> store = new HashMap<>();

    @Override
    public Post save(Post post) {
        post.setId(++sequence);
        store.put(post.getId(), post);


        //Git test
        return post;
    }

    @Override
    public void delete(Long id) {
        if (store.get(id) != null){
            store.remove(id);
        }
    }

    @Override
    public Optional<Post> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Post> findByTitle(String title) {
        return store.values().stream()
                .filter(post -> post.getTitle().equals(title))
                .findAny();
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(store.values());
    }

    public void ClearStore(){
        store.clear();
    }
}
