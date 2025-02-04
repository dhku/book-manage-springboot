package kr.kudong.book.entity;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_book_file")
@Data
@DynamicUpdate
public class BookFileEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
    private int idx;
	
	@Column(nullable = false)
    private String originalFileName;
	
	@Column(nullable = false)
    private String storedFilePath;
	
	@Column(nullable = false)
    private String fileSize;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	@JsonBackReference
    private BookEntity book;
}
