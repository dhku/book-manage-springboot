package kr.kudong.book.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.kudong.book.entity.BookEntity;
import kr.kudong.book.entity.BookFileEntity;


public interface BookService
{
	public List<BookEntity> selectAllBook();
	public List<BookEntity> selectBookByKeyword(String search);
	public void deleteBook(int num);
	public BookEntity selectBook(int id);
	public void updateBook(BookEntity dto, MultipartHttpServletRequest request);
	public void createBook(BookEntity dto, MultipartHttpServletRequest request);
	
	public List<BookFileEntity> selectBookImage(int bookId);
	public void deleteBookImage(int bookId);
}
