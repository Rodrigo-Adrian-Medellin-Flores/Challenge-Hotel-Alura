package com.alura.hotel.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Objects;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.alura.hotel.view.ReservasView;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class JCalendarCellEditor extends DefaultCellEditor {

    public JCalendarCellEditor(JCheckBox checkbox, Integer columnNumber, LocalDate minDate, LocalDate maxDate){
        super(checkbox);
        this.columnNumber = Objects.requireNonNull(columnNumber, "Debe especificar un número de columna. 'columnNumber' no puede ser null");
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

    public JCalendarCellEditor(JCheckBox checkbox, Integer columnNumber, Integer columnNumber2, LocalDate minDate, LocalDate maxDate){
        super(checkbox);
        this.columnNumber = Objects.requireNonNull(columnNumber, "Debe especificar un número de columna. 'columnNumber' no puede ser null");
        this.columnNumber2 = Objects.requireNonNull(columnNumber2, "Debe especificar un número de columna. 'columnNumber2' no puede ser null");
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

    public JCalendarCellEditor(JCheckBox checkbox, Integer columnNumber, Integer columnNumber2, Integer columnValue, BigDecimal multiply, LocalDate minDate, LocalDate maxDate){
        super(checkbox);
        this.columnNumber = Objects.requireNonNull(columnNumber, "Debe especificar un número de columna. 'columnNumber' no puede ser null");
        this.columnNumber2 = Objects.requireNonNull(columnNumber2, "Debe especificar un número de columna. 'columnNumber2' no puede ser null");
        this.columnValue = Objects.requireNonNull(columnValue, "Debe especificar un número de columna. 'columnValue' no puede ser null");
        this.multiply = Objects.requireNonNull(multiply, "Debe especificar un multiplo. 'multiply' no puede ser null");
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

    private JDateChooser date;
    private Integer columnNumber;
    private Integer columnNumber2;
    private Integer columnValue;
    private LocalDate minDate;
    private LocalDate minDate2;
    private LocalDate maxDate;
    private BigDecimal multiply;
    
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){


        LocalDate fecha = null;

        if(Integer.valueOf(column) == columnNumber){
            if(table.getValueAt(row, columnNumber)!=null){
                String fechaString = table.getValueAt(row, columnNumber).toString();
                if(!fechaString.trim().isEmpty()){
                    fecha = stringToLocalDate(fechaString);
                }
            }
            
        }
        
        if(Integer.valueOf(column) == columnNumber2){
            if(table.getValueAt(row, columnNumber2) != null){
                String fechaString1 = table.getValueAt(row, columnNumber2).toString();
                if(!fechaString1.trim().isEmpty()){
                    fecha = stringToLocalDate(fechaString1);
                }
            }
            
            if(table.getValueAt(row, columnNumber) != null){
                String fechaString2 = table.getValueAt(row, columnNumber).toString();
                if(!fechaString2.trim().isEmpty()){
                    minDate2 = stringToLocalDate(fechaString2).plusDays(1);
                }
            }
        }

        date = new JDateChooser();
		date.getCalendarButton().setIcon(new ImageIcon(getClass().getResource("/com/alura/hotel/imagenes/icon-reservas.png")));
		date.getCalendarButton().setBackground(SystemColor.textHighlight);
		date.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 11));
		date.setBackground(Color.WHITE);
		date.setFont(new Font("Roboto", Font.PLAIN, 14));
        date.setDateFormatString("yyyy-MM-dd");
		date.setBorder(new LineBorder(new Color(255, 255, 255), 0));
        JTextFieldDateEditor dateEditor = (JTextFieldDateEditor) date.getDateEditor();
		dateEditor.setEditable(false);

        if(fecha != null){
            date.setDate(Date.valueOf(fecha));
        }

        if(Integer.valueOf(column) == columnNumber && this.minDate != null){
            date.setMinSelectableDate(Date.valueOf(minDate));
        }

        if(Integer.valueOf(column)== columnNumber2 && this.minDate2 != null){
            date.setMinSelectableDate(Date.valueOf(minDate2));
        }

        if(this.maxDate != null){
            date.setMaxSelectableDate(Date.valueOf(maxDate));
        }

        if(this.columnNumber != null && this.columnNumber2 != null && this.columnValue != null && this.multiply != null){

            date.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
    
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if(e.getNewValue()!= null && e.getNewValue().getClass() == java.util.Date.class){
                        LocalDate fechaEntrada = null;
                        LocalDate fechaSalida = null;
                        if(column == columnNumber){
                            fechaEntrada = date.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            fechaSalida = (LocalDate) table.getValueAt(row, columnNumber2);
                        } else if(column == columnNumber2){
                            fechaEntrada = (LocalDate) table.getValueAt(row, columnNumber);
                            fechaSalida = date.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        }
                        Period periodo = Period.between(fechaEntrada, fechaSalida);
                        Integer dias = periodo.getDays();
                        BigDecimal precioUnitario = multiply;
                        BigDecimal precioTotal = precioUnitario.multiply(new BigDecimal(String.valueOf(dias)));
                        if((precioTotal.compareTo(new BigDecimal("0")))<0){
                            table.setValueAt(null, row, columnValue);
                            table.setValueAt(null, row, columnNumber2);
                        } else {
                            table.setValueAt(precioTotal, row, columnValue);
                        }
                    }
                }
                
            });
        }
                

        return date;

        
    }

    public Object getCellEditorValue() {
        String fechaString =((JTextField)date.getDateEditor().getUiComponent()).getText();
        if(fechaString.trim().isEmpty()){
            return fechaString;
        } else {
            return stringToLocalDate(fechaString);
        }
    }

    private LocalDate stringToLocalDate(String string){
        String[] fechaString = string.split("-");
        Integer year = Integer.valueOf(fechaString[0]);
        Integer month = Integer.valueOf(fechaString[1]);
        Integer day = Integer.valueOf(fechaString[2]);
        return LocalDate.of(year, month, day);
    }

    
}
