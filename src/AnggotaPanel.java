import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AnggotaPanel extends BorderPane {

    public AnggotaPanel() {
        // Form input di bagian atas
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        // Label dan TextField untuk ID Anggota
        Label lblIDAnggota = new Label("ID Anggota");
        TextField txtIDAnggota = new TextField();

        // Label dan TextField untuk Nama
        Label lblNama = new Label("Nama");
        TextField txtNama = new TextField();

        // Label dan TextField untuk Alamat
        Label lblAlamat = new Label("Alamat");
        TextField txtAlamat = new TextField();

        // Label dan TextField untuk Telepon
        Label lblTelepon = new Label("Telepon");
        TextField txtTelepon = new TextField();

        // Tombol untuk aksi
        Button btnSimpan = new Button("Simpan");
        Button btnHapus = new Button("Hapus");
        TextField txtCari = new TextField();
        txtCari.setPromptText("Cari Anggota");
        Button btnCari = new Button("Cari");

        // Menambahkan elemen ke form
        form.add(lblIDAnggota, 0, 0);
        form.add(txtIDAnggota, 1, 0);

        form.add(lblNama, 0, 1);
        form.add(txtNama, 1, 1);

        form.add(lblAlamat, 0, 2);
        form.add(txtAlamat, 1, 2);

        form.add(lblTelepon, 0, 3);
        form.add(txtTelepon, 1, 3);

        HBox buttonBox = new HBox(10, btnSimpan, btnHapus, txtCari, btnCari);
        buttonBox.setPadding(new Insets(10));

        // Tabel untuk daftar anggota
        TableView<String> tableAnggota = new TableView<>();

        // Kolom tabel
        TableColumn<String, String> colIDAnggota = new TableColumn<>("ID Anggota");
        colIDAnggota.setPrefWidth(100);

        TableColumn<String, String> colNama = new TableColumn<>("Nama");
        colNama.setPrefWidth(200);

        TableColumn<String, String> colAlamat = new TableColumn<>("Alamat");
        colAlamat.setPrefWidth(200);

        TableColumn<String, String> colTelepon = new TableColumn<>("Telepon");
        colTelepon.setPrefWidth(150);

        tableAnggota.getColumns().addAll(colIDAnggota, colNama, colAlamat, colTelepon);

        // Layout
        VBox centerBox = new VBox(10, form, buttonBox, tableAnggota);
        centerBox.setPadding(new Insets(10));
        this.setCenter(centerBox);
    }
}
