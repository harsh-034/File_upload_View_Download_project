package cod.multipart.springbootfileuploaddemo.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "uploaded_files")
@Data
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // Unique file names
    private String fileName;

    private String fileType;
    private long size;

    @Lob
    private byte[] data;
}
