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
	 
	public boolean Field=false;
	public boolean Method=false;
	public boolean Class=false;
	public boolean use=false;
	public String  search ="";    //搜尋字串
	
	@FXML
	private Label label;
	@FXML
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
		  if(!Class) {
		  		Class=true;
		  	}
		  	else {
		  		Class=false;
		  	}
	    }
	  
	  @FXML
	    public void choose_type(){                //確定要拿資料的function
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
	 public  void set_Data(){                         //放資料
		data.get(Class, Method, Field, search=searchfield.getText(),use);
		use=false;
		search=searchfield.getText();
		barchart.getData().clear();                   //清除資料
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
	
}