package selen.driver;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Store driver connection info in file
 */
public class ConnectionStore {
    private final Path path = Path.of(System.getProperty("java.io.tmpdir") + File.separator + "selen_browser_connection.txt");

    public String getConnectionString() throws IOException {
        return Files.readAllLines(path).get(0);
    }

    public void storeConnectionString(RemoteWebDriver driver) throws IOException {
        Files.writeString(path, getConnectionString(driver) + "\r\nFile is safe to delete.");
    }

    @NotNull
    private static String getConnectionString(RemoteWebDriver driver) {
        SessionId sessionId = driver.getSessionId();
        HttpCommandExecutor executor = (HttpCommandExecutor) driver.getCommandExecutor();
        URL url = executor.getAddressOfRemoteServer();
        return sessionId + ">>>" + url;
    }
}
