package com.ddd.dev.generator.service.impl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.config.Config;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.CharSet;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JpaHandler {

    public static void cleanJpa() {
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
//        if(StringUtils.endsWith(file.getName(),"JpaHandler.java") ||
//                StringUtils.contains(file.getName(),"javax.") )
//        {
//            return;
//        }
//        else if(StringUtils.endsWith(file.getName(),".java"))
//        {
//            List<String> lines  = FileUtils.readLines(file, "UTF-8");
//            for(int i=0;i<lines.size();i++)
//            {
//                if(StringUtils.contains(lines.get(i),"javax.persistence"))
//                {
//                    if(! StringUtils.startsWith(lines.get(i),"//"))
//                    {
//                        lines.set(i,"//"+lines.get(i));
//                    }
//                }
//                if(StringUtils.contains(lines.get(i),"@Column") ||
//                        StringUtils.contains(lines.get(i),"@Entity") ||
//                        StringUtils.contains(lines.get(i),"@Table") ||
//                        StringUtils.contains(lines.get(i),"@GeneratedValue") ||
//                        StringUtils.contains(lines.get(i),"@Transient") ||
//                        StringUtils.contains(lines.get(i),"@Index") ||
//                        StringUtils.contains(lines.get(i),"@MappedSuperclass") ||
//                        StringUtils.contains(lines.get(i),"@Id")
//                )
//                {
//                    if(! StringUtils.startsWith(lines.get(i),"//"))
//                    {
//                        lines.set(i,"//"+lines.get(i));
//                    }
//                }
//            }
//            FileUtils.writeLines(file,lines);
//        }
//        else
            if(StringUtils.endsWith(file.getName(),"pom.xml"))
        {
            String content  = FileUtils.readFileToString(file, "UTF-8");
            if(StringUtils.contains(content,"spring-boot-starter-data-jpa"))
            {
                List<String> lines  = FileUtils.readLines(file, "UTF-8");
                for(int i=0;i<lines.size();i++)
                {
                    if(StringUtils.contains(lines.get(i),"spring-boot-starter-data-jpa")){
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
        else if(StringUtils.endsWith(file.getName(),"application.yml"))
        {
            String content  = FileUtils.readFileToString(file, "UTF-8");
            if(StringUtils.contains(content,"jpa"))
            {
                List<String> lines  = FileUtils.readLines(file, "UTF-8");
                for(int i=0;i<lines.size();i++)
                {
                    if(StringUtils.contains(lines.get(i),"jpa")){
                        if(!StringUtils.startsWith(lines.get(i),"#"))
                        {
                            for(int j=0;j<6;j++)
                            {
                                lines.set(i+j,"#"+lines.get(i+j));
                            }
                        }
                    }
                }
                FileUtils.writeLines(file,lines);
            }

        }
    }


    public static void setJpa() {
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
//        if(StringUtils.endsWith(file.getName(),"JpaHandler.java") ||
//                StringUtils.contains(file.getName(),"javax.") )
//        {
//            List<String> lines  = FileUtils.readLines(file, "UTF-8");
//            for(int i=0;i<lines.size();i++)
//            {
//                if(StringUtils.contains(lines.get(i),"javax.persistence"))
//                {
//                    if(StringUtils.startsWith(lines.get(i),"//"))
//                    {
//                        lines.set(i, StringUtils.removeStart(lines.get(i),"//") );
//                    }
//                }
//                if(StringUtils.contains(lines.get(i),"@Column") ||
//                        StringUtils.contains(lines.get(i),"@Entity") ||
//                        StringUtils.contains(lines.get(i),"@Table") ||
//                        StringUtils.contains(lines.get(i),"@GeneratedValue") ||
//                        StringUtils.contains(lines.get(i),"@Transient") ||
//                        StringUtils.contains(lines.get(i),"@Index") ||
//                        StringUtils.contains(lines.get(i),"@MappedSuperclass") ||
//                        StringUtils.contains(lines.get(i),"@Id")
//
//                )
//                {
//                    if(StringUtils.startsWith(lines.get(i),"//"))
//                    {
//                        lines.set(i, StringUtils.removeStart(lines.get(i),"//") );
//
//                    }
//                }
//            }
//            FileUtils.writeLines(file,lines);
//        }
//        else
        if(StringUtils.endsWith(file.getName(),"pom.xml"))
        {
            String content  = FileUtils.readFileToString(file, "UTF-8");
            if(StringUtils.contains(content,"spring-boot-starter-data-jpa"))
            {
                List<String> lines  = FileUtils.readLines(file, "UTF-8");
                for(int i=0;i<lines.size();i++)
                {
                    if(StringUtils.contains(lines.get(i),"spring-boot-starter-data-jpa")){
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
        else if(StringUtils.endsWith(file.getName(),"application.yml"))
        {
            String content  = FileUtils.readFileToString(file, "UTF-8");
            if(StringUtils.contains(content,"jpa"))
            {
                List<String> lines  = FileUtils.readLines(file, "UTF-8");
                for(int i=0;i<lines.size();i++)
                {
                    if(StringUtils.contains(lines.get(i),"jpa")){
                        if(StringUtils.startsWith(lines.get(i),"#"))
                        {
                            for(int j=0;j<6;j++)
                            {
                                lines.set(i+j, StringUtils.removeStart(lines.get(i+j),"#") );
                            }
                        }
                    }
                }
                FileUtils.writeLines(file,lines);
            }

        }
    }
}
