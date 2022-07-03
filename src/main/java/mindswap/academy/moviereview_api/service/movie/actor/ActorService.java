package mindswap.academy.moviereview_api.service.movie.actor;

import lombok.RequiredArgsConstructor;
import mindswap.academy.moviereview_api.command.movie.actor.ActorDto;
import mindswap.academy.moviereview_api.command.movie.actor.ActorUpdateDto;
import mindswap.academy.moviereview_api.converter.movie.actor.IActorConverter;
import mindswap.academy.moviereview_api.exception.NotFoundException;
import mindswap.academy.moviereview_api.persistence.model.movie.actor.Actor;
import mindswap.academy.moviereview_api.persistence.repository.movie.IMovieRepository;
import mindswap.academy.moviereview_api.persistence.repository.movie.actor.IActorRepository;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ActorService implements IActorService {
    private final IActorRepository actorRepository;
    private final IActorConverter actorConverter;
    private final IMovieRepository movieRepository;
    private final CacheManager cacheManager;

    @Override
    @Cacheable("actors")
    public List<ActorDto> getAll() {
        List<Actor> actorList = this.actorRepository.findAll();
        return this.actorConverter.converterList(actorList, ActorDto.class);
    }

    @Override
    public ActorDto add(ActorDto actorDto) {
        Actor actor = this.actorConverter.converter(actorDto, Actor.class);
        Objects.requireNonNull(this.cacheManager.getCache("actors")).clear();
        Objects.requireNonNull(this.cacheManager.getCache("movies")).clear();
        Actor savedActor = this.actorRepository.save(actor);
        return this.actorConverter.converter(savedActor, ActorDto.class);
    }

    @Override
    @CacheEvict(key = "#id", value = "actor")
    public ResponseEntity<Object> delete(Long id) {
        Objects.requireNonNull(this.cacheManager.getCache("actors")).clear();
        Objects.requireNonNull(this.cacheManager.getCache("movies")).clear();
        return null;
    }

    @Override
    @CacheEvict(key = "#id", value = "actor")
    public ActorDto update(Long id, ActorUpdateDto actor) {
        Actor oldActor = this.actorRepository.findById(id).orElseThrow(() -> new NotFoundException("Actor not found"));
        Objects.requireNonNull(this.cacheManager.getCache("actors")).clear();
        Objects.requireNonNull(this.cacheManager.getCache("movies")).clear();
        Actor updatedActor = this.actorRepository.save(this.actorConverter.converterUpdate(actor, oldActor));
        return this.actorConverter.converter(updatedActor, ActorDto.class);
    }
}
