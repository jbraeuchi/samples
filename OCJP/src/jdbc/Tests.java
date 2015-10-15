package jdbc;

import org.junit.Test;

import javax.sql.RowSet;
import javax.sql.rowset.*;
import java.sql.*;

/**
 * Created by jakob on 06.10.2015.
 */
public class Tests {

    @Test
    public void testConnection() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "test", "test")) {
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
//    CREATE PROCEDURE `my_sqrt`(input_number INT, OUT out_number FLOAT)
//    BEGIN
//    SET out_number=SQRT(input_number);
//    END
    public void testCallableStatement() {
        String myProc = "{call my_sqrt(?,?)}";

        // add noAccessToProcedureBodies=true
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?noAccessToProcedureBodies=true", "test", "test")) {
            CallableStatement stmt = con.prepareCall(myProc);
            stmt.registerOutParameter(2, Types.FLOAT);

            stmt.setFloat(1, 2.0f);
            stmt.execute();

            float result = stmt.getFloat(2);

            System.out.println("Result: " + result);

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
        rs.setUsername("test");
        rs.setPassword("test");

        rs.setCommand("select * from env_person");
        rs.execute();

        rs.writeXml(System.out);
        rs.close();
    }

    @Test
    public void testFilterRowSet() throws Exception {
        RowSetFactory rsf = RowSetProvider.newFactory();
        FilteredRowSet rs = rsf.createFilteredRowSet();
        rs.setUrl("jdbc:mysql://localhost:3306/test");
        rs.setUsername("test");
        rs.setPassword("test");

        rs.setCommand("select * from env_person");
        rs.execute();

        Filter f = new Filter("on");
        rs.setFilter(f);

        int cols = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= cols; i++) {
                System.out.print(rs.getObject(i) + "\t");
            }
            System.out.println();
        }
    }

    @Test
    public void testJoinRowSet() throws Exception {
        RowSetFactory rsf = RowSetProvider.newFactory();
        CachedRowSet rs1 = rsf.createCachedRowSet();
        rs1.setUrl("jdbc:mysql://localhost:3306/test");
        rs1.setUsername("test");
        rs1.setPassword("test");
        rs1.setCommand("select * from env_person");
        rs1.execute();

        CachedRowSet rs2 = rsf.createCachedRowSet();
        rs2.setUrl("jdbc:mysql://localhost:3306/test");
        rs2.setUsername("test");
        rs2.setPassword("test");
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

    // Sucht den Filter-String in allen Spalten
    private class Filter implements Predicate {
        private String filter;

        public Filter(String filter) {
            this.filter = filter;
        }

        @Override
        public boolean evaluate(RowSet rs) {
            System.out.println("evaluate Rs");

            try {
                int cols = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= cols; i++) {
                    if (rs.getString(i).contains(filter)) {
                        System.out.println("found in column: " + i);
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean evaluate(Object value, int column) throws SQLException {
            System.out.println("evaluate col num");
            return false;
        }

        @Override
        public boolean evaluate(Object value, String columnName) throws SQLException {
            System.out.println("evaluate col name");
            return false;
        }
    }
}
