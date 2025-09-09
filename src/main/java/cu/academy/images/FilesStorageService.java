package cu.academy.images;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public interface FilesStorageService {

    Path rootPath = Paths.get("files");

    void init();

    String save(MultipartFile file, String pathAndName);

    Resource load(String filename);

    byte[] getFile(String imageName);

    void deleteAll();

    void delete(String type, String filename);

    Stream<Path> loadAll();

    public List<String> getFilesInsideTravelFolder(String folderName);

    String getExtension(MultipartFile file);

}
