package school.hei.eventManagerDWBackend.cloudinary.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
         final Map <String, String> config = new HashMap<>();
         config.put("cloud_name", "dkgovlztl");
         config.put("api_key", "966667138843588");
         config.put("api_secret", "YI-Gxb0Fmm9vvKTOnIM6z297PtI");
         return new Cloudinary(config);
    }
}
