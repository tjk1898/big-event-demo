package edu.czjtu.big_event_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import edu.czjtu.big_event_demo.util.Result;
import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${project.path}")
    private String projectPath;

    @Value("${file-upload.url-prefix}")
    private String fileUrlPrefix;

    @PostMapping
    public Result<String> upload(MultipartFile image) throws IOException {
        // 获取原始文件名
        String originalFilename = image.getOriginalFilename();
        // 生成新文件名
        String filename = UUID.randomUUID().toString() +
                originalFilename.substring(originalFilename.lastIndexOf("."));

        // 在上传方法里
        String uploadPath = projectPath + File.separator + "upload";

        // 确保上传目录存在
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 保存文件
        image.transferTo(new File(dir, filename));

        // 返回文件访问路径
        return Result.success(fileUrlPrefix + filename);
    }
}