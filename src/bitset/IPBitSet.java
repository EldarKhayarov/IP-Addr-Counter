package bitset;

public class IPBitSet implements IBitSet {
	private static final int WORDS_COUNT = 67_108_864;		// 512 Mb. Количество лонговых "слов" в битовой карте.
	private static final int LONG_SIZE = 64;				// Количество бит в слове.
	private long[] words;

	public IPBitSet() {
		this.words = new long[WORDS_COUNT];
	}

	@Override
	public boolean set(long nBit) {
		// "Выбивание" единицы на битовой карте
		// Битовая карта имеет длину, равную количеству всех возможных значений IP.
		// Каждый адрес переводится в натуральное число, которое будет равно индексу адреса на битовой карте.
		if (nBit < 0){
			throw new IndexOutOfBoundsException("Bit index < 0: " + nBit);
		}

		int wordIndex = this.getWordIndex(nBit);
		int bitIndex = this.getBitIndex(nBit);

		// Если бит с индексом адреса уже равен единице, то метод возвращает false.
		if (checkBitValue(this.words[wordIndex], bitIndex))
			return false;

		// Иначе биту по индексу присваивается единица и возвращается true.
		this.words[wordIndex] |= (1L << bitIndex);
		return true;
	}

	private int getWordIndex(long nBit) {
		return (int)(nBit / LONG_SIZE);
	}

	private int getBitIndex(long nBit) {
		return (int)(nBit % LONG_SIZE);
	}

	private static boolean checkBitValue(long word, int bitIndex) {
		return word >> bitIndex % 2 != 0;
	}
}
