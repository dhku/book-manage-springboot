package kr.kudong.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import kr.kudong.book.entity.BookEntity;
import kr.kudong.book.entity.BookFileEntity;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Integer>
{
	// 쿼리 메소드 <= 우리가 지정가능 
	List<BookEntity> findAllByOrderByBookId();
	
	List<BookEntity> findByTitleLike(String title);
	
	// @Query 어노테이션으로 실행할 쿼리를 직접 정의 JPQL문 : 붙은건 매개변수임
	@Query("SELECT file FROM BookFileEntity file WHERE file.book.bookId = :bookId")
	List<BookFileEntity> findBookFile(@Param("bookId") int bookId);
}
