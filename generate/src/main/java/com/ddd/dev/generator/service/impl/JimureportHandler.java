package com.ddd.dev.generator.service.impl;

import com.mt.common.core.config.Config;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JimureportHandler {

    public static void clean() {
        List<File> fileList = (List<File>) FileUtils.listFiles(new File(Config.workspapce+"\\erp-boot"), null, true);

        fileList.stream().forEach(file -> {
            try {
                cleanFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
    private static void  cleanFile(File file) throws IOException {
            if(StringUtils.endsWith(file.getName(),"pom.xml"))
        {
            String content  = FileUtils.readFileToString(file, "UTF-8");
            if(StringUtils.contains(content,"org.jeecgframework.jimureport"))
            {
                List<String> lines  = FileUtils.readLines(file, "UTF-8");
                for(int i=0;i<lines.size();i++)
                {
                    if(StringUtils.contains(lines.get(i),"org.jeecgframework.jimureport")){
                        if(!StringUtils.startsWith(lines.get(i),"<!--"))
                        {
                            for(int j=-2;j<=1;j++)
                            {
                                lines.set(i+j,"<!--"+lines.get(i+j)+"-->");
                            }
                        }
                    }
                }
                FileUtils.writeLines(file,lines);
            }
        }

    }


    public static void set() {
        List<File> fileList = (List<File>) FileUtils.listFiles(new File(Config.workspapce+"\\erp-boot"), null, true);

        fileList.stream().forEach(file -> {
            try {
                setFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private static void  setFile(File file) throws IOException {

        if(StringUtils.endsWith(file.getName(),"pom.xml"))
        {
            String content  = FileUtils.readFileToString(file, "UTF-8");
            if(StringUtils.contains(content,"org.jeecgframework.jimureport"))
            {
                List<String> lines  = FileUtils.readLines(file, "UTF-8");
                for(int i=0;i<lines.size();i++)
                {
                    if(StringUtils.contains(lines.get(i),"org.jeecgframework.jimureport")){
                        if(StringUtils.startsWith(lines.get(i),"<!--"))
                        {
                            for(int j=-2;j<=1;j++)
                            {
                                lines.set(i+j,StringUtils.removeEnd(StringUtils.removeStart(lines.get(i+j),"<!--"),"-->"));
                            }
                        }
                    }
                }
                FileUtils.writeLines(file,lines);
            }

        }

    }
}
