package com.github.nfantoni.dependency.tools;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.MojoExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

    private Functions() {}

    public static Map<String,String> decodeExpression(String expression){
        Pattern pattern = Pattern.compile(Constants.OUTPUT_FORMAT_PATTRN);
        Matcher matcher = pattern.matcher(expression);
        Map<String,String> keyList = new HashMap<>();

        while (matcher.find()) {
            String key = matcher.group(0);
            keyList.put(key,key.replace("#{", "").replace("}#", ""));
        }
        return keyList;
    }

    public static String formatDependencyList(List<Dependency> dependencyList,
                                              String expression, String includeScope,
                                              Map<String,String> keyList) throws MojoExecutionException {
        StringBuilder returnValue = new StringBuilder();
        for(Dependency item : dependencyList){
            if(checkScope(includeScope, item.getScope())){
                String appender = expression;
                for (Map.Entry<String, String> entry : keyList.entrySet()) {
                    try {
                        String value =  (String) PropertyUtils.getProperty(item, entry.getValue());
                        appender = appender.replace(entry.getKey(), value);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new MojoExecutionException("Error ", e);
                    }
                }
                returnValue.append(appender).append(System.lineSeparator());
            }

        }

        return returnValue.toString().replaceAll("([\\n\\r]+\\s*)*$", "");
    }

    public static boolean checkScope(String includeScope, String itemScope){
        if(includeScope.equals("all"))
            return true;
        else
            return includeScope.contains(itemScope);
    }
}
