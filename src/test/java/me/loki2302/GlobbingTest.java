package me.loki2302;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GlobbingTest {
    @Test
    public void dummy() throws IOException {
        List<Path> foundFiles = findFiles(Paths.get(""), "**/Nashorn*.java");
        assertEquals(2, foundFiles.size());
    }

    private static List<Path> findFiles(Path location, String glob) throws IOException {
        List<Path> foundFiles = new ArrayList<>();

        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
        Files.walkFileTree(location, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                if(pathMatcher.matches(path)) {
                    foundFiles.add(path);
                }

                return FileVisitResult.CONTINUE;
            }
        });

        return foundFiles;
    }
}
