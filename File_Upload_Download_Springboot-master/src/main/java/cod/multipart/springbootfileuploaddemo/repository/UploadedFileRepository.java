package cod.multipart.springbootfileuploaddemo.repository;

import cod.multipart.springbootfileuploaddemo.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {

    Optional<UploadedFile> findFirstByFileName(String fileName);
}
