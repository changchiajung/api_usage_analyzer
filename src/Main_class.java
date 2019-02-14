import org.eclipse.jdt.core.dom.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class Main_class {
    // use ASTParse to parse string
    public static void parse(String str) {
        ASTParser parser = ASTParser.newParser(AST.JLS11);
        parser.setResolveBindings(true);
        parser.setSource(str.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setEnvironment( // apply classpath
                new String[] { "D:\\premiere_project\\winter_project\\src" }, //
                new String[] { "" }, new String[] { "UTF-8" }, true);
        parser.setUnitName("winter_project/src/data.java"	);

        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        AST ast = cu.getAST();
        System.out.println(ast);
        cu.accept(new ASTVisitor() {
            Set names = new HashSet();

            public boolean visit(VariableDeclarationFragment node) {
                //System.out.println('1');
                //System.out.println(node);
                //System.out.println("var name: " + node.getName());

                //System.out.println(node.getNameProperty());
                //System.out.println("type name : "+ node.resolveBinding().getVariableDeclaration().getType().getName());
                try {
                    ITypeBinding typeBinding = node.getInitializer().resolveTypeBinding();
                    System.out.println(node.getInitializer().resolveTypeBinding().getPackage().isUnnamed());
                    System.out.println("package : "+node.getInitializer().resolveTypeBinding().getPackage().getName());
                    System.out.println("type name : "+ node.resolveBinding().getVariableDeclaration().getType().getName());
                }
                catch (Exception e){

                }

                //Expression expression = node.getExpression();
                /*if (expression != null) {
                    //System.out.println("Expr: " + expression.toString());
                    ITypeBinding typeBinding = expression.resolveTypeBinding();
                    if (typeBinding != null) {
                        System.out.println("Class: " + typeBinding.getName());
                        System.out.println("Package: "+ typeBinding.getPackage().getName()
                        );
                    }
                }*/
                //ITypeBinding  method=node.resolveBinding();
                //String method_string = method.toString();
                //System.out.println(method_string);
                //System.out.println("parament type :" + method_string.substring(method_string.indexOf("(")+1,method_string.indexOf(")")));
                System.out.println();
                return true;
            }
        });

    }

    // read file content into a string
    public static String readFileToString(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        char[] buf = new char[10];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            // System.out.println(numRead);
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
        // read single file
        parse(readFileToString("D:\\premiere_project\\winter_project\\src\\data.java"));
        /*
         * for (File f : files) { //System.out.println(f); filePath =
         * f.getAbsolutePath(); System.out.println(filePath); if (f.isFile()) {
         * parse(readFileToString(filePath)); } }
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
                /*
                 * statement.executeUpdate("INSERT INTO person values(' "+ids[i]+"', '"+names[i]
                 * +"')");
                 */
                // insert value in db
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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        opendb();
        ParseFilesInDir();
    }
}