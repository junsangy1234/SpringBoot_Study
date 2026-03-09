package post.post_spring.repository;

import jakarta.persistence.EntityManager;
import post.post_spring.domain.Post;

import java.util.List;
import java.util.Optional;

public class JpaPostRepository implements PostRepository{

    private final EntityManager em;

    public JpaPostRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Post save(Post post) {
        em.persist(post);

        return post;
    }

    @Override
    public void delete(Long id) {
        Post post = em.find(Post.class, id);

        if (post != null){
            em.remove(post);
        }
    }

    @Override
    public Optional<Post> findById(Long id) {
        Post post = em.find(Post.class, id);

        return Optional.ofNullable(post);
    }

    @Override
    public Optional<Post> findByTitle(String title) {
        List<Post> result = em.createQuery("SELECT p FROM Post p WHERE p.title = :title",Post.class)
                .setParameter("title", title)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Post> findAll() {
        return em.createQuery("SELECT p FROM Post p", Post.class)
                .getResultList();
    }
}
