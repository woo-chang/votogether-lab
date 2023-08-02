package com.example.votogether.image;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostOptionCreateRequest {

    private String content;
    private MultipartFile image;

    @Override
    public String toString() {
        return "PostOptionCreateRequest{" +
                "content='" + content + '\'' +
                ", image=" + image.getOriginalFilename() +
                '}';
    }
}
