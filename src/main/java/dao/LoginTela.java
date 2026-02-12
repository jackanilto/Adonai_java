import dao.UsuarioDAO;

import javax.swing.*;
import java.awt.*;

public class LoginTela extends JFrame {

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    public LoginTela() {

        setTitle("Login do Sistema");
        setSize(350, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(new JLabel("Usuário:"));
        txtLogin = new JTextField();
        painel.add(txtLogin);

        painel.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        painel.add(txtSenha);

        btnEntrar = new JButton("Entrar");
        painel.add(new JLabel()); // espaço vazio
        painel.add(btnEntrar);

        add(painel, BorderLayout.CENTER);

        btnEntrar.addActionListener(e -> validarLogin());

        setVisible(true);
    }

    private void validarLogin() {

        String login = txtLogin.getText();
        String senha = new String(txtSenha.getPassword());

        UsuarioDAO dao = new UsuarioDAO();

        if (dao.validarLogin(login, senha)) {
            JOptionPane.showMessageDialog(this, "✅ Login realizado com sucesso!");

            this.dispose(); // fecha a tela de login
            new TelaPrincipal(); // abre a tela principal

        } else {
            JOptionPane.showMessageDialog(this, "❌ Usuário ou senha inválidos!");
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginTela::new);
    }
}
