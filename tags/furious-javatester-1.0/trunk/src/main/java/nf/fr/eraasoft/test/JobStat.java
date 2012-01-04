package nf.fr.eraasoft.test;

public class JobStat {
	public long getEnd() {
		return end;
	}

	public int getNbThread() {
		return nbThread;
	}

	public long[] getShot() {
		return shot;
	}

	public long getStart() {
		return start;
	}

	public int getDuration() {
		return (int) (end - start);
	}

	public int nbThread;

	long[] shot;
	public long start;

	public long end;
}
