package ru.itis.hw4;

import com.google.auto.service.AutoService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@AutoService(Processor.class)
@SupportedAnnotationTypes("ru.itis.hw4.HtmlForm")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HtmlFormProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements =  roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        Set<? extends Element> annotatedFields =  roundEnv.getElementsAnnotatedWith(HtmlInput.class);

        for (Element element : annotatedElements) {
            String path = HtmlFormProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = path.substring(1) + element.getSimpleName().toString() + ".html";
            Path out = Paths.get(path);

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(out.toFile()));
                HtmlForm annotation = element.getAnnotation(HtmlForm.class);
                Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
                cfg.setDirectoryForTemplateLoading(
                        new File(HtmlFormProcessor.class
                                .getClassLoader()
                                .getResource("template/user.ftlh")
                                .getPath())
                                .getParentFile());
                Map<String, Object> model = new HashMap<>();
                model.put("form", new HtmlFormObj(annotation));
                model.put("fields", annotatedFields.stream().map(a -> new HtmlInputObj(a.getAnnotation(HtmlInput.class))).collect(Collectors.toList()));
                Template template = cfg.getTemplate("user.ftlh");
                template.process(model, writer);
                writer.flush();
                writer.close();
            } catch (IOException | TemplateException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return true;
    }
}
