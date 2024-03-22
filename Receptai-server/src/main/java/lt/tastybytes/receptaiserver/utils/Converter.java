package lt.tastybytes.receptaiserver.utils;

import java.util.Optional;
import java.util.regex.Pattern;

public class Converter {

    public static Optional<String> extractVideoIdFromUrl(String youtubeUrl) {
        var pattern = Pattern.compile(".*(?:youtu.be\\/|v\\/|u\\/\\w\\/|embed\\/|watch\\?v=)([^#\\&\\?]*).*");
        var matcher = pattern.matcher(youtubeUrl);
        if (matcher.find()) {
            var res = matcher.group(1);
            if (res.length() == 11) return Optional.of(res);
        }
        return Optional.empty();
    }

}
