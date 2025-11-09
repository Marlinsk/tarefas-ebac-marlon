package br.com.ebac.animal_service.interfaces.dto;

import br.com.ebac.animal_service.domain.enums.Porte;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record AnimalRequestDTO(
        @NotBlank(message = "Nome provisório é obrigatório")
        String nomeProvisorio,

        @NotNull(message = "Idade estimada é obrigatória")
        @Positive(message = "Idade estimada deve ser positiva")
        Integer idadeEstimada,

        @NotBlank(message = "Raça ou espécie é obrigatória")
        String racaOuEspecie,

        @NotNull(message = "Data de entrada é obrigatória")
        LocalDate dataEntrada,

        String descricaoCondicao,

        @NotNull(message = "ID do funcionário recebedor é obrigatório")
        Long funcionarioRecebedorId,

        @NotNull(message = "Porte é obrigatório")
        Porte porte
) {
}
