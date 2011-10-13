package jpropcompare.output;

/**
 * Interface allowing multiple output points
 * @author: Joe Vartuli
 * @date: 19/09/11
 */
public interface Output {

    /**
     * Result data given to the Output for processing
     * @param data - resulting data
     */
    public void write(String data);

    public void finalise();

}
