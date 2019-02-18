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
	 �z�Lpublic��boolean�ܼ�(Field,Method,Class,use)�A
	 �ǻ��ݭn�e�{����ƭȡC
	 */
	public boolean Field=false;		
	public boolean Method=false;
	public boolean Class=false;
	public boolean use=false;
	
	public String  search ="";    //�j�M�r��
	
	@FXML
	private Label label;
	@FXML
	
	/*
	  ����ҭnŪ���ɮ�
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
	 ��l�ƩҦ�javafx����C
	 �ҥΨ줧���ϤΦU�ӫ��s�C
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
	   �]�wjavafx����button��function�C
	   �z�Lchoose_Method�Bchoose_Field�Bchoose_class�M�w�ҭn�e�{��ơC
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
	   �Hchoose_type�T�w�ҭn�ϥηj�M��k�C
	  �P�wuse���M�wŪ��button�٬Otextfield��ơC 
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
	 ��s���ϩM�����ϸ��*/
	 public  void set_Data(){                         
		data.get(Class, Method, Field, search=searchfield.getText(),use);  //�H�N��ƶǤJgetdata��class����ҭn�e�{���
		use=false;                                                         //��l�ƨϥέ�
		search=searchfield.getText();
		barchart.getData().clear();                                        //�M���쥻�����ϸ��
		pieChart.getData().clear();                                        //�M���쥻���ϸ��
		ArrayList<String> name = new ArrayList<String>();                  //���ϩM�����ϩҭnAPI�W�ٸ��
		ArrayList<Integer> times = new ArrayList<Integer>();               //���ϩM�����ϩҭnAPI�ϥΦ��Ƹ��
		name=data.result_api;                                              
		times=data.result_count;
		 ObservableList<PieChart.Data> pieChartData                         //pieChart
         = FXCollections.observableArrayList();
		XYChart.Series setl =new XYChart.Series<>();                        //barChart
		for(int i=0;i<name.size();i++) {                                    //�s�����
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
	public void initialize(URL url, ResourceBundle rb) {             //��l�Ƹ��
		 XYChart.Series setl =new XYChart.Series<>();   //barChart
		 barchart.getData().addAll(setl);
		 
		 ObservableList<PieChart.Data> pieChartData     //pieChart
	                = FXCollections.observableArrayList(

	                new PieChart.Data("", 0));
		 
		 pieChart.setData(pieChartData);
		 
	}
	
}