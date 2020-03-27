package whling.java.agent.hotdeploy2;

import java.time.LocalDateTime;

/**
 * 每次更改之后重新构建，idea build or maven package
 */
public class CatService implements BaseService {

    @Override
    public void say() {
        System.out.println(LocalDateTime.now() + " miao miao miao");
//        System.out.println(LocalDateTime.now() + " 喵 喵 喵");
    }
}
