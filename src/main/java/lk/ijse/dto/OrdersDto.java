package lk.ijse.dto;

import lk.ijse.dto.tm.CartTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrdersDto {
    private String order_id;
    private double price;
    private LocalDate date;
    private String cust_id;
    private List<CartTm> tmList=new ArrayList<>();
}
