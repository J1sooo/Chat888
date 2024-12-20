package col.carrot.back.post;

import col.carrot.back.user.userlogin.domain.UserEntity;
import col.carrot.back.user.userlogin.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    final private PostRepository postRepository;
    final private FileUploadService fileUploadService;
    final private UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, FileUploadService fileUploadService, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.fileUploadService = fileUploadService;
        this.userRepository = userRepository;
    }

    public PostEntity createPost(String title, String content, Integer price, MultipartFile file, String userId) throws IOException {
        PostEntity post = new PostEntity();
        post.setTitle(title);
        post.setContent(content);
        post.setPrice(price);
        post.setImageUrl(null);

        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        post.setUser(user);

        if (file != null && !file.isEmpty()) {
            String bucketName = "chat888";
            String imageUrl = fileUploadService.uploadFile(file, bucketName);
            post.setImageUrl(imageUrl);
        }

        return postRepository.save(post);
    }

    public List<PostEntity> allPost() {
        return postRepository.findAll();
    }

    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

    public Optional<PostEntity> findPost(Integer id) {
        return postRepository.findById(id);
    }

    public PostEntity modifyPost(Integer id, String title, String content, Integer price, MultipartFile file) throws IOException {
        PostEntity postDto = new PostEntity();
        postDto.setTitle(title);
        postDto.setContent(content);
        postDto.setPrice(price); 

        if (file != null && !file.isEmpty()) {
            String bucketName = "chat888";
            String imageUrl = fileUploadService.uploadFile(file, bucketName);
            postDto.setImageUrl(imageUrl);
        }

        return postRepository.findById(id)
                .map(existingPost -> {
                    existingPost.setTitle(postDto.getTitle());
                    existingPost.setContent(postDto.getContent());
                    existingPost.setImageUrl(postDto.getImageUrl());
                    existingPost.setPrice(postDto.getPrice());
                    return postRepository.save(existingPost);
                })
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. id: " + id));
    }

    public List<PostEntity> searchPosts(String keyword) {
        return postRepository.searchByKeyword(keyword);
    }
}
