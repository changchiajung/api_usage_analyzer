package application;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ChildListPropertyDescriptor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.MethodRefParameter;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class SampleController implements Initializable{
	get_data data= new get_data ();    //get data class
	 
	public boolean Field=false;
	public boolean Method=false;
	public boolean Class_boolen=false;
	public boolean use=false;
	public String  search ="";    //�j�M�r��
	public static String Client_ID ="";//�O��������Client

	
	@FXML
	private Label label;
	@FXML
	private void choose_repo() throws ClassNotFoundException, IOException, SQLException {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open Resource File");
		File selectedDirectory = directoryChooser.showDialog(null);
		if(selectedDirectory == null){
		     //No Directory selected
		}else{
			 opendb();
			 ParseFilesInDir(selectedDirectory.getAbsolutePath());
			 System.out.println(selectedDirectory.getAbsolutePath());
		}
		
	}
	@FXML
	private void logout() {
		System.exit(0);
		System.out.println("logout");
	}
	 @FXML
	    private PieChart pieChart;
	 
	  @FXML
	    private ToggleButton Field_button;
	  @FXML
	    private ToggleButton Method_button;
	  @FXML
	    private ToggleButton Class_button;
	  

	 
	  @FXML
	  public void choose_Method() {
		  	if(!Method) {
		  		Method=true;
		  	}
		  	else {
		  		Method=false;
		  	}
	    }

	  @FXML
	    public void choose_Field() {
		  if(!Field) {
		  		Field=true;
		  	}
		  	else {
		  		Field=false;
		  	} 
	    }

	  @FXML
	    public void choose_Class() {
		  if(!Class_boolen) {
			  	Class_boolen=true;
		  	}
		  	else {
		  		Class_boolen=false;
		  	}
	    }
	  
	  @FXML
	    public void choose_type(){                //�T�w�n����ƪ�function
		  use=true;
		  set_Data();
	    }	  
	 
	 
	 @FXML
	    private BarChart<?, ?> barchart;

	 @FXML
	    private CategoryAxis x;
	 @FXML
	    private NumberAxis y;
	@FXML
	 public  void set_Data(){                         //����
		data.get(Class_boolen, Method, Field, search=searchfield.getText(),use);
		use=false;
		search=searchfield.getText();
		barchart.getData().clear();                   //�M�����
		pieChart.getData().clear();
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<Integer> times = new ArrayList<Integer>();
		name=data.result_api;
		times=data.result_count;
		 ObservableList<PieChart.Data> pieChartData     //pieChart
         = FXCollections.observableArrayList();
		XYChart.Series setl =new XYChart.Series<>();   //barChart
		for(int i=0;i<name.size();i++) {
			 setl.getData().add(new XYChart.Data(name.get(i),times.get(i)));
			 pieChartData.add(new PieChart.Data(name.get(i),times.get(i)));
		}		 
		 barchart.getData().addAll(setl);
		 
		
		 pieChart.setData(pieChartData);
	}
	//get information
	
	 @FXML
	    public TextField searchfield;
	 	
	 @FXML
	    void search(ActionEvent event) {
	    
	    }
	 
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		 XYChart.Series setl =new XYChart.Series<>();   //barChart
//		 setl.getData().add(new XYChart.Data("",30));
//		 setl.getData().add(new XYChart.Data("",3));
//		 setl.getData().add(new XYChart.Data("",3));
//		 setl.getData().add(new XYChart.Data("",12));
		 barchart.getData().addAll(setl);
		 
		 ObservableList<PieChart.Data> pieChartData     //pieChart
	                = FXCollections.observableArrayList(
//	                new PieChart.Data("", 13),
//	                new PieChart.Data("", 25),
//	                new PieChart.Data("", 10),
//	                new PieChart.Data("", 22),
	                new PieChart.Data("", 0));
		 
		 pieChart.setData(pieChartData);
	}

	static int java_module_id = 0 ; //������U��java_module
	public static void parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3); //AST�]�w
		parser.setResolveBindings(true);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setEnvironment( // apply classpath
				new String[] { "C:\\Users\\User\\workspace\\winter_project\\bin" }, //
				new String[] { "" }, new String[] { "UTF-8" }, true);
		parser.setUnitName("winter_project/src/data.java");
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		//AST�]�w//

		//�i����R
		cu.accept(new ASTVisitor() {
			Set names = new HashSet();
			//�M��method API
			public boolean visit(MethodInvocation node) {
				String package_name = "";//����API��package name 
				String class_name = "";//����API��Class name
				String method_name = "";//����API��Method name
				//System.out.println("METHOD Invocation: " + node.getName());
				method_name += node.getName();//����method name (no parameter)
				Expression expression = node.getExpression(); //���F����package �M class
				if (expression != null) { //
					//System.out.println("Expr: " + expression.toString());
					ITypeBinding typeBinding = expression.resolveTypeBinding();//���F����package �M class
					if (typeBinding != null) {
						class_name = typeBinding.getName();
						package_name = typeBinding.getPackage().getName();
						//System.out.println("Class: " + typeBinding.getName());
						//System.out.println("Package: " + typeBinding.getPackage().getName());
					}else {
						//typeBinding �� null ������
						return true;
					}
				}else {return true;}//expression �� null ������
				IMethodBinding method = node.resolveMethodBinding();//�����ꪺmethod name
				if(method !=null) {
					String method_string = method.toString();
					//System.out.println("parament type :"
							//+ method_string.substring(method_string.indexOf("(") + 1, method_string.indexOf(")")));
					method_name += method_string.substring(method_string.indexOf("("));//����parameter 
				}
				try {
					api_in_db(package_name,class_name,method_name,"null");//�s�idatabase
				} catch (Exception e) {
					System.out.println(e);
				}
				return true;
			}
			public boolean visit(VariableDeclarationFragment node) {
                try {
                	String package_name = "";//����API��Package name
    				String class_name = "";//����API��Class_name
                    ITypeBinding typeBinding = node.getInitializer().resolveTypeBinding();
                    //System.out.println(node.getInitializer().resolveTypeBinding().getPackage().isUnnamed());
                    //System.out.println("package : "+node.getInitializer().resolveTypeBinding().getPackage().getName());
                    //System.out.println("type name : "+ node.resolveBinding().getVariableDeclaration().getType().getName());
                    package_name = node.getInitializer().resolveTypeBinding().getPackage().getName();//����API package name
                    class_name = node.resolveBinding().getVariableDeclaration().getType().getName();//����API Class name
                    api_in_db(package_name,class_name,"null","null");
                }
                catch (Exception e){

                }
                return true;
            }
			public boolean visit(MethodDeclaration node) {
				//System.out.println("\nIn MethodDeclaration:"+node.getName().toString());
				System.out.println(node.getName().toString().equals("main"));
				
				Block block = node.getBody();
				
				//System.out.println(block.toString());
				
				if(node.getName().toString().equals("main")) {
					//�^�� main�̭������e
					block.accept(new ASTVisitor() {			
						public boolean visit(QualifiedName node) {
							
							IBinding binding = node.resolveBinding();
							ITypeBinding typeBinding = node.resolveTypeBinding();
							String package_name = "";//����API��package name
		    				String class_name = "";//����API��Class name
		    				String field_name = "";//����API��Field name
		    				if(binding != null) {
		    					field_name = binding.getName();//����API field name
		    				}else {return true;}
							String key = binding.getKey();
							System.out.println(key);
							int lastCharIndex = key.indexOf(")");
							key = key.substring(1, lastCharIndex - binding.getName().length() - 2);
							String newKey = null;
							if(key.indexOf("$") != -1)newKey = key.replace("$", ".");
							else if(key.indexOf("/") != -1)newKey = key.replace("/", ".");
							System.out.println(newKey);
							//���r��B�z
							
							int classSplitIndex = newKey.lastIndexOf(".");
							class_name = newKey.substring(classSplitIndex + 1);//���oClass name
							package_name = newKey.substring(0, classSplitIndex);//���oPackage name
		                    try {
								api_in_db(package_name,class_name,"null",field_name);//�s�iDB
							} catch (Exception e) {
								e.printStackTrace();
							}

							
							return true;
						}
					});
				}
				
				return true;
			}
			
		});

	}
	
	//�N��J����Ʀs�idb
	public static void api_in_db(String package_name, String class_name, String method_name, String field_name) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		Statement statement = connection.createStatement();
		//�Pdatabase�s�u
		try {
			String module_id = "";
			String execute_command = "SELECT API_ID FROM API WHERE Package = ? AND Class = ?";
			if(method_name.equals("null")) {
				if(field_name.equals("null")) {
				}else {
					execute_command+="AND Field = ?";
				}
			}else {
				execute_command+="AND Method = ?";
			}
			PreparedStatement pst =connection.prepareStatement(execute_command);
			
			pst.setString(1, package_name);
			pst.setString(2, class_name);
			
			if(method_name.equals("null")) {
				if(field_name.equals("null")) {
				}else {
					pst.setString(3, field_name);
				}
			}else {
				pst.setString(3, method_name);
			}
			//�ھڤ��Pparameter�����P����ܳ]�w//
			
			ResultSet rs_api = pst.executeQuery();
			if(rs_api.next()) {
				//�� API �s�b
				String search_api_id =  rs_api.getString("API_ID");
				String exe_api_usage = "SELECT API_Usage_ID, Usage_Count FROM [API USAGE] WHERE API_ID = "+ search_api_id +" AND Java_Module_ID = "+ java_module_id ;
				PreparedStatement pst_api_usage =connection.prepareStatement(exe_api_usage);
				ResultSet rs_api_usage = pst_api_usage.executeQuery();
				//�M���API �b API table���� API ID
				if(rs_api_usage.next()) {
					// API Usage �s�b
					int new_count = rs_api_usage.getInt("Usage_Count") + 1;
					String search_api_usage_id = rs_api_usage.getString("API_Usage_ID"); 
					Statement stmt = connection.createStatement();
				      String sql = "UPDATE [API USAGE] " +
				                   "SET Usage_count = "+ new_count +" WHERE API_Usage_ID = "+search_api_usage_id;
				      stmt.executeUpdate(sql);//��s�ϥΦ���
					//rs_api_usage.updateInt("Usage_Count", new_count);
				}else {
					//API Usage���s�b 
					String new_usage_ID ="";
					ResultSet rs_api_usage_id = statement.executeQuery("SELECT MAX(API_Usage_ID) as maxapi_usage FROM [API USAGE]");
					if (rs_api_usage_id.next()) {
						int w = rs_api_usage_id.getInt("maxapi_usage") + 1;
						new_usage_ID= String.valueOf(w);
					}
					//��ܥXAPI Usage�� ID �̤j(���F�s�W)
					String exe_create_api_usage = "INSERT INTO [API USAGE] values(?,?,?,?,?)";
					PreparedStatement pst_create_api_usage =connection.prepareStatement(exe_create_api_usage);
					pst_create_api_usage.setString(1, new_usage_ID);//�s��ID
					pst_create_api_usage.setInt(2, 1);//�ϥΦ���
					pst_create_api_usage.setString(3, Client_ID);//����Client
					pst_create_api_usage.setString(4,search_api_id);//������API ID
					pst_create_api_usage.setString(5,String.valueOf(java_module_id));//��U��java module id
					pst_create_api_usage.executeUpdate();
					//�[�J��API USAGE table
				}
			}else {
				//�� API �b�������Q�ϥιL(���Q�O���L)
				String new_ID ="";
				ResultSet rs_api_id = statement.executeQuery("SELECT MAX(API_ID) as maxapi FROM API");
				if (rs_api_id.next()) {
					int w = rs_api_id.getInt("maxapi") + 1;
					new_ID= String.valueOf(w);
				}
				//��ܥXAPI �� ID �̤j(���F�s�W)
				String execute_command_create = "INSERT INTO [API] (API_ID,Package,Class,Method,Field) values(?,?,?,?,?)";
				try(PreparedStatement pst_create_api =connection.prepareStatement(execute_command_create)){
					pst_create_api.setString(1, new_ID);
					pst_create_api.setString(2, package_name);
					pst_create_api.setString(3, class_name);
					
					if(method_name.equals("null")) {
						pst_create_api.setNull(4, java.sql.Types.VARCHAR); //�Y���ūh�]�w��NULL
						if(field_name.equals("null")) {
							pst_create_api.setNull(5, java.sql.Types.VARCHAR);//�Y���ūh�]�w��NULL
						}else {
							pst_create_api.setString(5, field_name);//�]�wvalue
						}
					}else {
						pst_create_api.setString(4, method_name);//�]�wvalue
						pst_create_api.setNull(5, java.sql.Types.VARCHAR);//�Y���ūh�]�w��NULL
					}
					
					pst_create_api.executeUpdate();//�i���s
				}
				//API�s�ا���
				//�ѩ�LAPI���w�L������API Usage �h�����i��s�W
				String new_usage_ID ="";
				ResultSet rs_api_usage_id = statement.executeQuery("SELECT MAX(API_Usage_ID) as maxapi_usage FROM [API USAGE]");
				if (rs_api_usage_id.next()) {
					int w = rs_api_usage_id.getInt("maxapi_usage") + 1;
					new_usage_ID= String.valueOf(w);
				}
				//��ܥXAPI Usage �� ID �̤j(���F�s�W)
				String exe_create_api_usage = "INSERT INTO [API USAGE] values(?,?,?,?,?)";
				PreparedStatement pst_create_api_usage =connection.prepareStatement(exe_create_api_usage);
				pst_create_api_usage.setString(1, new_usage_ID);//�s��ID
				pst_create_api_usage.setInt(2, 1);//�ϥΦ���
				pst_create_api_usage.setString(3, Client_ID);//����Client
				pst_create_api_usage.setString(4,search_api_id);//������API ID
				pst_create_api_usage.setString(5,String.valueOf(java_module_id));//��U��java module id
				pst_create_api_usage.executeUpdate();
				//�O����database��
			}
		} catch (SQLException e) {
			e.printStackTrace();
			//statement.executeUpdate(
				//	"INSERT INTO CLIENT values('" + w + "','src','" + 1 + "','" + dateFormat.format(date) + "')");
		}
		connection.close();
	}
	
	// read file content into a string
	public static String readFileToString(String filePath) throws IOException, ClassNotFoundException, SQLException {
		String filename = filePath.substring(filePath.lastIndexOf("\\")+1); //���o�̫᪺�ɮצW��
		String filedirectory = filePath.substring(filePath.substring(0,filePath.lastIndexOf("\\")).lastIndexOf("\\")+1,filePath.lastIndexOf("\\"));//���o�ɮצW�٤��e����Ƨ��W��
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		Statement statement = connection.createStatement();
		//database�s�u
		ResultSet rs_java_module_id = statement.executeQuery("select MAX(Java_Module_ID) as maxmodule from [JAVA MODULE]");
		if (rs_java_module_id.next()) {
			java_module_id = rs_java_module_id.getInt("maxmodule") + 1;
		}
		//��ܥX�̤j��java module id
		statement.executeUpdate("INSERT INTO [JAVA MODULE] values('" + java_module_id + "','" + filename + "','" + filedirectory + "','"+ Client_ID +"')");
		//�s�Wjava module �H�{�b���ɮצW��
		connection.close();
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		//�N�ɮפ��eŪ��string�Ǧ^
		return fileData.toString();
	}

	// loop directory to get file list
	public static void ParseFilesInDir(String dirPath) throws IOException, ClassNotFoundException, SQLException {
		//File dirs = new File(".");
		//String dirPath = dirs.getCanonicalPath() + File.separator + "src" + File.separator;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		int w = 0;
		int v = 0;
		
		
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		Statement statement = connection.createStatement();
		//database�s�u
		try {
			ResultSet rs_client_id = statement.executeQuery("select MAX(Client_ID) as maxclient from CLIENT");
			if (rs_client_id.next()) {
				w = rs_client_id.getInt("maxclient") + 1;
				Client_ID = String.valueOf(w);
			}
			//��̤ܳj��Client Id
			ResultSet rs_client_version = statement.executeQuery("select MAX(Client_version) as maxversion FROM CLIENT WHERE Client_name = 'src'");
			if (rs_client_version.next()) {
				v = rs_client_version.getInt("maxversion") + 1;
			}
			//�Y��Client name�w�Q�ϥιL �h�j�Mversion �Y�L�h��1
			statement.executeUpdate("INSERT INTO CLIENT values('" + w + "','src','" + v + "','" + dateFormat.format(date) + "')");

		} catch (SQLException e) {
			e.printStackTrace();
			statement.executeUpdate(
					"INSERT INTO CLIENT values('" + w + "','src','" + 1 + "','" + dateFormat.format(date) + "')");
		}
		connection.close();//database�����s�u
		File root = new File(dirPath);
		File[] files = root.listFiles();
		String filePath = null;
		//Ū���Ҧ��ɮ�
		for (File f : files) { // System.out.println(f); filePath =
			filePath = f.getAbsolutePath();
			if (f.isFile()) {
				parse(readFileToString(filePath));//�N�ɮ��ରstring��ǤJparse�i����R
			}
		}
		

	}

	// create sqlite with four table
	public static void opendb() throws ClassNotFoundException {
		{
			// load the sqlite-JDBC driver using the current class loader
			Class.forName("org.sqlite.JDBC");

			Connection connection = null;
			try {
				// create a database connection
				connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30); // set timeout to 30 sec.
				
				statement.executeUpdate(
						"CREATE TABLE IF NOT EXISTS [API] (API_ID STRING PRIMARY KEY NOT NULL, Package STRING NOT NULL, Class STRING NOT NULL, Method STRING, Field STRING)");
				statement.executeUpdate(
						"CREATE TABLE IF NOT EXISTS [CLIENT] (Client_ID STRING PRIMARY KEY NOT NULL, Client_name STRING NOT NULL, Client_Version STRING NOT NULL, Release_Date DATE NOT NULL)");
				statement.executeUpdate(
						"CREATE TABLE IF NOT EXISTS [JAVA MODULE] (Java_Module_ID STRING PRIMARY KEY NOT NULL, Module_name STRING NOT NULL, Module_Directory STRING NOT NULL, Client_ID STRING ,foreign key(Client_ID) REFERENCES CLIENT(Client_ID))");
				statement.executeUpdate(
						"CREATE TABLE IF NOT EXISTS [API USAGE] (API_Usage_ID STRING PRIMARY KEY NOT NULL, Usage_Count INT NOT NULL, Client_ID STRING , API_ID STRING , Java_Module_ID STRING, FOREIGN KEY(Client_ID) REFERENCES CLIENT(Client_ID), FOREIGN KEY(API_ID) REFERENCES API(API_ID), FOREIGN KEY(Java_Module_ID) REFERENCES [JAVA MODULE](Java_Module_ID))");
				//�Ydatabase table���s�b�h�s�W
			}

			
			catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (connection != null)
						connection.close();//����database�s�u
				} catch (SQLException e) { // Use SQLException class instead.
					System.err.println(e);
				}
			}
		}
	}

	
}