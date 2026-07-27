package pe.com.apolo.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import pe.com.apolo.domain.model.fine.valueobjects.Money;
import pe.com.apolo.infrastructure.persistence.mapper.config.CentralMapperConfig;

import java.math.BigDecimal;
import java.util.Currency;

@Mapper(config = CentralMapperConfig.class)
public interface MoneyMapper {

    default Money toMoney(BigDecimal amount, String currency) {
        if (amount == null || currency == null) {
            return null;
        }

        return new Money(
                amount,
                Currency.getInstance(currency)
        );
    }

    default BigDecimal toAmount(Money money) {
        return money == null
                ? null
                : money.getAmount();
    }

    default String toCurrency(Money money) {
        return money == null
                ? null
                : money.getCurrency().getCurrencyCode();
    }
}
