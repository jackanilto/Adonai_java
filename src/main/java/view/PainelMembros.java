package view;

import dao.MembroDAO;
import model.Membro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    // 🔑 Guarda o ID do membro selecionado para edição
    private Integer idMembroSelecionado = null;

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
        btnNovo.addActionListener(e -> limparCampos());

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarOuAtualizar());

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());

        // Botão de exclusão
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirMembro());

        botoes.add(btnNovo);
        botoes.add(btnSalvar);
        botoes.add(btnLimpar);
        botoes.add(btnExcluir);

        add(botoes, BorderLayout.SOUTH);

        // 🖱 Clique na tabela para edição
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabela.getSelectedRow();

                if (linha != -1) {
                    // 🔑 Captura o ID do membro selecionado
                    idMembroSelecionado = Integer.parseInt(
                            tabela.getValueAt(linha, 0).toString()
                    );

                    txtRoll.setText(tabela.getValueAt(linha, 1).toString());
                    txtNome.setText(tabela.getValueAt(linha, 2).toString());
                    txtCpf.setText(tabela.getValueAt(linha, 3).toString());
                    txtTelefone.setText(tabela.getValueAt(linha, 4).toString());
                    txtEmail.setText(tabela.getValueAt(linha, 5).toString());

                    String ativo = tabela.getValueAt(linha, 6).toString();
                    chkAtivo.setSelected("Sim".equalsIgnoreCase(ativo));
                }
            }
        });

        // 🔄 Carregar dados ao abrir
        carregarTabela();
    }

    // 🔁 Decide se vai INSERIR ou ATUALIZAR
    private void salvarOuAtualizar() {
        try {
            if (txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome é obrigatório!");
                return;
            }

            Membro membro = new Membro();

            // Se tem ID selecionado, é UPDATE
            if (idMembroSelecionado != null) {
                membro.setId(idMembroSelecionado);
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

            if (idMembroSelecionado == null) {
                dao.salvar(membro);
                JOptionPane.showMessageDialog(this, "Membro cadastrado com sucesso!");
            } else {
                dao.atualizar(membro);
                JOptionPane.showMessageDialog(this, "Membro atualizado com sucesso!");
            }

            limparCampos();
            carregarTabela();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Roll deve ser um número!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
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

        // 🔄 Limpa o ID para voltar ao modo NOVO
        idMembroSelecionado = null;
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

    // Função para excluir membro
    private void excluirMembro() {
        if (idMembroSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Nenhum membro selecionado para exclusão.");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir este membro?",
                "Confirmação de exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                MembroDAO dao = new MembroDAO();
                boolean sucesso = dao.excluir(idMembroSelecionado);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Membro excluído com sucesso!");
                    limparCampos(); // Limpa os campos após a exclusão
                    carregarTabela(); // Atualiza a tabela para refletir a exclusão
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir o membro.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            }
        }
    }
}
