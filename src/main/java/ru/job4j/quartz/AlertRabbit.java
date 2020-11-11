package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlertRabbit {
    public static Properties readProps(String s) {
        Properties props = new Properties();
        try (BufferedReader br = new BufferedReader(
                new FileReader(s))) {
            props.load(br);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props;
    }

    public static Connection getConnection(Properties props) throws SQLException {
        String url = props.getProperty("url");
        String login = props.getProperty("login");
        String password = props.getProperty("password");
        return DriverManager.getConnection(url, login, password);
    }

    public static void init(Connection cn) {
        try (Statement st = cn.createStatement()) {
            String dropSql = "drop table if exists rabbit";
            String createSql = "create table rabbit(id serial primary key, created timestamp)";
            st.execute(dropSql);
            st.execute(createSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            Properties props = readProps("src/main/resources/rabbit.properties");
            Connection connection = getConnection(props);
            init(connection);
            int interval = Integer.parseInt(props.getProperty("rabbit.interval"));
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(interval)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection cn = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            try (PreparedStatement pst = cn.prepareStatement(
                    "insert into rabbit(created) values(?)")) {
                pst.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                pst.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
