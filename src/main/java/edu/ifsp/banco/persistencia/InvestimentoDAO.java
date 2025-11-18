package edu.ifsp.banco.persistencia;

import edu.ifsp.banco.modelo.Investimento;
import edu.ifsp.banco.modelo.Investimento.statusInvestimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestimentoDAO {

    public void inserir(Investimento inv) throws DataAccessException {

        String sql = "INSERT INTO investimento (id, id_conta, tipo_investimento, status, valor_investido, data_inicio, data_fim) "
                   + "VALUES (SEQ_INVESTIMENTO.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, inv.getIdConta());
            stmt.setString(2, inv.getTipoInvestimento());
            stmt.setString(3, inv.getStatus().getValor());
            stmt.setBigDecimal(4, inv.getValorInvestido());
            stmt.setTimestamp(5, inv.getDataInicio());
            stmt.setTimestamp(6, inv.getDataFim());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Erro ao inserir investimento");
        }
    }



    
    
    public Investimento buscarPorId(int id) throws DataAccessException {

        String sql = "SELECT * FROM investimento WHERE id = ?";
        Investimento investimento = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                investimento = new Investimento(
                        rs.getInt("id"),
                        rs.getInt("id_conta"),
                        rs.getString("tipo_investimento"),
                        statusInvestimento.valueOf(rs.getString("status")),
                        rs.getBigDecimal("valor_investido"),
                        rs.getTimestamp("data_inicio"),
                        rs.getTimestamp("data_fim")
                );
            }

        } catch (SQLException e) {
            throw new DataAccessException("Erro ao buscar investimento");
        }

        return investimento;
    }

    
    


    public List<Investimento> listarPorConta(int idConta) throws DataAccessException {

        List<Investimento> lista = new ArrayList<>();
        String sql = "SELECT * FROM investimento WHERE id_conta = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idConta);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Investimento inv = new Investimento(
                        rs.getInt("id"),
                        rs.getInt("id_conta"),
                        rs.getString("tipo_investimento"),
                        statusInvestimento.valueOf(rs.getString("status")),
                        rs.getBigDecimal("valor_investido"),
                        rs.getTimestamp("data_inicio"),
                        rs.getTimestamp("data_fim")
                );

                lista.add(inv);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Erro ao listar investimentos");
        }

        return lista;
    }



    
    
    public void atualizar(Investimento inv) throws DataAccessException {

        String sql = "UPDATE investimento SET id_conta = ?, tipo_investimento = ?, status = ?, valor_investido = ?, data_inicio = ?, data_fim = ? "
                   + "WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, inv.getIdConta());
            stmt.setString(2, inv.getTipoInvestimento());
            stmt.setString(3, inv.getStatus().getValor());
            stmt.setBigDecimal(4, inv.getValorInvestido());
            stmt.setTimestamp(5, inv.getDataInicio());
            stmt.setTimestamp(6, inv.getDataFim());
            stmt.setInt(7, inv.getId());

            int linhas = stmt.executeUpdate();

            if (linhas == 0) {
                throw new DataAccessException("Nenhum investimento atualizado");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Erro ao atualizar investimento");
        }
    }
    
    
    



    public void encerrar(int id, Timestamp dataFim) throws DataAccessException {

        String sql = "UPDATE investimento SET status = ?, data_fim = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, statusInvestimento.ENCERRADO.getValor());
            stmt.setTimestamp(2, dataFim);
            stmt.setInt(3, id);

            int linhas = stmt.executeUpdate();

            if (linhas == 0) {
                throw new DataAccessException("Nenhum investimento encerrado");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Erro ao encerrar investimento");
        }
    }

    
    
    


    public void remover(int id) throws DataAccessException {

        String sql = "DELETE FROM investimento WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas == 0) {
                throw new DataAccessException("Nenhum investimento removido");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Erro ao remover investimento");
        }
    }
}
