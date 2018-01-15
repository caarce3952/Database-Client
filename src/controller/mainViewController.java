/*
     * ============================================================================================================
     * @author Caleb
     * Description: Database Management Controller.
     * --Tab inserts/deletes/searches are managed by this controller.
     * Text Field values are used to construct queries and load Table Views.
     * All Table Views are populated at start with entire database.
     * ============================================================================================================
     */
package controller;

//https://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData.html
//See above for metadata functions
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.ResultSetMetaData;

import application.MainFXApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import model.Employee;
import model.Department;
import model.Project;
import model.Works;
import model.Custom;

public class mainViewController {
	Stage stage;
	Scene scene;
	Parent root;
	private String DATABASE_NAME = "company";
	Connection conn = MainFXApp.con;
	private MainFXApp main;

	public void setMain(MainFXApp mainIn) {
		main = mainIn;
	}

	// Meta data Components
	@FXML
	private Button btnMeta = new Button();
	@FXML
	private TextField tfMeta = new TextField();
	
	// Employee Buttons
	@FXML
	private Button btnEInsert = new Button();
	@FXML
	private Button btnEDelete = new Button();
	@FXML
	private Button btnESearch = new Button();

	// Department Buttons
	@FXML
	private Button btnDInsert = new Button();
	@FXML
	private Button btnDDelete = new Button();
	@FXML
	private Button btnDSearch = new Button();

	// Project Buttons
	@FXML
	private Button btnPInsert = new Button();
	@FXML
	private Button btnPDelete = new Button();
	@FXML
	private Button btnPSearch = new Button();

	// Works_On Buttons
	@FXML
	private Button btnWInsert = new Button();
	@FXML
	private Button btnWDelete = new Button();
	@FXML
	private Button btnWSearch = new Button();

	// Custom Button
	@FXML
	private Button btnCSubmit = new Button();

	// Employee TextFields
	@FXML
	private TextField tfEfname = new TextField();
	@FXML
	private TextField tfElname = new TextField();
	@FXML
	private TextField tfESsn = new TextField();
	@FXML
	private TextField tfESuperSsn = new TextField();
	@FXML
	private TextField tfEDepartmentNum = new TextField();

	// Department TextFields
	@FXML
	private TextField tfDDepartmentName = new TextField();
	@FXML
	private TextField tfDDepartmentNum = new TextField();
	@FXML
	private TextField tfDManagerSsn = new TextField();
	@FXML
	private TextField tfDDLocation = new TextField();

	// Project TextFields
	@FXML
	private TextField tfPProjectName = new TextField();
	@FXML
	private TextField tfPProjectNum = new TextField();
	@FXML
	private TextField tfPPLocation = new TextField();
	@FXML
	private TextField tfPDepartmentNum = new TextField();

	// Works_On
	@FXML
	private TextField tfWESsn = new TextField();
	@FXML
	private TextField tfWProjectNum = new TextField();
	@FXML
	private TextField tfWHours = new TextField();

	// Custom TextArea
	@FXML
	private TextArea taCQuery = new TextArea();
	@FXML
	private TextArea taMeta = new TextArea();
	// Employee TableView
	@FXML
	private TableView<Employee> TvE;
	@FXML
	private TableColumn<Employee, String> tcEFirstName;
	@FXML
	private TableColumn<Employee, String> tcELastName;
	@FXML
	private TableColumn<Employee, String> tcESsn;
	@FXML
	private TableColumn<Employee, String> tcESuperSsn;
	@FXML
	private TableColumn<Employee, String> tcEDno;

	// Department TableView
	@FXML
	private TableView<Department> TvD;
	@FXML
	private TableColumn<Department, String> tcDDname;
	@FXML
	private TableColumn<Department, String> tcDDnumber;
	@FXML
	private TableColumn<Department, String> tcDMgrSsn;
	@FXML
	private TableColumn<Department, String> tcDDLocation;

	// Project TableView
	@FXML
	private TableView<Project> TvP;
	@FXML
	private TableColumn<Project, String> tcPPname;
	@FXML
	private TableColumn<Project, String> tcPPnumber;
	@FXML
	private TableColumn<Project, String> tcPPLocation;
	@FXML
	private TableColumn<Project, String> tcPDepartmentNum;

	// Works_On TableView
	@FXML
	private TableView<Works> TvW;
	@FXML
	private TableColumn<Works, String> tcWEssn;
	@FXML
	private TableColumn<Works, String> tcWPno;
	@FXML
	private TableColumn<Works, String> tcWHours;

	// Custom TableView
	@FXML
	private TableView<Custom> TvC;
	@FXML
	private TableColumn<Custom, String> tc1;
	@FXML
	private TableColumn<Custom, String> tc2;
	@FXML
	private TableColumn<Custom, String> tc3;
	@FXML
	private TableColumn<Custom, String> tc4;
	@FXML
	private TableColumn<Custom, String> tc5;

	// Lists to hold table items
	static List<Employee> listE = new ArrayList<Employee>();
	static List<Department> listD = new ArrayList<Department>();
	static List<Project> listP = new ArrayList<Project>();
	static List<Works> listW = new ArrayList<Works>();
	static List<Custom> listC = new ArrayList<Custom>();

	// Observable Lists
	static ObservableList<Employee> OListE = FXCollections.observableList(listE);
	static ObservableList<Department> OListD = FXCollections.observableList(listD);
	static ObservableList<Project> OListP = FXCollections.observableList(listP);
	static ObservableList<Works> OListW = FXCollections.observableList(listW);
	static ObservableList<Custom> OListC = FXCollections.observableList(listC);

	// Temporary String variables
	private static String fname = "", lname = "", Ssn = "", Super_ssn = "", Dno = "", query = "";
	private static String Dname = "", Dnumber = "", Mgr_Ssn = "", DLocation = "";
	private static String Pname = "", Pnumber = "", PLocation = "", Dnum = "";
	private static String Essn = "", Pno = "", Hours = "";
	private static String col1 = "", col2 = "", col3 = "", col4 = "", col5 = "";

	@FXML
	private Label elbE = new Label();
	@FXML
	private Label elbD = new Label();
	@FXML
	private Label elbP = new Label();
	@FXML
	private Label elbW = new Label();
	@FXML
	private Label elbC = new Label();

	public void initialize() throws SQLException {
		System.out.println(conn.getMetaData());
		/*
		 * Set Error labels to blank. This is not done in xml because they
		 * become difficult to edit in SceneBuilder
		 */
		elbE.setText("");
		elbD.setText("");
		elbP.setText("");
		elbW.setText("");
		elbC.setText("");
		// Load Employees
		try {
			query = ("select * from employee;");
			loadE();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Load Departments
		try {
			query = ("select * from department;");
			loadD();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Load Projects
		try {
			query = ("select * from project;");
			loadP();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Load Works_On
		try {
			query = ("select * from works_on;");
			loadW();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}// End Initialize

	// Submit Custom Query
	public void submitC(ActionEvent event) throws Exception {
		query = taCQuery.getText();
		listC = new ArrayList<Custom>();
		OListC = FXCollections.observableList(listC);
		Custom c1;
		try {
			Statement statement = conn.createStatement();
			ResultSet RS;
			ResultSetMetaData MD;
			if (query.toLowerCase().contains("insert") || query.toLowerCase().contains("delete"))
				statement.execute(query);
			else {
				RS = statement.executeQuery(query);
				MD = (ResultSetMetaData) RS.getMetaData();
				int count = MD.getColumnCount();
				StringProperty temp;
				boolean one = false, two = false, three = false, four = false, five = false;
				while (RS.next()) {
					one = false;
					two = false;
					three = false;
					four = false;
					five = false;
					c1 = new Custom("", "", "", "", "");

					if (count >= 1) {
						temp = new SimpleStringProperty(RS.getString(1));
						c1.setOne(temp);
						one = true;
					}
					if (count >= 2) {
						temp = new SimpleStringProperty(RS.getString(2));
						c1.setTwo(temp);
						two = true;
					}
					if (count >= 3) {
						temp = new SimpleStringProperty(RS.getString(3));
						c1.setThree(temp);
						three = true;
					}
					if (count >= 4) {
						temp = new SimpleStringProperty(RS.getString(4));
						c1.setFour(temp);
						four = true;
					}

					if (count >= 5) {
						temp = new SimpleStringProperty(RS.getString(5));
						c1.setFive(temp);
						five = true;
					}

					OListC.add(c1);
				}
				if (one)
					tc1.setCellValueFactory(cellData -> cellData.getValue().getOne());
				if (two)
					tc2.setCellValueFactory(cellData -> cellData.getValue().getTwo());
				if (three)
					tc3.setCellValueFactory(cellData -> cellData.getValue().getThree());
				if (four)
					tc4.setCellValueFactory(cellData -> cellData.getValue().getFour());
				if (five)
					tc5.setCellValueFactory(cellData -> cellData.getValue().getFive());

				TvC.setItems(OListC);

				System.out.println(query);
				loadE();
				loadD();
				loadP();
				loadW();
			}
		} catch (Exception e) {

			elbC.setText(e.getMessage());

		}

	}

	// Click on Tables
	public void mouseClicked(MouseEvent event) {
		System.out.println("HIT");
	}

	// Search Buttons
	public void searchE(ActionEvent event) throws Exception {
		query = "SELECT * FROM employee " + queryBuilderE();
		System.out.println(query);
		loadE();
	}

	public void searchD(ActionEvent event) throws Exception {
		query = "SELECT * FROM department " + queryBuilderD();
		System.out.println(query);
		loadD();
	}

	public void searchP(ActionEvent event) throws Exception {
		query = "SELECT * FROM project " + queryBuilderP();
		System.out.println(query);
		loadP();
	}

	public void searchW(ActionEvent event) throws Exception {
		query = "SELECT * FROM works_on " + queryBuilderW();
		System.out.println(query);
		loadW();
	}

	// Insert Buttons
	public void insertE(ActionEvent event) throws Exception {
		queryBuilderE(); // load static temporary strings
		col1 = "";
		col2 = "";
		col3 = "";
		col4 = "";
		col5 = "";
		query = "";
		if (fname.length() != 0 || lname.length() != 0 || Ssn.length() != 0 || Super_ssn.length() != 0
				|| Dno.length() != 0) {

			boolean first = false;

			if (fname.length() != 0) {
				col1 = "fname";
				query += "'" + fname + "'";
				first = true;
			}
			if (lname.length() != 0) {
				if (first) {
					query += ", ";
					col2 = ", lname";
				} else
					col2 = "lname";
				query += "'" + lname + "'";
				first = true;

			}
			if (Ssn.length() != 0) {
				if (first) {
					query += ", ";
					col3 = ", Ssn";
				} else
					col3 = "Ssn";
				query += "'" + Ssn + "'";
				first = true;
			}
			if (Super_ssn.length() != 0) {
				if (first) {
					query += ", ";
					col4 = ", Super_ssn";
				} else
					col4 = "Super_ssn";
				query += "'" + Super_ssn + "'";
				first = true;
			}
			if (Dno.length() != 0) {
				if (first) {
					query += ", ";
					col5 = ", Dno";
				} else
					col5 = "Dno";
				query += "'" + Dno + "'";
			}

			elbE.setText("");

		} // end condition check

		query = "INSERT INTO employee(" + col1 + " " + col2 + " " + col3 + " " + col4 + " " + col5 + ") VALUES("
				+ query;

		query += ");";

		if (query.equals("INSERT INTO employee(    ) VALUES();"))
			elbE.setText("Invalid Insert");
		else {
			try {
				Statement statement = conn.createStatement();
				statement.execute(query);
				// preparedStatement .executeUpdate();
				System.out.println(query);
				query = "SELECT * FROM employee;";
				loadE();
			} catch (Exception e) {
				elbE.setText(e.getMessage());
			}
		}
	}// End insertE

	public void insertD(ActionEvent event) throws Exception {
		queryBuilderD(); // load static temporary strings
		col1 = "";
		col2 = "";
		col3 = "";
		col4 = "";
		col5 = "";
		query = "";
		if (Dname.length() != 0 || Dnumber.length() != 0 || Mgr_Ssn.length() != 0 || DLocation.length() != 0) {

			boolean first = false;

			if (Dname.length() != 0) {
				query += "'" + Dname + "'";
				col1 = "Dname";
				first = true;
			}
			if (Dnumber.length() != 0) {
				if (first) {
					query += ", ";
					col2 = ", Dnumber";
				} else
					col2 = "Dnumber";
				query += "'" + Dnumber + "'";
				first = true;

			}
			if (Mgr_Ssn.length() != 0) {
				if (first) {
					query += ", ";
					col3 = ", Mgr_Ssn";
				} else
					col3 = "Mgr_Ssn";
				query += "'" + Mgr_Ssn + "'";
				first = true;
			}
			if (DLocation.length() != 0) {
				if (first) {
					query += ", ";
					col4 = ", DLocation";
				} else
					col4 = "DLocation";
				query += "'" + DLocation + "'";
				first = true;
			}

			elbD.setText("");

		} // end condition check

		query = "INSERT INTO department(" + col1 + " " + col2 + " " + col3 + " " + col4 + ") VALUES(" + query;

		query += ");";

		if (query.equals("INSERT INTO department VALUES();"))
			elbD.setText("Invalid Insert");
		else {
			// execute
			try {
				System.out.println(query);
				Statement statement = conn.createStatement();
				statement.execute(query);
				// reload table
				query = "SELECT * FROM department;";
				loadD();
			} catch (Exception e) {
				elbD.setText(e.getMessage());

			}

		}
	}// end insertD

	public void insertP(ActionEvent event) throws Exception {
		queryBuilderP(); // load static temporary strings
		col1 = "";
		col2 = "";
		col3 = "";
		col4 = "";
		col5 = "";
		query = "";
		if (Pname.length() != 0 || Pnumber.length() != 0 || PLocation.length() != 0 || Dnum.length() != 0) {

			boolean first = false;

			if (Pname.length() != 0) {
				query += "'" + Pname + "'";
				first = true;
				col1 = "Pname";
			}
			if (Pnumber.length() != 0) {
				if (first) {
					query += ", ";
					col2 = ", Pnumber";
				} else
					col2 = "Pnumber";
				query += "'" + Pnumber + "'";

				first = true;

			}
			if (PLocation.length() != 0) {
				if (first) {
					query += ", ";
					col3 = ", PLocation";
				} else
					col3 = "PLocation";
				query += "'" + PLocation + "'";
				first = true;

			}
			if (Dnum.length() != 0) {
				if (first) {
					query += ", ";
					col4 = ", Dnum";
				} else
					col4 = "Dnum";
				query += "'" + Dnum + "'";
				first = true;
			}

			elbP.setText("");
		} // end condition check

		query = "INSERT INTO project(" + col1 + " " + col2 + " " + col3 + " " + col4 + ") VALUES(" + query;
		query += ");";

		if (query.equals("INSERT INTO project VALUES();"))
			elbP.setText("Invalid Insert");
		else {
			try {
				// execute
				System.out.println(query);
				Statement statement = conn.createStatement();
				statement.execute(query);

				query = "SELECT * FROM project;";
				loadP();
			} catch (Exception e) {
				elbP.setText(e.getMessage());
			}
		}
	}// end insertP

	public void insertW(ActionEvent event) throws Exception {
		queryBuilderW(); // load static temporary strings
		col1 = "";
		col2 = "";
		col3 = "";
		col4 = "";
		col5 = "";
		query = "";
		if (Essn.length() != 0 || Pno.length() != 0 || Hours.length() != 0) {

			boolean first = false;

			if (Essn.length() != 0) {
				query += "'" + Essn + "'";
				first = true;
				col1 = "Essn";
			}
			if (Pno.length() != 0) {
				if (first) {
					query += ", ";
					col2 = ", Pno";
				} else
					col2 = "Pno";
				query += "'" + Pno + "'";
				first = true;

			}
			if (Hours.length() != 0) {
				if (first) {
					query += ", ";
					col3 = ", Hours";
				} else
					col3 = "Hours";
				query += "'" + Hours + "'";
				first = true;
			}
			elbW.setText("");
		} // end condition check

		query = "INSERT INTO works_on(" + col1 + " " + col2 + " " + col3 + ") VALUES(" + query;
		query += ");";

		if (query.equals("INSERT INTO works_on VALUES();"))
			elbW.setText("Invalid Insert");
		else {
			// execute
			try {
				System.out.println(query);
				Statement statement = conn.createStatement();
				statement.execute(query);

				query = "SELECT * FROM works_on;";
				loadW();
			} catch (Exception e) {
				elbW.setText(e.getMessage());
			}
		}
	}

	// Delete Buttons
	public void deleteE(ActionEvent event) throws Exception {

		query = "DELETE FROM employee " + queryBuilderE();
		if (query.length() < 40) // No delete criteria cancels the delete
			elbE.setText("Invalid Delete Query");
		else {
			elbE.setText("");
			try {
				Statement statement = conn.createStatement();
				statement.execute(query);

				// reload the table
				System.out.println(query);
				query = "SELECT * FROM employee;";

				loadE();
			} catch (Exception e) {
				elbE.setText(e.getMessage());
			}
		}
	}

	public void deleteD(ActionEvent event) throws Exception {
		query = "DELETE FROM department " + queryBuilderD();
		if (query.length() < 42)
			elbD.setText("Invalid Delete Query");
		else {
			elbD.setText("");
			try {
				Statement statement = conn.createStatement();
				String sql = "select REF_COL_NAME from information_schema.INNODB_SYS_FOREIGN_COLS where ID LIKE '%company%';";
				ResultSet result = statement.executeQuery(sql);
				boolean foreignKeyMatch = false;
				while (result.next())
				{
					String column = result.getString("REF_COL_NAME");
					if (query.contains(column))
						foreignKeyMatch = true;
				}
				if (foreignKeyMatch == true)
				{
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setContentText("Deleting this tuple will result in a cascade. Do you want to continue?");
					Optional<ButtonType> buttonResult = alert.showAndWait();
					if (buttonResult.get() == ButtonType.OK){
						statement.execute(query);
						System.out.println(query);
					} 
					
				}
					
				
				// reload the table
				
				query = "SELECT * FROM department;";
				loadD();
				
			} catch (Exception e) {
				elbD.setText(e.getMessage());
			}
		}
	}

	public void deleteP(ActionEvent event) throws Exception {
		query = "DELETE FROM project " + queryBuilderP();
		if (query.length() < 39)
			elbP.setText("Invalid Delete Query");
		else {
			elbP.setText("");
			try {
				Statement statement = conn.createStatement();
				String sql = "select REF_COL_NAME from information_schema.INNODB_SYS_FOREIGN_COLS where ID LIKE '%company%';";
				ResultSet result = statement.executeQuery(sql);
				boolean foreignKeyMatch = false;
				while (result.next())
				{
					String column = result.getString("REF_COL_NAME");
					if (query.contains(column))
						foreignKeyMatch = true;
				}
				if (foreignKeyMatch == true)
				{
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setContentText("Deleting this tuple will result in a cascade. Do you want to continue?");
					Optional<ButtonType> buttonResult = alert.showAndWait();
					if (buttonResult.get() == ButtonType.OK){
						statement.execute(query);
						System.out.println(query);
					} 
					
				}

				// reload table
				query = "SELECT * FROM project;";
				loadP();
				
			} catch (Exception e) {
				elbP.setText(e.getMessage());
			}
		}
	}

	public void deleteW(ActionEvent event) throws Exception {
		query = "DELETE FROM works_on " + queryBuilderW();
		if (query.length() < 40)
			elbW.setText("Invalid Delete Query");
		else {
			elbW.setText("");
			try {
				Statement statement = conn.createStatement();
				statement.execute(query);

				// reload the table
				System.out.println(query);
				query = "SELECT * FROM works_on;";
				loadW();
			} catch (Exception e) {
				elbW.setText(e.getMessage());
			}
		}
	}

	// Query Builders
	public String queryBuilderE() {

		String q;
		fname = tfEfname.getText();
		lname = tfElname.getText();
		Ssn = tfESsn.getText();
		Super_ssn = tfESuperSsn.getText();
		Dno = tfEDepartmentNum.getText();

		q = "";

		// If there is a search parameter, add a where clause
		if (fname.length() != 0 || lname.length() != 0 || Ssn.length() != 0 || Super_ssn.length() != 0
				|| Dno.length() != 0) {

			q += "WHERE ";
			boolean first = false;

			if (fname.length() != 0) {
				q += "fname = '" + fname + "' ";
				first = true;

			}
			if (lname.length() != 0) {
				if (first)
					q += "AND ";
				q += "lname = '" + lname + "' ";
				first = true;

			}
			if (Ssn.length() != 0) {
				if (first)
					q += "AND ";
				q += "Ssn = '" + Ssn + "' ";
				first = true;
			}
			if (Super_ssn.length() != 0) {
				if (first)
					q += "AND ";
				q += "Super_ssn = '" + Super_ssn + "' ";
				first = true;
			}
			if (Dno.length() != 0) {
				if (first)
					q += "AND ";
				q += "Dno = '" + Dno + "' ";
			}

		} // end condition check

		q += ";";

		return q;
	}// end queryBuilderE

	public String queryBuilderD() {
		String q;
		Dname = tfDDepartmentName.getText();
		Dnumber = tfDDepartmentNum.getText();
		Mgr_Ssn = tfDManagerSsn.getText();
		DLocation = tfDDLocation.getText();
		q = "";

		// If there is a search parameter, add a where clause
		if (Dname.length() != 0 || Dnumber.length() != 0 || Mgr_Ssn.length() != 0 || DLocation.length() != 0) {

			q += "WHERE ";
			boolean first = false;

			if (Dname.length() != 0) {
				q += "Dname = '" + Dname + "' ";
				first = true;

			}
			if (Dnumber.length() != 0) {
				if (first)
					q += "AND ";
				q += "Dnumber = '" + Dnumber + "' ";
				first = true;

			}
			if (Mgr_Ssn.length() != 0) {
				if (first)
					q += "AND ";
				q += "Mgr_Ssn = '" + Mgr_Ssn + "' ";
				first = true;
			}
			if (DLocation.length() != 0) {
				if (first)
					q += "AND ";
				q += "DLocation = '" + DLocation + "' ";
				first = true;
			}

		} // end condition check

		q += ";";
		return q;
	}

	public String queryBuilderP() {
		String q;
		Pname = tfPProjectName.getText();
		Pnumber = tfPProjectNum.getText();
		PLocation = tfPPLocation.getText();
		Dnum = tfPDepartmentNum.getText();

		q = "";

		if (Pname.length() != 0 || Pnumber.length() != 0 || PLocation.length() != 0 || Dnum.length() != 0) {

			q += "WHERE ";
			boolean first = false;

			if (Pname.length() != 0) {
				q += "Pname = '" + Pname + "' ";
				first = true;

			}
			if (Pnumber.length() != 0) {
				if (first)
					q += "AND ";
				q += "Pnumber = '" + Pnumber + "' ";
				first = true;

			}
			if (PLocation.length() != 0) {
				if (first)
					q += "AND ";
				q += "PLocation = '" + PLocation + "' ";
				first = true;
			}
			if (Dnum.length() != 0) {
				if (first)
					q += "AND ";
				q += "Dnum = '" + Dnum + "' ";
				first = true;
			}

		} // end condition check

		q += ";";

		return q;
	}// end queryBuilderP

	public String queryBuilderW() {
		String q = "";

		Essn = tfWESsn.getText();
		Pno = tfWProjectNum.getText();
		Hours = tfWHours.getText();

		if (Essn.length() != 0 || Pno.length() != 0 || Hours.length() != 0) {

			q += "WHERE ";
			boolean first = false;

			if (Essn.length() != 0) {
				q += "Essn = '" + Essn + "' ";
				first = true;

			}
			if (Pno.length() != 0) {
				if (first)
					q += "AND ";
				q += "Pno = '" + Pno + "' ";
				first = true;

			}
			if (Hours.length() != 0) {
				if (first)
					q += "AND ";
				q += "Hours = '" + Hours + "' ";
				first = true;
			}

		} // end condition check

		q += ";";

		return q;
	}

	// TableView Loaders
	public void loadE() {
		try {
			Statement statement = conn.createStatement();
			ResultSet RS = null;
			RS = statement.executeQuery(query);

			listE = new ArrayList<Employee>();
			OListE = FXCollections.observableList(listE);

			while (RS.next()) {

				fname = RS.getString("fname");
				lname = RS.getString("lname");
				Ssn = RS.getString("Ssn");
				Super_ssn = RS.getString("Super_ssn");
				Dno = RS.getString("Dno");

				Employee temp = new Employee(fname, lname, Ssn, Super_ssn, Dno);
				OListE.add(temp);
			}

			tcEFirstName.setCellValueFactory(cellData -> cellData.getValue().getfName());
			tcELastName.setCellValueFactory(cellData -> cellData.getValue().getlName());
			tcESsn.setCellValueFactory(cellData -> cellData.getValue().getSsn());
			tcESuperSsn.setCellValueFactory(cellData -> cellData.getValue().getSuper_ssn());
			tcEDno.setCellValueFactory(cellData -> cellData.getValue().getDno());

			TvE.setItems(OListE);
			fname = "";
			lname = "";
			Ssn = "";
			Super_ssn = "";
			Dno = "";
			query = "";

			elbE.setText("");
		} catch (Exception e) {
			elbE.setText(e.getMessage());
		}
	}// end loadE

	public void loadD() {
		try {

			Statement statement = conn.createStatement();
			ResultSet RS = null;

			listD = new ArrayList<Department>();
			OListD = FXCollections.observableList(listD);

			// this will execute the String 'query' exactly as if you were in
			// SQL console
			// and it returns a result set which contains everything we want,
			// but we need to decode it first
			RS = statement.executeQuery(query);
			// if the query goes through, RS will no longer be null
			while (RS.next()) {

				Dname = RS.getString("Dname");
				Dnumber = RS.getString("Dnumber");
				Mgr_Ssn = RS.getString("Mgr_Ssn");
				DLocation = RS.getString("DLocation");

				Department temp = new Department(Dname, Dnumber, Mgr_Ssn, DLocation);
				OListD.add(temp);
			}

			tcDDname.setCellValueFactory(cellData -> cellData.getValue().getDname());
			tcDDnumber.setCellValueFactory(cellData -> cellData.getValue().getDnumber());
			tcDMgrSsn.setCellValueFactory(cellData -> cellData.getValue().getMgr_ssn());
			tcDDLocation.setCellValueFactory(cellData -> cellData.getValue().getDLocation());

			TvD.setItems(OListD);
			Dname = "";
			Dnumber = "";
			Mgr_Ssn = "";
			DLocation = "";
			elbD.setText("");
		} catch (Exception e) {
			elbD.setText(e.getMessage());
		}
	}// end loadD

	public void loadP() {
		try {

			Statement statement = conn.createStatement();
			ResultSet RS = null;

			listP = new ArrayList<Project>();
			OListP = FXCollections.observableList(listP);

			// this will execute the String 'query' exactly as if you were in
			// SQL console
			// and it returns a result set which contains everything we want,
			// but we need to decode it first
			RS = statement.executeQuery(query);
			// if the query goes through, RS will no longer be null
			while (RS.next()) {

				Pname = RS.getString("Pname");
				Pnumber = RS.getString("Pnumber");
				PLocation = RS.getString("PLocation");
				Dnum = RS.getString("Dnum");

				Project temp = new Project(Pname, Pnumber, PLocation, Dnum);
				OListP.add(temp);
			}

			tcPPname.setCellValueFactory(cellData -> cellData.getValue().getPname());
			tcPPnumber.setCellValueFactory(cellData -> cellData.getValue().getPnumber());
			tcPPLocation.setCellValueFactory(cellData -> cellData.getValue().getPLocation());
			tcPDepartmentNum.setCellValueFactory(cellData -> cellData.getValue().getDnum());

			TvP.setItems(OListP);
			Pname = "";
			Pnumber = "";
			PLocation = "";
			Dnum = "";
			elbP.setText("");
		} catch (Exception e) {
			elbP.setText(e.getMessage());
		}
	}// end loadP

	public void loadW() {
		try {

			Statement statement = conn.createStatement();
			ResultSet RS = null;

			listW = new ArrayList<Works>();
			OListW = FXCollections.observableList(listW);

			// this will execute the String 'query' exactly as if you were in
			// SQL console
			// and it returns a result set which contains everything we want,
			// but we need to decode it first
			RS = statement.executeQuery(query);
			// if the query goes through, RS will no longer be null
			while (RS.next()) {

				Essn = RS.getString("Essn");
				Pno = RS.getString("Pno");
				Hours = RS.getString("Hours");

				Works temp = new Works(Essn, Pno, Hours);
				OListW.add(temp);
			}

			tcWEssn.setCellValueFactory(cellData -> cellData.getValue().getEssn());
			tcWPno.setCellValueFactory(cellData -> cellData.getValue().getPno());
			tcWHours.setCellValueFactory(cellData -> cellData.getValue().getHours());

			TvW.setItems(OListW);
			String Essn = "";
			Pno = "";
			Hours = "";
			elbW.setText("");
		} catch (Exception e) {
			elbW.setText(e.getMessage());
		}
	}// end loadW

	public void meta(ActionEvent event) throws Exception {
		try {
			
			query = "SELECT * FROM "+tfMeta.getText()+";";
			System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			//DatabaseMetaData dbmd = (DatabaseMetaData) conn.getMetaData();
			ResultSet RS = ps.executeQuery();
			
			RS.next();
			
			taMeta.setText(RS.getMetaData().toString());
			
			
			/*if (event.getSource() == btnCatalog) {
				System.out.println("Fetching Catalog");
				RS = dbmd.getCatalogs();
				while (RS.next()) {
					taMeta.appendText(RS.getString(1) + "\n");
				}
				RS = dbmd.getTables(null, null, "%", null);
				while (RS.next()) {
					taMeta.appendText(RS.getString(3) + "\n");
				}
			} else if (event.getSource() == btnPrimary) {
				System.out.println("Fetching Primary Keys");
				ResultSet rs = dbmd.getTables(null, null, "%", null);

				while (rs.next()) {
					taMeta.appendText(dbmd.getPrimaryKeys("company", null, rs.getString(3)) + "\n");
				}

			} else if (event.getSource() == btnAttributes) {

			} else if (event.getSource() == btnColumns) {

			}*/

			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}//end controller