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

public class SampleController implements Initializable{
	get_data data= new get_data ();    //get data class
	 
	/*
	 透過public的boolean變數(Field,Method,Class,use)，
	 傳遞需要呈現的資料值。
	 */
	public boolean Field=false;		
	public boolean Method=false;
	public boolean Class=false;
	public boolean use=false;
	
	public String  search ="";    //搜尋字串
	
	@FXML
	private Label label;
	@FXML
	
	/*
	  選取所要讀取檔案
	  */
	private void choose_repo() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open Resource File");
		File selectedDirectory = directoryChooser.showDialog(null);
		if(selectedDirectory == null){
		     //No Directory selected
		}else{
		     System.out.println(selectedDirectory.getAbsolutePath());
		}
		
	}
	/*
	 初始化所有javafx物件。
	 所用到之圓餅圖及各個按鈕。
	 */
	 @FXML
	    private PieChart pieChart;     
	 
	  @FXML
	    private ToggleButton Field_button;
	  @FXML
	    private ToggleButton Method_button;
	  @FXML
	    private ToggleButton Class_button;
	  
	  /*
	   設定javafx物件中button的function。
	   透過choose_Method、choose_Field、choose_class決定所要呈現資料。
	   */
	  
	 
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
		  if(!Class) {
		  		Class=true;
		  	}
		  	else {
		  		Class=false;
		  	}
	    }
	  
	  /*
	   以choose_type確定所要使用搜尋方法。
	  判定use的決定讀取button還是textfield資料。 
	   */
	  
	  @FXML
	    public void choose_type(){                
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
	/*
	 更新圓餅圖和長條圖資料*/
	 public  void set_Data(){                         
		data.get(Class, Method, Field, search=searchfield.getText(),use);  //以將資料傳入getdata的class獲取所要呈現資料
		use=false;                                                         //初始化使用值
		search=searchfield.getText();
		barchart.getData().clear();                                        //清除原本長條圖資料
		pieChart.getData().clear();                                        //清除原本圓餅圖資料
		ArrayList<String> name = new ArrayList<String>();                  //圓餅圖和長條圖所要API名稱資料
		ArrayList<Integer> times = new ArrayList<Integer>();               //圓餅圖和長條圖所要API使用次數資料
		name=data.result_api;                                              
		times=data.result_count;
		 ObservableList<PieChart.Data> pieChartData                         //pieChart
         = FXCollections.observableArrayList();
		XYChart.Series setl =new XYChart.Series<>();                        //barChart
		for(int i=0;i<name.size();i++) {                                    //存取資料
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
	public void initialize(URL url, ResourceBundle rb) {             //初始化資料
		 XYChart.Series setl =new XYChart.Series<>();   //barChart
		 barchart.getData().addAll(setl);
		 
		 ObservableList<PieChart.Data> pieChartData     //pieChart
	                = FXCollections.observableArrayList(

	                new PieChart.Data("", 0));
		 
		 pieChart.setData(pieChartData);
		 
	}
	
}