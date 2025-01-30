package kr.kudong.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.kudong.book.dto.BookDto;
import kr.kudong.book.mapper.BookMapper;


public interface BookService
{
	public List<BookDto> selectAllBook();
	public List<BookDto> selectBookByKeyword(String search);
	public void removeBook(int num);
	public BookDto selectBook(int id);
	public void updateBook(BookDto dto);
	public void createBook(BookDto dto);
}
