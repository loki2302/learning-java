package me.loki2302;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WatchServiceTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void dummy() throws IOException, InterruptedException {
        temporaryFolder.create();
        Path temporaryFolderPath = temporaryFolder.getRoot().toPath();

        Watcher watcher = new Watcher(temporaryFolderPath);
        watcher.start();
        Thread.sleep(1000); // let it start and subscribe, TODO: wait synchronously inside start()

        try {
            Path tempFilePath = temporaryFolder.newFile().toPath();
            Thread.sleep(1000); // give it sometime to discover an event

            assertEquals(1, watcher.getFiles().size());
            assertEquals(tempFilePath.getFileName(), watcher.getFiles().stream().findFirst().get().getFileName());
        } finally {
            watcher.stop();
        }
    }

    public static class Watcher {
        private final Path path;
        private volatile boolean shouldStop;
        private Thread workerThread;
        private Set<Path> files = new HashSet<>();

        public Watcher(Path path) {
            this.path = path;
        }

        public Set<Path> getFiles() {
            return files;
        }

        public void start() {
            if(workerThread != null) {
                throw new RuntimeException();
            }

            workerThread = new Thread(() -> {
                try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                    WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
                    while (!shouldStop) {
                        for (WatchEvent<?> event : watchKey.pollEvents()) {
                            WatchEvent.Kind<?> kind = event.kind();

                            if (kind == StandardWatchEventKinds.OVERFLOW) {
                                System.out.println("overflow");
                                continue;
                            }

                            WatchEvent<Path> e = (WatchEvent<Path>) event;
                            files.add(e.context());
                        }

                        boolean valid = watchKey.reset();
                        if (!valid) {
                            break;
                        }
                    }

                    shouldStop = false;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            files.clear();
            workerThread.start();
        }

        public void stop() throws InterruptedException {
            if(workerThread == null) {
                throw new RuntimeException();
            }

            shouldStop = true;
            workerThread.join();
            workerThread = null;
        }
    }
}
