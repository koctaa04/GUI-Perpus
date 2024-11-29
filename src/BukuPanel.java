import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class BukuPanel extends VBox {
    private TableView<BukuData> tableBuku;
    private TextField txtJudul, txtPenulis, txtPenerbit, txtCari;
    private ComboBox<String> cmbKategori;
    private Button btnTambah, btnHapus, btnCari;
    private Connection connection;

    public BukuPanel() {
        // Initialize the database connection
        connection = Database.getConnection();  // Assuming Database.getConnection() is defined

        // Form Input Buku
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        txtJudul = new TextField();
        txtPenulis = new TextField();
        txtPenerbit = new TextField();
        cmbKategori = new ComboBox<>();
        cmbKategori.setPrefWidth(200);

        form.add(new Label("Judul:"), 0, 0);
        form.add(txtJudul, 1, 0);
        form.add(new Label("Penulis:"), 0, 1);
        form.add(txtPenulis, 1, 1);
        form.add(new Label("Penerbit:"), 0, 2);
        form.add(txtPenerbit, 1, 2);
        form.add(new Label("Kategori:"), 0, 3);
        form.add(cmbKategori, 1, 3);

        // Tombol
        btnTambah = new Button("Tambah");
        btnHapus = new Button("Hapus");
        btnCari = new Button("Cari");
        txtCari = new TextField();
        txtCari.setPromptText("Cari berdasarkan judul");

        HBox buttonBox = new HBox(10, btnTambah, btnHapus, txtCari, btnCari);
        buttonBox.setPadding(new Insets(10));

        // Tabel Buku
        tableBuku = new TableView<>();
        tableBuku.setPrefHeight(400);

        TableColumn<BukuData, Integer> colID = new TableColumn<>("ID Buku");
        colID.setCellValueFactory(new PropertyValueFactory<>("idBuku"));
        colID.setPrefWidth(100);

        TableColumn<BukuData, String> colKategori = new TableColumn<>("Kategori");
        colKategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        colKategori.setPrefWidth(150);

        TableColumn<BukuData, String> colJudul = new TableColumn<>("Judul");
        colJudul.setCellValueFactory(new PropertyValueFactory<>("judul"));
        colJudul.setPrefWidth(200);

        TableColumn<BukuData, String> colPenulis = new TableColumn<>("Penulis");
        colPenulis.setCellValueFactory(new PropertyValueFactory<>("penulis"));
        colPenulis.setPrefWidth(150);

        TableColumn<BukuData, String> colPenerbit = new TableColumn<>("Penerbit");
        colPenerbit.setCellValueFactory(new PropertyValueFactory<>("penerbit"));
        colPenerbit.setPrefWidth(150);

        tableBuku.getColumns().addAll(colID, colKategori, colJudul, colPenulis, colPenerbit);

        // Layout Utama
        VBox centerBox = new VBox(10, form, buttonBox, tableBuku);
        centerBox.setPadding(new Insets(10));

        this.getChildren().add(centerBox);

        // Load Data Awal
        loadKategori();
        loadDataBuku();

        // Event Tombol
        btnTambah.setOnAction(e -> tambahBuku());
        btnHapus.setOnAction(e -> hapusBuku());
        btnCari.setOnAction(e -> {
            String keyword = txtCari.getText();
            if (keyword.isEmpty()) {
                loadDataBuku();
            } else {
                cariBuku(keyword);
            }
        });
    }

    private void loadKategori() {
        if (connection != null) {
            try {
                String query = "SELECT nama FROM kategori";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    cmbKategori.getItems().add(rs.getString("nama"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadDataBuku() {
        ObservableList<BukuData> data = FXCollections.observableArrayList();
        if (connection != null) {
            try {
                String query = "SELECT b.id_buku, k.nama AS kategori, b.judul, b.penulis, b.penerbit " +
                        "FROM buku b " +
                        "JOIN kategori k ON b.id_kategori = k.id_kategori";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);

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
        }
    }

    private void tambahBuku() {
        if (connection != null) {
            try {
                String query = "INSERT INTO buku (id_kategori, judul, penulis, penerbit) " +
                        "VALUES ((SELECT id_kategori FROM kategori WHERE nama = ?), ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, cmbKategori.getValue());
                stmt.setString(2, txtJudul.getText());
                stmt.setString(3, txtPenulis.getText());
                stmt.setString(4, txtPenerbit.getText());

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    loadDataBuku();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void hapusBuku() {
        BukuData selected = tableBuku.getSelectionModel().getSelectedItem();
        if (selected != null && connection != null) {
            try {
                String query = "DELETE FROM buku WHERE id_buku = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setInt(1, selected.getIdBuku());

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    loadDataBuku();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void cariBuku(String keyword) {
        ObservableList<BukuData> data = FXCollections.observableArrayList();
        if (connection != null) {
            try {
                String query = "SELECT b.id_buku, k.nama AS kategori, b.judul, b.penulis, b.penerbit " +
                        "FROM buku b " +
                        "JOIN kategori k ON b.id_kategori = k.id_kategori " +
                        "WHERE b.judul LIKE ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, "%" + keyword + "%");
                ResultSet rs = stmt.executeQuery();

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
        }
    }
}
