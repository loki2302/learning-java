package io.agibalov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WatchServiceTest {
    @Test
    public void canDiscoverFileCreation(@TempDir Path tempDir) throws IOException, InterruptedException {
        Watcher watcher = new Watcher(tempDir);
        watcher.start();

        try {
            Path filePath = tempDir.resolve("1.txt");
            Files.writeString(filePath, "hello there");
            Thread.sleep(1000); // give it sometime to discover an event

            assertEquals(1, watcher.getFiles().size());
            assertEquals(filePath.getFileName(), watcher.getFiles().stream().findFirst().get().getFileName());
        } finally {
            watcher.stop();
        }
    }

    @Test
    public void canDiscoverFileDeletion(@TempDir Path tempDir) throws IOException, InterruptedException {
        Path filePath = tempDir.resolve("1.txt");
        Files.writeString(filePath, "hello there");

        Watcher watcher = new Watcher(tempDir);
        watcher.start();

        try {
            filePath.toFile().delete();
            Thread.sleep(1000); // give it sometime to discover an event

            assertEquals(1, watcher.getFiles().size());
            assertEquals(filePath.getFileName(), watcher.getFiles().stream().findFirst().get().getFileName());
        } finally {
            watcher.stop();
        }
    }

    @Test
    public void canDiscoverFileEditing(@TempDir Path tempDir) throws InterruptedException, IOException {
        Path filePath = tempDir.resolve("1.txt");
        Files.writeString(filePath, "hello there");

        Watcher watcher = new Watcher(tempDir);
        watcher.start();

        try {
            Files.writeString(filePath, "omg");
            Thread.sleep(1000); // give it sometime to discover an event

            assertEquals(1, watcher.getFiles().size());
            assertEquals(filePath.getFileName(), watcher.getFiles().stream().findFirst().get().getFileName());
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

            CyclicBarrier startCyclicBarrier = new CyclicBarrier(2);
            workerThread = new Thread(() -> {
                try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                    WatchKey watchKey = path.register(watchService,
                            StandardWatchEventKinds.ENTRY_CREATE,
                            StandardWatchEventKinds.ENTRY_DELETE,
                            StandardWatchEventKinds.ENTRY_MODIFY);
                    try {
                        startCyclicBarrier.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (BrokenBarrierException e) {
                        throw new RuntimeException(e);
                    }

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

            try {
                startCyclicBarrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            } catch (BrokenBarrierException e) {
                throw new RuntimeException();
            }
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
