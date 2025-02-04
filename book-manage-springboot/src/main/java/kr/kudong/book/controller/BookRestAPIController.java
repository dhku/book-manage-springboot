package kr.kudong.book.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.kudong.book.dto.BookDto;
import kr.kudong.book.entity.BookEntity;
import kr.kudong.book.service.BookService;

//http://localhost:8080/swagger-ui/index.html 에서 테스트
@RestController
@RequestMapping("/api")
public class BookRestAPIController
{
	@Autowired
	private BookService service;
	
    // 목록 조회
    @GetMapping("/book")
    public ResponseEntity<Object> openBoardList() throws Exception {
    	List<BookDto> results = new ArrayList<>();
    	
    	try {
    		List<BookEntity> boardList = service.selectAllBook();
    		boardList.forEach(dto -> results.add(new ModelMapper().map(dto, BookDto.class)));
    		return new ResponseEntity<>(results,HttpStatus.OK);
    	}
    	catch (Exception e)
    	{
    		return new ResponseEntity<>("목록 조회 실패",HttpStatus.INTERNAL_SERVER_ERROR);
    		//return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    	}
        //return ResponseEntity.ok(results);
    }
	
    // 저장 처리
    @PostMapping(value = "/book", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	private ResponseEntity<Object> createBook(@RequestParam("book") String bookData,MultipartHttpServletRequest request) throws Exception
	{
        ObjectMapper objectMapper = new ObjectMapper();
        BookEntity bookEntity = objectMapper.readValue(bookData, BookEntity.class);
        Map<String, String> result = new HashMap<>();
        try {
        	this.service.createBook(bookEntity,request);
            result.put("message", "게시판 저장 성공");
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch(Exception e) {
            result.put("message", "도서 등록 실패");
            result.put("description", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);            
        }
	}
    
    // 상세 조회
    @GetMapping("/book/{boardIdx}")
    public ResponseEntity<Object> openBoardDetail(@PathVariable("bookId") int bookId) throws Exception {
        BookEntity bookEntity = this.service.selectBook(bookId);
        if (bookEntity == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", HttpStatus.NOT_FOUND.value());
            result.put("name", HttpStatus.NOT_FOUND.name());
            result.put("message", "도서 번호 " + bookId + "와 일치하는 도서가 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(bookEntity);
        }
    }
	
    // 수정 처리
	@PutMapping("/book/{bookId}")
    public void updateBook(@PathVariable("bookId") int bookId, @RequestBody BookEntity bookEntity) throws Exception {
		bookEntity.setBookId(bookId);
		this.service.updateBook(bookEntity, null);
    }  
	
	@DeleteMapping("/book/{bookId}")
	private void deleteBook(@PathVariable("bookId") int bookId) throws Exception
	{
		this.service.deleteBook(bookId);
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
