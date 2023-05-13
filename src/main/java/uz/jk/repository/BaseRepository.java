package uz.jk.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BaseRepository<T> {
    ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);


    default List<T> readFromFile(String path, Class<T> elementType) {
        try {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, elementType);
            return objectMapper.readValue(new File(path), listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default void writeToFile(List<T> data, String path) {
        try {
            objectMapper.writeValue(new File(path), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Optional<T> save(T t);

    Optional<T> getById(UUID id);

    int deleteById(UUID id);

    List<T> getAll();

    int update(T update);
}
