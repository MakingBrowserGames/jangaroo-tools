package net.jangaroo.exml.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ConfigClass extends DescriptionHolder {

  private List<ConfigAttribute> cfgs = new ArrayList<ConfigAttribute>();

  private String name;
  private String packageName;

  private String superClassName;
  private String superClassPackage;

  private String componentName;


  public void addCfg(ConfigAttribute cfg) {
    cfgs.add(cfg);
  }

  public List<ConfigAttribute> getCfgs() {
    return cfgs;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getFullName() {
    return packageName + "." + name;
  }

  public void setComponentName(String componentName) {
    this.componentName = componentName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getSuperClassName() {
    return superClassName;
  }

  public void setSuperClassName(String superClassName) {
    this.superClassName = superClassName;
  }

  public String getSuperClassPackage() {
    return superClassPackage;
  }

  public void setSuperClassPackage(String superClassPackage) {
    this.superClassPackage = superClassPackage;
  }

  public String getComponentName() {
    return componentName;
  }
}