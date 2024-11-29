import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class BukuPanel extends BorderPane {

    public BukuPanel() {
        // Form input di bagian atas
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        // Label dan TextField untuk ID Buku
        Label lblIDBuku = new Label("ID Buku");
        TextField txtIDBuku = new TextField();

        // Label dan ComboBox untuk Kategori
        Label lblKategori = new Label("Kategori");
        ComboBox<String> comboKategori = new ComboBox<>();
        comboKategori.getItems().addAll("Fiksi", "Non-Fiksi", "Edukasi", "Komik", "Lainnya");

        // Label dan TextField untuk Judul
        Label lblJudul = new Label("Judul");
        TextField txtJudul = new TextField();

        // Label dan TextField untuk Penerbit
        Label lblPenerbit = new Label("Penerbit");
        TextField txtPenerbit = new TextField();

        // Label dan TextField untuk Penulis
        Label lblPenulis = new Label("Penulis");
        TextField txtPenulis = new TextField();

        // Tombol untuk aksi
        Button btnSimpan = new Button("Simpan");
        Button btnHapus = new Button("Hapus");
        TextField txtCari = new TextField();
        txtCari.setPromptText("Cari Buku");
        Button btnCari = new Button("Cari");

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

        HBox buttonBox = new HBox(10, btnSimpan, btnHapus, txtCari, btnCari);
        buttonBox.setPadding(new Insets(10));

        // Tabel untuk daftar buku
        TableView<String> tableBuku = new TableView<>();

        // Kolom tabel
        TableColumn<String, String> colID = new TableColumn<>("ID Buku");
        colID.setPrefWidth(100);

        TableColumn<String, String> colKategori = new TableColumn<>("Kategori");
        colKategori.setPrefWidth(150);

        TableColumn<String, String> colJudul = new TableColumn<>("Judul");
        colJudul.setPrefWidth(200);

        TableColumn<String, String> colPenulis = new TableColumn<>("Penulis");
        colPenulis.setPrefWidth(150);

        TableColumn<String, String> colPenerbit = new TableColumn<>("Penerbit");
        colPenerbit.setPrefWidth(150);

        tableBuku.getColumns().addAll(colID, colKategori, colJudul, colPenulis, colPenerbit);

        // Layout
        VBox centerBox = new VBox(10, form, buttonBox, tableBuku);
        centerBox.setPadding(new Insets(10));
        this.setCenter(centerBox);
    }
}
