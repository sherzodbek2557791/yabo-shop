package uz.kvikk.yabo;

import org.hashids.Hashids;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateHashids {

    @Autowired
    DSLContext dsl;

    @Test
    public void contextLoads() {
        String salt = "eYKM+jt6N!^LZ#au*?Eav$@YJdhSx=ktEnigma";
        int minHashLength = 4;
        String alphabet = "abcdef0123456789";

        Hashids hashids = new Hashids(salt,
                minHashLength,
                alphabet);
        Table<Record> entity = DSL.table("K_BRANCH");
        List<Long> list = dsl.fetch("select ID from {0}", entity).into(Long.class);

        for (Long id : list) {
            dsl.update(entity)
                    .set(DSL.field("CODE"), hashids.encode(id))
                    .where(DSL.field("ID").eq(id))
            .execute();
        }
    }
}

