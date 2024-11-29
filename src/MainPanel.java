

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainPanel extends BorderPane {

    private Button btnBuku;
    private Button btnAnggota;
    private BukuPanel bukuPanel;
    private AnggotaPanel anggotaPanel;

    public MainPanel() {
        HBox menuBar = new HBox(10);

        btnBuku = new Button("Buku");
        btnAnggota = new Button("Anggota");

        bukuPanel = new BukuPanel();
        anggotaPanel = new AnggotaPanel();

        menuBar.getChildren().addAll(btnBuku, btnAnggota);
        this.setTop(menuBar);

        // Event handling
        btnBuku.setOnAction(e -> this.setCenter(bukuPanel));
        btnAnggota.setOnAction(e -> this.setCenter(anggotaPanel));
    }
}
