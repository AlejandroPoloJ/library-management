package pe.com.apolo.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.com.apolo.domain.model.loan.Loan;
import pe.com.apolo.infrastructure.persistence.mapper.BookCopyIdMapper;
import pe.com.apolo.infrastructure.persistence.mapper.LoanIdMapper;
import pe.com.apolo.infrastructure.persistence.mapper.UserIdMapper;
import pe.com.apolo.infrastructure.web.dto.response.LoanResponse;

@Mapper(
        componentModel = "spring",
        uses = {
                LoanIdMapper.class,
                UserIdMapper.class,
                BookCopyIdMapper.class
        }
)
public interface LoanResponseMapper {

    @Mapping(target = "loanId", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "bookCopyId", source = "bookCopy.id")
    @Mapping(target = "status", expression = "java(loan.getStatus().name())")
    LoanResponse toResponse(Loan loan);

}
