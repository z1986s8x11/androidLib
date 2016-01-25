package zsx.com.test.ui.test;


import com.zsx.apt.database.Lib_SQL;
import com.zsx.apt.database.SQLFieldEnum;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/1/25.15:33
 */
public class Entity {
    @Lib_SQL(_type = SQLFieldEnum.String)
    public String a;
}
