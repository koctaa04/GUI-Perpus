import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Panel utama
        BorderPane root = new BorderPane();

        // Panel navigasi
        HBox navigationBar = new HBox(10);
        navigationBar.setPadding(new javafx.geometry.Insets(10));
        Button btnPeminjaman = new Button("Peminjaman");
        Button btnBuku = new Button("Buku");
        Button btnAnggota = new Button("Anggota");
        Button btnKategori = new Button("Kategori");
        navigationBar.getChildren().addAll(btnPeminjaman, btnBuku, btnAnggota, btnKategori);

        // Set panel default (Peminjaman)
        PeminjamanPanel peminjamanPanel = new PeminjamanPanel();
        root.setCenter(peminjamanPanel);

        // Event tombol navigasi
        btnPeminjaman.setOnAction(e -> root.setCenter(new PeminjamanPanel()));
        btnBuku.setOnAction(e -> root.setCenter(new BukuPanel()));
        btnAnggota.setOnAction(e -> root.setCenter(new AnggotaPanel()));
        btnKategori.setOnAction(e -> root.setCenter(new KategoriPanel()));

        root.setTop(navigationBar);

        // Scene dan stage
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("PerpusApp - Navigasi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
