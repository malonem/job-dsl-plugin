package javaposse.jobdsl.plugin

import hudson.model.TopLevelItem

/**
 * This is a groovy based wrapper for a jenkins hierarchical group, like a ModifiableTopLevelItemGroup,
 * or com.cloudbees.hudson.plugins.folder.Folder.  This is non-typesafe for now, since Folder does not
 * implement jenkins.model.ModifiableTopLevelItemGroup.
 */
class HierarchicalModelAdapter {
    def hierarchicalGroup = null

    public HierarchicalModelAdapter(def hierarchicalGroup) {
        this.hierarchicalGroup = hierarchicalGroup
    }

    public TopLevelItem createProjectFromXML(String name, InputStream xml) throws IOException {
         return this.hierarchicalGroup.createProjectFromXML(name,xml)
    }
}
