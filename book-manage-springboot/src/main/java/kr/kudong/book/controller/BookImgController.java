package kr.kudong.book.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookImgController
{
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
