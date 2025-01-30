package kr.kudong.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.kudong.book.common.FileUtils;
import kr.kudong.book.dto.BookDto;
import kr.kudong.book.dto.BookFileDto;
import kr.kudong.book.mapper.BookMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookServiceImpl implements BookService
{
	@Autowired
	private BookMapper mapper;
	
	@Autowired
	private FileUtils fileUtils;
	
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
	public void updateBook(BookDto dto,MultipartHttpServletRequest request)
	{
		this.mapper.updateBook(dto);
		this.mapper.removeBookFileList(dto.getBookId());
		
		try
		{
			//첨부파일을 디스크에 저장하고, 첨부파일 정보를 반환
			List<BookFileDto> fileInfoList = fileUtils.parseFileInfo(dto.getBookId(), request);
			if(!CollectionUtils.isEmpty(fileInfoList)) {
				this.mapper.insertBookFileList(fileInfoList);
			}
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
	}

	@Override
	public void createBook(BookDto dto,MultipartHttpServletRequest request)
	{
		this.mapper.insertBook(dto);
		
		try
		{
			//첨부파일을 디스크에 저장하고, 첨부파일 정보를 반환
			List<BookFileDto> fileInfoList = fileUtils.parseFileInfo(dto.getBookId(), request);
			if(!CollectionUtils.isEmpty(fileInfoList)) {
				this.mapper.insertBookFileList(fileInfoList);
			}
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
	}

	@Override
	public List<BookFileDto> selectBookImage(int bookId)
	{
		return this.mapper.selectBookFileList(bookId);
	}

	@Override
	public void removeBookImage(int bookId)
	{
		this.mapper.removeBookFileList(bookId);
	}
}
