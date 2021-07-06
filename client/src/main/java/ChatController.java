import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChatController implements Initializable {

    private InputStream is;
    private OutputStream os;
    private byte[] buffer;

    public ListView<String> listView;

    public TextField textField;

    public void send(ActionEvent actionEvent) throws IOException {
        String msg = textField.getText();
        os.write(msg.getBytes(StandardCharsets.UTF_8));
        os.flush();
        textField.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buffer = new byte[256];
        try {
            File dir = new File("./");
            listView.getItems().clear();
            listView.getItems().addAll(dir.list());
            Socket socket = new Socket("localhost", 8189);
            is = socket.getInputStream();
            os = socket.getOutputStream();
            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        int read = is.read(buffer);
                        String msg = new String(buffer, 0, read);
                        Platform.runLater(() -> listView.getItems().add(msg));
                    }
                } catch (Exception e) {
                    System.err.println("Exception while read");
                }
            });
            readThread.setDaemon(true);
            readThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
