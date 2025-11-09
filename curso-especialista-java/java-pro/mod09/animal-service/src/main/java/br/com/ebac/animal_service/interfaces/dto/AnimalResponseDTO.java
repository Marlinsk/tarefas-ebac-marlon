package br.com.ebac.animal_service.interfaces.dto;

import br.com.ebac.animal_service.domain.enums.Porte;

import java.time.LocalDate;

public record AnimalResponseDTO(
        Long id,
        String nomeProvisorio,
        Integer idadeEstimada,
        String racaOuEspecie,
        LocalDate dataEntrada,
        LocalDate dataAdocao,
        String descricaoCondicao,
        Long funcionarioRecebedorId,
        String nomeFuncionarioRecebedor,
        LocalDate dataObito,
        Porte porte,
        boolean adotado,
        boolean disponivel
) {
}
