package in.wptrafficanalyzer.androidplotdemo;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	   private GestureDetector gestureDetector;



	    @Override
	    public boolean onTouchEvent(MotionEvent event) {
	        if (gestureDetector.onTouchEvent(event))
	            return true;
	        return super.onTouchEvent(event);
	    }

	private XYPlot xyPlot;
	
	final String[] mMonths = new String[] {
        	"0.000","0.004", "0.008","0.012", "0.016","0.020",
        	"0.24", "0.028","0.032","0.036", "0.040","0.044"
        };
	 
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(MainActivity.this, "Zmiana wykresu", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
 
        // initialize our XYPlot reference:
        xyPlot = (XYPlot) findViewById(R.id.xyplot);
 
        
        Number[] income =  {0, 200, 300, 300, 200, 0, -200, -300, -300, -200, 0 };
        Number[] expense = {0, 100, 200, 200, 100, 0, -100, -200, -200, -100, 0 };
 
        // Converting the above income array into XYSeries
        XYSeries incomeSeries = new SimpleXYSeries(
                Arrays.asList(income),          		 // array => list
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY , // Y_VALS_ONLY means use the element index as the x value
                "Wykres 2");                             	 // Title of this series
 
     // Converting the above expense array into XYSeries
        XYSeries expenseSeries = new SimpleXYSeries(	
        		Arrays.asList(expense), 				// array => list
        		SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
        		"Wykres 1");								// Title of this series
 
        // Create a formatter to format Line and Point of income series
        LineAndPointFormatter incomeFormat = new LineAndPointFormatter(
                Color.rgb(0, 0, 255),                   // line color
                Color.rgb(200, 200, 200),               // point color
                null );                					// fill color (none)
        
        
        // Create a formatter to format Line and Point of expense series
        LineAndPointFormatter expenseFormat = new LineAndPointFormatter(
                Color.rgb(255, 0, 0),                   // line color
                Color.rgb(200, 200, 200),               // point color
                null);					                // fill color (none)
 
        
        
        // add expense series to the xyplot:
        xyPlot.addSeries(expenseSeries,expenseFormat);
        
        // add income series to the xyplot:
        xyPlot.addSeries(incomeSeries, incomeFormat);
        
        
        // Formatting the Domain Values ( X-Axis )
        xyPlot.setDomainValueFormat(new Format() {
 
			@Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                return new StringBuffer( mMonths[ ( (Number)obj).intValue() ]  );				
            }
 
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null; 
            }
        });        
       
        xyPlot.setDomainLabel("[sekunda.msekunda]");
        xyPlot.setRangeLabel("Wszystkie V [V]");
        
        // Increment X-Axis by 1 value
        xyPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
        
        xyPlot.getGraphWidget().setRangeLabelWidth(50);               
        
        // Reduce the number of range labels
        xyPlot.setTicksPerRangeLabel(2);
        
        // Reduce the number of domain labels
        xyPlot.setTicksPerDomainLabel(2);
 
        // Remove all the developer guides from the chart
        xyPlot.disableAllMarkup();
    }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}