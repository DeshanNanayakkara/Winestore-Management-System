package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemTM {
    private String item_id;
    private double unit_price;
    private double stock_price;
    private String description;
    private int qtyOnHand;
}