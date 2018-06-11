import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static String getProcessorName(String processorName) {

        int i;
        for(i = 0; i < processorName.length(); i++) {
            if(processorName.charAt(i) >= '0' && processorName.charAt(i) <= '9')
                break;
        }

        return processorName.substring(0, i);
    }

    public static void main(String... args) {

        System.out.println(getProcessorName("KSTREAM-FILTER-0000000006"));

    }

}
