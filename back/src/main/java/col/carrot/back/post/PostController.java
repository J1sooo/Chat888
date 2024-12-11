package col.carrot.back.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5174")
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
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("파일이 비어있습니다.");
            }
            String bucketName = "chat888"; // application.properties에서 설정한 버킷 이름과 동일해야 함
            String result = fileUploadService.uploadFile(file, bucketName);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace(); // 콘솔에 상세한 에러 로그 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("파일 업로드 실패: " + e.getMessage());
        }
    }
}
