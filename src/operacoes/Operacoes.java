/*
    Objectivo: Implementação das operações do super terminal
    Date:
    Time:
    Ficheiro: Operacoes.java
    Tipo classe: Java Class.
    Número de linha(code line): 120
*/
package operacoes;
/*------------------------------------------------------------------------------------------------------------------
                                    AS SUPER BIBLIOTECAS
------------------------------------------------------------------------------------------------------------------*/
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import connection.ConnectionFactory;

/**
 *
 * @author Baziota Beans
 */
public class Operacoes {

    private ResultSetMetaData metaData;
    private DefaultTableModel tableModel;
    
    
    // fazer uma selecao super consulta
    public void selects (String sql, JTable table)
    {
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement ps = c.prepareStatement(sql); 
                ResultSet rs = ps.executeQuery()) {
            table.setModel(mostrar(rs));
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("" + e);
        }
    }
    
    // 
    public void inserts (String sql)
    {
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) 
        {
            ps.execute();
            trocarBd(sql);
            JOptionPane.showMessageDialog(null, "sucesso  " + sql.split(" ")[0]);
        } catch (SQLException | ClassNotFoundException e) 
        {
            System.out.println(" " + e);
        }
    }
    
    // executar uma consulta
    public void executar (String sql, JTable table)
    {
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.execute();
            trocarBd(sql);
            try (ResultSet rs = ps.getResultSet()) 
            {
                if (rs != null)
                    table.setModel(mostrar(rs));
                System.out.println(" resul set  " + rs);
            }
            JOptionPane.showMessageDialog(null, "Executado com sucesso  " + sql.split(" ")[0]);
        } catch (SQLException | ClassNotFoundException e) {
            
            System.out.println(" " + e);
        }
    }
    // chamar o procedumento
    public void procedureCall (String sql, JTable table)
    {
        try (Connection c = ConnectionFactory.getConnection(); CallableStatement cs = c.prepareCall(sql)) {
            cs.execute();
            try (ResultSet rs = cs.getResultSet()) 
            {
                if (rs != null)
                    table.setModel(mostrar(rs));
            }
        } catch (SQLException | ClassNotFoundException e) 
        {
            System.out.println("" + e);
        }
    }
    // mostrar o resultado da consulta
    private DefaultTableModel mostrar(ResultSet resultSet) throws SQLException 
    {
        metaData = resultSet.getMetaData();
        Object[] columns = new Object[metaData.getColumnCount()];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = metaData.getColumnName(i + 1);
        }
        tableModel = new DefaultTableModel(columns, 0);

        while (resultSet.next()) {
            Object[] linha = new Object[metaData.getColumnCount()];
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                linha[i] = resultSet.getObject(i + 1);
            }
            tableModel.addRow(linha);
        }
        //table.setModel(tableModel);
        return  tableModel;
    }
    // trocar a base de dados
    public void trocarBd(String bd)
    {
        if (bd.split(" ")[0].equalsIgnoreCase("use"))
            ConnectionFactory.base = bd.split(" ")[1];
    }

}
