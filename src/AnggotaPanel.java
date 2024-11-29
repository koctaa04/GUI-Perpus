import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.*;

public class AnggotaPanel extends BorderPane {

    private TableView<AnggotaData> tableAnggota;
    private TextField txtIDAnggota, txtNama, txtAlamat, txtTelepon, txtCari;
    private Button btnSimpan, btnHapus, btnReset;

    public AnggotaPanel() {
        // Form input di bagian atas
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        // Label dan TextField untuk ID Anggota
        Label lblIDAnggota = new Label("ID Anggota");
        txtIDAnggota = new TextField();
        txtIDAnggota.setEditable(false); // ID tidak bisa diedit

        // Label dan TextField untuk Nama
        Label lblNama = new Label("Nama");
        txtNama = new TextField();

        // Label dan TextField untuk Alamat
        Label lblAlamat = new Label("Alamat");
        txtAlamat = new TextField();

        // Label dan TextField untuk Telepon
        Label lblTelepon = new Label("Telepon");
        txtTelepon = new TextField();

        // Tombol untuk aksi
        btnSimpan = new Button("Simpan");
        btnHapus = new Button("Hapus");
        btnReset = new Button("Reset");
        txtCari = new TextField();
        txtCari.setPromptText("Cari Nama Anggota");
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

        HBox buttonBox = new HBox(10, btnSimpan, btnHapus, btnReset, txtCari, btnCari);
        buttonBox.setPadding(new Insets(10));

        // Tabel untuk daftar anggota
        tableAnggota = new TableView<>();

        // Kolom tabel
        TableColumn<AnggotaData, Integer> colIDAnggota = new TableColumn<>("ID Anggota");
        colIDAnggota.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getIdAnggota()).asObject());
        colIDAnggota.setPrefWidth(100);

        TableColumn<AnggotaData, String> colNama = new TableColumn<>("Nama");
        colNama.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNama()));
        colNama.setPrefWidth(200);

        TableColumn<AnggotaData, String> colAlamat = new TableColumn<>("Alamat");
        colAlamat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlamat()));
        colAlamat.setPrefWidth(200);

        TableColumn<AnggotaData, String> colTelepon = new TableColumn<>("Telepon");
        colTelepon.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelepon()));
        colTelepon.setPrefWidth(150);

        tableAnggota.getColumns().addAll(colIDAnggota, colNama, colAlamat, colTelepon);

        // Layout
        VBox centerBox = new VBox(10, form, buttonBox, tableAnggota);
        centerBox.setPadding(new Insets(10));
        this.setCenter(centerBox);

        // Load data ke dalam tabel
        loadData();

        // Menambahkan listener untuk memilih baris
        tableAnggota.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Mengisi form dengan data yang dipilih dari tabel
                txtIDAnggota.setText(String.valueOf(newValue.getIdAnggota()));
                txtNama.setText(newValue.getNama());
                txtAlamat.setText(newValue.getAlamat());
                txtTelepon.setText(newValue.getTelepon());

                // Menonaktifkan textField untuk ID Anggota agar tidak bisa diubah
                txtIDAnggota.setEditable(false); // Menonaktifkan edit pada ID anggota
            }
        });

        // Menambahkan aksi pada tombol simpan
        btnSimpan.setOnAction(event -> {
            saveData();
            // Tetap non-editable setelah simpan
            txtIDAnggota.setEditable(false);
        });

        // Aksi tombol Hapus
        btnHapus.setOnAction(event -> {
            deleteData();
            // Reset form setelah hapus
            resetForm();
        });

        // Aksi tombol Reset
        btnReset.setOnAction(event -> resetForm());

        // Aksi tombol Cari
        btnCari.setOnAction(event -> {
            String keyword = txtCari.getText();
            if (keyword.isEmpty()) {
                loadData(); // Jika pencarian kosong, tampilkan semua data
            } else {
                cariAnggota(keyword);
            }
        });

    }

    private void loadData() {
        ObservableList<AnggotaData> data = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM anggota")) {
            while (resultSet.next()) {
                data.add(new AnggotaData(
                        resultSet.getInt("id_anggota"),
                        resultSet.getString("nama"),
                        resultSet.getString("alamat"),
                        resultSet.getString("telepon")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableAnggota.setItems(data);
    }

    private void saveData() {
        try (Connection connection = Database.getConnection()) {
            String query;
            PreparedStatement preparedStatement;
            if (txtIDAnggota.getText().isEmpty()) {
                // Insert new data
                query = "INSERT INTO anggota (nama, alamat, telepon) VALUES (?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, txtNama.getText());
                preparedStatement.setString(2, txtAlamat.getText());
                preparedStatement.setString(3, txtTelepon.getText());
            } else {
                // Update existing data
                query = "UPDATE anggota SET nama = ?, alamat = ?, telepon = ? WHERE id_anggota = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, txtNama.getText());
                preparedStatement.setString(2, txtAlamat.getText());
                preparedStatement.setString(3, txtTelepon.getText());
                preparedStatement.setInt(4, Integer.parseInt(txtIDAnggota.getText()));
            }

            preparedStatement.executeUpdate();
            loadData();
            resetForm();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteData() {
        try (Connection connection = Database.getConnection()) {
            String query = "DELETE FROM anggota WHERE id_anggota = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(txtIDAnggota.getText()));
            preparedStatement.executeUpdate();
            loadData();
            resetForm();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resetForm() {
        txtIDAnggota.clear();
        txtNama.clear();
        txtAlamat.clear();
        txtTelepon.clear();
        txtIDAnggota.setEditable(true); // Mengaktifkan kembali ID agar bisa diisi saat menambah data baru
    }

    private void cariAnggota(String keyword) {
        ObservableList<AnggotaData> data = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM anggota WHERE nama LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                data.add(new AnggotaData(
                        rs.getInt("id_anggota"),
                        rs.getString("nama"),
                        rs.getString("alamat"),
                        rs.getString("telepon")));
            }
            tableAnggota.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
