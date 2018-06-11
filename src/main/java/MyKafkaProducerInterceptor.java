import com.appdynamics.instrumentation.sdk.Rule;
import com.appdynamics.instrumentation.sdk.SDKClassMatchType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MyKafkaProducerInterceptor extends AbstractKafkaStreamsInterceptor {

    private static final String PROCESS_NAME = "org.apache.kafka.streams.processor.ProcessorContext";
    private static final String PROCESS = "forward";
    private static final String OBJECT = "java.lang.Object";

    public HashMap<String, Integer> transformationMap = new HashMap<>();

    private void incrementProcessorCount(String processor) {
        if(!transformationMap.containsKey(processor))
            transformationMap.put(processor, 0);

        int value = transformationMap.get(processor);
        transformationMap.put(processor, ++value);
        System.out.println(processor + value);
    }

    private String getProcessorName(String processorName) {

        int i;
        for(i = 0; i < processorName.length(); i++) {
            if(processorName.charAt(i) >= '0' && processorName.charAt(i) <= '9')
                break;
        }

        for(int j = 0; j < 10; j++)
            System.out.println(j);

        return processorName.substring(0, i);
    }

    @Override
    public Object onMethodBegin(Object invokedObject, String className, String methodName, Object[] paramValues) {

//        System.out.println("****** onMethodBegin SourceInterceptor ******");
//        System.out.println("Class Name : " + className + " Method Name : " + methodName);


        Object task = super.getFieldValue(invokedObject, "task");
        Object topology = super.justInvoke(task, "topology");
        Set<String> sourceTopics = (Set<String>) super.justInvoke(topology, "sourceTopics");
        Set<String> sinkTopics = (Set<String>) super.justInvoke(topology, "sinkTopics");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\nsourceTopics : ")
                .append(sourceTopics.toString())
                .append("\nsinkTopics : ")
                .append(sinkTopics.toString());

        Object processorNode = super.justInvoke(invokedObject, "currentNode");
        String processorName = (String) super.justInvoke(processorNode, "name");

        System.out.println(stringBuilder.toString());
        System.out.println("Current Processor Name : " + processorName);
        System.out.println("------------------------------------------------------\n");

        //        sourceSinkTopics.set(stringBuilder.toString());
//        threadLocal.set("set");

        incrementProcessorCount(getProcessorName(processorName));

        if(transformationMap.get("KSTREAM-SOURCE-") > 50)
            System.out.println(transformationMap.toString());

        return null;
    }

    @Override
    public void onMethodEnd(Object state, Object invokedObject, String className, String methodName, Object[] paramValues, Throwable thrownException, Object returnValue) {

//        System.out.println("XXXXXXX onMethodEnd Source XXXXXXX");
//        System.out.println("Class Name : " + className + " Method Name : " + methodName);

    }

    @Override
    public List<Rule> initializeRules() {

        return Arrays.asList(
                new Rule.Builder(PROCESS_NAME)
                        .methodMatchString(PROCESS)
                        .classMatchType(SDKClassMatchType.IMPLEMENTS_INTERFACE)
                        .withParams(OBJECT, OBJECT)
                        .build()
        );
    }
}
