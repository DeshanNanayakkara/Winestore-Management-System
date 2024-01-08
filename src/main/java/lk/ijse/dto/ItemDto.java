package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDto  {
    private String item_id;
    private double unit_price;
    private double stock_price;
    private String description;
    private int qtyOnHand;

    public ItemDto(String item_id, String unit_price, String stock_price, String description, Integer qtyOnhand) {
        this.item_id=item_id;
        this.unit_price= Double.parseDouble(unit_price);
        this.stock_price= Double.parseDouble(stock_price);
        this.description=description;
        this.qtyOnHand= qtyOnhand;
    }
}
