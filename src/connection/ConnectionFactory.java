/*
    Objectivo: Classe responsável pela conexão do servidor mysql
    Date:
    Time:
    Ficheiro: ConnectionFactory.java
    Tipo classe: Java Class.
    Número de linha(code line):
*/
package connection;
/*------------------------------------------------------------------------------------------------------------------
                                    AS SUPER BIBLIOTECAS
------------------------------------------------------------------------------------------------------------------*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Baziota Beans
 */
public class ConnectionFactory 
{
    // URL de banco de dados, nome de usuário e senha
    public static String base = "salaoantoniabeauty";
    private static final String USERNAME = "root"; 
    private static final String PASSWORD = "fabio007"; 
    
    // Conecta-se ao banco de dados
    public static Connection getConnection () throws SQLException, ClassNotFoundException 
    {
        Class.forName("com.mysql.jdbc.Driver");
        //Retorna o statuc da conexão, (URL de banco de dados, nome de usuário e senha)
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + base, USERNAME, PASSWORD);
    }
}
