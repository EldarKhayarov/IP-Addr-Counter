package counter;

import java.io.*;
import java.nio.charset.StandardCharsets;

import bitset.IPBitSet;
import bitset.IBitSet;
import common.CountsWrapper;
import common.ICountsWrapperIncrementer;
import reporter.Reporter;


public class IPCounter implements ICounter {
	private String filePath;
	private IBitSet ipBitSet;
	private Thread reporter;
	private ICountsWrapperIncrementer countsWrapper;

	public IPCounter(String filePath) {
		this.filePath = filePath;
		this.ipBitSet = new IPBitSet();
		this.countsWrapper = new CountsWrapper();		// Для передачи ссылки на примитивные переменные репортёру используется класс-обёртка
		this.reporter = new Thread(new Reporter(this.countsWrapper), "Reporter");
	}

	public void scan() {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(this.filePath), StandardCharsets.UTF_8))){
			String line;
			this.reporter.start();
			boolean isAdded;

			while((line = reader.readLine()) != null) {
				// Построчное чтение файла. Если метод возвращает true, то инкрементируем счётчик уникальных значений.
				isAdded = this.ipBitSet.set(this.ipToNumber(line));
				this.countsWrapper.incrementScannedCount();
				if (isAdded) this.countsWrapper.incrementUniqueCount();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			this.stopScan();
		}
	}

	private void stopScan() {
		// Остановка репортёра. Он самостоятельно выведет результат.
		this.reporter.interrupt();
	}

	private long ipToNumber(String ipLine) {
		// Перевод IP в натуральное число. Результат является индексом IP в битовой карте.
		long result = 0L;
		try {
			String[] nums = ipLine.split("\\.");
			for (int idx=0; idx < 4; idx++) {
				result += Long.parseLong(nums[idx]) * Math.pow(256, 3-idx);
			}
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}
}
