package main.java.com.projeto1.ui;

import main.java.com.projeto1.domain.Cliente;
import main.java.com.projeto1.domain.ClientePessoaFisica;
import main.java.com.projeto1.domain.ClientePessoaJuridica;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ClienteTableModel extends AbstractTableModel {
    private final String[] cols = {"Tipo", "Nome/Razão", "Documento", "Email", "Telefone"};
    private List<Cliente> data = new ArrayList<>();

    public void setData(List<Cliente> nova) {
        this.data = new ArrayList<>(nova);
        fireTableDataChanged();
    }

    public Cliente getAt(int row) {
        return data.get(row);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int c) {
        return cols[c];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente c = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return (c instanceof ClientePessoaFisica) ? "PF" : (c instanceof ClientePessoaJuridica) ? "PJ" : "?";
            case 1:
                if (c instanceof ClientePessoaFisica) return c.getNome();
                if (c instanceof ClientePessoaJuridica) return ((ClientePessoaJuridica) c).getRazaoSocial();
                return c.getNome();
            case 2:
                if (c instanceof ClientePessoaFisica) return ((ClientePessoaFisica) c).getCpf();
                if (c instanceof ClientePessoaJuridica) return ((ClientePessoaJuridica) c).getCnpj();
                return "";
            case 3:
                return c.getEmail();
            case 4:
                return c.getContato();
            default:
                return "";
        }
    }
}

