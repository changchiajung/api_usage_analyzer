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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ChildListPropertyDescriptor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.MethodRefParameter;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Main_Method {
	 // use ASTParse to parse string
	static String Client_ID ="";
	public static void parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setResolveBindings(true);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setEnvironment( // apply classpath
				new String[] { "C:\\Users\\User\\workspace\\winter_project\\bin" }, //
				new String[] { "" }, new String[] { "UTF-8" }, true);
		parser.setUnitName("winter_project/src/data.java");

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {
			Set names = new HashSet();

			/*
			 * public boolean visit(TypeDeclaration node) { System.out.print("Class: '" +
			 * node.getName()); return true; }
			 * 
			 * public boolean visit(ParameterizedType node) { System.out.print("METHOD: " +
			 * node.getName()); System.out.print(": "+node.getClass().getPackage());
			 * System.out.println("Class: "+node.getClass()); return true; }
			 * 
			 */
			public boolean visit(MethodInvocation node) {
				System.out.println("METHOD Invocation: " + node.getName());
				Expression expression = node.getExpression();
				if (expression != null) { //
					System.out.println("Expr: " + expression.toString());
					ITypeBinding typeBinding = expression.resolveTypeBinding();
					if (typeBinding != null) {
						System.out.println("Class: " + typeBinding.getName());
						System.out.println("Package: " + typeBinding.getPackage().getName());
					}
				}
				IMethodBinding method = node.resolveMethodBinding();
				String method_string = method.toString();
				System.out.println("parament type :"
						+ method_string.substring(method_string.indexOf("(") + 1, method_string.indexOf(")")));

				return true;
			}
			/*
			 * 
			 * public boolean visit(FieldDeclaration node) { System.out.print("FIELD'" +
			 * node.fragments()); return true; } public boolean visit(SimpleType node) {
			 * System.out.print("TYPE"+node.toString()); return true; } public boolean
			 * visit(VariableDeclarationFragment node) { SimpleName name = node.getName();
			 * this.names.add(name.getIdentifier()); System.out.println("  Declaration of '"
			 * + name + "' at line" + cu.getLineNumber(name.getStartPosition())); return
			 * true; // do not continue }
			 * 
			 * public boolean visit(SimpleName node) { if
			 * (this.names.contains(node.getIdentifier())) {
			 * //System.out.println("Usage of '" + node + "' at line " +
			 * cu.getLineNumber(node.getStartPosition())); } return true; }
			 */
			// AST 找各種東西
			// 但不確定 type method field 找的是不是正確的 可能要配合下面的找variable後再拿他的class method field
			// need survey
		});

	}

	// read file content into a string
	public static String readFileToString(String filePath) throws IOException, ClassNotFoundException, SQLException {
		String filename = filePath.substring(filePath.lastIndexOf("\\")+1);
		String filedirectory = filePath.substring(filePath.substring(0,filePath.lastIndexOf("\\")).lastIndexOf("\\")+1,filePath.lastIndexOf("\\"));
		int java_module_id = 0 ;
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		Statement statement = connection.createStatement();
		ResultSet rs_java_module_id = statement.executeQuery("select MAX(Java_Module_ID) as maxmodule from [JAVA MODULE]");
		if (rs_java_module_id.next()) {
			java_module_id = rs_java_module_id.getInt("maxmodule") + 1;
		}
		statement.executeUpdate("INSERT INTO [JAVA MODULE] values('" + java_module_id + "','" + filename + "','" + filedirectory + "','"+ Client_ID +"')");

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
		return fileData.toString();
	}

	// loop directory to get file list
	public static void ParseFilesInDir() throws IOException, ClassNotFoundException, SQLException {
		File dirs = new File(".");
		String dirPath = dirs.getCanonicalPath() + File.separator + "src" + File.separator;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		int w = 0;
		int v = 0;
		
		
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		Statement statement = connection.createStatement();
		try {
			ResultSet rs_client_id = statement.executeQuery("select MAX(Client_ID) as maxclient from CLIENT");
			if (rs_client_id.next()) {
				w = rs_client_id.getInt("maxclient") + 1;
				Client_ID = String.valueOf(w);
			}
			ResultSet rs_client_version = statement.executeQuery("select MAX(Client_version) as maxversion FROM CLIENT WHERE Client_name = 'src'");
			if (rs_client_version.next()) {
				v = rs_client_version.getInt("maxversion") + 1;
			}
			statement.executeUpdate("INSERT INTO CLIENT values('" + w + "','src','" + v + "','" + dateFormat.format(date) + "')");

		} catch (SQLException e) {
			System.out.println(e);
			statement.executeUpdate(
					"INSERT INTO CLIENT values('" + w + "','src','" + 1 + "','" + dateFormat.format(date) + "')");
		}

		File root = new File(dirPath);
		File[] files = root.listFiles();
		String filePath = null;
		// read single file
		parse(readFileToString("C:\\Users\\User\\workspace\\winter_project\\src\\data.java"));
		/*
		for (File f : files) { // System.out.println(f); filePath =
			filePath = f.getAbsolutePath();
			System.out.println(filePath);
			if (f.isFile()) {
				parse(readFileToString(filePath));
				//readFileToString(filePath);
			}
		}
		*/

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
			}

			catch (SQLException e) {
				System.err.println(e.getMessage());
			} finally {
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException e) { // Use SQLException class instead.
					System.err.println(e);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		opendb();
		ParseFilesInDir();
	}
}
