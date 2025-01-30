package kr.kudong.book.dto;

import lombok.Data;

@Data
public class BookFileDto
{
    private int idx;
    private int bookId;
    private String originalFileName;
    private String storedFilePath;
    private String fileSize;
}
