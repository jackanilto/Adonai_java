package view;

import dao.MembroDAO;
import model.Membro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelMembros extends JPanel {

    private JTextField txtRoll;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JComboBox<String> cbSexo;
    private JCheckBox chkAtivo;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public PainelMembros() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Título
        JLabel titulo = new JLabel("Cadastro de Membros");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(titulo, BorderLayout.NORTH);

        // Painel do formulário
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Roll:"), gbc);
        gbc.gridx = 1;
        txtRoll = new JTextField(10);
        form.add(txtRoll, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(30);
        form.add(txtNome, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        txtCpf = new JTextField(14);
        form.add(txtCpf, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        txtTelefone = new JTextField(15);
        form.add(txtTelefone, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(30);
        form.add(txtEmail, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Sexo:"), gbc);
        gbc.gridx = 1;
        cbSexo = new JComboBox<>(new String[]{"Selecione", "Masculino", "Feminino"});
        form.add(cbSexo, gbc);

        y++;
        gbc.gridx = 1; gbc.gridy = y;
        chkAtivo = new JCheckBox("Membro ativo");
        form.add(chkAtivo, gbc);

        // 🔽 Tabela de membros
        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Roll", "Nome", "CPF", "Telefone", "Email", "Ativo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(form, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);

        // Botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnNovo = new JButton("Novo");

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarMembro());

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());

        botoes.add(btnNovo);
        botoes.add(btnSalvar);
        botoes.add(btnLimpar);

        add(botoes, BorderLayout.SOUTH);

        // 🔄 Carregar dados ao abrir
        carregarTabela();
    }

    private void salvarMembro() {
        try {
            Membro membro = new Membro();

            if (txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome é obrigatório!");
                return;
            }

            membro.setIdIgreja(1);
            membro.setRoll(Integer.parseInt(txtRoll.getText()));
            membro.setNome(txtNome.getText());
            membro.setCpf(txtCpf.getText());
            membro.setTelefone(txtTelefone.getText());
            membro.setEmail(txtEmail.getText());
            membro.setSexo(cbSexo.getSelectedItem().toString());
            membro.setAtivo(chkAtivo.isSelected());

            MembroDAO dao = new MembroDAO();
            dao.salvar(membro);

            JOptionPane.showMessageDialog(this, "Membro salvo com sucesso!");
            limparCampos();
            carregarTabela();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Roll deve ser um número!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar membro: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void limparCampos() {
        txtRoll.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        cbSexo.setSelectedIndex(0);
        chkAtivo.setSelected(false);
    }

    private void carregarTabela() {
        try {
            MembroDAO dao = new MembroDAO();
            List<Membro> membros = dao.listar();

            modeloTabela.setRowCount(0);

            for (Membro m : membros) {
                modeloTabela.addRow(new Object[]{
                        m.getId(),
                        m.getRoll(),
                        m.getNome(),
                        m.getCpf(),
                        m.getTelefone(),
                        m.getEmail(),
                        m.isAtivo() ? "Sim" : "Não"
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar membros:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
