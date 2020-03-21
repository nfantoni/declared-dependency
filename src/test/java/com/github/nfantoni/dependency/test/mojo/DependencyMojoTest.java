package com.github.nfantoni.dependency.test.mojo;

import com.github.nfantoni.dependency.mojo.list.DependencyList;
import com.github.nfantoni.dependency.utilis.MavenUtils;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.junit.Test;


import java.io.File;

public class DependencyMojoTest extends AbstractMojoTestCase {

    private static final String DEFAULT_EXPRESSION = "#{groupId}:#{artifactId}:#{version}:#{scope}";
private static final String INCLUDE_SCOPE_DEFAULT = "compile";

    @Test
    public void testEvaulateHappyPath() throws Exception {

        try{
            MavenUtils mavenUtils = new MavenUtils();
            MavenProject project = mavenUtils.readMavenProject( new File( "src/test/resources/dependency/list/test-project/" ) );
            DependencyList mojo = new DependencyList(DEFAULT_EXPRESSION,INCLUDE_SCOPE_DEFAULT,project);
            mojo.execute();
        }catch (Exception ex){

        }

    }
}
