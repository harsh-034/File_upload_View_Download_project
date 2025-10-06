package cod.multipart.springbootfileuploaddemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    // Save the uploaded file to this folder
    private static final String UPLOADED_FOLDER = "C:\\fileuplode\\";

    @GetMapping("/")
    public String index() {
        System.out.println("i am called");
        return "upload";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/uploadStatus";
        }

        try {
            // Ensure the upload folder exists
            Path uploadDir = Paths.get(UPLOADED_FOLDER);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir); // creates folder if missing
            }

            // Save the file
            Path path = uploadDir.resolve(file.getOriginalFilename());
            Files.write(path, file.getBytes());

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "Failed to upload '" + file.getOriginalFilename() + "'");
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
}
