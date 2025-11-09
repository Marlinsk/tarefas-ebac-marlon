package br.com.ebac.animal_service.interfaces.dto;

import br.com.ebac.animal_service.domain.enums.Porte;

import java.time.LocalDate;

public record AnimalUpdateDTO(
        String nomeProvisorio,
        Integer idadeEstimada,
        String racaOuEspecie,
        String descricaoCondicao,
        Porte porte,
        LocalDate dataAdocao,
        LocalDate dataObito
) {
}
