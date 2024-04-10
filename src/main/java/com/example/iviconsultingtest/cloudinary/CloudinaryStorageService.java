package com.example.iviconsultingtest.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.iviconsultingtest.cloudinary.dto.CloudiaryResponseDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class CloudinaryStorageService  implements StorageService{

    @Autowired
    private Cloudinary cloudinaryConfig;

    @Override
    public CloudiaryResponseDto uploadImage(MultipartFile file) throws IOException {
        final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File uploadedFile = convertMultiPartToFile(file);
        var result = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
        uploadedFile.delete();
        return mapper.convertValue(result, CloudiaryResponseDto.class);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


}
