package br.com.ebac.memelandia.categoria.infrastructure.persistence;

import br.com.ebac.memelandia.categoria.domain.entities.CategoriaMeme;
import br.com.ebac.memelandia.categoria.domain.repository.CategoriaMemeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CategoriaMemeRepositoryImpl implements CategoriaMemeRepository {

    private final SpringDataCategoriaMemeRepository springDataRepository;

    public CategoriaMemeRepositoryImpl(SpringDataCategoriaMemeRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public CategoriaMeme salvar(CategoriaMeme categoriaMeme) {
        CategoriaMemeEntity entity = CategoriaMemeEntity.fromDomain(categoriaMeme);
        CategoriaMemeEntity savedEntity = springDataRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<CategoriaMeme> buscarPorId(Long id) {
        return springDataRepository.findById(id).map(CategoriaMemeEntity::toDomain);
    }

    @Override
    public List<CategoriaMeme> buscarTodas() {
        return springDataRepository.findAll().stream().map(CategoriaMemeEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<CategoriaMeme> buscarPorUsuarioId(Long usuarioId) {
        return springDataRepository.findByUsuarioId(usuarioId).stream().map(CategoriaMemeEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public boolean existePorNomeEUsuarioId(String nome, Long usuarioId) {
        return springDataRepository.existsByNomeAndUsuarioId(nome, usuarioId);
    }
}
