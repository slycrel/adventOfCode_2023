import java.util.ArrayList;

/*
--- Day 10: Pipe Maze ---

You use the hang glider to ride the hot air from Desert Island all the way up to the floating metal island. This island is surprisingly cold and there definitely aren't any thermals to glide on, so you leave your hang glider behind.

You wander around for a while, but you don't find any people or animals. However, you do occasionally find signposts labeled "Hot Springs" pointing in a seemingly consistent direction; maybe you can find someone at the hot springs and ask them where the desert-machine parts are made.

The landscape here is alien; even the flowers and trees are made of metal. As you stop to admire some metal grass, you notice something metallic scurry away in your peripheral vision and jump into a big pipe! It didn't look like any animal you've ever seen; if you want a better look, you'll need to get ahead of it.

Scanning the area, you discover that the entire field you're standing on is densely packed with pipes; it was hard to tell at first because they're the same metallic silver color as the "ground". You make a quick sketch of all of the surface pipes you can see (your puzzle input).

The pipes are arranged in a two-dimensional grid of tiles:

| is a vertical pipe connecting north and south.
- is a horizontal pipe connecting east and west.
L is a 90-degree bend connecting north and east.
J is a 90-degree bend connecting north and west.
7 is a 90-degree bend connecting south and west.
F is a 90-degree bend connecting south and east.
. is ground; there is no pipe in this tile.
S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
Based on the acoustics of the animal's scurrying, you're confident the pipe that contains the animal is one large, continuous loop.

For example, here is a square loop of pipe:

.....
.F-7.
.|.|.
.L-J.
.....
If the animal had entered this loop in the northwest corner, the sketch would instead look like this:

.....
.S-7.
.|.|.
.L-J.
.....
In the above diagram, the S tile is still a 90-degree F bend: you can tell because of how the adjacent pipes connect to it.

Unfortunately, there are also many pipes that aren't connected to the loop! This sketch shows the same loop as above:

-L|F7
7S-7|
L|7||
-L-J|
L|-JF
In the above diagram, you can still figure out which pipes form the main loop: they're the ones connected to S, pipes those pipes connect to, pipes those pipes connect to, and so on. Every pipe in the main loop connects to its two neighbors (including S, which will have exactly two pipes connecting to it, and which is assumed to connect back to those two pipes).

Here is a sketch that contains a slightly more complex main loop:

..F7.
.FJ|.
SJ.L7
|F--J
LJ...
Here's the same example sketch with the extra, non-main-loop pipe tiles also shown:

7-F7-
.FJ|7
SJLL7
|F--J
LJ.LJ
If you want to get out ahead of the animal, you should find the tile in the loop that is farthest from the starting position. Because the animal is in the pipe, it doesn't make sense to measure this by direct distance. Instead, you need to find the tile that would take the longest number of steps along the loop to reach from the starting point - regardless of which way around the loop the animal went.

In the first example with the square loop:

.....
.S-7.
.|.|.
.L-J.
.....
You can count the distance each tile in the loop is from the starting point like this:

.....
.012.
.1.3.
.234.
.....
In this example, the farthest point from the start is 4 steps away.

Here's the more complex loop again:

..F7.
.FJ|.
SJ.L7
|F--J
LJ...
Here are the distances for each tile on that loop:

..45.
.236.
01.78
14567
23...
Find the single giant loop starting at S. How many steps along the loop does it take to get from the starting position to the point farthest from the starting position?

Your puzzle answer was 6640.

The first half of this puzzle is complete! It provides one gold star: *

*/

// first part was an enjoyable 2 hours
public class Advent10
{
	static Pipe[][] mazeData;
	static Pipe start;

	static final char N = 'N';
	static final char E = 'E';
	static final char S = 'S';
	static final char W = 'W';

	public static void main(String[] args)
	{
		LoadData(input);
		ConnectStart();

		var forward = new ArrayList<Pipe>();

		// find the loop
		if (start.toNorth().south)
			AttemptMove(forward, start.toNorth(), S);
		else if (start.toEast().west)
			AttemptMove(forward, start.toEast(), W);
		else if (start.toSouth().north)
			AttemptMove(forward, start.toSouth(), N);
		else if (start.toWest().east)
			AttemptMove(forward, start.toWest(), E);

		// is odd loop sizes step count really just / 2 + 1?
		int bonus = (forward.size() % 2 == 0) ? 0 : 1;
		System.out.println("step count to furthest is: " + (forward.size() / 2 + bonus) + " from total loop steps " + forward.size());

//		// for odd sizes, get a reverse loop
//		var reverse = new ArrayList<>(forward);
//		Collections.reverse(reverse);
//
//		// step over until we collide
//
//		// show step count upon collision
	}

	static boolean AttemptMove(ArrayList<Pipe> steps, Pipe toPipe, char fromDirection)
	{
		if (toPipe.part == 'S' || toPipe.part == '.')
			return false;   // do not traverse, done

		// check for incoming block
		switch (fromDirection)
		{
			case N:
				if (!toPipe.north)
					return false;
				break;
			case E:
				if (!toPipe.east)
					return false;
				break;
			case S:
				if (!toPipe.south)
					return false;
				break;
			case W:
				if (!toPipe.west)
					return false;
				break;
		}

		steps.add(toPipe);
		char next = toPipe.WhereNext(fromDirection);
		return switch (next) {
			case N -> AttemptMove(steps, toPipe.toNorth(), S);
			case E -> AttemptMove(steps, toPipe.toEast(), W);
			case S -> AttemptMove(steps, toPipe.toSouth(), N);
			case W -> AttemptMove(steps, toPipe.toWest(), E);
			default -> false;
		};
	}

	static void LoadData(String input)
	{
		var lines = input.split("\n");
		int max = lines.length;     // assume square maze

		mazeData = new Pipe[max][max];

		int across = 0;
		int down = 0;

		for (String line : lines)
		{
			for (char c : line.toCharArray())
			{
				var pipe = Pipe.createPipe(c, across, down);
				mazeData[across][down] = pipe;
				across++;

				if (c == 'S')
					start = pipe;
			}
			down++;
			across = 0;
		}
	}

	static void ConnectStart()
	{
		boolean canNorth = start.toNorth().south;
		boolean canEast = start.toEast().west;
		boolean canSouth = start.toSouth().north;
		boolean canWest = start.toWest().east;
		start = new Pipe(start.part, start.across, start.down, canNorth, canEast, canSouth, canWest);
		mazeData[start.across][start.down] = start;
	}

	/*
		| is a vertical pipe connecting north and south.
		- is a horizontal pipe connecting east and west.
		L is a 90-degree bend connecting north and east.
		J is a 90-degree bend connecting north and west.
		7 is a 90-degree bend connecting south and west.
		F is a 90-degree bend connecting south and east.
		. is ground; there is no pipe in this tile.
		S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
	*/
	record Pipe(char part, int across, int down, boolean north, boolean east, boolean south, boolean west)
	{
		static Pipe createPipe(char input, int across, int down)
		{
			return switch (input)
			{
				case '|' -> new Pipe(input, across, down, true, false, true, false);
				case '-' -> new Pipe(input, across, down, false, true, false, true);
				case 'L' -> new Pipe(input, across, down, true, true, false, false);
				case 'J' -> new Pipe(input, across, down, true, false, false, true);
				case '7' -> new Pipe(input, across, down, false, false, true, true);
				case 'F' -> new Pipe(input, across, down, false, true, true, false);
				case 'S' -> new Pipe(input, across, down, true, true, true, true);
				// '.' default is ground, no access
				default -> new Pipe(input, across, down, false, false, false, false);
			};
		}

		Pipe toEast()  { return mazeData[across+1][down]; }
		Pipe toWest()  { return mazeData[across-1][down]; }
		Pipe toNorth() { return mazeData[across][down-1]; }
		Pipe toSouth() { return mazeData[across][down+1]; }

		public char WhereNext(char cameFrom)
		{
			return switch (part)
			{
				case '|' -> (cameFrom == S) ? N : S;
				case '-' -> (cameFrom == E) ? W : E;
				case 'L' -> (cameFrom == N) ? E : N;
				case 'J' -> (cameFrom == N) ? W : N;
				case '7' -> (cameFrom == S) ? W : S;
				case 'F' -> (cameFrom == S) ? E : S;
//				case 'S', '.'
					default -> 0;
			};
		}
	}

	// the one with the looped maze 140x140
	// hax -- added ground around the border to more easily ignore bounds checking
	static String input =
		"""
		..............................................................................................................................................
		.L7L7F|7-77F7---LF7L7L7.L-7-F7.F-F-77.|.FJ7J7FF|.F-FF7FL..--77F-F--77FF7FFF77L|777F7F--F-FL7F-7F-7FJ7-FJ-77.F-|--F7-J7FJ7F.LF-|JF-FJ777|-|F77.
		.F-|7|JL.J-|--L-7J--|LF-F|L7-L7|L|LJ7FJ--L-7|FJ|7JLLL--JFFJ|LJ-F7J7|7LJF7-FLLJ|L77.LJ.FJ-JL7-JF-7|FFJ.|L7|L-|-7-L--7J|.|-LFJJ.FJ--JJ|7FJLFJ.7.
		.FJ|FF-JFJFJ.J|L|FJ.|J|7---|JL-7.-JLL7LL.7LL-7F--7--FJ..L-F7L|.-J||J7JJ||FL|7-|-|FJJ|-|..7.||FF|L-77JFJ.|LJ|..|JF.FJ-LF7|F|L-7L7F--J.FJ.L|.LL.
		.J.-FJ||L7FJ7.F7.F.-7F7J.L||.L|.FJJ|FL7|||FL-LJ-LFJ.|-|7..|.F7|--|JJ||-77L--|-|J|LJFJ||.FJ7JFJ-|.|LL-7.FLJ7|F-7-77J|J..LFLJJF|7LJL7-F|-F.|7.|.
		..F7.FFL.FF|-FF7FJ-7FLJ7-LLJL7F7-FF7|.LFL77.J.|F|JFL-7J|J7L7L|-F.|LL||J.L7|||LL777LL7-J7JL|-7|.|7L7FLLL777F7JFLFJ..7.F.7|.||LF7.||L7L|-L-|7.F.
		...|-LJ7.|L|.L|L|J.J7JFJ.F|J|FJ.-JLJ--7J-|-|JFFLJLF|L--JL7JJJL||L--|LF-LJ|7L7.JL---F||L7J7JFL-F|7JLJ.|F|-7|L-F7.FF-JF-.F77F7JLL7|LJ7J.|.L7J||.
		.F7.LF||JJFJ--F7|F7LF.|LFFF-JL7-J.J.|-|L-|-L.7.F7.|L.LLJ-|-|F-FL-J7---....L-7-7L|J-J.F7JF777|F|7--F--F7.|L7J-|J-7LLFL-J-JLL|-7L|--F---L--|7FJ.
		.|7|.L7..FJ.||LLLF7..-J-F7F7FFL.L|F-7.|JLL7JFL-F7--77.|J7|LF-7LJ7L7L|L-|-|-FF-FF7J-JF||F||F77|F7-7|JFL-7L7|7F7..L7-|.-LJFJ.|L|F-7FJLF.LLFJ7LJ.
		.L-JFLF7-7JFFF7LLLFJJ7LF||||F7J-7F|FFLL---JJLL--J|.L77---7-.LLL-F-77F77|.|F|.-J||..FFJL7||||77||J.JJ|-|7LJ-F7|FFF-F7.||FJJLL-J||L7|FLF-.|-JJ..
		.|LF--||LL-FL7JLFFJL|7.FJLJ|||7.|L|F7JFJ7-||.L|FF77.|LJL7|-LJJL7F-F7|L-7-7||-FFJ|F-7L7FJ|LJL7FJ|-F-7.L|--|.|L7-7J.L.F-|J7|LFJ7LJJLF7F|J.F-|77.
		.F.--LJ7F|FL-7J-J7|7F77L-7FJ|L7F7F-J|F|7F77F-7.F|L7-FL7.L|.||FF|LFJLJF-J||L7--|FJ|FJFJL7L--7LJFJ.L|L|7LFF-FL7|.|FF-7|LFJL-.L-L-J|FLLJ77-J.-J7.
		.L77F7|FL-|JL|7LL77F|L---JL7|FJ||L-7|7F7|L7|FJ-FJFJFLJJ7..FFFF7JFL--7|F7F7J||||L-JL7L-7L7F7L7FJF7-FFF7F|..LL||F-7F7.F.L7JLF7|L..F-LJ.L7-|J-JJ.
		.-7--LLJJ-|FLLJ-FJ|FL---7F-J|L7||F7|L-JLJFJ|L7JL7||7JLL-.|-F7|L77F7FJLJ||||F7-L7F-7|F-JFJ|L7|L-JL--7F7F--7.L||L7||L7.F7LF-J-L.7.FJL.77|.|F-LJ.
		.|||7LL7L.F|J.L7|7|--F--JL7FJFJ||||L----7L7|FJ7F|L7.|...FL7|||FJFJ|L7F-J||FJL7.|||LJL-7L7L7||F-----J|||F-J7-||FJ||FJJ7|.F..7LLJF|7.FLF-7-LJLF.
		.LL|J||LJ-7J..L||-J|.L--7FJL7|FJ|||LF---JFJ||FF-JFJF7F77JFFJLJL7|FJFJL7FJ|L7FJFJL--7F-JFJJ|LJL----7FJ|||L|.FJLJFJ|L77F|7..FL7J.F7--7FJ-LFJ.F7.
		.|F|-F7J7JLF7J-F7FF7-LF-JL--J||FJ||FL-7F7L-JL7L-7L7|LJ|7--L---7|||FL7FJL7|FJL7L-7F-JL7FJF7|F-7F---JL7||L7JFL7F7L7|FJ77.7FLL-JLFLF7|-L7FF-J--7.
		.FLL7L7L7--||L.JLFJL7FL-----7LJL-JL-7FLJL7F--JF7|FJL7FJF7JF7F-JLJL77|L7FJ|L-7|F-JL7|FJL-J||L7LJF-7F-J||FJFF7LJL7LJL--7JL|LL|F--JL|-7-FF7.FF.|.
		.LJ.F|-77J.|7L|-|L-7|F7F77F7|F----7FJ7LF7|L--7|LJL7FJL7||-||L---7FJFJFJL7|F7||L--7L7L--7FJ|FJFFJFJ|F7||L-7||J|FJF----J7FLF77-J|FF|F--7--F7-7-.
		.7-7|J.L-7JJF7.FFF7||||||FJLJL-7F7LJF7FJ||F7FJ|F--J|F-J||FJ|F7F-J||L7|F7||||||-F-JFJFF7|L7||F7L7L7LJ|||F-J||F7L7L77F7.FF7||7.|F7JJ|J.LJ||L-JJ.
		.F--L-F-J7|FFF---JLJLJLJ|L--7F7|||F7||L7LJ|LJFJ|F--J|FFJ||FJ||L-7L7-|||LJLJLJL7|F7L7FJ|L7LJLJL7L7L7FJ|||F7||||FJFJFJL7FJ|||.F7LJ7FJ|F7.F|J7FJ.
		.|L7|FJ7LF|J||F-7F-7F--7|F7FJ|LJ||||||FJF-JF7L7|L--7L7|FJ||7||F-JFJFJ||F7F--7FJLJL7|L7|F|F----JFJFJ|FJ|LJ||LJ|L7L7|F-JL7||L-77J-FF-FJJF|JJFJJ.
		.|7F77LFFJ||L||7LJ-LJF-J|||L7L77||||||L7L-7|L7|L-77L7LJ|FJL-J||F7L7|FJLJ||F-J|F7F-JL-JL7|L---77L7L-J|7|F-JL-7|||FJ||FF7|LJF-J7J-L-JLJ.L.|FL7F.
		.L||L-.L-.LJ.LJ-|F7JFL-7LJL-JFJFJLJLJ|FJF7||FJL7FJF7|F-JL7F--JLJL7LJL--7LJL-7LJ|L--7F--J|F---JF-JF-7|FJ|F7|FJ|FJ|FJL7||L-7L--7J7|JLJ7-|F||L|J.
		.F-JJLL|J|J.F||FFJ|F7F7L--7F7L7L----7||FJLJ|L7FJL7|||L7F-JL-7F7F7L7F---JFF-7L-7|F7FJL-7FJL7-F7L-7L7||L7|||FJFJL7||F-J|L7FJF--J.FL7.FJ7LFJFJ||.
		.|FJ-F.|FL-LF--7L7|||||F7FLJL7|F7FF7|LJL7F7L7||F7|||L7|L--7FJ||||-|L7F7F7L7|F7|LJLJF--JL-7L7|L7FL7|LJFJ|||L7L7FJLJL--JFJ|FJF7J7LL.FJ-J||FJLFJ.
		.J7|LL||L.LL|F7L-JLJLJ||L7F7FJLJL-JLJF7FJ|L7||||LJ|L7|L-7FJL7|||L7L7||||L7|LJ|L--7FJF--77L7|L7|F7|L7FJFJ||FJFJL7F----7|FJL-J|F7J.F-7.F-||7.LJ.
		.L||--7-FFF7LJ|F-----7|L7LJ|L---7F7F-J||||FJ|||L7FJFJ|F7|L-7||||FJFJLJ||FJ|F7L---J|7L-7L7FJ|FJLJ|L7||FJF||L7|F-JL---7||L7F--J||7F|LJ-7LL--7|L.
		.FF7|L|--7|LJ7LJF7F7FJL-JF7L77F7||LJF7|L-JL7|LJFJL7|FJ|LJF7||||||FJF--J|L7LJL-7F-7L7F7|FJ|FJL--7L7||||F7||FJLJF7F-7FJLJFJL-7FJL7F7-|7J||JF7JJ.
		.FL7F.|7F|7..FF7|||LJF-7FJL7L-J|LJF7||L---7||F-JF7|LJFJF7|LJ||||LJFJJF-JFJLF-7LJF|FJ|||L7||FF--JFJ|||||LJLJF--J||FJL7F-JF--JL7FJ|L7F7JL|77LJJ.
		.7-|.FL-L-F-FF||||L--JLLJF7L--7|F7|LJL----J||L7FJ||F7L7|||F7|||L7FJF7L-7L7FJFJF-7||||||FJ|L7L7F7L-JLJ||F7F-JF7FLJL--JL-7L-7FFJL-JFJ||L||L7.F7.
		..LLL|JF.|L-LJ|LJL7F--7F-JL---J|||L--7F7F-7||FJ|FJLJ|FJ||||LJ||FJL-JL7FJFJL7|FL7||L7|LJ|FJFJ|LJL--7F-JLJ|L--JL7F----7F-JF-JFJF7F7L-JL--7-7F|..
		.777||-J-L-7.FL-7FJL-7|L-7F---7|||LF7LJLJ|||||FJ|F-7|L7|LJL7||||F----JL7L7FJL7FJLJFJ|F-J|FJF7F7F--JL7FF7|F---7LJF---JL-7|7FJFJ||L7F----J-|7J7.
		.J--F|JF||LJ-F--JL---JL7FJ|F--JLJL7||7F---JLJ||FJL7||FJ|F--JFJLJL-7-F7FL7|L-7|L7F-JFJL-7||L||||L---7L7|||L--7|F-JFF--7FJ|FJFJFJ|FJL7J7LJ.LJLL.
		..|.LJ-F-F-JJ|F------7FJL-JL7F---7||L7L--7F--J|L7FJ||L7|L7F7L--7F-JFJ|F7||F7||FJ|F7L7F-J|L7||||F-7FJFJ|LJF--JLJ.F7|F-JL7LJFJ7|FJL--J.|-FLFJ-7.
		..-7F.F.FJF|7||F-----JL7F7F7LJF--J|L7|F7||||F7|FJL7|L7|L7|||F7L||F7L7|||||||||L7||||||F7|FJ||||L7|L7|FL-7|F77F-7|||L--7|F7L-7LJF7F7.F|JJ|JFJJ.
		.FF-JFL-JF7.F||L--7F--7LJLJ|F7L--7|FJ||L7|L7||||F7|L7||FJ|||||FJ|||FJ||||||LJ|FJ||L7|||LJ|FJ||L7|L7|L7F7|LJL7L7LJLJF--J||L--J-FJLJL--7--F-LJ7.
		.FLJ.L.||L---|||F7LJF7L-7F7LJL-7F||L7|L7|L7|||||||L7||||FJ|||||F||||FJ|LJ|L-7|L7||7||LJF-JL7||FJL7|L7LJ|L7F7L7|F---JF7FJL--7F-JF---7FJJ.F77-7.
		.|JL-JL-|FJLFLJF|L--JL--J||F7F7L-JL-JL-J|FJ|||||||FJ||||L7||||L7||LJ|FJF-JF-JL7||L7LJF-JF7FJ|||F7||FJF-JFJ|L7LJ|F7F7|||F--7|L7FJ7F7LJL7.|||7J.
		.|7F|-L.|7--FJF-L-7F7F7F7|LJLJL-7F-----7|L7LJ||||||FJ|||FJ|||L7|||F-JL7|F7L--7LJL7L-7L7FJ|L7|||||||L7L-7|-L7L7FJ||||||||F7LJFJ|F7|L-7J|7JJ.|..
		.F--7.LJ-J||LF7-F-J|LJLJLJF-----JL7F---JL7L-7||LJ|||FJ||L7||L7LJLJL7F7|||L7F7L-7JL7FJFJL7L7|||LJ||L7L7FJ|F-J||L-JLJLJLJLJL-7|FJ|||F-JF77J|.F7.
		.LL.F77|.LJ7J|L7L--JF7LF-7L-----7FJL----7L7FJ||F7LJ|L7||LLJL7L---7FJ|||||7|||F7|F-JL7L7FJFJ||L-7||FJFJL-JL--7|F------7F-7F-J|L-JLJL7F|L7LL77J.
		.-JFJL7-|7-F7|FJF---JL-JFJF7.F7.LJF7F-7FJFJ|FJLJL7FL7|LJF---JF---JL7|||||FJ|||||L7F7|FJL7L-J|F7|||L7|F------JLJ7F7F7JLJFJL--JF-----J7|FJ||F7..
		.|F-7LF-J.L|LJL7L-7F---7|FJL-JL---J|L7LJJL7|L7F7FJF-JL7FJF7F7L---7FJ|||LJL7|LJ||7||LJL7FJ-F-J||||L7||L----7F----JLJL--7|F-7F7L----7|FJ|F77|LF.
		.|L7|.|F.F7L--7L-7LJF7FJLJF-7F----7L-JF77FJL7LJLJ|L7F-JL7|LJ|F-7FJL7|||F7FJL-7|L7|L7F7LJF-JF-J|||FJLJF----J|F----7F7F-J||FJ||F7F-7L7|FJ||F77..
		.-JL7.7L7|FJ|LL-7L--JLJF7FJLLJF7F7L---JL7L-7L--7F7FJL--7||F-J|FJL-7|||||LJF7FJ|FJ|FJ||F-JF7L7-||||7F7L--7F7||F--7LJ|L77LJL-J||LJLL7LJL-JLJ|F7.
		.L7LF7|.J7LFJLF-JF7F7F7|||F---JLJL---7F7L-7|F-7||LJF-7FJLJL7FJL-7FJ||||L-7||L7||FJL-J||F7|L7L7LJLJFJL--7LJLJ||F7L-7L7L7-F7F7|L---7L7F--7F7LJ|.
		.LJFL|---J-FJ-L--JLJLJLJLJL------7F-7LJ|F-JLJ-||L-7L7|L--7FJL7F-J|FJ|LJF7||L-JLJL7F7FJLJ|L7L7|F7F-JF7F-JF7F7LJ||F7L7L7L7||||L7F7FJFJL-7LJ|F7|.
		.-.L7JJF|7FLF---7F-7F7F7-F7F7F--7LJ|L7FJL----7LJFFJFJ|F--JL7FJL-7|L7||FJLJL--7F7FJ|||F7FJFJLLJ||L--J||F7|||L7FJLJL-JJL7LJLJL7LJ||-|F7FJ.FLJLJ.
		.|7F|.FJL-F.L--7|L7||LJL7|LJ||F7L----J|F----7|F-7L7L7|L---7LJFF-J|FJ|FJF7F7F-J||L-J|LJ|L7|LF--J|F7F7|LJLJLJFJL-----7F-JF7F-7L77LJFJ||L--7J-|J.
		.F-7-7|F--|-JLFJL-JLJF-7LJF7LJ||F---7|LJF--7|LJFJFJFJ|F7F7|F--JF7||FJL7||||L--JL--7|F-JFJ|FJF-7LJLJLJF----7L-------JL7FJ|L7L7L-7FJFJ|F-7|.F|7.
		.-LJJL-L.F-7|LL---7F7|.L-7||F7|LJLF-JF7||F7|L--JFJFJFJ|||LJL7F-J||||F7LJ|||F-----7||L7.L-JL-JFL------JF7F7L-------7LFJ|7L-J7|F-JL7|LLJFLJ-7JF.
		.|J|J.|--J-L7-LF--J|LJF--J|LJLJLF-JF-JL7||LJF7F7L7|FJFJLJ-F-J|F-J||||L--J||L7F7F7LJL-JF7-F--7FF7F-----JLJL-------7L7L7L--7F7LJF7|LJ|LJ7||LL7|.
		.7L--7L7-|J.F7FJF7FJF-JF-7|F----JF7|F--J|L7FJLJ||LJL-JF7F7L7FJL7FJLJL--7FJL7LJLJL-7F--J|FJF7L-JLJF--------7F7F7F7L-J.L---J|L--JL--77J|L-FJFJ|.
		.|.L-|..F|FF||L-JLJFJF-JLLJL----7|LJL---JFJ|F--JF7-F7L|LJ|-LJF-JL7F----JL7||F-7F-7|L--7LJFJL7F7F-JF----7F7LJLJLJL7F7-F7F7FJF-7F7F7|7|F|F|FF-J.
		.||.FJJ7|LF-JL7F-7FL-JF7F-------J|F-----7L7||F7L|L-JL-JF-JF7.L-7FJL---7F7|FJL7|L7LJF--JF7|F7LJ|L7FL---7LJL-7F---7LJL-J||||FJF||||||.|-|-F-|||.
		.|JL|J.FLFL--7|L7|F7FFJ|L------7FJL-7FF7L-J|LJL-JF7F7F7L--JL7|LLJF----J||||F7|L7L-7L---J|||L--JFJF7F7FJF--7LJ|F7L-7F-7||||L-7||LJLJFJ7JF7|||7.
		..F.7LF|-F---JL7|||L7|FJF-7F7F7LJ7F-JFJL---JF7F7FJLJLJL-----J-FF-JF7F-7|||||||FL--J7FF-7LJL7F7FJFJLJLJFJ7FJFF-JL-7LJL|LJLJF7||L-77|F|F7-F7-F-.
		.|-|LFJ..L----7LJ|L7LJL7L7LJLJ|JF7L--JF7F7F-JLJ|L-------------7L-7||L7LJ||LJ|L----7F-JFJF-7LJLJ7L--7F-JF7L--JF--7L7F7|F7F-JLJL7FJJL-7LLJ||FFJ.
		..F|7LL.F-F7F7L-7|JL--7L7L---7L-JL----J|||L---7L--7F--7F-7F-7FJF-J|L7L7FJ|F-JF---7LJF7|LL7L7F7F7FF7LJF7|L----JF7L-J||LJ|L-7F-7|||.F.|.L7FF-J|.
		.J-JJ7-F--JLJL-7|L--7JL7|-F7|L--------7|||F7F-JF-7LJF7LJ||L7||FJF7|FJFJL7|L-7L-77L7FJ||77L7||||L-JL7FJLJF---7FJL---JL-7|F7LJFJLJFJ7..F7LF|J.L.
		.LF|7F-JF-7F--7|L-7FJF-J|FJL--7F--7F--JLJLJLJF7|FJF-JL7F7L-JLJL-JLJ|FJF-JL-7L7FJF-JL7||-F-JLJ|L---7|L---JJF7LJF-------J|||F-JF7JF-F.F.LJLJ.77.
		.L-.LL7FJFJ|F-J|F7|L7|F7|L-7F7|L-7|L-----7F7-|LJL7|F7FJ||F-7||F|JF-JL7L7F--JFJL7L7F-JLJLL---7L----JL---7F7|L-7|F7LF7F77LJ|L7J||F7LJ--.||J.JFJ.
		.7-|LLLJ-L7|L-7LJ|L7|LJ|||FJ||L--JL--7F7FJ|L-JF-7LJ||L-JLJFJ7L-7-|F7FJF|L7-FJF7L7LJF7F7FF7F7L---------7LJLJF7LJ|L-JLJL7F7L-J.||||7|-LL--.F7L7.
		.LF||.|LJ-LJF-JF7L-JL--JL-JFJL7F7F7F7LJ|L-JF7FJ7L7FJL7F--7L-7J.L-LJLJF-JFJF|FJL7L7.||||FJ||L----------JF---JL--JF7F7F7LJL7L|FJLJL-7777|-F777|.
		.FF|7FJ.|-F7L--JL--7F7F-7F7L7FJ||||||F7L---JLJF77|L-7|L-7L7FJL7L7.|JLL-7L7FJL7||FJ.|LJ|L7|L------------JF7F7F---JLJLJL--7L7FJF----J.LF7-FJ|FJ.
		..LJ|-JFL.|L-------J|LJL|||FJL-JLJ||LJL7F-7F--JL7L7FJ|F7L7LJF7F-7.-7L.L|FJ|F-J-||J7|F7L-J||F7|F---7F----JLJLJLF------7F7L-JL7|F7F-7JF7..L-FFJ.
		.F7FJ7.L-FL--7F--7F7L-7FJ|||F--7F7||F-7LJFJ|F-7FJ-|L7||L7L--JLJFJJ7L.FFJL7LJLJ-LJ7FJ|L--7L-JL7L--7|L----7F----JF--7F-J||F7JFJLJ||FJJ|JFJ7L|||.
		.F|L|-|JFF---J|F-J||F-JL-J|||F7LJLJ|L7|F7L-JL7LJF7L-JLJ|L----7FJ|LF|7-L--JLJJL.|.FL7|F--JF--7|F7FJL----7LJF---7L-7|L7-|LJL-JF7FJ||.F||FL|.F77.
		.-J-L-F.FL----JL-7|LJ.F---J||||F7F-JFJLJL7F-7L--JL7F7LF7F---7LJ-L..F7..FLJ..F|-|7J|LJL---JF7|||||F7F--7L7FL--7L--JL-JFJF7F--J||FJL7JL--777F7J.
		.LF7-F|-LF---7JF7LJ|F7L----JLJLJLJF7L-7F7|L7L7F7F7LJL-JLJF7FJJ7L|-L-7J777-7.-J|L|LF|FF7|F-JLJLJ|LJLJF-JFJF---JF7F77F-JFJLJF7JLJ|F-J.L--FF-JL7.
		.7-7JLJ7LL7F7L-JL---JL------------JL-7LJ|L7L7LJLJL-------J||F77J|FL|JLJ.JJF-.|FJL-F--JL-JF7F--7|JF7FJF7L7L-7|FJLJL7L7FJF7|||F--JL7-|J-FL|L-7|.
		.LFJ7|LF7LLJ|F-7F7F--7F7F7F7F---7F7F7L--J|L-JF--7F7F-----7|LJ|F777-|7J|-|FLJ|.L|.LL-7F---J||F-JL7||L-JL7L--JFJF7F-JFJL-JL7|||F--7|F|7.-.JLJ-..
		..|FF7.L|-LL||FJ|||F-J|LJ||LJF--J|LJL---7F7F7|F-J|||F----JL7FJ|L7JFJ77|.LJJ7L77.L-F7LJF7F-J|L7F7LJL-7F-JF---JFJ|L--JF7F-7LJLJL-7|L7J7-F7.7LJ7.
		.FF7JF7.7-JFLJL7||||F7|F-JL-7L---JF7F--7||LJLJL--JLJL-7F77|LJ-|FJJ|||---|7|F.LJ.|F||F-J||F7|FJ||F--7|L--JF-7FJFL----JLJF|F-7F7FJL-JJ77L77|F-7.
		..F..-|-|LFFJ|LLJLJ||||L7F--J|F7F-JLJF7LJL7F--7F--7F-7LJ|F7LF-J||..F7JJ.FFJJ--|-F-J|L-7|LJLJL-JLJF-JL---7L7LJF7F77F7F7F7LJ7LJLJJF7J-7FL-JF|FJ.
		.|J--JLJ|-FF7LJFF7LLJLJFLJF7F-J|L----JL-7FJL-7LJF7||LL-7LJL-JF-J77FL|JJF|L|-7-L7L-7|F-J|JF7F--7F-JF----7L-JF7|LJL-JLJLJL7F7F7F7.||7-L|.|.LJJ..
		.|LF7.F-|LLF7|.FJ|F7JF7F-7||L-7|F-------JL7F7L--J|LJF-7L7F7F-J.F7-F7|77L7-|-|-|FJ.||L-7L-J|L-7|L-7|F---JF--JLJF--7F--7F-J||||||FJL7L|.FJJ-|J7.
		.F7|J.L-|FF||F7L7LJL-J||FJ|L7FJ|L------7FFJ||F7F7L-7L7||||LJ7.F||F777--7|L-JLJF7F-J|F7L-7FJF7||F7LJ|F7F-J-F--7L-7|L-7|L--JLJLJLJF7L7-77JLL7.|.
		.|F7|LL-|.FJLJL-JF7F-7LJL7L7|L7|F7F7F7FJFJFJ|||||F7L-JL7LJF7J-FJLJL--7JLJ-J7LJ||L-7LJL--JL-JLJ|||F7LJLJF--JF7L--JL--J|F---------JL-J-|J-F7L7J.
		.|7L|JJL|FL7F7F7FJLJFL7F-JFJL-JLJLJLJ|L-JFJFJ||||||F7F7L7J||FFJF-7F--JLJ77.FJFJL-7L7F7F7F7F--7LJLJ|F7|FJF-7|L------7FJL---7F---7LF7|-L7|J|FJ7.
		.LJF|F7.F7|LJ||LJJ-F7FJL-7L------7F-7L---J|L-JLJLJ||||L-JFJL7L7||LJJFL7LF-7.FJF-7L-J|||||||F-JF--7LJL7|FJ.LJF------JL-7F-7LJF-7L-JL77.LF-7-.|.
		.|L|--JL|L---J|F-7FJ|L-7FJF7F-7|FJ|JL---7F7F--7F-7LJLJFF7L7FJFJL-7JL|7J-|.F7L-JL|F7FJ||||LJL--JF7L--7LJ|F---J-F7LF7F7.LJFL7FJ-L---7L-7|.JJ|-7.
		.L-J..|L|F7F7FJL7|L7L--JL7||L7L-JFJF---7LJLJF-J|FJF-7.FJL7|L7L7F-J|7LL7JLFJL7F-7LJLJ|LJ||F7F---JL---JF-JL-7F--JL-JLJ|F---7LJJF7F7FJF-J77|LJ7J.
		.|J|-.FFLJ||||F7|L7L7F--7LJL-JF7FJ7L--7L-7F7L--JL7L7L7|F7||FJFJL---77F|JLL-7|L7|F----7FJ||LJF7F------JF--7||F---7F-7LJF-7L---JLJ|L7L-7J7F7|LJ.
		.|-L7---77LJLJ||L7L-JL7FJF7F--J||F---7L-7LJL-----JFJFJLJ||||FJF-7F-JJF7.F7FJL7||L---7|L7||F7|LJF--7F-7L-7|LJL--7LJ7|F-J.L7F7F7F7L7|F-JL7|LJ77.
		.|FL7.LJL77F7FJL7L7F7FJL7|LJF--J|L--7|F7L--7F----7L7L7F7|LJLJFJ.LJJF7|L7||L-7|||FF7FJ|FLJLJLJF7L7FJ|FJF-JL-7F-7L--7|L--7JLJLJLJL-JLJ-|FJJ|.||.
		.|77L7JFJF-JLJF7L7LJLJF7||JFJF7FJ-F-J||L--7LJF---JFJFJ||L-7F7L---7FJ|L7LJL7FJ|||FJ|L7L-7.F7LFJL-JL-JL-JF--7|L7|F--JL7F7L---------7J|.-F-LF||F.
		.L7-FJFJ-L7F--JL7L----JLJ|FJFJLJ.FJF7LJF7.L--JF---JFJFJL7||||F---J|FJJL--7||FJ||L7L-JF-JFJL7L---7F7F7F7|F-JL-J|L---7||L7F7F7F7F7FJ-7L.J--|LFJ.
		.7L7|L|-7FLJF---JF7F7F7F-J|FJF--7L-JL--JL-----JF--7L7L-7L-J||L7F-7||F7F7FJ|||FJL7L--7L-7L-7|F7|FJ|||LJLJL-7F-7L7F--JLJ|||LJLJLJLJFLL|-.-|JFL7.
		.L7JF7|F-7-FL--7FJ||||||F-J|FJF7L----------7F-7L-7L-JF7L7F-JL-J|FJ|LJ|||L7LJ||F-J-F7|F7|F-JLJL-JFJ||F-7F-7LJFJJ|L---7|FJL-7F-7F7F7J7..FF---J7.
		.FF7JF-J.J-F---J|F||LJLJL--JL-JL7F--------7||FJF-JF-7||||L-7F--JL-JF7LJ|JL-7|||F7FJ|||LJL--7F7F7|7LJL7||FJF-JF7L-7F7L7L7F7||FJ||||LF7F-7|L-L|.
		.--J7LJJ7|-L7F-7|FJ|F---7F-----7LJF-------JLJL-JF7|FJ||FJF-JL---7F-J|F-JF7FJLJLJ|L7LJ||F7F-J||||L7F--JLJL-JF7||F7LJL7L7LJLJ|L7|||L-7LJLLFJJ.|.
		.FF|JJ7.FFJ|||FJ|L7|L--7|L--7F7L--JF------7F7F--J||L7|LJFJF7F--7LJF7||F-J|L7F7F-J7L-7L7||L-7|LJL7||F7F7F7F-JLJLJL-7.L7L-7JFJFJ|LJF-J-J7.|..FJ.
		.F|JJJ|7-|LLLJL7L7|L7F-JL7F7LJ|F---JF7F7F7|||L--7||FJL-7|J|||F7|F7|LJ||F-JFJ|||F7F7||FJ|L-7LJF7-LJLJLJ||||F-------JF7L7FJFJFJFJF-J.|FFF-7.LLF.
		.7LL.7J|LJ--FF7L-JL-JL7F7LJ|F7LJJF--JLJLJLJ||F7FJLJL7F7|L-J|||LJ||L-7||L7FJFJLJ|LJL-JL-JF-JF7||F7F---7LJLJL--------JL7LJLL7L-JFJF--77-|--7-L|.
		.-.L7F-7-LJFFJL-77.F7JLJL-7|||F-7L---7FF---JLJ|L--7FJ|||F--J|L7FJ|F7|||FJL7L7F7L-----7F-JF7||||||L--7|F7F-----------7L-7F-JF7FJ|L7FJ7|L-|JJ.J.
		.L|F7.FJ.J.FL--7L7FJL-7F--J|||L7L7F--JFJF7F---JF--JL-JLJ|F-7|FJL7|||||||F7|FJ||F7F-7FJL7J|||||LJ|F--JLJLJF7F7F7F-7F7L--JL7FJLJF7FJ|---|7F-.J7.
		.FF-7-F-7||.F7|L7|L--7|L--7LJL7L7|L---JFJLJLF7LL----7F--J|FJ||F7|||||LJ|||||FJ||||FJL7FJFJLJ|L7FJL-----7FJLJLJ||FJ||F7FF-JL---JLJFJJ.F|||.F-|.
		.F7FJ||FJLLFJL--JL---JL-7LL7F7L-JL----7|-F-7||F7F---J|7F7||L||||||||L-7LJ||LJFJ||||LFJL7L7F-JFJL7F7F7F-J|F---7LJL-JLJL-JF7F7F----J|.F-JLJFJL|.
		.LJJ.FF|FJLL7F----7F--7FJF7LJL--7F---7|L7L7LJLJ|L---7L7|||L7|LJ|||||F7L-7||F-JFJ||L7L-7L7|L-7L7FJ||||L-7|L--7|F7F--7F7F7|||||F--7F7FF|7.FF7JF.
		.F|-JJJF.|.LLJJF7|LJF-JL-JL7F7F7||F--JL-JLL7F--JF---JFJ|LJFJL-7LJLJ||L--JLJL-7L7|L7|F7|FJL7FJFJL7|||L-7||F--JLJ|L7|LJ||LJLJ|LJF7LJL7.|FJJLJFJ.
		.7.L|J.J-L-7||FJ|F7|L7F7F-7LJLJLJ||F--7F7-FJL7F7L---7L7|F-JF7LL-7F7|L----7F--JFJ|FJ||LJL7FJ|FJF-J||L7FJLJL7F7F7L-JF-7|L7LF-JF-JL--7L7-JFF|.L7.
		.|.F|JF77L|.F7L7||L7|LJLJ.L---7F7|LJF-J||FJF-J||F7JFJFJ|L7FJL7F7||LJ.F7F7||LF7L7|L7||F--JL7|L7|F7|L7|L-7F-J||||F77|FJL7L7L--JF---7L7L7LJ-JF77.
		.J7-JF-7L7-FJL-J||FJF7F77F7F7FJ|LJF7|F7||L7|F-J|||FJFJLL7LJF-J|LJ|F7FJ||||L7||FJL7|||L-7F7||FJLJ|L7||F-JL-7|||||L-JL-7L-JF---JF7FJ-L-J|FL-FJ|.
		.|.|JLFF|7-L---7||L7|LJL-JLJ||FJF-JLJ||||7||L7FJ||L7|-F7L-7|F7L7FJ||L7|||L7|||L-7||||F-J||||L7F-J7||||F7F7LJLJLJF----JF7J|F--7|LJ7.|JL|77LL-7.
		.F-77.-|-F-----J||FJL----7F7LJ|JL-7F7||||FJL7||FJL7|L7||F7|||L7|L7|L7|||L7|||L7FJ||LJL7FJLJ|FJL7F-J|||||||F7F-7FJF--77|L-JL-7|L--7-|.-L7-7.-L.
		.|7LJ.||LL-----7|||-F7F--J|L7FJF--J|LJ||||-FJ|LJF-J|FJ|||||LJ7||-||LLJLJFJ||L7||FJ|LF-J|F--JL7||L-7|||||||||L7|L-JF7L7|F----JL7F-JF|7L-L7--LJ.
		.|L77.-F7-F---7|LJL-JLJF7FJJ||LL--7L7FJ||L7L7|F-JF7||FJ|||L7F-J|FJL-7F7.L7||FJ|||FJFJF7|L-7F-JFJF7|||||||||L7||F7FJL7LJL-----7|L7--L7.L|L-77J.
		.L7L7FF|L-JF-7LJF----7FJ||F-JL--7LL7|L7|L7|FJ|L7FJ||LJ-LJ|FJL7FJL7F7LJL-7||||FJ||L7|FJ|L7FJL-7L7|LJLJ|||||L7||LJ|L-7|F-----7FJL-JFJJ||-F|..J7.
		..|F.LFL-7FJ7|F7L7F--J|FLJL7F7F7L-7LJ||L7LJL7|FJL7|L77F--JL-7||F7LJL7F-7||||||FJ|FJ|L7L7||F-7|7|L--7FJ||||FJ|L-7|F7||L----7LJF|J-F7||-7L7-7L|.
		.LJLFLFJLLJ7FJ||FJL7F7L-7F-J||||F-JF--JFJ7F-J||F7|L7L7L7F7F7||LJ|F--JL7LJ||||LJFJL7|FJF||||FJ|FJF7FJL7|LJLJ7|F7|||||L----7L--7|FF7F|J.L7|J|||.
		..L-7JL7F---JFJ||F-J||F7||F7||||L7FJF7FJF-JF7|||||.|FJFJ|||LJ|F-JL-7F7L7FJ|LJF-JF7|LJF-J||||FJ|FJ|L-7|L--7F7||||LJ|L----7L---JF-7LF|L|.FJFJF7.
		.F7JL7.LL---7|JLJL--J||||LJ||||L7|L7|||LL--J||||||FJ|FL7|LJF-JL--7FJ|L7|L7L-7L-7||L7FJF7|||||7LJJL7FJ|F-7LJ|||LJF-JF7F--JLF7F7|FJF-L-F-77|-7J.
		.|L7FF-7FLF-J|7F7F7|FJ||L7-||LJLLJFJ||L7F---J|||LJL7L7FJL-7L-7F7FJL7L7|L7L7FJF-J|L7||FJLJLJLJF----J|-|||L7FJ|L-7L7FJ|L----JLJLJL-7.FF|.LLJ7L7.
		..-J|J.L|L|F-JFJLJL-JFJ|FJFJL-7F--JFJ|FJL7F-7||L--7|FJL7F7|F-J|||F-JFJ|FJFJL7|F7L7LJ|L---7|F-JF7F--JFJL-7LJL|F7L7LJFJF7F7F7F-7F7FJ7FJF-|F|77F.
		.-7L7F7JJ.LJ.FL7F-7F7|FJL7|F-7||F-7|FJ|LFJ|F||L7F7||L7FJ||||F7|LJ|F7L7LJJ|F7|||L7L-7|F-7FJFJF7|||-F-JF-7L--7||L7L7|L7|LJLJLJ-LJLJF7LFJF|--F7..
		.L|FJ-FF7J.|-F-J|FJ|||L7FJLJ||||L7LJ|FJFJFJFJ|L|||||FJL7|||LJ|L-7|||FJFF-J|LJLJ|L7FJ||FJ|FJFJ||||FJF7L7|F7FJ|||L7L7J|L-----7-F--7J|||--|FFFL7.
		.-JL|7L|||7JFL-7|L7||L7|L-7J-LJL-JF-JL7L7||L7L7||LJLJF7||LJF-JF7||||L-7L7FJF--7F-JL7LJL7|L7|FJ|||L7|L7|LJ||FJL-7|FJFJF7F7F7L-JF-J7F7|JFLF-FLJ.
		.FF|L|.LFF-7JLFJ|FJ|L7||F-J--F----JF7FJFJL-7|FJLJ7F--J|LJF7L-7|||||L7FJFJ|J|F7LJF7FJF7.||FJ|L7||L7||FJ|F7LJL-7FJ||LL7|||LJ|F7FJLJ7F7JL|-JJ|-L.
		.-.L.J7--.FJ7JL7|L7L7LJLJJ.|LL--7F7|LJ-L-7FJ|L--7FJF-7L--JL--J|LJ||FJL7L7L7||L7FJ|L-JL7||L7L7|||FJ||L7LJL7-F-J|J|L-7|||L-7LJ|L-7JLJFJLJ7J.LFJ.
		.LF-7||-|F-7L7LLJ.|FJJ.||7F--7.FJ|LJF7F7FJL7L7F-JL7L7L7F7F7F7FJF7LJL-7L7|FJ||.|L7|F---JLJ7L7||||L7|L7L7F7L7L-7L7L7FJ|||F-JJL|F-J7|-L--7F--F-7.
		.L77FL7-L7L77JJ7.FLJJL-L.-7..F-|FJF-JLJLJF-JFJL-7FJFJFJ||||||L-JL-7F-JFJ||FJ|FJFJ|L----7-F-J|LJ|FJ|FJFJ|L7L7||FJFJ|F|||L---7LJJL7JF-7.L7|7JF|.
		.||F|-7.|J-F|-F7777.J7LL7L--J.FLJ-L-7F7F7L7JL7F-JL7|FJFJ|||||F7F-7||F7L7LJL7|L7L7L7F7F7L7L-7L-7LJ-LJFJFJ-L7L7|L7L7L7|||F7F-J-L7J|-F-J-.L7L777.
		.-LJL-J7J7.LJF|L-LJJ7|J.L-J..F77.F.LLJLJ|FJF7LJF--J|L-JFJ|||||||FJ|||L7L--7LJL|FJF|||||FJF-JF7L-7F--JFJF--JFJL7L7|FJLJ||LJJ|--|7J7|.L-J7J|||J.
		.FLFJL|77|-|-F|7LL.F-JF--7--L7LJ-|.|F---JL-J|JLL7F7L-77L-J||||||L7|||FL7F7|FF-JL-7||LJLJ||F-J|F-JL-7FJFL7F7L-7|FJ|L-77LJ.LJJ-7J7-F7.FL-J-FJJF.
		.|.|.F-77J.F7.|-7LF|J7L.|.F|F-J..LF-JF---7F-J.F7LJ|F7L7|F-J||||L7LJ|L7FJ|||FJF-7FJ|L--7F-JL7FJ||F--J|F7FLJ|F-JLJL|F7L7J-LJ7.F|L-J.777.F7FF.F|.
		.|FLLLJ|L-7|F-JLJ-FF-7.F-F|-L-F-J7L7FJF--JL77FJL--J|L7L7L7FJLJL7L-7L7|L7|||L7|-|L7L7F-J|F7FJ|FJFJF7FJ||F7FJL----7|||FJ.F|LF-|J.L-7LJ.F-||L.7-.
		.|||-L7|7L-LJ...JFF7-F|J.|...7L77LL||FJF7F7L7L---7FJ-L7|FJ|LF--JF7|FJL7||||-LJFJFJF|L7FJ|||FJ|L|FJ|L-JLJ|L7F7F7FJLJLJJ|FF7L||FFJ.|7LFF-|FF-J|.
		.LL|J|F--FJ.FFJ7.L.LLJJF|J7-F7--7LLLJL7||||FJJF--J|7F-J|L7L7L-7FJ||L-7|LJLJ77FJFJ7FJFJL7|S|L7L7||-|F7F-7|FJ|||LJJJLF|FL7.FFL7JLJ-F||LF.|-7.F7.
		...|.7J.FF|-7J.|FF-..J-7J.|.LJ.LJJ|LL.||LJLJ7.L7F7L7L7FJJ|FJF7||FJL-7||J||.L-L7L7F|FJF-J|LJ-L7|||FJ||L7||L-J|L-7JJF77F7|7F7FJF-.LF-|.J.|LL-J|.
		.F.FLJ.F-77LF7-L-J.F.|F7.|.7.FJ7|.|.J-LJ-||LJ7LLJL7L7LJF-JL-J|||L7F-JLJ-7---|7L7L7||LL-7L-7F-J|||L7|L7|||F--JF-J|77LJLJLL7|J77J7FJJ|F.F7..|F7.
		.7-FJ-LJFL77L-.L|.-L7--7.|-L.|FFJ-F7LF-|-FL-|7F|FFJFJLFJF7F--JLJJLJJJ-JJJ||-FFLL7|||FF-JF-JL7FJ||-|L7|||||F-7L7J-F7||.L|L||7F.F7F7-L77F----||.
		.L-J||..JLJ|F|FLFF7L-F-|F7.L-7-|F||L7..|--J|LJFL7|FJJJL7||L7|JLJ.|.FJ..|F7|FFLJ-LJLJ-L-7L7J.|L7||F|FJLJLJLJFJFJJL-L--.FJ-L||.FFJL--.LL|J7.|LF.
		.|7FJF7-J7.|7|L-7L77|LJ.||7.|JFF7.L-L7-F7.F7|F|JLLJJ|FLLJL-J--7JF7J|F---L7F7LJ|7|.LLF.LL-J7LL-JLJFJL--7LFF-JFJ7|-L|FJ7J7.LLJFJ|L||L7FJ||-77|L.
		.JF-J7JJ.--F-JJJ|||FJFLFJL77|.LLJ--J7JJL|-LL|-J.|.|.LJ.L|J||-7.|L--7F|-|LLL|..77|J.L|F.|JL7L|.L-LL7F--J-LL-7|LL--FFJLJF-7LJ-L.JLJ7|F7J|7-LL7|.
		.LFL-J--7|||J|F-FJ7|LL77-.F-L--LL-L|JLFFF.FL7FL-7--|.FJ-J7LJJLF77|LL7J.|-77|F7.FL-FF7JLJL7|FL7|.LLLJJFJ|J-|LJJ.|.F|JFLFL-.|-JJ.F.--|77L7JF|FF.
		.||J-||J|FFJ.FL-L|-F-.L|-7JJ.|7LL7..7L-FF77-LF7-J|FJFJ7F.L-J-7LFF-7.FJFL7LF-7J--JFLFJ77.F|77|JF7.F|7LLJ----J7LFJ-J|-J-7-.FL.||F|-|L-JL7J7|F7J.
		.F||J|J.FF7FFJL-FJL|.FJ|.L7-F-7.L77.L.L-L-7LL|L.|7L77LFJ7|LLL|--L-7-F7J.|.F-J--|L-L|LF-LJ|L-77-J7FL7-FLLF.|.--L-F.7FLF|.7-J.F-LJ.-F7L7L-F7LLJ.
		..F|7|JF|.F7.FF-777LJ|L-7LF.LLLF7L|7F7LL-L7.L|J7FF7J7.|-.L7F-J||L7|L|JJ.L7.FLF7L7L7-|J||7-|FLL7FF77J-FF7LL7.L7LFJJLF7L-7|-F7L---L7LJL--7LL7...
		.7L-7JLJLJ.L-JLL|-LJL|JJF-J7-7L-J-LF-JJ.--F7-JL-7JJ.L--.J-JL|L-F--F-|J.-JJ-7LL-.L-JL.L|7-JLLJJ-J-LJJ.J.-L-J-L7-F-|.JJ--7JLLFJJ..LL-JL|-F.JJ.J.
		..............................................................................................................................................
		""";
}
