package com.example.votogether.image;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final FileService fileService;

    public List<PostOptionResponse> createPost(PostCreateRequest request) {
        System.out.println(request);
        return request.getPostOptions().stream()
                .map(this::convertToPostOptionResponse)
                .toList();
    }

    private PostOptionResponse convertToPostOptionResponse(PostOptionCreateRequest postOption) {
        String imageUrl = fileService.save(postOption.getImage());
        return new PostOptionResponse(postOption.getContent(), imageUrl);
    }
}
