package com.example.votogether.image;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFormData {

    private String content;
    private List<UploadData> options;

    @Override
    public String toString() {
        return "UploadFormData{" +
                "content='" + content + '\'' +
                ", options=" + options +
                '}';
    }
}
