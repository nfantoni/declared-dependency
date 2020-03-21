package com.github.nfantoni.dependency.mojo.list;

import com.github.nfantoni.dependency.tools.Functions;
import com.google.inject.Inject;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.List;
import java.util.Map;

@Mojo(name = "list")
public class DependencyList extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;

    @Parameter(defaultValue = "#{groupId}:#{artifactId}:#{version}:#{scope}", property = "declared-dependency.list.expression")
    private String expression;

    @Parameter(defaultValue = "compile", property = "declared-dependency.list.includeScope")
    private String includeScope;

    public DependencyList(String expression, String includeScope, MavenProject mavenProject) {

        this.mavenProject= mavenProject;
        this.expression = expression;
        this.includeScope = includeScope;
    }

    @Inject
    public DependencyList() {}

    @Override
    public void execute() throws MojoExecutionException {

        Map<String,String> keyList = Functions.decodeExpression(expression);

        List<Dependency> dependencyList = mavenProject.getDependencies();
        String dependencyOut = Functions.formatDependencyList(dependencyList, expression, includeScope,  keyList);

        System.out.println(dependencyOut);

    }
}
