package col.carrot.back.post;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class PostController {
    final private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/createPost")
    public PostEntity createPost(@RequestBody PostEntity Post){
        return postService.createPost(Post);
    }
    @GetMapping("/postAll")
    public List<PostEntity> allPost(){
        return postService.allPost();
    }
    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable Integer id){
        postService.deletePost(id);
    }
}
