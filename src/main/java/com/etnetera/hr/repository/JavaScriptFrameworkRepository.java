package com.etnetera.hr.repository;

import com.etnetera.hr.data.JavaScriptFramework;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

/**
 * Spring data repository interface used for accessing the data in database.
 *
 * @author Etnetera
 */
public interface JavaScriptFrameworkRepository extends CrudRepository<JavaScriptFramework, Long> {

    Iterable<JavaScriptFramework> findAllByArchivedFalse();

    Iterable<JavaScriptFramework> findAllByNameContaining(String nameLike);

    Iterable<JavaScriptFramework> findAllByArchivedTrueAndModifiedBefore(LocalDateTime dateTime);
}
