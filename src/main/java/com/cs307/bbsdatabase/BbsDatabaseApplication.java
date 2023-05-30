package com.cs307.bbsdatabase;

import com.cs307.bbsdatabase.Entity.User;
import com.cs307.bbsdatabase.Service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@MapperScan("com.cs307.bbsdatabase.Mapper")
public class BbsDatabaseApplication {
/*TODO:
        1. 先写好 UserController.java
        2. 再写好 PostController.java
        3. 再洒洒水写点其他数据库基本操作

  注意：我自己设置的User类中的ID字段存在问题，因为按照project要求ID应当允许存在字符'X'
        所以应当是String类型，而我设置了Integer类型，所以你需要自己改一下
        (这个已经做了
* */
    public static void main(String[] args) {
        SpringApplication.run(BbsDatabaseApplication.class, args);

    }
}
