import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class BukuPanel extends BorderPane {

    private Connection connection;
    private TableView<BukuData> tableBuku;
    private TextField txtIDBuku, txtJudul, txtPenulis, txtPenerbit, txtCari;
    private ComboBox<String> comboKategori;
    private Button btnSimpan, btnHapus, btnReset, btnCari;

    public BukuPanel() {
        // Gunakan koneksi dari Database.java
        connection = Database.getConnection();
        
        // Periksa apakah koneksi berhasil
        if (connection != null) {
            System.out.println("Koneksi berhasil.");
        } else {
            System.out.println("Koneksi ke database gagal.");
        }

        // Form input di bagian atas
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        // Label dan TextField untuk ID Buku
        Label lblIDBuku = new Label("ID Buku");
        txtIDBuku = new TextField();
        txtIDBuku.setEditable(false); // ID Buku tidak bisa diedit

        // Label dan ComboBox untuk Kategori
        Label lblKategori = new Label("Kategori");
        comboKategori = new ComboBox<>();
        loadKategori(comboKategori);  // Memuat kategori ke ComboBox

        // Label dan TextField untuk Judul
        Label lblJudul = new Label("Judul");
        txtJudul = new TextField();

        // Label dan TextField untuk Penerbit
        Label lblPenerbit = new Label("Penerbit");
        txtPenerbit = new TextField();

        // Label dan TextField untuk Penulis
        Label lblPenulis = new Label("Penulis");
        txtPenulis = new TextField();

        // Tombol untuk aksi
        btnSimpan = new Button("Simpan");
        btnHapus = new Button("Hapus");
        btnReset = new Button("Reset");
        txtCari = new TextField();
        txtCari.setPromptText("Cari Buku");
        btnCari = new Button("Cari");

        // Menambahkan elemen ke form
        form.add(lblIDBuku, 0, 0);
        form.add(txtIDBuku, 1, 0);

        form.add(lblKategori, 0, 1);
        form.add(comboKategori, 1, 1);

        form.add(lblJudul, 0, 2);
        form.add(txtJudul, 1, 2);

        form.add(lblPenerbit, 0, 3);
        form.add(txtPenerbit, 1, 3);

        form.add(lblPenulis, 0, 4);
        form.add(txtPenulis, 1, 4);

        HBox buttonBox = new HBox(10, btnSimpan, btnHapus, btnReset, txtCari, btnCari);
        buttonBox.setPadding(new Insets(10));

        // Tabel untuk daftar buku
        tableBuku = new TableView<>();

        // Kolom tabel
        TableColumn<BukuData, Integer> colID = new TableColumn<>("ID Buku");
        colID.setPrefWidth(100);
        colID.setCellValueFactory(new PropertyValueFactory<>("idBuku"));

        TableColumn<BukuData, String> colKategori = new TableColumn<>("Kategori");
        colKategori.setPrefWidth(150);
        colKategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));

        TableColumn<BukuData, String> colJudul = new TableColumn<>("Judul");
        colJudul.setPrefWidth(200);
        colJudul.setCellValueFactory(new PropertyValueFactory<>("judul"));

        TableColumn<BukuData, String> colPenulis = new TableColumn<>("Penulis");
        colPenulis.setPrefWidth(150);
        colPenulis.setCellValueFactory(new PropertyValueFactory<>("penulis"));

        TableColumn<BukuData, String> colPenerbit = new TableColumn<>("Penerbit");
        colPenerbit.setPrefWidth(150);
        colPenerbit.setCellValueFactory(new PropertyValueFactory<>("penerbit"));

        tableBuku.getColumns().addAll(colID, colKategori, colJudul, colPenulis, colPenerbit);

        // Layout
        VBox centerBox = new VBox(10, form, buttonBox, tableBuku);
        centerBox.setPadding(new Insets(10));
        this.setCenter(centerBox);

        // Load data buku ke dalam tabel
        loadDataBuku();

        // Event listener untuk memilih baris di tabel
        tableBuku.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Ketika baris dipilih, tampilkan data ke form
                txtIDBuku.setText(String.valueOf(newValue.getIdBuku()));
                comboKategori.setValue(newValue.getKategori());
                txtJudul.setText(newValue.getJudul());
                txtPenulis.setText(newValue.getPenulis());
                txtPenerbit.setText(newValue.getPenerbit());
            }
        });

        // Tombol Reset
        btnReset.setOnAction(e -> resetForm());

        // Tombol Simpan (untuk menambah atau mengedit buku)
        btnSimpan.setOnAction(e -> {
            if (txtIDBuku.getText().isEmpty()) {
                tambahBuku(); // Menambah buku baru
            } else {
                editBuku(); // Mengedit buku yang sudah ada
            }
        });

        // Tombol Hapus
        btnHapus.setOnAction(e -> hapusBuku());
    }

    // Method untuk memuat kategori ke ComboBox
    private void loadKategori(ComboBox<String> comboBox) {
        ObservableList<String> kategoriList = FXCollections.observableArrayList();

        // Cek apakah koneksi null
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT nama FROM kategori");

                while (rs.next()) {
                    kategoriList.add(rs.getString("nama"));
                }

                comboBox.setItems(kategoriList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Koneksi database tidak tersedia.");
        }
    }

    // Method untuk memuat data buku ke dalam tabel
    private void loadDataBuku() {
        ObservableList<BukuData> data = FXCollections.observableArrayList();

        // Cek apakah koneksi null
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT b.id_buku, k.nama AS kategori, b.judul, b.penulis, b.penerbit " +
                                "FROM buku b " +
                                "JOIN kategori k ON b.id_kategori = k.id_kategori"
                );

                while (rs.next()) {
                    data.add(new BukuData(
                            rs.getInt("id_buku"),
                            rs.getString("kategori"),
                            rs.getString("judul"),
                            rs.getString("penulis"),
                            rs.getString("penerbit")
                    ));
                }

                tableBuku.setItems(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Koneksi database tidak tersedia.");
        }
    }

    // Method untuk menambah buku
    private void tambahBuku() {
        String kategori = comboKategori.getValue();
        String judul = txtJudul.getText();
        String penulis = txtPenulis.getText();
        String penerbit = txtPenerbit.getText();

        // Cek apakah koneksi null
        if (connection != null) {
            try {
                String query = "INSERT INTO buku (id_kategori, judul, penulis, penerbit) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setInt(1, getKategoriId(kategori)); // Mendapatkan ID kategori
                stmt.setString(2, judul);
                stmt.setString(3, penulis);
                stmt.setString(4, penerbit);

                stmt.executeUpdate();
                loadDataBuku(); // Reload data buku setelah penambahan
                resetForm(); // Reset form setelah tambah
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method untuk mendapatkan ID kategori berdasarkan nama kategori
    private int getKategoriId(String kategori) {
        int kategoriId = 0;
        try {
            String query = "SELECT id_kategori FROM kategori WHERE nama = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, kategori);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                kategoriId = rs.getInt("id_kategori");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kategoriId;
    }

    // Method untuk mengedit buku
    private void editBuku() {
        int idBuku = Integer.parseInt(txtIDBuku.getText());
        String kategori = comboKategori.getValue();
        String judul = txtJudul.getText();
        String penulis = txtPenulis.getText();
        String penerbit = txtPenerbit.getText();

        // Cek apakah koneksi null
        if (connection != null) {
            try {
                String query = "UPDATE buku SET id_kategori = ?, judul = ?, penulis = ?, penerbit = ? WHERE id_buku = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setInt(1, getKategoriId(kategori));
                stmt.setString(2, judul);
                stmt.setString(3, penulis);
                stmt.setString(4, penerbit);
                stmt.setInt(5, idBuku);

                stmt.executeUpdate();
                loadDataBuku(); // Reload data buku setelah update
                resetForm(); // Reset form setelah edit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method untuk menghapus buku
    private void hapusBuku() {
        int idBuku = Integer.parseInt(txtIDBuku.getText());

        // Cek apakah koneksi null
        if (connection != null) {
            try {
                String query = "DELETE FROM buku WHERE id_buku = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setInt(1, idBuku);

                stmt.executeUpdate();
                loadDataBuku(); // Reload data buku setelah hapus
                resetForm(); // Reset form setelah hapus
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method untuk mereset form
    private void resetForm() {
        txtIDBuku.clear();
        txtJudul.clear();
        txtPenulis.clear();
        txtPenerbit.clear();
        comboKategori.setValue(null);
    }
}
