package br.com.ebac.animal_service.usecase;

import br.com.ebac.animal_service.domain.entity.Animal;
import br.com.ebac.animal_service.domain.repository.AnimalRepository;
import br.com.ebac.animal_service.interfaces.dto.EstatisticaResgateResponseDTO;
import br.com.ebac.animal_service.usecase.exception.IntervaloDataInvalidoException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BuscarEstatisticasResgateUseCase {

    private static final long INTERVALO_MAXIMO_DIAS = 365;

    private final AnimalRepository animalRepository;

    public BuscarEstatisticasResgateUseCase(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<EstatisticaResgateResponseDTO> execute(LocalDate dataInicio, LocalDate dataFim) {
        validarIntervalo(dataInicio, dataFim);
        List<Animal> animais = animalRepository.findByDataEntradaBetween(dataInicio, dataFim);
        Map<String, Long> estatisticasPorFuncionario = animais.stream().collect(Collectors.groupingBy(animal -> animal.getFuncionarioRecebedor().getNome(), Collectors.counting()));
        return estatisticasPorFuncionario.entrySet().stream()
                .map(entry -> new EstatisticaResgateResponseDTO(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> b.quantidadeAnimaisResgatados().compareTo(a.quantidadeAnimaisResgatados()))
                .collect(Collectors.toList());
    }

    private void validarIntervalo(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio == null || dataFim == null) {
            throw new IntervaloDataInvalidoException("As datas de início e fim são obrigatórias");
        }

        if (dataInicio.isAfter(dataFim)) {
            throw new IntervaloDataInvalidoException("A data de início não pode ser posterior à data de fim");
        }

        long diasIntervalo = ChronoUnit.DAYS.between(dataInicio, dataFim);
        if (diasIntervalo > INTERVALO_MAXIMO_DIAS) {
            throw new IntervaloDataInvalidoException(
                    String.format("O intervalo máximo permitido é de %d dias (1 ano). Intervalo informado: %d dias",
                            INTERVALO_MAXIMO_DIAS, diasIntervalo)
            );
        }
    }
}
