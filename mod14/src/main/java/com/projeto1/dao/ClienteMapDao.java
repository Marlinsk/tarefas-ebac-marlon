package main.java.com.projeto1.dao;

import main.java.com.projeto1.domain.Cliente;
import main.java.com.projeto1.domain.ClientePessoaFisica;
import main.java.com.projeto1.domain.ClientePessoaJuridica;

import java.util.*;
import java.util.stream.Collectors;

public class ClienteMapDao implements IClienteDao {

    private final Map<String, Cliente> map = new LinkedHashMap<>();
    private final Map<String, String> docIndex = new HashMap<>();

    @Override
    public Cliente salvar(Cliente entity) {
        String id = entity.getId();
        String novoDoc = documentoDe(entity);

        String dono = this.docIndex.get(novoDoc);
        if (!novoDoc.isEmpty() && dono != null && !dono.equals(id)) {
            System.out.println("⚠ Documento já cadastrado: " + novoDoc);
            return null;
        }

        Cliente antigo = this.map.get(id);
        String docAntigo = antigo == null ? "" : documentoDe(antigo);

        this.map.put(id, entity);

        if (!docAntigo.isEmpty() && !docAntigo.equals(novoDoc)) {
            this.docIndex.remove(docAntigo);
        }
        if (!novoDoc.isEmpty()) {
            this.docIndex.put(novoDoc, id);
        }

        return entity;
    }


    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(this.map.values());
    }

    @Override
    public boolean removerPorId(String id) {
        Cliente removido = this.map.remove(id);
        if (removido != null) {
            String doc = documentoDe(removido);
            if (!doc.isEmpty()) this.docIndex.remove(doc);
            return true;
        }
        return false;
    }

    @Override
    public Optional<ClientePessoaFisica> buscarPfPorCpf(String cpf) {
        String doc = normalizarDocumento(cpf);
        return this.map.values().stream().filter(c -> c instanceof ClientePessoaFisica).map(c -> (ClientePessoaFisica) c).filter(pf -> normalizarDocumento(pf.getCpf()).equals(doc)).findFirst();
    }

    @Override
    public Optional<ClientePessoaJuridica> buscarPjPorCnpj(String cnpj) {
        String doc = normalizarDocumento(cnpj);
        return this.map.values().stream().filter(c -> c instanceof ClientePessoaJuridica).map(c -> (ClientePessoaJuridica) c).filter(pj -> normalizarDocumento(pj.getCnpj()).equals(doc)).findFirst();
    }

    @Override
    public List<ClientePessoaFisica> listarPessoasFisicas() {
        return this.map.values().stream().filter(c -> c instanceof ClientePessoaFisica).map(c -> (ClientePessoaFisica) c).collect(Collectors.toList());
    }

    @Override
    public List<ClientePessoaJuridica> listarPessoasJuridicas() {
        return this.map.values().stream().filter(c -> c instanceof ClientePessoaJuridica).map(c -> (ClientePessoaJuridica) c).collect(Collectors.toList());
    }

    public boolean removerPorDocumento(String docEntrada) {
        String doc = this.normalizarDocumento(docEntrada);

        Optional<Cliente> opt = this.buscarPorDocumento(doc);
        if (opt.isEmpty()) {
            System.out.println("Nenhum cliente encontrado para o documento informado.");
            return false;
        }

        Cliente cliente = opt.get();
        boolean removido = this.removerPorId(cliente.getId());

        if (removido) System.out.println("Cliente removido com sucesso: " + cliente.getNome());
        else System.out.println("Erro ao remover cliente.");

        return removido;
    }

    private String normalizarDocumento(String doc) {
        return doc == null ? "" : doc.replaceAll("\\D", "");
    }

    private String documentoDe(Cliente c) {
        if (c instanceof ClientePessoaFisica) {
            return normalizarDocumento(((ClientePessoaFisica) c).getCpf());
        } else if (c instanceof ClientePessoaJuridica) {
            return normalizarDocumento(((ClientePessoaJuridica) c).getCnpj());
        }
        return "";
    }

    private Optional<Cliente> buscarPorDocumento(String docNormalizado) {
        Optional<ClientePessoaFisica> pf = this.map.values().stream().filter(c -> c instanceof ClientePessoaFisica).map(c -> (ClientePessoaFisica) c).filter(p -> normalizarDocumento(p.getCpf()).equals(docNormalizado)).findFirst();
        if (pf.isPresent()) return Optional.of(pf.get());

        Optional<ClientePessoaJuridica> pj = this.map.values().stream().filter(c -> c instanceof ClientePessoaJuridica).map(c -> (ClientePessoaJuridica) c).filter(p -> normalizarDocumento(p.getCnpj()).equals(docNormalizado)).findFirst();
        return pj.map(c -> c);
    }
}
