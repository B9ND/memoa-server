package org.example.memoaserver.domain.school.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.school.dto.SchoolDTO;
import org.example.memoaserver.domain.school.dto.res.DepartmentResponse;
import org.example.memoaserver.domain.school.dto.req.SchoolRequest;
import org.example.memoaserver.domain.school.entity.DepartmentEntity;
import org.example.memoaserver.domain.school.entity.SchoolEntity;
import org.example.memoaserver.domain.school.repository.DepartmentRepository;
import org.example.memoaserver.domain.school.repository.SchoolRepository;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.example.memoaserver.global.exception.SchoolAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final UserAuthHolder userAuthHolder;


    public DepartmentResponse getMySchool() {
        DepartmentEntity department = userRepository.findByEmail(userAuthHolder.current().getEmail()).getDepartment();
        return DepartmentResponse.fromDepartmentEntity(department);
    }

    public void addSchool(SchoolRequest schoolRequest) {
        if (schoolRepository.existsByName(schoolRequest.getName())) {
            throw new SchoolAlreadyExistsException("School name '" + schoolRequest.getName() + "' is already exists");
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

    public List<SchoolEntity> searchSchool(String schoolName) {

        return schoolRepository.findByNameContainingIgnoreCase(schoolName);
    }
}
