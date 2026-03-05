package post.post_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import post.post_spring.domain.Post;
import post.post_spring.form.PostForm;
import post.post_spring.service.PostService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/post")
    public String post(Model model){

        List posts = new ArrayList(postService.findAll());
        model.addAttribute("posts", posts);

        return "post";
    }

    @GetMapping("/post/createForm")
    public String createForm(Model model){
        return "post/createForm";
    }

    @PostMapping("/post/createForm")
    public String create(PostForm form){
        Post post = new Post();

        post.setTitle(form.getTitle());
        post.setContent(form.getContent());

        postService.write(post);

        return "redirect:/post";
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        Post post = postService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        model.addAttribute("post", post);

        return "post/detail";
    }

    @GetMapping("/post/{id}/delete")
    public String delete(@PathVariable("id") Long id){
        postService.delete(id);

        return "redirect:/post";
    }

    @GetMapping("/post/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model){
        Post post = postService.findById(id).get();
        model.addAttribute("post", post);

        return "post/editForm";
    }

    @PostMapping("/post/{id}/edit")
    public String edit(@PathVariable("id") Long id, PostForm form){
        postService.edit(id, form.getTitle(), form.getContent());

        return "redirect:/post";
    }
}
