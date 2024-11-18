package org.example.memoaserver.domain.school.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.school.dto.request.SchoolRequest;
import org.example.memoaserver.domain.school.dto.response.SchoolDataResponse;
import org.example.memoaserver.domain.school.entity.DepartmentEntity;
import org.example.memoaserver.domain.school.entity.SchoolEntity;
import org.example.memoaserver.domain.school.repository.SchoolRepository;
import org.example.memoaserver.domain.school.exception.SchoolAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;

    public void addSchool(SchoolRequest schoolRequest) {
        if (schoolRepository.existsByName(schoolRequest.getName())) {
            throw new SchoolAlreadyExistsException();
        }

        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setName(schoolRequest.getName());

        List<DepartmentEntity> departmentEntities = schoolRequest.getDepartments().stream()
                .map(departmentDTO -> {
                    DepartmentEntity departmentEntity = new DepartmentEntity();
                    departmentEntity.setName(departmentDTO.getName());
                    departmentEntity.setGrade(departmentDTO.getGrade());
                    departmentEntity.setSubjects(departmentDTO.getSubjects());
                    departmentEntity.setSchoolEntity(schoolEntity);
                    return departmentEntity;
                })
                .toList();

        schoolEntity.setDepartments(departmentEntities);

        schoolRepository.save(schoolEntity);
    }

    @Transactional(readOnly = true)
    public List<SchoolDataResponse> searchSchool(String schoolName) {

        return schoolRepository.findByNameContainingIgnoreCase(schoolName).stream().map(SchoolDataResponse::fromSchoolEntity).toList();
    }
}
