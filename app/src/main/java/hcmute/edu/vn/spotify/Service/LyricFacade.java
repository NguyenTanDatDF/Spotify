package hcmute.edu.vn.spotify.Service;

import android.content.Context;

import java.io.File;

import cn.zhaiyifan.lyric.widget.LyricView;
// facede pattern
public class LyricFacade {
    FileProcesser fileProcesser;
    StringProcesser stringProcesser;
    // constructor
    public LyricFacade() {
        fileProcesser = new FileProcesser();
        stringProcesser = new StringProcesser();
    }


    // it will return a file with a data, data is the lyric of track in lrc format
    public File createFileObjectWithLyric(Context context, String content)
    {
        String path = fileProcesser.getDevicePath(context);
        File file = fileProcesser.createFile(path);
        String[] line = stringProcesser.splitStringByChar(content);
        fileProcesser.writeFile(file, line);
        return file;
    }
}
