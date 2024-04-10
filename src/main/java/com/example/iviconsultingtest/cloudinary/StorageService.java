package com.example.iviconsultingtest.cloudinary;

import com.example.iviconsultingtest.cloudinary.dto.CloudiaryResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    CloudiaryResponseDto uploadImage(MultipartFile file) throws IOException;

}
