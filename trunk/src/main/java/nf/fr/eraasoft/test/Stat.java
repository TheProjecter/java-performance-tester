package nf.fr.eraasoft.test;

import java.util.HashMap;
import java.util.Map;

public class Stat {
	JobStat[] jobs;
	Job job;

	public Job getJob() {
		return job;
	}

	public Stat(int maxthreads, Job job) {
		jobs = new JobStat[maxthreads];
		for (int n = 0; n < maxthreads; n++) {
			jobs[n] = new JobStat();
		}
		this.job = job;
	}

	public static String[][] getRows(Stat[] stats) {
		String[][] rows = new String[stats[0].jobs.length][stats.length];
		for (int n = 0; n < rows.length; n++) {
			String[] row = rows[n];

			for (int c = 0; c < stats.length; c++) {
				row[c] = Integer.toString(stats[c].jobs[n].getDuration());
			}
			if (n == rows.length - 2) {
				System.out.println();
			}
		}
		return rows;
	}

	public JobStat[] getJobs() {
		return jobs;
	}

	@Override
	public String toString() {

		StringBuilder b = new StringBuilder();
		b.append(job).append(": ");
		for (JobStat jobStat : jobs) {
			b.append('[').append(jobStat.nbThread).append(']');
			b.append(jobStat.end - jobStat.start).append(" ms");

			b.append('\t');
		}

		return b.toString();
	}

	public static void displayGraph(Stat[] stats) {

		Map<String, Object> root = new HashMap<String, Object>();
		root.put("stats", stats);
		root.put("rows", getRows(stats));
		new GoogleGraph().applyTemplateSilent("gchart.html", root);
	}

}
