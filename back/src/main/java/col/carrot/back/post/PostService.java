package col.carrot.back.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    final private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostEntity createPost(PostEntity postDto) {
        // DTO를 엔티티로 변환
        PostEntity post = new PostEntity();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        return postRepository.save(post);
    }
    public List<PostEntity> allPost(){
        return postRepository.findAll();
    }
    public void deletePost(Integer id){
        postRepository.deleteById(id);
    }
}
