package intellijeval.util;

import com.intellij.openapi.diagnostic.ErrorReportSubmitter;
import com.intellij.openapi.diagnostic.IdeaLoggingEvent;
import com.intellij.openapi.diagnostic.SubmittedReportInfo;

import java.awt.*;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 10:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyErrorReportSubmitter extends ErrorReportSubmitter {
    @Override
    public String getReportActionText() {
      return  "report!";
    }

    @Override
    public SubmittedReportInfo submit(IdeaLoggingEvent[] events, Component parentComponent) {
        System.out.println("SUBMIT ERROR:");
        System.out.println("\t parentComponent = " + parentComponent.getName());
        System.out.println("\t events = " + Arrays.deepToString(events));

        return new SubmittedReportInfo("http://www.google.com","google", SubmittedReportInfo.SubmissionStatus.NEW_ISSUE);
    }
}
