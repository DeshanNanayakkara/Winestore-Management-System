package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {
    private String attendance_id;
    private String emp_id;
    private Date date;
    private String time_in;
    private String time_out;
}
