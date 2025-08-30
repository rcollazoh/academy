package cu.academy.email;

import org.springframework.core.io.InputStreamSource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class MultipartFileInputStreamSource implements InputStreamSource {
    private final MultipartFile file;
    public MultipartFileInputStreamSource(MultipartFile file) {
        this.file = file;
    }
    @Override
    public InputStream getInputStream() throws IOException {
        return file.getInputStream(); // Devuelve un nuevo InputStream cada vez que se llama
    }
}
