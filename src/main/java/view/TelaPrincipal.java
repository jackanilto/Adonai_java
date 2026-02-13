package view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private JPanel painelCentral;   // Área onde as telas (cards) serão exibidas
    private CardLayout cardLayout;  // Gerenciador de troca de telas

    public TelaPrincipal() {
        setTitle("Sistema de Gestão da Igreja");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== MENU LATERAL =====
        JPanel menu = new JPanel();
        menu.setBackground(new Color(30, 30, 30));
        menu.setPreferredSize(new Dimension(220, 0));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("MENU");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton btnMembros = new JButton("Membros");
        JButton btnDashboard = new JButton("Dashboard");
        JButton btnSair = new JButton("Sair");

        estilizarBotaoMenu(btnDashboard);
        estilizarBotaoMenu(btnMembros);
        estilizarBotaoMenu(btnSair);

        menu.add(lblTitulo);
        menu.add(btnDashboard);
        menu.add(Box.createVerticalStrut(10));
        menu.add(btnMembros);
        menu.add(Box.createVerticalGlue());
        menu.add(btnSair);

        add(menu, BorderLayout.WEST);

        // ===== PAINEL CENTRAL COM CARDS =====
        cardLayout = new CardLayout();
        painelCentral = new JPanel(cardLayout);

        // Telas (cards)
        JPanel painelDashboard = criarPainelDashboard();
        PainelMembros painelMembros = new PainelMembros();

        painelCentral.add(painelDashboard, "DASHBOARD");
        painelCentral.add(painelMembros, "MEMBROS");

        add(painelCentral, BorderLayout.CENTER);

        // ===== AÇÕES DOS BOTÕES =====
        btnDashboard.addActionListener(e -> cardLayout.show(painelCentral, "DASHBOARD"));
        btnMembros.addActionListener(e -> cardLayout.show(painelCentral, "MEMBROS"));
        btnSair.addActionListener(e -> System.exit(0));
    }

    // Estilo padrão dos botões do menu
    private void estilizarBotaoMenu(JButton btn) {
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    // Tela inicial (Dashboard simples)
    private JPanel criarPainelDashboard() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel("Bem-vindo ao Sistema de Gestão da Igreja", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 24));
        lbl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(lbl, BorderLayout.CENTER);

        return painel;
    }

    // MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}
