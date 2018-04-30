package me.fetonxu.tank_console;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    @Test
    public void test(){
        Date date = new Date(1524745507071l);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String time = formatter.format(date);
        System.out.println(time);
    }
}
