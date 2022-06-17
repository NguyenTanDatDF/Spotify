package hcmute.edu.vn.spotify.Service;

import android.content.Context;

import java.io.File;

import cn.zhaiyifan.lyric.widget.LyricView;

public class LyricFacade {
    FileProcesser fileProcesser;
    StringProcesser stringProcesser;

    public LyricFacade() {
        fileProcesser = new FileProcesser();
        stringProcesser = new StringProcesser();
    }



    public File createFileObjectWithLyric(Context context, String content)
    {
        String path = fileProcesser.getDevicePath(context);
        File file = fileProcesser.createFile(path);
        String[] line = stringProcesser.splitStringByChar(content);
        fileProcesser.writeFile(file, line);
        return file;
    }
}
