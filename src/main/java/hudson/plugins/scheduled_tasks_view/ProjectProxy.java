/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hudson.plugins.scheduled_tasks_view;

import antlr.ANTLRException;
import hudson.model.Project;
import hudson.model.Run;
import hudson.scheduler.CronTab;
import hudson.triggers.TimerTrigger;
import hudson.util.RunList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * hudson.model.Project's proxy class
 * @author andre
 */
public class ProjectProxy {

    private static final Logger LOGGER = Logger.getLogger(CalendarView.class.getName());
    private Project p;

    public ProjectProxy(Project p) {
        this.p = p;
    }

    /**
     * Get the real name of the Hudson's Project
     * @return p.getName()
     */
    public String getName() {
        return p.getName();
    }

    /**
     * Get a list of dates in which builds were done.
     * @param lowerBound A calendar that represents the lower bound
     * @param upperBound A calendar that represents the upper bound
     * @return List<Date>
     */
    public List<Date> getDateOfPreviousBuilds(Calendar lowerBound, Calendar upperBound) {
        List<Date> dateOfPreviousBuilds = new ArrayList<Date>();
        RunList<Run> runList = p.getBuilds().byTimestamp(
                lowerBound.getTimeInMillis(),
                upperBound.getTimeInMillis());
        for (Object b : runList) {
            dateOfPreviousBuilds.add(((Run<?, ?>) b).getTime());
        }
        Collections.reverse(dateOfPreviousBuilds);
        return dateOfPreviousBuilds;
    }

    /**
     * Get a list of dates in which builds were done.
     * @return List<Date>
     */
    public List<Date> getDateOfPreviousBuilds() {
        List<Date> dateOfPreviousBuilds = new ArrayList<Date>();
        for (Object b : p.getBuilds()) {
            dateOfPreviousBuilds.add(((Run<?, ?>) b).getTime());
        }
        Collections.reverse(dateOfPreviousBuilds);
        return dateOfPreviousBuilds;
    }

    /**
     * Get a list of the next builds' dates
     * @param lowerBound
     * @param upperBound
     * @return List<Date>
     */
    public List<Date> getDateOfNextBuilds(Calendar lowerBound, Calendar upperBound) {
        List<Date> dateOfFutureBuilds = new ArrayList<Date>();
        TimerTrigger timerTrigger = (TimerTrigger) p.getTrigger(TimerTrigger.class);
        CronTab cronTab = null;
        try {
            cronTab = new CronTab(timerTrigger.getSpec());
        } catch (ANTLRException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        Calendar cal = lowerBound.before(Calendar.getInstance()) ? Calendar.getInstance() : (Calendar)lowerBound.clone();
        cal = cronTab.ceil(cal);
        while (cal.getTimeInMillis() < upperBound.getTimeInMillis()) {
            dateOfFutureBuilds.add(cal.getTime());
            cal.add(Calendar.MINUTE, 1);
            cal = cronTab.ceil(cal);
        }
        return dateOfFutureBuilds;
    }

    /**
     * Get a list of the next builds' dates
     * @return List<Date>
     */
    public List<Date> getDateOfNextBuilds() {
        Calendar cal = Calendar.getInstance();
        Calendar upperBound = (Calendar) cal.clone();
        upperBound.add(Calendar.DAY_OF_YEAR, 31);
        return getDateOfNextBuilds(cal, upperBound);
    }

    /**
     * Get a list of builds and future builds in an interval
     * @param lowerBound
     * @param upperBound
     * @return List<Date>
     */
    public List<Date> getDateOfBuilds(Calendar lowerBound, Calendar upperBound) {
        List<Date> dateOfBuilds = new ArrayList<Date>();
        dateOfBuilds.addAll(getDateOfPreviousBuilds(lowerBound, upperBound));
        dateOfBuilds.addAll(getDateOfNextBuilds(lowerBound, upperBound));
        return dateOfBuilds;
    }

    /**
     * Get a list of builds of the month and the future builds to the same month
     * @return List<Date>
     */
    public List<Date> getDateOfBuilds() {
        List<Date> dateOfBuilds = new ArrayList<Date>();
        dateOfBuilds.addAll(getDateOfPreviousBuilds());
        dateOfBuilds.addAll(getDateOfNextBuilds());
        return dateOfBuilds;
    }
}
