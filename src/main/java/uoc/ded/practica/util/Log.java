package uoc.ded.practica.util;

public class Log {
    public static void d(String tag, Object... params) {
        System.out.println("[" + tag + "]" + ": " + buildMessage(params));
    }

    private static String buildMessage(Object... params) {
        if (params.length == 0) {
            return "";
        } else {
            StringBuilder buffer = new StringBuilder();
            for (Object obj : params) {
                buffer.append(obj.toString());
            }
            return buffer.toString();
        }
    }
}
