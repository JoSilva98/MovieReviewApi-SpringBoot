package mindswap.academy.moviereview_api.converter;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public interface IConverter {
    ModelMapper getMapper();

    default <T, D> D converter(T inClass, Class<D> outClass) {
        return getMapper().map(inClass, outClass);
    }

    default <D, T> List<D> converterList(List<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> getMapper().map(entity, outCLass))
                .collect(Collectors.toList());
    }
}
