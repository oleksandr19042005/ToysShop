    package com.example.toysshop.database;

    import android.annotation.SuppressLint;
    import android.os.StrictMode;
    import android.util.Log;

    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.PreparedStatement;

    public class ConSQL {

        Connection con;

        @SuppressLint("NewApi")
        public Connection conclass() {
            String ip = "", port = "", db = "ToysRoom", username = "sa", password = "sa";
            StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(a);
            String connectURL = null;

            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                connectURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + db + ";user=" + username + ";" + "password=" + password + ";";
                con = DriverManager.getConnection(connectURL);
            } catch (Exception e) {
                Log.e("Error :", e.getMessage());
            }
            return con;
        }
        public boolean deleteToy(String toyName) {
            try {
                Connection con = conclass();
                if (con != null) {
                    String query = "DELETE FROM Toys WHERE name = ?";
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1, toyName);
                    int affectedRows = stmt.executeUpdate();
                    con.close();
                    return affectedRows > 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

    }