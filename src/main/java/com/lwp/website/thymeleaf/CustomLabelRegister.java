package com.lwp.website.thymeleaf;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * 新建自定义标签注册类
 * @Auther: liweipeng
 * @Date: 2020/09/28/12:01
 * @Description:
 */
@Component
public class CustomLabelRegister extends AbstractProcessorDialect {

    /**
     * 定义方言名称
     */

    private static final String NAME = "自定义标签";
    /**
     * 定义方言属性
     */
    private static final String PREFIX = "ll";


    protected CustomLabelRegister() {
        super(NAME,PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String s) {
        final Set<IProcessor> processor = new HashSet();
        processor.add(new ComboImpl(PREFIX));
        processor.add(new RadioImpl(PREFIX));
        return processor;
    }
}
