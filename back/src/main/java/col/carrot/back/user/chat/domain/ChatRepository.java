package col.carrot.back.user.chat.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<ChatEntity, Long> {
    public Iterable<ChatEntity> findAll(Sort sort);
}
