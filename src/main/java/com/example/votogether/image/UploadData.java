package com.example.votogether.image;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadData {

    private String content;
    private MultipartFile multipartFile;

    @Override
    public String toString() {
        return "UploadData{" +
                "content='" + content + '\'' +
                ", multipartFile=" + multipartFile +
                '}';
    }
}
