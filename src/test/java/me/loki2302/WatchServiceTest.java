package me.loki2302;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.Assert.assertTrue;

public class WatchServiceTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    // TODO: rewrite more clearly

    @Test
    public void dummy() throws IOException {
        temporaryFolder.create();
        Path temporaryFolderPath = temporaryFolder.getRoot().toPath();

        try(WatchService watchService = FileSystems.getDefault().newWatchService()) {
            WatchKey watchKey = temporaryFolderPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            String tempFilename = temporaryFolder.newFile().getName();

            boolean found = false;
            while(!found) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        System.out.println("overflow");
                        continue;
                    }

                    WatchEvent<Path> e = (WatchEvent<Path>) event;
                    System.out.printf("%s\n", e.context());
                    if(e.context().endsWith(tempFilename)) {
                        found = true;
                    }

                    break; // temporary
                }

                boolean valid = watchKey.reset();
                if(!valid) {
                    break;
                }
            }

            assertTrue(found);
        }
    }
}
