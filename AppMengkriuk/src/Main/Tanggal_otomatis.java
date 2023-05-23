/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static Main.TransaksiPenjualan.tanggal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.Timer;

/**
 *
 * @author rhoma
 */
public class Tanggal_otomatis {
    public void dateTime(){
        Timer timer;
        ActionListener actionListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime now = LocalDateTime.now();

                Date date = new Date();
                DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String time = timeFormat.format(date);
                tanggal.setText(dft.format(now)+"  "+time);
            }
        };
        
        timer = new Timer(1000,actionListener);
        timer.setInitialDelay(0);
        timer.start();
    }
}
