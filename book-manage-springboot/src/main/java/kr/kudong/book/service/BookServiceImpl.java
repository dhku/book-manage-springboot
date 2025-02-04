package kr.kudong.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.kudong.book.common.FileUtils;
import kr.kudong.book.entity.BookEntity;
import kr.kudong.book.entity.BookFileEntity;
import kr.kudong.book.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class BookServiceImpl implements BookService
{
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public List<BookEntity> selectAllBook()
	{
		return this.bookRepository.findAllByOrderByBookId();
	}
	
	@Override
	public List<BookEntity> selectBookByKeyword(String search)
	{
		return this.bookRepository.findByTitleLike(search);
	}
	
	@Override
	public void deleteBook(int id)
	{
		this.bookRepository.deleteById(id);
	}
	
	@Override
	public BookEntity selectBook(int id)
	{
		return this.bookRepository.findById(id).orElse(null);
	}

	@Override
	public void updateBook(BookEntity bookEntity,MultipartHttpServletRequest request)
	{
		try
		{
			//첨부파일을 디스크에 저장하고, 첨부파일 정보를 반환
			List<BookFileEntity> fileInfoList = fileUtils.parseFileInfo(bookEntity.getBookId(), request);
			if(!CollectionUtils.isEmpty(fileInfoList)) {
				bookEntity.setBookImage(fileInfoList);
			}
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		
		this.bookRepository.save(bookEntity);
	}

	@Override
	public void createBook(BookEntity bookEntity,MultipartHttpServletRequest request)
	{
		try
		{
			//첨부파일을 디스크에 저장하고, 첨부파일 정보를 반환
			List<BookFileEntity> fileInfoList = fileUtils.parseFileInfo(bookEntity.getBookId(), request);
			if(!CollectionUtils.isEmpty(fileInfoList)) {
				bookEntity.setBookImage(fileInfoList);
			}
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		
		this.bookRepository.save(bookEntity);
	}

	@Override
	public List<BookFileEntity> selectBookImage(int bookId)
	{
		return this.bookRepository.findBookFile(bookId);
	}

	@Override
	public void deleteBookImage(int bookId)
	{
		BookEntity e = this.selectBook(bookId);
		if(e != null)
		{
			e.getBookImage().clear();
			this.bookRepository.save(e);
		}
	}
}
