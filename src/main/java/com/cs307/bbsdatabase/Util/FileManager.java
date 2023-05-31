package com.cs307.bbsdatabase.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileManager {
    private FileManager(){

    }
    public static String saveFile(MultipartFile file, long currentId) throws IOException {
        // 创建目录
        String directory = "src/main/resources/static/Files/users/"+currentId+"/";
        String fileName = file.getOriginalFilename();
        File dir = new File(directory);
        if (!dir.exists()) {
            boolean makeDir = dir.mkdirs();
            System.err.println(makeDir);
        }

        String finalFileName = getUniqueFileName(directory, fileName);

        // 保存文件
        Path filePath = Paths.get(directory, finalFileName);
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return directory+finalFileName;
    }

    private static String getUniqueFileName(String directory, String fileName) {
        String baseName = FilenameUtils.getBaseName(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        String uniqueFileName = fileName;

        // 使用正则表达式匹配重复文件名的模式
        Pattern pattern = Pattern.compile("^" + baseName + "\\((\\d+)\\)\\." + extension + "$");
        int count = 1;

        // 检查目标目录下是否有重复的文件名
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory))) {
            for (Path path : directoryStream) {
                String existingFileName = path.getFileName().toString();
                Matcher matcher = pattern.matcher(existingFileName);
                if (matcher.matches()) {
                    int num = Integer.parseInt(((Matcher) matcher).group(1));
                    count = Math.max(count, num + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 若有重复文件名，则在文件名后面添加类似“(1)”，“(2)”的后缀
        if (count > 1) {
            uniqueFileName = baseName + "(" + count + ")." + extension;
        }

        return uniqueFileName;
    }
}
