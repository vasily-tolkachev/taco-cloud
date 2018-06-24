package sia.tacocloud;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class Order {

    private Long id;
    private Date placedAt;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Street is required")
    private String street;

    @NotEmpty(message = "City is required")
    private String city;

    @NotEmpty(message = "State is required")
    private String state;

    @NotEmpty(message = "Zip code is required")
    private String zip;

    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([/])([1-9][0-9])$",
            message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;
}
