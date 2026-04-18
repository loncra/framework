package io.github.loncra.framework.commons.exception;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 系统异常基类
 *
 * @author maurice
 */
public class SystemException extends RuntimeException {

    public final static Logger LOGGER = LoggerFactory.getLogger(SystemException.class);

    @Serial
    private static final long serialVersionUID = 1464832006579973342L;

    /**
     * 系统异常
     */
    public SystemException() {
        super();
    }

    /**
     * 系统异常
     *
     * @param message 异常信息
     */
    public SystemException(String message) {
        super(message);
    }

    /**
     * 系统异常
     *
     * @param message 异常信息
     * @param cause   异常类
     *
     * @since 1.4
     */
    public SystemException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }

    /**
     * 系统异常
     *
     * @param cause 异常类
     *
     * @since 1.4
     */
    public SystemException(Throwable cause) {
        super(cause);
    }

    /**
     * 断言表达式为 true，否则抛出系统异常
     *
     * @param expression 表达式结果
     * @param message    异常信息
     */
    public static void isTrue(
            boolean expression,
            String message
    ) {
        if (!expression) {
            throw new SystemException(message);
        }
    }

    /**
     * 断言表达式为 true，否则抛出系统异常
     *
     * @param expression 表达式结果
     * @param exception  异常提供者
     */
    public static void isTrue(
            boolean expression,
            Supplier<? extends SystemException> exception
    ) {
        if (!expression) {
            throw exception.get();
        }
    }

    /**
     * 将可能抛出异常的 Runnable 转换为运行时异常
     *
     * @param runnable 可执行的任务
     * @param function 异常转换函数
     */
    public static void convertRunnable(
            RunnableWithException runnable,
            Function<Exception, ? extends RuntimeException> function
    ) {

        try {
            runnable.run();
        }
        catch (Exception e) {
            if (Objects.isNull(function)) {
                LOGGER.warn("convertRunnable 执行时出现错误，返回 null 值", e);
                return;
            }
            throw function.apply(e);
        }
    }

    /**
     * 将可能抛出异常的 Supplier 转换为运行时异常
     *
     * @param supplier 值提供者
     * @param <T>      返回值类型
     *
     * @return 提供的值
     */
    public static <T> T convertSupplier(SupplierWithException<T> supplier) {
        return convertSupplier(supplier, (String) null);
    }

    /**
     * 将可能抛出异常的 Runnable 转换为运行时异常
     *
     * @param runnable 可执行的任务
     * @param message  异常信息
     */
    public static void convertRunnable(
            RunnableWithException runnable,
            String message
    ) {

        try {
            runnable.run();
        }
        catch (Exception e) {
            if (Objects.isNull(message)) {
                LOGGER.warn("convertRunnable 执行时出现错误，返回 null 值", e);
                return;
            }

            if (e instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            else if (StringUtils.isEmpty(message)) {
                throw new SystemException(e);
            }

            throw new SystemException(message, e);
        }
    }

    /**
     * 将可能抛出异常的 Supplier 转换为运行时异常
     *
     * @param supplier 值提供者
     * @param function 异常转换函数
     * @param <T>      返回值类型
     *
     * @return 提供的值，如果出现异常且转换函数返回 null 则返回 null
     */
    public static <T> T convertSupplier(
            SupplierWithException<T> supplier,
            Function<Exception, ? extends RuntimeException> function
    ) {

        try {
            return supplier.get();
        }
        catch (Exception e) {
            if (Objects.isNull(function)) {
                LOGGER.warn("convertSupplier 执行时出现错误，返回 null 值", e);
                return null;
            }

            RuntimeException runtimeException = function.apply(e);
            if (Objects.isNull(runtimeException)) {
                return null;
            }

            throw runtimeException;
        }
    }

    /**
     * 将可能抛出异常的 Supplier 转换为运行时异常
     *
     * @param supplier 值提供者
     * @param message  异常信息
     * @param <T>      返回值类型
     *
     * @return 提供的值，如果出现异常且 message 为 null 则返回 null
     */
    public static <T> T convertSupplier(
            SupplierWithException<T> supplier,
            String message
    ) {
        try {
            return supplier.get();
        }
        catch (Exception e) {
            if (Objects.isNull(message)) {
                LOGGER.warn("convertSupplier 执行时出现错误，返回 null 值", e);
                return null;
            }

            if (e instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            else if (StringUtils.isEmpty(message)) {
                throw new SystemException(e);
            }
            else {
                throw new SystemException(message, e);
            }
        }
    }

    /**
     * 自定义无返回值的函数式接口，允许抛出异常
     *
     * @author maurice.chen
     */
    @FunctionalInterface
    public interface RunnableWithException {
        /**
         * 执行任务，可能抛出异常
         *
         * @throws Exception 执行时可能抛出的异常
         */
        void run() throws Exception;
    }

    /**
     * 自定义有返回值的函数式接口，允许抛出异常
     *
     * @param <T> 返回值类型
     *
     * @author maurice.chen
     */
    @FunctionalInterface
    public interface SupplierWithException<T> {
        /**
         * 获取值，可能抛出异常
         *
         * @return 提供的值
         *
         * @throws Exception 获取值时可能抛出的异常
         */
        T get() throws Exception;
    }
}

