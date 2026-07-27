package pe.com.apolo.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import pe.com.apolo.domain.model.fine.valueobjects.FineId;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

import java.util.UUID;

@Component
public class FineRequestMapper {

    public UserId toUserId(UUID id) {
        return new UserId(id);
    }

    public FineId toFineId(UUID id) {
        return new FineId(id);
    }

}