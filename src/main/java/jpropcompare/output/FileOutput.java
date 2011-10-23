package jpropcompare.output;

import jpropcompare.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Author: Joe Vartuli
 * Date: 22/09/11
 */
public class FileOutput extends StandardOutputFormatter {

    private StringBuilder stringBuilder;
    private String filename;
    private File fileOutput;

    public FileOutput(String filename) {
        this.stringBuilder = new StringBuilder();
        this.filename = filename;
        setup();
    }

    private void setup() {
        this.fileOutput = new File(filename);
        if (!fileOutput.exists()) {
            File parent = fileOutput.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
        }

    }

    @Override
    public void write(String data) {
        stringBuilder.append(data);
        stringBuilder.append(Constants.NEW_LINE);
    }


    @Override
    public void finalise() {
        String data = stringBuilder.toString();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileOutput);
            fileWriter.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
