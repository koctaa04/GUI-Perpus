import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.SQLException;

public class PeminjamanPanel extends BorderPane {
    private TableView<PeminjamanData> tablePeminjaman;
    private TextField txtID, txtIDAnggota, txtIDBuku, txtTanggalPinjam, txtTanggalKembali;
    private Label lblNamaAnggota, lblJudulBuku;
    private Button btnSimpan, btnHapus, btnReset;

    public PeminjamanPanel() {
        // Form input di bagian atas
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        // Label dan TextField untuk ID Peminjaman
        Label lblID = new Label("ID");
        txtID = new TextField();
        txtID.setEditable(false); // ID tidak bisa diedit

        // Label dan TextField untuk ID Anggota
        Label lblIDAnggota = new Label("ID Anggota");
        txtIDAnggota = new TextField();
        Button btnCariAnggota = new Button("Cari");
        lblNamaAnggota = new Label("Nama Anggota");

        // Label dan TextField untuk ID Buku
        Label lblIDBuku = new Label("ID Buku");
        txtIDBuku = new TextField();
        Button btnCariBuku = new Button("Cari");
        lblJudulBuku = new Label("Judul Buku");

        // Label dan TextField untuk tanggal pinjam dan kembali
        Label lblTanggalPinjam = new Label("Tanggal Pinjam");
        txtTanggalPinjam = new TextField();
        txtTanggalPinjam.setPromptText("Format: YYYY/MM/DD");

        Label lblTanggalKembali = new Label("Tanggal Kembali");
        txtTanggalKembali = new TextField();
        txtTanggalKembali.setPromptText("Format: YYYY/MM/DD");

        // Tombol untuk aksi
        btnSimpan = new Button("Simpan");
        btnHapus = new Button("Hapus");
        btnReset = new Button("Reset");

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

        HBox buttonBox = new HBox(10, btnSimpan, btnHapus, btnReset);
        buttonBox.setPadding(new Insets(10));

        // Tabel untuk daftar peminjaman
        tablePeminjaman = new TableView<>();

        // Kolom tabel
        TableColumn<PeminjamanData, Integer> colID = new TableColumn<>("ID Peminjaman");
        colID.setCellValueFactory(new PropertyValueFactory<>("idPeminjaman"));
        colID.setPrefWidth(80);

        TableColumn<PeminjamanData, Integer> colIDAnggota = new TableColumn<>("ID Anggota");
        colIDAnggota.setCellValueFactory(new PropertyValueFactory<>("idAnggota"));
        colIDAnggota.setPrefWidth(80);

        TableColumn<PeminjamanData, String> colNamaAnggota = new TableColumn<>("Nama Anggota");
        colNamaAnggota.setCellValueFactory(new PropertyValueFactory<>("namaAnggota"));
        colNamaAnggota.setPrefWidth(200);

        TableColumn<PeminjamanData, Integer> colIDBuku = new TableColumn<>("ID Buku");
        colIDBuku.setCellValueFactory(new PropertyValueFactory<>("idBuku"));
        colIDBuku.setPrefWidth(80);

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
                colID, colIDAnggota, colNamaAnggota, colIDBuku, colJudulBuku, colTanggalPinjam, colTanggalKembali);

        // Layout: Menempatkan form, tombol aksi dan tabel
        VBox centerBox = new VBox(10, form, buttonBox, tablePeminjaman);
        centerBox.setPadding(new Insets(10));

        this.setCenter(centerBox);

        // Load data ke dalam tabel
        loadData();

        // Menambahkan listener untuk memilih baris
        tablePeminjaman.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Mengisi form dengan data yang dipilih dari tabel
                txtID.setText(String.valueOf(newValue.getIdPeminjaman()));
                txtIDAnggota.setText(String.valueOf(newValue.getIdAnggota()));
                txtIDBuku.setText(String.valueOf(newValue.getIdBuku()));
                txtTanggalPinjam.setText(newValue.getTanggalPinjam());
                txtTanggalKembali.setText(newValue.getTanggalKembali());

                // Menonaktifkan textField untuk ID peminjaman agar tidak bisa diubah
                txtID.setEditable(false); // Menonaktifkan edit pada ID peminjaman
            }
        });

        // Menambahkan aksi pada tombol simpan
        btnSimpan.setOnAction(event -> {
            if (txtID.getText().isEmpty()) {
                // Jika ID kosong, berarti data baru
                saveData(true);
            } else {
                // Jika ID tidak kosong, berarti update data yang ada
                saveData(false);
            }
            // Tetap non-editable setelah simpan
            txtID.setEditable(false);
        });

        // Aksi tombol Hapus
        btnHapus.setOnAction(event -> {
            PeminjamanData selectedData = tablePeminjaman.getSelectionModel().getSelectedItem();

            // Mengecek jika ada baris yang dipilih
            if (selectedData != null) {
                // Menghapus data dari tabel
                ObservableList<PeminjamanData> data = tablePeminjaman.getItems();
                data.remove(selectedData);

                // Menghapus data dari database
                try (Connection connection = Database.getConnection()) {
                    String query = "DELETE FROM peminjaman WHERE id_peminjaman = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setInt(1, selectedData.getIdPeminjaman());
                        preparedStatement.executeUpdate();
                        System.out.println("Data berhasil dihapus.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.err.println("Terjadi kesalahan saat menghapus data.");
                }

                // Reset form input setelah penghapusan
                txtID.clear();
                txtIDAnggota.clear();
                txtIDBuku.clear();
                txtTanggalPinjam.clear();
                txtTanggalKembali.clear();

                // Menonaktifkan ID peminjaman setelah reset
                txtID.setEditable(false);
            }
        });

        // Aksi tombol Reset untuk mengosongkan form
        btnReset.setOnAction(event -> {
            txtID.clear();
            txtIDAnggota.clear();
            txtIDBuku.clear();
            txtTanggalPinjam.clear();
            txtTanggalKembali.clear();
            txtID.setEditable(false); // Menonaktifkan ID agar tidak bisa diedit setelah reset
        });

        btnCariAnggota.setOnAction(event -> {
            String idAnggota = txtIDAnggota.getText();
            if (!idAnggota.isEmpty()) {
                cariAnggota(idAnggota);
            }
        });

        btnCariBuku.setOnAction(event -> {
            String idBuku = txtIDBuku.getText();
            if (!idBuku.isEmpty()) {
                cariBuku(idBuku);
            }
        });
    }

    private void saveData(boolean isNew) {
        try (Connection connection = Database.getConnection()) {
            String query;
            if (isNew) {
                query = "INSERT INTO peminjaman (id_anggota, id_buku, tanggal_pinjam, tanggal_kembali) VALUES (?, ?, ?, ?)";
            } else {
                query = "UPDATE peminjaman SET id_anggota = ?, id_buku = ?, tanggal_pinjam = ?, tanggal_kembali = ? WHERE id_peminjaman = ?";
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(txtIDAnggota.getText()));
                preparedStatement.setInt(2, Integer.parseInt(txtIDBuku.getText()));
                preparedStatement.setString(3, txtTanggalPinjam.getText());
                preparedStatement.setString(4, txtTanggalKembali.getText());

                if (!isNew) {
                    preparedStatement.setInt(5, Integer.parseInt(txtID.getText()));
                }

                preparedStatement.executeUpdate();
                System.out.println(isNew ? "Data berhasil disimpan." : "Data berhasil diperbarui.");
                loadData(); // Reload data setelah penyimpanan

                // Reset form input
                txtID.clear();
                txtIDAnggota.clear();
                txtIDBuku.clear();
                txtTanggalPinjam.clear();
                txtTanggalKembali.clear();

                txtID.setEditable(false); // Setelah simpan, ID tidak bisa diubah
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        ObservableList<PeminjamanData> data = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT \n" + //
                                "    p.id_peminjaman AS id_peminjaman, \n" + //
                                "    a.nama AS Nama_Anggota, \n" + //
                                "    p.id_anggota as id_anggota, \n" + //
                                "    b.judul AS Judul_Buku, \n" + //
                                "    p.id_buku as id_buku, \n" + //
                                "    p.tanggal_pinjam AS Tanggal_Pinjam, \n" + //
                                "    p.tanggal_kembali AS Tanggal_Kembali\n" + //
                                "FROM \n" + //
                                "    peminjaman p\n" + //
                                "JOIN \n" + //
                                "    anggota a ON p.id_anggota = a.id_anggota\n" + //
                                "JOIN \n" + //
                                "    buku b ON p.id_buku = b.id_buku;";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    PeminjamanData peminjaman = new PeminjamanData(
                            resultSet.getInt("id_peminjaman"),
                            resultSet.getInt("id_anggota"),
                            resultSet.getString("nama_anggota"),
                            resultSet.getInt("id_buku"),
                            resultSet.getString("judul_buku"),
                            resultSet.getString("tanggal_pinjam"),
                            resultSet.getString("tanggal_kembali"));
                    data.add(peminjaman);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tablePeminjaman.setItems(data);
    }

    private void resetForm() {
        txtID.clear();
        txtIDAnggota.clear();
        txtIDBuku.clear();
        txtTanggalPinjam.clear();
        txtTanggalKembali.clear();
        txtID.setEditable(false); // Non-editable untuk ID Peminjaman
    }

    private void cariAnggota(String idAnggota) {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM anggota WHERE id_anggota = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, idAnggota);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    lblNamaAnggota.setText(resultSet.getString("nama"));
                } else {
                    lblNamaAnggota.setText("Anggota tidak ditemukan.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cariBuku(String idBuku) {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM buku WHERE id_buku = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, idBuku);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    lblJudulBuku.setText(resultSet.getString("judul"));
                } else {
                    lblJudulBuku.setText("Buku tidak ditemukan.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
