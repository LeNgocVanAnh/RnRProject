package com.example.minhnguyen.rnrapplication;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import android.widget.SimpleAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;

/**
 * Created by Erp-Tuyen on 03/01/2018.
 */

public  class SERVER {
    static String ip = "192.168.1.20"; //server
    static String classs = "net.sourceforge.jtds.jdbc.Driver"; //khóa này không thay đổi
    static String db = "RnRDatabase";//tên database
    static String un = ""; //user đăng nhập
    static String password = "";// mật khẩu đăng nhập

    static String z;
    static Boolean isSuccess;


    @SuppressLint("NewApi")
    public static Connection Connect() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip +":1433;databaseName="+ db + ";";
            //ConnURL="jdbc:mysql://{hostname}:{port}"
            conn = DriverManager.getConnection(ConnURL);
            ///Connection conn2 = DriverManager.getConnection(url, username, password);

        } catch (SQLException se) {
            Log.e("ERROR1","Không thể tải lớp Driver! "+ se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERROR2","Xuất hiện vấn đề truy cập trong khi tải! "+ e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR3", "Không thể khởi tạo Driver! "+e.getMessage());
        }
        return conn;

    }

}
