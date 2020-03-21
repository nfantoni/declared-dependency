package com.github.nfantoni.dependency.utilis;

import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingRequest;
import org.junit.Test;
import org.sonatype.aether.util.DefaultRepositorySystemSession;


import java.io.File;

public class MavenUtils extends AbstractMojoTestCase {

    public MavenProject readMavenProject(File basedir )
            throws Exception
    {
        File pom = new File( basedir, "pom.xml" );
        MavenExecutionRequest request = new DefaultMavenExecutionRequest();
        request.setBaseDirectory( basedir );
        ProjectBuildingRequest configuration = request.getProjectBuildingRequest();
        configuration.setRepositorySession( new DefaultRepositorySystemSession() );
        return lookup( ProjectBuilder.class ).build( pom, configuration ).getProject();
    }

    @Test
    public void testMavenProjectNotNull() throws Exception {
        MavenProject project = readMavenProject( new File( "src/test/resources/dependency/tools/" ) );

        assertNotNull(project);
    }
}
