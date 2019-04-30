package UTN;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {

    private static Palabra p;
    private static Thread j1;
    private static Thread j2;
    private static Thread j3;
    private static Thread j4;

    private static PreparedStatement ps = null;
    private static ResultSet rs = null;

    public static void main(String[] args) {

        try {

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Ahorcado?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            con.setAutoCommit(false);

            ps = con.prepareStatement("select palabra from palabra order by rand() limit 1");
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new Palabra(rs.getString("palabra"));
            }
            ps.close();
            rs.close();

            j1 = new Thread(new Jugador(1, "Jugador 1", p));
            j2 = new Thread(new Jugador(2, "Jugador 2", p));
            j3 = new Thread(new Jugador(3, "Jugador 3", p));
            j4 = new Thread(new Jugador(4, "Jugador 4", p));

            j1.start();
            j2.start();
            j3.start();
            j4.start();

            j1.join();
            j2.join();
            j3.join();
            j4.join();

            persistir(con);

        } catch (Exception e) {
            System.out.println("Error:\n" + e);
        }
    }

    public static void persistir(Connection con) throws SQLException {

        try {

            ps = con.prepareStatement("insert into resultado values(0, ?, now(), ?)");
            ps.setString(1, p.getGanador());
            ps.setString(2, p.getPalabra());
            ps.execute();
            ps.close();
            con.commit();
            System.out.println("Datos guardados correctamente.");

        } catch (SQLException e) {
            con.rollback();
            System.out.println("Error:\n" + e);
        } finally {
            if (!con.isClosed()) {
                con.close();
            }
        }
    }
}