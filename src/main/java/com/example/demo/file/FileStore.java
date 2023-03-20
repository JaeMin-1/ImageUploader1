package com.example.demo.file;

import com.example.demo.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles)
            throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException
    {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();          //오리지널 파일 이름
        String storeFileName = createStoreFileName(originalFilename);           //오리지널이름에서 확장자를 받아서 UUID에 붙인 새로운 파일 이름
        multipartFile.transferTo(new File(getFullPath(storeFileName)));         //파일들의 이름을 새로 만든 파일 이름으로 변경하여 실제 폴더에 저장
        return new UploadFile(storeFileName);                                   //uploadFile 클래스에 넘겨줌
    }

    //오리지널 파일 이름에서 마지막 확장자만 가져와서 UUID에 붙여서 새로운 store 파일 이름 생성
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    //확장자를 오리지널 파일 이름에서 추출하는 과정
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
