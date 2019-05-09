package Interface;

import java.io.IOException;

public interface Center {
//    int port = 8889;

//    String host = "127.0.0.1";

    void stop();

    void start() throws IOException;

    void register(Class serviceInterface, Class impl);

    boolean isRunning();

    int getPort();
}
