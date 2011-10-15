package jpropcompare.loading.strategy;

import java.util.Properties;

/**
 * The LoadingStrategy interface provides a customisable way to allow Property files to be constructed, for later comparison.
 * For instance, if  properties have a more complex method of loading a single file, such as a layered structure, then this interface provides the
 * ability to consolidate all properties into a single properties file to compare.
 *
 * Author: Joe Vartuli
 * Date: 19/09/11
 */
public interface LoadingStrategy {

    /**
     * Returns the property file name argument injected into the command line (-p1).
     * @return propertyName
     */
    public String getPropertyName1();

    /**
     * The property file name provided via command line (-p1) is passed in.
     * @param propertyName - property file name provided via command line (-p1) is passed in.
     */
    public void setPropertyName1(String propertyName);

    /**
     * Returns the property file name argument injected into the command line (-p2).
     * @return propertyName
     */
    public String getPropertyName2();

    /**
     * The property file name provided via command line (-p2) is passed in.
     * @param propertyName - property file name provided via command line (-p2) is passed in.
     */
    public void setPropertyName2(String propertyName);

    /**
     * Returns the first finalised property file to compare
     * @return - finalised property file
     */
    public Properties getPropertyFile1();

    /**
     * Returns the second finalised property file to compare
     * @return - finalised property file
     */
    public Properties getPropertyFile2();

    /**
     * Args used to call are injected in here for full customisability.
     * @param args - used to execute the jar
     */
    public void setArgs(String[] args);

}
