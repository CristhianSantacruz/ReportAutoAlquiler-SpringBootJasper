package com.report.springbootjasperreport;

import net.sf.jasperreports.engine.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringBootJasperReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJasperReportApplication.class, args);
    }

    @Bean
    CommandLineRunner init(){
        return args -> {
          String destinationPath = "src" + File.separator +
                                    "main" + File.separator +
                                    "resources" + File.separator  +
                                    "static" +  File.separator +
                                    "ReportAutoAlquiler.pdf";

            String filePath = "src" + File.separator +
                        "main" + File.separator +
                    "resources" + File.separator  +
                    "templates" +  File.separator +
                    "report" + File.separator +
                    "Report.jrxml";

            LocalTime time = LocalTime.of(1,12,45);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");


            Map<String,Object>  paremeters = new HashMap<>();
            paremeters.put("voucher_id","000213213");
            paremeters.put("dni","0944524545");
            paremeters.put("current_date", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(LocalDateTime.now()));
            paremeters.put("AmountPaid", new BigDecimal(540));
            paremeters.put("payMethod","Transferencia");
            paremeters.put("plate","GUE-766");
            paremeters.put("time",formatter.format(time));
            paremeters.put("name","Lucas Reney");
            paremeters.put("imageDir","classpath:/static/images/");

            JasperReport report = JasperCompileManager.compileReport(filePath);
            JasperPrint print = JasperFillManager.fillReport(report,paremeters,new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(print,destinationPath);

            System.out.println("REPORT CREATE SUCCESFULL!!");
        };


    }
}
