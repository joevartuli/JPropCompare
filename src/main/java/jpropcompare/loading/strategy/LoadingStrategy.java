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
