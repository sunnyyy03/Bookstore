/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528project;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import java.io.PrintWriter;
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 *
 * @author Sanchit Das, Thiveyan Nadeswaran
 */

public class JavaFXApplication extends Application{
    
    TableView<Book> table1;
    ObservableList<Book> bookData;    
    TableView<Customer> table2;
    ObservableList<Customer> customerData;

    @Override
    public void start(Stage primaryStage) {
        try{
            
            primaryStage.setTitle("Login Screen");
        
            // Create GridPane layout
            GridPane grid = new GridPane();
            grid.setAlignment(javafx.geometry.Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            
            // Create Text object for title
            Text scenetitle = new Text("Welcome to the BookStore App");
            scenetitle.setFont(Font.font("Elephant", FontWeight.NORMAL, 16));
            grid.add(scenetitle, 0, 0, 2, 1);

            // Create Label and TextField for username
            Label userName = new Label("Username:");
            grid.add(userName, 0, 1);
            TextField userTextField = new TextField();
            grid.add(userTextField, 1, 1);

            // Create Label and PasswordField for password
            Label pw = new Label("Password:");
            grid.add(pw, 0, 2);
            PasswordField pwBox = new PasswordField();
            grid.add(pwBox, 1, 2);

            // Create Button and HBox layout for login button
            Button btn = new Button("Login");
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(javafx.geometry.Pos.BOTTOM_LEFT);
            hbBtn.getChildren().add(btn);
            grid.add(hbBtn, 1, 3);

            // Create Text object for error messages
            final Text actiontarget = new Text();
            grid.add(actiontarget, 1, 6);
        
            // Set up the data for the books table
            bookData = FXCollections.observableArrayList();
            List<Book> books = new ArrayList<>();
            try {
                books = readBooksFromFile("books.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            bookData.addAll(books);
            
            // Set up the data for the customers table
            customerData = FXCollections.observableArrayList();
            List<Customer> customers = new ArrayList<>();
            try {
                customers = readCustomersFromFile("customers.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            customerData.addAll(customers);

            // Add event handler for login button
            btn.setOnAction(e -> {
                
                String username = userTextField.getText();
                String password = pwBox.getText();
                boolean isCustomer = false;
                
                // Read Customer info from file
                try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] tokens = line.split(" ");
                        if (username.equals(tokens[0]) && password.equals(tokens[1])) {
                            isCustomer = true;
                            break;
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
                // Check if owner is loging in    
                if (username.equals("admin") && password.equals("admin")) {           
                
                    // Create new owner screen
                    Stage ownerStage = new Stage();
                    ownerStage.setTitle("Owner Screen");
                    GridPane ownerGrid = new GridPane();
                    ownerGrid.setAlignment(javafx.geometry.Pos.CENTER);
                    ownerGrid.setHgap(10);
                    ownerGrid.setVgap(10);
                    ownerGrid.setPadding(new Insets(25, 25, 25, 25));
                
                    // Create logout button
                    Button logoutBtn = new Button("Logout");
                    HBox hbLogoutBtn = new HBox(10);
                    hbLogoutBtn.setAlignment(javafx.geometry.Pos.CENTER);
                    hbLogoutBtn.getChildren().add(logoutBtn);
                    ownerGrid.add(hbLogoutBtn, 1, 7);
                
                    // Create books button
                    Button booksBtn = new Button("Books");
                    HBox hbBooksBtn = new HBox(10);
                    hbBooksBtn.setAlignment(javafx.geometry.Pos.CENTER);
                    hbBooksBtn.getChildren().add(booksBtn);
                    ownerGrid.add(hbBooksBtn, 1, 1);
                
                    // Create customers button
                    Button customersBtn = new Button("Customers");
                    HBox hbCustomersBtn = new HBox(10);
                    hbCustomersBtn.setAlignment(javafx.geometry.Pos.CENTER);
                    hbCustomersBtn.getChildren().add(customersBtn);
                    ownerGrid.add(hbCustomersBtn, 1, 4); 
                
                    // Add event handler for logout button
                    logoutBtn.setOnAction(e2 -> {
                        ownerStage.close();
                        primaryStage.show();
                        userTextField.clear();
                        pwBox.clear();
                    });
                
                    // Add event handler for books button
                    booksBtn.setOnAction(e2 -> {
                        Stage booksStage = new Stage();
                        booksStage.setTitle("Books Table");
                    
                    // Create table view for books
                    table1 = new TableView<>();
                    table1.setEditable(true);
                    
                    // Create columns for book name and book price
                    TableColumn<Book, String> bookNameColumn = new TableColumn<>("Book Name");
                    bookNameColumn.setMinWidth(200);
                    bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                    TableColumn<Book, Double> bookPriceColumn = new TableColumn<>("Book Price");
                    bookPriceColumn.setMinWidth(100);
                    bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                
                    // Add data to the table
                    table1.setItems(bookData);
                    table1.getColumns().addAll(bookNameColumn, bookPriceColumn);
                
                    // Create layout and add table view
                    VBox vbox = new VBox();
                    vbox.setSpacing(10);
                    vbox.setPadding(new Insets(10, 10, 10, 10));
                    vbox.getChildren().addAll(table1);
                    
                    
                    // Create labels and text fields for adding books                    
                    Label nameLabel = new Label("Name:");
                    TextField bookNameField = new TextField();
                    Label priceLabel = new Label ("Price:");
                    TextField bookPriceField = new TextField();
                    
                    // Add labels and text fields to grid pane
                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(10);
                    gridPane.setVgap(10);
                    gridPane.setPadding(new Insets(10, 10, 10, 10));
                    gridPane.add(nameLabel, 0, 0);
                    gridPane.add(bookNameField, 1, 0);
                    gridPane.add(priceLabel, 0, 1);
                    gridPane.add(bookPriceField, 1, 1);
                    vbox.getChildren().addAll(gridPane);
                    
                    // Add everything to scene
                    Scene booksScene = new Scene(vbox);
                    booksStage.setScene(booksScene);
                    ownerStage.close();
                    booksStage.show();
                    
                    // Create buttons for adding/deleting books and going back
                    Button addBookBtn = new Button("Add Book");
                    Button deleteBookBtn = new Button("Delete Book");
                    Button backBtn = new Button("Back");
                    HBox hbox = new HBox();
                    hbox.setSpacing(10);
                    hbox.setPadding(new Insets(10, 10, 10, 10));
                    hbox.getChildren().addAll(addBookBtn, deleteBookBtn, backBtn);
                    vbox.getChildren().addAll(hbox);
                    
                    // Add event handlers for adding books
                    addBookBtn.setOnAction(e3 -> {
                        String name = bookNameField.getText();
                        double price = Double.parseDouble(bookPriceField.getText());
                        Book newBook = new Book(name, price);
                        bookData.add(newBook);
                        table1.setItems(bookData);
                        writeBooksToFile(bookData, "books.txt");
                        bookNameField.clear();
                        bookPriceField.clear();
                        });

                    // Add event handlers for deleting books
                        deleteBookBtn.setOnAction(e3 -> {
                            Book selectedBook = table1.getSelectionModel().getSelectedItem();
                            bookData.remove(selectedBook);
                            table1.setItems(bookData);
                            writeBooksToFile(bookData,"books.txt");
                        });
                        
                    // Add event handlers for going back
                    backBtn.setOnAction(e3 -> {
                        booksStage.close();
                        ownerStage.show();
                        });
                });
                
                    // Add event handler for customers button
                    customersBtn.setOnAction(e2 -> {
                        Stage customersStage = new Stage();
                        customersStage.setTitle("Customers Table");
                   
                    // Create table view for customers
                    table2 = new TableView<>();
                    table2.setEditable(true);
                   
                    // Create columns for username, password and points
                    TableColumn<Customer, String> usernameColumn = new TableColumn<>("Username");
                    usernameColumn.setMinWidth(200);
                    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
                    TableColumn<Customer, String> passwordColumn = new TableColumn<>("Password");
                    passwordColumn.setMinWidth(200);
                    passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
                    TableColumn<Customer, String> pointsColumn = new TableColumn<>("Points");
                    pointsColumn.setMinWidth(100);
                    pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
                
                    // Add data to the table
                    table2.setItems(customerData);
                    table2.getColumns().addAll(usernameColumn, passwordColumn, pointsColumn);
                
                    // Create layout and add table view
                    VBox vbox = new VBox();
                    vbox.setSpacing(10);
                    vbox.setPadding(new Insets(10, 10, 10, 10));
                    vbox.getChildren().addAll(table2);                                        
                    
                    // Create labels and text fields for adding customers                    
                    Label usernameLabel = new Label("Username:");
                    TextField usernameField = new TextField();
                    Label passwordLabel = new Label ("Password:");
                    TextField passwordField = new TextField();
                    
                    // Add labels and text fields to grid pane
                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(10);
                    gridPane.setVgap(10);
                    gridPane.setPadding(new Insets(10, 10, 10, 10));
                    gridPane.add(usernameLabel, 0, 0);
                    gridPane.add(usernameField, 1, 0);
                    gridPane.add(passwordLabel, 0, 1);
                    gridPane.add(passwordField, 1, 1);
                    vbox.getChildren().addAll(gridPane);
                    
                    // Add everything to scene
                    Scene customersScene = new Scene(vbox);
                    customersStage.setScene(customersScene);
                    ownerStage.close();
                    customersStage.show();
                    
                    // Create buttons for adding/deleting customers and going back
                    Button addCustomerBtn = new Button("Add Customer");
                    Button deleteCustomerBtn = new Button("Delete Customer");
                    Button backBtn = new Button("Back");
                    HBox hbox = new HBox();
                    hbox.setSpacing(10);
                    hbox.setPadding(new Insets(10, 10, 10, 10));
                    hbox.getChildren().addAll(addCustomerBtn, deleteCustomerBtn, backBtn);
                    vbox.getChildren().addAll(hbox);
                   
                    // Add event handlers for adding customers
                    addCustomerBtn.setOnAction(e4 -> {
                    String user = usernameField.getText();
                    String pswd  = passwordField.getText();
                    int points = 0;
                    Customer newCustomer = new Customer(user, pswd, points);
                        customerData.add(newCustomer);
                        table2.setItems(customerData);
                        writeCustomersToFile(customerData, "customers.txt");
                        usernameField.clear();
                        passwordField.clear();
                        });
 
                    // Add event handlers for deleting customers
                        deleteCustomerBtn.setOnAction(e4 -> {
                            Customer selectedCustomer = table2.getSelectionModel().getSelectedItem();
                            customerData.remove(selectedCustomer);
                            table2.setItems(customerData);
                            writeCustomersToFile(customerData,"customers.txt");
                        });
                        
                    // Add event handlers for going back
                    backBtn.setOnAction(e4 -> {
                        customersStage.close();
                        ownerStage.show();
                        }); 
                });
                   
                    Scene ownerScene = new Scene(ownerGrid, 400, 300);
                    ownerStage.setScene(ownerScene);
                    primaryStage.close();
                    ownerStage.show();
                }
                
                else if (isCustomer) {
                
                // Create new customer screen
                Stage customerStage = new Stage();
                customerStage.setTitle("Customer Screen");
                VBox vbox = new VBox();
                vbox.setPadding(new Insets(10));
                vbox.setSpacing(10);
                Customer.setStatus(Customer.points);
                Text cusScenetitle = new Text("Welcome "+Customer.getUsername()+". You have "+Customer.getPoints()+" points. Your status is "+Customer.getStatus()+".");
                cusScenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
                vbox.getChildren().add(cusScenetitle);
                
                // Create table for books
                table1 = new TableView<>();
                table1.setEditable(true);
                    
                // Create columns for book name and book price
                TableColumn<Book, String> bookNameColumn = new TableColumn<>("Book Name");
                bookNameColumn.setMinWidth(170);
                bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                TableColumn<Book, Double> bookPriceColumn = new TableColumn<>("Book Price");
                bookPriceColumn.setMinWidth(150);
                bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                TableColumn<Book, CheckBox> selectCol = new TableColumn<>("Select");
                selectCol.setMinWidth(50);
                selectCol.setCellValueFactory(new PropertyValueFactory<>("select"));
                vbox.getChildren().add(table1);
                
                // Add data to the table
                table1.setItems(bookData);
                table1.getColumns().addAll(bookNameColumn, bookPriceColumn, selectCol);
                
                // Add buttons
                Button b1 = new Button("Buy");       
                Button b2 = new Button("Redeen Points and Buy");
                Button b3 = new Button("Logout");
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                hbox.setPadding(new Insets(10, 10, 10, 10));
                hbox.getChildren().addAll(b1, b2, b3);
                vbox.getChildren().addAll(hbox);
                
                // Add event handler for logout button
                b3.setOnAction(e2 -> {
                customerStage.close();
                primaryStage.show();
                userTextField.clear();
                pwBox.clear();
                });
               
                // Add event handler for buy button
                b1.setOnAction(e2 -> {
                    double totalBookCost = 0;
                    for (Book m: table1.getItems()) {
                        if(m.select.isSelected()==true){
                            totalBookCost += m.getPrice();
                        }
                    }
                    Customer.points += (int)totalBookCost*10;
                    Customer.setStatus(Customer.points);
                
                    Stage buyStage = new Stage();
                    buyStage.setTitle("Customer-Start-Screen:Buy");
                    GridPane buyGrid = new GridPane();
                    buyGrid.setAlignment(javafx.geometry.Pos.CENTER);
                    buyGrid.setHgap(10);
                    buyGrid.setVgap(10);
                    buyGrid.setPadding(new Insets(25,25,25,25));
                
                    Text totalCost = new Text("Total Cost: "+totalBookCost);
                    totalCost.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                    Customer.setStatus(Customer.points);
                    Text pointsAndStatus = new Text("Points: "+Customer.getPoints()+", Status: "+Customer.getStatus());
                    totalCost.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                
                    Button logOut = new Button("Logout");
                
                    buyGrid.add(totalCost, 0,0);
                    buyGrid.add(pointsAndStatus, 0, 12);
                    buyGrid.add(logOut,0,25);
                    Scene scene = new Scene(buyGrid, 400, 400);
                
                    buyStage.setScene(scene);
                
                    customerStage.close();
                    buyStage.show();
                    
                    logOut.setOnAction(e3 -> {
                        
                        primaryStage.show();
                        buyStage.close();
                        userTextField.clear();
                        pwBox.clear();
                    });
                });
                
                //Add event handler for redeem and buy
                b2.setOnAction(e2 -> {
            
                double totalBookCost = 0;
                for (Book m: table1.getItems()) {
                    if(m.select.isSelected()==true){
                        totalBookCost += m.getPrice();
                    }
                }
                int redeemedPrice = (int)Customer.getPoints()/100;
                if(redeemedPrice <= totalBookCost){
                    Customer.points -= (redeemedPrice*100);
                    Customer.setStatus(Customer.points);
                    totalBookCost-=redeemedPrice;   
                }
                else{
                    redeemedPrice -=(int)totalBookCost;
                    totalBookCost -= redeemedPrice;
                    Customer.points-=(redeemedPrice*100);
                    Customer.setStatus(Customer.points);
                }                
                
                Stage buyStage = new Stage();
                buyStage.setTitle("Customer-Start-Screen:RedeemPointsAndBuy");
                GridPane buyGrid = new GridPane();
                buyGrid.setAlignment(javafx.geometry.Pos.CENTER);
                buyGrid.setHgap(10);
                buyGrid.setVgap(10);
                buyGrid.setPadding(new Insets(25,25,25,25));
                
                Text totalCost = new Text("Total Cost: "+ totalBookCost);
                totalCost.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                
                Text pointsAndStatus = new Text("Points: "+Customer.getPoints()+", Status: "+Customer.getStatus());
                totalCost.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                
                Button logOut = new Button("Logout");
                
                buyGrid.add(totalCost, 0,0);
                buyGrid.add(pointsAndStatus, 0, 12);
                buyGrid.add(logOut,0,25);
                Scene scene = new Scene(buyGrid, 400, 400);
                
                buyStage.setScene(scene);
                
                customerStage.close();
                buyStage.show();
                
                logOut.setOnAction(e3 -> {
                        
                        primaryStage.show();
                        buyStage.close();
                        userTextField.clear();
                        pwBox.clear();
                    });
        });
                
                Scene customerScene = new Scene(vbox, 400, 400);
                customerStage.setScene(customerScene);
                primaryStage.close();
                customerStage.show();
                
                
                }   
                    
                
                else {
                    actiontarget.setFill(Color.RED);
                    actiontarget.setText("Incorrect username or password");
                }
            });

                // Create Scene and show Stage
                Scene scene = new Scene(grid, 400, 300);
                primaryStage.setScene(scene);
                primaryStage.show();
        }
            catch(Exception e){
            e.printStackTrace();
            }
    }
            // Read books from the file
            private List<Book> readBooksFromFile(String fileName) throws IOException {
            List<Book> books = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String name = parts[0].trim();
                double price = Double.parseDouble(parts[1].trim());
                books.add(new Book(name, price));
            }
            reader.close();
            return books;
        }
            // Write books to file
            private void writeBooksToFile(List<Book> books, String fileName) {
                try (PrintWriter writer = new PrintWriter(fileName)) {
                    for (Book book : books) {
                        writer.println(book.getName() + " " + book.getPrice());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            // Read customers from the file
            private List<Customer> readCustomersFromFile(String fileName) throws IOException {
            List<Customer> customers = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    int points = Integer.parseInt(parts[2].trim());
                    customers.add(new Customer(username, password, points));
                }
            }
            reader.close();
            return customers;
        }
        
            // Write customers to file
            private void writeCustomersToFile(List<Customer> customers, String fileName) {
                try (PrintWriter writer = new PrintWriter(fileName)) {
                    for (Customer customer : customers) {
                        writer.println(customer.getUsername() + " " + customer.getPassword() + " " + 0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            public static void main(String[] args) {
            launch(args);
        }
    }
