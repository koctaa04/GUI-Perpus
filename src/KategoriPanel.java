import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.cell.PropertyValueFactory; // <-- Add this import
import java.sql.*;

public class KategoriPanel extends BorderPane {

    private TableView<KategoriData> tableKategori;
    private TextField txtIDKategori, txtNamaKategori, txtKeterangan, txtCari;
    private Button btnSimpan, btnHapus, btnReset;

    public KategoriPanel() {
        // Form input di bagian atas
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        // Label dan TextField untuk ID Kategori
        Label lblIDKategori = new Label("ID Kategori");
        txtIDKategori = new TextField();
        txtIDKategori.setEditable(false); // ID tidak bisa diedit

        // Label dan TextField untuk Nama Kategori
        Label lblNamaKategori = new Label("Nama Kategori");
        txtNamaKategori = new TextField();

        // Label dan TextField untuk Keterangan
        Label lblKeterangan = new Label("Keterangan");
        txtKeterangan = new TextField();

        // Tombol untuk aksi
        btnSimpan = new Button("Simpan");
        btnHapus = new Button("Hapus");
        btnReset = new Button("Reset");
        txtCari = new TextField();
        txtCari.setPromptText("Cari Kategori");
        Button btnCari = new Button("Cari");

        // Menambahkan elemen ke form
        form.add(lblIDKategori, 0, 0);
        form.add(txtIDKategori, 1, 0);

        form.add(lblNamaKategori, 0, 1);
        form.add(txtNamaKategori, 1, 1);

        form.add(lblKeterangan, 0, 2);
        form.add(txtKeterangan, 1, 2);

        HBox buttonBox = new HBox(10, btnSimpan, btnHapus, btnReset, txtCari, btnCari);
        buttonBox.setPadding(new Insets(10));

        // Tabel untuk daftar kategori
        tableKategori = new TableView<>();

        // Kolom tabel
        TableColumn<KategoriData, Integer> colID = new TableColumn<>("ID Kategori");
        colID.setCellValueFactory(new PropertyValueFactory<>("idKategori"));
        colID.setPrefWidth(100);

        TableColumn<KategoriData, String> colNama = new TableColumn<>("Nama Kategori");
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colNama.setPrefWidth(200);

        TableColumn<KategoriData, String> colKeterangan = new TableColumn<>("Keterangan");
        colKeterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));
        colKeterangan.setPrefWidth(300);

        tableKategori.getColumns().addAll(colID, colNama, colKeterangan);

        // Layout
        VBox centerBox = new VBox(10, form, buttonBox, tableKategori);
        centerBox.setPadding(new Insets(10));
        this.setCenter(centerBox);

        // Load data ke dalam tabel
        loadData();

        // Menambahkan listener untuk memilih baris
        tableKategori.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Mengisi form dengan data yang dipilih dari tabel
                txtIDKategori.setText(String.valueOf(newValue.getIdKategori()));
                txtNamaKategori.setText(newValue.getNama());
                txtKeterangan.setText(newValue.getKeterangan());

                // Menonaktifkan textField untuk ID Kategori agar tidak bisa diubah
                txtIDKategori.setEditable(false); // Menonaktifkan edit pada ID kategori
            }
        });

        // Menambahkan aksi pada tombol simpan
        btnSimpan.setOnAction(event -> {
            saveData();
            // Tetap non-editable setelah simpan
            txtIDKategori.setEditable(false);
        });

        // Aksi tombol Hapus
        btnHapus.setOnAction(event -> {
            deleteData();
            // Reset form setelah hapus
            resetForm();
        });

        // Aksi tombol Reset
        btnReset.setOnAction(event -> resetForm());
    }

    private void loadData() {
        ObservableList<KategoriData> data = FXCollections.observableArrayList();
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM kategori")) {
            while (resultSet.next()) {
                data.add(new KategoriData(
                        resultSet.getInt("id_kategori"),
                        resultSet.getString("nama"),
                        resultSet.getString("keterangan")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableKategori.setItems(data);
    }

    private void saveData() {
        try (Connection connection = Database.getConnection()) {
            String query;
            PreparedStatement preparedStatement;
            if (txtIDKategori.getText().isEmpty()) {
                // Insert new data
                query = "INSERT INTO kategori (nama, keterangan) VALUES (?, ?)"; 
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, txtNamaKategori.getText());
                preparedStatement.setString(2, txtKeterangan.getText());
            } else {
                // Update existing data
                query = "UPDATE kategori SET nama = ?, keterangan = ? WHERE id_kategori = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, txtNamaKategori.getText());
                preparedStatement.setString(2, txtKeterangan.getText());
                preparedStatement.setInt(3, Integer.parseInt(txtIDKategori.getText()));
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
            String query = "DELETE FROM kategori WHERE id_kategori = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(txtIDKategori.getText()));
            preparedStatement.executeUpdate();
            loadData();
            resetForm();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resetForm() {
        txtIDKategori.clear();
        txtNamaKategori.clear();
        txtKeterangan.clear();
        txtIDKategori.setEditable(true); // Mengaktifkan kembali ID agar bisa diisi saat menambah data baru
    }
}
