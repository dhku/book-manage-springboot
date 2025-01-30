package kr.kudong.book.dto;

import lombok.Data;

@Data
public class BookDto
{
	private int bookId;
	private String title;
	private String author;
	private String publisher;
	private String publishedDate;
	private String genres;
	private String isbn;
	private String description;
	private String createdAt;
	private String updatedAt;
}
