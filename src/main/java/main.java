
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import joinery.DataFrame;
import static tech.tablesaw.aggregate.AggregateFunctions.median;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

public class main {
    public static void main(String[] args) throws IOException {

            double[] numbers = {1, 2, 3, 4};
            DoubleColumn nc = DoubleColumn.create("nc", numbers);
            System.out.println(nc.print());
            
            ////////tablesaw ////////
            ReadCSVByTableSaw ();
           ///////joinery.DataFrame////////
           ReadCSVByJoinery();
    }

    public static void ReadCSVByTableSaw() {
      
        try {

            Table titanicData = Table.read().csv("titanic-passengers.csv");
            List<String> colNames = titanicData.columnNames();
            titanicData.structure().printAll();
            titanicData .structure() .where(titanicData.structure().stringColumn("Column Type").isEqualTo("String"));
            titanicData.first(3);
            System.out.println(titanicData.summary());
            
            //Descriptive statistics
            titanicData.column("Survived").summary().print();
            
            //Median
            Table PassByGender = titanicData.summarize("Gender", median).by("Sex").sortOn("Sex");
            PassByGender.setName("Median Passengers by Gender");
            PassByGender.column("Median Passengers by Gender").summary().print();
  
            PassByGender.write().csv("titanic-passengers-Updated.csv");
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    public static void ReadCSVByJoinery() {
        try {
            DataFrame<Object> df = DataFrame.readCsv("titanic-passengers.csv");
            List<Object> countrylst = df.col("Name");
            System.out.println(" No. of Passangers = " + countrylst.size());
            System.out.println("Passangers are :  " + countrylst);
            
            DataFrame<Object> passengersGroupedByPClass = df.groupBy("Pclass")
                    .count()
                    .sortBy("Age")
                    .head(10);
            System.out.println("Passangers are :  " + passengersGroupedByPClass);
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

