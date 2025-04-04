package school.hei.eventManagerDWBackend.cloudinary.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUploadUtil {
    public  static final long MAX_FILE_SIZE = 8 * 1024 * 1024;

    public static final String IMAGE_PATTERN = ".*";

    public static final String DATE_FORMAT = "yyyyMMddHHmmss";

    public static final String FILE_NAME_FORMAT = "%s-%s";

    public static boolean isAllowedExtension(final String fileName, final String pattern) {
        final Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(fileName);
        return matcher.matches();
    }

    public static void assertAllowed(MultipartFile file, String pattern){
        final long size = file.getSize();
        if (size > MAX_FILE_SIZE){
            throw new RuntimeException("Max file size is 8MB");
        }
        final String fileName = file.getOriginalFilename();
        final String extension = FilenameUtils.getExtension(fileName);
        if (!isAllowedExtension(fileName,pattern)) {
            throw new RuntimeException("Only jpg, png, gif bmp files are allowed");
        }
    }

    public static String getFileName(final String name){
        final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        final String date = dateFormat.format(System.currentTimeMillis());
        return String.format(FILE_NAME_FORMAT, name,  date);
    }

}
