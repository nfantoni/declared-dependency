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
public class MojoList extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;

    @Parameter(defaultValue = "#{groupId}#:#{artifactId}#:#{version}#:#{scope}#", property = "declared-dependency.list.outputFormat")
    private String outputFormat;

    @Parameter(defaultValue = "compile", property = "declared-dependency.list.scopes")
    private String scopes;

    public MojoList(String outputFormat, String scopes, MavenProject mavenProject) {

        this.mavenProject= mavenProject;
        this.outputFormat = outputFormat;
        this.scopes = scopes;
    }

    @Inject
    public MojoList() {}

    @Override
    public void execute() throws MojoExecutionException {

        Map<String,String> keyList = Functions.decodeExpression(outputFormat);

        List<Dependency> dependencyList = mavenProject.getDependencies();
        String dependencyOut = Functions.formatDependencyList(dependencyList, outputFormat, scopes,  keyList);

        System.out.println(dependencyOut);

    }
}
