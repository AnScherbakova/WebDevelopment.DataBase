package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Value("${path.to.avatar.folder}")
    private String avatarDir;
    private final StudentService studentService; //работа с методами
    private final AvatarRepository avatarRepository; //для работы в БД

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException //метод загрузки аватара
    {
        logger.error("Был вызван метод загрузки аватара for {} file {}", studentId, avatarFile);
        Student student = studentService.findStudent(studentId);

        Path filePath = Path.of(avatarDir, student.getId() + "." + getExtension(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream inputStream = avatarFile.getInputStream();
             OutputStream outputStream = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 1024);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 1024);
        ) {
            bufferedInputStream.transferTo(bufferedOutputStream);
        }

        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize((int) avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generateDataForSB(filePath));

        avatarRepository.save(avatar);
        logger.info ("Аватар для студента с id {} file {}: успешно загружен", studentId, avatarFile);
    }

    private byte[] generateDataForSB(Path filePath) throws IOException {
        logger.error ("Метод для уменьшения размера изображения по заданным параметрам {}: ", filePath);
        try (

                InputStream inputStream = Files.newInputStream(filePath);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 1024);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bufferedInputStream);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }

    public Optional <Avatar> findAvatar(Long studentId) //метод поиска аватара
    {
        logger.info("Был вызван метод поиска аватара {}", studentId);

        return Optional.of(avatarRepository.findByStudentId(studentId).orElse(new Avatar()));
    }

    private String getExtension(String fileName) {
        logger.info("Был вызван метод расширения {}", fileName);
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    public List <Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        logger.info( "Был вызван метод получения аватара постранично {} {}", pageNumber, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }

}
