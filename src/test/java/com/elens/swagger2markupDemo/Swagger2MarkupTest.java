package com.elens.swagger2markupDemo;

import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;
import java.nio.file.Paths;

/**
 * @author ChaselX
 * @date 2019/1/29 13:02
 * <p>
 * 完整异常：
 * Unable to find a @SpringBootConfiguration, you need to use @ContextConfiguration or @SpringBootTest(classes=...) with your test
 * 无法找到@SpringBootConfiguration，您需要在测试中使用@ContextConfiguration或@SpringBootTest(classes=…)
 * <p>
 * 问题产生的几种情况：
 * 1.启动类 或者 程序入口类的包名和测试类所在包名不一致
 * 例如：
 * 启动类：com.leyou下的启动类
 * 测试类：com.leyou.test下的测试类，“.test”多余
 * <p>
 * 2.程序入口类忘了添加：SpringBootApplication注解
 * <p>
 * 解决：
 * 包名一致
 * <p>
 * 或者在@SpringBootTest(classes = com.xxx.class)中指定启动类
 * <p>
 * 日志配置，如果用logback.xml，然后application.yml中的指定，则会提示java.lang.IllegalStateException: Failed to load ApplicationContext
 * 要改为logback-spring.xml才能默认加载
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@AutoConfigureRestDocs(outputDir = "build/asciidoc/snippets")
@SpringBootTest
@AutoConfigureMockMvc
public class Swagger2MarkupTest {

    @Autowired
    private MockMvc mockMvc;

    //    @Test
//    public void testApi() throws Exception {
//        mockMvc.perform(get("/greeting")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(document("greetingGet",
//                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
//                .andExpect(status().isOk());
//    }
    @org.junit.jupiter.api.Test
    public void generateAsciiDocs() throws Exception {
        //    输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withGeneratedExamples()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8011/admin/api/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("src/docs/asciidoc/generated/index"));
    }
}
