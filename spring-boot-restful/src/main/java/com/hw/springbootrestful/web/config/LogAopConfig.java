package com.hw.springbootrestful.web.config;

import com.hw.springbootrestful.utils.IPUtil;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Description AOP日志打印配置
 * @Author hw
 * @Date 2018/11/21 11:50
 * @Version 1.0
 */
@Component  //声明组件
@Aspect //  声明切面
@ComponentScan  //组件自动扫描
@EnableAspectJAutoProxy //spring自动切换JDK动态代理和CGLIB
public class LogAopConfig {
    private Logger logger = LogManager.getLogger(LogAopConfig.class);


    /**
     * 打印类method的名称以及参数
     *
     * @param point 切面
     */
    public void printMethodParams(JoinPoint point) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;

        if (point == null) {
            return;
        }
        /**
         * Signature 包含了方法名、申明类型以及地址等信息
         */
        String class_name = point.getTarget().getClass().getName();
        String method_name = point.getSignature().getName();
        //重新定义日志
        logger = LogManager.getLogger(point.getTarget().getClass());
//        logger.info("class_name = {}", class_name);
//        logger.info("method_name = {}", method_name);

        /**
         * 获取方法的参数值数组。
         */
        Object[] method_args = point.getArgs();

        try {
            /**
             * 获取方法参数名称
             */
            String[] paramNames = getFieldsName(class_name, method_name);

            /**
             * 打印方法的参数名和参数值
             */
            ;
            logger.info("{}\t{}\t{}\t{}\t{}",
                    IPUtil.getRealIP(sra.getRequest()),
                    sra.getRequest().getRequestURI(),
                    method_name,
                    sra.getRequest().getMethod(),
                    logParam(paramNames, method_args));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用javassist来获取方法参数名称
     *
     * @param class_name  类名
     * @param method_name 方法名
     */
    private String[] getFieldsName(String class_name, String method_name) throws Exception {
        Class<?> clazz = Class.forName(class_name);
        String clazz_name = clazz.getName();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(clazz);
        pool.insertClassPath(classPath);

        CtClass ctClass = pool.get(clazz_name);
        CtMethod ctMethod = ctClass.getDeclaredMethod(method_name);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            return null;
        }
        String[] paramsArgsName = new String[ctMethod.getParameterTypes().length];
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramsArgsName.length; i++) {
            paramsArgsName[i] = attr.variableName(i + pos);
        }
        return paramsArgsName;
    }


    /**
     * 判断是否为基本类型：包括String
     *
     * @param clazz clazz
     * @return true：是;     false：不是
     */
    private boolean isPrimite(Class<?> clazz) {
        if (clazz.isPrimitive() || clazz == String.class) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 打印方法参数值  基本类型直接打印，非基本类型需要重写toString方法
     *
     * @param paramsArgsName  方法参数名数组
     * @param paramsArgsValue 方法参数值数组
     */
    private String logParam(String[] paramsArgsName, Object[] paramsArgsValue) {
        if (ArrayUtils.isEmpty(paramsArgsName) || ArrayUtils.isEmpty(paramsArgsValue)) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < paramsArgsName.length; i++) {
            //参数名
            String name = paramsArgsName[i];
            //参数值
            Object value = paramsArgsValue[i];
            buffer.append(name + "=");
            if (isPrimite(value.getClass())) {
                buffer.append(value + ",");
            } else {
                buffer.append(value.toString() + ",");
            }
        }
        return buffer.toString();
    }

    /**
     * <li>Before       : 在方法执行前进行切面</li>
     * <li>execution    : 定义切面表达式</li>
     * <p>public * com.eparty.ccp.*.impl..*.*(..)
     * <li>public :匹配所有目标类的public方法，不写则匹配所有访问权限</li>
     * <li>第一个* :方法返回值类型，*代表所有类型 </li>
     * <li>第二个* :包路径的通配符</li>
     * <li>第三个..* :表示impl这个目录下所有的类，包括子目录的类</li>
     * <li>第四个*(..) : *表示所有任意方法名,..表示任意参数</li>
     * </p>
     *
     * @param point 切面
     */
    @Before("execution(public * com.qm.products.website.web.controller..*.*(..))")
    public void before(JoinPoint point) {
        this.printMethodParams(point);
    }


}
