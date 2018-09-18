package hr.fer.zemris.java.hw05.db;

/**
 * Class containing multiple implementations of the <code>IFieldValueGetter</code>.
 * Each field is an implementation of concrete strategy.
 * @author Alen Carin
 *
 */
public class FieldValueGetters {

	/**Get method is implemented to return first name of the student record.*/
	public static final IFieldValueGetter FIRST_NAME;
	
	/**Get method is implemented to return last name of the student record.*/
	public static final IFieldValueGetter LAST_NAME;
	
	/**Get method is implemented to return jmbag of the student record.*/
	public static final IFieldValueGetter JMBAG;
	
	static {
		FIRST_NAME = record -> record.getFirstName();
		
		LAST_NAME = record -> record.getLastName();
		
		JMBAG = record -> record.getJmbag();
	}
}
