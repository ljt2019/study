package com.tiger.springboot.multiple.data.sources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultipleDataSourcesApplicationTests {

//    @Test
//    public void contextLoads() {
//        System.out.println("=====dataSource=======【" + dataSource.getClass() + "】");
//    }

//    @Qualifier("db2JdbcTemplate")// 或者直接写对应的名称
//    @Autowired
    @Resource(name="db1JdbcTemplate")
    JdbcTemplate dbJdbcTemplate;

//    @Autowired  //使用排除默认数据源时，会报错
//    DataSource dataSource;

    @Test
    public void addDataData() {

        String sql = "insert into user_info(name,age) values('tiger',22)";
        dbJdbcTemplate.execute(sql);
    }

}
