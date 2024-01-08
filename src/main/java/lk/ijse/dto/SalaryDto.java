package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalaryDto {
    private String salary_id;
    private String emp_id;
    private Date payment_date;
    private String amount;
    private String month;
}
