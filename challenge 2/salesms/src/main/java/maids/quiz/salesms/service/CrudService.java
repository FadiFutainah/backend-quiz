package maids.quiz.salesms.service;


import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.exception.CommonExceptions;
import maids.quiz.salesms.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class CrudService<Entity extends BaseEntity<Id>, Id> {
    protected final JpaRepository<Entity, Id> jpaRepository;

    public CrudService(JpaRepository<Entity, Id> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public Entity lookupResource(Id id) {
        Entity resource = jpaRepository.findById(id).orElse(null);
        if (resource == null) {
            throw new CommonExceptions.ResourceNotFoundException("Resource with id " + id + " does not exist");
        }
        return resource;
    }

    public ResponseEntity<ResponseDto<List<Entity>>> fetch() {
        return ResponseDto.response(jpaRepository.findAll());
    }

    public ResponseEntity<ResponseDto<Entity>> fetch(Id id) {
        return ResponseDto.response(lookupResource(id));
    }

    public ResponseEntity<ResponseDto<Entity>> add(Entity resource) {
        return ResponseDto.response(jpaRepository.save(resource));
    }

    public ResponseEntity<ResponseDto<String>> delete(Id id) {
        jpaRepository.delete(lookupResource(id));
        return ResponseDto.response("", HttpStatus.OK, "resource deleted successfully");
    }

    public ResponseEntity<ResponseDto<Entity>> update(Entity resource) {
        return ResponseDto.response(jpaRepository.save(lookupResource(resource.getId())));
    }
}
