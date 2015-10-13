package jdbc;

import org.junit.Test;

import javax.sql.rowset.*;
import java.sql.*;

/**
 * Created by jakob on 06.10.2015.
 */
public class Tests {

    @Test
    public void testConnection() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", ""))
        {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select * from env_person");

            int cols = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= cols; i++) {
                    System.out.print(rs.getObject(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testJdbcRowSet() throws Exception {
        RowSetFactory rsf = RowSetProvider.newFactory();
        JdbcRowSet rs = rsf.createJdbcRowSet();
        rs.setUrl("jdbc:mysql://localhost:3306/test");
        rs.setUsername("root");
        rs.setPassword("");

        rs.setCommand("select * from env_person");
        rs.execute();

        int cols = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= cols; i++) {
                System.out.print(rs.getObject(i) + "\t");
            }
            System.out.println();
        }

        rs.close();
    }

    @Test
    public void testWebRowSet() throws Exception {
        RowSetFactory rsf = RowSetProvider.newFactory();
        WebRowSet rs = rsf.createWebRowSet();
        rs.setUrl("jdbc:mysql://localhost:3306/test");
        rs.setUsername("root");
        rs.setPassword("");

        rs.setCommand("select * from env_person");
        rs.execute();

        rs.writeXml(System.out);
        rs.close();
    }

    @Test
    public void testJoinRowSet() throws Exception {
        RowSetFactory rsf = RowSetProvider.newFactory();
        CachedRowSet rs1 = rsf.createCachedRowSet();
        rs1.setUrl("jdbc:mysql://localhost:3306/test");
        rs1.setUsername("root");
        rs1.setPassword("");
        rs1.setCommand("select * from env_person");
        rs1.execute();

        CachedRowSet rs2 = rsf.createCachedRowSet();
        rs2.setUrl("jdbc:mysql://localhost:3306/test");
        rs2.setUsername("root");
        rs2.setPassword("");
        rs2.setCommand("select id, rev, revtype from env_person_aud");
        rs2.execute();

        JoinRowSet rs3 = rsf.createJoinRowSet();
        rs3.addRowSet(rs1, "id");
        rs3.addRowSet(rs2, "id");

        int cols = rs3.getMetaData().getColumnCount();
        while (rs3.next()) {
            for (int i = 1; i <= cols; i++) {
                System.out.print(rs3.getObject(i) + "\t");
            }
            System.out.println();
        }

    }
}
