package tw.edu.fju.miniclinic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class AboutController {

    @GetMapping("/api/about")
    public Map<String, String> about() {
        return Map.of(
            "student_id",   "411570317",
            "student_name", "李睿筑",
            "project",      "MiniClinic",
            "version",      "0.1.0",
            "chapter",      "Ch09-A"
        );
    }
}
