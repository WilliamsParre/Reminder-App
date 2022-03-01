package dsProject;
import java.time.LocalDate;
import java.time.Period; 
public class DiffDate {   
	static int find(LocalDate  first_date, LocalDate second_date)   
    {     
        Period difference = Period.between(first_date, second_date);      
        return (difference.getDays()+difference.getMonths()+difference.getYears());   
    }             
}