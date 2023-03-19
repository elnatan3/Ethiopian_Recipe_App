import javax.swing.*;
import java.awt.*;

public class ButtonsPanel extends JPanel {
    private JButton editButton;
    private JButton deleteButton;

    public ButtonsPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        editButton = new JButton("Edit");
        add(editButton);

        deleteButton = new JButton("Delete");
        add(deleteButton);
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
}
