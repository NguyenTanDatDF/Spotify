package hcmute.edu.vn.spotify.Service;

public class StringProcesser {
    String content;
    char c;
    // split the string to array of string depend the '|' character
    public String[] splitStringByChar(String content)
    {
        return  content.split("\\|");
    }
}
