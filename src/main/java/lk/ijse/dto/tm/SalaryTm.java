package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalaryTm {
    private String salaryId;
    private String employeeId;
    private String Amount;
    private String Month;
}
