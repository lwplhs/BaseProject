package com.lwp.website.thymeleaf;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.lwp.website.utils.DictUtil;
import com.lwp.website.utils.StringUtil;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressionParser;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/10/15:50
 * @Description:
 */
public class RadioImpl extends AbstractElementTagProcessor {

    private static final String TAG_NAME = "radio";
    private static final int PRECEDENCE = 1000;//优先级

    public RadioImpl(final String dialectPrefix) {
        super(TemplateMode.HTML,// 此处理器将仅应用于HTML模式
                dialectPrefix,// 要应用于名称的匹配前缀
                TAG_NAME,// 标签名称：匹配此名称的特定标签
                true,// 没有应用于标签名的前缀
                null,// 无属性名称：将通过标签名称匹配
                false,// 没有要应用于属性名称的前缀
                PRECEDENCE// 优先(内部方言自己的优先
        );
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler) {
        //select 的 option
        List<String> radios = new ArrayList<>();
        //2、根据字典类型获取数据,有过value有值，则默认选中
        String radioHtml = getDictDateByType(tag, context);
        radios.add(radioHtml);
        //创建模型
        IModelFactory modelFactory = context.getModelFactory();
        IModel model = modelFactory.createModel();
        //添加模型元素
        IOpenElementTag openElementTag = addModelElement(tag, modelFactory);
        model.add(openElementTag);

        model.add(modelFactory.createText(HtmlEscape.unescapeHtml(String.join("\n\t", radios))));
        model.add(modelFactory.createCloseElementTag("div"));
        //替换前面的标签
        structureHandler.replaceWith(model, false);
    }

    /**
     * 添加模型元素（默认）
     * @param tag
     * @param modelFactory
     * @return
     * */
    private IOpenElementTag addModelElement(IProcessableElementTag tag,IModelFactory modelFactory){

        String classValue = tag.getAttributeValue("class");
        String id = tag.getAttributeValue("id");
        String name = tag.getAttributeValue("name");
        String style = tag.getAttributeValue("style");
        String disabled = tag.getAttributeValue("disabled");
        String readonly = tag.getAttributeValue("readonly");
        IOpenElementTag openElementTag = modelFactory.createOpenElementTag("div","class",classValue);
        if(StrUtil.isNotEmpty(id)){
            openElementTag = modelFactory.setAttribute(openElementTag,"id",id);
        }
        if(StrUtil.isNotEmpty(name)){
            openElementTag = modelFactory.setAttribute(openElementTag,"name",name);
        }
        if(StrUtil.isNotEmpty(style)){
            openElementTag = modelFactory.setAttribute(openElementTag,"style",style);
        }
        if(StrUtil.isNotEmpty(disabled)){
            openElementTag = modelFactory.setAttribute(openElementTag,"disabled",disabled);
        }
        if(StrUtil.isNotEmpty(readonly)){
            openElementTag = modelFactory.setAttribute(openElementTag,"readonly",readonly);
        }
        return openElementTag;
    }

    /**
     * 根据字典类型获取数据
     * @param tag
     * @param context
     * @return
     */
    private String getDictDateByType(IProcessableElementTag tag,ITemplateContext context){

        //获取类型
        String dictType = tag.getAttributeValue("dictType");
        if(StrUtil.isEmpty(dictType)){
            return "";
        }
        String name = tag.getAttributeValue("radioName");
        String value = tag.getAttributeValue("th:value");
        Object executeExpression = null;
        if(StrUtil.isNotEmpty(value)){
            executeExpression = executeExpression(value,context);
        }
        if(StringUtil.isNull(executeExpression)) {
            String defaultValue = tag.getAttributeValue("defaultValue");
            executeExpression = defaultValue;
        }
        //获取 选项的值
        List<Map<String,Object>> dicts = DictUtil.getDictByType(dictType);
        if(CollectionUtil.isEmpty(dicts)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dicts.size(); i++) {

            Map<String,Object> map = dicts.get(i);
            if(executeExpression != null && executeExpression.toString().equals(map.get("key"))){
                //<input type="radio" name="status" th:value="0" title="是" checked="checked"/>
                sb.append("<input type=\"radio\" name='"+name+"' value='"+map.get("key") + "' title='"+map.get("value")+"' checked=\"checked\" >");
            }else {
                sb.append("<input type=\"radio\" name='"+name+"' value='"+map.get("key") + "' title='"+map.get("value")+"' >");
            }
            sb.append("</input>");
        }
        return sb.toString();

    }


    /**
     * 执行自定义标签中的表达式 thymeleaf中的表达式
     * @param value
     * @param context
     * @return
     */
    private Object executeExpression(String value, ITemplateContext context){
        StandardExpressionParser parser = new StandardExpressionParser();
        Expression parseExpression = parser.parseExpression(context,value);
        Object execute = parseExpression.execute(context);
        return execute;
    }
}
