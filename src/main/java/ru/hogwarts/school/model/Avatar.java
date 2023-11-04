package ru.hogwarts.school.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Avatar {

    @Id
    @GeneratedValue
    private Long id;
    private int fileSize; //размер файла
    private String filePath; //путь до файла на диске
    private String mediaType; //тип файла

    @Lob
    private byte[] data; //информация о файле, представленная в массиве байтов

    @OneToOne
    private Student student;

    public Avatar(Long id, int fileSize, String filePath, String mediaType, byte[] data, Student student) {
        this.id = id;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.mediaType = mediaType;
        this.data = data;
        this.student = student;
    }

    public Avatar() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return fileSize == avatar.fileSize && id.equals(avatar.id) && filePath.equals(avatar.filePath) && mediaType.equals(avatar.mediaType) && Arrays.equals(data, avatar.data) && student.equals(avatar.student);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, fileSize, filePath, mediaType, student);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "id=" + id +
                ", fileSize=" + fileSize +
                ", filePath='" + filePath + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", student=" + student +
                '}';
    }


}
