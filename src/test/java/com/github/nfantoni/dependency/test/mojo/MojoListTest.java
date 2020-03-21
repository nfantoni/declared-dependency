package com.github.nfantoni.dependency.test.mojo;

import com.github.nfantoni.dependency.mojo.list.MojoList;
import com.github.nfantoni.dependency.utilis.MavenUtils;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.junit.Test;


import java.io.File;

public class MojoListTest extends AbstractMojoTestCase {

    private static final String DEFAULT_EXPRESSION = "#{groupId}:#{artifactId}:#{version}:#{scope}";
private static final String SCOPES_DEFAULT = "compile";

    @Test
    public void testEvaulateHappyPath() {

        try{
            MavenUtils mavenUtils = new MavenUtils();
            MavenProject project = mavenUtils.readMavenProject( new File( "src/test/resources/dependency/list/test-project/" ) );
            MojoList mojo = new MojoList(DEFAULT_EXPRESSION,SCOPES_DEFAULT,project);
            mojo.execute();
        }catch (Exception ex){
            fail("Exception not allowed");
        }

    }
}
