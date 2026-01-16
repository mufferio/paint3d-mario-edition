package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class MenuView {

    private MenuBar menuBar;
    private MenuController m_controller;

    /**
     * Constructs a new MenuView and initializes its menu bar.
     * @param m_controller the controller that will handle all the menu actions
     */
    public MenuView(MenuController m_controller) {
        this.m_controller = m_controller;
        this.createMenuBar();
    }

    /**
     * @return the menu bar used in the UI
     */
    public MenuBar getMenuBar() {
        return this.menuBar;
    }

    /**
     * creates the menu bar and all associated menus and menu items.
     * @return the fully constructed MenuBar
     */
    private MenuBar createMenuBar() {

        this.menuBar = new MenuBar();
        Menu menu;
        MenuItem menuItem;

        // A menu for File
        menu = new Menu("File");

        menuItem = new MenuItem("New");
        menuItem.setOnAction(this.m_controller);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Open");
        menuItem.setOnAction(this.m_controller);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Save");
        menuItem.setOnAction(this.m_controller);
        menu.getItems().add(menuItem);

        menu.getItems().add(new SeparatorMenuItem());

        menuItem = new MenuItem("Exit");
        menuItem.setOnAction(this.m_controller);
        menu.getItems().add(menuItem);

        this.menuBar.getMenus().add(menu);

        // Another menu for Edit
        menu = new Menu("Edit");

        menuItem = new MenuItem("Cut");
        menuItem.setOnAction(this.m_controller);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Copy");
        menuItem.setOnAction(this.m_controller);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Paste");
        menuItem.setOnAction(this.m_controller);
        menu.getItems().add(menuItem);

        menu.getItems().add(new SeparatorMenuItem());
        menuItem = new MenuItem("Undo");
        menuItem.setOnAction(this.m_controller);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Redo");
        menuItem.setOnAction(this.m_controller);
        menu.getItems().add(menuItem);

        menuItem = new MenuItem("Delete");
        menuItem.setOnAction(this.m_controller);
        menu.getItems().add(menuItem);

        this.menuBar.getMenus().add(menu);

        return menuBar;
    }
}
