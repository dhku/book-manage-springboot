package kr.kudong.book.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.kudong.book.dto.BookDto;
import kr.kudong.book.dto.BookFileDto;

@Mapper
public interface BookMapper
{
	List<BookDto> selectBookList();
	List<BookDto> selectBookListByKeyword(String keyword);
	BookDto selectBook(int id);
	void removeBook(int bookId);
	void updateBook(BookDto dto);
	void insertBook(BookDto dto);
	void insertBookFileList(List<BookFileDto> fileInfoList);
	
	List<BookFileDto> selectBookFileList(int bookId);
	void removeBookFileList(int bookId);
}
