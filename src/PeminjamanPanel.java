import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class PeminjamanPanel extends BorderPane {

    public PeminjamanPanel() {
        // Form input di bagian atas
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        // Label dan TextField untuk ID Peminjaman
        Label lblID = new Label("ID");
        TextField txtID = new TextField();

        // Label dan TextField untuk ID Anggota
        Label lblIDAnggota = new Label("ID Anggota");
        TextField txtIDAnggota = new TextField();
        Button btnCariAnggota = new Button("Cari");
        Label lblNamaAnggota = new Label("Nama Anggota");

        // Label dan TextField untuk ID Buku
        Label lblIDBuku = new Label("ID Buku");
        TextField txtIDBuku = new TextField();
        Button btnCariBuku = new Button("Cari");
        Label lblJudulBuku = new Label("Judul Buku");

        // Label dan TextField untuk tanggal pinjam dan kembali
        Label lblTanggalPinjam = new Label("Tanggal Pinjam");
        TextField txtTanggalPinjam = new TextField();
        txtTanggalPinjam.setPromptText("Format: YYYY/MM/DD");

        Label lblTanggalKembali = new Label("Tanggal Kembali");
        TextField txtTanggalKembali = new TextField();
        txtTanggalKembali.setPromptText("Format: YYYY/MM/DD");

        // Tombol untuk aksi
        Button btnSimpan = new Button("Simpan");
        Button btnHapus = new Button("Hapus");

        // Menambahkan elemen ke form
        form.add(lblID, 0, 0);
        form.add(txtID, 1, 0);

        form.add(lblIDAnggota, 0, 1);
        form.add(txtIDAnggota, 1, 1);
        form.add(btnCariAnggota, 2, 1);
        form.add(lblNamaAnggota, 3, 1);

        form.add(lblIDBuku, 0, 2);
        form.add(txtIDBuku, 1, 2);
        form.add(btnCariBuku, 2, 2);
        form.add(lblJudulBuku, 3, 2);

        form.add(lblTanggalPinjam, 0, 3);
        form.add(txtTanggalPinjam, 1, 3);

        form.add(lblTanggalKembali, 0, 4);
        form.add(txtTanggalKembali, 1, 4);

        HBox buttonBox = new HBox(10, btnSimpan, btnHapus);
        buttonBox.setPadding(new Insets(10));

        // Tabel untuk daftar peminjaman
        TableView<String> tablePeminjaman = new TableView<>();

        // Kolom tabel
        TableColumn<String, String> colID = new TableColumn<>("ID");
        colID.setPrefWidth(80);

        TableColumn<String, String> colIDAnggota = new TableColumn<>("ID Anggota");
        colIDAnggota.setPrefWidth(120);

        TableColumn<String, String> colNamaAnggota = new TableColumn<>("Nama Anggota");
        colNamaAnggota.setPrefWidth(200);

        TableColumn<String, String> colIDBuku = new TableColumn<>("ID Buku");
        colIDBuku.setPrefWidth(80);

        TableColumn<String, String> colJudulBuku = new TableColumn<>("Judul Buku");
        colJudulBuku.setPrefWidth(200);

        TableColumn<String, String> colTanggalPinjam = new TableColumn<>("Tanggal Pinjam");
        colTanggalPinjam.setPrefWidth(150);

        TableColumn<String, String> colTanggalKembali = new TableColumn<>("Tanggal Kembali");
        colTanggalKembali.setPrefWidth(150);

        tablePeminjaman.getColumns().addAll(
                colID, colIDAnggota, colNamaAnggota, colIDBuku, colJudulBuku, colTanggalPinjam, colTanggalKembali);

        // Layout: Menempatkan form, tombol aksi dan tabel
        VBox centerBox = new VBox(10, form, buttonBox, tablePeminjaman);
        centerBox.setPadding(new Insets(10));

        this.setCenter(centerBox);
    }
}
