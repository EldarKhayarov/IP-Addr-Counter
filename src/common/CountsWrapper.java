package common;

public class CountsWrapper implements ICountsWrapperIncrementer {
	public long scannedCount;
	public long uniqueCount;

	public CountsWrapper() {
		this.scannedCount = 0;
		this.uniqueCount = 0;
	}


	@Override
	public void incrementScannedCount() { this.scannedCount++; }

	@Override
	public void incrementUniqueCount() { this.uniqueCount++; }

	@Override
	public long getScannedCount() { return this.scannedCount; }

	@Override
	public long getUniqueCount() { return this.uniqueCount; }
}
