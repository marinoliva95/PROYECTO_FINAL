package com.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rest.model.ScoredRecordEntity;
import com.rest.model.StudentEntity;
import com.rest.services.ScoredRecordService;
import com.rest.services.StudentService;
import com.rest.services.SubjectService;

@Controller
public class StudentController extends AbstractResourceController{
	
	@Autowired
	private StudentService studentService;
	
	@Autowired 
	private SubjectService subjectService;
	
	@Autowired
	private ScoredRecordService scoredRecordService;
	
	//student login

	@GetMapping(value = "/student/login")
	@ResponseBody
	public StudentEntity studentLogin(@RequestParam("email") String email, @RequestParam("pass") String pass){
		return studentService.login(email, pass);
	}
	//student subject enroll
	@RequestMapping(value = "/student/enroll", method = RequestMethod.POST)
	@ResponseBody
	public void subjectEnroll(@RequestBody StudentEntity student, @RequestParam("subject") String code){
		//StudentEntity student = studentService.getStudentById(1);
		
		//first we create the new scoredRecord
		ScoredRecordEntity scoredRecordEntity = new ScoredRecordEntity();
		scoredRecordEntity.setStudent(student);
		scoredRecordEntity.setSubject(subjectService.getSubjectByCode(code));
		
		//we add to the DB
		scoredRecordService.saveScoredRecord(scoredRecordEntity);
		
		System.out.println("----------------------------------------");
		System.out.println("STUDENT -> "+scoredRecordEntity.getStudent().getStudentId());
		System.out.println("SUBJECT -> "+scoredRecordEntity.getSubject().getSubjectId());
		//we add the relation to the student
		
		List<ScoredRecordEntity> lista = scoredRecordService.getScoredRecordByStudentId(student.getStudentId());
		lista.add(scoredRecordEntity);
		student.setScoredRecord(lista);
		studentService.saveStudent(student);
	}

	@GetMapping(value = "/student")
	@ResponseBody
	public StudentEntity getStudentById(@RequestParam("id") int id) {
		return studentService.getStudentById(id);
	}

	@GetMapping("/student/list")
	@ResponseBody
	public List<StudentEntity> getAllStudent(){
		return studentService.listStudent();
	}
	
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	@ResponseBody
	public void postStudent(@RequestBody StudentEntity entity){
		studentService.saveStudent(entity);
	}
	
	@RequestMapping(value = "/student", method = RequestMethod.PUT)
	@ResponseBody
	public void putStudent(@RequestBody StudentEntity entity) {
		
		/*StudentEntity dto = studentService.getStudentById((entity.getStudentId()));
		dto.setStudentName(entity.getStudentName());
		dto.setLastNameParent1(entity.getLastNameParent1());
		dto.setLastNameParent2(entity.getLastNameParent2());
		dto.setDateOfBirth(entity.getDateOfBirth());
		dto.setPlaceOfBirth(entity.getPlaceOfBirth());
		dto.setSex(entity.getSex());
		dto.setTelephone(entity.getTelephone());
		dto.setMobilePhone(entity.getMobilePhone());
		dto.setAddress(entity.getAddress());
		dto.setParent1Name(entity.getParent1Name());
		dto.setParent2Name(entity.getParent2Name());
		dto.setParent1MobilePhone(entity.getParent1MobilePhone());
		dto.setParent2MobilePhone(entity.getParent2MobilePhone());
		dto.setParent1Profession(entity.getParent1Profession());
		dto.setParent2Profession(entity.getParent2Profession());
		dto.setObservation(entity.getObservation());*/
		
		//TODO set of the list of ScoredRecords
		studentService.saveStudent(entity);
	}
	
	@RequestMapping(value = "/student", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteStudent(@RequestBody StudentEntity entity){
		studentService.deleteStudent(entity);
	}
	
}
