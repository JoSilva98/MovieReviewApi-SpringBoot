package mindswap.academy.moviereview_api.service.movie.writer;

import lombok.RequiredArgsConstructor;
import mindswap.academy.moviereview_api.command.movie.writer.WriterDto;
import mindswap.academy.moviereview_api.command.movie.writer.WriterUpdateDto;
import mindswap.academy.moviereview_api.converter.movie.writer.IWriterConverter;
import mindswap.academy.moviereview_api.exception.ConflictException;
import mindswap.academy.moviereview_api.exception.NotFoundException;
import mindswap.academy.moviereview_api.persistence.model.movie.writer.Writer;
import mindswap.academy.moviereview_api.persistence.repository.movie.writer.IWriterRepository;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static mindswap.academy.moviereview_api.exception.ExceptionMessages.WRITER_IS_BEING_USED;
import static mindswap.academy.moviereview_api.exception.ExceptionMessages.WRITER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class WriterService implements IWriterService {
    private final IWriterConverter writerConverter;
    private final IWriterRepository writerRepository;
    private final CacheManager cacheManager;

    @Override
    @Cacheable("writers")
    public List<WriterDto> getAll() {
        List<Writer> writerList = this.writerRepository.findAll();
        return this.writerConverter.converterList(writerList, WriterDto.class);
    }

    @Override
    public WriterDto add(WriterDto writerDto) {
        Writer writer = this.writerConverter.converter(writerDto, Writer.class);
        clearCacheWriterAndMovie();
        Writer savedWriter = this.writerRepository.save(writer);
        return this.writerConverter.converter(savedWriter, WriterDto.class);
    }

    @Override
    @CacheEvict(key = "#id", value = "writer")
    public ResponseEntity<Object> delete(Long id) {
        this.writerRepository.checkIfWriterIsBeingUsed(id)
                .ifPresent((writer) -> {
                    throw new ConflictException(WRITER_IS_BEING_USED);
                });
        Writer writer = this.writerRepository.findById(id).orElseThrow(() -> new NotFoundException(WRITER_NOT_FOUND));
        this.writerRepository.delete(writer);
        clearCacheWriterAndMovie();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @CacheEvict(key = "#id", value = "writer")
    public WriterDto update(Long id, WriterUpdateDto writerUpdateDto) {
        Writer oldWriter = this.writerRepository.findById(id).orElseThrow(() -> new NotFoundException("Writer not found"));
        clearCacheWriterAndMovie();
        Writer updatedWriter = this.writerRepository.save(this.writerConverter.converterUpdate(writerUpdateDto, oldWriter));
        return this.writerConverter.converter(updatedWriter, WriterDto.class);
    }

    private void clearCacheWriterAndMovie() {
        Cache  writerCache = this.cacheManager.getCache("writers");
        Cache  movieCache = this.cacheManager.getCache("movies");
        if(writerCache!=null)writerCache.clear();
        if(movieCache!=null)movieCache.clear();
    }
}
