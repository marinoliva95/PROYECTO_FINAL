package com.rest.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.rest.model.StudentEntity;
import com.rest.persistance.StudentRepository;
import com.rest.services.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository repository;

	@Override
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public StudentEntity getStudentById(int id) {
		return repository.findByStudentId(id);
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void saveStudent(StudentEntity entity) {
		try {
			repository.save(entity);
		} catch (DataAccessException e) {}
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void deleteStudent(StudentEntity entity) {
		try {
			repository.delete(entity);
		} catch (DataAccessException e) {}
	}

	@Override
	public List<StudentEntity> listStudent() {
		return repository.findAll();
	}
	
}