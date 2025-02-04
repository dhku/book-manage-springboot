package kr.kudong.book.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_book")
@Data
@DynamicUpdate
public class BookEntity
{
	@Id //기본키에 해당
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookId;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String author;
	
	@Column(nullable = false)
	private String publisher;
	
	@Column(nullable = false)
	private String publishedDate;
	
	@Column(nullable = false)
	private String genres;
	
	@Column(nullable = false)
	private String isbn;
	
	private String description;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private String createdAt;
	
	@UpdateTimestamp
	private String updatedAt;
	
    //LAZY 부모 엔티티(Board)는 조회할때 자식엔티티(BoardFileEntity)는 조회 안함
    //EAGER는 함께 가져옴
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    @JsonManagedReference
	private List<BookFileEntity> bookImage;
}
