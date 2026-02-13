package dao;

import config.ConexaoFirebird;
import model.Membro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MembroDAO {

    /**
     * Salva um novo membro no banco de dados (INSERT).
     * Cada ? representa um valor que será enviado ao banco via PreparedStatement.
     * Usar PreparedStatement é uma boa prática de segurança (evita SQL Injection).
     */
    public void salvar(Membro membro) throws Exception {

        String sql = "INSERT INTO TBL_MEMBROS " +
                "(ID_IGREJA, ROLL, NOME, CPF, TEL_PESSOAL, EMAIL, SEXO, ATIVO) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoFirebird.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, membro.getIdIgreja());
            ps.setInt(2, membro.getRoll());
            ps.setString(3, membro.getNome());
            ps.setString(4, membro.getCpf());
            ps.setString(5, membro.getTelefone());
            ps.setString(6, membro.getEmail());
            ps.setString(7, membro.getSexo());
            ps.setString(8, membro.isAtivo() ? "S" : "N");

            ps.executeUpdate();

        } catch (java.sql.SQLException e) {

            // 🔴 Erro de violação de constraint UNIQUE no Firebird
            if (e.getMessage().toLowerCase().contains("violation") ||
                    e.getMessage().toLowerCase().contains("unique")) {

                // Lança exceção amigável para a camada de tela
                throw new Exception("O número de ROLL informado já existe no sistema.");

            } else {
                // Qualquer outro erro do banco
                throw new Exception("Erro ao salvar membro no banco de dados: " + e.getMessage());
            }
        }
    }


    /**
     * Lista todos os membros cadastrados no banco.
     * Retorna uma List<Membro> preenchida com os dados da tabela.
     */
    public List<Membro> listar() throws Exception {

        List<Membro> lista = new ArrayList<>();

        // SQL de consulta
        String sql = "SELECT ID_MEMBRO, ROLL, NOME, CPF, TEL_PESSOAL, EMAIL, SEXO, ATIVO " +
                "FROM TBL_MEMBROS " +
                "ORDER BY NOME";

        // try-with-resources também fecha ResultSet, PreparedStatement e Connection
        try (Connection conn = ConexaoFirebird.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Percorre todos os registros retornados pela consulta
            while (rs.next()) {

                // Cria um novo objeto Membro para cada linha do banco
                Membro m = new Membro();

                // Mapeia cada coluna do banco para os atributos do objeto Java
                m.setId(rs.getInt("ID_MEMBRO"));                  // ID do membro (PK)
                m.setRoll(rs.getInt("ROLL"));                     // ROLL
                m.setNome(rs.getString("NOME"));                  // NOME
                m.setCpf(rs.getString("CPF"));                    // CPF
                m.setTelefone(rs.getString("TEL_PESSOAL"));       // TELEFONE
                m.setEmail(rs.getString("EMAIL"));                // EMAIL
                m.setSexo(rs.getString("SEXO"));                  // SEXO
                m.setAtivo("S".equals(rs.getString("ATIVO")));    // Converte 'S'/'N' para boolean

                // Adiciona o membro preenchido à lista
                lista.add(m);
            }
        }

        // Retorna a lista completa de membros
        return lista;
    }

    /**
     * Atualiza os dados de um membro existente no banco (UPDATE).
     * O membro é identificado pelo ID_MEMBRO (chave primária).
     */
    public void atualizar(Membro membro) throws Exception {

        // SQL de atualização (coerente com o SELECT e com os nomes das colunas no Firebird)
        String sql = "UPDATE TBL_MEMBROS SET " +
                "ROLL = ?, " +
                "NOME = ?, " +
                "CPF = ?, " +
                "TEL_PESSOAL = ?, " +
                "EMAIL = ?, " +
                "SEXO = ?, " +
                "ATIVO = ? " +
                "WHERE ID_MEMBRO = ?";

        // try-with-resources para fechar tudo automaticamente
        try (Connection conn = ConexaoFirebird.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Parâmetros do SET (na mesma ordem do SQL)
            ps.setInt(1, membro.getRoll());                       // ROLL
            ps.setString(2, membro.getNome());                    // NOME
            ps.setString(3, membro.getCpf());                     // CPF
            ps.setString(4, membro.getTelefone());                // TEL_PESSOAL
            ps.setString(5, membro.getEmail());                   // EMAIL
            ps.setString(6, membro.getSexo());                    // SEXO
            ps.setString(7, membro.isAtivo() ? "S" : "N");        // ATIVO ('S' ou 'N')

            // Parâmetro do WHERE (ID do membro que será atualizado)
            ps.setInt(8, membro.getId());                         // ID_MEMBRO

            // Executa o UPDATE no banco
            ps.executeUpdate();
        }
    }
}
