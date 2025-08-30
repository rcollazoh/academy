package cu.academy.images;



import cu.academy.shared.enum_types.EnumTipoDuenoImagen;
import cu.academy.shared.exceptions.ImageNotFoundException;
import cu.academy.trace.TraceService;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class FilesStorageServiceImpl implements FilesStorageService {

    //    private final Map<EnumTipoDuenoImagen, Path> paths = new HashMap<>();
    private final Path docsPath = Paths.get(this.rootPath.toAbsolutePath() + File.separator + EnumTipoDuenoImagen.DOCUMENTO);
    private final Path persPath = Paths.get(this.rootPath.toAbsolutePath() + File.separator + EnumTipoDuenoImagen.PERSONA);
    private final Path vjesPath = Paths.get(this.rootPath.toAbsolutePath() + File.separator + EnumTipoDuenoImagen.VIAJE);
    private final Path gastosPath = Paths.get(this.rootPath.toAbsolutePath() + File.separator + EnumTipoDuenoImagen.GASTOS);
    private final Path operacionesPath = Paths.get(this.rootPath.toAbsolutePath() + File.separator + EnumTipoDuenoImagen.OPERACIONES);
    private final Path incidenciasPath = Paths.get(this.rootPath.toAbsolutePath() + File.separator + EnumTipoDuenoImagen.INCIDENCIAS);
    private final Path utilesHerramientasPath = Paths.get(this.rootPath.toAbsolutePath() + File.separator + EnumTipoDuenoImagen.UTILES_HERRAMIENTAS);
    private final Path logo = Paths.get(this.rootPath.toAbsolutePath() + File.separator + "logo" +  File.separator + "logo-rene-chico.jpg");

    private final TraceService traceService;
    private static final String className = "FilesStorageServiceImpl";

    public FilesStorageServiceImpl(TraceService traceServ) {
        this.traceService = traceServ;
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootPath);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar la carpeta para subir imágenes!");
        }
    }

    /**
     * Salvar una imagen en la ruta especificada
     *
     * @param file        fichero a guardar
     * @param pathAndName ruta y nombre del fichero a guardar, por ejemplo: documentos/20220502-documento1.jpg
     */
    @Override
    public String save(MultipartFile file, String pathAndName) {
        String methodName = "save";
        try {
            String fileName = file.getOriginalFilename();
            String imagePathNameAndExt;
            if (pathAndName.contains(EnumTipoDuenoImagen.VIAJE.toString())) {
                imagePathNameAndExt = pathAndName + File.separator + fileName;
                if (!Files.exists(this.rootPath.resolve(pathAndName)))
                    Files.createDirectories(this.rootPath.resolve(pathAndName));
            } else
                imagePathNameAndExt = pathAndName + "." + FilenameUtils.getExtension(fileName);
            Path path = this.rootPath.resolve(imagePathNameAndExt);
            if (!Files.exists(this.rootPath)) {
                createDefaultDirectories();
            } else if (!Files.exists(this.docsPath)) {
                createDocDirectories();
            } else if (!Files.exists(this.persPath)) {
                createPerDirectories();
            } else if (!Files.exists(this.vjesPath)) {
                createViajeDirectories();
            } else if (!Files.exists(this.gastosPath)) {
                createGastosDirectories();
            } else if (!Files.exists(this.operacionesPath)) {
                createOperacionesDirectories();
            } else if (!Files.exists(this.incidenciasPath)) {
                createIncidenciasDirectories();
            } else if (!Files.exists(this.utilesHerramientasPath)) {
                createUtilesHerramientas();
            }

            deleteAllFilesWithSameName(pathAndName);

            if (!file.isEmpty())
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(path.toAbsolutePath());
            return imagePathNameAndExt;
        } catch (Exception e) {
           // trazaLogSistemaService.insertLog(className, methodName, "Error al guardar imagen.", e, 3);
            throw new RuntimeException("No se pudo guardar la imagen. Error: " + e.getMessage());
        }

    }

    public String getRutaLogoAgencia(){
        return logo.toFile().getAbsolutePath();
    }

    public String getExtension(MultipartFile file){
        String methodName = "getExtension";

        try{
            String fileName = file.getOriginalFilename();
            return FilenameUtils.getExtension(fileName);
        }catch (Exception e){
          //  trazaLogSistemaService.insertLog(className, methodName, "Error al recuperar la extension de la imagen.", e, 3);
            throw new RuntimeException("No se pudo recuperar la extension de la imagen. Error: " + e.getMessage());

        }

    }

    private void deleteAllFilesWithSameName(String pathAndName) throws IOException {
        String[] stringParts = pathAndName.split("/");
        String destinName = stringParts[stringParts.length - 1];
        File directory = new File(this.rootPath.resolve(stringParts[0]).toString());
        File[] files = directory.listFiles((dir, name) -> name.startsWith(destinName));
        if (files != null) {
            for (File f : files) {
                if (!f.isDirectory() && f.getName().startsWith(destinName))
                    Files.deleteIfExists(f.toPath());
            }
        }
    }

    @Override
    public Resource load(String filename) {
        String methodName = "load";

        try {
            Path file = rootPath.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable())
                return resource;
            else
                throw new RuntimeException("No se pudo leer la imagen!");

        } catch (MalformedURLException e) {
          //  trazaLogSistemaService.insertLog(className, methodName, "Error al leer la imagen.", e, 3);
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

//    @Override
//    public byte[] getFile(String filename, String extension) {
//        byte[] image;
//        try {
//            String path = this.rootPath + File.separator + filename + "." + extension;
//            image = FileUtils.readFileToByteArray(new File(path));
//        } catch (IOException e) {
//            throw new ImageNotFoundException();
//        }
//        return image;
//    }

    @Override
    public byte[] getFile(String imageType, String imageName) {
        String methodName = "getFile";

        try {
            return FileUtils.readFileToByteArray(searchFileWithTypeAndName(imageType, imageName));
        } catch (IOException e) {
       //     trazaLogSistemaService.insertLog(className, methodName, "Error al leer el fichero.", e, 3);
            throw new ImageNotFoundException();
        }
    }

    public List<String> getFilesInsideTravelFolder(String folderName) {
        try {
            File folder = new File(this.rootPath + File.separator + EnumTipoDuenoImagen.VIAJE + File.separator + folderName);
            return Arrays.stream(Objects.requireNonNull(folder.listFiles())).map(File::getName).collect(toList());
        } catch (Exception e) {
            return null;
        }
    }

    private File searchFileWithTypeAndName(String imageType, String imageName) {
        File image = null;
        try {
            File folder = new File(this.rootPath + File.separator + imageType);
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null)
                image = Arrays.stream(listOfFiles)
                        .filter(file -> file.getName().substring(0, file.getName().indexOf(".")).equalsIgnoreCase(imageName))
                        .collect(toList())
                        .get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new ImageNotFoundException();
        }
        return image;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootPath.toFile());
    }

    @Override
    public void delete(String type, String filename) {
        FileSystemUtils.deleteRecursively(searchFileWithTypeAndName(type, filename));
    }

    @Override
    public Stream<Path> loadAll() {
        String methodName = "loadAll";

        try {
            return Files.walk(this.rootPath, 1).filter(path -> !path.equals(this.rootPath)).map(this.rootPath::relativize);
        } catch (IOException e) {
         //   trazaLogSistemaService.insertLog(className, methodName, "Error al leer los ficheros.", e, 3);
            throw new RuntimeException("Could not load the files!");
        }
    }

    private void createDefaultDirectories() throws IOException {
        Files.createDirectories(this.rootPath);
        Files.createDirectories(this.docsPath);
        Files.createDirectories(this.persPath);
        Files.createDirectories(this.vjesPath);
        Files.createDirectories(this.gastosPath);
        Files.createDirectories(this.operacionesPath);
        Files.createDirectories(this.incidenciasPath);
        Files.createDirectories(this.utilesHerramientasPath);
    }

    private void createDocDirectories() throws IOException {
        Files.createDirectories(this.docsPath);
    }

    private void createPerDirectories() throws IOException {
        Files.createDirectories(this.persPath);
    }

    private void createViajeDirectories() throws IOException {
        Files.createDirectories(this.vjesPath);
    }

    private void createGastosDirectories() throws IOException {
        Files.createDirectories(this.gastosPath);
    }

    private void createOperacionesDirectories() throws IOException {
        Files.createDirectories(this.operacionesPath);
    }

    private void createIncidenciasDirectories() throws IOException {
        Files.createDirectories(this.incidenciasPath);
    }

    private void createUtilesHerramientas() throws IOException {
        Files.createDirectories(this.utilesHerramientasPath);
    }

}
