package com.example.minhnguyen.rnrapplication;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.CheckResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URI;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText username,pasword;
    ProgressBar progressBar;
    Connection con;
    String un,pass,db,ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button)findViewById(R.id.button);
        username =(EditText)findViewById(R.id.editText);
        pasword = (EditText)findViewById(R.id.editText2);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        ip = "";
        db = "";
        un = "";
        pass = "";

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute();

            }
        });

    }
    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z="";
        Boolean isSuccess = false;

        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }
        protected String doInBackground (String...params){
            String usernam = username.getText().toString();
            String passwordd = pasword.getText().toString();
            if (usernam.trim().equals("")|| passwordd.trim().equals(""))
            {
                z ="Plese Enter Username and Password";
            }
            else {
                try {
                    con = connectionclass (un,pass,db,ip);
                    if (con==null){
                        z = "Check your Internet Access!";
                    }
                    else
                    {
                        String query = "select * from login where user_name= " + usernam.toString()+ "'and pass_word= '"+ passwordd.toString();
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()){
                            z = "Login Successful";
                            isSuccess = true;
                            con.close();
                        }else
                        {
                            z = "Invalid Credentials!";
                            isSuccess = false;
                        }
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
        protected void onPostExecute (String r)
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this,r,Toast.LENGTH_SHORT).show();
            if (isSuccess)
            {
                Toast.makeText(MainActivity.this,"Login Successfull", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public Connection connectionclass (String user, String password, String database, String server)
    {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" +server + database + ";user" + user+ ";pasword=" +password+ ";";
            connection = DriverManager.getConnection(ConnectionURL);
        }catch (SQLException se)
        {
            Log.e("error here 1: ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2: ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e ("error here 3: ",e.getMessage());
        }
        return connection;

    }
}
