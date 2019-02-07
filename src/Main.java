import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Main {

	// use ASTParse to parse string
	public static void parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {
			Set names = new HashSet();
			public boolean visit(TypeDeclaration node) {
				System.out.print("Class: '" + node.getName());
				return true;
			}
			public boolean visit(MethodDeclaration node) {
				System.out.print("METHOD: '" + node.getName());
				return true;
			}
			public boolean visit(FieldDeclaration node) {
				System.out.print("FIELD'" + node.fragments());
				return true; 
			}
			public boolean visit(SimpleType node) {
				System.out.print("TYPE"+node.toString());
				return true;
			}
			public boolean visit(VariableDeclarationFragment node) {
				SimpleName name = node.getName();
				this.names.add(name.getIdentifier());
				System.out.println("  Declaration of '" + name + "' at line" + cu.getLineNumber(name.getStartPosition()));
				return false; // do not continue
			}

			public boolean visit(SimpleName node) {
				if (this.names.contains(node.getIdentifier())) {
					//System.out.println("Usage of '" + node + "' at line " + cu.getLineNumber(node.getStartPosition()));
				}
				return true;
			}
			//AST 找各種東西
			//但不確定 type method field 找的是不是正確的 可能要配合下面的找variable後再拿他的class method field
			//need survey
		});

	}

	// read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			//System.out.println(numRead);
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}

		reader.close();

		return fileData.toString();
	}

	// loop directory to get file list
	public static void ParseFilesInDir() throws IOException {
		File dirs = new File(".");
		String dirPath = dirs.getCanonicalPath() + File.separator + "src" + File.separator;

		File root = new File(dirPath);
		// System.out.println(rootDir.listFiles());
		File[] files = root.listFiles();
		String filePath = null;
		//read single file
		//parse(readFileToString("C:\\Users\\User\\workspace\\winter_project\\src\\Test.java"));
		for (File f : files) {
			//System.out.println(f);
			filePath = f.getAbsolutePath();
			System.out.println(filePath);
			if (f.isFile()) {
				parse(readFileToString(filePath));
			}
		}
	}
	//create sqlite with four table
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

				statement.executeUpdate("DROP TABLE IF EXISTS API");
				statement.executeUpdate("DROP TABLE IF EXISTS CLIENT");
				statement.executeUpdate("DROP TABLE IF EXISTS [JAVA MODULE]");
				statement.executeUpdate("DROP TABLE IF EXISTS [API USAGE]");
				statement.executeUpdate(
						"CREATE TABLE [API] (API_ID STRING PRIMARY KEY, Package STRING NOT NULL, Class STRING NOT NULL, Method STRING, Field STRING)");
				statement.executeUpdate(
						"CREATE TABLE [CLIENT] (Client_ID STRING PRIMARY KEY, Client_name STRING NOT NULL, Client_Version STRING NOT NULL, Release_Data DATE NOT NULL)");
				statement.executeUpdate(
						"CREATE TABLE [JAVA MODULE] (Java_Module_ID STRING PRIMARY KEY, Module name STRING NOT NULL, Module_Directory STRING NOT NULL, Client_ID STRING NOT NULL ,FOREIGN KEY(Client_ID) REFERENCES CLIENT(Client_ID))");
				statement.executeUpdate(
						"CREATE TABLE [API USAGE] (API_Usage_ID STRING PRIMARY KEY, Usage_Count INT NOT NULL, Client_ID STRING NOT NULL, API_ID STRING NOT NULL, Java_Module_ID STRING NOT NULL, FOREIGN KEY(Client_ID) REFERENCES CLIENT(Client_ID), FOREIGN KEY(API_ID) REFERENCES API(API_ID), FOREIGN KEY(Java_Module_ID) REFERENCES [JAVA MODULE](Java_Module_ID))");
			/*statement.executeUpdate("INSERT INTO person values(' "+ids[i]+"', '"+names[i]+"')");*/
				//insert value in db
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

	public static void main(String[] args) throws IOException,ClassNotFoundException {
		opendb();
		ParseFilesInDir();
	}
}
