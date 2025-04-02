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
         config.put("api_key", "777373725158756");
         config.put("api_secret", "fzHZUf6zKK66QSU15CI0iGwYZfo");
         return new Cloudinary(config);
    }
}
