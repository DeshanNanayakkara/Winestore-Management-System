package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BiteDto  {
    private String item_id;
    private double unit_price;
    private String description;

    public BiteDto(String item_id, String unit_price,String description) {
        this.item_id=item_id;
        this.unit_price= Double.parseDouble(unit_price);
        this.description=description;
        }
}
