/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hudson.plugins.scheduled_tasks_view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 
 * @author andre
 */
public class ScheduledTasks {
    private List<ProjectProxyBuilds> projectProxiesBuilds;
    private Calendar lowerBound;
    private Calendar upperBound;

    public ScheduledTasks(List<ProjectProxy> projectProxies, Calendar lowerBound, Calendar upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.projectProxiesBuilds = new ArrayList<ProjectProxyBuilds>();
        for (ProjectProxy p : projectProxies) {
            ProjectProxyBuilds ppb = new ProjectProxyBuilds(p);
            ppb.setBuilds(this.lowerBound, this.upperBound);
            if (ppb.getBuilds() != null || ppb.getBuilds().isEmpty())
                this.projectProxiesBuilds.add(ppb);
        }
    }

    public ScheduledTasks(List<ProjectProxy> projectProxies, Calendar lowerBound, int field, int amount) {
        this.lowerBound = lowerBound;
        upperBound = (Calendar)lowerBound.clone();
        upperBound.add(field, amount);
        this.projectProxiesBuilds = new ArrayList<ProjectProxyBuilds>();
        for (ProjectProxy p : projectProxies) {
            ProjectProxyBuilds ppb = new ProjectProxyBuilds(p);
            ppb.setBuilds(this.lowerBound, this.upperBound);
            if (ppb.getBuilds() != null && !ppb.getBuilds().isEmpty())
                this.projectProxiesBuilds.add(ppb);
        }
    }

    /**
     * Get the tasks which ran or which should run in a day-time
     * @return
     * To be refactored
     */
    public List<ProjectProxyBuilds> getScheduledTasks() {
        List<ProjectProxyBuilds> retProjectProxies =
                new ArrayList<ProjectProxyBuilds>();
        for (ProjectProxyBuilds ppb : getProjectProxiesBuilds()) {
            ppb.setBuilds(lowerBound, upperBound);
        }
        return retProjectProxies;
    }

    /**
     * @return the lowerBound
     */
    public Calendar getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Calendar lowerBound) {
        if (lowerBound.after(upperBound))
            upperBound = (Calendar)lowerBound.clone();
        this.lowerBound = lowerBound;
    }

    /**
     * @return the upperBound
     */
    public Calendar getUpperBound() {
        return upperBound;
    }

    /**
     * @param upperBound the upperBound to set
     */
    public void setUpperBound(Calendar upperBound) {
        this.upperBound = upperBound;
    }

    public void setInterval(int field, int amount) {
        upperBound = (Calendar)lowerBound.clone();
        upperBound.add(field, amount);
    }

    /**
     * @return the projectProxiesBuilds
     */
    public List<ProjectProxyBuilds> getProjectProxiesBuilds() {
        return projectProxiesBuilds;
    }

    public int getProjectProxiesBuildsSize() {
        return projectProxiesBuilds.size();
    }
}
