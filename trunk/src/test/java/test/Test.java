package test;

import nf.fr.eraasoft.test.Bench;
import nf.fr.eraasoft.test.Job;
import junit.framework.TestCase;

public class Test extends TestCase {
	public void test1() throws InterruptedException{
		Bench bench = new Bench();
		bench.addJob(new Job() {
			
			@Override
			public void go() {
				System.out.println("Hello");
				
			}
		});
		bench.maxthreads(3).launch();
	}

}
