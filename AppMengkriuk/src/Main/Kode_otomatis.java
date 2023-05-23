/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static Main.TransaksiPenjualan.no_transaksi;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author rhoma
 */
public class Kode_otomatis {
    public void Nota_otomatis() {
        String nota = "N-00000000";
        int i = 0;
        try {
            java.sql.Connection conn = (Connection) Config.ConfigDB();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet res = st.executeQuery("select no_transaksi from transaksi_penjualan order by no_transaksi desc limit 1");

            while (res.next()) {
                nota = res.getString("no_transaksi");
            }
            nota = nota.substring(2);
            i = Integer.parseInt(nota) + 1;
            nota = "0000000" + i;
            nota = "N-" + nota.substring(nota.length() - 8);
            no_transaksi.setText(nota);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
