//package com.example.config;
//
///**
// * @Author: liujinhui
// * @Date: 2019/9/25 12:38
// */
//import com.example.annotation.FieldLog;
//import com.example.vo.Student;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import javassist.expr.Instanceof;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.aspectj.lang.reflect.SourceLocation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//
//@Component
//@Aspect
//public class LogAspect {
//
//    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
//    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
//
//    private final String POINT_CUT = "execution(public * com.example.controller.*.*(..))";
//
//    @Pointcut(POINT_CUT)
//    public void pointCut() {
//    }
//
//    public static Object getValue(Object object, String fieldName){
//        if (object == null) {
//            return null;
//        }
//        if (StringUtils.isEmpty(fieldName)) {
//            return null;
//        }
//        Field field = null;
//        Class<?> clazz = object.getClass();
//        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
//            try {
//                field = clazz.getDeclaredField(fieldName);
//                field.setAccessible(true);
//                return field.get(object);
//            } catch (Exception e) {
//                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
//                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
//                logger.error("出错了", e);
//            }
//        }
//        return null;
//    }
//
//    @Before(value = "pointCut()")
//    public void before(JoinPoint joinPoint) {
//        logger.info("@Before通知执行开始");
//        //获取目标方法参数信息
//        Object[] args = joinPoint.getArgs();
//        getFieldDesc(args);
//        logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Method targetMethod = methodSignature.getMethod();
//        getParameterAnnotations(targetMethod);
//        logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//
//        logger.info("++++++++++++++++++++++++++++++++");
//        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
//            for ( int i = 0; i < joinPoint.getArgs().length; i++) {
//                logger.info(String.valueOf(joinPoint.getArgs()[i]));
//            }
//        }
//        logger.info("++++++++++++++++++++++++++++++++");
//        //aop代理对象
//        Object aThis = joinPoint.getThis();
//        logger.info(aThis.toString()); //com.xhx.springboot.controller.HelloController@69fbbcdd
//
//        //被代理对象
//        Object target = joinPoint.getTarget();
//        logger.info(target.toString()); //com.xhx.springboot.controller.HelloController@69fbbcdd
//
//        //获取连接点的方法签名对象
//        Signature signature = joinPoint.getSignature();
//        logger.info(signature.toLongString()); //public java.lang.String com.xhx.springboot.controller.HelloController.getName(java.lang.String)
//        logger.info(signature.toShortString()); //HelloController.getName(..)
//        logger.info(signature.toString()); //String com.xhx.springboot.controller.HelloController.getName(String)
//        logger.info("==============================");
//        //获取方法名
//        logger.info(signature.getName()); //getName
//        //获取声明类型名
//        logger.info(signature.getDeclaringTypeName()); //com.xhx.springboot.controller.HelloController
//        //获取声明类型  方法所在类的class对象
//        logger.info(signature.getDeclaringType().toString()); //class com.xhx.springboot.controller.HelloController
//        //和getDeclaringTypeName()一样
//        logger.info(signature.getDeclaringType().getName());//com.xhx.springboot.controller.HelloController
//        logger.info("-------------------------");
//        //连接点类型
//        String kind = joinPoint.getKind();
//        logger.info(kind);//method-execution
//
//        //返回连接点方法所在类文件中的位置  打印报异常
//        SourceLocation sourceLocation = joinPoint.getSourceLocation();
//        logger.info(sourceLocation.toString());
//        //logger.info(sourceLocation.getFileName());
//        //logger.info(sourceLocation.getLine()+"");
//        //logger.info(sourceLocation.getWithinType().toString()); //class com.xhx.springboot.controller.HelloController
//
//        ///返回连接点静态部分
//        JoinPoint.StaticPart staticPart = joinPoint.getStaticPart();
//        logger.info(staticPart.toLongString());  //execution(public java.lang.String com.xhx.springboot.controller.HelloController.getName(java.lang.String))
//
//
//        //attributes可以获取request信息 session信息等
//        ServletRequestAttributes attributes =
//                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        logger.info(request.getRequestURL().toString()); //http://127.0.0.1:8080/hello/getName
//        logger.info(request.getRemoteAddr()); //127.0.0.1
//        logger.info(request.getMethod()); //GET
//
//        logger.info("@Before通知执行结束。。。");
//
//    }
//
//    /**
//     *  获取切面的方法参数的的描述
//     */
//    private void getParameterAnnotations(Method method){
//        //获取方法参数注解
//        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
//        for (Annotation[] annotations : parameterAnnotations) {
//            for (Annotation annotation : annotations) {
//                //获取注解名
//                String name = annotation.annotationType().getSimpleName();
//                try {
//                    Class<?> aClass = Class.forName(name);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//                logger.info("parmameter:{}", name);
//            }
//        }
//    }
//
//    /**
//     * 获取字段上注解FieldLog的值
//     * @param args
//     */
//    private void getFieldDesc(Object[] args) {
//        Arrays.stream(args).forEach(arg -> {  // 大大
//            // controller类中的方法中的参数，这个参数可能是简单类型，可能是复杂类型，有可能是没有参数，这个需要根据实际的情况做不同的判断
//            Class<?> aClass = arg.getClass();
//
//            // 参数arg0的类型中定义的字段
//            Field[] declaredFields = aClass.getDeclaredFields();
//            for (Field field : declaredFields){
//                FieldLog annotation  = field.getAnnotation(FieldLog.class);
//                if(annotation != null) {
//                    String value = annotation.value(); // 获取传入参数对象， 获取对象中方法中有FieldLog注解的方法
//                    logger.info(String.format("----------注解的描述:%s------------!!!!!!!!!!!!!!!!", value));
//                    Object o = null;
//                    try {
//                        field.setAccessible(true); //设置些属性是可以访问的
//                        o = field.get(arg); //得到此属性的值
//                    } catch (IllegalAccessException e) {
//                        logger.error("抛出异常...", e);
//                    }
//                    logger.info(String.format("----------有注解，调用该方法的值为：:%s------------!!!!!!!!!!!!!!!!", o));
//                }
//            }
//            logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            try {
//                logger.info(OBJECT_MAPPER.writeValueAsString(arg));
//            } catch (JsonProcessingException e) {
//                logger.info(arg.toString());
//            }
//            logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        });
//    }
//
//
//    /**
//     * 后置返回
//     * 如果第一个参数为JoinPoint，则第二个参数为返回值的信息
//     * 如果第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
//     * returning：限定了只有目标方法返回值与通知方法参数类型匹配时才能执行后置返回通知，否则不执行，
//     * 参数为Object类型将匹配任何目标返回值
//     */
//    @AfterReturning(value = POINT_CUT, returning = "result")
//    public void doAfterReturningAdvice1(JoinPoint joinPoint, Object result) {
//        logger.info("@AfterReturning开始了。。。。");
//        logger.info("@AfterReturning第一个后置返回通知的返回值：" + result);
//        if( result instanceof Student) {
//            Student student = (Student)result;
//            student.setId("aaaa");
//            logger.info("{}", student);
//        }
//        logger.info("@AfterReturning结束。。。。");
//    }
//
//    @AfterReturning(value = POINT_CUT, returning = "result", argNames = "result")
//    public void doAfterReturningAdvice2(String result) {
//        logger.info("@AfterReturning开始，第二个后置返回通知的返回值：" + result);
//    }
//    //第一个后置返回通知的返回值：姓名是大大
//    //第二个后置返回通知的返回值：姓名是大大
//    //第一个后置返回通知的返回值：{name=小小, id=1}
//
//
//    /**
//     * 后置异常通知
//     * 定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
//     * throwing:限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
//     * 对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。
//     *
//     * @param joinPoint
//     * @param exception
//     */
//    @AfterThrowing(value = POINT_CUT, throwing = "exception")
//    public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
//        logger.info("@AfterThrowing开始。。。。。");
//        logger.info(joinPoint.getSignature().getName());
//        if (exception instanceof RuntimeException) {
//            logger.info("@AfterThrowing执行了，发生了空指针异常!!!!!");
//        }
//    }
//
//    @After(value = POINT_CUT)
//    public void doAfterAdvice(JoinPoint joinPoint) {
//        logger.info("@After后置通知执行开始。。。");
//    }
//
//    /**
//     * 环绕通知：
//     * 注意:Spring AOP的环绕通知会影响到AfterThrowing通知的运行,不要同时使用
//     * <p>
//     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
//     * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
//     */
//    @Around(value = POINT_CUT)
//    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
//        logger.info("@Around环绕通知开始: " + proceedingJoinPoint.getSignature().toString());
//        Object obj = null;
//        // Object[] objects = new Object[1];
//        // objects[0]="10";
//        try {
//            //    obj = proceedingJoinPoint.proceed(objects); //可以加参数,这样就把入参给改了
//            obj = proceedingJoinPoint.proceed();
//            logger.info("@Around环绕通知结束111111: ============================================================");
//            logger.info(obj.toString());
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//
//        logger.info("@Around环绕通知执行结束222222");
//        if (1 == 1 ) {
////            throw new RuntimeException("123Exception");
//        }
//        return obj;
//    }
//}