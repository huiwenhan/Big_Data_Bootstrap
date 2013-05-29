package me.huiwen.example.weka.linearRegression.housePrices;

import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class WekaHousePrices {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		// Define each attribute (or column), and give it a numerical column number
		// Likely, a better design wouldn't require the column number, but
		// would instead get it from the index in the container
		Attribute a1 = new Attribute("houseSize", 0);
		Attribute a2 = new Attribute("lotSize", 1);
		Attribute a3 = new Attribute("bedrooms", 2);
		Attribute a4 = new Attribute("granite", 3);
		Attribute a5 = new Attribute("bathroom", 4);
		Attribute a6 = new Attribute("sellingPrice", 5);
		
		// Each element must be added to a FastVector, a custom
		// container used in this version of Weka.
		// Later versions of Weka corrected this mistake by only
		// using an ArrayList		
		FastVector attrs = new FastVector();
		attrs.addElement(a1);
		attrs.addElement(a2);
		attrs.addElement(a3);
		attrs.addElement(a4);
		attrs.addElement(a5);
		attrs.addElement(a6);
		
		// Each data instance needs to create an Instance class
		// The constructor requires the number of columns that
		// will be defined.  In this case, this is a good design,
		// since you can pass in empty values where they exist.
				
		Instance i1 = new Instance(6);
		i1.setValue(a1, 3529);
		i1.setValue(a2, 9191);
		i1.setValue(a3, 6);
		i1.setValue(a4, 0);
		i1.setValue(a5, 0);
		i1.setValue(a6, 205000);

		Instance i2 = new Instance(6);
		i2.setValue(a1, 3247);
		i2.setValue(a2, 10061);
		i2.setValue(a3, 5);
		i2.setValue(a4, 1);
		i2.setValue(a5, 1);
		i2.setValue(a6, 224900);

		Instance i3 = new Instance(6);
		i3.setValue(a1, 4032);
		i3.setValue(a2, 10150);
		i3.setValue(a3, 5);
		i3.setValue(a4, 0);
		i3.setValue(a5, 1);
		i3.setValue(a6, 197900);

		Instance i4 = new Instance(6);
		i4.setValue(a1, 2397);
		i4.setValue(a2, 14156);
		i4.setValue(a3, 4);
		i4.setValue(a4, 1);
		i4.setValue(a5, 0);
		i4.setValue(a6, 189900);

		Instance i5 = new Instance(6);
		i5.setValue(a1, 2200);
		i5.setValue(a2, 9600);
		i5.setValue(a3, 4);
		i5.setValue(a4, 0);
		i5.setValue(a5, 1);
		i5.setValue(a6, 195000);

		Instance i6 = new Instance(6);
		i6.setValue(a1, 3536);
		i6.setValue(a2, 19994);
		i6.setValue(a3, 6);
		i6.setValue(a4, 1);
		i6.setValue(a5, 1);
		i6.setValue(a6, 325000);
		
		Instance i7 = new Instance(6);
		i7.setValue(a1, 2983);
		i7.setValue(a2, 9365);
		i7.setValue(a3, 5);
		i7.setValue(a4, 0);
		i7.setValue(a5, 1);
		i7.setValue(a6, 230000);

		// Each Instance has to be added to a larger container, the
		// Instances class.  In the constructor for this class, you
		// must give it a name, pass along the Attributes that
		// are used in the data set, and the number of 
		// Instance objects to be added.  Again, probably not ideal design
		// to require the number of objects to be added in the constructor,
		// especially since you can specify 0 here, and then add Instance
		// objects, and it will return the correct value later (so in 
		// other words, you should just pass in '0' here)
	    Instances dataset = new Instances("housePrices", attrs, 7);
	    dataset.add(i1);
	    dataset.add(i2);
	    dataset.add(i3);
	    dataset.add(i4);
	    dataset.add(i5);
	    dataset.add(i6);
	    dataset.add(i7); 
	    
	    // In the Instances class, we need to set the column that is
	    // the output (aka the dependent variable).  You should remember
	    // that some data mining methods are used to predict an output
	    // variable, and regression is one of them.  
	    dataset.setClassIndex(dataset.numAttributes() - 1);

	    // Create the LinearRegression model, which is the data mining
	    // model we're using in this example
	    LinearRegression linearRegression = new LinearRegression();
	    
	    // This method does the "magic", and will compute the regression
	    // model.  It takes the entire dataset we've defined to this point
	    // When this method completes, all our "data mining" will be complete
	    // and it is up to you to get information from the results
	    linearRegression.buildClassifier(dataset);
	    
	    // We are most interested in the computed coefficients in our model,
	    // since those will be used to compute the output values from an
	    // unknown data instance.
	    double[] coef = linearRegression.coefficients();
	    
	    // Using the values from my house (from the first article), we
	    // plug in the values and multiply them by the coefficients
	    // that the regression model created.  Note that we skipped
	    // coefficient[5] as that is 0, because it was the output
	    // variable from our training data
	    double myHouseValue = (coef[0] * 3198) + 
	                          (coef[1] * 9669) +
	                          (coef[2] * 5) +
	                          (coef[3] * 3) +
	                          (coef[4] * 1) +
	                          coef[6];
	    
	    System.out.println(myHouseValue);
	    // outputs 219328.35717359098
	    // which matches the output from the earlier article
	}

}
