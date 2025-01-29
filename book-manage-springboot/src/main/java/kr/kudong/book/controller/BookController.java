package kr.kudong.book.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController
{
	@RequestMapping("/")
	private String main()
	{
		return "Hello World!!!";
	}
}
