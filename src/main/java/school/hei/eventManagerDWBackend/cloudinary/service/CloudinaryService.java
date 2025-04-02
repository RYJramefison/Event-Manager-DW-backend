package school.hei.eventManagerDWBackend.cloudinary.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import school.hei.eventManagerDWBackend.cloudinary.response.CloudinaryResponse;

import java.io.IOException;
import java.util.Map;
@RequiredArgsConstructor
@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

//    @Transactional
    public CloudinaryResponse uploadFile(MultipartFile file, String fileName) {
        try {
            final Map result = cloudinary.uploader()
                    .upload(file.getBytes(),
                            Map.of("public_id", "nhmdev/product/"+ fileName));

            final String url = (String) result.get("secure_url");
            final String publicId = (String) result.get("public_id");
            return CloudinaryResponse.builder().publicId(publicId).url(url).build();

        } catch (IOException e) {
            throw new RuntimeException("failed to upload file "+ e);
        }
    }
}
