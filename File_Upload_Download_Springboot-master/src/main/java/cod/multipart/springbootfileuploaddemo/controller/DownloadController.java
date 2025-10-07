package cod.multipart.springbootfileuploaddemo.controller;

import cod.multipart.springbootfileuploaddemo.entity.UploadedFile;
import cod.multipart.springbootfileuploaddemo.repository.UploadedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class DownloadController {

    @Autowired
    private UploadedFileRepository fileRepository;

    @GetMapping("/downloadfile")
    public String index(Model model) {
        List<UploadedFile> files = fileRepository.findAll();
        model.addAttribute("files", files);
        return "downloadFile";
    }

    @GetMapping("/view")
    public ResponseEntity<ByteArrayResource> viewFile(@RequestParam String fileName) {
        UploadedFile file = fileRepository.findFirstByFileName(fileName)
                .orElseThrow(() -> new RuntimeException("File not found: " + fileName));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getData()));
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam String fileName) {
        UploadedFile file = fileRepository.findFirstByFileName(fileName)
                .orElseThrow(() -> new RuntimeException("File not found: " + fileName));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getData()));
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam String fileName) {
        UploadedFile file = fileRepository.findFirstByFileName(fileName)
                .orElseThrow(() -> new RuntimeException("File not found: " + fileName));

        fileRepository.delete(file);
        return "redirect:/downloadfile"; // After delete, redirect to list page
    }
}
