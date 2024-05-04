import java.io.*;

public class FileUsing {
    private String filePath;

    public FileUsing(String filePath) {
        this.filePath = filePath;
    }
    // for stats
    public void writeToFile(String content) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // for log
    public void writeToFile2(String content) {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(content + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // read for stats
    public String readFromFile() {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                if (reader.ready()) {
                    content.append("\n");
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
    // clear for log
    public void clearFile(String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath, false);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}