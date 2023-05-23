/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rhoma
 */
public class MainView extends javax.swing.JFrame {

    public Statement st;
    public ResultSet res;
    public DefaultTableModel tabModel;
    public PreparedStatement pst;
    public Connection conn;

    public Border border1 = new LineBorder(new Color(76, 78, 72), 1, true);
    public Border border2 = new LineBorder(new Color(21, 21, 21), 1, true);
    
    //Combo box
    public void loadComboSatuan(){
        combo_satuan_barang.removeAllItems();
        try {
            String sql = "Select * from data_satuan order by id_satuan asc";
            conn = (Connection)Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            res = pst.executeQuery();
            while(res.next()){
                combo_satuan_barang.addItem(res.getString("nama_satuan"));
            }
        } catch (Exception e) {
        }
        
        try {
            String sql = "Select * from data_satuan where nama_satuan like '"+combo_satuan_barang.getSelectedItem()+"'";
            conn = (Connection)Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            res = pst.executeQuery();
            while(res.next()){
                id_satuan_barang.setText(res.getString("id_satuan"));
            }
        } catch (Exception e) {
        }
    }
    
    public void loadComboKategori(){
        combo_kategori_barang.removeAllItems();
        try {
            String sql = "Select * from data_kategori order by id_kategori asc";
            conn = (Connection)Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            res = pst.executeQuery();
            while(res.next()){
                combo_kategori_barang.addItem(res.getString("nama_kategori"));
            }
        } catch (Exception e) {
        }
        
        try {
            String sql = "Select * from data_kategori where nama_kategori like '"+combo_kategori_barang.getSelectedItem()+"'";
            conn = (Connection)Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            res = pst.executeQuery();
            while(res.next()){
                id_kategori_barang.setText(res.getString("id_kategori"));
            }
        } catch (Exception e) {
        }
    }

    // Clear or Batal inputan
    //Master data
    public void ClearKategori() {
        input_idkategori.setText(null);
        input_nama_kategori.setText(null);
        input_keterangan_kategori.setText(null);
    }
    
    public void ClearSatuan(){
        input_id_satuan.setText(null);
        input_nama_satuan.setText(null);
        input_keterangan_satuan.setText(null);
    }
    
    public void ClearBarang(){
        input_kodeDanBarcode_barang.setText(null);
                input_nama_barang.setText(null);
                id_satuan_barang.setText(null);
                id_kategori_barang.setText(null);
                input_hargaBiasa_barang.setText("0");
                input_hargaJual_barang.setText("0");
                input_laba_barang.setText("0");
                input_hargaGrosir_barang.setText("0");
                input_minBeliGrosir_barang.setText("0");
                input_diskonHargaPersen_barang.setText("0");
                input_minBeliHarga_barang.setText("0");
                input_stok_barang.setText("0");
    }
    
    public void ClearPelanggan(){
        input_nama_pelanggan.setText(null);
        input_alamat_pelanggan.setText(null);
        input_noHp_pelanggan.setText(null);
        input_email_pelanggan.setText(null);
    }
    
    public void ClearUser(){
        input_id_user.setText(null);
        input_nama_user.setText(null);
        input_username_user.setText(null);
        input_password_user.setText(null);
        input_noHp_user.setText(null);
    }

    //Load Tabel
    //Master data
    public void LoadTable_kategori() {
        tabModel = new DefaultTableModel();
        tabModel.addColumn("No");
        tabModel.addColumn("Nama Kategori");
        tabModel.addColumn("Keterangan");
        tabModel.addColumn("Status");
        int no = 1;
        try {
            String sql2 = "SELECT COUNT(nama_kategori) as nama FROM data_kategori";
            conn = (Connection) Config.ConfigDB();
            java.sql.Statement st2 = conn.prepareStatement(sql2);
            java.sql.ResultSet res2 = st2.executeQuery(sql2);
            if (res2.next()) {
                jml_kategori.setText(res2.getString("nama") + " Barang");
            }
            
            String sql = "Select * from data_kategori";
            st = conn.prepareStatement(sql);
            res = st.executeQuery(sql);
            while (res.next()) {
                tabModel.addRow(new Object[]{
                    no++, res.getString(2), res.getString(3), res.getString(4)
                });
            }
            Table_data_kategori.setModel(tabModel);
        } catch (Exception e) {
        }
        Table_data_kategori.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        Table_data_kategori.getTableHeader().setOpaque(false); //jika ingin memberi garis true
        Table_data_kategori.getTableHeader().setBackground(new Color(7, 175, 247));
        Table_data_kategori.getTableHeader().setForeground(new Color(255, 255, 255));
        Table_data_kategori.getColorModel();
        Table_data_kategori.setRowHeight(25);
        Table_data_kategori.getTableHeader().setPreferredSize(new Dimension(25, 25));
    }
    
    public void LoadTable_satuan(){
        tabModel = new DefaultTableModel();
        tabModel.addColumn("No");
        tabModel.addColumn("Nama Kategori");
        tabModel.addColumn("Keterangan");
        tabModel.addColumn("Status");
        int no=1;
        try {
            String sql2 = "SELECT COUNT(nama_satuan) as nama FROM data_satuan";
            conn = (Connection) Config.ConfigDB();
            java.sql.Statement st2 = conn.prepareStatement(sql2);
            java.sql.ResultSet res2 = st2.executeQuery(sql2);
            if (res2.next()) {
                Jmlh_Satuan.setText(res2.getString("nama") + " Satuan");
            }
            
            String sql = "Select * from data_satuan";
            conn = (Connection)Config.ConfigDB();
            st = conn.prepareStatement(sql);
            res = st.executeQuery(sql);
            while(res.next()){
                tabModel.addRow(new Object[]{
                    no++,res.getString(2),res.getString(3),res.getString(4)
                });
            }
            Table_satuan.setModel(tabModel);
        } catch (Exception e) {
        }
        Table_satuan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        Table_satuan.getTableHeader().setOpaque(false); //jika ingin memberi garis true
        Table_satuan.getTableHeader().setBackground(new Color(7, 175, 247));
        Table_satuan.getTableHeader().setForeground(new Color(255, 255, 255));
        Table_satuan.getColorModel();
        Table_satuan.setRowHeight(25);
        Table_satuan.getTableHeader().setPreferredSize(new Dimension(25, 25));
    }
    
    public void LoadTable_barang(){
        tabModel = new DefaultTableModel();
        tabModel.addColumn("No");
        tabModel.addColumn("Kode/barcode");
        tabModel.addColumn("Nama Barang");
        tabModel.addColumn("Satuan");
        tabModel.addColumn("Kategori");
        tabModel.addColumn("Harga Jual");
        tabModel.addColumn("Laba(%)");
        tabModel.addColumn("Stok");
        int no=1;
        
        String cari = input_cari_barang.getText();
        try {
            String sql2 = "SELECT COUNT(nama_barang) as nama FROM barang";
            conn = (Connection) Config.ConfigDB();
            java.sql.Statement st2 = conn.prepareStatement(sql2);
            java.sql.ResultSet res2 = st2.executeQuery(sql2);
            if (res2.next()) {
                Jmlh_barang.setText(res2.getString("nama") + " Barang");
            }
            
            String sql = "SELECT kode_dan_barcode, barang.nama_barang, data_satuan.nama_satuan, data_kategori.nama_kategori,barang.harga_jual,barang.laba,barang.stok "
                    + "FROM barang JOIN data_satuan ON barang.id_satuan = data_satuan.id_satuan "
                    + "JOIN data_kategori ON barang.id_kategori=data_kategori.id_kategori WHERE nama_barang LIKE '"+cari+"%'";
            conn = (Connection)Config.ConfigDB();
            st = conn.prepareStatement(sql);
            res = st.executeQuery(sql);
            while(res.next()){
                tabModel.addRow(new Object[]{
                    no++,res.getString("kode_dan_barcode"),res.getString("barang.nama_barang"),res.getString("data_satuan.nama_satuan"),res.getString("data_kategori.nama_kategori"),
                    res.getString("barang.harga_jual"),res.getString("barang.laba"),res.getString("barang.stok")
                });
            }
            Table_barang.setModel(tabModel);
        } catch (Exception e) {
        }
        Table_barang.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        Table_barang.getTableHeader().setOpaque(false); //jika ingin memberi garis true
        Table_barang.getTableHeader().setBackground(new Color(7, 175, 247));
        Table_barang.getTableHeader().setForeground(new Color(255, 255, 255));
        Table_barang.getColorModel();
        Table_barang.setRowHeight(25);
        Table_barang.getTableHeader().setPreferredSize(new Dimension(25, 25));
    }
    
    public void LoadTable_pelanggan(){
        tabModel = new DefaultTableModel();
        tabModel.addColumn("No");
        tabModel.addColumn("Nama Pelanggan");
        tabModel.addColumn("Alamat");
        tabModel.addColumn("No hp");
        tabModel.addColumn("Status");
        
        int no=1;
        String cari = input_cari_pelanggan.getText();
        try {
            String sql2 = "SELECT COUNT(nama_pelanggan) as nama FROM pelanggan";
            conn = (Connection) Config.ConfigDB();
            java.sql.Statement st2 = conn.prepareStatement(sql2);
            java.sql.ResultSet res2 = st2.executeQuery(sql2);
            if (res2.next()) {
                JmlPelanggan.setText(res2.getString("nama") + " Pelanggan");
            }
            
            String sql = "Select * from pelanggan where nama_pelanggan like '"+cari+"%' order by kode_pelanggan ASC";
            conn = (Connection)Config.ConfigDB();
            st = conn.prepareStatement(sql);
            res = st.executeQuery(sql);
            while(res.next()){
                tabModel.addRow(new Object[]{
                    no++,res.getString(2),res.getString(3),res.getString(4),res.getString(6)
                });
            }
            Table_Pelanggan.setModel(tabModel);
        } catch (Exception e) {
        }
        Table_Pelanggan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        Table_Pelanggan.getTableHeader().setOpaque(false); //jika ingin memberi garis true
        Table_Pelanggan.getTableHeader().setBackground(new Color(7, 175, 247));
        Table_Pelanggan.getTableHeader().setForeground(new Color(255, 255, 255));
        Table_Pelanggan.getColorModel();
        Table_Pelanggan.setRowHeight(25);
        Table_Pelanggan.getTableHeader().setPreferredSize(new Dimension(25, 25));
    }
    
    public void LoadTable_Users(){
        tabModel = new DefaultTableModel();
        tabModel.addColumn("No");
        tabModel.addColumn("Nama lengkap");
        tabModel.addColumn("Username");
        tabModel.addColumn("No hp");
        tabModel.addColumn("Level");
        tabModel.addColumn("Status");
        
        int no=1;
        String cari = input_cari_pelanggan.getText();
        try {
            String sql2 = "SELECT COUNT(nama_user) as nama FROM data_user";
            conn = (Connection) Config.ConfigDB();
            java.sql.Statement st2 = conn.prepareStatement(sql2);
            java.sql.ResultSet res2 = st2.executeQuery(sql2);
            if (res2.next()) {
                Jmlh_users.setText(res2.getString("nama") + " Users");
            }
            
            String sql = "Select * from data_user order by id_user ASC";
            conn = (Connection)Config.ConfigDB();
            st = conn.prepareStatement(sql);
            res = st.executeQuery(sql);
            while(res.next()){
                tabModel.addRow(new Object[]{
                    no++,res.getString(2),res.getString(3),res.getString(5),res.getString(6),res.getString(7)
                });
            }
            Table_Users.setModel(tabModel);
        } catch (Exception e) {
        }
        Table_Users.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        Table_Users.getTableHeader().setOpaque(false); //jika ingin memberi garis true
        Table_Users.getTableHeader().setBackground(new Color(7, 175, 247));
        Table_Users.getTableHeader().setForeground(new Color(255, 255, 255));
        Table_Users.getColorModel();
        Table_Users.setRowHeight(25);
        Table_Users.getTableHeader().setPreferredSize(new Dimension(25, 25));
    }

    /**
     * Creates new form MainView
     */
    public MainView() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        MenuPanelSetting.add(PanelSetting);
        MenuPanelMaster.add(PanelMaster);
        MenuPanelTransaksi.add(PanelTransaksi);
        MenuPanelLaporan.add(PanelLaporan);
        
        loadComboSatuan();
        loadComboKategori();

        //Data kategori
        LoadTable_kategori();
        btn_tambah_kategori.setEnabled(true);
        btn_ubah_kategori.setEnabled(false);
        btn_hapus_kategori.setEnabled(false);
        
        //Data satuan
        LoadTable_satuan();
        btn_tambah_satuan.setEnabled(true);
        btn_ubah_satuan.setEnabled(false);
        btn_hapus_satuan.setEnabled(false);
        
        //Data Barang
        LoadTable_barang();
        input_kodeDanBarcode_barang.setEnabled(true);
        btn_tambah_barang.setEnabled(true);
        btn_ubah_barang.setEnabled(false);
        btn_hapus_barang.setEnabled(false);
        
        //Data Pelanggan
        LoadTable_pelanggan();
        btn_tambah_pelanggan.setEnabled(true);
        btn_ubah_pelanggan.setEnabled(false);
        btn_hapus_pelanggan.setEnabled(false);
        
        //Data Users
        LoadTable_Users();
        btn_tambah_user.setEnabled(true);
        btn_ubah_user.setEnabled(false);
        btn_hapus_user.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelSetting = new javax.swing.JPanel();
        SettingAplikasi = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        MenuPanelSetting = new javax.swing.JPopupMenu();
        PanelMaster = new javax.swing.JPanel();
        MenuKategori = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        MenuDataBarang = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        MenuSatuan = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        MenuPelanggan = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        MenuUsers = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        MenuPanelMaster = new javax.swing.JPopupMenu();
        PanelTransaksi = new javax.swing.JPanel();
        MenuTransaksiPenjualan = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        MenuReturnPenjualan = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        MenuUpdateHarga = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        MenuStokOpname = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        MenuPanelTransaksi = new javax.swing.JPopupMenu();
        PanelLaporan = new javax.swing.JPanel();
        MenuLaporanBarang = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        MenuLaporanStokOpname = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        MenuLaporanPenjualan = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        MenuLaporanReturnPenjualan = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        MenuLaporanUpdateHarga = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        MenuLaporanPenjualan_perPelanggan = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        MenuLaporanLaba = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        MenuPanelLaporan = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        MenuDashboard = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtMenu_Dashboard = new javax.swing.JLabel();
        MenuMaster = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtMenu_Master = new javax.swing.JLabel();
        MenuLaporan = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtMenu_Laporan = new javax.swing.JLabel();
        MenuTransaksi = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtMenu_Transaksi = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        MenuKoneksiDatabase = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtKoneksi = new javax.swing.JLabel();
        MenuManualProgram = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtManualProgram = new javax.swing.JLabel();
        MenuManualBook = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtManualBook = new javax.swing.JLabel();
        MenuAboutMe = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        txtAboutMe = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        Card = new javax.swing.JPanel();
        Dashboard = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        Data_kategori = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jml_kategori = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        input_idkategori = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        input_nama_kategori = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        input_keterangan_kategori = new javax.swing.JTextField();
        btn_tambah_kategori = new javax.swing.JButton();
        btn_batal_kategori = new javax.swing.JButton();
        btn_hapus_kategori = new javax.swing.JButton();
        btn_ubah_kategori = new javax.swing.JButton();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        Combo_status_kategori = new javax.swing.JComboBox<>();
        jLabel84 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_data_kategori = new javax.swing.JTable();
        Jenis_Satuan = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        Jmlh_Satuan = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        input_id_satuan = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        input_nama_satuan = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        input_keterangan_satuan = new javax.swing.JTextField();
        btn_tambah_satuan = new javax.swing.JButton();
        btn_batal_satuan = new javax.swing.JButton();
        btn_hapus_satuan = new javax.swing.JButton();
        btn_ubah_satuan = new javax.swing.JButton();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        Combo_status_satuan = new javax.swing.JComboBox<>();
        jLabel85 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_satuan = new javax.swing.JTable();
        Data_barang = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        input_kodeDanBarcode_barang = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        input_nama_barang = new javax.swing.JTextField();
        btn_tambah_barang = new javax.swing.JButton();
        btn_batal_barang = new javax.swing.JButton();
        btn_hapus_barang = new javax.swing.JButton();
        btn_ubah_barang = new javax.swing.JButton();
        input_hargaBiasa_barang = new javax.swing.JTextField();
        jLabel93 = new javax.swing.JLabel();
        combo_satuan_barang = new javax.swing.JComboBox<>();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        combo_kategori_barang = new javax.swing.JComboBox<>();
        input_hargaJual_barang = new javax.swing.JTextField();
        jLabel100 = new javax.swing.JLabel();
        input_hargaGrosir_barang = new javax.swing.JTextField();
        jLabel94 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        input_minBeliGrosir_barang = new javax.swing.JTextField();
        input_diskonHargaPersen_barang = new javax.swing.JTextField();
        input_minBeliHarga_barang = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        input_laba_barang = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        input_stok_barang = new javax.swing.JTextField();
        jLabel90 = new javax.swing.JLabel();
        id_satuan_barang = new javax.swing.JLabel();
        id_kategori_barang = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel96 = new javax.swing.JLabel();
        Jmlh_barang = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        Table_barang = new javax.swing.JTable();
        jLabel104 = new javax.swing.JLabel();
        input_cari_barang = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        Data_pelanggan = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        JmlPelanggan = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        input_kode_pelanggan = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        input_nama_pelanggan = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        btn_tambah_pelanggan = new javax.swing.JButton();
        btn_batal_pelanggan = new javax.swing.JButton();
        btn_hapus_pelanggan = new javax.swing.JButton();
        btn_ubah_pelanggan = new javax.swing.JButton();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        input_alamat_pelanggan = new javax.swing.JTextArea();
        input_noHp_pelanggan = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        input_email_pelanggan = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        combo_status_pelanggan = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table_Pelanggan = new javax.swing.JTable();
        input_cari_pelanggan = new javax.swing.JTextField();
        jLabel113 = new javax.swing.JLabel();
        Data_users = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel91 = new javax.swing.JLabel();
        Jmlh_users = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        input_nama_user = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        input_username_user = new javax.swing.JTextField();
        jLabel109 = new javax.swing.JLabel();
        btn_tambah_user = new javax.swing.JButton();
        btn_batal_user = new javax.swing.JButton();
        btn_hapus_user = new javax.swing.JButton();
        btn_ubah_user = new javax.swing.JButton();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        input_noHp_user = new javax.swing.JTextField();
        jLabel112 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        combo_status_user = new javax.swing.JComboBox<>();
        input_password_user = new javax.swing.JTextField();
        combo_level_user = new javax.swing.JComboBox<>();
        jLabel115 = new javax.swing.JLabel();
        input_id_user = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        Table_Users = new javax.swing.JTable();
        ReturnPenjualan = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        StokOpname = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        UpdateHarga = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        LaporanBarang = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        LaporanStokOpname = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        LaporanPenjualan = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        LaporanReturnPenjualan = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        LaporanUpdateHarga = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        LaporanPenjualanPerPelanggan = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        Laba = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        Judul = new javax.swing.JLabel();
        Setting = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        PanelSetting.setBackground(new java.awt.Color(255, 255, 255));

        SettingAplikasi.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Setting Aplikasi");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
        });

        javax.swing.GroupLayout SettingAplikasiLayout = new javax.swing.GroupLayout(SettingAplikasi);
        SettingAplikasi.setLayout(SettingAplikasiLayout);
        SettingAplikasiLayout.setHorizontalGroup(
            SettingAplikasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SettingAplikasiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
        );
        SettingAplikasiLayout.setVerticalGroup(
            SettingAplikasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
        );

        btnLogout.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Logout");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnLogoutLayout = new javax.swing.GroupLayout(btnLogout);
        btnLogout.setLayout(btnLogoutLayout);
        btnLogoutLayout.setHorizontalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLogoutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnLogoutLayout.setVerticalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelSettingLayout = new javax.swing.GroupLayout(PanelSetting);
        PanelSetting.setLayout(PanelSettingLayout);
        PanelSettingLayout.setHorizontalGroup(
            PanelSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SettingAplikasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelSettingLayout.setVerticalGroup(
            PanelSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSettingLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(SettingAplikasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        MenuPanelSetting.setBorder(null);

        PanelMaster.setBackground(new java.awt.Color(43, 43, 43));
        PanelMaster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PanelMasterMouseExited(evt);
            }
        });

        MenuKategori.setBackground(new java.awt.Color(43, 43, 43));

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(139, 139, 139));
        jLabel31.setText("Data kategori");
        jLabel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel31MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel31MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel31MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuKategoriLayout = new javax.swing.GroupLayout(MenuKategori);
        MenuKategori.setLayout(MenuKategoriLayout);
        MenuKategoriLayout.setHorizontalGroup(
            MenuKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuKategoriLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuKategoriLayout.setVerticalGroup(
            MenuKategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        MenuDataBarang.setBackground(new java.awt.Color(43, 43, 43));

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(139, 139, 139));
        jLabel33.setText("Data barang");
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel33MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel33MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuDataBarangLayout = new javax.swing.GroupLayout(MenuDataBarang);
        MenuDataBarang.setLayout(MenuDataBarangLayout);
        MenuDataBarangLayout.setHorizontalGroup(
            MenuDataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuDataBarangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuDataBarangLayout.setVerticalGroup(
            MenuDataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        MenuSatuan.setBackground(new java.awt.Color(43, 43, 43));

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(139, 139, 139));
        jLabel32.setText("Jenis Satuan");
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel32MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel32MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuSatuanLayout = new javax.swing.GroupLayout(MenuSatuan);
        MenuSatuan.setLayout(MenuSatuanLayout);
        MenuSatuanLayout.setHorizontalGroup(
            MenuSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuSatuanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuSatuanLayout.setVerticalGroup(
            MenuSatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        MenuPelanggan.setBackground(new java.awt.Color(43, 43, 43));

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(139, 139, 139));
        jLabel34.setText("Data pelanggan");
        jLabel34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel34MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel34MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel34MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuPelangganLayout = new javax.swing.GroupLayout(MenuPelanggan);
        MenuPelanggan.setLayout(MenuPelangganLayout);
        MenuPelangganLayout.setHorizontalGroup(
            MenuPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuPelangganLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
        );
        MenuPelangganLayout.setVerticalGroup(
            MenuPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        MenuUsers.setBackground(new java.awt.Color(43, 43, 43));

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(139, 139, 139));
        jLabel35.setText("Data users");
        jLabel35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel35MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel35MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel35MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuUsersLayout = new javax.swing.GroupLayout(MenuUsers);
        MenuUsers.setLayout(MenuUsersLayout);
        MenuUsersLayout.setHorizontalGroup(
            MenuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuUsersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuUsersLayout.setVerticalGroup(
            MenuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelMasterLayout = new javax.swing.GroupLayout(PanelMaster);
        PanelMaster.setLayout(PanelMasterLayout);
        PanelMasterLayout.setHorizontalGroup(
            PanelMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MenuUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MenuPelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MenuDataBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MenuSatuan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MenuKategori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelMasterLayout.setVerticalGroup(
            PanelMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMasterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MenuKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuDataBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelTransaksi.setBackground(new java.awt.Color(43, 43, 43));

        MenuTransaksiPenjualan.setBackground(new java.awt.Color(43, 43, 43));

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(139, 139, 139));
        jLabel15.setText("Transaksi penjualan");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel15MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuTransaksiPenjualanLayout = new javax.swing.GroupLayout(MenuTransaksiPenjualan);
        MenuTransaksiPenjualan.setLayout(MenuTransaksiPenjualanLayout);
        MenuTransaksiPenjualanLayout.setHorizontalGroup(
            MenuTransaksiPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuTransaksiPenjualanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
        );
        MenuTransaksiPenjualanLayout.setVerticalGroup(
            MenuTransaksiPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        MenuReturnPenjualan.setBackground(new java.awt.Color(43, 43, 43));

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(139, 139, 139));
        jLabel16.setText("Return Penjualan");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel16MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuReturnPenjualanLayout = new javax.swing.GroupLayout(MenuReturnPenjualan);
        MenuReturnPenjualan.setLayout(MenuReturnPenjualanLayout);
        MenuReturnPenjualanLayout.setHorizontalGroup(
            MenuReturnPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuReturnPenjualanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuReturnPenjualanLayout.setVerticalGroup(
            MenuReturnPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        MenuUpdateHarga.setBackground(new java.awt.Color(43, 43, 43));

        jLabel39.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(139, 139, 139));
        jLabel39.setText("Update harga");
        jLabel39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel39MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel39MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel39MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuUpdateHargaLayout = new javax.swing.GroupLayout(MenuUpdateHarga);
        MenuUpdateHarga.setLayout(MenuUpdateHargaLayout);
        MenuUpdateHargaLayout.setHorizontalGroup(
            MenuUpdateHargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuUpdateHargaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuUpdateHargaLayout.setVerticalGroup(
            MenuUpdateHargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        MenuStokOpname.setBackground(new java.awt.Color(43, 43, 43));

        jLabel40.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(139, 139, 139));
        jLabel40.setText("Stok opname");
        jLabel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel40MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel40MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel40MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuStokOpnameLayout = new javax.swing.GroupLayout(MenuStokOpname);
        MenuStokOpname.setLayout(MenuStokOpnameLayout);
        MenuStokOpnameLayout.setHorizontalGroup(
            MenuStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuStokOpnameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuStokOpnameLayout.setVerticalGroup(
            MenuStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelTransaksiLayout = new javax.swing.GroupLayout(PanelTransaksi);
        PanelTransaksi.setLayout(PanelTransaksiLayout);
        PanelTransaksiLayout.setHorizontalGroup(
            PanelTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MenuTransaksiPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MenuReturnPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MenuStokOpname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MenuUpdateHarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelTransaksiLayout.setVerticalGroup(
            PanelTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelTransaksiLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(MenuTransaksiPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuReturnPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuStokOpname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuUpdateHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        PanelLaporan.setBackground(new java.awt.Color(43, 43, 43));

        MenuLaporanBarang.setBackground(new java.awt.Color(43, 43, 43));

        jLabel44.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(139, 139, 139));
        jLabel44.setText("Laporan barang");
        jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel44MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel44MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel44MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuLaporanBarangLayout = new javax.swing.GroupLayout(MenuLaporanBarang);
        MenuLaporanBarang.setLayout(MenuLaporanBarangLayout);
        MenuLaporanBarangLayout.setHorizontalGroup(
            MenuLaporanBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLaporanBarangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuLaporanBarangLayout.setVerticalGroup(
            MenuLaporanBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        MenuLaporanStokOpname.setBackground(new java.awt.Color(43, 43, 43));

        jLabel47.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(139, 139, 139));
        jLabel47.setText("Laporan stok opname");
        jLabel47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel47MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel47MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel47MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuLaporanStokOpnameLayout = new javax.swing.GroupLayout(MenuLaporanStokOpname);
        MenuLaporanStokOpname.setLayout(MenuLaporanStokOpnameLayout);
        MenuLaporanStokOpnameLayout.setHorizontalGroup(
            MenuLaporanStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLaporanStokOpnameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
        );
        MenuLaporanStokOpnameLayout.setVerticalGroup(
            MenuLaporanStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        MenuLaporanPenjualan.setBackground(new java.awt.Color(43, 43, 43));

        jLabel48.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(139, 139, 139));
        jLabel48.setText("Laporan Penjualan");
        jLabel48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel48MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel48MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel48MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuLaporanPenjualanLayout = new javax.swing.GroupLayout(MenuLaporanPenjualan);
        MenuLaporanPenjualan.setLayout(MenuLaporanPenjualanLayout);
        MenuLaporanPenjualanLayout.setHorizontalGroup(
            MenuLaporanPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLaporanPenjualanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuLaporanPenjualanLayout.setVerticalGroup(
            MenuLaporanPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        MenuLaporanReturnPenjualan.setBackground(new java.awt.Color(43, 43, 43));

        jLabel49.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(139, 139, 139));
        jLabel49.setText("Laporan return penjualan");
        jLabel49.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel49MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel49MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel49MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuLaporanReturnPenjualanLayout = new javax.swing.GroupLayout(MenuLaporanReturnPenjualan);
        MenuLaporanReturnPenjualan.setLayout(MenuLaporanReturnPenjualanLayout);
        MenuLaporanReturnPenjualanLayout.setHorizontalGroup(
            MenuLaporanReturnPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLaporanReturnPenjualanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
        );
        MenuLaporanReturnPenjualanLayout.setVerticalGroup(
            MenuLaporanReturnPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        MenuLaporanUpdateHarga.setBackground(new java.awt.Color(43, 43, 43));

        jLabel50.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(139, 139, 139));
        jLabel50.setText("Laporan update harga");
        jLabel50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel50MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel50MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel50MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuLaporanUpdateHargaLayout = new javax.swing.GroupLayout(MenuLaporanUpdateHarga);
        MenuLaporanUpdateHarga.setLayout(MenuLaporanUpdateHargaLayout);
        MenuLaporanUpdateHargaLayout.setHorizontalGroup(
            MenuLaporanUpdateHargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLaporanUpdateHargaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
        );
        MenuLaporanUpdateHargaLayout.setVerticalGroup(
            MenuLaporanUpdateHargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        MenuLaporanPenjualan_perPelanggan.setBackground(new java.awt.Color(43, 43, 43));

        jLabel51.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(139, 139, 139));
        jLabel51.setText("Penjualan per pelanggan");
        jLabel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel51MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel51MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel51MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuLaporanPenjualan_perPelangganLayout = new javax.swing.GroupLayout(MenuLaporanPenjualan_perPelanggan);
        MenuLaporanPenjualan_perPelanggan.setLayout(MenuLaporanPenjualan_perPelangganLayout);
        MenuLaporanPenjualan_perPelangganLayout.setHorizontalGroup(
            MenuLaporanPenjualan_perPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLaporanPenjualan_perPelangganLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuLaporanPenjualan_perPelangganLayout.setVerticalGroup(
            MenuLaporanPenjualan_perPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel51, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        MenuLaporanLaba.setBackground(new java.awt.Color(43, 43, 43));

        jLabel52.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(139, 139, 139));
        jLabel52.setText("Laba");
        jLabel52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel52MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel52MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel52MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuLaporanLabaLayout = new javax.swing.GroupLayout(MenuLaporanLaba);
        MenuLaporanLaba.setLayout(MenuLaporanLabaLayout);
        MenuLaporanLabaLayout.setHorizontalGroup(
            MenuLaporanLabaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLaporanLabaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
        );
        MenuLaporanLabaLayout.setVerticalGroup(
            MenuLaporanLabaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelLaporanLayout = new javax.swing.GroupLayout(PanelLaporan);
        PanelLaporan.setLayout(PanelLaporanLayout);
        PanelLaporanLayout.setHorizontalGroup(
            PanelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MenuLaporanBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MenuLaporanStokOpname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MenuLaporanPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MenuLaporanReturnPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MenuLaporanUpdateHarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MenuLaporanPenjualan_perPelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MenuLaporanLaba, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelLaporanLayout.setVerticalGroup(
            PanelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLaporanLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(MenuLaporanBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuLaporanStokOpname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuLaporanPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuLaporanReturnPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuLaporanUpdateHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuLaporanPenjualan_perPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuLaporanLaba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setType(java.awt.Window.Type.POPUP);

        jPanel1.setBackground(new java.awt.Color(21, 21, 21));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mengkriuk");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(139, 139, 139));
        jLabel3.setText("MENGELOLA");

        MenuDashboard.setBackground(new java.awt.Color(76, 78, 72));
        MenuDashboard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(21, 21, 21)));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Dashboard.png"))); // NOI18N

        txtMenu_Dashboard.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtMenu_Dashboard.setForeground(new java.awt.Color(255, 255, 255));
        txtMenu_Dashboard.setText("Dashboard");
        txtMenu_Dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMenu_DashboardMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtMenu_DashboardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtMenu_DashboardMouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuDashboardLayout = new javax.swing.GroupLayout(MenuDashboard);
        MenuDashboard.setLayout(MenuDashboardLayout);
        MenuDashboardLayout.setHorizontalGroup(
            MenuDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMenu_Dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuDashboardLayout.setVerticalGroup(
            MenuDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(txtMenu_Dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MenuMaster.setBackground(new java.awt.Color(21, 21, 21));
        MenuMaster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(21, 21, 21)));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Master_data.png"))); // NOI18N

        txtMenu_Master.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtMenu_Master.setForeground(new java.awt.Color(139, 139, 139));
        txtMenu_Master.setText("Master data");
        txtMenu_Master.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMenu_MasterMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtMenu_MasterMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtMenu_MasterMouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuMasterLayout = new javax.swing.GroupLayout(MenuMaster);
        MenuMaster.setLayout(MenuMasterLayout);
        MenuMasterLayout.setHorizontalGroup(
            MenuMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuMasterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMenu_Master, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuMasterLayout.setVerticalGroup(
            MenuMasterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(txtMenu_Master, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MenuLaporan.setBackground(new java.awt.Color(21, 21, 21));
        MenuLaporan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(21, 21, 21)));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Laporan.png"))); // NOI18N

        txtMenu_Laporan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtMenu_Laporan.setForeground(new java.awt.Color(139, 139, 139));
        txtMenu_Laporan.setText("Laporan");
        txtMenu_Laporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMenu_LaporanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtMenu_LaporanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtMenu_LaporanMouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuLaporanLayout = new javax.swing.GroupLayout(MenuLaporan);
        MenuLaporan.setLayout(MenuLaporanLayout);
        MenuLaporanLayout.setHorizontalGroup(
            MenuLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLaporanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMenu_Laporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuLaporanLayout.setVerticalGroup(
            MenuLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(txtMenu_Laporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MenuTransaksi.setBackground(new java.awt.Color(21, 21, 21));
        MenuTransaksi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(21, 21, 21)));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Transaction.png"))); // NOI18N

        txtMenu_Transaksi.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtMenu_Transaksi.setForeground(new java.awt.Color(139, 139, 139));
        txtMenu_Transaksi.setText("Transaksi");
        txtMenu_Transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMenu_TransaksiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtMenu_TransaksiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtMenu_TransaksiMouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuTransaksiLayout = new javax.swing.GroupLayout(MenuTransaksi);
        MenuTransaksi.setLayout(MenuTransaksiLayout);
        MenuTransaksiLayout.setHorizontalGroup(
            MenuTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMenu_Transaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuTransaksiLayout.setVerticalGroup(
            MenuTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(txtMenu_Transaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(139, 139, 139));
        jLabel17.setText("BANTUAN");

        MenuKoneksiDatabase.setBackground(new java.awt.Color(21, 21, 21));
        MenuKoneksiDatabase.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(21, 21, 21)));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/koneksi_database.png"))); // NOI18N

        txtKoneksi.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtKoneksi.setForeground(new java.awt.Color(139, 139, 139));
        txtKoneksi.setText("Koneksi Database");
        txtKoneksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtKoneksiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtKoneksiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtKoneksiMouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuKoneksiDatabaseLayout = new javax.swing.GroupLayout(MenuKoneksiDatabase);
        MenuKoneksiDatabase.setLayout(MenuKoneksiDatabaseLayout);
        MenuKoneksiDatabaseLayout.setHorizontalGroup(
            MenuKoneksiDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuKoneksiDatabaseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtKoneksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuKoneksiDatabaseLayout.setVerticalGroup(
            MenuKoneksiDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(txtKoneksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MenuManualProgram.setBackground(new java.awt.Color(21, 21, 21));
        MenuManualProgram.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(21, 21, 21)));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/manual_program.png"))); // NOI18N

        txtManualProgram.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtManualProgram.setForeground(new java.awt.Color(139, 139, 139));
        txtManualProgram.setText("Manual Program");
        txtManualProgram.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtManualProgramMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtManualProgramMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtManualProgramMouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuManualProgramLayout = new javax.swing.GroupLayout(MenuManualProgram);
        MenuManualProgram.setLayout(MenuManualProgramLayout);
        MenuManualProgramLayout.setHorizontalGroup(
            MenuManualProgramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuManualProgramLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtManualProgram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuManualProgramLayout.setVerticalGroup(
            MenuManualProgramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
            .addComponent(txtManualProgram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MenuManualBook.setBackground(new java.awt.Color(21, 21, 21));
        MenuManualBook.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(21, 21, 21)));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/manual_book.png"))); // NOI18N

        txtManualBook.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtManualBook.setForeground(new java.awt.Color(139, 139, 139));
        txtManualBook.setText("Manual book");
        txtManualBook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtManualBookMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtManualBookMouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuManualBookLayout = new javax.swing.GroupLayout(MenuManualBook);
        MenuManualBook.setLayout(MenuManualBookLayout);
        MenuManualBookLayout.setHorizontalGroup(
            MenuManualBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuManualBookLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtManualBook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuManualBookLayout.setVerticalGroup(
            MenuManualBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(txtManualBook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MenuAboutMe.setBackground(new java.awt.Color(21, 21, 21));
        MenuAboutMe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(21, 21, 21)));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/about.png"))); // NOI18N

        txtAboutMe.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtAboutMe.setForeground(new java.awt.Color(139, 139, 139));
        txtAboutMe.setText("Tentang kami");
        txtAboutMe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtAboutMeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtAboutMeMouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuAboutMeLayout = new javax.swing.GroupLayout(MenuAboutMe);
        MenuAboutMe.setLayout(MenuAboutMeLayout);
        MenuAboutMeLayout.setHorizontalGroup(
            MenuAboutMeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuAboutMeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtAboutMe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuAboutMeLayout.setVerticalGroup(
            MenuAboutMeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
            .addComponent(txtAboutMe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/loginUser.png"))); // NOI18N

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Muhammad Rhomaedi");

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(139, 139, 139));
        jLabel30.setText("Sebagai Admin");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 11, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(MenuDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(MenuMaster, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(MenuTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(MenuLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(MenuKoneksiDatabase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(MenuManualProgram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(MenuManualBook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(MenuAboutMe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MenuDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MenuMaster, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MenuTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MenuLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel17)
                .addGap(26, 26, 26)
                .addComponent(MenuKoneksiDatabase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MenuManualProgram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MenuManualBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MenuAboutMe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        Card.setBackground(new java.awt.Color(243, 244, 244));
        Card.setLayout(new java.awt.CardLayout());

        Dashboard.setBackground(new java.awt.Color(243, 244, 244));

        jPanel2.setBackground(new java.awt.Color(21, 21, 21));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/TotalPendapatan.png"))); // NOI18N

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(139, 139, 139));
        jLabel25.setText("Total Pendapatan");

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Rp.350.000");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Sale.png"))); // NOI18N

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(139, 139, 139));
        jLabel36.setText("Transaksi Penjualan");

        jLabel60.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(0, 0, 0));
        jLabel60.setText("Rp.1.350.000");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout DashboardLayout = new javax.swing.GroupLayout(Dashboard);
        Dashboard.setLayout(DashboardLayout);
        DashboardLayout.setHorizontalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(713, Short.MAX_VALUE))
        );
        DashboardLayout.setVerticalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(519, Short.MAX_VALUE))
        );

        Card.add(Dashboard, "card2");

        Data_kategori.setBackground(new java.awt.Color(243, 244, 244));

        jPanel3.setBackground(new java.awt.Color(220, 220, 220));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(139, 139, 139));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Jumlah Kategori");

        jml_kategori.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jml_kategori.setForeground(new java.awt.Color(114, 114, 114));
        jml_kategori.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jml_kategori.setText("0 Kategori");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jml_kategori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(jml_kategori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(114, 114, 114));
        jLabel7.setText("Data  Kategori");

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(158, 158, 160));
        jLabel22.setText("Digunalan untuk mendata kategori barang di toko");

        input_idkategori.setBackground(new java.awt.Color(255, 255, 255));
        input_idkategori.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_idkategori.setForeground(new java.awt.Color(114, 114, 114));
        input_idkategori.setBorder(null);

        jLabel45.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(139, 139, 139));
        jLabel45.setText("Nama kategori");

        input_nama_kategori.setBackground(new java.awt.Color(255, 255, 255));
        input_nama_kategori.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_nama_kategori.setForeground(new java.awt.Color(114, 114, 114));
        input_nama_kategori.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel46.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(139, 139, 139));
        jLabel46.setText("Keterangan");

        input_keterangan_kategori.setBackground(new java.awt.Color(255, 255, 255));
        input_keterangan_kategori.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_keterangan_kategori.setForeground(new java.awt.Color(114, 114, 114));
        input_keterangan_kategori.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        btn_tambah_kategori.setBackground(new java.awt.Color(7, 175, 247));
        btn_tambah_kategori.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_tambah_kategori.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah_kategori.setText("Tambah");
        btn_tambah_kategori.setBorder(null);
        btn_tambah_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambah_kategoriActionPerformed(evt);
            }
        });

        btn_batal_kategori.setBackground(new java.awt.Color(7, 175, 247));
        btn_batal_kategori.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_batal_kategori.setForeground(new java.awt.Color(255, 255, 255));
        btn_batal_kategori.setText("Batal");
        btn_batal_kategori.setBorder(null);
        btn_batal_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batal_kategoriActionPerformed(evt);
            }
        });

        btn_hapus_kategori.setBackground(new java.awt.Color(247, 65, 7));
        btn_hapus_kategori.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_hapus_kategori.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus_kategori.setText("Hapus");
        btn_hapus_kategori.setBorder(null);
        btn_hapus_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapus_kategoriActionPerformed(evt);
            }
        });

        btn_ubah_kategori.setBackground(new java.awt.Color(7, 175, 247));
        btn_ubah_kategori.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_ubah_kategori.setForeground(new java.awt.Color(255, 255, 255));
        btn_ubah_kategori.setText("Ubah");
        btn_ubah_kategori.setBorder(null);
        btn_ubah_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubah_kategoriActionPerformed(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(158, 158, 160));
        jLabel61.setText("Jika data kategori sudah dipakai di data barang");

        jLabel62.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(158, 158, 160));
        jLabel62.setText("maka tidak dihapus");

        Combo_status_kategori.setBackground(new java.awt.Color(255, 255, 255));
        Combo_status_kategori.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        Combo_status_kategori.setForeground(new java.awt.Color(114, 114, 114));
        Combo_status_kategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktive", "Tidak aktive" }));
        Combo_status_kategori.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel84.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(139, 139, 139));
        jLabel84.setText("Status");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Combo_status_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel84)
                    .addComponent(jLabel62)
                    .addComponent(jLabel61)
                    .addComponent(jLabel46)
                    .addComponent(jLabel45)
                    .addComponent(jLabel22)
                    .addComponent(jLabel7)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(input_idkategori, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(input_nama_kategori, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(input_keterangan_kategori, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(btn_tambah_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_batal_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_ubah_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_hapus_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(jLabel45)
                .addGap(3, 3, 3)
                .addComponent(input_nama_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel46)
                .addGap(3, 3, 3)
                .addComponent(input_keterangan_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel84)
                .addGap(3, 3, 3)
                .addComponent(Combo_status_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_batal_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ubah_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_hapus_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel61)
                .addGap(0, 0, 0)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(input_idkategori, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Table_data_kategori.setBackground(new java.awt.Color(255, 255, 255));
        Table_data_kategori.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No", "Nama Kategori", "Keterangan", "Status"
            }
        ));
        Table_data_kategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_data_kategoriMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table_data_kategori);
        if (Table_data_kategori.getColumnModel().getColumnCount() > 0) {
            Table_data_kategori.getColumnModel().getColumn(0).setMinWidth(20);
            Table_data_kategori.getColumnModel().getColumn(1).setMinWidth(250);
            Table_data_kategori.getColumnModel().getColumn(2).setMinWidth(150);
        }

        javax.swing.GroupLayout Data_kategoriLayout = new javax.swing.GroupLayout(Data_kategori);
        Data_kategori.setLayout(Data_kategoriLayout);
        Data_kategoriLayout.setHorizontalGroup(
            Data_kategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Data_kategoriLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Data_kategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Data_kategoriLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Data_kategoriLayout.setVerticalGroup(
            Data_kategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Data_kategoriLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(Data_kategoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Data_kategoriLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(Data_kategoriLayout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(132, Short.MAX_VALUE))))
        );

        Card.add(Data_kategori, "card3");

        Jenis_Satuan.setBackground(new java.awt.Color(243, 244, 244));

        jPanel7.setBackground(new java.awt.Color(220, 220, 220));

        jLabel63.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(139, 139, 139));
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("Jumlah Data Satuan");

        Jmlh_Satuan.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        Jmlh_Satuan.setForeground(new java.awt.Color(114, 114, 114));
        Jmlh_Satuan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Jmlh_Satuan.setText("0 Satuan");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
                    .addComponent(Jmlh_Satuan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel63)
                .addGap(0, 0, 0)
                .addComponent(Jmlh_Satuan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel65.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(114, 114, 114));
        jLabel65.setText("Data Satuan");

        jLabel66.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(158, 158, 160));
        jLabel66.setText("Digunalan untuk mendata kategori barang di toko");

        input_id_satuan.setBackground(new java.awt.Color(255, 255, 255));
        input_id_satuan.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_id_satuan.setForeground(new java.awt.Color(114, 114, 114));
        input_id_satuan.setBorder(null);

        jLabel68.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(139, 139, 139));
        jLabel68.setText("Nama Satuan");

        input_nama_satuan.setBackground(new java.awt.Color(255, 255, 255));
        input_nama_satuan.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_nama_satuan.setForeground(new java.awt.Color(114, 114, 114));
        input_nama_satuan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel69.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(139, 139, 139));
        jLabel69.setText("Keterangan");

        input_keterangan_satuan.setBackground(new java.awt.Color(255, 255, 255));
        input_keterangan_satuan.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_keterangan_satuan.setForeground(new java.awt.Color(114, 114, 114));
        input_keterangan_satuan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        btn_tambah_satuan.setBackground(new java.awt.Color(7, 175, 247));
        btn_tambah_satuan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_tambah_satuan.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah_satuan.setText("Tambah");
        btn_tambah_satuan.setBorder(null);
        btn_tambah_satuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambah_satuanActionPerformed(evt);
            }
        });

        btn_batal_satuan.setBackground(new java.awt.Color(7, 175, 247));
        btn_batal_satuan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_batal_satuan.setForeground(new java.awt.Color(255, 255, 255));
        btn_batal_satuan.setText("Batal");
        btn_batal_satuan.setBorder(null);
        btn_batal_satuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batal_satuanActionPerformed(evt);
            }
        });

        btn_hapus_satuan.setBackground(new java.awt.Color(247, 65, 7));
        btn_hapus_satuan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_hapus_satuan.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus_satuan.setText("Hapus");
        btn_hapus_satuan.setBorder(null);
        btn_hapus_satuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapus_satuanActionPerformed(evt);
            }
        });

        btn_ubah_satuan.setBackground(new java.awt.Color(7, 175, 247));
        btn_ubah_satuan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_ubah_satuan.setForeground(new java.awt.Color(255, 255, 255));
        btn_ubah_satuan.setText("Ubah");
        btn_ubah_satuan.setBorder(null);
        btn_ubah_satuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubah_satuanActionPerformed(evt);
            }
        });

        jLabel70.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(158, 158, 160));
        jLabel70.setText("Jika data kategori sudah dipakai di data barang");

        jLabel71.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(158, 158, 160));
        jLabel71.setText("maka tidak dihapus");

        Combo_status_satuan.setBackground(new java.awt.Color(255, 255, 255));
        Combo_status_satuan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        Combo_status_satuan.setForeground(new java.awt.Color(114, 114, 114));
        Combo_status_satuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktive", "Tidak aktive" }));
        Combo_status_satuan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel85.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(139, 139, 139));
        jLabel85.setText("Status");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Combo_status_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel85)
                    .addComponent(jLabel71)
                    .addComponent(jLabel70)
                    .addComponent(jLabel69)
                    .addComponent(jLabel68)
                    .addComponent(jLabel66)
                    .addComponent(jLabel65)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(input_id_satuan, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(input_nama_satuan, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(input_keterangan_satuan, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                            .addComponent(btn_tambah_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_batal_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_ubah_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_hapus_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel65)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel66)
                .addGap(18, 18, 18)
                .addComponent(jLabel68)
                .addGap(3, 3, 3)
                .addComponent(input_nama_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel69)
                .addGap(3, 3, 3)
                .addComponent(input_keterangan_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel85)
                .addGap(1, 1, 1)
                .addComponent(Combo_status_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_batal_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ubah_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_hapus_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel70)
                .addGap(0, 0, 0)
                .addComponent(jLabel71)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(input_id_satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Table_satuan.setBackground(new java.awt.Color(255, 255, 255));
        Table_satuan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No", "Nama Satuan", "Keterangan", "Status"
            }
        ));
        Table_satuan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_satuanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table_satuan);
        if (Table_satuan.getColumnModel().getColumnCount() > 0) {
            Table_satuan.getColumnModel().getColumn(0).setMinWidth(10);
            Table_satuan.getColumnModel().getColumn(1).setMinWidth(250);
            Table_satuan.getColumnModel().getColumn(2).setMinWidth(190);
            Table_satuan.getColumnModel().getColumn(3).setMinWidth(70);
        }

        javax.swing.GroupLayout Jenis_SatuanLayout = new javax.swing.GroupLayout(Jenis_Satuan);
        Jenis_Satuan.setLayout(Jenis_SatuanLayout);
        Jenis_SatuanLayout.setHorizontalGroup(
            Jenis_SatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jenis_SatuanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Jenis_SatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Jenis_SatuanLayout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Jenis_SatuanLayout.setVerticalGroup(
            Jenis_SatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Jenis_SatuanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(Jenis_SatuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Jenis_SatuanLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(Jenis_SatuanLayout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(135, Short.MAX_VALUE))))
        );

        Card.add(Jenis_Satuan, "card4");

        Data_barang.setBackground(new java.awt.Color(243, 244, 244));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel86.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(114, 114, 114));
        jLabel86.setText("Data Barang");

        jLabel87.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(158, 158, 160));
        jLabel87.setText("Digunalan untuk mendata Pelanggan supaya dpt grosir");

        input_kodeDanBarcode_barang.setBackground(new java.awt.Color(255, 255, 255));
        input_kodeDanBarcode_barang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        input_kodeDanBarcode_barang.setForeground(new java.awt.Color(114, 114, 114));
        input_kodeDanBarcode_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel88.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(139, 139, 139));
        jLabel88.setText("Kode/Barcode");

        jLabel89.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(139, 139, 139));
        jLabel89.setText("Nama Barang");

        input_nama_barang.setBackground(new java.awt.Color(255, 255, 255));
        input_nama_barang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        input_nama_barang.setForeground(new java.awt.Color(114, 114, 114));
        input_nama_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        btn_tambah_barang.setBackground(new java.awt.Color(7, 175, 247));
        btn_tambah_barang.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_tambah_barang.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah_barang.setText("Tambah");
        btn_tambah_barang.setBorder(null);
        btn_tambah_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambah_barangActionPerformed(evt);
            }
        });

        btn_batal_barang.setBackground(new java.awt.Color(7, 175, 247));
        btn_batal_barang.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_batal_barang.setForeground(new java.awt.Color(255, 255, 255));
        btn_batal_barang.setText("Batal");
        btn_batal_barang.setBorder(null);
        btn_batal_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batal_barangActionPerformed(evt);
            }
        });

        btn_hapus_barang.setBackground(new java.awt.Color(247, 65, 7));
        btn_hapus_barang.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_hapus_barang.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus_barang.setText("Hapus");
        btn_hapus_barang.setBorder(null);
        btn_hapus_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapus_barangActionPerformed(evt);
            }
        });

        btn_ubah_barang.setBackground(new java.awt.Color(7, 175, 247));
        btn_ubah_barang.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_ubah_barang.setForeground(new java.awt.Color(255, 255, 255));
        btn_ubah_barang.setText("Ubah");
        btn_ubah_barang.setBorder(null);
        btn_ubah_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubah_barangActionPerformed(evt);
            }
        });

        input_hargaBiasa_barang.setBackground(new java.awt.Color(255, 255, 255));
        input_hargaBiasa_barang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        input_hargaBiasa_barang.setForeground(new java.awt.Color(114, 114, 114));
        input_hargaBiasa_barang.setText("0");
        input_hargaBiasa_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel93.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(139, 139, 139));
        jLabel93.setText("Harga biasa");

        combo_satuan_barang.setBackground(new java.awt.Color(255, 255, 255));
        combo_satuan_barang.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        combo_satuan_barang.setForeground(new java.awt.Color(114, 114, 114));
        combo_satuan_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));
        combo_satuan_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_satuan_barangActionPerformed(evt);
            }
        });

        jLabel98.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(139, 139, 139));
        jLabel98.setText("Satuan");

        jLabel99.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(139, 139, 139));
        jLabel99.setText("Kategori");

        combo_kategori_barang.setBackground(new java.awt.Color(255, 255, 255));
        combo_kategori_barang.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        combo_kategori_barang.setForeground(new java.awt.Color(114, 114, 114));
        combo_kategori_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));
        combo_kategori_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_kategori_barangActionPerformed(evt);
            }
        });

        input_hargaJual_barang.setBackground(new java.awt.Color(255, 255, 255));
        input_hargaJual_barang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        input_hargaJual_barang.setForeground(new java.awt.Color(114, 114, 114));
        input_hargaJual_barang.setText("0");
        input_hargaJual_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel100.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(139, 139, 139));
        jLabel100.setText("Harga jual");

        input_hargaGrosir_barang.setBackground(new java.awt.Color(255, 255, 255));
        input_hargaGrosir_barang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        input_hargaGrosir_barang.setForeground(new java.awt.Color(114, 114, 114));
        input_hargaGrosir_barang.setText("0");
        input_hargaGrosir_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel94.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(139, 139, 139));
        jLabel94.setText("Harga Grosir");

        jLabel101.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(139, 139, 139));
        jLabel101.setText("Min beli");

        input_minBeliGrosir_barang.setBackground(new java.awt.Color(255, 255, 255));
        input_minBeliGrosir_barang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        input_minBeliGrosir_barang.setForeground(new java.awt.Color(7, 175, 247));
        input_minBeliGrosir_barang.setText("0");
        input_minBeliGrosir_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        input_diskonHargaPersen_barang.setBackground(new java.awt.Color(255, 255, 255));
        input_diskonHargaPersen_barang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        input_diskonHargaPersen_barang.setForeground(new java.awt.Color(114, 114, 114));
        input_diskonHargaPersen_barang.setText("0");
        input_diskonHargaPersen_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        input_minBeliHarga_barang.setBackground(new java.awt.Color(255, 255, 255));
        input_minBeliHarga_barang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        input_minBeliHarga_barang.setForeground(new java.awt.Color(7, 175, 247));
        input_minBeliHarga_barang.setText("0");
        input_minBeliHarga_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel102.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(139, 139, 139));
        jLabel102.setText("Min beli");

        jLabel95.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(139, 139, 139));
        jLabel95.setText("Diskon harga(%)");

        input_laba_barang.setBackground(new java.awt.Color(255, 255, 255));
        input_laba_barang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        input_laba_barang.setForeground(new java.awt.Color(7, 175, 247));
        input_laba_barang.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        input_laba_barang.setText("0");
        input_laba_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel103.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(139, 139, 139));
        jLabel103.setText("Laba");

        input_stok_barang.setBackground(new java.awt.Color(255, 255, 255));
        input_stok_barang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        input_stok_barang.setForeground(new java.awt.Color(114, 114, 114));
        input_stok_barang.setText("0");
        input_stok_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel90.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(139, 139, 139));
        jLabel90.setText("Stok");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(7, 175, 247));
        jLabel6.setText("%");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(id_satuan_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(id_kategori_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel90)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel87, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(input_stok_barang)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(input_hargaBiasa_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel100)
                                            .addComponent(input_hargaJual_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel93))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel103)
                                    .addComponent(input_laba_barang)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                                .addComponent(input_hargaGrosir_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel101)
                                    .addComponent(input_minBeliGrosir_barang)))
                            .addComponent(jLabel94, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel89, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel88, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel86, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel95, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                                .addComponent(input_diskonHargaPersen_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel102)
                                    .addComponent(input_minBeliHarga_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel99, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel98, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_kategori_barang, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_satuan_barang, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(input_kodeDanBarcode_barang, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(input_nama_barang, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(btn_tambah_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_batal_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_ubah_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_hapus_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(2, 2, 2)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel86)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel87)
                .addGap(21, 21, 21)
                .addComponent(jLabel88)
                .addGap(3, 3, 3)
                .addComponent(input_kodeDanBarcode_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel89)
                .addGap(3, 3, 3)
                .addComponent(input_nama_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel98)
                .addGap(3, 3, 3)
                .addComponent(combo_satuan_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel99)
                .addGap(3, 3, 3)
                .addComponent(combo_kategori_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel93)
                        .addGap(3, 3, 3)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(input_hargaBiasa_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(input_hargaJual_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(input_laba_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel103)
                            .addComponent(jLabel100))
                        .addGap(31, 31, 31)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel94)
                        .addGap(3, 3, 3)
                        .addComponent(input_hargaGrosir_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel101)
                        .addGap(3, 3, 3)
                        .addComponent(input_minBeliGrosir_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel95)
                        .addGap(3, 3, 3)
                        .addComponent(input_diskonHargaPersen_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel102)
                        .addGap(3, 3, 3)
                        .addComponent(input_minBeliHarga_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel90)
                .addGap(3, 3, 3)
                .addComponent(input_stok_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id_kategori_barang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(id_satuan_barang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_tambah_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_batal_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_ubah_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_hapus_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel12.setBackground(new java.awt.Color(220, 220, 220));

        jLabel96.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(139, 139, 139));
        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel96.setText("Jumlah Data Barang");

        Jmlh_barang.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        Jmlh_barang.setForeground(new java.awt.Color(114, 114, 114));
        Jmlh_barang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Jmlh_barang.setText("0 Barang");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel96, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
                    .addComponent(Jmlh_barang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel96)
                .addGap(0, 0, 0)
                .addComponent(Jmlh_barang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Table_barang.setBackground(new java.awt.Color(255, 255, 255));
        Table_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode/barcode", "Nama barang", "Satuan", "Kategori", "Harga jual", "Laba(%)", "Stok"
            }
        ));
        Table_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_barangMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(Table_barang);
        if (Table_barang.getColumnModel().getColumnCount() > 0) {
            Table_barang.getColumnModel().getColumn(0).setMinWidth(20);
            Table_barang.getColumnModel().getColumn(1).setMinWidth(80);
            Table_barang.getColumnModel().getColumn(2).setMinWidth(150);
            Table_barang.getColumnModel().getColumn(3).setMinWidth(80);
            Table_barang.getColumnModel().getColumn(4).setMinWidth(80);
            Table_barang.getColumnModel().getColumn(5).setMinWidth(90);
            Table_barang.getColumnModel().getColumn(6).setMinWidth(40);
            Table_barang.getColumnModel().getColumn(7).setMinWidth(40);
        }

        jLabel104.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(114, 114, 114));
        jLabel104.setText("Cari Barang");

        input_cari_barang.setBackground(new java.awt.Color(255, 255, 255));
        input_cari_barang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        input_cari_barang.setForeground(new java.awt.Color(114, 114, 114));
        input_cari_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));
        input_cari_barang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                input_cari_barangKeyReleased(evt);
            }
        });

        jButton17.setBackground(new java.awt.Color(7, 175, 247));
        jButton17.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/cetakBarcode.png"))); // NOI18N
        jButton17.setText("Cetak barcode");
        jButton17.setBorder(null);
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Data_barangLayout = new javax.swing.GroupLayout(Data_barang);
        Data_barang.setLayout(Data_barangLayout);
        Data_barangLayout.setHorizontalGroup(
            Data_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Data_barangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Data_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Data_barangLayout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(Data_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
                            .addGroup(Data_barangLayout.createSequentialGroup()
                                .addComponent(jLabel104)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(Data_barangLayout.createSequentialGroup()
                                .addComponent(input_cari_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        Data_barangLayout.setVerticalGroup(
            Data_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Data_barangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(Data_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Data_barangLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(Data_barangLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel104)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Data_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(input_cari_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        Card.add(Data_barang, "card5");

        Data_pelanggan.setBackground(new java.awt.Color(243, 244, 244));

        jPanel9.setBackground(new java.awt.Color(220, 220, 220));

        jLabel72.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(139, 139, 139));
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel72.setText("Jumlah Data Pelanggan");

        JmlPelanggan.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        JmlPelanggan.setForeground(new java.awt.Color(114, 114, 114));
        JmlPelanggan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JmlPelanggan.setText("0 Pelanggan");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JmlPelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel72)
                .addGap(0, 0, 0)
                .addComponent(JmlPelanggan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel74.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(114, 114, 114));
        jLabel74.setText("Data Pelanggan");

        jLabel75.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(158, 158, 160));
        jLabel75.setText("Digunalan untuk mendata Pelanggan supaya dpt grosir");

        input_kode_pelanggan.setBackground(new java.awt.Color(255, 255, 255));
        input_kode_pelanggan.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_kode_pelanggan.setForeground(new java.awt.Color(114, 114, 114));
        input_kode_pelanggan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel76.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(139, 139, 139));
        jLabel76.setText("Kode Pelanggan");

        jLabel77.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(139, 139, 139));
        jLabel77.setText("Nama Pelanggan");

        input_nama_pelanggan.setBackground(new java.awt.Color(255, 255, 255));
        input_nama_pelanggan.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_nama_pelanggan.setForeground(new java.awt.Color(114, 114, 114));
        input_nama_pelanggan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel78.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(139, 139, 139));
        jLabel78.setText("Alamat");

        btn_tambah_pelanggan.setBackground(new java.awt.Color(7, 175, 247));
        btn_tambah_pelanggan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_tambah_pelanggan.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah_pelanggan.setText("Tambah");
        btn_tambah_pelanggan.setBorder(null);
        btn_tambah_pelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambah_pelangganActionPerformed(evt);
            }
        });

        btn_batal_pelanggan.setBackground(new java.awt.Color(7, 175, 247));
        btn_batal_pelanggan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_batal_pelanggan.setForeground(new java.awt.Color(255, 255, 255));
        btn_batal_pelanggan.setText("Batal");
        btn_batal_pelanggan.setBorder(null);
        btn_batal_pelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batal_pelangganActionPerformed(evt);
            }
        });

        btn_hapus_pelanggan.setBackground(new java.awt.Color(247, 65, 7));
        btn_hapus_pelanggan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_hapus_pelanggan.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus_pelanggan.setText("Hapus");
        btn_hapus_pelanggan.setBorder(null);
        btn_hapus_pelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapus_pelangganActionPerformed(evt);
            }
        });

        btn_ubah_pelanggan.setBackground(new java.awt.Color(7, 175, 247));
        btn_ubah_pelanggan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_ubah_pelanggan.setForeground(new java.awt.Color(255, 255, 255));
        btn_ubah_pelanggan.setText("Ubah");
        btn_ubah_pelanggan.setBorder(null);
        btn_ubah_pelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubah_pelangganActionPerformed(evt);
            }
        });

        jLabel79.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(158, 158, 160));
        jLabel79.setText("Jika data kategori sudah dipakai di data barang");

        jLabel80.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(158, 158, 160));
        jLabel80.setText("maka tidak dihapus");

        input_alamat_pelanggan.setBackground(new java.awt.Color(255, 255, 255));
        input_alamat_pelanggan.setColumns(20);
        input_alamat_pelanggan.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_alamat_pelanggan.setForeground(new java.awt.Color(114, 114, 114));
        input_alamat_pelanggan.setRows(5);
        input_alamat_pelanggan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));
        jScrollPane4.setViewportView(input_alamat_pelanggan);

        input_noHp_pelanggan.setBackground(new java.awt.Color(255, 255, 255));
        input_noHp_pelanggan.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_noHp_pelanggan.setForeground(new java.awt.Color(114, 114, 114));
        input_noHp_pelanggan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel81.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(139, 139, 139));
        jLabel81.setText("No Hp");

        jLabel82.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(139, 139, 139));
        jLabel82.setText("Email");

        input_email_pelanggan.setBackground(new java.awt.Color(255, 255, 255));
        input_email_pelanggan.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_email_pelanggan.setForeground(new java.awt.Color(114, 114, 114));
        input_email_pelanggan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel83.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(139, 139, 139));
        jLabel83.setText("Status");

        combo_status_pelanggan.setBackground(new java.awt.Color(255, 255, 255));
        combo_status_pelanggan.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        combo_status_pelanggan.setForeground(new java.awt.Color(114, 114, 114));
        combo_status_pelanggan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktive", "Tidak aktive" }));
        combo_status_pelanggan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel80)
                            .addComponent(jLabel79)
                            .addComponent(jLabel78)
                            .addComponent(jLabel77)
                            .addComponent(jLabel76)
                            .addComponent(jLabel74)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(input_kode_pelanggan, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(input_nama_pelanggan, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel10Layout.createSequentialGroup()
                                            .addComponent(btn_tambah_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btn_batal_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btn_ubah_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(combo_status_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btn_hapus_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel81))
                        .addContainerGap())
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel83)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(input_noHp_pelanggan, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addComponent(jLabel82, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(input_email_pelanggan, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel74)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel75)
                .addGap(21, 21, 21)
                .addComponent(jLabel76)
                .addGap(3, 3, 3)
                .addComponent(input_kode_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel77)
                .addGap(3, 3, 3)
                .addComponent(input_nama_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel78)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel81)
                .addGap(3, 3, 3)
                .addComponent(input_noHp_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel82)
                .addGap(3, 3, 3)
                .addComponent(input_email_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel83)
                .addGap(3, 3, 3)
                .addComponent(combo_status_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_batal_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ubah_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_hapus_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel79)
                .addGap(0, 0, 0)
                .addComponent(jLabel80)
                .addContainerGap())
        );

        Table_Pelanggan.setBackground(new java.awt.Color(255, 255, 255));
        Table_Pelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No", "Nama Pelanggan", "Alamat", "No hp", "Status"
            }
        ));
        Table_Pelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_PelangganMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Table_Pelanggan);
        if (Table_Pelanggan.getColumnModel().getColumnCount() > 0) {
            Table_Pelanggan.getColumnModel().getColumn(0).setMinWidth(20);
            Table_Pelanggan.getColumnModel().getColumn(1).setMinWidth(200);
            Table_Pelanggan.getColumnModel().getColumn(2).setMinWidth(150);
            Table_Pelanggan.getColumnModel().getColumn(3).setMinWidth(100);
            Table_Pelanggan.getColumnModel().getColumn(4).setMinWidth(50);
        }

        input_cari_pelanggan.setBackground(new java.awt.Color(255, 255, 255));
        input_cari_pelanggan.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_cari_pelanggan.setForeground(new java.awt.Color(114, 114, 114));
        input_cari_pelanggan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));
        input_cari_pelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                input_cari_pelangganKeyReleased(evt);
            }
        });

        jLabel113.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(114, 114, 114));
        jLabel113.setText("Cari pelanggan");

        javax.swing.GroupLayout Data_pelangganLayout = new javax.swing.GroupLayout(Data_pelanggan);
        Data_pelanggan.setLayout(Data_pelangganLayout);
        Data_pelangganLayout.setHorizontalGroup(
            Data_pelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Data_pelangganLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Data_pelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Data_pelangganLayout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(Data_pelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                            .addGroup(Data_pelangganLayout.createSequentialGroup()
                                .addGroup(Data_pelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel113)
                                    .addComponent(input_cari_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        Data_pelangganLayout.setVerticalGroup(
            Data_pelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Data_pelangganLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(Data_pelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Data_pelangganLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(Data_pelangganLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel113)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input_cari_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        Card.add(Data_pelanggan, "card6");

        Data_users.setBackground(new java.awt.Color(243, 244, 244));

        jPanel13.setBackground(new java.awt.Color(220, 220, 220));

        jLabel91.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(139, 139, 139));
        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel91.setText("Jumlah Data Users");

        Jmlh_users.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        Jmlh_users.setForeground(new java.awt.Color(114, 114, 114));
        Jmlh_users.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Jmlh_users.setText("0 Users");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
                    .addComponent(Jmlh_users, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel91)
                .addGap(0, 0, 0)
                .addComponent(Jmlh_users, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel105.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(114, 114, 114));
        jLabel105.setText("Data Users");

        jLabel106.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(158, 158, 160));
        jLabel106.setText("Digunalan untuk mendata pengguna aplikasi ini");

        input_nama_user.setBackground(new java.awt.Color(255, 255, 255));
        input_nama_user.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_nama_user.setForeground(new java.awt.Color(114, 114, 114));
        input_nama_user.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel107.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(139, 139, 139));
        jLabel107.setText("Nama engkap");

        jLabel108.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(139, 139, 139));
        jLabel108.setText("Username");

        input_username_user.setBackground(new java.awt.Color(255, 255, 255));
        input_username_user.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_username_user.setForeground(new java.awt.Color(114, 114, 114));
        input_username_user.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel109.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(139, 139, 139));
        jLabel109.setText("Password");

        btn_tambah_user.setBackground(new java.awt.Color(7, 175, 247));
        btn_tambah_user.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_tambah_user.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah_user.setText("Tambah");
        btn_tambah_user.setBorder(null);
        btn_tambah_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambah_userActionPerformed(evt);
            }
        });

        btn_batal_user.setBackground(new java.awt.Color(7, 175, 247));
        btn_batal_user.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_batal_user.setForeground(new java.awt.Color(255, 255, 255));
        btn_batal_user.setText("Batal");
        btn_batal_user.setBorder(null);
        btn_batal_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batal_userActionPerformed(evt);
            }
        });

        btn_hapus_user.setBackground(new java.awt.Color(247, 65, 7));
        btn_hapus_user.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_hapus_user.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus_user.setText("Hapus");
        btn_hapus_user.setBorder(null);
        btn_hapus_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapus_userActionPerformed(evt);
            }
        });

        btn_ubah_user.setBackground(new java.awt.Color(7, 175, 247));
        btn_ubah_user.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btn_ubah_user.setForeground(new java.awt.Color(255, 255, 255));
        btn_ubah_user.setText("Ubah");
        btn_ubah_user.setBorder(null);
        btn_ubah_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubah_userActionPerformed(evt);
            }
        });

        jLabel110.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(158, 158, 160));
        jLabel110.setText("Jika data user sudah berelasi dengan yang lain");

        jLabel111.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(158, 158, 160));
        jLabel111.setText("maka tidak bisa dihapus");

        input_noHp_user.setBackground(new java.awt.Color(255, 255, 255));
        input_noHp_user.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_noHp_user.setForeground(new java.awt.Color(114, 114, 114));
        input_noHp_user.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel112.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(139, 139, 139));
        jLabel112.setText("No Hp");

        jLabel114.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(139, 139, 139));
        jLabel114.setText("Status");

        combo_status_user.setBackground(new java.awt.Color(255, 255, 255));
        combo_status_user.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        combo_status_user.setForeground(new java.awt.Color(114, 114, 114));
        combo_status_user.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktive", "Tidak aktive" }));
        combo_status_user.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        input_password_user.setBackground(new java.awt.Color(255, 255, 255));
        input_password_user.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        input_password_user.setForeground(new java.awt.Color(114, 114, 114));
        input_password_user.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        combo_level_user.setBackground(new java.awt.Color(255, 255, 255));
        combo_level_user.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        combo_level_user.setForeground(new java.awt.Color(114, 114, 114));
        combo_level_user.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Karyawan" }));
        combo_level_user.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel115.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(139, 139, 139));
        jLabel115.setText("Level");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel112)
                            .addComponent(jLabel111)
                            .addComponent(jLabel110)
                            .addComponent(jLabel109)
                            .addComponent(jLabel108)
                            .addComponent(jLabel107)
                            .addComponent(jLabel105)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(input_nama_user, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(input_username_user, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                                    .addComponent(btn_tambah_user, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btn_batal_user, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btn_ubah_user, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btn_hapus_user, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(input_noHp_user, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addComponent(combo_status_user, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel114, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel115, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(combo_level_user, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(input_password_user, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(input_id_user, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel105)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel106)
                .addGap(21, 21, 21)
                .addComponent(jLabel107)
                .addGap(3, 3, 3)
                .addComponent(input_nama_user, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel108)
                .addGap(3, 3, 3)
                .addComponent(input_username_user, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel109)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_password_user, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel112)
                .addGap(3, 3, 3)
                .addComponent(input_noHp_user, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel115)
                .addGap(3, 3, 3)
                .addComponent(combo_level_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel114)
                .addGap(3, 3, 3)
                .addComponent(combo_status_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah_user, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_batal_user, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ubah_user, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_hapus_user, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel110)
                .addGap(0, 0, 0)
                .addComponent(jLabel111)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_id_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Table_Users.setBackground(new java.awt.Color(255, 255, 255));
        Table_Users.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nama lengkap", "Username", "No hp", "Level", "Status"
            }
        ));
        Table_Users.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_UsersMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(Table_Users);
        if (Table_Users.getColumnModel().getColumnCount() > 0) {
            Table_Users.getColumnModel().getColumn(0).setMinWidth(20);
            Table_Users.getColumnModel().getColumn(1).setMinWidth(200);
            Table_Users.getColumnModel().getColumn(2).setMinWidth(150);
            Table_Users.getColumnModel().getColumn(3).setMinWidth(100);
            Table_Users.getColumnModel().getColumn(4).setMinWidth(50);
            Table_Users.getColumnModel().getColumn(5).setMinWidth(50);
        }

        javax.swing.GroupLayout Data_usersLayout = new javax.swing.GroupLayout(Data_users);
        Data_users.setLayout(Data_usersLayout);
        Data_usersLayout.setHorizontalGroup(
            Data_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Data_usersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Data_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Data_usersLayout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Data_usersLayout.setVerticalGroup(
            Data_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Data_usersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(Data_usersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Data_usersLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(Data_usersLayout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        Card.add(Data_users, "card7");

        ReturnPenjualan.setBackground(new java.awt.Color(243, 244, 244));

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(51, 255, 51));
        jLabel41.setText("Return Penjualan");

        javax.swing.GroupLayout ReturnPenjualanLayout = new javax.swing.GroupLayout(ReturnPenjualan);
        ReturnPenjualan.setLayout(ReturnPenjualanLayout);
        ReturnPenjualanLayout.setHorizontalGroup(
            ReturnPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReturnPenjualanLayout.createSequentialGroup()
                .addGap(381, 381, 381)
                .addComponent(jLabel41)
                .addContainerGap(390, Short.MAX_VALUE))
        );
        ReturnPenjualanLayout.setVerticalGroup(
            ReturnPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReturnPenjualanLayout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(364, Short.MAX_VALUE))
        );

        Card.add(ReturnPenjualan, "card8");

        StokOpname.setBackground(new java.awt.Color(243, 244, 244));

        jLabel42.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(51, 255, 51));
        jLabel42.setText("Stok opname");

        javax.swing.GroupLayout StokOpnameLayout = new javax.swing.GroupLayout(StokOpname);
        StokOpname.setLayout(StokOpnameLayout);
        StokOpnameLayout.setHorizontalGroup(
            StokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StokOpnameLayout.createSequentialGroup()
                .addGap(381, 381, 381)
                .addComponent(jLabel42)
                .addContainerGap(455, Short.MAX_VALUE))
        );
        StokOpnameLayout.setVerticalGroup(
            StokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StokOpnameLayout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(364, Short.MAX_VALUE))
        );

        Card.add(StokOpname, "card9");

        UpdateHarga.setBackground(new java.awt.Color(243, 244, 244));

        jLabel43.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(51, 255, 51));
        jLabel43.setText("Update harga");

        javax.swing.GroupLayout UpdateHargaLayout = new javax.swing.GroupLayout(UpdateHarga);
        UpdateHarga.setLayout(UpdateHargaLayout);
        UpdateHargaLayout.setHorizontalGroup(
            UpdateHargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpdateHargaLayout.createSequentialGroup()
                .addGap(381, 381, 381)
                .addComponent(jLabel43)
                .addContainerGap(450, Short.MAX_VALUE))
        );
        UpdateHargaLayout.setVerticalGroup(
            UpdateHargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpdateHargaLayout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(364, Short.MAX_VALUE))
        );

        Card.add(UpdateHarga, "card10");

        LaporanBarang.setBackground(new java.awt.Color(243, 244, 244));

        jLabel53.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(51, 255, 51));
        jLabel53.setText("Laporan Barang");

        javax.swing.GroupLayout LaporanBarangLayout = new javax.swing.GroupLayout(LaporanBarang);
        LaporanBarang.setLayout(LaporanBarangLayout);
        LaporanBarangLayout.setHorizontalGroup(
            LaporanBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanBarangLayout.createSequentialGroup()
                .addGap(381, 381, 381)
                .addComponent(jLabel53)
                .addContainerGap(404, Short.MAX_VALUE))
        );
        LaporanBarangLayout.setVerticalGroup(
            LaporanBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanBarangLayout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(364, Short.MAX_VALUE))
        );

        Card.add(LaporanBarang, "card11");

        LaporanStokOpname.setBackground(new java.awt.Color(243, 244, 244));

        jLabel54.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(51, 255, 51));
        jLabel54.setText("Laporan Stok opname");

        javax.swing.GroupLayout LaporanStokOpnameLayout = new javax.swing.GroupLayout(LaporanStokOpname);
        LaporanStokOpname.setLayout(LaporanStokOpnameLayout);
        LaporanStokOpnameLayout.setHorizontalGroup(
            LaporanStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanStokOpnameLayout.createSequentialGroup()
                .addGap(381, 381, 381)
                .addComponent(jLabel54)
                .addContainerGap(302, Short.MAX_VALUE))
        );
        LaporanStokOpnameLayout.setVerticalGroup(
            LaporanStokOpnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanStokOpnameLayout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(364, Short.MAX_VALUE))
        );

        Card.add(LaporanStokOpname, "card12");

        LaporanPenjualan.setBackground(new java.awt.Color(243, 244, 244));

        jLabel55.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(51, 255, 51));
        jLabel55.setText("Laporan Penjualan");

        javax.swing.GroupLayout LaporanPenjualanLayout = new javax.swing.GroupLayout(LaporanPenjualan);
        LaporanPenjualan.setLayout(LaporanPenjualanLayout);
        LaporanPenjualanLayout.setHorizontalGroup(
            LaporanPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanPenjualanLayout.createSequentialGroup()
                .addGap(381, 381, 381)
                .addComponent(jLabel55)
                .addContainerGap(361, Short.MAX_VALUE))
        );
        LaporanPenjualanLayout.setVerticalGroup(
            LaporanPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanPenjualanLayout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(364, Short.MAX_VALUE))
        );

        Card.add(LaporanPenjualan, "card13");

        LaporanReturnPenjualan.setBackground(new java.awt.Color(243, 244, 244));

        jLabel56.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(51, 255, 51));
        jLabel56.setText("Laporan Return Penjualan ");

        javax.swing.GroupLayout LaporanReturnPenjualanLayout = new javax.swing.GroupLayout(LaporanReturnPenjualan);
        LaporanReturnPenjualan.setLayout(LaporanReturnPenjualanLayout);
        LaporanReturnPenjualanLayout.setHorizontalGroup(
            LaporanReturnPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanReturnPenjualanLayout.createSequentialGroup()
                .addGap(381, 381, 381)
                .addComponent(jLabel56)
                .addContainerGap(227, Short.MAX_VALUE))
        );
        LaporanReturnPenjualanLayout.setVerticalGroup(
            LaporanReturnPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanReturnPenjualanLayout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(364, Short.MAX_VALUE))
        );

        Card.add(LaporanReturnPenjualan, "card14");

        LaporanUpdateHarga.setBackground(new java.awt.Color(243, 244, 244));

        jLabel57.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(51, 255, 51));
        jLabel57.setText("Laporan Update Harga");

        javax.swing.GroupLayout LaporanUpdateHargaLayout = new javax.swing.GroupLayout(LaporanUpdateHarga);
        LaporanUpdateHarga.setLayout(LaporanUpdateHargaLayout);
        LaporanUpdateHargaLayout.setHorizontalGroup(
            LaporanUpdateHargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanUpdateHargaLayout.createSequentialGroup()
                .addGap(381, 381, 381)
                .addComponent(jLabel57)
                .addContainerGap(292, Short.MAX_VALUE))
        );
        LaporanUpdateHargaLayout.setVerticalGroup(
            LaporanUpdateHargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanUpdateHargaLayout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(364, Short.MAX_VALUE))
        );

        Card.add(LaporanUpdateHarga, "card15");

        LaporanPenjualanPerPelanggan.setBackground(new java.awt.Color(243, 244, 244));

        jLabel58.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(51, 255, 51));
        jLabel58.setText("Laporan Penjualan perpelanggan");

        javax.swing.GroupLayout LaporanPenjualanPerPelangganLayout = new javax.swing.GroupLayout(LaporanPenjualanPerPelanggan);
        LaporanPenjualanPerPelanggan.setLayout(LaporanPenjualanPerPelangganLayout);
        LaporanPenjualanPerPelangganLayout.setHorizontalGroup(
            LaporanPenjualanPerPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LaporanPenjualanPerPelangganLayout.createSequentialGroup()
                .addContainerGap(284, Short.MAX_VALUE)
                .addComponent(jLabel58)
                .addGap(213, 213, 213))
        );
        LaporanPenjualanPerPelangganLayout.setVerticalGroup(
            LaporanPenjualanPerPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LaporanPenjualanPerPelangganLayout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(364, Short.MAX_VALUE))
        );

        Card.add(LaporanPenjualanPerPelanggan, "card16");

        Laba.setBackground(new java.awt.Color(243, 244, 244));

        jLabel59.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(51, 255, 51));
        jLabel59.setText("Laporan Laba");

        javax.swing.GroupLayout LabaLayout = new javax.swing.GroupLayout(Laba);
        Laba.setLayout(LabaLayout);
        LabaLayout.setHorizontalGroup(
            LabaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LabaLayout.createSequentialGroup()
                .addGap(373, 373, 373)
                .addComponent(jLabel59)
                .addContainerGap(451, Short.MAX_VALUE))
        );
        LabaLayout.setVerticalGroup(
            LabaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LabaLayout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(359, Short.MAX_VALUE))
        );

        Card.add(Laba, "card17");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        Judul.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        Judul.setForeground(new java.awt.Color(0, 0, 0));
        Judul.setText("Dashboard");

        Setting.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Settings.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });

        javax.swing.GroupLayout SettingLayout = new javax.swing.GroupLayout(Setting);
        Setting.setLayout(SettingLayout);
        SettingLayout.setHorizontalGroup(
            SettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );
        SettingLayout.setVerticalGroup(
            SettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(Judul, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Setting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Judul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Setting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Card, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Card, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        Setting.setBackground(new Color(21, 21, 21));
        MenuPanelSetting.show(Setting, 0, Setting.getHeight());
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        Setting.setBackground(Color.WHITE);
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        MenuPanelSetting.show(Setting, 0, Setting.getHeight());
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
        SettingAplikasi.setBackground(Color.WHITE);
    }//GEN-LAST:event_jLabel5MouseExited

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        SettingAplikasi.setBackground(Color.GRAY);
    }//GEN-LAST:event_jLabel5MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        btnLogout.setBackground(Color.WHITE);
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        btnLogout.setBackground(Color.GRAY);
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void txtMenu_MasterMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenu_MasterMouseEntered
        MenuMaster.setBorder(border1);
        MenuPanelMaster.show(MenuMaster, 205, MenuMaster.getX());
    }//GEN-LAST:event_txtMenu_MasterMouseEntered

    private void txtMenu_MasterMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenu_MasterMouseExited
        MenuMaster.setBorder(border2);
    }//GEN-LAST:event_txtMenu_MasterMouseExited

    private void PanelMasterMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PanelMasterMouseExited
//        MenuPanelMaster.show(MenuMaster,0,MenuMaster.getWidth());
    }//GEN-LAST:event_PanelMasterMouseExited

    private void txtMenu_MasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenu_MasterMouseClicked
        MenuPanelMaster.show(MenuMaster, 205, MenuMaster.getX());
    }//GEN-LAST:event_txtMenu_MasterMouseClicked

    private void jLabel31MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseEntered
        MenuKategori.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel31MouseEntered

    private void jLabel31MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseExited
        MenuKategori.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel31MouseExited

    private void jLabel32MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseEntered
        MenuSatuan.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel32MouseEntered

    private void jLabel32MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseExited
        MenuSatuan.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel32MouseExited

    private void jLabel33MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseEntered
        MenuDataBarang.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel33MouseEntered

    private void jLabel33MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseExited
        MenuDataBarang.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel33MouseExited

    private void jLabel34MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel34MouseEntered
        MenuPelanggan.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel34MouseEntered

    private void jLabel34MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel34MouseExited
        MenuPelanggan.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel34MouseExited

    private void jLabel35MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel35MouseEntered
        MenuUsers.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel35MouseEntered

    private void jLabel35MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel35MouseExited
        MenuUsers.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel35MouseExited

    private void jLabel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseClicked
        //Remove Pannel
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(Data_kategori);
        Card.repaint();
        Card.revalidate();

        txtMenu_Master.setForeground(Color.WHITE);
        MenuMaster.setBackground(new Color(76, 78, 72));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Transaksi.setForeground(new Color(139, 139, 139));
        MenuTransaksi.setBackground(new Color(21, 21, 21));

        txtMenu_Laporan.setForeground(new Color(139, 139, 139));
        MenuLaporan.setBackground(new Color(21, 21, 21));

        Judul.setText("Master Data");
    }//GEN-LAST:event_jLabel31MouseClicked

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(Jenis_Satuan);
        Card.repaint();
        Card.revalidate();

        txtMenu_Master.setForeground(Color.WHITE);
        MenuMaster.setBackground(new Color(76, 78, 72));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Transaksi.setForeground(new Color(139, 139, 139));
        MenuTransaksi.setBackground(new Color(21, 21, 21));

        txtMenu_Laporan.setForeground(new Color(139, 139, 139));
        MenuLaporan.setBackground(new Color(21, 21, 21));

        Judul.setText("Master Data");
    }//GEN-LAST:event_jLabel32MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(Data_barang);
        Card.repaint();
        Card.revalidate();

        txtMenu_Master.setForeground(Color.WHITE);
        MenuMaster.setBackground(new Color(76, 78, 72));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Transaksi.setForeground(new Color(139, 139, 139));
        MenuTransaksi.setBackground(new Color(21, 21, 21));

        txtMenu_Laporan.setForeground(new Color(139, 139, 139));
        MenuLaporan.setBackground(new Color(21, 21, 21));

        Judul.setText("Master Data");
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jLabel34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel34MouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(Data_pelanggan);
        Card.repaint();
        Card.revalidate();

        txtMenu_Master.setForeground(Color.WHITE);
        MenuMaster.setBackground(new Color(76, 78, 72));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Transaksi.setForeground(new Color(139, 139, 139));
        MenuTransaksi.setBackground(new Color(21, 21, 21));

        txtMenu_Laporan.setForeground(new Color(139, 139, 139));
        MenuLaporan.setBackground(new Color(21, 21, 21));

        Judul.setText("Master Data");
    }//GEN-LAST:event_jLabel34MouseClicked

    private void jLabel35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel35MouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(Data_users);
        Card.repaint();
        Card.revalidate();

        txtMenu_Master.setForeground(Color.WHITE);
        MenuMaster.setBackground(new Color(76, 78, 72));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Transaksi.setForeground(new Color(139, 139, 139));
        MenuTransaksi.setBackground(new Color(21, 21, 21));

        txtMenu_Laporan.setForeground(new Color(139, 139, 139));
        MenuLaporan.setBackground(new Color(21, 21, 21));

        Judul.setText("Master Data");
    }//GEN-LAST:event_jLabel35MouseClicked

    private void txtMenu_DashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenu_DashboardMouseEntered
        MenuDashboard.setBorder(border1);
    }//GEN-LAST:event_txtMenu_DashboardMouseEntered

    private void txtMenu_DashboardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenu_DashboardMouseExited
        MenuDashboard.setBorder(border2);
    }//GEN-LAST:event_txtMenu_DashboardMouseExited

    private void txtMenu_DashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenu_DashboardMouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(Dashboard);
        Card.repaint();
        Card.revalidate();
        txtMenu_Dashboard.setForeground(Color.WHITE);
        MenuDashboard.setBackground(new Color(76, 78, 72));

        txtMenu_Master.setForeground(new Color(139, 139, 139));
        MenuMaster.setBackground(new Color(21, 21, 21));

        txtMenu_Transaksi.setForeground(new Color(139, 139, 139));
        MenuTransaksi.setBackground(new Color(21, 21, 21));

        txtMenu_Laporan.setForeground(new Color(139, 139, 139));
        MenuLaporan.setBackground(new Color(21, 21, 21));

        Judul.setText("Dashboard");
    }//GEN-LAST:event_txtMenu_DashboardMouseClicked

    private void txtMenu_TransaksiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenu_TransaksiMouseEntered
        MenuTransaksi.setBorder(border1);
        MenuPanelTransaksi.show(MenuTransaksi, 205, MenuTransaksi.getX());
    }//GEN-LAST:event_txtMenu_TransaksiMouseEntered

    private void txtMenu_TransaksiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenu_TransaksiMouseExited
        MenuTransaksi.setBorder(border2);
    }//GEN-LAST:event_txtMenu_TransaksiMouseExited

    private void txtMenu_LaporanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenu_LaporanMouseEntered
        MenuLaporan.setBorder(border1);
        MenuPanelLaporan.show(MenuLaporan, 205, MenuLaporan.getX());
    }//GEN-LAST:event_txtMenu_LaporanMouseEntered

    private void txtMenu_LaporanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenu_LaporanMouseExited
        MenuLaporan.setBorder(border2);
    }//GEN-LAST:event_txtMenu_LaporanMouseExited

    private void jLabel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseEntered
        MenuTransaksiPenjualan.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel15MouseEntered

    private void jLabel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseExited
        MenuTransaksiPenjualan.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel15MouseExited

    private void jLabel16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseEntered
        MenuReturnPenjualan.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel16MouseEntered

    private void jLabel16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseExited
        MenuReturnPenjualan.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel16MouseExited

    private void jLabel40MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseEntered
        MenuStokOpname.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel40MouseEntered

    private void jLabel40MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseExited
        MenuStokOpname.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel40MouseExited

    private void jLabel39MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseEntered
        MenuUpdateHarga.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel39MouseEntered

    private void jLabel39MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseExited
        MenuUpdateHarga.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel39MouseExited

    private void txtMenu_TransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenu_TransaksiMouseClicked
        MenuPanelTransaksi.show(MenuTransaksi, 205, MenuTransaksi.getX());
    }//GEN-LAST:event_txtMenu_TransaksiMouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(ReturnPenjualan);
        Card.repaint();
        Card.revalidate();
        txtMenu_Transaksi.setForeground(Color.WHITE);
        MenuTransaksi.setBackground(new Color(76, 78, 72));

        txtMenu_Master.setForeground(new Color(139, 139, 139));
        MenuMaster.setBackground(new Color(21, 21, 21));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Laporan.setForeground(new Color(139, 139, 139));
        MenuLaporan.setBackground(new Color(21, 21, 21));

        Judul.setText("Transaksi");
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(StokOpname);
        Card.repaint();
        Card.revalidate();
        txtMenu_Transaksi.setForeground(Color.WHITE);
        MenuTransaksi.setBackground(new Color(76, 78, 72));

        txtMenu_Master.setForeground(new Color(139, 139, 139));
        MenuMaster.setBackground(new Color(21, 21, 21));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Laporan.setForeground(new Color(139, 139, 139));
        MenuLaporan.setBackground(new Color(21, 21, 21));

        Judul.setText("Transaksi");
    }//GEN-LAST:event_jLabel40MouseClicked

    private void jLabel39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(UpdateHarga);
        Card.repaint();
        Card.revalidate();
        txtMenu_Transaksi.setForeground(Color.WHITE);
        MenuTransaksi.setBackground(new Color(76, 78, 72));

        txtMenu_Master.setForeground(new Color(139, 139, 139));
        MenuMaster.setBackground(new Color(21, 21, 21));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Laporan.setForeground(new Color(139, 139, 139));
        MenuLaporan.setBackground(new Color(21, 21, 21));

        Judul.setText("Transaksi");
    }//GEN-LAST:event_jLabel39MouseClicked

    private void jLabel44MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseEntered
        MenuLaporanBarang.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel44MouseEntered

    private void jLabel44MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseExited
        MenuLaporanBarang.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel44MouseExited

    private void jLabel47MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseEntered
        MenuLaporanStokOpname.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel47MouseEntered

    private void jLabel47MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseExited
        MenuLaporanStokOpname.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel47MouseExited

    private void jLabel48MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseEntered
        MenuLaporanPenjualan.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel48MouseEntered

    private void jLabel48MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseExited
        MenuLaporanPenjualan.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel48MouseExited

    private void jLabel49MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseEntered
        MenuLaporanReturnPenjualan.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel49MouseEntered

    private void jLabel49MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseExited
        MenuLaporanReturnPenjualan.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel49MouseExited

    private void jLabel50MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseEntered
        MenuLaporanUpdateHarga.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel50MouseEntered

    private void jLabel50MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseExited
        MenuLaporanUpdateHarga.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel50MouseExited

    private void jLabel51MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseEntered
        MenuLaporanPenjualan_perPelanggan.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel51MouseEntered

    private void jLabel51MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseExited
        MenuLaporanPenjualan_perPelanggan.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel51MouseExited

    private void jLabel52MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseEntered
        MenuLaporanLaba.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_jLabel52MouseEntered

    private void jLabel52MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseExited
        MenuLaporanLaba.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_jLabel52MouseExited

    private void txtMenu_LaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMenu_LaporanMouseClicked
        MenuPanelLaporan.show(MenuLaporan, 205, MenuLaporan.getX());
    }//GEN-LAST:event_txtMenu_LaporanMouseClicked

    private void jLabel44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(LaporanBarang);
        Card.repaint();
        Card.revalidate();
        txtMenu_Laporan.setForeground(Color.WHITE);
        MenuLaporan.setBackground(new Color(76, 78, 72));

        txtMenu_Master.setForeground(new Color(139, 139, 139));
        MenuMaster.setBackground(new Color(21, 21, 21));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Transaksi.setForeground(new Color(139, 139, 139));
        MenuTransaksi.setBackground(new Color(21, 21, 21));

        Judul.setText("Laporan");
    }//GEN-LAST:event_jLabel44MouseClicked

    private void jLabel47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(LaporanStokOpname);
        Card.repaint();
        Card.revalidate();
        txtMenu_Laporan.setForeground(Color.WHITE);
        MenuLaporan.setBackground(new Color(76, 78, 72));

        txtMenu_Master.setForeground(new Color(139, 139, 139));
        MenuMaster.setBackground(new Color(21, 21, 21));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Transaksi.setForeground(new Color(139, 139, 139));
        MenuTransaksi.setBackground(new Color(21, 21, 21));

        Judul.setText("Laporan");
    }//GEN-LAST:event_jLabel47MouseClicked

    private void jLabel48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(LaporanPenjualan);
        Card.repaint();
        Card.revalidate();
        txtMenu_Laporan.setForeground(Color.WHITE);
        MenuLaporan.setBackground(new Color(76, 78, 72));

        txtMenu_Master.setForeground(new Color(139, 139, 139));
        MenuMaster.setBackground(new Color(21, 21, 21));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Transaksi.setForeground(new Color(139, 139, 139));
        MenuTransaksi.setBackground(new Color(21, 21, 21));

        Judul.setText("Laporan");
    }//GEN-LAST:event_jLabel48MouseClicked

    private void jLabel49MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(LaporanReturnPenjualan);
        Card.repaint();
        Card.revalidate();
        txtMenu_Laporan.setForeground(Color.WHITE);
        MenuLaporan.setBackground(new Color(76, 78, 72));

        txtMenu_Master.setForeground(new Color(139, 139, 139));
        MenuMaster.setBackground(new Color(21, 21, 21));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Transaksi.setForeground(new Color(139, 139, 139));
        MenuTransaksi.setBackground(new Color(21, 21, 21));

        Judul.setText("Laporan");
    }//GEN-LAST:event_jLabel49MouseClicked

    private void jLabel50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(LaporanUpdateHarga);
        Card.repaint();
        Card.revalidate();
        txtMenu_Laporan.setForeground(Color.WHITE);
        MenuLaporan.setBackground(new Color(76, 78, 72));

        txtMenu_Master.setForeground(new Color(139, 139, 139));
        MenuMaster.setBackground(new Color(21, 21, 21));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Transaksi.setForeground(new Color(139, 139, 139));
        MenuTransaksi.setBackground(new Color(21, 21, 21));

        Judul.setText("Laporan");
    }//GEN-LAST:event_jLabel50MouseClicked

    private void jLabel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(LaporanPenjualanPerPelanggan);
        Card.repaint();
        Card.revalidate();
        txtMenu_Laporan.setForeground(Color.WHITE);
        MenuLaporan.setBackground(new Color(76, 78, 72));

        txtMenu_Master.setForeground(new Color(139, 139, 139));
        MenuMaster.setBackground(new Color(21, 21, 21));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Transaksi.setForeground(new Color(139, 139, 139));
        MenuTransaksi.setBackground(new Color(21, 21, 21));

        Judul.setText("Laporan");
    }//GEN-LAST:event_jLabel51MouseClicked

    private void jLabel52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseClicked
        Card.removeAll();
        Card.repaint();
        Card.revalidate();

        //Add Pannel      
        Card.add(Laba);
        Card.repaint();
        Card.revalidate();
        txtMenu_Laporan.setForeground(Color.WHITE);
        MenuLaporan.setBackground(new Color(76, 78, 72));

        txtMenu_Master.setForeground(new Color(139, 139, 139));
        MenuMaster.setBackground(new Color(21, 21, 21));

        txtMenu_Dashboard.setForeground(new Color(139, 139, 139));
        MenuDashboard.setBackground(new Color(21, 21, 21));

        txtMenu_Transaksi.setForeground(new Color(139, 139, 139));
        MenuTransaksi.setBackground(new Color(21, 21, 21));

        Judul.setText("Laporan");
    }//GEN-LAST:event_jLabel52MouseClicked

    private void txtKoneksiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtKoneksiMouseEntered
        txtKoneksi.setForeground(Color.WHITE);
        MenuKoneksiDatabase.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_txtKoneksiMouseEntered

    private void txtKoneksiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtKoneksiMouseExited
        txtKoneksi.setForeground(new Color(139, 139, 139));
        MenuKoneksiDatabase.setBackground(new Color(21, 21, 21));
    }//GEN-LAST:event_txtKoneksiMouseExited

    private void txtManualProgramMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtManualProgramMouseEntered
        txtManualProgram.setForeground(Color.WHITE);
        MenuManualProgram.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_txtManualProgramMouseEntered

    private void txtManualProgramMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtManualProgramMouseExited
        txtManualProgram.setForeground(new Color(139, 139, 139));
        MenuManualProgram.setBackground(new Color(21, 21, 21));
    }//GEN-LAST:event_txtManualProgramMouseExited

    private void txtManualBookMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtManualBookMouseEntered
        txtManualBook.setForeground(Color.WHITE);
        MenuManualBook.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_txtManualBookMouseEntered

    private void txtManualBookMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtManualBookMouseExited
        txtManualBook.setForeground(new Color(139, 139, 139));
        MenuManualBook.setBackground(new Color(21, 21, 21));
    }//GEN-LAST:event_txtManualBookMouseExited

    private void txtAboutMeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAboutMeMouseEntered
        txtAboutMe.setForeground(Color.WHITE);
        MenuAboutMe.setBackground(new Color(76, 78, 72));
    }//GEN-LAST:event_txtAboutMeMouseEntered

    private void txtAboutMeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAboutMeMouseExited
        txtAboutMe.setForeground(new Color(139, 139, 139));
        MenuAboutMe.setBackground(new Color(21, 21, 21));
    }//GEN-LAST:event_txtAboutMeMouseExited

    private void txtKoneksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtKoneksiMouseClicked
        try {
            Runtime.getRuntime().exec("C:\\xampp\\xampp-control.exe");
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtKoneksiMouseClicked

    private void txtManualProgramMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtManualProgramMouseClicked
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/channel/UCT3HX48RW0Vx7wt8mfRoAvg"));
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtManualProgramMouseClicked

    private void btn_tambah_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambah_kategoriActionPerformed
        try {
            String sql = "Insert into data_kategori (nama_kategori,keterangan,status) values "
                    + "('" + input_nama_kategori.getText() + "',"
                    + "'" + input_keterangan_kategori.getText() + "','" + Combo_status_kategori.getSelectedItem() + "')";
            conn = (Connection) Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Berhasil");
            ClearKategori();
            LoadTable_kategori();
            btn_tambah_kategori.setEnabled(true);
            btn_ubah_kategori.setEnabled(false);
            btn_hapus_kategori.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }//GEN-LAST:event_btn_tambah_kategoriActionPerformed

    private void btn_batal_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batal_kategoriActionPerformed
        ClearKategori();
        btn_tambah_kategori.setEnabled(true);
        btn_ubah_kategori.setEnabled(false);
        btn_hapus_kategori.setEnabled(false);
    }//GEN-LAST:event_btn_batal_kategoriActionPerformed

    private void btn_hapus_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapus_kategoriActionPerformed
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah kategori " + input_nama_kategori.getText() + " ingin dihapus!!!");
        switch (jawab) {
            case JOptionPane.YES_OPTION:
                try {
                    String sql = "Delete from data_kategori where id_kategori='" + input_idkategori.getText() + "'";
                    conn = (Connection) Config.ConfigDB();
                    pst = conn.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Berhasil Dihapus");
                    LoadTable_kategori();
                    ClearKategori();
                    btn_tambah_kategori.setEnabled(true);
                    btn_ubah_kategori.setEnabled(false);
                    btn_hapus_kategori.setEnabled(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
                break;
            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(null, "Membatalkan Penghapusan");
                break;
            case JOptionPane.CANCEL_OPTION:
                break;
        }

    }//GEN-LAST:event_btn_hapus_kategoriActionPerformed

    private void btn_ubah_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubah_kategoriActionPerformed
        try {
            String sql = "Update data_kategori set nama_kategori='" + input_nama_kategori.getText() + "',keterangan='" + input_keterangan_kategori.getText() + "',"
                    + "status='" + Combo_status_kategori.getSelectedItem() + "' where id_kategori='" + input_idkategori.getText() + "'";
            conn = (Connection) Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Berhasil Merubah");
            LoadTable_kategori();
            ClearKategori();
            btn_tambah_kategori.setEnabled(true);
            btn_ubah_kategori.setEnabled(false);
            btn_hapus_kategori.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }//GEN-LAST:event_btn_ubah_kategoriActionPerformed

    private void btn_tambah_satuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambah_satuanActionPerformed
        try {
            String sql = "insert into data_satuan (nama_satuan,keterangan,status) values ('" + input_nama_satuan.getText() + "',"
                    + "'" + input_keterangan_satuan.getText() + "','" + Combo_status_satuan.getSelectedItem() + "')";
            conn = (Connection) Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Berhasil menamhkan data");
            LoadTable_satuan();
            ClearSatuan();
            btn_tambah_satuan.setEnabled(true);
            btn_ubah_satuan.setEnabled(false);
            btn_hapus_satuan.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_tambah_satuanActionPerformed

    private void btn_batal_satuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batal_satuanActionPerformed
        ClearSatuan();
        btn_tambah_satuan.setEnabled(true);
        btn_ubah_satuan.setEnabled(false);
        btn_hapus_satuan.setEnabled(false);
    }//GEN-LAST:event_btn_batal_satuanActionPerformed

    private void btn_hapus_satuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapus_satuanActionPerformed
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah kategori " + input_nama_satuan.getText() + " ingin dihapus!!!");
        switch (jawab) {
            case JOptionPane.YES_OPTION:
                try {
                    String sql = "Delete from data_satuan where id_satuan='" + input_id_satuan.getText() + "'";
                    conn = (Connection) Config.ConfigDB();
                    pst = conn.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Berhasil Dihapus");
                    LoadTable_satuan();
                    ClearSatuan();
                    btn_tambah_satuan.setEnabled(true);
                    btn_ubah_satuan.setEnabled(false);
                    btn_hapus_satuan.setEnabled(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
                break;
            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(null, "Membatalkan Penghapusan");
                break;
            case JOptionPane.CANCEL_OPTION:
                break;
        }
    }//GEN-LAST:event_btn_hapus_satuanActionPerformed

    private void btn_ubah_satuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubah_satuanActionPerformed
        try {
            String sql = "update data_satuan set nama_satuan='"+input_nama_satuan.getText()+"',"
                + "keterangan='"+input_keterangan_satuan.getText()+"',status='"+Combo_status_satuan.getSelectedItem()+"'"
                + "where id_satuan='"+input_id_satuan.getText()+"'";
            conn = (Connection) Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Berhasil diubah");
            LoadTable_satuan();
            ClearSatuan();
            btn_tambah_satuan.setEnabled(true);
            btn_ubah_satuan.setEnabled(false);
            btn_hapus_satuan.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btn_ubah_satuanActionPerformed

    private void btn_tambah_pelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambah_pelangganActionPerformed
        try {
            String sql = "Insert into pelanggan (kode_pelanggan,nama_pelanggan,alamat,no_hp,email,status) values "
                    + "('"+input_kode_pelanggan.getText()+"','"+input_nama_pelanggan.getText()+"',"
                    + "'"+input_alamat_pelanggan.getText()+"','"+input_noHp_pelanggan.getText()+"',"
                    + "'"+input_email_pelanggan.getText()+"','"+combo_status_pelanggan.getSelectedItem()+"')";
            conn = (Connection)Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Berhasil Menambahkan");
            LoadTable_pelanggan();
            ClearPelanggan();
            btn_tambah_pelanggan.setEnabled(true);
            btn_ubah_pelanggan.setEnabled(false);
            btn_hapus_pelanggan.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_tambah_pelangganActionPerformed

    private void btn_batal_pelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batal_pelangganActionPerformed
            ClearPelanggan();
            btn_tambah_pelanggan.setEnabled(true);
            btn_ubah_pelanggan.setEnabled(false);
            btn_hapus_pelanggan.setEnabled(false);
    }//GEN-LAST:event_btn_batal_pelangganActionPerformed

    private void btn_hapus_pelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapus_pelangganActionPerformed
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah pelanggan " + input_nama_pelanggan.getText() + " ingin dihapus!!!");
        switch (jawab) {
            case JOptionPane.YES_OPTION:
                try {
                    String sql = "Delete from pelanggan where kode_pelanggan='" + input_kode_pelanggan.getText() + "'";
                    conn = (Connection) Config.ConfigDB();
                    pst = conn.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Berhasil Dihapus");
                    LoadTable_pelanggan();
                    ClearPelanggan();
                    btn_tambah_pelanggan.setEnabled(true);
                    btn_ubah_pelanggan.setEnabled(false);
                    btn_hapus_pelanggan.setEnabled(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
                break;
            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(null, "Membatalkan Penghapusan");
                break;
            case JOptionPane.CANCEL_OPTION:
                break;
        }
    }//GEN-LAST:event_btn_hapus_pelangganActionPerformed

    private void btn_ubah_pelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubah_pelangganActionPerformed
        try {
            String sql = "Update pelanggan set nama_pelanggan='"+input_nama_pelanggan.getText()+"',"
                    + "alamat='"+input_alamat_pelanggan.getText()+"',no_hp='"+input_noHp_pelanggan.getText()+"',"
                    + "email='"+input_email_pelanggan.getText()+"',status='"+combo_status_pelanggan.getSelectedItem()+"' "
                    + "where kode_pelanggan='"+input_kode_pelanggan.getText()+"'";
            conn = (Connection) Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Berhasil Mengubah data pelanggan");
            LoadTable_pelanggan();
            ClearPelanggan();
            btn_tambah_pelanggan.setEnabled(true);
            btn_ubah_pelanggan.setEnabled(false);
            btn_hapus_pelanggan.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_ubah_pelangganActionPerformed

    private void Table_data_kategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_data_kategoriMouseClicked
        try {
            int row = Table_data_kategori.getSelectedRow();
            String tabel_klik = (Table_data_kategori.getModel().getValueAt(row, 1).toString());
            conn = (java.sql.Connection) Config.ConfigDB();
            st = conn.createStatement();
            java.sql.ResultSet sql = st.executeQuery("Select * from data_kategori where nama_kategori='"+tabel_klik+"'");
            if (sql.next()) {
                input_idkategori.setText(sql.getString("id_kategori"));
                input_nama_kategori.setText(sql.getString("nama_kategori"));
                input_keterangan_kategori.setText(sql.getString("keterangan"));
                Combo_status_kategori.setSelectedItem(sql.getString("status"));
            }
            btn_tambah_kategori.setEnabled(false);
            btn_ubah_kategori.setEnabled(true);
            btn_hapus_kategori.setEnabled(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_Table_data_kategoriMouseClicked

    private void Table_satuanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_satuanMouseClicked
        try {
            int row = Table_satuan.getSelectedRow();
            String tabel_klik = (Table_satuan.getModel().getValueAt(row, 1).toString());
            conn = (java.sql.Connection) Config.ConfigDB();
            st = conn.createStatement();
            java.sql.ResultSet sql = st.executeQuery("Select * from data_satuan where nama_satuan='"+tabel_klik+"'");
            if (sql.next()) {
                input_id_satuan.setText(sql.getString("id_satuan"));
                input_nama_satuan.setText(sql.getString("nama_satuan"));
                input_keterangan_satuan.setText(sql.getString("keterangan"));
                Combo_status_satuan.setSelectedItem(sql.getString("status"));
            }
            btn_tambah_satuan.setEnabled(false);
            btn_ubah_satuan.setEnabled(true);
            btn_hapus_satuan.setEnabled(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_Table_satuanMouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        TransaksiPenjualan n = new TransaksiPenjualan();
        n.setVisible(true);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void btn_tambah_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambah_barangActionPerformed
        try {
            String sql = "INSERT INTO barang (kode_dan_barcode, nama_barang, id_satuan, id_kategori,harga_biasa, harga_jual, laba, harga_grosir, "
                    + "minimal_beli_grosir, diskon_jual, minimal_beliDiskon_jual, stok) VALUES "
                    + "('"+input_kodeDanBarcode_barang.getText()+"','"+input_nama_barang.getText()+"','"+id_satuan_barang.getText()+"',"
                    + "'"+id_kategori_barang.getText()+"','"+input_hargaBiasa_barang.getText()+"','"+input_hargaJual_barang.getText()+"','"+input_laba_barang.getText()+"',"
                    + "'"+input_hargaGrosir_barang.getText()+"','"+input_minBeliGrosir_barang.getText()+"','"+input_diskonHargaPersen_barang.getText()+"',"
                    + "'"+input_minBeliHarga_barang.getText()+"','"+input_stok_barang.getText()+"')";
            conn = (Connection)Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Berhasil Menambahkan data barang");
            LoadTable_barang();
            ClearBarang();
            input_kodeDanBarcode_barang.setEnabled(true);
                    btn_tambah_barang.setEnabled(true);
                    btn_ubah_barang.setEnabled(false);
                    btn_hapus_barang.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btn_tambah_barangActionPerformed

    private void btn_batal_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batal_barangActionPerformed
            ClearBarang();
            input_kodeDanBarcode_barang.setEnabled(true);
                    btn_tambah_barang.setEnabled(true);
                    btn_ubah_barang.setEnabled(false);
                    btn_hapus_barang.setEnabled(false);
    }//GEN-LAST:event_btn_batal_barangActionPerformed

    private void btn_hapus_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapus_barangActionPerformed
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah barang " + input_nama_barang.getText() + " ingin dihapus!!!");
        switch (jawab) {
            case JOptionPane.YES_OPTION:
                try {
                    String sql = "Delete from barang where kode_dan_barcode='" + input_kodeDanBarcode_barang.getText() + "'";
                    conn = (Connection) Config.ConfigDB();
                    pst = conn.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Berhasil Dihapus");
                    LoadTable_barang();
                    ClearBarang();
                    input_kodeDanBarcode_barang.setEnabled(true);
                    btn_tambah_barang.setEnabled(true);
                    btn_ubah_barang.setEnabled(false);
                    btn_hapus_barang.setEnabled(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
                break;
            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(null, "Membatalkan Penghapusan");
                break;
            case JOptionPane.CANCEL_OPTION:
                break;
        }
    }//GEN-LAST:event_btn_hapus_barangActionPerformed

    private void btn_ubah_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubah_barangActionPerformed
        try {
            String sql = "update barang set kode_dan_barcode='"+input_kodeDanBarcode_barang.getText()+"',nama_barang='"+input_nama_barang.getText()+"',"
                    + "id_satuan='"+id_satuan_barang.getText()+"',id_kategori='"+id_kategori_barang.getText()+"',harga_biasa='"+input_hargaBiasa_barang.getText()+"',"
                    + "harga_jual='"+input_hargaJual_barang.getText()+"',laba='"+input_laba_barang.getText()+"',harga_grosir='"+input_hargaGrosir_barang.getText()+"',"
                    + "minimal_beli_grosir='"+input_minBeliGrosir_barang.getText()+"',diskon_jual='"+input_diskonHargaPersen_barang.getText()+"',"
                    + "minimal_beliDiskon_jual='"+input_minBeliHarga_barang.getText()+"', stok='"+input_stok_barang.getText()+"' "
                    + "where kode_dan_barcode='"+input_kodeDanBarcode_barang.getText()+"'";
            conn = (Connection)Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Berhasil Merubah data barang");
            LoadTable_barang();
            ClearBarang();
            input_kodeDanBarcode_barang.setEnabled(true);
                    btn_tambah_barang.setEnabled(true);
                    btn_ubah_barang.setEnabled(false);
                    btn_hapus_barang.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_ubah_barangActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton17ActionPerformed

    private void btn_tambah_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambah_userActionPerformed
        try {
            String sql = "insert into data_user (nama_user,username,password,no_hp,level,status) values "
                    + "('"+input_nama_user.getText()+"','"+input_username_user.getText()+"','"+input_password_user.getText()+"',"
                    + "'"+input_noHp_user.getText()+"','"+combo_level_user.getSelectedItem()+"','"+combo_status_user.getSelectedItem()+"')";
            conn = (Connection)Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Berhasil Menambahkan");
            LoadTable_Users();
            ClearUser();
            btn_tambah_user.setEnabled(true);
            btn_ubah_user.setEnabled(false);
            btn_hapus_user.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_tambah_userActionPerformed

    private void btn_batal_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batal_userActionPerformed
        ClearUser();
        btn_tambah_user.setEnabled(true);
        btn_ubah_user.setEnabled(false);
        btn_hapus_user.setEnabled(false);
    }//GEN-LAST:event_btn_batal_userActionPerformed

    private void btn_hapus_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapus_userActionPerformed
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah User " + input_nama_user.getText() + " ingin dihapus!!!");
        switch (jawab) {
            case JOptionPane.YES_OPTION:
                try {
                    String sql = "Delete from data_user where id_user='" + input_id_user.getText() + "'";
                    conn = (Connection) Config.ConfigDB();
                    pst = conn.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Berhasil Dihapus");
                    LoadTable_Users();
                    ClearUser();
                    btn_tambah_user.setEnabled(true);
                    btn_ubah_user.setEnabled(false);
                    btn_hapus_user.setEnabled(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
                break;
            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(null, "Membatalkan Penghapusan");
                break;
            case JOptionPane.CANCEL_OPTION:
                break;
        }
    }//GEN-LAST:event_btn_hapus_userActionPerformed

    private void btn_ubah_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubah_userActionPerformed
        try {
            String sql = "Update data_user set nama_user='"+input_nama_user.getText()+"',username='"+input_username_user.getText()+"',"
                    + "password='"+input_password_user.getText()+"',no_hp='"+input_noHp_user.getText()+"',"
                    + "level='"+combo_level_user.getSelectedItem()+"',status='"+combo_status_user.getSelectedItem()+"' "
                    + "where id_user='"+input_id_user.getText()+"'";
            conn = (Connection)Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Berhasil Merubah data user");
            LoadTable_Users();
            ClearUser();
            btn_tambah_user.setEnabled(true);
            btn_ubah_user.setEnabled(false);
            btn_hapus_user.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_ubah_userActionPerformed

    private void Table_PelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_PelangganMouseClicked
       try {
            int row = Table_Pelanggan.getSelectedRow();
            String tabel_klik = (Table_Pelanggan.getModel().getValueAt(row, 1).toString());
            String noHp = (Table_Pelanggan.getModel().getValueAt(row, 3).toString());
            conn = (java.sql.Connection) Config.ConfigDB();
            st = conn.createStatement();
            java.sql.ResultSet sql = st.executeQuery("Select * from pelanggan where nama_pelanggan='"+tabel_klik+"' And no_hp='"+noHp+"'");
            if (sql.next()) {
                input_kode_pelanggan.setText(sql.getString("kode_pelanggan"));
                input_nama_pelanggan.setText(sql.getString("nama_pelanggan"));
                input_alamat_pelanggan.setText(sql.getString("alamat"));
                input_noHp_pelanggan.setText(sql.getString("no_hp"));
                input_email_pelanggan.setText(sql.getString("email"));
                combo_status_pelanggan.setSelectedItem(sql.getString("status"));
            }
            btn_tambah_pelanggan.setEnabled(false);
            btn_ubah_pelanggan.setEnabled(true);
            btn_hapus_pelanggan.setEnabled(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_Table_PelangganMouseClicked

    private void input_cari_pelangganKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_cari_pelangganKeyReleased
        LoadTable_pelanggan();
    }//GEN-LAST:event_input_cari_pelangganKeyReleased

    private void Table_UsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_UsersMouseClicked
        try {
            int row = Table_Users.getSelectedRow();
            String tabel_klik = (Table_Users.getModel().getValueAt(row, 1).toString());
            String noHp = (Table_Users.getModel().getValueAt(row, 3).toString());
            conn = (java.sql.Connection) Config.ConfigDB();
            st = conn.createStatement();
            java.sql.ResultSet sql = st.executeQuery("Select * from data_user where nama_user='"+tabel_klik+"' And no_hp='"+noHp+"'");
            if (sql.next()) {
                input_id_user.setText(sql.getString("id_user"));
                input_nama_user.setText(sql.getString("nama_user"));
                input_username_user.setText(sql.getString("username"));
                input_password_user.setText(sql.getString("password"));
                input_noHp_user.setText(sql.getString("no_hp"));
                combo_level_user.setSelectedItem(sql.getString("level"));
                combo_status_user.setSelectedItem(sql.getString("status"));
            }
            btn_tambah_user.setEnabled(false);
            btn_ubah_user.setEnabled(true);
            btn_hapus_user.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_Table_UsersMouseClicked

    private void combo_satuan_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_satuan_barangActionPerformed
        
        try {
            String sql1 = "SELECT id_satuan FROM data_satuan WHERE nama_satuan LIKE '"+combo_satuan_barang.getSelectedItem()+"'";
                java.sql.Connection conna=(Connection) Config.ConfigDB();
                java.sql.PreparedStatement pst1=conna.prepareStatement(sql1);
                ResultSet rs=pst1.executeQuery();
                while(rs.next()){
                    id_satuan_barang.setText(rs.getString("id_satuan"));
                }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_combo_satuan_barangActionPerformed

    private void combo_kategori_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_kategori_barangActionPerformed
        try {
            String sql1 = "SELECT id_kategori FROM data_kategori WHERE nama_kategori LIKE '"+combo_kategori_barang.getSelectedItem()+"'";
                java.sql.Connection conna=(Connection) Config.ConfigDB();
                java.sql.PreparedStatement pst1=conna.prepareStatement(sql1);
                ResultSet rs=pst1.executeQuery();
                while(rs.next()){
                    id_kategori_barang.setText(rs.getString("id_kategori"));
                }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_combo_kategori_barangActionPerformed

    private void input_cari_barangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_cari_barangKeyReleased
        LoadTable_barang();
    }//GEN-LAST:event_input_cari_barangKeyReleased

    private void Table_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_barangMouseClicked
        try {
            int row = Table_barang.getSelectedRow();
            String tabel_klik = (Table_barang.getModel().getValueAt(row, 1).toString());
            conn = (java.sql.Connection) Config.ConfigDB();
            st = conn.createStatement();
            java.sql.ResultSet sql = st.executeQuery("SELECT kode_dan_barcode,nama_barang,data_satuan.id_satuan,data_satuan.nama_satuan,"
                    + "data_kategori.id_kategori,data_kategori.nama_kategori,harga_biasa,harga_jual,laba,harga_grosir,minimal_beli_grosir,"
                    + "diskon_jual,minimal_beliDiskon_jual,stok FROM barang JOIN data_satuan ON barang.id_satuan = data_satuan.id_satuan "
                    + "JOIN data_kategori ON barang.id_kategori=data_kategori.id_kategori Where kode_dan_barcode='"+tabel_klik+"'");
            if (sql.next()) {
                input_kodeDanBarcode_barang.setText(sql.getString("kode_dan_barcode"));
                input_nama_barang.setText(sql.getString("nama_barang"));
                id_satuan_barang.setText(sql.getString("data_satuan.id_satuan"));
                combo_satuan_barang.setSelectedItem(sql.getString("data_satuan.nama_satuan"));
                id_kategori_barang.setText(sql.getString("data_kategori.id_kategori"));
                combo_kategori_barang.setSelectedItem(sql.getString("data_kategori.nama_kategori"));
                input_hargaBiasa_barang.setText(sql.getString("harga_biasa"));
                input_hargaJual_barang.setText(sql.getString("harga_jual"));
                input_laba_barang.setText(sql.getString("laba"));
                input_hargaGrosir_barang.setText(sql.getString("harga_grosir"));
                input_minBeliGrosir_barang.setText(sql.getString("minimal_beli_grosir"));
                input_diskonHargaPersen_barang.setText(sql.getString("diskon_jual"));
                input_minBeliHarga_barang.setText(sql.getString("minimal_beliDiskon_jual"));
                input_stok_barang.setText(sql.getString("stok"));
            }
            input_kodeDanBarcode_barang.setEnabled(false);
                    btn_tambah_barang.setEnabled(false);
                    btn_ubah_barang.setEnabled(true);
                    btn_hapus_barang.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Table_barangMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("MacOs".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Card;
    private javax.swing.JComboBox<String> Combo_status_kategori;
    private javax.swing.JComboBox<String> Combo_status_satuan;
    private javax.swing.JPanel Dashboard;
    private javax.swing.JPanel Data_barang;
    private javax.swing.JPanel Data_kategori;
    private javax.swing.JPanel Data_pelanggan;
    private javax.swing.JPanel Data_users;
    private javax.swing.JPanel Jenis_Satuan;
    private javax.swing.JLabel JmlPelanggan;
    private javax.swing.JLabel Jmlh_Satuan;
    private javax.swing.JLabel Jmlh_barang;
    private javax.swing.JLabel Jmlh_users;
    private javax.swing.JLabel Judul;
    private javax.swing.JPanel Laba;
    private javax.swing.JPanel LaporanBarang;
    private javax.swing.JPanel LaporanPenjualan;
    private javax.swing.JPanel LaporanPenjualanPerPelanggan;
    private javax.swing.JPanel LaporanReturnPenjualan;
    private javax.swing.JPanel LaporanStokOpname;
    private javax.swing.JPanel LaporanUpdateHarga;
    private javax.swing.JPanel MenuAboutMe;
    private javax.swing.JPanel MenuDashboard;
    private javax.swing.JPanel MenuDataBarang;
    private javax.swing.JPanel MenuKategori;
    private javax.swing.JPanel MenuKoneksiDatabase;
    private javax.swing.JPanel MenuLaporan;
    private javax.swing.JPanel MenuLaporanBarang;
    private javax.swing.JPanel MenuLaporanLaba;
    private javax.swing.JPanel MenuLaporanPenjualan;
    private javax.swing.JPanel MenuLaporanPenjualan_perPelanggan;
    private javax.swing.JPanel MenuLaporanReturnPenjualan;
    private javax.swing.JPanel MenuLaporanStokOpname;
    private javax.swing.JPanel MenuLaporanUpdateHarga;
    private javax.swing.JPanel MenuManualBook;
    private javax.swing.JPanel MenuManualProgram;
    private javax.swing.JPanel MenuMaster;
    private javax.swing.JPopupMenu MenuPanelLaporan;
    private javax.swing.JPopupMenu MenuPanelMaster;
    private javax.swing.JPopupMenu MenuPanelSetting;
    private javax.swing.JPopupMenu MenuPanelTransaksi;
    private javax.swing.JPanel MenuPelanggan;
    private javax.swing.JPanel MenuReturnPenjualan;
    private javax.swing.JPanel MenuSatuan;
    private javax.swing.JPanel MenuStokOpname;
    private javax.swing.JPanel MenuTransaksi;
    private javax.swing.JPanel MenuTransaksiPenjualan;
    private javax.swing.JPanel MenuUpdateHarga;
    private javax.swing.JPanel MenuUsers;
    private javax.swing.JPanel PanelLaporan;
    private javax.swing.JPanel PanelMaster;
    private javax.swing.JPanel PanelSetting;
    private javax.swing.JPanel PanelTransaksi;
    private javax.swing.JPanel ReturnPenjualan;
    private javax.swing.JPanel Setting;
    private javax.swing.JPanel SettingAplikasi;
    private javax.swing.JPanel StokOpname;
    private javax.swing.JTable Table_Pelanggan;
    private javax.swing.JTable Table_Users;
    private javax.swing.JTable Table_barang;
    private javax.swing.JTable Table_data_kategori;
    private javax.swing.JTable Table_satuan;
    private javax.swing.JPanel UpdateHarga;
    private javax.swing.JPanel btnLogout;
    private javax.swing.JButton btn_batal_barang;
    private javax.swing.JButton btn_batal_kategori;
    private javax.swing.JButton btn_batal_pelanggan;
    private javax.swing.JButton btn_batal_satuan;
    private javax.swing.JButton btn_batal_user;
    private javax.swing.JButton btn_hapus_barang;
    private javax.swing.JButton btn_hapus_kategori;
    private javax.swing.JButton btn_hapus_pelanggan;
    private javax.swing.JButton btn_hapus_satuan;
    private javax.swing.JButton btn_hapus_user;
    private javax.swing.JButton btn_tambah_barang;
    private javax.swing.JButton btn_tambah_kategori;
    private javax.swing.JButton btn_tambah_pelanggan;
    private javax.swing.JButton btn_tambah_satuan;
    private javax.swing.JButton btn_tambah_user;
    private javax.swing.JButton btn_ubah_barang;
    private javax.swing.JButton btn_ubah_kategori;
    private javax.swing.JButton btn_ubah_pelanggan;
    private javax.swing.JButton btn_ubah_satuan;
    private javax.swing.JButton btn_ubah_user;
    private javax.swing.JComboBox<String> combo_kategori_barang;
    private javax.swing.JComboBox<String> combo_level_user;
    private javax.swing.JComboBox<String> combo_satuan_barang;
    private javax.swing.JComboBox<String> combo_status_pelanggan;
    private javax.swing.JComboBox<String> combo_status_user;
    private javax.swing.JLabel id_kategori_barang;
    private javax.swing.JLabel id_satuan_barang;
    private javax.swing.JTextArea input_alamat_pelanggan;
    private javax.swing.JTextField input_cari_barang;
    private javax.swing.JTextField input_cari_pelanggan;
    private javax.swing.JTextField input_diskonHargaPersen_barang;
    private javax.swing.JTextField input_email_pelanggan;
    private javax.swing.JTextField input_hargaBiasa_barang;
    private javax.swing.JTextField input_hargaGrosir_barang;
    private javax.swing.JTextField input_hargaJual_barang;
    private javax.swing.JTextField input_id_satuan;
    private javax.swing.JTextField input_id_user;
    private javax.swing.JTextField input_idkategori;
    private javax.swing.JTextField input_keterangan_kategori;
    private javax.swing.JTextField input_keterangan_satuan;
    private javax.swing.JTextField input_kodeDanBarcode_barang;
    private javax.swing.JTextField input_kode_pelanggan;
    private javax.swing.JTextField input_laba_barang;
    private javax.swing.JTextField input_minBeliGrosir_barang;
    private javax.swing.JTextField input_minBeliHarga_barang;
    private javax.swing.JTextField input_nama_barang;
    private javax.swing.JTextField input_nama_kategori;
    private javax.swing.JTextField input_nama_pelanggan;
    private javax.swing.JTextField input_nama_satuan;
    private javax.swing.JTextField input_nama_user;
    private javax.swing.JTextField input_noHp_pelanggan;
    private javax.swing.JTextField input_noHp_user;
    private javax.swing.JTextField input_password_user;
    private javax.swing.JTextField input_stok_barang;
    private javax.swing.JTextField input_username_user;
    private javax.swing.JButton jButton17;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel jml_kategori;
    private javax.swing.JLabel txtAboutMe;
    private javax.swing.JLabel txtKoneksi;
    private javax.swing.JLabel txtManualBook;
    private javax.swing.JLabel txtManualProgram;
    private javax.swing.JLabel txtMenu_Dashboard;
    private javax.swing.JLabel txtMenu_Laporan;
    private javax.swing.JLabel txtMenu_Master;
    private javax.swing.JLabel txtMenu_Transaksi;
    // End of variables declaration//GEN-END:variables
}
