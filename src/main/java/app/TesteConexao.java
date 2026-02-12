package app;

import config.ConexaoFirebird;

public class TesteConexao {
    public static void main(String[] args) {
        try {
            ConexaoFirebird.getConexao();
            System.out.println("🔥 Conectou no Firebird com sucesso!");
        } catch (Exception e) {
            System.err.println("❌ Erro ao conectar no Firebird:");
            e.printStackTrace();
        }
    }

}
