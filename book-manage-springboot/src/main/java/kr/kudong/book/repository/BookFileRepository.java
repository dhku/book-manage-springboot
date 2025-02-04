package kr.kudong.book.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import kr.kudong.book.entity.BookFileEntity;

@Repository
public interface BookFileRepository extends CrudRepository<BookFileEntity, Integer>
{

}
