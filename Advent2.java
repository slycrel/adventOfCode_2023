import java.util.ArrayList;
import java.util.List;

/*
--- Day 2: Cube Conundrum ---

You're launched high into the atmosphere! The apex of your trajectory just barely reaches the surface of a large island floating in the sky. You gently land in a fluffy pile of leaves. It's quite cold, but you don't see much snow. An Elf runs over to greet you.

The Elf explains that you've arrived at Snow Island and apologizes for the lack of snow. He'll be happy to explain the situation, but it's a bit of a walk, so you have some time. They don't get many visitors up here; would you like to play a game in the meantime?

As you walk, the Elf shows you a small bag and some cubes which are either red, green, or blue. Each time you play this game, he will hide a secret number of cubes of each color in the bag, and your goal is to figure out information about the number of cubes.

To get information, once a bag has been loaded with cubes, the Elf will reach into the bag, grab a handful of random cubes, show them to you, and then put them back in the bag. He'll do this a few times per game.

You play several games and record the information from each game (your puzzle input). Each game is listed with its ID number (like the 11 in Game 11: ...) followed by a semicolon-separated list of subsets of cubes that were revealed from the bag (like 3 red, 5 green, 4 blue).

For example, the record of a few games might look like this:

Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
In game 1, three sets of cubes are revealed from the bag (and then put back again). The first set is 3 blue cubes and 4 red cubes; the second set is 1 red cube, 2 green cubes, and 6 blue cubes; the third set is only 2 green cubes.

The Elf would first like to know which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?

In the example above, games 1, 2, and 5 would have been possible if the bag had been loaded with that configuration. However, game 3 would have been impossible because at one point the Elf showed you 20 red cubes at once; similarly, game 4 would also have been impossible because the Elf showed you 15 blue cubes at once. If you add up the IDs of the games that would have been possible, you get 8.

Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?

Your puzzle answer was 2283.

--- Part Two ---

The Elf says they've stopped producing snow because they aren't getting any water! He isn't sure why the water stopped; however, he can show you how to get to the water source to check it out for yourself. It's just up ahead!

As you continue your walk, the Elf poses a second question: in each game you played, what is the fewest number of cubes of each color that could have been in the bag to make the game possible?

Again consider the example games from earlier:

Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
In game 1, the game could have been played with as few as 4 red, 2 green, and 6 blue cubes. If any color had even one fewer cube, the game would have been impossible.
Game 2 could have been played with a minimum of 1 red, 3 green, and 4 blue cubes.
Game 3 must have been played with at least 20 red, 13 green, and 6 blue cubes.
Game 4 required at least 14 red, 3 green, and 15 blue cubes.
Game 5 needed no fewer than 6 red, 3 green, and 2 blue cubes in the bag.
The power of a set of cubes is equal to the numbers of red, green, and blue cubes multiplied together. The power of the minimum set of cubes in game 1 is 48. In games 2-5 it was 12, 1560, 630, and 36, respectively. Adding up these five powers produces the sum 2286.

For each game, find the minimum set of cubes that must have been present. What is the sum of the power of these sets?

Your puzzle answer was 78669.

Both parts of this puzzle are complete! They provide two gold stars: **
*/

// pair programming with a non-programmer, took me ~2.5 hrs
public class Advent2
{
	public static final int redMax = 12;
	public static final int greenMax = 13;
	public static final int blueMax = 14;

	public static void main(String[] args)
	{
		System.out.println("starting!");

		int sum = 0;
		int powerSum = 0;

		for (var line : input.split("\n"))
		{
			// part 1:
			var game = parsePulls(line);
			sum += game.gameSum();      // 0 for invalid games

			// part 2:
			powerSum += game.gamePower();
		}

		// part 1 answer:
		System.out.println("total sum is " + sum);

		// part 2 answer:
		System.out.println("total POWER sum is " + powerSum);
	}

	// input formed like:
	//  Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
	static Game parsePulls(String inputLine)
	{
		var inputs = inputLine.split(":");
		if (inputs.length < 2)
			throw new RuntimeException("invalid input line when parsing bag pulls: " + inputLine);

		// parse the game number from before the ":"
		var whichGame = inputs[0].replaceAll("[^0-9]", "");
		int game = Integer.parseInt(whichGame);
		System.out.println("game ID parsed: " + game + " from input " + whichGame);

		// parse the number of pulls into an array
		var pullsToProcess = List.of(inputs[1].split(";"));
		var array = new ArrayList<BagPull>();
		System.out.println("parsing: " + inputs[1]);

		// extract each pull and add it to the list
		for (var pull : pullsToProcess)
		{
			int red = 0, green = 0, blue = 0;
			var colors = pull.split(",");
			for (var color : colors)
			{
				var values = color.split(" ");
				switch (values[2])
				{
					case "red" -> red = Integer.parseInt(values[1]);
					case "green" -> green = Integer.parseInt(values[1]);
					case "blue" -> blue = Integer.parseInt(values[1]);
					default -> { }
				}
			}
			var tPull = new BagPull(red, green, blue, pull);
			array.add(tPull);
			System.out.println("bag pull is " + tPull);
		}

		// return the game info
		var gam = new Game(array, game);
		return gam;
	}

	private record BagPull(int red, int green, int blue, String pull)
	{
		@Override
		public String toString()
		{
			return red() + " red, " +
			       green() + " green, " +
			       blue() + " blue";
		}
	}

	private record Game(ArrayList<BagPull> pulls, int gameId) {
		private boolean eachPullValid()
		{
			for (var pull : pulls)
			{
				if (pull.red() > redMax ||
				    pull.green() > greenMax ||
				    pull.blue() > blueMax)
				{
					return false;
				}
			}

			return true;
		}

		// part 2: min cubes per color
		BagPull minCubesForGame()
		{
			int red = 0, green = 0, blue = 0;
			for (var pull : pulls)
			{
				red = Math.max(red, pull.red());
				green = Math.max(green, pull.green());
				blue = Math.max(blue, pull.blue());
			}
			return new BagPull(red, green, blue, "<calculated>");
		}

		int gamePower()
		{
			var cubes = minCubesForGame();
			return cubes.red() * cubes.green() * cubes.blue();
		}

		int gameSum()
		{
			if (eachPullValid())
				return gameId;

			return 0;
		}
	}

	public static final String testInput =
		"""
				Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
				Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
				Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
				Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
				Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
			""";

	public static final String input =
		"""
			Game 1: 9 red, 2 green, 13 blue; 10 blue, 2 green, 13 red; 8 blue, 3 red, 6 green; 5 green, 2 red, 1 blue
			Game 2: 2 green, 2 blue, 16 red; 14 red; 13 red, 13 green, 2 blue; 7 red, 7 green, 2 blue
			Game 3: 6 red, 4 green, 7 blue; 7 blue, 9 red, 3 green; 2 red, 4 green; 6 red, 2 blue; 7 blue, 9 red, 5 green
			Game 4: 8 red, 2 green, 2 blue; 5 red, 2 blue, 3 green; 7 red, 9 green, 2 blue; 10 blue, 16 green, 1 red; 6 blue, 1 green, 8 red
			Game 5: 12 blue, 6 red, 1 green; 10 green, 8 blue, 15 red; 1 green, 12 red, 11 blue; 3 green, 11 blue, 1 red; 7 blue, 5 red, 5 green; 11 green, 2 blue
			Game 6: 3 blue, 10 green, 2 red; 5 green; 6 blue, 3 red
			Game 7: 3 green, 1 blue, 4 red; 3 red, 13 blue; 14 blue, 6 red; 7 green, 15 blue, 9 red; 1 green, 3 red, 9 blue
			Game 8: 19 red, 1 green, 4 blue; 14 blue; 2 red, 3 blue
			Game 9: 5 green, 14 blue, 3 red; 8 red, 16 blue, 5 green; 12 green, 15 red, 4 blue; 14 blue, 17 red; 6 red, 11 blue, 8 green
			Game 10: 9 red, 7 blue; 6 red, 3 blue, 4 green; 1 red, 5 green, 3 blue; 5 red, 9 green, 6 blue; 7 blue, 6 green
			Game 11: 2 blue, 10 green; 11 green; 2 green, 1 red, 2 blue; 2 blue, 1 green, 1 red; 2 blue, 18 green
			Game 12: 6 green, 2 red; 2 green; 11 red, 6 green, 1 blue
			Game 13: 1 blue, 10 red, 2 green; 2 green, 7 red; 9 green, 9 red; 1 red, 4 green
			Game 14: 7 red, 15 green, 17 blue; 10 green, 6 red, 12 blue; 3 blue, 7 red, 3 green
			Game 15: 8 green; 10 red, 17 green, 1 blue; 9 red, 1 blue; 6 green, 8 red; 1 green, 1 red; 17 green, 2 red
			Game 16: 3 blue, 2 green, 7 red; 12 blue, 9 red, 5 green; 8 green, 3 blue, 5 red; 6 red, 2 blue, 11 green; 3 green, 8 blue, 9 red; 1 green, 5 red, 12 blue
			Game 17: 2 green, 1 blue, 3 red; 1 blue, 1 green, 4 red; 2 blue, 2 green, 11 red; 1 red, 2 green; 2 blue, 10 red, 2 green
			Game 18: 3 blue, 3 red; 8 blue, 1 green, 5 red; 4 green, 6 blue, 3 red
			Game 19: 9 red, 3 green, 14 blue; 15 red, 2 blue, 1 green; 2 green, 15 red, 5 blue; 3 red, 3 blue, 1 green
			Game 20: 2 red, 1 blue, 5 green; 11 blue, 1 red, 4 green; 6 blue, 2 red, 2 green; 13 blue, 2 red, 10 green; 7 green, 13 blue
			Game 21: 3 red, 1 green, 1 blue; 3 red, 7 blue, 1 green; 1 green, 4 red, 7 blue; 1 green; 6 blue, 3 red; 2 red, 6 blue
			Game 22: 7 red, 5 green, 2 blue; 14 red; 2 blue, 5 green, 15 red; 1 blue, 5 green, 14 red; 13 red, 1 green; 3 red, 1 green
			Game 23: 2 green, 8 blue, 5 red; 3 blue, 9 red; 9 blue, 2 green; 9 red, 2 green, 5 blue; 5 red, 2 green
			Game 24: 14 green, 7 blue, 2 red; 7 blue, 3 red, 16 green; 1 blue, 5 red, 6 green; 3 blue, 7 red, 2 green; 7 red, 9 green
			Game 25: 3 blue, 8 red, 15 green; 2 red, 8 green, 2 blue; 16 red, 3 blue, 13 green
			Game 26: 1 blue, 9 red, 8 green; 6 green, 1 red, 3 blue; 7 red, 8 blue, 6 green; 9 red, 7 green
			Game 27: 6 red, 9 green, 1 blue; 8 green, 3 red, 1 blue; 8 green, 15 red; 14 red, 11 green, 2 blue; 11 green, 4 red, 2 blue; 1 blue, 16 red
			Game 28: 6 green, 10 blue, 4 red; 6 red, 2 blue, 6 green; 6 green, 14 blue, 17 red; 9 green, 1 red, 9 blue
			Game 29: 10 blue, 2 red; 3 red, 11 green, 7 blue; 8 green, 2 blue, 7 red; 17 green, 5 blue, 9 red; 19 green, 10 red, 9 blue
			Game 30: 6 blue, 9 green, 6 red; 17 green, 10 blue, 8 red; 17 green, 12 red, 7 blue
			Game 31: 9 red, 1 green, 1 blue; 3 red; 6 red, 1 blue, 1 green; 2 blue, 7 red
			Game 32: 3 green, 8 blue; 5 red, 4 green, 2 blue; 10 blue, 3 green; 2 green, 4 blue; 2 green, 16 blue, 2 red
			Game 33: 2 red, 8 blue; 2 green, 6 red, 1 blue; 7 red, 12 blue; 1 red, 1 green, 7 blue; 1 green, 3 red, 2 blue
			Game 34: 15 green; 1 red, 6 green; 6 blue, 1 red, 17 green; 14 green, 3 blue; 11 green, 1 red; 4 green, 1 blue
			Game 35: 4 red, 3 green, 12 blue; 20 blue, 2 red, 2 green; 4 red, 4 green, 8 blue
			Game 36: 8 red, 7 green; 15 red, 2 blue, 3 green; 2 red, 1 blue, 5 green; 4 green, 1 blue, 9 red; 14 red, 1 blue, 1 green; 11 green, 12 red
			Game 37: 3 red, 3 green, 8 blue; 3 blue; 1 green, 3 blue, 2 red; 1 red, 2 blue, 2 green
			Game 38: 18 red, 12 blue, 7 green; 7 blue, 10 red, 10 green; 1 green, 6 blue, 10 red; 7 red, 3 blue, 12 green; 9 green, 5 blue, 3 red
			Game 39: 2 blue, 2 green, 2 red; 5 blue, 1 green; 1 blue; 1 red, 5 blue, 2 green
			Game 40: 4 blue, 1 green; 5 blue, 1 red, 7 green; 2 red, 5 green, 5 blue
			Game 41: 15 blue, 3 green, 16 red; 16 blue, 18 red, 8 green; 8 green, 1 blue, 12 red; 11 blue, 5 green, 18 red; 5 green, 20 red, 17 blue
			Game 42: 7 red, 10 green; 1 blue, 5 red, 6 green; 6 red, 3 blue, 9 green; 6 green, 3 blue, 8 red; 4 green, 4 red, 2 blue; 2 blue, 1 green
			Game 43: 6 blue, 5 red, 1 green; 2 green; 2 blue, 1 red; 4 green, 5 blue, 2 red
			Game 44: 10 green; 8 green; 10 green, 2 blue, 10 red
			Game 45: 9 green, 3 red; 8 green, 2 blue; 2 green, 8 blue
			Game 46: 2 red, 9 blue, 9 green; 6 red, 10 green, 11 blue; 5 red, 4 green, 3 blue; 3 red, 2 green, 14 blue; 5 green, 5 red, 12 blue; 1 red, 9 green, 18 blue
			Game 47: 2 red, 3 blue; 2 green; 2 green, 3 blue
			Game 48: 9 green, 6 red, 1 blue; 3 blue, 12 green, 8 red; 4 red, 5 green, 5 blue; 14 green, 5 red; 1 red, 18 green, 4 blue
			Game 49: 11 red, 10 green, 12 blue; 4 green, 6 red, 6 blue; 9 red, 3 blue, 7 green; 6 red, 10 green, 14 blue; 8 blue, 10 red, 5 green; 6 blue, 17 green, 6 red
			Game 50: 8 blue, 4 green, 2 red; 1 red; 10 green, 4 blue, 1 red; 5 green, 8 blue, 2 red; 6 red, 1 blue, 3 green; 6 blue, 6 red, 1 green
			Game 51: 3 red, 3 blue, 2 green; 8 green, 5 red; 11 red, 11 green, 2 blue; 12 green, 2 blue, 19 red; 12 red, 1 blue, 12 green; 10 green, 1 blue, 3 red
			Game 52: 2 blue, 15 red; 19 red, 4 green; 11 red, 3 green, 6 blue; 1 green, 2 blue, 8 red; 3 blue, 6 red; 1 green, 7 blue, 1 red
			Game 53: 1 green; 2 green, 1 blue; 1 red
			Game 54: 1 red, 1 green, 12 blue; 1 green, 5 red, 12 blue; 2 green, 4 blue; 13 blue, 2 red, 2 green; 2 green, 10 blue, 1 red; 5 red, 1 green, 7 blue
			Game 55: 4 green, 16 blue, 11 red; 6 red, 15 blue; 6 blue, 4 green, 5 red; 10 blue, 10 red, 3 green
			Game 56: 3 red, 8 blue, 11 green; 9 green, 5 red, 4 blue; 1 blue, 4 red, 4 green
			Game 57: 3 red, 12 green, 7 blue; 13 green, 14 blue, 1 red; 4 red, 6 green, 9 blue; 12 blue, 13 green
			Game 58: 4 red, 4 blue, 3 green; 3 blue, 4 red; 11 green, 2 blue, 2 red
			Game 59: 12 green, 5 red, 1 blue; 7 red, 1 blue, 3 green; 17 green, 2 blue, 4 red; 12 red, 17 green; 7 red, 10 green, 2 blue
			Game 60: 5 blue, 3 green; 5 green, 11 red, 12 blue; 1 red, 2 blue, 15 green
			Game 61: 12 blue, 1 green, 12 red; 14 blue, 12 green, 5 red; 7 red, 12 blue, 16 green
			Game 62: 1 green, 8 red, 8 blue; 11 blue, 2 red; 1 green, 10 blue, 12 red; 7 red, 2 blue, 1 green; 6 red, 1 green, 11 blue; 1 green, 6 red, 6 blue
			Game 63: 17 green, 16 red, 10 blue; 9 red, 14 green, 11 blue; 5 green, 16 red
			Game 64: 12 red, 1 green, 1 blue; 4 blue, 7 red; 10 blue, 4 green, 6 red; 8 blue, 4 red, 13 green; 13 green, 9 blue, 9 red; 13 blue, 7 red, 3 green
			Game 65: 4 blue, 3 green, 4 red; 10 blue, 5 red, 7 green; 1 red, 4 blue, 3 green; 13 green, 1 red, 9 blue; 8 green, 3 blue
			Game 66: 17 green, 13 red, 3 blue; 15 green, 2 red, 4 blue; 10 red, 4 blue, 5 green; 6 red, 5 blue, 7 green; 3 red, 10 green, 1 blue
			Game 67: 10 blue; 4 blue, 4 red; 4 blue, 3 green, 3 red; 1 green, 5 blue, 3 red
			Game 68: 6 green, 6 blue, 3 red; 13 blue, 1 red; 14 blue, 6 red, 2 green; 14 blue, 7 red, 2 green
			Game 69: 2 green, 2 blue, 3 red; 1 red, 6 blue, 4 green; 8 green, 9 red; 2 green
			Game 70: 2 red, 7 green; 1 red, 10 green, 1 blue; 6 blue, 14 green; 6 green, 2 blue, 1 red; 5 blue, 10 green, 2 red; 2 blue, 11 green
			Game 71: 6 blue, 4 red, 7 green; 13 green, 6 red, 3 blue; 8 green, 2 blue, 4 red
			Game 72: 3 blue, 1 green, 11 red; 5 green, 11 blue, 4 red; 7 blue, 13 red; 14 blue, 12 red, 5 green
			Game 73: 8 red, 19 blue, 4 green; 15 green, 3 red, 16 blue; 7 blue, 9 red
			Game 74: 5 green, 8 red, 6 blue; 8 green, 9 red; 11 green, 6 blue
			Game 75: 1 green, 6 red, 4 blue; 2 green, 13 blue, 6 red; 10 red, 1 green, 10 blue
			Game 76: 3 blue, 6 green, 2 red; 7 red, 6 blue, 4 green; 5 blue, 6 red, 3 green
			Game 77: 17 red, 6 green; 7 green, 12 red, 4 blue; 4 red, 7 green, 14 blue
			Game 78: 1 green, 15 red, 5 blue; 16 green, 3 red, 18 blue; 13 blue, 1 red, 13 green
			Game 79: 7 red, 1 blue, 3 green; 2 green, 5 red; 5 green, 2 blue, 8 red; 11 red, 1 blue, 3 green
			Game 80: 9 blue, 4 red; 4 green, 3 blue, 4 red; 7 red, 9 blue, 4 green; 5 red, 9 blue, 4 green; 3 red, 11 blue, 5 green; 6 red, 4 green, 11 blue
			Game 81: 1 red, 16 green, 2 blue; 4 red, 15 green, 10 blue; 2 red, 9 blue, 17 green; 10 blue, 16 green
			Game 82: 4 blue, 3 green; 2 green, 3 blue; 4 blue, 2 red, 2 green; 2 red, 1 green
			Game 83: 10 blue, 8 red, 19 green; 8 red, 4 blue, 17 green; 2 blue, 9 red, 6 green; 11 blue, 2 red, 17 green; 1 red, 17 green, 8 blue
			Game 84: 4 blue, 1 red, 6 green; 7 blue, 10 green, 4 red; 1 green, 7 blue, 1 red
			Game 85: 1 blue, 2 red, 1 green; 5 blue, 2 green, 9 red; 1 green, 2 red, 10 blue
			Game 86: 17 blue, 9 green, 9 red; 19 red, 12 green, 11 blue; 19 red, 9 green, 17 blue
			Game 87: 11 green, 6 red; 1 green, 4 blue; 16 green, 5 red; 14 green, 1 red, 14 blue; 12 blue, 8 green, 6 red; 4 red, 11 blue, 2 green
			Game 88: 6 green, 7 red, 1 blue; 5 red, 1 green, 1 blue; 3 red, 6 green
			Game 89: 1 green, 8 blue, 1 red; 8 red, 1 green; 4 red, 6 blue; 4 red, 10 blue; 8 red, 9 blue, 1 green
			Game 90: 6 blue, 12 green; 1 red, 4 blue, 14 green; 5 red, 5 blue, 6 green; 1 blue, 3 red, 3 green; 7 green, 4 blue; 2 green, 2 red, 1 blue
			Game 91: 2 blue, 12 red, 4 green; 7 green, 6 red; 1 blue, 7 green, 6 red; 5 green, 5 red, 1 blue; 1 blue, 11 green, 9 red; 4 red, 1 blue, 2 green
			Game 92: 4 blue, 12 green; 6 red, 4 blue, 2 green; 5 blue, 1 red, 17 green; 6 red, 4 blue, 15 green; 3 blue, 5 red, 13 green
			Game 93: 8 green, 2 blue, 16 red; 7 green, 3 blue, 8 red; 9 green, 4 red, 3 blue; 13 red, 5 blue; 5 blue, 1 green, 10 red; 1 blue, 9 red
			Game 94: 7 blue, 5 red, 14 green; 8 green, 3 blue, 1 red; 4 blue, 8 red
			Game 95: 3 blue, 4 green, 10 red; 5 blue, 17 red, 2 green; 18 red, 2 green, 5 blue; 3 blue, 3 green, 2 red; 4 blue, 18 red; 6 red, 2 green
			Game 96: 1 blue, 9 green, 2 red; 3 red, 10 green; 2 red, 8 green, 4 blue; 17 green, 2 blue; 10 green, 1 blue, 1 red; 8 green, 1 blue, 3 red
			Game 97: 9 green, 1 blue; 1 blue, 6 green; 7 blue, 3 green; 3 red; 17 green, 5 red; 1 blue, 17 green, 5 red
			Game 98: 4 blue, 7 green, 2 red; 15 green, 10 blue, 1 red; 4 blue, 5 green; 1 green, 2 red, 5 blue
			Game 99: 2 green, 1 blue, 14 red; 11 red, 6 blue, 5 green; 10 red, 18 blue, 1 green; 5 green, 9 blue, 9 red
			Game 100: 5 blue, 5 green; 7 blue, 15 green; 4 red, 7 green, 12 blue; 7 green, 1 blue; 5 blue, 9 green, 1 red
			""";

}
