package com.example.votogether.image;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequest {

    private List<Integer> categoryIds;
    private String title;
    private String content;
    // record에서는 동작 X
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime deadline;
    private List<MultipartFile> images;
    private List<PostOptionCreateRequest> postOptions;

    @Override
    public String toString() {
        String images = this.images.stream()
                .map(MultipartFile::getOriginalFilename)
                .collect(Collectors.joining(", "));

        return "PostCreateRequest{" +
                "categoryIds=" + categoryIds +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", deadline=" + deadline +
                ", images=" + images +
                ", postOptions=" + postOptions +
                '}';
    }
}
