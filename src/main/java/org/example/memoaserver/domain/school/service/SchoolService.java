package org.example.memoaserver.domain.school.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.school.dto.DepartmentDTO;
import org.example.memoaserver.domain.school.dto.SchoolDTO;
import org.example.memoaserver.domain.school.entity.DepartmentEntity;
import org.example.memoaserver.domain.school.entity.SchoolEntity;
import org.example.memoaserver.domain.school.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;

    public void addSchool(SchoolDTO school) {
        log.info(school.getName());

        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setName(school.getName());


        List<DepartmentEntity> departmentEntities = new ArrayList<>();

        for (DepartmentDTO department : school.getDepartments()) {
            DepartmentEntity departmentEntity = new DepartmentEntity();
            departmentEntity.setName(department.getName());
            departmentEntity.setGrade(department.getGrade());
            departmentEntity.setSubjects(department.getSubjects());
            departmentEntity.setSchoolEntity(schoolEntity);
            departmentEntities.add(departmentEntity);
        }

        schoolEntity.setDepartments(departmentEntities);

        schoolRepository.save(schoolEntity);
    }

    public List<SchoolEntity> searchSchool(String schoolName) {
        List<SchoolEntity> school = schoolRepository.findByNameContainingIgnoreCase(schoolName);
        for (SchoolEntity schoolEntity : school) {
            log.info(schoolEntity.getName());
        }

        return school;
    }
}
