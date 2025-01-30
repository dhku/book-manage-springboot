package kr.kudong.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.kudong.book.dto.BookDto;
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
		return "redirect:/book_search";
	}
	
	//도서 관리 페이지로 이동합니다.
	@GetMapping("/book_search")
	private ModelAndView openBookSearch(@RequestParam(value = "keyword", required = false) String keyword)
	{
		ModelAndView mv = new ModelAndView("/book/book_search");
		List<BookDto> list;
		if (keyword == null || keyword.trim().isEmpty()) {
	        list = service.selectAllBook();  // 검색어 없을 때 모든 책 조회
	    } else {
	        list = service.selectBookByKeyword(keyword);  // 검색어 있을 때 해당 책 조회
	    }
		mv.addObject("bookList",list);
		return mv;
	}
	
	//도서 등록 페이지로 이동합니다.
	@GetMapping("/book_create")
	private ModelAndView openBookCreate()
	{
		ModelAndView mv = new ModelAndView("/book/book_create");
		return mv;
	}
	
	//도서 상세 페이지를 엽니다.
	@GetMapping("/book_detail")
	private ModelAndView openBookDetail(@RequestParam("id") int id)
	{
		ModelAndView mv = new ModelAndView("/book/book_detail");
		BookDto list = service.selectBook(id);
		mv.addObject("book",list);
		return mv;
	}
	
	//도서 수정 페이지를 엽니다.
	@PostMapping("/openUpdateBook")
	private ModelAndView openUpdateBook(BookDto dto)
	{
		ModelAndView mv = new ModelAndView("/book/book_update");
		dto.setPublishedDate(dto.getPublishedDate().replace(".", "-"));
		mv.addObject("book",dto);
		return mv;
	}
	
	//들어온 input 태그 (이름 numbers, 타입 checkbox)에서 체크박스에 체크된 value를 가져옵니다.
	@PostMapping("/deleteBookByCheckBox.do")
	private String deleteBookByCheckBox(@RequestParam(value="numbers",required = false) List<Integer> numbers)
	{
		if(numbers == null)return "redirect:/book_search";
		
		for(int num : numbers)
		{
			this.service.removeBook(num);
		}
			
		return "redirect:/book_search";
	}

	//Form 요청으로 들어온것을 처리
	@PostMapping("/updateBook.do")
	private String updateBook(BookDto dto)
	{
		this.service.updateBook(dto);
		return "redirect:/book_detail?id="+dto.getBookId();
	}

	//Form 요청으로 들어온것을 처리
	@PostMapping("/createBook.do")
	private String createBook(BookDto dto)
	{
		this.service.createBook(dto);
		return "redirect:/book_search";
	}

	@GetMapping("/book/test")
	private ModelAndView openBookTest()
	{
		ModelAndView mv = new ModelAndView("/book/book_test");
		return mv;
	}
}
