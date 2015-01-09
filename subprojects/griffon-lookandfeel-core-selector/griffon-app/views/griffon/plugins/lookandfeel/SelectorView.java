/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package griffon.plugins.lookandfeel;

import griffon.core.CallableWithArgs;
import griffon.core.artifact.GriffonView;
import griffon.metadata.ArtifactProviderFor;
import griffon.swing.support.SwingAction;
import net.miginfocom.swing.MigLayout;
import org.codehaus.griffon.runtime.swing.artifact.AbstractSwingGriffonView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author Andres Almiray
 */
@ArtifactProviderFor(GriffonView.class)
public class SelectorView extends AbstractSwingGriffonView {
    private SelectorModel model;
    private SelectorController controller;

    private JPanel content;
    private JDesktopPane desktop;

    public void setModel(SelectorModel model) {
        this.model = model;
    }

    public void setController(SelectorController controller) {
        this.controller = controller;
    }

    @Nonnull
    public JDesktopPane getDesktop() {
        return desktop;
    }

    public boolean show() {
        int option = JOptionPane.CANCEL_OPTION;
        try {
            Window window = null;
            for (Window w : Window.getWindows()) {
                if (w.isFocused()) {
                    window = w;
                    break;
                }
            }

            option = JOptionPane.showConfirmDialog(
                window,
                content,
                "Look & Feel Selection",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        } catch (Exception x) {
            // ignore ?
        }

        return option == JOptionPane.OK_OPTION;
    }

    @Override
    public void initUI() {
        content = new JPanel(new BorderLayout(15, 15));
        content.add(controlPanel(), BorderLayout.NORTH);
        content.add(desktopPanel(), BorderLayout.CENTER);
    }

    private JComponent controlPanel() {
        JPanel panel = new JPanel(new MigLayout("fill"));

        panel.add(new JLabel("Look & Feel"), "left");
        final JComboBox comboHandler = new JComboBox(model.getHandlers());
        panel.add(comboHandler, "grow, span 2, right, wrap");
        comboHandler.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                model.setSelectedHandler((LookAndFeelHandler) comboHandler.getSelectedItem());
            }
        });

        panel.add(new JLabel("Theme"), "left");
        final JComboBox comboTheme = new JComboBox(model.getThemes());
        panel.add(comboTheme, "grow, span 2, right, wrap");
        comboTheme.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                model.setSelectedTheme((LookAndFeelDescriptor) comboTheme.getSelectedItem());
            }
        });

        // spacer
        panel.add(new JLabel(""), "left");
        panel.add(
            new JButton((Action) actionFor(controller, "preview").getToolkitAction()),
            "left");
        panel.add(
            new JButton((Action) actionFor(controller, "reset").getToolkitAction()),
            "right");

        return panel;
    }

    private JComponent desktopPanel() {
        desktop = new JDesktopPane();
        desktop.setPreferredSize(new Dimension(450, 380));

        JInternalFrame internalFrame = new JInternalFrame("Sample");
        internalFrame.setLayout(new BorderLayout());
        internalFrame.setIconifiable(true);
        internalFrame.setMaximizable(true);
        internalFrame.setResizable(true);
        internalFrame.setSize(new Dimension(400, 340));
        internalFrame.setPreferredSize(new Dimension(400, 340));
        internalFrame.setLocation(20, 20);
        internalFrame.setVisible(true);

        internalFrame.add(topPanel(), BorderLayout.NORTH);
        internalFrame.add(centerPanel(), BorderLayout.CENTER);

        desktop.add(internalFrame);

        return desktop;
    }

    private JComponent topPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.add(new JMenuItem(NEW_FILE_ACTION));
        menu.add(new JMenuItem(OPEN_ACTION));
        menu.add(new JMenuItem(SAVE_ACTION));
        menuBar.add(menu);
        menu = new JMenu("Edit");
        menu.add(new JMenuItem(CUT_ACTION));
        menu.add(new JMenuItem(COPY_ACTION));
        menu.add(new JMenuItem(PASTE_ACTION));
        menuBar.add(menu);

        JToolBar toolBar = new JToolBar();
        toolBar.add(toolbarButton(NEW_FILE_ACTION));
        toolBar.add(toolbarButton(OPEN_ACTION));
        toolBar.add(toolbarButton(SAVE_ACTION));
        toolBar.addSeparator();
        toolBar.add(toolbarButton(CUT_ACTION));
        toolBar.add(toolbarButton(COPY_ACTION));
        toolBar.add(toolbarButton(PASTE_ACTION));

        panel.add(menuBar);
        panel.add(toolBar);

        return panel;
    }

    private JButton toolbarButton(Action action) {
        JButton button = new JButton(action);
        button.setText("");
        return button;
    }

    private JComponent centerPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel sampleTab = new JPanel(new BorderLayout());
        JPanel rendererTab = new JPanel(new BorderLayout());

        JPanel north = new JPanel(new GridLayout(6, 2));
        JCheckBox checkBox = new JCheckBox("Enabled selected");
        checkBox.setSelected(true);
        north.add(checkBox);
        JRadioButton radioButton = new JRadioButton("Enabled selected");
        radioButton.setSelected(true);
        north.add(radioButton);

        checkBox = new JCheckBox("Disabled selected");
        checkBox.setEnabled(false);
        checkBox.setSelected(true);
        north.add(checkBox);
        radioButton = new JRadioButton("Disabled selected");
        radioButton.setEnabled(false);
        radioButton.setSelected(true);
        north.add(radioButton);

        north.add(new JCheckBox("Enabled unselected"));
        north.add(new JRadioButton("Enabled unselected"));

        JComboBox comboBox = new JComboBox(new String[]{"Item 1", "Item 2", "Item3"});
        north.add(comboBox);
        JTextField textField = new JTextField("Enabled");
        north.add(textField);
        comboBox = new JComboBox(new String[]{"Item 1", "Item 2", "Item3"});
        comboBox.setEnabled(false);
        north.add(comboBox);
        textField = new JTextField("Disabled");
        textField.setEnabled(false);
        north.add(textField);

        north.add(new JLabel("Label"));
        textField = new JTextField("Uneditable");
        textField.setEditable(false);
        north.add(textField);

        JPanel south = new JPanel(new FlowLayout());
        south.add(new JButton("prev"));
        JButton button = new JButton("cancel");
        button.setEnabled(false);
        south.add(button);
        button = new JButton("ok");
        button.setSelected(true);
        south.add(button);

        sampleTab.add(new JScrollPane(north), BorderLayout.NORTH);
        sampleTab.add(south, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.4d);
        splitPane.setLeftComponent(new JScrollPane(new JTree()));
        splitPane.setRightComponent(new JScrollPane(new JList(items())));
        rendererTab.add(splitPane, BorderLayout.CENTER);

        tabbedPane.addTab("Sample", sampleTab);
        tabbedPane.addTab("Renderers", rendererTab);

        return tabbedPane;
    }

    private String[] items() {
        String[] items = new String[20];
        for (int i = 0; i < 20; i++) {
            items[i] = "Item " + (i + 1);
        }
        return items;
    }

    private static final CallableWithArgs<Void> NOOP_CALLABLE = new CallableWithArgs<Void>() {
        @Nullable
        @Override
        public Void call(Object... args) {
            return null;
        }
    };

    private static final Action NEW_FILE_ACTION = SwingAction.action("New File")
        .withRunnable(NOOP_CALLABLE)
        .withMnemonic("N")
        .withAccelerator("meta N")
        .withShortDescription("New File")
        .withSmallIcon(new ImageIcon(SelectorView.class.getResource("icons/page.png")))
        .build();
    private static final Action OPEN_ACTION = SwingAction.action("Open")
        .withMnemonic("O")
        .withRunnable(NOOP_CALLABLE)
        .withAccelerator("meta O")
        .withSmallIcon(new ImageIcon(SelectorView.class.getResource("icons/folder_page.png")))
        .withShortDescription("Open File")
        .build();

    private static final Action SAVE_ACTION = SwingAction.action("Save")
        .withMnemonic("S")
        .withRunnable(NOOP_CALLABLE)
        .withAccelerator("meta S")
        .withSmallIcon(new ImageIcon(SelectorView.class.getResource("icons/disk.png")))
        .withShortDescription("Save File")
        .withEnabled(false)
        .build();

    private static final Action CUT_ACTION = SwingAction.action("Cut")
        .withMnemonic("T")
        .withRunnable(NOOP_CALLABLE)
        .withAccelerator("meta X")
        .withSmallIcon(new ImageIcon(SelectorView.class.getResource("icons/cut.png")))
        .withShortDescription("Cut")
        .build();

    private static final Action COPY_ACTION = SwingAction.action("Copy")
        .withMnemonic("C")
        .withRunnable(NOOP_CALLABLE)
        .withAccelerator("meta C")
        .withSmallIcon(new ImageIcon(SelectorView.class.getResource("icons/page_copy.png")))
        .withShortDescription("Copy")
        .build();

    private static final Action PASTE_ACTION = SwingAction.action("Paste")
        .withMnemonic("P")
        .withRunnable(NOOP_CALLABLE)
        .withAccelerator("meta V")
        .withSmallIcon(new ImageIcon(SelectorView.class.getResource("icons/page_paste.png")))
        .withShortDescription("Paste")
        .build();


}