package kr.kudong.book.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.kudong.book.dto.BookDto;
import kr.kudong.book.dto.BookFileDto;
import kr.kudong.book.entity.BookEntity;
import kr.kudong.book.entity.BookFileEntity;
import kr.kudong.book.service.BookService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BookController
{
	@Autowired
	private BookService service;
	
	@GetMapping("/")
	private String openBookMain()
	{
		return "redirect:/book/search";
	}
	
	//도서 관리 페이지로 이동합니다.
	@GetMapping("/book/search")
	private ModelAndView openBookSearch(@RequestParam(value = "keyword", required = false) String keyword)
	{
		ModelAndView mv = new ModelAndView("/book/book_search");
		List<BookEntity> list;
		if (keyword == null || keyword.trim().isEmpty()) {
	        list = service.selectAllBook();  // 검색어 없을 때 모든 책 조회
	    } else {
	        list = service.selectBookByKeyword("%" + keyword + "%");  // 검색어 있을 때 해당 책 조회
	    }
		
		mv.addObject("bookList",list);
		return mv;
	}
	
	//도서 등록 페이지로 이동합니다.
	@GetMapping("/book/create")
	private ModelAndView openBookCreate()
	{
		ModelAndView mv = new ModelAndView("/book/book_create");
		return mv;
	}
	
	//Form 요청으로 들어온것을 처리
	@PostMapping("/book/create")
	private String createBook(BookEntity entity,MultipartHttpServletRequest request) throws Exception
	{
		this.service.createBook(entity,request);
		return "redirect:/book/search";
	}
	
	//도서 상세 페이지를 엽니다. id는 bookId
	@GetMapping("/book/detail/{bookId}")
	private ModelAndView openBookDetail(@PathVariable("bookId") int bookId)
	{
		ModelAndView mv = new ModelAndView("/book/book_detail");
		BookEntity list = service.selectBook(bookId);
		List<BookFileEntity> dtoList = service.selectBookImage(bookId);

		mv.addObject("book",list);
		if(!dtoList.isEmpty()) 
		{
			//인덱스 0만 빼고 다 지우기
			if (dtoList.size() > 1) {
				dtoList.subList(1, dtoList.size()).clear();
	        }
			
			//http://localhost:8080/image/2025-01-30/sample.jpg
			BookFileEntity dto = dtoList.get(0);	
			String path = dto.getStoredFilePath().substring(18, dto.getStoredFilePath().length());
			dto.setOriginalFileName(path);
			path = "http://localhost:8080/image/" + path;
			dto.setStoredFilePath(path);
		}
		mv.addObject("bookImg",dtoList);
		return mv;
	}
	
	//도서 수정 페이지를 엽니다.
	@PostMapping("/book/detail/update")
	private ModelAndView openUpdateBook(BookEntity entity, @RequestParam(value = "originalFileName",required = false) String file)
	{
		ModelAndView mv = new ModelAndView("/book/book_update");
		mv.addObject("book",entity);
		mv.addObject("bookImage",file);
		return mv;
	}
	
	//들어온 input 태그 (이름 numbers, 타입 checkbox)에서 체크박스에 체크된 value를 가져옵니다.
	@PostMapping("/book/delete/checkbox")
	private String deleteBookByCheckBox(@RequestParam(value="numbers",required = false) List<Integer> numbers)
	{
		if(numbers == null)return "redirect:/book/search";
		
		for(int num : numbers)
		{
			this.service.deleteBook(num);
		}
			
		return "redirect:/book/search";
	}

	//Form 요청으로 들어온것을 처리
	@PutMapping("/book/detail/{bookId}")
	private String updateBook(BookEntity entity, MultipartHttpServletRequest request) throws Exception
	{
		this.service.updateBook(entity, request);
		return "redirect:/book/detail/"+entity.getBookId();
	}
	
	@DeleteMapping("/book/detail/{bookId}")
	private String deleteBook(@PathVariable("bookId") int bookId) throws Exception
	{
		this.service.deleteBook(bookId);
		return "redirect:/book/search";
	}
	
	@GetMapping("/book/test")
	private ModelAndView openBookTest()
	{
		ModelAndView mv = new ModelAndView("/book/book_test");
		return mv;
	}
	
    @GetMapping("/image/{day}/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable("day") String day, @PathVariable("filename") String filename) throws MalformedURLException {

        Path imagePath = Paths.get("C:\\uploads\\images\\"+ day).resolve(filename);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)  // 이미지 형식에 맞게 수정
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
