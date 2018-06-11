import com.appdynamics.instrumentation.sdk.Rule;

import java.util.Arrays;
import java.util.List;

public class TaskNameInterceptor extends AbstractKafkaStreamsInterceptor {

    private static final String PROCESS_NAME = "org.apache.kafka.streams.processor.internals.AbstractProcessorContext";
    private static final String PROCESS = "currentNode";
    private static final String OBJECT = "java.lang.Object";

    //private ThreadLocal threadLocal = new ThreadLocal();

    @Override
    public Object onMethodBegin(Object o, String s, String s1, Object[] objects) {

//        String hello = threadLocal.get().toString();
//        if(hello.equals("set")) {
//
//            System.out.println(sourceSinkTopics.get().toString());
//
//            Object currentNode = super.getFieldValue(o, "currentNode");
//
//            if(currentNode != null) {
//                String name = (String) super.justInvoke(currentNode, "name");
//                System.out.println("Current node name : " + name);
//
////                List<Object> children = (List<Object>) super.getFieldValue(currentNode, "children");
////                for (Object child : children) {
////                    String childName = (String) super.justInvoke(child, "name");
////                    System.out.println("childName : " + childName);
////                }
//
//                System.out.println("\n------------------------------------------------------------------------\n\n");
//            }
//
////            taskName.set(name);
//        }

        return null;
    }

    @Override
    public void onMethodEnd(Object o, Object o1, String s, String s1, Object[] objects, Throwable throwable, Object o2) {

    }

    @Override
    public List<Rule> initializeRules() {
        return Arrays.asList(
                new Rule.Builder(PROCESS_NAME)
                        .methodMatchString(PROCESS)
                        .build()
        );
    }
}
