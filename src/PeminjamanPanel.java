import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PeminjamanPanel extends BorderPane {
    private TableView<PeminjamanData> tablePeminjaman;

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
        tablePeminjaman = new TableView<>();

        // Kolom tabel
        TableColumn<PeminjamanData, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colID.setPrefWidth(80);

        TableColumn<PeminjamanData, Integer> colIDAnggota = new TableColumn<>("ID Anggota");
        colIDAnggota.setCellValueFactory(new PropertyValueFactory<>("idAnggota"));
        colIDAnggota.setPrefWidth(120);

        TableColumn<PeminjamanData, Integer> colIDBuku = new TableColumn<>("ID Buku");
        colIDBuku.setCellValueFactory(new PropertyValueFactory<>("idBuku"));
        colIDBuku.setPrefWidth(120);

        TableColumn<PeminjamanData, String> colNamaAnggota = new TableColumn<>("Nama Anggota");
        colNamaAnggota.setCellValueFactory(new PropertyValueFactory<>("namaAnggota"));
        colNamaAnggota.setPrefWidth(200);

        TableColumn<PeminjamanData, String> colJudulBuku = new TableColumn<>("Judul Buku");
        colJudulBuku.setCellValueFactory(new PropertyValueFactory<>("judulBuku"));
        colJudulBuku.setPrefWidth(200);

        TableColumn<PeminjamanData, String> colTanggalPinjam = new TableColumn<>("Tanggal Pinjam");
        colTanggalPinjam.setCellValueFactory(new PropertyValueFactory<>("tanggalPinjam"));
        colTanggalPinjam.setPrefWidth(150);

        TableColumn<PeminjamanData, String> colTanggalKembali = new TableColumn<>("Tanggal Kembali");
        colTanggalKembali.setCellValueFactory(new PropertyValueFactory<>("tanggalKembali"));
        colTanggalKembali.setPrefWidth(150);

        tablePeminjaman.getColumns().addAll(
                colID, colIDAnggota, colIDBuku, colNamaAnggota, colJudulBuku, colTanggalPinjam, colTanggalKembali);

        // Layout: Menempatkan form, tombol aksi dan tabel
        VBox centerBox = new VBox(10, form, buttonBox, tablePeminjaman);
        centerBox.setPadding(new Insets(10));

        this.setCenter(centerBox);

        // Load data ke dalam tabel
        loadData();
    }

    private void loadData() {
        ObservableList<PeminjamanData> data = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                 "SELECT p.id_peminjaman AS ID, " +
                 "p.id_anggota AS ID_Anggota, " +
                 "p.id_buku AS ID_Buku, " +
                 "a.nama AS Nama_Anggota, " +
                 "b.judul AS Judul_Buku, " +
                 "p.tanggal_pinjam AS Tanggal_Pinjam, " +
                 "p.tanggal_kembali AS Tanggal_Kembali " +
                 "FROM peminjaman p " +
                 "JOIN anggota a ON p.id_anggota = a.id_anggota " +
                 "JOIN buku b ON p.id_buku = b.id_buku"
             )) {

            while (resultSet.next()) {
                data.add(new PeminjamanData(
                        resultSet.getInt("ID"),
                        resultSet.getInt("ID_Anggota"),
                        resultSet.getInt("ID_Buku"),
                        resultSet.getString("Nama_Anggota"),
                        resultSet.getString("Judul_Buku"),
                        resultSet.getString("Tanggal_Pinjam"),
                        resultSet.getString("Tanggal_Kembali")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tablePeminjaman.setItems(data);
    }
}
