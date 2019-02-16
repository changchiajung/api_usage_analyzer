package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class get_data {
    public ArrayList<String> result_api;
    public ArrayList<Integer> result_count;

    public get_data() {
        result_api = new ArrayList<String>();
        result_count = new ArrayList<Integer>();
    }

//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        get_data getData = new get_data();
//        getData.get(true, false, true, "lang", false);
//    }


    public boolean get(boolean class_name, boolean method_name, boolean field_name, String search, boolean send) {
        try {
            if (send) {
                //no search
            } else {
                //only search
            }
//            System.out.println("get data");
            Class.forName("org.sqlite.JDBC");
            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            ResultSet rs_api_usage = statement.executeQuery("select API_ID,Usage_Count from [API USAGE]");

            ArrayList<String> tmp_result_api = new ArrayList();
            ArrayList<Integer> tmp_result_count = new ArrayList();
            while (rs_api_usage.next()) {
                tmp_result_api.add(rs_api_usage.getString(1) + " ");
                tmp_result_count.add(Integer.parseInt(rs_api_usage.getString(2)));
            }
            int num = 0;
            String ret = "";
            while (num < tmp_result_api.size()) {
                ResultSet rs_api_search = statement.executeQuery(
                        "select Package,Class,Method,Field from [API] WHERE API_ID = " + tmp_result_api.get(num));
                /*if (rs_api_search.wasNull()) {
                    continue;
                }*/
                while (rs_api_search.next()) {
                    String tmp = "";
                    if (send) {
                        if (class_name) {
                            if (rs_api_search.getString(3) == null && rs_api_search.getString(4) == null) {
                                tmp = rs_api_search.getString(1) + "." + rs_api_search.getString(2);
                                ret = tmp;
                                //continue;
                            }
                        }
                        if (method_name) {
                            if (rs_api_search.getString(4) == null && !(rs_api_search.getString(3) == null)) {
                                tmp = rs_api_search.getString(1) + "." + rs_api_search.getString(2) + "." + rs_api_search.getString(3);
                                ret = tmp;
                                //continue;
                            }
                        }
                        if (field_name) {
                            if (rs_api_search.getString(3) == null && !(rs_api_search.getString(4) == null)) {
                                tmp = rs_api_search.getString(1) + "." + rs_api_search.getString(2) + "." + rs_api_search.getString(4);
                                ret = tmp;
                                //continue;
                            }
                        }
                        //nothing happend -> remove item
                        if(ret.equals("")){
                            tmp_result_api.remove(num);
                            tmp_result_count.remove(num);
                            continue;
                        }

                    } else {
                        for (int i = 1; i <= 4; i++) {
                            tmp = rs_api_search.getString(i);
                            //System.out.println(tmp);
                            if (rs_api_search.wasNull()) {
                                continue;
                            } else {
                                ret += tmp + ".";
                            }
                        }
                    }
                    tmp_result_api.set(num, ret);
                    ret = "";
                    num += 1;

                }

            }

            result_count=tmp_result_count;
            result_api=tmp_result_api;
        } catch (Exception e) {
            //System.out.println(e);
            e.printStackTrace();
            return false;
        }
        if (!send) {
            for (int i = 0; i < this.result_api.size(); i++) {
                if (!result_api.get(i).contains(search)) {
                    result_api.remove(i);
                    result_count.remove(i);
                    i--;
                }
            }
        }
//        System.out.println(result_api.toString());
//        System.out.println(result_count.toString());
        return true;
    }


}