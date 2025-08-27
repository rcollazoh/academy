package cu.academy.trace;

import cu.academy.trace.dto.TraceDto;
import cu.academy.trace.dto.TraceCreationDto;
import cu.academy.shared.dto.PagingResponseList;
import cu.academy.shared.utils.EndpointResult;
import jakarta.servlet.http.HttpServletRequest;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/academy/traces")
public class TraceController {

    private final TraceService service;

    public TraceController(TraceService service) {
        this.service = service;
    }

    @GetMapping(params = {"startDate", "endDate"})
    public ResponseEntity<EndpointResult> getLogs(
            @Or({
                    @Spec(path = "context", params = "searchParam", spec = Like.class),
                    @Spec(path = "details", params = "searchParam", spec = Like.class)
            }) Specification<TraceEntity> searchParamSpec,
            @Or({
                    @Spec(path = "fullName", params = "personaParam", spec = Like.class),
                    @Spec(path = "mobilePhone", params = "personaParam", spec = Like.class)
            }) Specification<TraceEntity> personaParamSpec,
            @And({
                    @Spec(path = "dateCreated", params = "startDate", spec = GreaterThanOrEqual.class, config = "yyyy/MM/dd HH:mm:ss"),
                    @Spec(path = "dateCreated", params = "endDate", spec = LessThanOrEqual.class, config = "yyyy/MM/dd HH:mm:ss")
            }) Specification<TraceEntity> andDatesSpec,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            Sort sort
    ) {
        try {
            Specification<TraceEntity> spec = Specification.allOf(andDatesSpec,searchParamSpec,personaParamSpec);

            List<TraceDto> traces = service.getAll(spec, pageNumber, pageSize, sort);

            PagingResponseList<TraceDto> response = new PagingResponseList<>(
                    traces.size(), pageNumber,traces.size(), pageNumber * pageSize, null, traces
            );

            return ResponseEntity.ok(new EndpointResult(response, null));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new EndpointResult(null, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EndpointResult> getById(@PathVariable Long id) {
        try {
            TraceDto dto = service.getById(id);
            return ResponseEntity.ok(new EndpointResult(dto, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new EndpointResult(null, e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<EndpointResult> create(@RequestBody TraceCreationDto dto, HttpServletRequest request) {
        try {
            TraceDto created = service.create(dto, request);
            return ResponseEntity.ok(new EndpointResult(created, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new EndpointResult(null, e.getMessage()));
        }
    }
}