package cod.multipart.springbootfileuploaddemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@Slf4j
public class DownloadController {

    // Use the same folder as uploads
    private static final String DIRECTORY = "C:\\fileuplode\\";

    @Autowired
    ServletContext context;

    // Ensure the folder exists at startup
    @PostConstruct
    public void init() {
        File dir = new File(DIRECTORY);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            System.out.println("Upload folder created: " + created);
        }
    }

    @GetMapping("/downloadfile")
    public String index() {
        System.out.println("downloadfile page called");
        return "downloadFile";
    }

    @GetMapping("/download")
    public Object downloadFile(@RequestParam(required = false) String fileName,
                               RedirectAttributes redirectAttributes) throws IOException {

        // Check if filename is provided
        if (fileName == null || fileName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "No file specified to download");
            return "redirect:/downloadfile";
        }

        // Build the full path safely
        Path filePath = Paths.get(DIRECTORY, fileName).normalize();

        // Check if file exists and is a file (not folder)
        if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
            redirectAttributes.addFlashAttribute("message", "File not found: " + fileName);
            return "redirect:/downloadfile";
        }

        File file = filePath.toFile();
        MediaType mediaType = getMediaTypeForFileName(context, fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    private MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        String mimeType = servletContext.getMimeType(fileName);
        try {
            return (mimeType != null) ? MediaType.parseMediaType(mimeType) : MediaType.APPLICATION_OCTET_STREAM;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
