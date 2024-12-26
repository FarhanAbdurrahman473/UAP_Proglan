import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
class ImageTableRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel();
        if (value != null && value instanceof String) {
            String imagePath = (String) value;
            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(image));
        }
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }
}
