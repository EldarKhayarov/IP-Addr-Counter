package reporter;

import common.CountsWrapper;
import common.ICountsWrapper;

public class Reporter implements Runnable {
	private ICountsWrapper countsWrapper;

	public Reporter(ICountsWrapper countsWrapper) {
		this.countsWrapper = countsWrapper;
	}

	public void run() {
		int timeSeconds = 0;
		try {
			long lastScannedIPCount = 0;
			long currentScannedIPCount;
			while(true) {
				// Параллельно выполнению программы работает отдельный поток, ежесекундно отчитывающийся о прогрессе сканирования.
				Thread.sleep(1000);
				currentScannedIPCount = this.countsWrapper.getScannedCount();
				timeSeconds++;
				System.out.flush();
				System.out.printf(
						"Обработано %d строк, а найдено %d уникальных значений; скорость: %d/сек; прошло %d минут %d секунд\n",
						currentScannedIPCount,
						this.countsWrapper.getUniqueCount(),
						currentScannedIPCount - lastScannedIPCount,
						timeSeconds / 60,
						timeSeconds % 60
				);
				lastScannedIPCount = currentScannedIPCount;
			}
		} catch (InterruptedException e) {
			// После выполнения сканирования в главном потоке производится прерывание репортёра и вывод финальной строки.
			System.out.flush();
			System.out.printf(
					"Обработка завершена за %d минут %d секунд. Найдено %d уникальных значений из %d обработанных строк.",
					timeSeconds / 60,
					timeSeconds % 60,
					this.countsWrapper.getUniqueCount(),
					this.countsWrapper.getScannedCount());
		}
	}
}
