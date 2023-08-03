package com.example.votogether.image;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private static final String ROOT_PATH = "static";
    private static final String IMAGE_PATH = "images";
    private static final String IMAGE_FULL_PATH =
            System.getProperty("user.dir") + File.separator + ROOT_PATH + File.separator + IMAGE_PATH + File.separator;
    private static final String IMAGE_REQUEST_PATH =
            ROOT_PATH + File.separator + IMAGE_PATH + File.separator;

    static {
        try {
            Files.createDirectories(Paths.get(IMAGE_FULL_PATH));
        } catch (IOException ignore) {
            throw new IllegalStateException("디렉토리 생성 도중 에러가 발생하였습니다.");
        }
    }

    public String save(MultipartFile image) {
        try {
            String fileFullPath = generateFileFullPath(image);
            String filePath = generateFilePath(image);
            validateFilePathLength(fileFullPath);
            validateImageFile(image);
            image.transferTo(new File(fileFullPath));
            return filePath;
        } catch (IOException e) {
            throw new IllegalStateException("사진 저장에 실패하였습니다.");
        }
    }

    private String generateFileFullPath(MultipartFile image) {
        return IMAGE_FULL_PATH + image.getOriginalFilename();
    }

    private String generateFilePath(MultipartFile image) {
        return IMAGE_REQUEST_PATH + image.getOriginalFilename();
    }

    private void validateFilePathLength(String fileFullPath) {
        if (fileFullPath.length() > 512) {
            throw new IllegalStateException("너무 긴 파일 이름은 사용할 수 없습니다.");
        }
    }

    private void validateImageFile(MultipartFile image) {
        try {
            InputStream originInputStream = new BufferedInputStream(image.getInputStream());
            if (ImageIO.read(originInputStream) == null) {
                throw new IllegalStateException("해당 파일은 이미지 파일이 아닙니다.");
            }
        } catch (IOException e) {
            throw new IllegalStateException("이미지 여부 검증 도중 에러가 발생하였습니다.");
        }
    }
}
