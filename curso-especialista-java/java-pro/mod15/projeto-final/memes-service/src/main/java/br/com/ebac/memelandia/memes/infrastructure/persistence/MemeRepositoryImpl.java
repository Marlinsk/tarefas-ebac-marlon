package br.com.ebac.memelandia.memes.infrastructure.persistence;

import br.com.ebac.memelandia.memes.domain.entity.Meme;
import br.com.ebac.memelandia.memes.domain.repository.MemeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MemeRepositoryImpl implements MemeRepository {

    private final SpringDataMemeRepository springDataRepository;

    public MemeRepositoryImpl(SpringDataMemeRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Meme salvar(Meme meme) {
        MemeEntity entity = MemeEntity.fromDomain(meme);
        MemeEntity savedEntity = springDataRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Meme> buscarPorId(Long id) {
        return springDataRepository.findById(id).map(MemeEntity::toDomain);
    }

    @Override
    public List<Meme> buscarTodos() {
        return springDataRepository.findAll().stream().map(MemeEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Meme> buscarPorUsuarioId(Long usuarioId) {
        return springDataRepository.findByUsuarioId(usuarioId).stream().map(MemeEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Meme> buscarPorCategoriaId(Long categoriaId) {
        return springDataRepository.findByCategoriaId(categoriaId).stream().map(MemeEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public boolean existePorId(Long id) {
        return springDataRepository.existsById(id);
    }
}
