package pe.com.apolo.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import pe.com.apolo.domain.model.book.valueobjects.BookId;
import pe.com.apolo.domain.model.loan.valueobjects.LoanId;
import pe.com.apolo.domain.model.user.valueobjects.UserId;

import java.util.UUID;

@Component
public class LoanRequestMapper {

    public UserId toUserId(UUID id) {
        return new UserId(id);
    }

    public BookId toBookId(UUID id) {
        return new BookId(id);
    }

    public LoanId toLoanId(UUID id) {
        return new LoanId(id);
    }

}
