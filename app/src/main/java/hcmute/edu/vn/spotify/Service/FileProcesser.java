package hcmute.edu.vn.spotify.Service;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import hcmute.edu.vn.spotify.Activity.PlayTrackActivity;
import hcmute.edu.vn.spotify.Activity.TopicMusicActivity;

public class FileProcesser {
    Context context;
    // get the path of context device
    public String getDevicePath(Context context)
    {
        return context.getFilesDir() + "/" + "hello.lrc";
    }
    // create file in the input path
    public File createFile(String path)
    {
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return file;
    }

    // write the array of string and every element it will create new line
    public void writeFile(File file, String[] content)
    {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            for(int i = 0 ; i < content.length; i++)
            {
                fileWriter.write(content[i]);
                fileWriter.write("\r\n");
            }

            fileWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
