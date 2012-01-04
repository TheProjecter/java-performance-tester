package nf.fr.eraasoft.test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Bench {
	List<Job> jobs = new ArrayList<Job>();
	BlockingQueue<String> queue = new ArrayBlockingQueue<String>(600);
	int maxiteration = 100;
	int maxthreads = 10;
	int minthreads = 1;
	int pause = 1000;
	Stat[] stats;

	public Bench() {

	}

	public Bench pause(int pause) {
		this.pause = pause;
		return this;
	}

	public int pause() {
		return pause;
	}

	public Bench addJob(Job job) {
		jobs.add(job);
		return this;
	}

	public Bench minthreads(int minthreads) {
		this.minthreads = minthreads;
		if (minthreads > maxthreads) {
			maxthreads = minthreads;
		}
		return this;
	}

	public Bench maxthreads(int maxthreads) {
		this.maxthreads = maxthreads;
		if (maxthreads < minthreads) {
			minthreads = maxthreads;
		}
		return this;
	}

	public Bench maxiteration(int maxiteration) {
		this.maxiteration = maxiteration;
		return this;
	}

	public void displayStat() {
		Stat.displayGraph(stats, graphContext);
		for (Stat stat : stats) {
			System.out.println(stat.toString());
		}
	}
	Map<String, Object> graphContext = new Hashtable<String, Object>();
	public Bench graphTitle(String graphTitle) {
		graphContext.put("graphtitle", graphTitle);
		return this;
	}

	public void launch() throws InterruptedException {
		stats = new Stat[jobs.size()];
		int i = 0;
		for (Job job : jobs) {
			stats[i++] = new Stat(maxthreads, job);
		}
		// queue.offer(1);

		for (int n = minthreads; n <= maxthreads; n++) {
			for (Stat stat : stats) {
				try {

					launch(stat, n);

					System.out.println();
					if (pause > 0) {
						Stat.displayGraph(stats, graphContext);
						Thread.sleep(pause);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	private void launch(Stat stat, int nbthread) {
		Thread[] tasks = new Thread[nbthread];

		for (int n = 0; n < nbthread; n++) {
			stat.jobs[n].nbThread = n;
			tasks[n] = new Task(stat.job, stat.jobs[nbthread - 1], this.maxiteration);
			tasks[n].setName(stat.job + " " + nbthread + " [" + n + "]");
		}

		for (int n = 0; n < nbthread; n++) {
			tasks[n].start();
		}

		for (int n = 0; n < nbthread; n++) {
			try {
				String qval = queue.take();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// System.out.println("    *********" + queue.size());

	}

	private class Task extends Thread {
		final Job job;
		final JobStat jobStat;
		int iteration;

		public Task(final Job job, final JobStat jobStat, int iteration) {
			this.job = job;
			this.iteration = iteration;
			this.jobStat = jobStat;
			this.jobStat.shot = new long[iteration];
		}

		@Override
		public void run() {
			jobStat.start = System.currentTimeMillis();
			for (int n = 0; n < iteration; n++) {
				job.go();
				jobStat.shot[n] = System.currentTimeMillis();
			}
			jobStat.end = System.currentTimeMillis();
			queue.offer(this.getName());

		}

	}

}
