package lk.ijse.dto.tm;


import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TableTM {
    private String id;
    private String availability;
    private JFXButton btn;
}
