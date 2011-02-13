/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hudson.plugins.scheduled_tasks_view;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author andre
 */
public class ProjectProxyBuilds {
    private ProjectProxy projectProxy;
    private List<Date> builds;

    public ProjectProxyBuilds(ProjectProxy projectProxy) {
        this.projectProxy = projectProxy;
    }

    public ProjectProxyBuilds(ProjectProxy projectProxy, List<Date> builds) {
        this(projectProxy);
        this.builds = builds;
    }

    /**
     * @return the projectProxy
     */
    public ProjectProxy getProjectProxy() {
        return projectProxy;
    }

    /**
     * @return the builds
     */
    public List<Date> getBuilds() {
        return builds;
    }

    /**
     * @param builds the builds to set
     */
    public void setBuilds(Calendar lowerBound, Calendar upperBound) {
        List<Date> dateOfBuilds = projectProxy.getDateOfBuilds(lowerBound, upperBound);
        if (dateOfBuilds.size() > 0)
            this.builds = dateOfBuilds;
    }

    public int getBuildsSize() {
        return builds.size();
    }
}
