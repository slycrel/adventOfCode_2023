import java.math.BigInteger;
import java.util.List;

record Race(BigInteger time, BigInteger distance) { }

public class Advent6
{
	/*
		Time:        49     78     79     80
		Distance:   298   1185   1066   1181
	*/

	public static void main(String[] args) {
		// part 1
		var races = List.of(
			getRace(49, 298),
			getRace(78, 1185),
			getRace(79, 1066),
			getRace(80, 1181)
		);

		System.out.println("part 1 answer: " + calculateWaysToWin(races));

		// part 2
		var race = new Race(new BigInteger("49787980"), new BigInteger("298118510661181"));
		System.out.println("part 2 answer: " + calculateWaysToWin(race));
	}

	static Race getRace(int time, int distance)
	{
		return new Race(new BigInteger(""+time), new BigInteger(""+distance));
	}

	static long calculateWaysToWin(List<Race> races) {
		long totalWays = 1;

		for (Race race : races)
		{
			var waysToWin = calculateWaysToWin(race).longValue();
			totalWays *= waysToWin;
		}

		return totalWays;
	}

	static BigInteger calculateWaysToWin(Race race)
	{
		BigInteger waysToWin = BigInteger.ZERO;

		for (BigInteger i = BigInteger.ZERO; i.compareTo(race.time()) < 0; i = i.add(BigInteger.ONE))
		{
			BigInteger distance = (race.time().subtract(i)).multiply(i);
			if (distance.compareTo(race.distance()) > 0)
				waysToWin = waysToWin.add(BigInteger.ONE);
		}

		return waysToWin;
	}
}

