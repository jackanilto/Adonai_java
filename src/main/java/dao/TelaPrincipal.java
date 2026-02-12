import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private JPanel painelConteudo;

    public TelaPrincipal() {
        setTitle("Sistema Adonai - Principal");

        setSize(1280, 800);
        setMinimumSize(new Dimension(1024, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLayout(new BorderLayout());

        // Painel lateral (menu)
        JPanel menuLateral = new JPanel();
        menuLateral.setPreferredSize(new Dimension(220, 0));
        menuLateral.setLayout(new GridLayout(0, 1, 10, 10));
        menuLateral.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton btnDashboard = new JButton("Dashboard");
        JButton btnUsuarios = new JButton("Usuários");
        JButton btnMembros = new JButton("Membros");
        JButton btnRelatorios = new JButton("Relatórios");

        menuLateral.add(btnDashboard);
        menuLateral.add(btnUsuarios);
        menuLateral.add(btnMembros);
        menuLateral.add(btnRelatorios);

        // Painel central (conteúdo que muda)
        painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.add(new JLabel("Bem-vindo ao Sistema!", SwingConstants.CENTER), BorderLayout.CENTER);

        add(menuLateral, BorderLayout.WEST);
        add(painelConteudo, BorderLayout.CENTER);

        // Ações dos botões
        btnDashboard.addActionListener(e -> trocarConteudo("Dashboard"));
        btnUsuarios.addActionListener(e -> trocarConteudo("Usuários"));
        btnMembros.addActionListener(e -> trocarConteudo("Membros"));
        btnRelatorios.addActionListener(e -> trocarConteudo("Relatórios"));

        setVisible(true);
    }

    private void trocarConteudo(String tela) {
        painelConteudo.removeAll();
        painelConteudo.add(new JLabel("Tela: " + tela, SwingConstants.CENTER), BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }
}
