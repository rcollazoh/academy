package cu.academy.trace;

import cu.academy.authentication.dto.UserResponseDto;
import cu.academy.person.PersonEntity;
import cu.academy.trace.dto.TraceCreationDto;
import cu.academy.trace.dto.TraceDto;
import cu.academy.trace.mapper.TraceMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class TraceService {

    private final TraceRepository traceRepository;
    private final TraceMapper mapper;

    public TraceService(TraceRepository traceRepository,
                        TraceMapper mapper) {
        this.traceRepository = traceRepository;
        this.mapper = mapper;
    }

    public List<TraceDto> getAll(Specification<TraceEntity> spec, int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return traceRepository.findAll(spec, pageable)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public TraceDto getById(Long id) {
        TraceEntity entity = traceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la traza con ID " + id));
        return mapper.toDto(entity);
    }

    @Transactional
    public TraceDto create(TraceCreationDto dto, HttpServletRequest request) {
        TraceEntity entity = mapper.toEntity(dto);
        entity.setDateCreated(Instant.now());
        entity.setIpAddress(resolveClientIp(request));
        return mapper.toDto(traceRepository.save(entity));
    }

    private String resolveClientIp(HttpServletRequest request) {
        if (request == null) return null;
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.contains(",") ? ip.split(",")[0].trim() : ip;
    }

    public void insertTraceLogin(UserResponseDto userDto, HttpServletRequest request) {
        try {
            TraceCreationDto traceCreationDto = mapper.fromUserResponseDto(userDto);
            create(traceCreationDto.withPersonId(userDto.getId())
                    .withActionId(3L)
                    .withFullName(userDto.getName().concat(" ").concat(userDto.getLastName()))
                    .withDetails("User login"), request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertTraceLogout(PersonEntity personLogout, HttpServletRequest request) {
        try {
            TraceCreationDto traceCreationDto = mapper.fromUserPerson(personLogout);
            create(traceCreationDto.withPersonId(personLogout.getId())
                    .withActionId(1L)
                    .withMobilePhone(personLogout.getPhone())
                    .withFullName(personLogout.getName().concat(" ").concat(personLogout.getLastName()))
                    .withDetails("User logout"), request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}