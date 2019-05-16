package uz.kvikk.yabo;

import uz.kvikk.yabo.utils.OnlyofficeUtil;

public class Main {
    public static void main(String[] args) throws Exception {
//        http://172.18.33.95/api/v1/fresource/download/e385

        String key  = OnlyofficeUtil.GenerateRevisionId("eYKM+jt6N!^LZ#au*?Eav$@YJdhSx=kt");
        System.out.println(key);
        String url = OnlyofficeUtil.GetConvertedUri("http://172.18.33.95/test.docx", "docx", "pdf", key, false);
        System.out.println(url);
    }

}
