package javaposse.jobdsl.dsl;

public class GeneratedJob implements Comparable {
    private String templateName;
    private String jobName;
    private String jobContext;
    private boolean created;

    public GeneratedJob(String templateName, String jobName, String jobContext, boolean created) {
        super();
        this.templateName = templateName;
        this.jobName = jobName;
        this.jobContext = jobContext != null ? jobContext : "job";
    }

    public String getJobName() {
        return jobName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public boolean isCreated() {
        return created;
    }

    public String getJobContext() {
        return jobContext;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof GeneratedJob) {
            int context = jobContext.compareTo(((GeneratedJob) o).getJobContext());
            if (context != 0) return context;
            return jobName.compareTo(((GeneratedJob) o).getJobName());
        } else {
            return jobName.compareTo(o.toString());
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((jobName == null) ? 0 : jobName.hashCode());
        result = prime * result + ((templateName == null) ? 0 : templateName.hashCode());
        result = prime * result + ((jobContext == null) ? 0 : jobContext.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GeneratedJob other = (GeneratedJob) obj;
        if (jobName == null) {
            if (other.jobName != null)
                return false;
        } else if (!jobName.equals(other.jobName))
            return false;
        if (templateName == null) {
            if (other.templateName != null)
                return false;
        } else if (!templateName.equals(other.templateName))
            return false;
        if (jobContext == null) {
            if (other.jobContext != null)
                return false;
        } else if (!jobContext.equals(other.jobContext))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String cleanTemplateName = templateName==null?"none":("'" + templateName + '\'');
        return "GeneratedJob{" +
                "jobName='" + jobName + "'" +
                ", templateName=" + cleanTemplateName +
                ", jobContext=" + jobContext +
                "}";
    }
}
