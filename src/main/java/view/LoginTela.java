package view;

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

    public static class TelaPrincipal extends JFrame {

        private JPanel painelMenu;
        private JPanel painelConteudo;

        public TelaPrincipal() {
            setTitle("Sistema Adonai");
            setSize(1280, 800);
            setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre maximizado
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            criarMenuLateral();
            criarPainelConteudo();

            setVisible(true);
        }

        private void criarMenuLateral() {
            painelMenu = new JPanel();
            painelMenu.setBackground(new Color(30, 30, 30));
            painelMenu.setPreferredSize(new Dimension(220, 0));
            painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));

            JLabel lblTitulo = new JLabel("ADONAI");
            lblTitulo.setForeground(Color.WHITE);
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
            lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

            JButton btnDashboard = criarBotaoMenu("Dashboard");
            JButton btnMembros = criarBotaoMenu("Membros");
            JButton btnUsuarios = criarBotaoMenu("Usuários");
            JButton btnFinanceiro = criarBotaoMenu("Financeiro");
            JButton btnRelatorios = criarBotaoMenu("Relatórios");
            JButton btnConfiguracoes = criarBotaoMenu("Configurações");

            painelMenu.add(lblTitulo);
            painelMenu.add(btnDashboard);
            painelMenu.add(btnMembros);
            painelMenu.add(btnUsuarios);
            painelMenu.add(btnFinanceiro);
            painelMenu.add(btnRelatorios);
            painelMenu.add(Box.createVerticalGlue());
            painelMenu.add(btnConfiguracoes);

            add(painelMenu, BorderLayout.WEST);
            // 👉 AQUI é o clique do botão Membros
            btnMembros.addActionListener(e -> mostrarTela(new PainelMembros()));
        }

        private JButton criarBotaoMenu(String texto) {
            JButton botao = new JButton(texto);
            botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            botao.setFocusPainted(false);
            botao.setForeground(Color.WHITE);
            botao.setBackground(new Color(45, 45, 45));
            botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            botao.setHorizontalAlignment(SwingConstants.LEFT);

            botao.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    botao.setBackground(new Color(60, 60, 60));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    botao.setBackground(new Color(45, 45, 45));
                }
            });

            return botao;
        }

        private void criarPainelConteudo() {
            painelConteudo = new JPanel(new BorderLayout());
            painelConteudo.setBackground(Color.WHITE);

            JLabel lbl = new JLabel("Bem-vindo ao Sistema Adonai", SwingConstants.CENTER);
            lbl.setFont(new Font("Arial", Font.BOLD, 22));

            painelConteudo.add(lbl, BorderLayout.CENTER);
            add(painelConteudo, BorderLayout.CENTER);
        }

        // 🔁 Método que troca a tela central
        private void mostrarTela(JPanel painel) {
            painelConteudo.removeAll();
            painelConteudo.add(painel, BorderLayout.CENTER);
            painelConteudo.revalidate();
            painelConteudo.repaint();
        }

    }
}
