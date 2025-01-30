package kr.kudong.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.kudong.book.dto.BookDto;
import kr.kudong.book.mapper.BookMapper;

@Service
public class BookServiceImpl implements BookService
{
	@Autowired
	private BookMapper mapper;
	
	@Override
	public List<BookDto> selectAllBook()
	{
		return this.mapper.selectBookList();
	}
	
	@Override
	public List<BookDto> selectBookByKeyword(String search)
	{
		return this.mapper.selectBookListByKeyword(search);
	}
	
	@Override
	public void removeBook(int num)
	{
		this.mapper.removeBook(num);
	}
	
	@Override
	public BookDto selectBook(int id)
	{
		return this.mapper.selectBook(id);
	}

	@Override
	public void updateBook(BookDto dto)
	{
		this.mapper.updateBook(dto);
	}

	@Override
	public void createBook(BookDto dto)
	{
		this.mapper.insertBook(dto);
	}
}
