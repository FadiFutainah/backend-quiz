package maids.quiz.salesms.service;


import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.exception.CommonExceptions;
import maids.quiz.salesms.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class CrudService<Entity extends BaseEntity<Id>, Id> {
    protected final JpaRepository<Entity, Id> jpaRepository;
    private final Logger log = LoggerFactory.getLogger(CrudService.class);

    public CrudService(JpaRepository<Entity, Id> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public Entity lookupResource(Id id) {
        String message = "Resource with id " + id + " does not exist";
        return jpaRepository.findById(id)
                .orElseThrow(() -> new CommonExceptions.ResourceNotFoundException(message));
    }

    public ResponseEntity<ResponseDto<List<Entity>>> fetch() {
        return ResponseDto.response(jpaRepository.findAll());
    }

    public ResponseEntity<ResponseDto<Page<Entity>>> fetch(Pageable pageable) {
        Page<Entity> page = jpaRepository.findAll(pageable);
        return ResponseDto.response(page);
    }

    public ResponseEntity<ResponseDto<Entity>> fetch(Id id) {
        return ResponseDto.response(lookupResource(id));
    }

    public ResponseEntity<ResponseDto<Entity>> add(Entity resource) {
        log.info("created new resource " + resource.getClass());
        return ResponseDto.response(jpaRepository.save(resource));
    }

    public ResponseEntity<ResponseDto<String>> delete(Id id) {
        Entity resource = lookupResource(id);
        jpaRepository.delete(resource);
        log.info("created resource " + resource.getClass());
        return ResponseDto.response("", HttpStatus.OK, "resource deleted successfully");
    }

    public ResponseEntity<ResponseDto<Entity>> update(Id id, Entity resource) {
        Entity entity = lookupResource(id);
        log.info("created resource " + entity.getClass());
        return ResponseDto.response(jpaRepository.save(resource));
    }
}
