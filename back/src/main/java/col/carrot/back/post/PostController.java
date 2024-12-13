package col.carrot.back.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("")
public class PostController {
    final private PostService postService;
    final private FileUploadService fileUploadService;

    public PostController(PostService postService, FileUploadService fileUploadService) {
        this.postService = postService;
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/createPost")
public ResponseEntity<?> createPost(
        @RequestParam("title") String title,
        @RequestParam("content") String content,
        @RequestParam("price") Integer price,
        @RequestParam(value = "file", required = false) MultipartFile file) {
    try {
        PostEntity savedPost = postService.createPost(title, content,price, file);
        return ResponseEntity.ok(savedPost);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("게시물 생성 실패: " + e.getMessage());
    }
}

    @GetMapping("/postAll")
    public List<PostEntity> allPost() {
        return postService.allPost();
    }

    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
    }

    @GetMapping("/findPost/{id}")
    public Optional<PostEntity> findPost(@PathVariable Integer id) {
        return postService.findPost(id);
    }
@PutMapping("/modify/{id}")
public ResponseEntity<PostEntity> modifyPost(
    @PathVariable Integer id,
    @RequestParam("title") String title,
    @RequestParam("content") String content,
    @RequestParam("price") Integer price,
    @RequestParam(value = "file", required = false) MultipartFile file
) {
    try {
        PostEntity modifiedPost = postService.modifyPost(id, title, content, price, file);
        return ResponseEntity.ok(modifiedPost);
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
}

