/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rhoma
 */
public class TransaksiPenjualan extends javax.swing.JFrame {
    public Statement st;
    public ResultSet res;
    public DefaultTableModel tabModel;
    public PreparedStatement pst;
    public Connection conn;
    
    //Disable transaksi
    public void disableTransaksi(){
        nama_atau_barcode.setEnabled(false);
        qty.setEnabled(false);
        nama_pelanggan.setEnabled(false);
        btn_cart.setEnabled(false);
        btn_clear.setEnabled(false);
        btn_bayar.setEnabled(false);
        btn_ubah.setEnabled(false);
        btn_hapus.setEnabled(false);
    }
    
    //Enable transaksi
    public void enableTransaksi(){
        nama_atau_barcode.setEnabled(true);
        qty.setEnabled(true);
        nama_pelanggan.setEnabled(true);
        btn_cart.setEnabled(true);
        btn_clear.setEnabled(true);
        btn_bayar.setEnabled(true);
        btn_ubah.setEnabled(true);
        btn_hapus.setEnabled(true);
    }
    
    public void ClearTransaksi(){
        kode_barcode.setText(null);
        nama_atau_barcode.setText(null);
        harga_jual.setText("0");
        qty.setText(null);
        subTotal.setText("0");
        Diskon_harga_rupiah.setText("0");
        harga_biasa.setText("0");
        harga_grosir.setText("0");
        Diskon_harga_persen.setText("0");
        MinDiskon.setText("0");
        MinGrosir.setText("0");
        getGrosir.setText("0");
        getDiskon.setText("0");
    }
    
    public void ClearPembayaran(){
        Total_transaksi.setText("0");
        Total_diskon.setText("0");
        diskon_persen_pembayaran.setText("0");
        total_diskon_rupiah_pembayaran.setText("0");
        bayar.setText("0");
        kembalian.setText("0");
    }
    
    //Auto Sum
    public void autoSum_subTotal() {
        int total = 0;
        for (int i = 0; i < Tabel_Transaksi_penjulan.getRowCount(); i++) {
            double amount = Double.parseDouble((String) Tabel_Transaksi_penjulan.getValueAt(i, 7));
            total += amount;
        }
        
        double blnj = Double.parseDouble(""+total);
            DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setCurrencySymbol("");
            dfs.setGroupingSeparator('.');
            df.setDecimalFormatSymbols(dfs);
            String hsl = "Rp." + df.format(blnj);
        
        Total_transaksi_utama.setText("" + hsl);
        Total_transaksi.setText("" + total);
//        grand_total.setText("" + total);

    }
    
    public void loadPencarian(){
        tabModel = new DefaultTableModel();
        tabModel.addColumn("Kode/barcode");
        tabModel.addColumn("Nama barang");
        tabModel.addColumn("Stok");
        String cari = nama_atau_barcode.getText();
        try {
            String sql = "Select * from barang where nama_barang like '"+cari+"%'";
            conn = (Connection)Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            res = pst.executeQuery();
            while(res.next()){
                tabModel.addRow(new Object[]{
                    res.getString("kode_dan_barcode"),res.getString("nama_barang"),res.getString("stok")
                });
            }
            table_cari_data.setModel(tabModel);
        } catch (Exception e) {
        }
    }
    
    public void loadPelanggan(){
        tabModel = new DefaultTableModel();
        tabModel.addColumn("Kode pelanggan");
        tabModel.addColumn("Nama pelanggan");
        
        String cari = nama_pelanggan.getText();
        try {
            String sql = "Select * from pelanggan where nama_pelanggan like '"+cari+"%'";
            conn = (Connection)Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            res = pst.executeQuery();
            while(res.next()){
                tabModel.addRow(new Object[]{
                    res.getString("kode_pelanggan"),res.getString("nama_pelanggan")
                });
            }
            Table_pelanggan.setModel(tabModel);
        } catch (Exception e) {
        }
    }
    
    public void loadtableTransaksi(){
        tabModel = new DefaultTableModel();
        tabModel.addColumn("Kode");
        tabModel.addColumn("Nama produk");
        tabModel.addColumn("Harga");
        tabModel.addColumn("Grosir");
        tabModel.addColumn("Jumlah");
        tabModel.addColumn("Diskon(%)");
        tabModel.addColumn("Diskon(Rp)");
        tabModel.addColumn("SubTotal");
        
        try {
            String sql = "SELECT barang.kode_dan_barcode,barang.nama_barang,detail_transaksi_penjualan.qty,detail_transaksi_penjualan.harga_jual,"
                    + "detail_transaksi_penjualan.harga_grosir,detail_transaksi_penjualan.diskon_persen,detail_transaksi_penjualan.diskon_rupiah,"
                    + "detail_transaksi_penjualan.total_jual FROM detail_transaksi_penjualan JOIN barang "
                    + "ON detail_transaksi_penjualan.kode_dan_barcode = barang.kode_dan_barcode WHERE no_transaksi='"+no_transaksi.getText()+"'";
            conn = (Connection)Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            res = pst.executeQuery();
            while(res.next()){
                tabModel.addRow(new Object[]{
                    res.getString("barang.kode_dan_barcode"),res.getString("barang.nama_barang"),
                    res.getString("detail_transaksi_penjualan.harga_jual"),res.getString("detail_transaksi_penjualan.harga_grosir"),
                    res.getString("detail_transaksi_penjualan.qty"),res.getString("detail_transaksi_penjualan.diskon_persen"),
                    res.getString("detail_transaksi_penjualan.diskon_rupiah"),res.getString("detail_transaksi_penjualan.total_jual")
                });
            }
            Tabel_Transaksi_penjulan.setModel(tabModel);
        } catch (Exception e) {
        }
    }
    /**
     * Creates new form TransaksiPenjualan
     */
    public TransaksiPenjualan() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        DialogPembayaran.setLocationRelativeTo(null);
        Tabel_Transaksi_penjulan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        Tabel_Transaksi_penjulan.getTableHeader().setOpaque(false); //jika ingin memberi garis true
        Tabel_Transaksi_penjulan.getTableHeader().setBackground(new Color(7, 175, 247));
        Tabel_Transaksi_penjulan.getTableHeader().setForeground(new Color(255, 255, 255));
        Tabel_Transaksi_penjulan.getColorModel();
        Tabel_Transaksi_penjulan.setRowHeight(30);
        Tabel_Transaksi_penjulan.getTableHeader().setPreferredSize(new Dimension(30, 30));
        
        menu_cari.add(panel_cari_nama);
        menu_pelanggan.add(panel_cari_pelanggan);
        
        id_pelanggan.setText(null);
        kasir.setText(null);
        
        btn_ubah.setEnabled(false);
        btn_hapus.setEnabled(false);
        
        loadPencarian();
        loadPelanggan();
        
        Tanggal_otomatis tgl = new Tanggal_otomatis();
        tgl.dateTime();
        
        Kode_otomatis kode = new Kode_otomatis();
        kode.Nota_otomatis();
        
        ClearTransaksi();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DialogPembayaran = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        Total_transaksi = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        Total_diskon = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        diskon_persen_pembayaran = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        total_diskon_rupiah_pembayaran = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        bayar = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btn_10 = new javax.swing.JButton();
        btn_20 = new javax.swing.JButton();
        btn_50 = new javax.swing.JButton();
        btn_100 = new javax.swing.JButton();
        kembalian = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        combo_metode = new javax.swing.JComboBox<>();
        btn_batal = new javax.swing.JButton();
        btn_bayar_final = new javax.swing.JButton();
        btn_bayarStruk = new javax.swing.JButton();
        btn_keluar_final = new javax.swing.JButton();
        panel_cari_nama = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_cari_data = new javax.swing.JTable();
        menu_cari = new javax.swing.JPopupMenu();
        panel_cari_pelanggan = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table_pelanggan = new javax.swing.JTable();
        menu_pelanggan = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        id_pelanggan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        nama_atau_barcode = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Total_transaksi_utama = new javax.swing.JLabel();
        harga_jual = new javax.swing.JTextField();
        qty = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btn_cart = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabel_Transaksi_penjulan = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btn_bayar = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        btn_baru = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        panelKeluar = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        nama_pelanggan = new javax.swing.JTextField();
        kasir = new javax.swing.JTextField();
        subTotal = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        ketHarga_grosir = new javax.swing.JLabel();
        harga_grosir = new javax.swing.JLabel();
        ketDiskon = new javax.swing.JLabel();
        Diskon_harga_persen = new javax.swing.JLabel();
        kode_barcode = new javax.swing.JLabel();
        MinGrosir = new javax.swing.JLabel();
        MinDiskon = new javax.swing.JLabel();
        harga_biasa = new javax.swing.JLabel();
        kunci_utama = new javax.swing.JLabel();
        Diskon_harga_rupiah = new javax.swing.JLabel();
        getGrosir = new javax.swing.JLabel();
        getDiskon = new javax.swing.JLabel();
        btn_clear = new javax.swing.JButton();

        DialogPembayaran.setMinimumSize(new java.awt.Dimension(480, 470));
        DialogPembayaran.setModal(true);
        DialogPembayaran.setType(java.awt.Window.Type.UTILITY);

        jPanel5.setBackground(new java.awt.Color(32, 188, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("[F11] : Bayar,   [F12] : Bayar + cetak struk");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
        );

        Total_transaksi.setBackground(new java.awt.Color(239, 244, 0));
        Total_transaksi.setFont(new java.awt.Font("Dialog", 1, 25)); // NOI18N
        Total_transaksi.setForeground(new java.awt.Color(84, 84, 84));
        Total_transaksi.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        Total_transaksi.setText("0");
        Total_transaksi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Total :");

        Total_diskon.setBackground(new java.awt.Color(239, 244, 0));
        Total_diskon.setFont(new java.awt.Font("Dialog", 1, 25)); // NOI18N
        Total_diskon.setForeground(new java.awt.Color(84, 84, 84));
        Total_diskon.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        Total_diskon.setText("0");
        Total_diskon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Diskon :");

        diskon_persen_pembayaran.setBackground(new java.awt.Color(255, 255, 255));
        diskon_persen_pembayaran.setFont(new java.awt.Font("Dialog", 1, 25)); // NOI18N
        diskon_persen_pembayaran.setForeground(new java.awt.Color(0, 0, 0));
        diskon_persen_pembayaran.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        diskon_persen_pembayaran.setText("0");
        diskon_persen_pembayaran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("%");

        total_diskon_rupiah_pembayaran.setEditable(false);
        total_diskon_rupiah_pembayaran.setBackground(new java.awt.Color(234, 229, 229));
        total_diskon_rupiah_pembayaran.setFont(new java.awt.Font("Dialog", 1, 25)); // NOI18N
        total_diskon_rupiah_pembayaran.setForeground(new java.awt.Color(84, 84, 84));
        total_diskon_rupiah_pembayaran.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        total_diskon_rupiah_pembayaran.setText("0");
        total_diskon_rupiah_pembayaran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jCheckBox1.setBackground(new java.awt.Color(32, 188, 255));
        jCheckBox1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox1.setText("Gunakan Diskon");

        bayar.setBackground(new java.awt.Color(90, 246, 228));
        bayar.setFont(new java.awt.Font("Dialog", 1, 25)); // NOI18N
        bayar.setForeground(new java.awt.Color(84, 84, 84));
        bayar.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        bayar.setText("0");
        bayar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                bayarKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Bayar :");

        btn_10.setBackground(new java.awt.Color(234, 229, 229));
        btn_10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_10.setForeground(new java.awt.Color(0, 0, 0));
        btn_10.setText("Rp.10.000");
        btn_10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_10ActionPerformed(evt);
            }
        });

        btn_20.setBackground(new java.awt.Color(234, 229, 229));
        btn_20.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_20.setForeground(new java.awt.Color(0, 0, 0));
        btn_20.setText("Rp.20.000");
        btn_20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_20ActionPerformed(evt);
            }
        });

        btn_50.setBackground(new java.awt.Color(234, 229, 229));
        btn_50.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_50.setForeground(new java.awt.Color(0, 0, 0));
        btn_50.setText("Rp.50.000");
        btn_50.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_50ActionPerformed(evt);
            }
        });

        btn_100.setBackground(new java.awt.Color(234, 229, 229));
        btn_100.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_100.setForeground(new java.awt.Color(0, 0, 0));
        btn_100.setText("Rp.100.000");
        btn_100.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_100ActionPerformed(evt);
            }
        });

        kembalian.setEditable(false);
        kembalian.setBackground(new java.awt.Color(234, 229, 229));
        kembalian.setFont(new java.awt.Font("Dialog", 1, 25)); // NOI18N
        kembalian.setForeground(new java.awt.Color(84, 84, 84));
        kembalian.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        kembalian.setText("0");
        kembalian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Kembali :");

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Metode :");

        combo_metode.setBackground(new java.awt.Color(255, 255, 255));
        combo_metode.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        combo_metode.setForeground(new java.awt.Color(0, 0, 0));
        combo_metode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "BRI", "BCA" }));

        btn_batal.setBackground(new java.awt.Color(255, 255, 255));
        btn_batal.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btn_batal.setForeground(new java.awt.Color(0, 0, 0));
        btn_batal.setText("Batal");
        btn_batal.setBorder(null);

        btn_bayar_final.setBackground(new java.awt.Color(255, 255, 255));
        btn_bayar_final.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btn_bayar_final.setForeground(new java.awt.Color(0, 0, 0));
        btn_bayar_final.setText("Bayar");
        btn_bayar_final.setBorder(null);
        btn_bayar_final.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bayar_finalActionPerformed(evt);
            }
        });

        btn_bayarStruk.setBackground(new java.awt.Color(255, 255, 255));
        btn_bayarStruk.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btn_bayarStruk.setForeground(new java.awt.Color(0, 0, 0));
        btn_bayarStruk.setText("(Bayar+struk)");
        btn_bayarStruk.setBorder(null);

        btn_keluar_final.setBackground(new java.awt.Color(247, 65, 7));
        btn_keluar_final.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btn_keluar_final.setForeground(new java.awt.Color(255, 255, 255));
        btn_keluar_final.setText("Keluar");
        btn_keluar_final.setBorder(null);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bayar)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Total_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(btn_10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btn_20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btn_50, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btn_100, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(combo_metode, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(diskon_persen_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel13)
                                        .addGap(11, 11, 11)
                                        .addComponent(total_diskon_rupiah_pembayaran))
                                    .addComponent(Total_diskon))))
                        .addGap(0, 19, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_bayar_final, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_bayarStruk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_keluar_final, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Total_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Total_diskon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(total_diskon_rupiah_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(diskon_persen_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bayar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_100, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_50, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kembalian, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_metode, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_keluar_final, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_bayarStruk, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_bayar_final, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DialogPembayaranLayout = new javax.swing.GroupLayout(DialogPembayaran.getContentPane());
        DialogPembayaran.getContentPane().setLayout(DialogPembayaranLayout);
        DialogPembayaranLayout.setHorizontalGroup(
            DialogPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DialogPembayaranLayout.setVerticalGroup(
            DialogPembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panel_cari_nama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel_cari_namaMouseExited(evt);
            }
        });

        table_cari_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "kode/barcode", "Nama barang", "stok"
            }
        ));
        table_cari_data.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_cari_dataMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_cari_data);
        if (table_cari_data.getColumnModel().getColumnCount() > 0) {
            table_cari_data.getColumnModel().getColumn(0).setMinWidth(90);
            table_cari_data.getColumnModel().getColumn(1).setMinWidth(220);
            table_cari_data.getColumnModel().getColumn(2).setMinWidth(30);
        }

        javax.swing.GroupLayout panel_cari_namaLayout = new javax.swing.GroupLayout(panel_cari_nama);
        panel_cari_nama.setLayout(panel_cari_namaLayout);
        panel_cari_namaLayout.setHorizontalGroup(
            panel_cari_namaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panel_cari_namaLayout.setVerticalGroup(
            panel_cari_namaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        menu_cari.setFocusable(false);

        Table_pelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Kode pelanggan", "Nama Pelanggan"
            }
        ));
        Table_pelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_pelangganMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Table_pelanggan);

        javax.swing.GroupLayout panel_cari_pelangganLayout = new javax.swing.GroupLayout(panel_cari_pelanggan);
        panel_cari_pelanggan.setLayout(panel_cari_pelangganLayout);
        panel_cari_pelangganLayout.setHorizontalGroup(
            panel_cari_pelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panel_cari_pelangganLayout.setVerticalGroup(
            panel_cari_pelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        menu_pelanggan.setFocusable(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(243, 244, 244));
        jPanel1.setForeground(new java.awt.Color(243, 244, 244));

        no_transaksi.setEditable(false);
        no_transaksi.setBackground(new java.awt.Color(255, 255, 255));
        no_transaksi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        no_transaksi.setForeground(new java.awt.Color(114, 114, 114));
        no_transaksi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(114, 114, 114));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("No Transaksi :");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(114, 114, 114));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Tanggal :");

        tanggal.setEditable(false);
        tanggal.setBackground(new java.awt.Color(255, 255, 255));
        tanggal.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tanggal.setForeground(new java.awt.Color(114, 114, 114));
        tanggal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(114, 114, 114));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Pelanggan :");

        id_pelanggan.setEditable(false);
        id_pelanggan.setBackground(new java.awt.Color(255, 255, 255));
        id_pelanggan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        id_pelanggan.setForeground(new java.awt.Color(114, 114, 114));
        id_pelanggan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(114, 114, 114));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Nama/Barcode :");

        nama_atau_barcode.setBackground(new java.awt.Color(255, 255, 255));
        nama_atau_barcode.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        nama_atau_barcode.setForeground(new java.awt.Color(114, 114, 114));
        nama_atau_barcode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));
        nama_atau_barcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nama_atau_barcodeKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(114, 114, 114));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Kasir :");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Total Akhir :");

        Total_transaksi_utama.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        Total_transaksi_utama.setForeground(new java.awt.Color(0, 0, 0));
        Total_transaksi_utama.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Total_transaksi_utama.setText("Rp.0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Total_transaksi_utama, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 26, Short.MAX_VALUE)
                        .addComponent(Total_transaksi_utama))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        harga_jual.setEditable(false);
        harga_jual.setBackground(new java.awt.Color(255, 255, 255));
        harga_jual.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        harga_jual.setForeground(new java.awt.Color(114, 114, 114));
        harga_jual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        qty.setBackground(new java.awt.Color(255, 255, 255));
        qty.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        qty.setForeground(new java.awt.Color(114, 114, 114));
        qty.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qty.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));
        qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                qtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtyKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(114, 114, 114));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Qty :");

        btn_cart.setBackground(new java.awt.Color(255, 255, 255));
        btn_cart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/cart.png"))); // NOI18N
        btn_cart.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_cart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cartActionPerformed(evt);
            }
        });

        Tabel_Transaksi_penjulan.setAutoCreateRowSorter(true);
        Tabel_Transaksi_penjulan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode ", "Nama Produk", "Harga", "Grosir", "Jumlah", "Diskon(%)", "Diskon(Rp)", "SubTotal"
            }
        ));
        Tabel_Transaksi_penjulan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabel_Transaksi_penjulanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabel_Transaksi_penjulan);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(26, 51, 51));
        jLabel9.setText("[F10] : Bayar,   [F8] : Ubah,   [Ctrl+D] : Delete,   [esc] : Keluar,   [F5] : Transaksi baru");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        btn_bayar.setBackground(new java.awt.Color(7, 175, 247));
        btn_bayar.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btn_bayar.setForeground(new java.awt.Color(255, 255, 255));
        btn_bayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/bayar.png"))); // NOI18N
        btn_bayar.setText("Bayar");
        btn_bayar.setBorder(null);
        btn_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bayarActionPerformed(evt);
            }
        });

        btn_ubah.setBackground(new java.awt.Color(7, 175, 247));
        btn_ubah.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btn_ubah.setForeground(new java.awt.Color(255, 255, 255));
        btn_ubah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/ubah.png"))); // NOI18N
        btn_ubah.setText("Ubah");
        btn_ubah.setBorder(null);
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });

        btn_hapus.setBackground(new java.awt.Color(247, 65, 7));
        btn_hapus.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/hapus.png"))); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.setBorder(null);
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        btn_keluar.setBackground(new java.awt.Color(247, 65, 7));
        btn_keluar.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btn_keluar.setForeground(new java.awt.Color(255, 255, 255));
        btn_keluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/keluar.png"))); // NOI18N
        btn_keluar.setText("Keluar");
        btn_keluar.setBorder(null);

        btn_baru.setBackground(new java.awt.Color(7, 175, 247));
        btn_baru.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btn_baru.setForeground(new java.awt.Color(255, 255, 255));
        btn_baru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Baru.png"))); // NOI18N
        btn_baru.setText("Baru");
        btn_baru.setBorder(null);
        btn_baru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_baruActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        panelKeluar.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("x");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel10MouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelKeluarLayout = new javax.swing.GroupLayout(panelKeluar);
        panelKeluar.setLayout(panelKeluarLayout);
        panelKeluarLayout.setHorizontalGroup(
            panelKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );
        panelKeluarLayout.setVerticalGroup(
            panelKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        nama_pelanggan.setBackground(new java.awt.Color(255, 255, 255));
        nama_pelanggan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        nama_pelanggan.setForeground(new java.awt.Color(114, 114, 114));
        nama_pelanggan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));
        nama_pelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nama_pelangganKeyReleased(evt);
            }
        });

        kasir.setBackground(new java.awt.Color(255, 255, 255));
        kasir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        kasir.setForeground(new java.awt.Color(114, 114, 114));
        kasir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));

        subTotal.setEditable(false);
        subTotal.setBackground(new java.awt.Color(255, 255, 255));
        subTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        subTotal.setForeground(new java.awt.Color(114, 114, 114));
        subTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(206, 206, 206)));
        subTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subTotalActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(114, 114, 114));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("SubTotal :");

        ketHarga_grosir.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        ketHarga_grosir.setForeground(new java.awt.Color(243, 244, 244));
        ketHarga_grosir.setText("Dapat grosir :");

        harga_grosir.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        harga_grosir.setForeground(new java.awt.Color(243, 244, 244));
        harga_grosir.setText("0");

        ketDiskon.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        ketDiskon.setForeground(new java.awt.Color(243, 244, 244));
        ketDiskon.setText("Dapat Diskon :");

        Diskon_harga_persen.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        Diskon_harga_persen.setForeground(new java.awt.Color(243, 244, 244));
        Diskon_harga_persen.setText("0");

        kode_barcode.setForeground(new java.awt.Color(243, 244, 244));

        MinGrosir.setForeground(new java.awt.Color(243, 244, 244));
        MinGrosir.setText("0");

        MinDiskon.setForeground(new java.awt.Color(243, 244, 244));
        MinDiskon.setText("0");

        harga_biasa.setForeground(new java.awt.Color(243, 244, 244));

        kunci_utama.setForeground(new java.awt.Color(243, 244, 244));
        kunci_utama.setText("kunci");

        Diskon_harga_rupiah.setForeground(new java.awt.Color(243, 244, 244));
        Diskon_harga_rupiah.setText("0");

        getGrosir.setForeground(new java.awt.Color(243, 244, 244));

        getDiskon.setForeground(new java.awt.Color(243, 244, 244));

        btn_clear.setBackground(new java.awt.Color(255, 255, 255));
        btn_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/ClearTransaksi.png"))); // NOI18N
        btn_clear.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MinGrosir, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MinDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(kunci_utama, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(getGrosir, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(getDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_baru, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(id_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(nama_pelanggan))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kasir))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(no_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nama_atau_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(kode_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(harga_biasa, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(harga_jual, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(ketHarga_grosir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(harga_grosir, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(ketDiskon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Diskon_harga_persen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(Diskon_harga_rupiah, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(subTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_cart, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 36, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(no_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(kasir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(id_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(nama_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nama_atau_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(harga_jual, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel18)
                        .addComponent(subTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_cart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ketHarga_grosir)
                    .addComponent(harga_grosir)
                    .addComponent(ketDiskon)
                    .addComponent(Diskon_harga_persen)
                    .addComponent(kode_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(harga_biasa, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Diskon_harga_rupiah, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_baru, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(MinGrosir)
                        .addComponent(MinDiskon)
                        .addComponent(kunci_utama)
                        .addComponent(getGrosir, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(getDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseEntered
        panelKeluar.setBackground(new Color(247,65,7));
        jLabel10.setForeground(Color.WHITE);
    }//GEN-LAST:event_jLabel10MouseEntered

    private void jLabel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseExited
        panelKeluar.setBackground(Color.WHITE);
        jLabel10.setForeground(Color.black);
    }//GEN-LAST:event_jLabel10MouseExited

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        setVisible(false);
    }//GEN-LAST:event_jLabel10MouseClicked

    private void btn_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bayarActionPerformed
        DialogPembayaran.setVisible(true);
    }//GEN-LAST:event_btn_bayarActionPerformed

    private void subTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_subTotalActionPerformed

    private void nama_atau_barcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nama_atau_barcodeKeyReleased
        menu_cari.show(nama_atau_barcode,0,nama_atau_barcode.getHeight());
        loadPencarian();
    }//GEN-LAST:event_nama_atau_barcodeKeyReleased

    private void panel_cari_namaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_cari_namaMouseExited
        setVisible(false);
    }//GEN-LAST:event_panel_cari_namaMouseExited

    private void table_cari_dataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_cari_dataMouseClicked
        try {
            int row = table_cari_data.getSelectedRow();
            String tabel_klik = (table_cari_data.getModel().getValueAt(row, 0).toString());
            conn = (java.sql.Connection) Config.ConfigDB();
            st = conn.createStatement();
            java.sql.ResultSet sql = st.executeQuery("SELECT * from barang Where kode_dan_barcode='"+tabel_klik+"'");
            if (sql.next()) {
                kode_barcode.setText(sql.getString("kode_dan_barcode"));
                nama_atau_barcode.setText(sql.getString("nama_barang"));
                harga_biasa.setText(sql.getString("harga_biasa"));
                harga_jual.setText(sql.getString("harga_jual"));
                MinGrosir.setText(sql.getString("minimal_beli_grosir"));
                MinDiskon.setText(sql.getString("minimal_beliDiskon_jual"));
                getDiskon.setText(sql.getString("diskon_jual"));
                getGrosir.setText(sql.getString("harga_grosir"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_table_cari_dataMouseClicked

    private void btn_cartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cartActionPerformed
        try {
            String sql1 = "Select COUNT(*) as n from transaksi_penjualan where no_transaksi='"+no_transaksi.getText()+"'";
            conn = (Connection)Config.ConfigDB();
            st = conn.createStatement();
            java.sql.ResultSet resCek = st.executeQuery(sql1);
            int cek = 0;
            if(resCek.next()){
                cek = resCek.getInt("n");
            }
            
            int sub = Integer.parseInt(subTotal.getText());
            if(sub == 0){
                JOptionPane.showMessageDialog(null, "Pilih barang untuk menmahkan ke keranjang...","Inane warning",JOptionPane.WARNING_MESSAGE);
            }else{
                //Seleksi pada transaksi penjualan supaya tidak terjadi duplicate data
                if(cek == 1){
                    String sql4 = "insert into detail_transaksi_penjualan (no_transaksi,kode_dan_barcode,harga_biasa,harga_jual,harga_grosir,qty,"
                            + "diskon_persen,diskon_rupiah,total_jual) VALUES ('"+no_transaksi.getText()+"','"+kode_barcode.getText()+"',"
                            + "'"+harga_biasa.getText()+"','"+harga_jual.getText()+"','"+harga_grosir.getText()+"','"+qty.getText()+"','"+Diskon_harga_persen.getText()+"',"
                            + "'"+Diskon_harga_rupiah.getText()+"','"+subTotal.getText()+"') ";
                    pst = conn.prepareStatement(sql4);
                    pst.execute();
                    System.out.println("Behasil menambah data detail ke 2 dan seterusnya");
                }else{
                    String sql3 = "insert into transaksi_penjualan (no_transaksi,tanggal,kode_pelanggan,id_user,total_diskon_transaksi,diskon_persen_pembayaran,"
                            + "total_diskon_rupiah_pembayaran,total_seluruh,bayar,kembalian,metode) VALUES ('"+no_transaksi.getText()+"',"
                            + "'"+tanggal.getText()+"','"+id_pelanggan.getText()+"','"+kasir.getText()+"','"+Total_diskon.getText()+"','"+diskon_persen_pembayaran.getText()+"',"
                            + "'"+total_diskon_rupiah_pembayaran.getText()+"','"+Total_transaksi.getText()+"','"+bayar.getText()+"','"+kembalian.getText()+"',"
                            + "'"+combo_metode.getSelectedItem()+"')";
                    java.sql.PreparedStatement pst2 = conn.prepareStatement(sql3);
                    pst2.execute();
                    System.out.println("Behasil menambah data Transaksi");

                    String sql2 = "insert into detail_transaksi_penjualan (no_transaksi,kode_dan_barcode,harga_biasa,harga_jual,harga_grosir,qty,"
                            + "diskon_persen,diskon_rupiah,total_jual) VALUES ('"+no_transaksi.getText()+"','"+kode_barcode.getText()+"',"
                            + "'"+harga_biasa.getText()+"','"+harga_jual.getText()+"','"+harga_grosir.getText()+"','"+qty.getText()+"','"+Diskon_harga_persen.getText()+"',"
                            + "'"+Diskon_harga_rupiah.getText()+"','"+subTotal.getText()+"') ";
                    pst = conn.prepareStatement(sql2);
                    pst.execute();
                    System.out.println("Behasil menambah data detail");
                }
            }
            loadtableTransaksi();
            autoSum_subTotal();
            ClearTransaksi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "id_kasir dan pelanggan diisi terlebih dahulu karena masih proses pengembangan...");
            System.out.println(e);
        }
    }//GEN-LAST:event_btn_cartActionPerformed

    private void nama_pelangganKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nama_pelangganKeyReleased
        if (nama_pelanggan.getText() != null && !nama_pelanggan.getText().trim().isEmpty()) {
            
        }else{
            id_pelanggan.setText(null);
        }
        menu_pelanggan.show(nama_pelanggan,0,nama_pelanggan.getHeight());
        loadPelanggan();
    }//GEN-LAST:event_nama_pelangganKeyReleased

    private void Table_pelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_pelangganMouseClicked
        try {
            int row = Table_pelanggan.getSelectedRow();
            String tabel_klik = (Table_pelanggan.getModel().getValueAt(row, 0).toString());
            conn = (java.sql.Connection) Config.ConfigDB();
            st = conn.createStatement();
            java.sql.ResultSet sql = st.executeQuery("SELECT * from pelanggan Where kode_pelanggan='"+tabel_klik+"'");
            if (sql.next()) {
                id_pelanggan.setText(sql.getString("kode_pelanggan"));
                nama_pelanggan.setText(sql.getString("nama_pelanggan"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_Table_pelangganMouseClicked

    private void qtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyKeyReleased
        int qty = Integer.parseInt(this.qty.getText());
        int minGros = Integer.parseInt(MinGrosir.getText());
        int hargaJual = Integer.parseInt(harga_jual.getText());
        int hargaGros = Integer.parseInt(getGrosir.getText());
        if (id_pelanggan.getText() != null && !id_pelanggan.getText().trim().isEmpty()) {
            if(qty >= minGros){
                try {
                    String sql = "Select * from barang where kode_dan_barcode='"+kode_barcode.getText()+"'";
                    conn = (Connection)Config.ConfigDB();
                    pst = conn.prepareStatement(sql);
                    res = pst.executeQuery();
                    if(res.next()){
                        harga_grosir.setText(res.getString("harga_grosir"));
                    }
                } catch (Exception e) {
                }
                harga_grosir.setForeground(new Color(7,175,247));
                ketHarga_grosir.setForeground(new Color(7,175,247));
                int jmlh = qty*hargaGros;
                subTotal.setText(""+jmlh);
            }else{
                harga_grosir.setForeground(new Color(243,244,244));
                ketHarga_grosir.setForeground(new Color(243,244,244));
                harga_grosir.setText("0");
                int jmlh = hargaJual*qty;
                subTotal.setText(""+jmlh);
            }
        }else{
            int jmlh = hargaJual*qty;
            subTotal.setText(""+jmlh);
        }
        
        
    }//GEN-LAST:event_qtyKeyReleased

    private void qtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qtyKeyTyped
        char karakter = evt.getKeyChar();
        if(!(((karakter >= '0') && (karakter <= '9')))){
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_qtyKeyTyped

    private void Tabel_Transaksi_penjulanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabel_Transaksi_penjulanMouseClicked
        int baris = Tabel_Transaksi_penjulan.rowAtPoint(evt.getPoint());
        String kode_br = Tabel_Transaksi_penjulan.getValueAt(baris, 0).toString();
        kode_barcode.setText(kode_br);
        String nama = Tabel_Transaksi_penjulan.getValueAt(baris, 1).toString();
        nama_atau_barcode.setText(nama);
        String hargaJual = Tabel_Transaksi_penjulan.getValueAt(baris, 2).toString();
        harga_jual.setText(hargaJual);
        String grosir = Tabel_Transaksi_penjulan.getValueAt(baris, 3).toString();
        harga_grosir.setText(grosir);
        String jml = Tabel_Transaksi_penjulan.getValueAt(baris, 4).toString();
        qty.setText(jml);
        String diskonPer = Tabel_Transaksi_penjulan.getValueAt(baris, 5).toString();
        Diskon_harga_persen.setText(diskonPer);
        String diskonRup = Tabel_Transaksi_penjulan.getValueAt(baris, 6).toString();
        Diskon_harga_rupiah.setText(diskonRup);
        String sub = Tabel_Transaksi_penjulan.getValueAt(baris, 7).toString();
        subTotal.setText(sub);
        
        try {
            int row = Tabel_Transaksi_penjulan.getSelectedRow();
            String tabel_klik = (Tabel_Transaksi_penjulan.getModel().getValueAt(row, 0).toString());
            conn = (java.sql.Connection) Config.ConfigDB();
            st = conn.createStatement();
            java.sql.ResultSet sql = st.executeQuery("SELECT * from barang Where kode_dan_barcode='"+tabel_klik+"'");
            if (sql.next()) {
//                kode_barcode.setText(sql.getString("kode_dan_barcode"));
//                nama_atau_barcode.setText(sql.getString("nama_barang"));
                harga_biasa.setText(sql.getString("harga_biasa"));
//                harga_jual.setText(sql.getString("harga_jual"));
                MinGrosir.setText(sql.getString("minimal_beli_grosir"));
                MinDiskon.setText(sql.getString("minimal_beliDiskon_jual"));
                getDiskon.setText(sql.getString("diskon_jual"));
//                getGrosir.setText(sql.getString("harga_grosir"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        int qty = Integer.parseInt(this.qty.getText());
        int minGros = Integer.parseInt(MinGrosir.getText());
        if(qty >= minGros){
            harga_grosir.setForeground(new Color(7,175,247));
            ketHarga_grosir.setForeground(new Color(7,175,247));
        }else{
            harga_grosir.setForeground(new Color(243,244,244));
            ketHarga_grosir.setForeground(new Color(243,244,244));
        }
        
        btn_cart.setEnabled(false);
        btn_ubah.setEnabled(true);
        btn_hapus.setEnabled(true);
    }//GEN-LAST:event_Tabel_Transaksi_penjulanMouseClicked

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        ClearTransaksi();
        btn_cart.setEnabled(true);
        btn_ubah.setEnabled(false);
        btn_hapus.setEnabled(false);
    }//GEN-LAST:event_btn_clearActionPerformed

    private void bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bayarKeyReleased
        int total = Integer.parseInt(Total_transaksi.getText());
        int bayar = Integer.parseInt(this.bayar.getText());
        int jmlh = bayar-total;
        kembalian.setText(""+jmlh);
    }//GEN-LAST:event_bayarKeyReleased

    private void btn_10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_10ActionPerformed
        bayar.setText("10000");
        int total = Integer.parseInt(Total_transaksi.getText());
        int bayar = Integer.parseInt(this.bayar.getText());
        int jmlh = bayar-total;
        kembalian.setText(""+jmlh);
    }//GEN-LAST:event_btn_10ActionPerformed

    private void btn_20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_20ActionPerformed
        bayar.setText("20000");
        int total = Integer.parseInt(Total_transaksi.getText());
        int bayar = Integer.parseInt(this.bayar.getText());
        int jmlh = bayar-total;
        kembalian.setText(""+jmlh);
    }//GEN-LAST:event_btn_20ActionPerformed

    private void btn_50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_50ActionPerformed
        bayar.setText("50000");
        int total = Integer.parseInt(Total_transaksi.getText());
        int bayar = Integer.parseInt(this.bayar.getText());
        int jmlh = bayar-total;
        kembalian.setText(""+jmlh);
    }//GEN-LAST:event_btn_50ActionPerformed

    private void btn_100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_100ActionPerformed
        bayar.setText("100000");
        int total = Integer.parseInt(Total_transaksi.getText());
        int bayar = Integer.parseInt(this.bayar.getText());
        int jmlh = bayar-total;
        kembalian.setText(""+jmlh);
    }//GEN-LAST:event_btn_100ActionPerformed

    private void btn_bayar_finalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bayar_finalActionPerformed
        try {
            String sql = "update transaksi_penjualan set kode_pelanggan='"+id_pelanggan.getText()+"', total_diskon_transaksi='"+Total_diskon.getText()+"',"
                    + "diskon_persen_pembayaran='"+diskon_persen_pembayaran.getText()+"',total_diskon_rupiah_pembayaran='"+total_diskon_rupiah_pembayaran.getText()+"',"
                    + "total_seluruh='"+Total_transaksi.getText()+"',bayar='"+bayar.getText()+"',kembalian='"+kembalian.getText()+"',"
                    + "metode='"+combo_metode.getSelectedItem()+"' where no_transaksi='"+no_transaksi.getText()+"'";
            conn = (Connection)Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Berhasil");
            disableTransaksi();
            DialogPembayaran.setVisible(false);
            
            int total = Integer.parseInt(Total_transaksi.getText());
            int bayar = Integer.parseInt(this.bayar.getText());
            int jmlh = bayar-total;
            
            double blnj = Double.parseDouble(""+jmlh);
            DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setCurrencySymbol("");
            dfs.setGroupingSeparator('.');
            df.setDecimalFormatSymbols(dfs);
            String hsl = "Rp." + df.format(blnj);
            Total_transaksi_utama.setText(""+hsl);
            
            jLabel6.setText("Kembalian :");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_bayar_finalActionPerformed

    private void btn_baruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_baruActionPerformed
        enableTransaksi();
        jLabel6.setText("Total Akhir :");
        Total_transaksi_utama.setText("Rp.0");
        ClearPembayaran();
        ClearTransaksi();
        Kode_otomatis kode = new Kode_otomatis();
        kode.Nota_otomatis();
        loadtableTransaksi();
        id_pelanggan.setText(null);
        nama_pelanggan.setText(null);
        btn_ubah.setEnabled(false);
        btn_hapus.setEnabled(false);
    }//GEN-LAST:event_btn_baruActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        try {
            String sql = "Update detail_transaksi_penjualan set harga_jual='"+harga_jual.getText()+"',harga_grosir='"+harga_grosir.getText()+"',"
                    + "qty='"+qty.getText()+"',diskon_persen='"+Diskon_harga_persen.getText()+"',diskon_rupiah='"+Diskon_harga_rupiah.getText()+"',"
                    + "total_jual='"+subTotal.getText()+"' where no_transaksi='"+no_transaksi.getText()+"' AND kode_dan_barcode='"+kode_barcode.getText()+"' ";
            conn = (Connection)Config.ConfigDB();
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Berhasil Merubah data");
            loadtableTransaksi();
            ClearTransaksi();
            autoSum_subTotal();
            btn_cart.setEnabled(true);
            btn_ubah.setEnabled(false);
        btn_hapus.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah Transaksi " + nama_atau_barcode.getText() + " ingin dihapus!!!");
        switch (jawab) {
            case JOptionPane.YES_OPTION:
                try {
                    String sql = "Delete from detail_transaksi_penjualan where no_transaksi='"+no_transaksi.getText()+"' AND kode_dan_barcode='"+kode_barcode.getText()+"' ";
                        conn = (Connection) Config.ConfigDB();
                        pst = conn.prepareStatement(sql);
                        pst.execute();
                        
                        JOptionPane.showMessageDialog(null, "Berhasil Dihapus");

                        loadtableTransaksi();
                        ClearTransaksi();
                        btn_cart.setEnabled(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
                autoSum_subTotal();
                
                int total = Integer.parseInt(Total_transaksi.getText());
                if(total == 0){
                    try {
                        String sql2 = "Delete from transaksi_penjualan where no_transaksi='"+no_transaksi.getText()+"'";
                            conn = (Connection) Config.ConfigDB();
                            java.sql.PreparedStatement pst2 = conn.prepareStatement(sql2);
                            pst2.execute();
                            JOptionPane.showMessageDialog(null, "Berhasil Dihapus transaksi");
                            ClearPembayaran();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e);
                    }
                    
                }
                btn_ubah.setEnabled(false);
                btn_hapus.setEnabled(false);
                break;
            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(null, "Membatalkan Penghapusan");
                break;
            case JOptionPane.CANCEL_OPTION:
                break;
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

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
            java.util.logging.Logger.getLogger(TransaksiPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransaksiPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransaksiPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransaksiPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransaksiPenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog DialogPembayaran;
    private javax.swing.JLabel Diskon_harga_persen;
    private javax.swing.JLabel Diskon_harga_rupiah;
    private javax.swing.JLabel MinDiskon;
    private javax.swing.JLabel MinGrosir;
    private javax.swing.JTable Tabel_Transaksi_penjulan;
    private javax.swing.JTable Table_pelanggan;
    private javax.swing.JTextField Total_diskon;
    private javax.swing.JTextField Total_transaksi;
    private javax.swing.JLabel Total_transaksi_utama;
    private javax.swing.JTextField bayar;
    private javax.swing.JButton btn_10;
    private javax.swing.JButton btn_100;
    private javax.swing.JButton btn_20;
    private javax.swing.JButton btn_50;
    private javax.swing.JButton btn_baru;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_bayar;
    private javax.swing.JButton btn_bayarStruk;
    private javax.swing.JButton btn_bayar_final;
    private javax.swing.JButton btn_cart;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_keluar_final;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JComboBox<String> combo_metode;
    private javax.swing.JTextField diskon_persen_pembayaran;
    private javax.swing.JLabel getDiskon;
    private javax.swing.JLabel getGrosir;
    private javax.swing.JLabel harga_biasa;
    private javax.swing.JLabel harga_grosir;
    private javax.swing.JTextField harga_jual;
    private javax.swing.JTextField id_pelanggan;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField kasir;
    private javax.swing.JTextField kembalian;
    private javax.swing.JLabel ketDiskon;
    private javax.swing.JLabel ketHarga_grosir;
    private javax.swing.JLabel kode_barcode;
    private javax.swing.JLabel kunci_utama;
    private javax.swing.JPopupMenu menu_cari;
    private javax.swing.JPopupMenu menu_pelanggan;
    private javax.swing.JTextField nama_atau_barcode;
    private javax.swing.JTextField nama_pelanggan;
    public static final javax.swing.JTextField no_transaksi = new javax.swing.JTextField();
    private javax.swing.JPanel panelKeluar;
    private javax.swing.JPanel panel_cari_nama;
    private javax.swing.JPanel panel_cari_pelanggan;
    private javax.swing.JTextField qty;
    private javax.swing.JTextField subTotal;
    private javax.swing.JTable table_cari_data;
    public static final javax.swing.JTextField tanggal = new javax.swing.JTextField();
    private javax.swing.JTextField total_diskon_rupiah_pembayaran;
    // End of variables declaration//GEN-END:variables
}
