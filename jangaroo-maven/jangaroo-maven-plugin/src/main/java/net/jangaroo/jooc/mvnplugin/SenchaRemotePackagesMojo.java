/*
 * Copyright (c) 2009, CoreMedia AG, Hamburg. All rights reserved.
 */
package net.jangaroo.jooc.mvnplugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jangaroo.jooc.mvnplugin.sencha.SenchaUtils;
import net.jangaroo.jooc.mvnplugin.sencha.configurer.PackagesConfigurer;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.archiver.zip.ZipEntry;
import org.codehaus.plexus.archiver.zip.ZipFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

/**
 * Mojo to compile properties files to ActionScript3 files
 *
 * @goal extract-remote-packages
 * @phase process-resources
 * @requiresDependencyResolution
 * @threadSafe
 */
@SuppressWarnings({"ResultOfMethodCallIgnored", "UnusedDeclaration", "UnusedPrivateField"})
public class SenchaRemotePackagesMojo extends AbstractMojo {

  /**
   * The maven project.
   *
   * @parameter expression="${project}"
   * @required
   * @readonly
   */
  private MavenProject project;

  public void execute() throws MojoExecutionException, MojoFailureException {

    File workspaceDir = SenchaUtils.findClosestSenchaWorkspaceDir(project.getBasedir());
    if (null == workspaceDir) {
      throw new MojoExecutionException("could not find sencha workspace directory");
    }

    File workspaceFile = new File(workspaceDir.getAbsolutePath() + File.separator + SenchaUtils.SENCHA_WORKSPACE_FILENAME);

    ObjectMapper objectMapper = SenchaUtils.getObjectMapper();
    String remotePackagesDir = null;
    try {
      @SuppressWarnings("unchecked") Map<String, Object> workspaceConfig = (Map<String, Object>) objectMapper.readValue(workspaceFile, Map.class);
      Object oPackages = workspaceConfig.get(PackagesConfigurer.PACKAGES);
      if (oPackages instanceof Map) {
        @SuppressWarnings("unchecked") Map<String, Object> packagesConfig = (Map<String, Object>) oPackages;
        Object oExtract = packagesConfig.get(PackagesConfigurer.EXTRACT);
        if (oExtract instanceof String) {
          String extract = (String) oExtract;
          remotePackagesDir = extract.replace(SenchaUtils.PLACEHOLDERS.get(SenchaConfiguration.Type.WORKSPACE), workspaceDir.getAbsolutePath());
        }
      }
    } catch (IOException e) {
      throw new MojoExecutionException("could not read " + SenchaUtils.SENCHA_WORKSPACE_FILENAME, e);
    }
    if (StringUtils.isEmpty(remotePackagesDir)) {
      throw new MojoExecutionException("could not determine remote packages directory");
    }

    SenchaUtils.extractRemotePackagesForProject(project, remotePackagesDir);
  }

}