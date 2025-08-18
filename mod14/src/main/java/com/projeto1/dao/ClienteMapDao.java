package main.java.com.projeto1.dao;

import main.java.com.projeto1.domain.Cliente;
import main.java.com.projeto1.domain.ClientePessoaFisica;
import main.java.com.projeto1.domain.ClientePessoaJuridica;
import main.java.com.projeto1.domain.Endereco;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ClienteMapDao implements IClienteDao {

    private final Map<String, Cliente> map = new LinkedHashMap<>();
    private final Set<String> documentos = new HashSet<>();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public Cliente salvar(Cliente entity) {
        String doc = null;

        if (entity instanceof ClientePessoaFisica) doc = normalizarDocumento(((ClientePessoaFisica) entity).getCpf());
        else if (entity instanceof ClientePessoaJuridica) doc = normalizarDocumento(((ClientePessoaJuridica) entity).getCnpj());

        if (doc != null && documentos.contains(doc)) {
            System.out.println("⚠ Documento já cadastrado: " + doc);
            return null;
        }

        Cliente existente = map.get(entity.getId());
        if (existente != null) removerPorId(entity.getId());

        map.put(entity.getId(), entity);
        if (doc != null) documentos.add(doc);

        return entity;
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(map.values());
    }

    @Override
    public boolean removerPorId(String id) {
        Cliente removido = map.remove(id);
        if (removido != null) {
            String doc = null;

            if (removido instanceof ClientePessoaFisica) doc = normalizarDocumento(((ClientePessoaFisica) removido).getCpf());
            else if (removido instanceof ClientePessoaJuridica) doc = normalizarDocumento(((ClientePessoaJuridica) removido).getCnpj());

            if (doc != null) documentos.remove(doc);
            return true;
        }
        return false;
    }

    @Override
    public Optional<ClientePessoaFisica> buscarPfPorCpf(String cpf) {
        String doc = normalizarDocumento(cpf);
        return map.values().stream().filter(c -> c instanceof ClientePessoaFisica).map(c -> (ClientePessoaFisica) c).filter(pf -> normalizarDocumento(pf.getCpf()).equals(doc)).findFirst();
    }

    @Override
    public Optional<ClientePessoaJuridica> buscarPjPorCnpj(String cnpj) {
        String doc = normalizarDocumento(cnpj);
        return map.values().stream().filter(c -> c instanceof ClientePessoaJuridica).map(c -> (ClientePessoaJuridica) c).filter(pj -> normalizarDocumento(pj.getCnpj()).equals(doc)).findFirst();
    }

    @Override
    public List<ClientePessoaFisica> listarPessoasFisicas() {
        return map.values().stream().filter(c -> c instanceof ClientePessoaFisica).map(c -> (ClientePessoaFisica) c).collect(Collectors.toList());
    }

    @Override
    public List<ClientePessoaJuridica> listarPessoasJuridicas() {
        return map.values().stream().filter(c -> c instanceof ClientePessoaJuridica).map(c -> (ClientePessoaJuridica) c).collect(Collectors.toList());
    }

    public Cliente cadastrar() {
        System.out.println("=== Cadastro de Cliente ===");

        int tipo = 0;
        while (tipo != 1 && tipo != 2) {
            System.out.print("Pessoa Física (1) ou Jurídica (2)? ");
            try {
                tipo = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                tipo = 0;
            }
            if (tipo != 1 && tipo != 2) System.out.println("Opção inválida. Tente novamente.");
        }

        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();

        System.out.print("E-mail: ");
        String email = scanner.nextLine().trim();

        System.out.print("Contato (celular/telefone): ");
        String contato = scanner.nextLine().trim();

        System.out.println("=== Endereço ===");

        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine().trim();

        System.out.print("Número: ");
        String numero = scanner.nextLine().trim();

        System.out.print("Bairro: ");
        String bairro = scanner.nextLine().trim();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine().trim();

        System.out.print("UF: ");
        String uf = scanner.nextLine().trim();

        System.out.print("CEP: ");
        String cep = scanner.nextLine().trim();

        Endereco endereco = new Endereco(logradouro, numero, bairro, cidade, uf, cep);

        Cliente cliente;
        if (tipo == 1) {
            String cpfNormalizado;
            String cpfDigitado;
            while (true) {
                System.out.print("CPF: ");
                cpfDigitado = scanner.nextLine().trim();
                cpfNormalizado = normalizarDocumento(cpfDigitado);

                if (cpfNormalizado.isEmpty()) {
                    System.out.println("CPF vazio. Tente novamente.");
                    continue;
                }
                if (documentos.contains(cpfNormalizado)) {
                    System.out.println("⚠ CPF já cadastrado. Informe outro CPF.");
                    continue;
                }
                break;
            }

            LocalDate dataNascimento = lerData("Data de Nascimento no formato (yyyy-MM-dd): ");

            cliente = new ClientePessoaFisica(nome, email, contato, endereco, cpfDigitado, dataNascimento);

        } else if (tipo == 2) {
            String cnpjNormalizado;
            String cnpjDigitado;
            while (true) {
                System.out.print("CNPJ: ");
                cnpjDigitado = scanner.nextLine().trim();
                cnpjNormalizado = normalizarDocumento(cnpjDigitado);

                if (cnpjNormalizado.isEmpty()) {
                    System.out.println("CNPJ vazio. Tente novamente.");
                    continue;
                }
                if (documentos.contains(cnpjNormalizado)) {
                    System.out.println("⚠ CNPJ já cadastrado. Informe outro CNPJ.");
                    continue;
                }
                break;
            }

            System.out.print("Razão Social: ");
            String razaoSocial = scanner.nextLine().trim();

            System.out.print("Nome Fantasia: ");
            String nomeFantasia = scanner.nextLine().trim();

            System.out.print("Inscrição Estadual: ");
            String inscricaoEstadual = scanner.nextLine().trim();

            LocalDate dataFundacao = lerData("Data de Fundação no formato (yyyy-MM-dd): ");

            cliente = new ClientePessoaJuridica(nome, email, contato, endereco, cnpjDigitado, razaoSocial, nomeFantasia, inscricaoEstadual, dataFundacao);
        } else {
            System.out.println("Não existe essa opção de cadastro: ");
            return  null;
        }

        salvar(cliente);
        System.out.println("Cliente cadastrado com sucesso: " + cliente);
        return cliente;
    }

    public Cliente editarPorDocumento() {
        System.out.print("Digite CPF ou CNPJ: ");
        String docEntrada = scanner.nextLine();
        String doc = normalizarDocumento(docEntrada);

        Optional<Cliente> opt = buscarPorDocumento(doc);

        if (!opt.isPresent()) {
            System.out.println("Nenhum cliente encontrado para o documento informado.");
            return null;
        }

        Cliente cliente = opt.get();
        String id = cliente.getId();

        System.out.println("Editando cliente com ID: " + id);
        return editarInterno(cliente);
    }

    public boolean removerPorDocumento() {
        System.out.print("Digite CPF ou CNPJ do cliente a remover: ");
        String docEntrada = scanner.nextLine();
        String doc = normalizarDocumento(docEntrada);

        Optional<Cliente> opt = buscarPorDocumento(doc);
        if (!opt.isPresent()) {
            System.out.println("Nenhum cliente encontrado para o documento informado.");
            return false;
        }

        Cliente cliente = opt.get();
        boolean removido = removerPorId(cliente.getId());

        if (removido) System.out.println("Cliente removido com sucesso: " + cliente.getNome());
        else System.out.println("Erro ao remover cliente.");

        return removido;
    }

    private String normalizarDocumento(String doc) {
        return doc == null ? "" : doc.replaceAll("\\D", "");
    }

    private LocalDate lerData(String label) {
        while (true) {
            System.out.print(label);
            String entrada = scanner.nextLine().trim();
            try {
                return LocalDate.parse(entrada);
            } catch (Exception e) {
                System.out.println("Data inválida. Use o formato yyyy-MM-dd.");
            }
        }
    }

    private Optional<Cliente> buscarPorDocumento(String docNormalizado) {

        Optional<ClientePessoaFisica> pf = map.values().stream().filter(c -> c instanceof ClientePessoaFisica).map(c -> (ClientePessoaFisica) c).filter(p -> normalizarDocumento(p.getCpf()).equals(docNormalizado)).findFirst();
        if (pf.isPresent()) return Optional.of(pf.get());

        Optional<ClientePessoaJuridica> pj = map.values().stream().filter(c -> c instanceof ClientePessoaJuridica).map(c -> (ClientePessoaJuridica) c).filter(p -> normalizarDocumento(p.getCnpj()).equals(docNormalizado)).findFirst();
        return pj.map(c -> (Cliente) c);
    }

    private Cliente editarInterno(Cliente cliente) {
        System.out.println("=== Editando Cliente ===");

        System.out.println("Atual (nome): " + cliente.getNome());
        System.out.print("Novo nome (ou Enter p/ manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.trim().isEmpty()) cliente.setNome(novoNome);

        System.out.println("Atual (email): " + cliente.getEmail());
        System.out.print("Novo email (ou Enter p/ manter): ");
        String novoEmail = scanner.nextLine();
        if (!novoEmail.trim().isEmpty()) cliente.setEmail(novoEmail);

        System.out.println("Atual (contato/celular): " + cliente.getContato());
        System.out.print("Novo celular (ou Enter p/ manter): ");
        String novoContatoCelular = scanner.nextLine();
        if (!novoContatoCelular.trim().isEmpty()) cliente.setContato(novoContatoCelular);

        Endereco e = cliente.getEndereco();
        System.out.println("=== Endereço ===");
        System.out.println("Atual (logradouro): " + e.getLogradouro());
        System.out.print("Novo (ou Enter p/ manter): ");
        String log = scanner.nextLine();
        if (!log.trim().isEmpty()) e = new Endereco(log, e.getNumero(), e.getBairro(), e.getCidade(), e.getUf(), e.getCep());

        System.out.println("Atual (número): " + e.getNumero());
        System.out.print("Novo (ou Enter p/ manter): ");
        String num = scanner.nextLine();
        if (!num.trim().isEmpty()) e = new Endereco(e.getLogradouro(), num, e.getBairro(), e.getCidade(), e.getUf(), e.getCep());

        System.out.println("Atual (bairro): " + e.getBairro());
        System.out.print("Novo (ou Enter p/ manter): ");
        String bai = scanner.nextLine();
        if (!bai.trim().isEmpty()) e = new Endereco(e.getLogradouro(), e.getNumero(), bai, e.getCidade(), e.getUf(), e.getCep());

        System.out.println("Atual (cidade): " + e.getCidade());
        System.out.print("Novo (ou Enter p/ manter): ");
        String cid = scanner.nextLine();
        if (!cid.trim().isEmpty()) e = new Endereco(e.getLogradouro(), e.getNumero(), e.getBairro(), cid, e.getUf(), e.getCep());

        System.out.println("Atual (UF): " + e.getUf());
        System.out.print("Novo (ou Enter p/ manter): ");
        String uf = scanner.nextLine();
        if (!uf.trim().isEmpty()) e = new Endereco(e.getLogradouro(), e.getNumero(), e.getBairro(), e.getCidade(), uf, e.getCep());

        System.out.println("Atual (CEP): " + e.getCep());
        System.out.print("Novo (ou Enter p/ manter): ");
        String cep = scanner.nextLine();
        if (!cep.trim().isEmpty()) e = new Endereco(e.getLogradouro(), e.getNumero(), e.getBairro(), e.getCidade(), e.getUf(), cep);

        cliente.setEndereco(e);

        if (cliente instanceof ClientePessoaFisica) {
            ClientePessoaFisica pf = (ClientePessoaFisica) cliente;

            System.out.println("CPF (não editável): " + pf.getCpf());
            System.out.println("Atual (data nascimento): " + pf.getDataNascimento());
            System.out.print("Nova data (yyyy-MM-dd ou Enter p/ manter): ");
            String d = scanner.nextLine();
            if (!d.trim().isEmpty()) pf.setDataNascimento(LocalDate.parse(d));

        } else if (cliente instanceof ClientePessoaJuridica) {
            ClientePessoaJuridica pj = (ClientePessoaJuridica) cliente;

            System.out.println("CNPJ (não editável): " + pj.getCnpj());

            System.out.println("Atual (Razão Social): " + pj.getRazaoSocial());
            System.out.print("Nova (ou Enter p/ manter): ");
            String rz = scanner.nextLine();
            if (!rz.trim().isEmpty()) pj.setRazaoSocial(rz);

            System.out.println("Atual (Nome Fantasia): " + pj.getNomeFantasia());
            System.out.print("Nova (ou Enter p/ manter): ");
            String nf = scanner.nextLine();
            if (!nf.trim().isEmpty()) pj.setNomeFantasia(nf);

            System.out.println("Atual (Inscrição Estadual): " + pj.getInscricaoEstadual());
            System.out.print("Nova (ou Enter p/ manter): ");
            String ie = scanner.nextLine();
            if (!ie.trim().isEmpty()) pj.setInscricaoEstadual(ie);

            System.out.println("Atual (Data Fundação): " + pj.getDataFundacao());
            System.out.print("Nova data (yyyy-MM-dd ou Enter p/ manter): ");
            String df = scanner.nextLine();
            if (!df.trim().isEmpty()) pj.setDataFundacao(LocalDate.parse(df));
        }

        System.out.println("Cliente atualizado com sucesso: " + cliente);
        return cliente;
    }
}
