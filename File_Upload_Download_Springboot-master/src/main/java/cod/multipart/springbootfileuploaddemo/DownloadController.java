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
import org.springframework.ui.Model;

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

    private static final String DIRECTORY = "C:\\fileuplode\\";

    @Autowired
    ServletContext context;

    // Show all uploaded files
    @GetMapping("/downloadfile")
    public String index(Model model) {
        File folder = new File(DIRECTORY);
        String[] fileNames = new String[0];

        if (folder.exists() && folder.isDirectory()) {
            fileNames = folder.list((dir, name) -> new File(dir, name).isFile());
        }

        model.addAttribute("files", fileNames);
        return "downloadFile";
    }

    // View (and browser will allow download if needed)
    @GetMapping("/view")
    public Object viewFile(@RequestParam String fileName,
                           RedirectAttributes redirectAttributes) throws IOException {

        Path filePath = Paths.get(DIRECTORY, fileName);

        if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
            redirectAttributes.addFlashAttribute("message", "File not found: " + fileName);
            return "redirect:/downloadfile";
        }

        File file = filePath.toFile();
        MediaType mediaType = getMediaTypeForFileName(context, fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                // 🔹 Inline mode: file browser में खुलेगी
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    private MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        String mimeType = servletContext.getMimeType(fileName);
        try {
            return MediaType.parseMediaType(mimeType);
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
