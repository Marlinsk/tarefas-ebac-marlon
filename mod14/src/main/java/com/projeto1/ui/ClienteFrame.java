package main.java.com.projeto1.ui;

import main.java.com.projeto1.dao.ClienteMapDao;
import main.java.com.projeto1.domain.Cliente;
import main.java.com.projeto1.domain.ClientePessoaFisica;
import main.java.com.projeto1.domain.ClientePessoaJuridica;
import main.java.com.projeto1.domain.Endereco;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteFrame extends JFrame {
    private final ClienteMapDao dao;

    private final JTable tabela;
    private final ClienteTableModel tableModel;
    private final JComboBox<String> filtroTipoCombo;

    private final JTextField pfNome = new JTextField();
    private final JTextField pfEmail = new JTextField();
    private final JTextField pfContato = new JTextField();
    private final JTextField pfCpf = new JTextField();
    private final JTextField pfDataNasc = new JTextField("2001-11-01");
    private final JTextField pfLogradouro = new JTextField();
    private final JTextField pfNumero = new JTextField();
    private final JTextField pfBairro = new JTextField();
    private final JTextField pfCidade = new JTextField();
    private final JTextField pfUf = new JTextField();
    private final JTextField pfCep = new JTextField();

    private final JTextField pjNome = new JTextField();
    private final JTextField pjEmail = new JTextField();
    private final JTextField pjContato = new JTextField();
    private final JTextField pjCnpj = new JTextField();
    private final JTextField pjRazao = new JTextField();
    private final JTextField pjFantasia = new JTextField();
    private final JTextField pjIe = new JTextField();
    private final JTextField pjDataFund = new JTextField("2001-11-01");
    private final JTextField pjLogradouro = new JTextField();
    private final JTextField pjNumero = new JTextField();
    private final JTextField pjBairro = new JTextField();
    private final JTextField pjCidade = new JTextField();
    private final JTextField pjUf = new JTextField();
    private final JTextField pjCep = new JTextField();

    public ClienteFrame(ClienteMapDao dao) {
        super("Cadastro de Clientes - GUI");
        this.dao = dao;

        this.tableModel = new ClienteTableModel();
        this.tabela = new JTable(this.tableModel);
        this.tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.filtroTipoCombo = new JComboBox<>(new String[] { "Todos", "PF", "PJ" });

        this.setLayout(new BorderLayout());
        this.add(buildTopBar(), BorderLayout.NORTH);
        this.add(new JScrollPane(this.tabela), BorderLayout.CENTER);

        this.bindActions();

        this.refreshTable("Todos");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 524);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private JPanel buildTopBar() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.add(new JLabel("Exibir:"));
        left.add(this.filtroTipoCombo);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnCadastrar = new JButton("Cadastrar...");
        JButton btnBuscar = new JButton("Buscar...");
        JButton btnEditarSel = new JButton("Editar selecionado...");
        JButton btnRemoverSel = new JButton("Remover selecionado...");

        btnCadastrar.addActionListener(e -> showCadastrarDialog());
        btnBuscar.addActionListener(e -> showBuscarDialog());
        btnEditarSel.addActionListener(e -> editarSelecionado());
        btnRemoverSel.addActionListener(e -> removerSelecionado());

        right.add(btnCadastrar);
        right.add(btnBuscar);
        right.add(btnEditarSel);
        right.add(btnRemoverSel);

        panel.add(left, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);
        return panel;
    }

    private void showCadastrarDialog() {
        String[] tipos = { "Pessoa Física (PF)", "Pessoa Jurídica (PJ)" };

        int escolha = JOptionPane.showOptionDialog(this, "Qual tipo de cliente deseja cadastrar?", "Novo cadastro", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
        if (escolha == JOptionPane.CLOSED_OPTION) return;

        if (escolha == 0) {
            this.limparFormPF();
            JPanel form = this.buildFormPF(null);
            JDialog dialog = new JDialog(this, "Cadastrar PF", true);
            dialog.setContentPane(form);
            dialog.pack();
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);

            this.filtroTipoCombo.setSelectedItem("PF");
            this.refreshTable("PF");
        } else {
            this.limparFormPJ();
            JPanel form = this.buildFormPJ(null);
            JDialog dialog = new JDialog(this, "Cadastrar PJ", true);
            dialog.setContentPane(form);
            dialog.pack();
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);

            this.filtroTipoCombo.setSelectedItem("PJ");
            this.refreshTable("PJ");
        }
    }

    private void showBuscarDialog() {
        JTextField cpfField  = new JTextField(18);
        JTextField cnpjField = new JTextField(18);

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        gc.gridx = 0;
        gc.gridy = 0;
        panel.add(new JLabel("CPF:"), gc);
        gc.gridx = 1;
        gc.gridy = 0;
        panel.add(cpfField, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        panel.add(new JLabel("CNPJ:"), gc);
        gc.gridx = 1;
        gc.gridy = 1;
        panel.add(cnpjField, gc);

        int opt = JOptionPane.showConfirmDialog(this, panel, "Buscar cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opt != JOptionPane.OK_OPTION) return;

        String cpf = cpfField.getText().trim();
        String cnpj = cnpjField.getText().trim();

        if (!cpf.isEmpty()) {
            this.dao.buscarPfPorCpf(cpf).ifPresentOrElse(pf -> {
                JOptionPane.showMessageDialog(this, pf.toString(), "PF encontrado", JOptionPane.INFORMATION_MESSAGE);
                selectClienteInTable(pf);
            }, () -> JOptionPane.showMessageDialog(this, "PF não encontrado", "Busca", JOptionPane.WARNING_MESSAGE));
            return;
        }

        if (!cnpj.isEmpty()) {
            this.dao.buscarPjPorCnpj(cnpj).ifPresentOrElse(pj -> {
                JOptionPane.showMessageDialog(this, pj.toString(), "PJ encontrado", JOptionPane.INFORMATION_MESSAGE);
                selectClienteInTable(pj);
            }, () -> JOptionPane.showMessageDialog(this, "PJ não encontrado", "Busca", JOptionPane.WARNING_MESSAGE));
            return;
        }

        JOptionPane.showMessageDialog(this, "Informe CPF ou CNPJ.", "Busca", JOptionPane.WARNING_MESSAGE);
    }

    private void editarSelecionado() {
        int row = this.tabela.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha na tabela.", "Editar", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = this.tableModel.getAt(row);

        if (cliente instanceof ClientePessoaFisica) {
            ClientePessoaFisica pf = (ClientePessoaFisica) cliente;
            this.preencherFormPF(pf);
            JPanel form = this.buildFormPF(pf);
            JDialog dialog = new JDialog(this, "Editar PF", true);
            dialog.setContentPane(form);
            dialog.pack();
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            this.refreshTable((String) this.filtroTipoCombo.getSelectedItem());

        } else if (cliente instanceof ClientePessoaJuridica) {
            ClientePessoaJuridica pj = (ClientePessoaJuridica) cliente;
            this.preencherFormPJ(pj);
            JPanel form = this.buildFormPJ(pj);
            JDialog dialog = new JDialog(this, "Editar PJ", true);
            dialog.setContentPane(form);
            dialog.pack();
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            this.refreshTable((String) this.filtroTipoCombo.getSelectedItem());
        }
    }

    private void removerSelecionado() {
        int row = this.tabela.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha na tabela.", "Remover", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = this.tableModel.getAt(row);

        String doc;

        if (cliente instanceof ClientePessoaFisica) {
            doc = ((ClientePessoaFisica) cliente).getCpf();
        } else if (cliente instanceof ClientePessoaJuridica) {
            doc = ((ClientePessoaJuridica) cliente).getCnpj();
        } else {
            JOptionPane.showMessageDialog(this, "Tipo desconhecido.", "Remover", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int opt = JOptionPane.showConfirmDialog(this, "Remover cliente " + doc + "?", "Confirmação", JOptionPane.YES_NO_OPTION);

        if (opt == JOptionPane.YES_OPTION) {
            boolean ok = this.dao.removerPorDocumento(doc);
            if (ok) {
                this.refreshTable((String) this.filtroTipoCombo.getSelectedItem());
            } else {
                JOptionPane.showMessageDialog(this, "Documento não encontrado.", "Remover", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void bindActions() {
        this.filtroTipoCombo.addActionListener(e -> this.refreshTable((String) this.filtroTipoCombo.getSelectedItem()));
    }

    private void selectClienteInTable(Cliente alvo) {
        if (alvo instanceof ClientePessoaFisica) this.filtroTipoCombo.setSelectedItem("PF");
        else if (alvo instanceof ClientePessoaJuridica) this.filtroTipoCombo.setSelectedItem("PJ");
        else this.filtroTipoCombo.setSelectedItem("Todos");

        this.refreshTable((String) this.filtroTipoCombo.getSelectedItem());

        for (int i = 0; i < this.tableModel.getRowCount(); i++) {
            Cliente cliente = this.tableModel.getAt(i);
            if (cliente.getId().equals(alvo.getId())) {
                this.tabela.getSelectionModel().setSelectionInterval(i, i);
                this.tabela.scrollRectToVisible(this.tabela.getCellRect(i, 0, true));
                break;
            }
        }
    }

    private JPanel buildFormPF(ClientePessoaFisica existente) {
        JPanel root = new JPanel(new BorderLayout(8, 8));

        Dimension fieldSize = new Dimension(220, 26);

        JPanel pessoaJPanel = new JPanel(new GridBagLayout());
        pessoaJPanel.setBorder(BorderFactory.createTitledBorder("Dados da Pessoa Física"));

        GridBagConstraints gridA = new GridBagConstraints();
        gridA.insets = new Insets(6,6,6,6);
        gridA.fill = GridBagConstraints.HORIZONTAL;
        gridA.weightx = 1;

        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField contatoField = new JTextField();
        JTextField cpfField = new JTextField();
        JTextField nascField = new JTextField("2000-01-01");

        for (JTextField tf : new JTextField[]{nomeField, emailField, contatoField, cpfField, nascField}) tf.setPreferredSize(fieldSize);

        int row = 0;
        gridA.gridx = 0;
        gridA.gridy = row;
        pessoaJPanel.add(new JLabel("Nome:"), gridA);
        gridA.gridx = 1;
        pessoaJPanel.add(nomeField, gridA);
        gridA.gridx = 2;
        pessoaJPanel.add(new JLabel("Email:"), gridA);
        gridA.gridx = 3;
        pessoaJPanel.add(emailField, gridA);
        row++;

        gridA.gridx = 0;
        gridA.gridy = row;
        pessoaJPanel.add(new JLabel("Contato:"), gridA);
        gridA.gridx = 1;
        pessoaJPanel.add(contatoField, gridA);
        gridA.gridx = 2;
        pessoaJPanel.add(new JLabel("CPF:"), gridA);
        gridA.gridx = 3;
        pessoaJPanel.add(cpfField, gridA);
        row++;

        gridA.gridx = 0;
        gridA.gridy = row;
        pessoaJPanel.add(new JLabel("Data Nasc. (YYYY-MM-DD):"), gridA);
        gridA.gridx = 1;
        pessoaJPanel.add(nascField, gridA);
        row++;

        JPanel enderecoJPanel = new JPanel(new GridBagLayout());
        enderecoJPanel.setBorder(BorderFactory.createTitledBorder("Endereço"));

        GridBagConstraints gridB = new GridBagConstraints();
        gridB.insets = new Insets(6,6,6,6);
        gridB.fill = GridBagConstraints.HORIZONTAL;
        gridB.weightx = 1;

        JTextField logradouroField = new JTextField();
        JTextField numeroField = new JTextField();
        JTextField bairroField = new JTextField();
        JTextField cidadeField = new JTextField();
        JTextField ufField = new JTextField();
        JTextField cepField = new JTextField();

        for (JTextField tf : new JTextField[]{logradouroField, numeroField, bairroField, cidadeField, ufField, cepField}) tf.setPreferredSize(fieldSize);

        int rowEndereco = 0;
        gridB.gridx = 0;
        gridB.gridy = rowEndereco;
        enderecoJPanel.add(new JLabel("Logradouro:"), gridB);
        gridB.gridx = 1;
        enderecoJPanel.add(logradouroField, gridB);
        gridB.gridx = 2;
        enderecoJPanel.add(new JLabel("Número:"), gridB);
        gridB.gridx = 3;
        enderecoJPanel.add(numeroField, gridB);
        rowEndereco++;

        gridB.gridx = 0;
        gridB.gridy = rowEndereco;
        enderecoJPanel.add(new JLabel("Bairro:"), gridB);
        gridB.gridx = 1;
        enderecoJPanel.add(bairroField, gridB);
        gridB.gridx = 2;
        enderecoJPanel.add(new JLabel("Cidade:"), gridB);
        gridB.gridx = 3;
        enderecoJPanel.add(cidadeField, gridB);
        rowEndereco++;

        gridB.gridx = 0;
        gridB.gridy = rowEndereco;
        enderecoJPanel.add(new JLabel("UF:"), gridB);
        gridB.gridx = 1;
        enderecoJPanel.add(ufField, gridB);
        gridB.gridx = 2;
        enderecoJPanel.add(new JLabel("CEP:"), gridB);
        gridB.gridx = 3;
        enderecoJPanel.add(cepField, gridB);
        rowEndereco++;

        if (existente != null) {
            nomeField.setText(existente.getNome());
            emailField.setText(existente.getEmail());
            contatoField.setText(existente.getContato());
            cpfField.setText(existente.getCpf());
            nascField.setText(existente.getDataNascimento() != null ? existente.getDataNascimento().toString() : "2001-11-01");
            if (existente.getEndereco() != null) {
                logradouroField.setText(existente.getEndereco().getLogradouro());
                numeroField.setText(existente.getEndereco().getNumero());
                bairroField.setText(existente.getEndereco().getBairro());
                cidadeField.setText(existente.getEndereco().getCidade());
                ufField.setText(existente.getEndereco().getUf());
                cepField.setText(existente.getEndereco().getCep());
            }
        }

        JButton salvarBtn = new JButton(existente == null ? "Salvar PF" : "Salvar alterações");
        salvarBtn.addActionListener(e -> {
            this.pfNome.setText(nomeField.getText());
            this.pfEmail.setText(emailField.getText());
            this.pfContato.setText(contatoField.getText());
            this.pfCpf.setText(cpfField.getText());
            this.pfDataNasc.setText(nascField.getText());

            this.pfLogradouro.setText(logradouroField.getText());
            this.pfNumero.setText(numeroField.getText());
            this.pfBairro.setText(bairroField.getText());
            this.pfCidade.setText(cidadeField.getText());
            this.pfUf.setText(ufField.getText());
            this.pfCep.setText(cepField.getText());

            this.salvarPF(existente);
            Window w = SwingUtilities.getWindowAncestor(root);
            if (w != null) w.dispose();
            this.refreshTable((String) this.filtroTipoCombo.getSelectedItem());
        });

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rodape.add(salvarBtn);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.add(pessoaJPanel);
        centro.add(enderecoJPanel);

        root.add(centro, BorderLayout.CENTER);
        root.add(rodape, BorderLayout.SOUTH);
        return root;
    }

    private JPanel buildFormPJ(ClientePessoaJuridica existente) {
        JPanel root = new JPanel(new BorderLayout(8, 8));

        Dimension fieldSize = new Dimension(220, 26);

        JPanel empresaPanel = new JPanel(new GridBagLayout());
        empresaPanel.setBorder(BorderFactory.createTitledBorder("Dados da Pessoa Jurídica/Empresa"));

        GridBagConstraints gridA = new GridBagConstraints();
        gridA.insets = new Insets(6,6,6,6);
        gridA.fill = GridBagConstraints.HORIZONTAL;
        gridA.weightx = 1;

        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField contatoField = new JTextField();
        JTextField cnpjField = new JTextField();
        JTextField razaoField = new JTextField();
        JTextField fantasiaField = new JTextField();
        JTextField ieField = new JTextField();
        JTextField fundField = new JTextField("2001-11-01");

        for (JTextField tf : new JTextField[]{ nomeField, emailField, contatoField, cnpjField, razaoField, fantasiaField, ieField, fundField }) tf.setPreferredSize(fieldSize);

        int row = 0;
        gridA.gridx = 0;
        gridA.gridy = row;
        empresaPanel.add(new JLabel("Nome (contato):"), gridA);
        gridA.gridx = 1;
        empresaPanel.add(nomeField, gridA);
        gridA.gridx = 2;
        empresaPanel.add(new JLabel("Email:"), gridA);
        gridA.gridx = 3;
        empresaPanel.add(emailField, gridA);
        row++;

        gridA.gridx = 0; gridA.gridy = row;
        empresaPanel.add(new JLabel("Telefone:"), gridA);
        gridA.gridx = 1;
        empresaPanel.add(contatoField, gridA);
        gridA.gridx = 2;
        empresaPanel.add(new JLabel("CNPJ:"), gridA);
        gridA.gridx = 3;
        empresaPanel.add(cnpjField, gridA);
        row++;

        gridA.gridx = 0;
        gridA.gridy = row;
        empresaPanel.add(new JLabel("Razão Social:"), gridA);
        gridA.gridx = 1;
        empresaPanel.add(razaoField, gridA);
        gridA.gridx = 2;
        empresaPanel.add(new JLabel("Nome Fantasia:"), gridA);
        gridA.gridx = 3;
        empresaPanel.add(fantasiaField, gridA);
        row++;

        gridA.gridx = 0;
        gridA.gridy = row;
        empresaPanel.add(new JLabel("IE:"), gridA);
        gridA.gridx = 1;
        empresaPanel.add(ieField, gridA);
        gridA.gridx = 2;
        empresaPanel.add(new JLabel("Data Fundação (YYYY-MM-DD):"), gridA);
        gridA.gridx = 3;
        empresaPanel.add(fundField, gridA);
        row++;

        JPanel enderecoPanel = new JPanel(new GridBagLayout());
        enderecoPanel.setBorder(BorderFactory.createTitledBorder("Endereço"));

        GridBagConstraints gridB = new GridBagConstraints();
        gridB.insets = new Insets(6,6,6,6);
        gridB.fill = GridBagConstraints.HORIZONTAL;
        gridB.weightx = 1;

        JTextField logradouroField = new JTextField();
        JTextField numeroField = new JTextField();
        JTextField bairroField = new JTextField();
        JTextField cidadeField = new JTextField();
        JTextField ufField = new JTextField();
        JTextField cepField = new JTextField();

        for (JTextField tf : new JTextField[]{ logradouroField, numeroField, bairroField, cidadeField, ufField, cepField }) tf.setPreferredSize(fieldSize);

        int rowEndereco = 0;
        gridB.gridx = 0;
        gridB.gridy = rowEndereco;
        enderecoPanel.add(new JLabel("Logradouro:"), gridB);
        gridB.gridx = 1;
        enderecoPanel.add(logradouroField, gridB);
        gridB.gridx = 2;
        enderecoPanel.add(new JLabel("Número:"), gridB);
        gridB.gridx = 3;
        enderecoPanel.add(numeroField, gridB);
        rowEndereco++;

        gridB.gridx = 0;
        gridB.gridy = rowEndereco;
        enderecoPanel.add(new JLabel("Bairro:"), gridB);
        gridB.gridx = 1;
        enderecoPanel.add(bairroField, gridB);
        gridB.gridx = 2;
        enderecoPanel.add(new JLabel("Cidade:"), gridB);
        gridB.gridx = 3;
        enderecoPanel.add(cidadeField, gridB);
        rowEndereco++;

        gridB.gridx = 0;
        gridB.gridy = rowEndereco;
        enderecoPanel.add(new JLabel("UF:"), gridB);
        gridB.gridx = 1;
        enderecoPanel.add(ufField, gridB);
        gridB.gridx = 2;
        enderecoPanel.add(new JLabel("CEP:"), gridB);
        gridB.gridx = 3;
        enderecoPanel.add(cepField, gridB);
        rowEndereco++;

        if (existente != null) {
            nomeField.setText(existente.getNome());
            emailField.setText(existente.getEmail());
            contatoField.setText(existente.getContato());
            cnpjField.setText(existente.getCnpj());
            razaoField.setText(existente.getRazaoSocial());
            fantasiaField.setText(existente.getNomeFantasia());
            ieField.setText(existente.getInscricaoEstadual());
            fundField.setText(existente.getDataFundacao() != null ? existente.getDataFundacao().toString() : "2001-11-01");
            if (existente.getEndereco() != null) {
                logradouroField.setText(existente.getEndereco().getLogradouro());
                numeroField.setText(existente.getEndereco().getNumero());
                bairroField.setText(existente.getEndereco().getBairro());
                cidadeField.setText(existente.getEndereco().getCidade());
                ufField.setText(existente.getEndereco().getUf());
                cepField.setText(existente.getEndereco().getCep());
            }
        }

        JButton salvarBtn = new JButton(existente == null ? "Salvar PJ" : "Salvar alterações");
        salvarBtn.addActionListener(e -> {
            this.pjNome.setText(nomeField.getText());
            this.pjEmail.setText(emailField.getText());
            this.pjContato.setText(contatoField.getText());
            this.pjCnpj.setText(cnpjField.getText());
            this.pjRazao.setText(razaoField.getText());
            this.pjFantasia.setText(fantasiaField.getText());
            this.pjIe.setText(ieField.getText());
            this.pjDataFund.setText(fundField.getText());

            this.pjLogradouro.setText(logradouroField.getText());
            this.pjNumero.setText(numeroField.getText());
            this.pjBairro.setText(bairroField.getText());
            this.pjCidade.setText(cidadeField.getText());
            this.pjUf.setText(ufField.getText());
            this.pjCep.setText(cepField.getText());

            this.salvarPJ(existente);
            Window w = SwingUtilities.getWindowAncestor(root);
            if (w != null) w.dispose();
            this.refreshTable((String) filtroTipoCombo.getSelectedItem());
        });

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rodape.add(salvarBtn);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.add(empresaPanel);
        centro.add(enderecoPanel);

        root.add(centro, BorderLayout.CENTER);
        root.add(rodape, BorderLayout.SOUTH);
        return root;
    }

    private void refreshTable(String filtro) {
        List<Cliente> dados = new ArrayList<>();

        if ("PF".equals(filtro)) dados.addAll(this.dao.listarPessoasFisicas());
        else if ("PJ".equals(filtro)) dados.addAll(this.dao.listarPessoasJuridicas());
        else dados.addAll(this.dao.listarTodos());

        this.tableModel.setData(dados);
    }

    private void salvarPF(ClientePessoaFisica existente) {
        String nome = this.pfNome.getText().trim();
        String email = this.pfEmail.getText().trim();
        String contato = this.pfContato.getText().trim();
        String cpf = this.pfCpf.getText().trim();
        LocalDate dataNasc = LocalDate.parse(this.pfDataNasc.getText().trim());

        Endereco endereco = new Endereco(this.pfLogradouro.getText().trim(), this.pfNumero.getText().trim(), this.pfBairro.getText().trim(), this.pfCidade.getText().trim(), this.pfUf.getText().trim(), this.pfCep.getText().trim());

        ClientePessoaFisica pf;

        if (existente == null) {
            pf = new ClientePessoaFisica(nome, email, contato, endereco, cpf, dataNasc);
        } else {
            existente.setNome(nome);
            existente.setEmail(email);
            existente.setContato(contato);
            existente.setEndereco(endereco);
            existente.setCpf(cpf);
            existente.setDataNascimento(dataNasc);
            pf = existente;
        }

        this.dao.salvar(pf);
        JOptionPane.showMessageDialog(this, "PF salvo com sucesso!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
        this.refreshTable((String) this.filtroTipoCombo.getSelectedItem());
    }

    private void salvarPJ(ClientePessoaJuridica existente) {
        String nome = this.pjNome.getText().trim();
        String email = this.pjEmail.getText().trim();
        String contato = this.pjContato.getText().trim();

        String cnpj = this.pjCnpj.getText().trim();
        String razao = this.pjRazao.getText().trim();
        String fantasia = this.pjFantasia.getText().trim();
        String ie = this.pjIe.getText().trim();
        LocalDate dataFund = LocalDate.parse(this.pjDataFund.getText().trim());

        Endereco endereco = new Endereco(this.pjLogradouro.getText().trim(), this.pjNumero.getText().trim(), this.pjBairro.getText().trim(), this.pjCidade.getText().trim(), this.pjUf.getText().trim(), this.pjCep.getText().trim());

        ClientePessoaJuridica pj;

        if (existente == null) {
            pj = new ClientePessoaJuridica(nome, email, contato, endereco, cnpj, razao, fantasia, ie, dataFund);
        } else {
            existente.setNome(nome);
            existente.setEmail(email);
            existente.setContato(contato);
            existente.setEndereco(endereco);
            existente.setCnpj(cnpj);
            existente.setRazaoSocial(razao);
            existente.setNomeFantasia(fantasia);
            existente.setInscricaoEstadual(ie);
            existente.setDataFundacao(dataFund);
            pj = existente;
        }

        this.dao.salvar(pj);
        JOptionPane.showMessageDialog(this, "PJ salvo com sucesso!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
        this.refreshTable((String) this.filtroTipoCombo.getSelectedItem());
    }

    private void preencherFormPF(ClientePessoaFisica pf) {
        this.pfNome.setText(pf.getNome());
        this.pfEmail.setText(pf.getEmail());
        this.pfContato.setText(pf.getContato());
        this.pfCpf.setText(pf.getCpf());
        this.pfDataNasc.setText(pf.getDataNascimento().toString());
        if (pf.getEndereco() != null) {
            this.pfLogradouro.setText(pf.getEndereco().getLogradouro());
            this.pfNumero.setText(pf.getEndereco().getNumero());
            this.pfBairro.setText(pf.getEndereco().getBairro());
            this.pfCidade.setText(pf.getEndereco().getCidade());
            this.pfUf.setText(pf.getEndereco().getUf());
            this.pfCep.setText(pf.getEndereco().getCep());
        }
    }

    private void preencherFormPJ(ClientePessoaJuridica pj) {
        this.pjNome.setText(pj.getNome());
        this.pjEmail.setText(pj.getEmail());
        this.pjContato.setText(pj.getContato());
        this.pjCnpj.setText(pj.getCnpj());
        this.pjRazao.setText(pj.getRazaoSocial());
        this.pjFantasia.setText(pj.getNomeFantasia());
        this.pjIe.setText(pj.getInscricaoEstadual());
        this.pjDataFund.setText(pj.getDataFundacao().toString());
        if (pj.getEndereco() != null) {
            this.pjLogradouro.setText(pj.getEndereco().getLogradouro());
            this.pjNumero.setText(pj.getEndereco().getNumero());
            this.pjBairro.setText(pj.getEndereco().getBairro());
            this.pjCidade.setText(pj.getEndereco().getCidade());
            this.pjUf.setText(pj.getEndereco().getUf());
            this.pjCep.setText(pj.getEndereco().getCep());
        }
    }

    private void limparFormPF() {
        this.pfNome.setText("");
        this.pfEmail.setText("");
        this.pfContato.setText("");
        this.pfCpf.setText("");
        this.pfDataNasc.setText("2001-11-01");
        this.pfLogradouro.setText("");
        this.pfNumero.setText("");
        this.pfBairro.setText("");
        this.pfCidade.setText("");
        this.pfUf.setText("");
        this.pfCep.setText("");
    }

    private void limparFormPJ() {
        this.pjNome.setText("");
        this.pjEmail.setText("");
        this.pjContato.setText("");
        this.pjCnpj.setText("");
        this.pjRazao.setText("");
        this.pjFantasia.setText("");
        this.pjIe.setText("");
        this.pjDataFund.setText("2001-11-01");
        this.pjLogradouro.setText("");
        this.pjNumero.setText("");
        this.pjBairro.setText("");
        this.pjCidade.setText("");
        this.pjUf.setText("");
        this.pjCep.setText("");
    }
}
