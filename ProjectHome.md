# Simple tool for benchmark java classes #
Provide a simple API for test and compare java classes performance in multi-thread environment.
Generate an html report.


### A simple exemple ###
```
import nf.fr.eraasoft.test.Bench;
import nf.fr.eraasoft.test.Job;
import junit.framework.TestCase;

public class Test extends TestCase {
	public void test1() throws InterruptedException{
		Bench bench = new Bench();

		// 
		bench.addJob(new Job() {
			
			@Override
			public void go() {
 				// Here invoke your favorites services
				System.out.println("Hello");
				
			}
			// Override toString method for the name of job (use in report)
			@Override
			public String toString(){
				return "JobA";
			}
		});

		// you can add an other job
		// bench.addJob(jobB);

		bench.maxthreads(10).maxiteration(100);
		bench.launch();
	}
.

```

The exemple above,launch a test series :
  * Launch the first job in one thread
  * Generate the report, and make a pause
  * Launch the second job with one thread
  * Generate the report, and make a pause
  * ..
  * Launch the second job with 2 threads
  * ..

  * Two thread with 100 iterations
  * update report + pause
  * ...

The report is writen in the current directory