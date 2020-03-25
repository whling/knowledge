package whling.io.bio;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServerMultiThread {
    private static final Logger LOGGER = LoggerFactory.getLogger(IOServerMultiThread.class);

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(2345));

            try {
                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        InputStream inputstream = null;
                        try {
                            inputstream = socket.getInputStream();
                            LOGGER.info("Received message {}",
                                    IOUtils.toString(inputstream));
                            IOUtils.closeQuietly(inputstream);
                        } catch (IOException ex) {
                            LOGGER.error("Read message failed", ex);
                            IOUtils.closeQuietly(inputstream);
                        }
                    }).start();
                }
            } catch (IOException ex) {
                LOGGER.error("Accept connection failed", ex);
            }

        } catch (IOException ex) {
            LOGGER.error("Listen failed", ex);
            return;
        }

    }
}