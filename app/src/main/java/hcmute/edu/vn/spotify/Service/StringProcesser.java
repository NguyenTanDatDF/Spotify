package hcmute.edu.vn.spotify.Service;

public class StringProcesser {
    String content;
    char c;
    public String[] splitStringByChar(String content)
    {
        return  content.split("\\|");
    }
}
