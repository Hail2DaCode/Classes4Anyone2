package com.brian.classes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.brian.classes.models.Class;

@Repository
public interface ClassRepository extends CrudRepository<Class, Long> {
	List<Class> findAll();
	Optional<Class> findByTitle(String title);
	List<Class> findByTeacherId(Long id);
	List<Class> findByTeacherIdIsNotAndStudentsIdIs(Long teacherId, Long studentId);
	List<Class> findByTeacherIdNotAndStudentsIdIsNotOrStudentsIdIsNull(Long teacherId, Long studentId);
	@Query(value = "SELECT * FROM classes\r\n"
			+ "LEFT JOIN classes_students ON classes.id = classes_students.class_id\r\n"
			+ "WHERE teachers_id <> ?1 AND (student_id <> ?2 OR student_id IS Null);",
			 nativeQuery = true)
	List<Class> availableClasses(Long teachers_id, Long student_id);
}
