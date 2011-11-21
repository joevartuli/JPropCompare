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
     * Initialise the loading strategy with the property file names and additional arguments
     * @param propertyFileNameOne - name of the first property file
     * @param propertyFileNameTwo - name of the second property file
     * @param args - additional arguments (used during the Comparator to pass additional arguments)
     */
    public void initialise(String propertyFileNameOne, String propertyFileNameTwo, String[] args);

    /**
     * Returns the first finalised property file to compare
     * @return - finalised property file
     */
    public Properties getPropertyFileOne();

    /**
     * Returns the second finalised property file to compare
     * @return - finalised property file
     */
    public Properties getPropertyFileTwo();


}
