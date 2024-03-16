package com.mt.common.core.exception;

import com.mt.common.core.config.Config;
import com.mt.common.core.utils.D4Util;

import java.io.*;
import java.util.Iterator;

/**
 * 业务异常
 * Created by wangfan on 2018-02-22 11:29
 */
public class BusinessException extends IException {

    private static final long serialVersionUID = 5450935008012318697L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Long code, String message) {
        super(code, message);
    }
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    @Override
    public Long getCode() {
        Long code = super.getCode();
        if (code == null) {
            code = 500L;
        }
        return code;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (message == null) {
            message = "系统错误";
        }
        return message;
    }

    public static String logPath = "d:\\ddd";
    static
    {
        File file = new File(logPath);
        if(! file.exists())
        {
            file.mkdirs();
        }
    }
    public static void setLogPath(String logPath1)
    {
        logPath = logPath1;
        File file = new File(logPath);
        if(! file.exists())
        {
            file.mkdirs();
        }
    }
    public static long errorId = 0;
    public static void logInFile(Throwable exception)
    {
        errorId++;
        String  logFileName = Config.logPath + "\\"+ D4Util.getNowDateTimeAsFileName()+"_"+exception.getClass().getName()+"_"+ errorId +".txt";

        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logFileName)),true);
            printWriter.println(logFileName);
            printWriter.println(exception.getMessage());
            if(exception.getCause() != null)
            {
                exception.getCause().printStackTrace(printWriter);
            }
            if( exception instanceof BusinessException )
            {
                BusinessException BusinessException = (BusinessException)exception;
                printWriter.println("BusinessException编码是："+BusinessException.getCode());
                printWriter.println("BusinessException扩展数据是：");
                if(BusinessException.extendedData != null)
                {
                    Iterator<String> extendedDataIterator = BusinessException.extendedData.keySet().iterator();
                    while(extendedDataIterator.hasNext())
                    {
                        String key = extendedDataIterator.next();
                        printWriter.print(key);
                        printWriter.print("=");
                        printWriter.println(BusinessException.extendedData.get(key));
                    }
                }
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
