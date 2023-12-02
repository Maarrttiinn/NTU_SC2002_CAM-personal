package cam;

/**
Represents the Faculty of different schools.
@author Sruthi (Version 1.0)
@author Low Kar Choon (Version 1.1)
@version 1.1
@since 25-11-2023
*/
public enum Faculty {
	/**
	 * Department 1 - ADM
	 */
    DEPARTMENT1("ADM"),
    
    /**
	 * Department 2 - EEE
	 */
    DEPARTMENT2("EEE"),
    
    /**
	 * Department 3 - NBS
	 */
    DEPARTMENT3("NBS")
    
    /**
	 * Department 4 - SCSE
	 */,
    DEPARTMENT4("SCSE"),
    
    /**
	 * Department 5 - SSS
	 */
    DEPARTMENT5("SSS"),
    
    /**
	 * Department 6 - NTU
	 */
    DEPARTMENT6("NTU");

	/**
	 * Faculty Type of the Faculty.
	 * Stores the name of the Faculty.
	 */
    private final String facultyType;

    /**
     * Constructor for Faculty
     * @param facultyType The name of the Faculty
     */
    Faculty(String facultyType){
        this.facultyType = facultyType;
    }

    /**
     * Gets the name of the Department
     * @return The name of the Faculty
     */
    public String getFacultyType() {
        return facultyType;
    }
    
    /**
     * Gets the Faculty's department object based on the name of the Faculty
     * @param facultyName The name of the Faculty
     * @return The Faculty department object
     */
    public static Faculty getFacultyType(String facultyName) {
    	Faculty fac = null;
    	for (Faculty f : Faculty.values()) {
    		if (f.getFacultyType().equals(facultyName))
    			fac = f;
    	}
    	return fac;
    }
}
