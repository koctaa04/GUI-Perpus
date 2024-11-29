import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class KategoriPanel extends BorderPane {

    public KategoriPanel() {
        // Form input di bagian atas
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        // Label dan TextField untuk ID Kategori
        Label lblIDKategori = new Label("ID Kategori");
        TextField txtIDKategori = new TextField();

        // Label dan TextField untuk Nama Kategori
        Label lblNamaKategori = new Label("Nama Kategori");
        TextField txtNamaKategori = new TextField();

        // Label dan TextField untuk Keterangan
        Label lblKeterangan = new Label("Keterangan");
        TextField txtKeterangan = new TextField();

        // Tombol untuk aksi
        Button btnSimpan = new Button("Simpan");
        Button btnHapus = new Button("Hapus");
        TextField txtCari = new TextField();
        txtCari.setPromptText("Cari Kategori");
        Button btnCari = new Button("Cari");

        // Menambahkan elemen ke form
        form.add(lblIDKategori, 0, 0);
        form.add(txtIDKategori, 1, 0);

        form.add(lblNamaKategori, 0, 1);
        form.add(txtNamaKategori, 1, 1);

        form.add(lblKeterangan, 0, 2);
        form.add(txtKeterangan, 1, 2);

        HBox buttonBox = new HBox(10, btnSimpan, btnHapus, txtCari, btnCari);
        buttonBox.setPadding(new Insets(10));

        // Tabel untuk daftar kategori
        TableView<String> tableKategori = new TableView<>();

        // Kolom tabel
        TableColumn<String, String> colID = new TableColumn<>("ID Kategori");
        colID.setPrefWidth(100);

        TableColumn<String, String> colNama = new TableColumn<>("Nama Kategori");
        colNama.setPrefWidth(200);

        TableColumn<String, String> colKeterangan = new TableColumn<>("Keterangan");
        colKeterangan.setPrefWidth(300);

        tableKategori.getColumns().addAll(colID, colNama, colKeterangan);

        // Layout
        VBox centerBox = new VBox(10, form, buttonBox, tableKategori);
        centerBox.setPadding(new Insets(10));
        this.setCenter(centerBox);
    }
}
