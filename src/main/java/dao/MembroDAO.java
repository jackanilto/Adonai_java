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
     * Cada ? representa um valor que será setado no PreparedStatement.
     */
    public void salvar(Membro membro) throws Exception {

        // SQL de inserção com parâmetros (boa prática: evita SQL Injection)
        String sql = "INSERT INTO TBL_MEMBROS " +
                "(ID_IGREJA, ROLL, NOME, CPF, TEL_PESSOAL, EMAIL, SEXO, ATIVO) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // try-with-resources fecha a conexão automaticamente
        try (Connection conn = ConexaoFirebird.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Cada setXXX corresponde exatamente à ordem dos campos no SQL
            ps.setInt(1, membro.getIdIgreja());             // ID_IGREJA
            ps.setInt(2, membro.getRoll());                 // ROLL
            ps.setString(3, membro.getNome());              // NOME
            ps.setString(4, membro.getCpf());               // CPF
            ps.setString(5, membro.getTelefone());          // TEL_PESSOAL
            ps.setString(6, membro.getEmail());             // EMAIL
            ps.setString(7, membro.getSexo());              // SEXO
            ps.setString(8, membro.isAtivo() ? "S" : "N");  // ATIVO

            // Executa o INSERT no banco
            ps.executeUpdate();
        }
    }

    /**
     * Lista todos os membros cadastrados no banco.
     * Retorna uma lista de objetos Membro.
     */
    public List<Membro> listar() throws Exception {

        List<Membro> lista = new ArrayList<>();

        // SQL de consulta
        String sql = "SELECT ID_MEMBRO, ROLL, NOME, CPF, TEL_PESSOAL, EMAIL, SEXO, ATIVO " +
                "FROM TBL_MEMBROS " +
                "ORDER BY NOME";

        try (Connection conn = ConexaoFirebird.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Percorre os registros retornados pelo banco
            while (rs.next()) {

                Membro m = new Membro();

                // Mapeamento das colunas do banco para o objeto Java
                m.setId(rs.getInt("ID_MEMBRO"));
                m.setRoll(rs.getInt("ROLL"));
                m.setNome(rs.getString("NOME"));
                m.setCpf(rs.getString("CPF"));
                m.setTelefone(rs.getString("TEL_PESSOAL"));
                m.setEmail(rs.getString("EMAIL"));
                m.setSexo(rs.getString("SEXO"));
                m.setAtivo("S".equals(rs.getString("ATIVO"))); // converte CHAR para boolean

                lista.add(m);
            }
        }

        return lista;
    }
}
