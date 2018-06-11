import com.appdynamics.instrumentation.sdk.template.AGenericInterceptor;
import com.appdynamics.instrumentation.sdk.toolbox.reflection.IReflectionBuilder;
import com.appdynamics.instrumentation.sdk.toolbox.reflection.ReflectorFactory;

abstract class AbstractKafkaStreamsInterceptor extends AGenericInterceptor {

    private ReflectorFactory reflectorFactory;

    protected static ThreadLocal threadLocal = new ThreadLocal();
    protected static ThreadLocal sourceSinkTopics = new ThreadLocal();

    AbstractKafkaStreamsInterceptor() {
        reflectorFactory = ReflectorFactory.getInstance();
    }

    private IReflectionBuilder get() throws Exception {
        return reflectorFactory.getNewReflectionBuilder();
    }

    private static ClassLoader getLoader(Object object) {
        return object.getClass().getClassLoader();
    }

//    Object invoke(Object object, String method) {
//        try {
//            return get()
//                    .invokeInstanceMethod(method, true)
//                    .build()
//                    .execute(getLoader(object), object);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    Object invokeRecordInfo(Object record, String method) {
        try {
            return get()
                    .invokeInstanceMethod(method, true)
                    .build()
                    .execute(getLoader(record), record);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    Object invokeProcessorNodeInfo(Object processorNode, String method) {
        try {
            return get()
                    .invokeInstanceMethod(method, true)
                    .build()
                    .execute(getLoader(processorNode), processorNode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    Object invoke(Object recordInfo, String method) {
        try {
            IReflectionBuilder processorNode =  get()
                    .invokeInstanceMethod(method, true)
                    .build()
                    .execute(getLoader(recordInfo), recordInfo);

            return get()
                    .invokeInstanceMethod("name", true)
                    .build()
                    .execute(getLoader(processorNode), processorNode);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    Object justInvoke(Object invokedObject, String method) {
        try {
            return get()
                    .invokeInstanceMethod(method, true)
                    .build()
                    .execute(getLoader(invokedObject), invokedObject);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    Object justInvokeParams(Object invokedObject, String method, String... types) {
        try {
            return get()
                    .invokeInstanceMethod(method, true, types)
                    .build()
                    .execute(getLoader(invokedObject), invokedObject);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    Object getFieldValue(Object object, String field) {
        try {
            return get()
                    .accessFieldValue(field, true)
                    .build()
                    .execute(getLoader(object), object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    Object createObject(String className) {
        try {
            return get()
                    .createObject(className);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
