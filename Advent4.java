import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
--- Day 4: Scratchcards ---

The gondola takes you up. Strangely, though, the ground doesn't seem to be coming with you; you're not climbing a mountain. As the circle of Snow Island recedes below you, an entire new landmass suddenly appears above you! The gondola carries you to the surface of the new island and lurches into the station.

As you exit the gondola, the first thing you notice is that the air here is much warmer than it was on Snow Island. It's also quite humid. Is this where the water source is?

The next thing you notice is an Elf sitting on the floor across the station in what seems to be a pile of colorful square cards.

"Oh! Hello!" The Elf excitedly runs over to you. "How may I be of service?" You ask about water sources.

"I'm not sure; I just operate the gondola lift. That does sound like something we'd have, though - this is Island Island, after all! I bet the gardener would know. He's on a different island, though - er, the small kind surrounded by water, not the floating kind. We really need to come up with a better naming scheme. Tell you what: if you can help me with something quick, I'll let you borrow my boat and you can go visit the gardener. I got all these scratchcards as a gift, but I can't figure out what I've won."

The Elf leads you over to the pile of colorful cards. There, you discover dozens of scratchcards, all with their opaque covering already scratched off. Picking one up, it looks like each card has two lists of numbers separated by a vertical bar (|): a list of winning numbers and then a list of numbers you have. You organize the information into a table (your puzzle input).

As far as the Elf has been able to figure out, you have to figure out which of the numbers you have appear in the list of winning numbers. The first match makes the card worth one point and each match after the first doubles the point value of that card.

For example:

Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
In the above example, card 1 has five winning numbers (41, 48, 83, 86, and 17) and eight numbers you have (83, 86, 6, 31, 17, 9, 48, and 53). Of the numbers you have, four of them (48, 83, 17, and 86) are winning numbers! That means card 1 is worth 8 points (1 for the first match, then doubled three times for each of the three matches after the first).

Card 2 has two winning numbers (32 and 61), so it is worth 2 points.
Card 3 has two winning numbers (1 and 21), so it is worth 2 points.
Card 4 has one winning number (84), so it is worth 1 point.
Card 5 has no winning numbers, so it is worth no points.
Card 6 has no winning numbers, so it is worth no points.
So, in this example, the Elf's pile of scratchcards is worth 13 points.

Take a seat in the large pile of colorful cards. How many points are they worth in total?

Your puzzle answer was 27845.

The first half of this puzzle is complete! It provides one gold star: *

--- Part Two ---

Just as you're about to report your findings to the Elf, one of you realizes that the rules have actually been printed on the back of every card this whole time.

There's no such thing as "points". Instead, scratchcards only cause you to win more scratchcards equal to the number of winning numbers you have.

Specifically, you win copies of the scratchcards below the winning card equal to the number of matches. So, if card 10 were to have 5 matching numbers, you would win one copy each of cards 11, 12, 13, 14, and 15.

Copies of scratchcards are scored like normal scratchcards and have the same card number as the card they copied. So, if you win a copy of card 10 and it has 5 matching numbers, it would then win a copy of the same cards that the original card 10 won: cards 11, 12, 13, 14, and 15. This process repeats until none of the copies cause you to win any more cards. (Cards will never make you copy a card past the end of the table.)

This time, the above example goes differently:

Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
Card 1 has four matching numbers, so you win one copy each of the next four cards: cards 2, 3, 4, and 5.
Your original card 2 has two matching numbers, so you win one copy each of cards 3 and 4.
Your copy of card 2 also wins one copy each of cards 3 and 4.
Your four instances of card 3 (one original and three copies) have two matching numbers, so you win four copies each of cards 4 and 5.
Your eight instances of card 4 (one original and seven copies) have one matching number, so you win eight copies of card 5.
Your fourteen instances of card 5 (one original and thirteen copies) have no matching numbers and win no more cards.
Your one instance of card 6 (one original) has no matching numbers and wins no more cards.
Once all of the originals and copies have been processed, you end up with 1 instance of card 1, 2 instances of card 2, 4 instances of card 3, 8 instances of card 4, 14 instances of card 5, and 1 instance of card 6. In total, this example pile of scratchcards causes you to ultimately have 30 scratchcards!

Process all of the original and copied scratchcards until no more scratchcards are won. Including the original set of scratchcards, how many total scratchcards do you end up with?

Your puzzle answer was 9496801.

Both parts of this puzzle are complete! They provide two gold stars: **
*/

// part 1 took ~45 mins, coulda been faster but OCD.  :)
// Part 2 took ~2.5 hours broken up across multiple time slices throughout the day.
//
// Notes:
// part 2 would be some kind of tree traversal problem, it's been ages, so...  probably recursion-based solution to the rescue here.
// part 2 thoughts:  This feels like a single loop, but "inserting ahead of yourself" while iterating.  So maybe
//   a linked list traversal would be better for this so we could do that instead of building and walking a tree?
// **Note: why am I doing this?  I think I need to leverage chatGPT to help with a few of these things to save time...  :/
// (later...) wow, I really don't like how hard using a listIterator is in java.  No wonder I don't ever use them.  Also,
// chatGPT led me on a merry chase through some lies about how listIterators worked.  Fun times.


public class Advent4
{
	public static void main(String[] args)
	{
		var cards = new ArrayList<ScratchCard>();   // part 2
		var cardsById = new HashMap<Integer, ScratchCard>();

		int winnings = 0;
		var lines = input.split("\n");
		for (var line : lines)
		{
			var card = new ScratchCard(line);
			var win = card.getWinningsTotal();
			winnings += win;
			System.out.println(card.cardId + " winning: " + win);

			// part 2
			cards.add(card);
			cardsById.put(card.cardNum, card);
		}
		System.out.println("winnings total: " + winnings);

		// part 2
		System.out.println("Begin part 2...  original cards size: " + cards.size());

		var itr = cards.listIterator();
		while (itr.hasNext())
		{
			var card = itr.next();
			int wins = card.getWinningsCount();

			if (wins > 0)
			{
				System.out.println("processing card " + card.cardId + " with wins:" + wins);

				// add a bunch before next but after prev, so go forward 1, then back to where we were.
				// this adds in "reverse" order in the list, but we don't really care as long as they are only processed once each.
				for (int x = 1; x <= wins; x++)
				{
					var cardToAdd = cardsById.get(card.cardNum + x);
					itr.add(cardToAdd);
					var which = cardToAdd.cardId.replace(""+cardToAdd.cardNum, cardToAdd.cardNum + "-" + card.cardNum);
					System.out.println("added " + cardToAdd.cardId + "(bonus: " + which + ") overall size is " + cards.size());
				}

				// go back to before our added elements
				for (int i = wins; i > 0; i--)
					itr.previous();
			}
			else
			{
				System.out.println("Skipping card " + card.cardId + " with wins:" + wins);
			}
		}

		System.out.println("total cards with extras: " + cards.size());

		int x = 0;
		for (var card : cards)
		{
			x++;
			System.out.print(card.cardId + ",");
			if (x % 15 == 0)
				System.out.println();
		}
	}

	private static final class ScratchCard
	{
		Set<Integer> winners = new HashSet<>();
		Set<Integer> possibleWinners = new HashSet<>();
		String cardId;
		int cardNum;
		String inputLine;   // part 2

		ScratchCard(String inputLine)
		{
			this.inputLine = inputLine; // part 2

			var cardBasics = inputLine.split(":");
			cardId = cardBasics[0];
			cardNum = getCardNum(cardId);

			var winsAndPoss = cardBasics[1].split("\\|");

			for (String parseWins : winsAndPoss[0].split(" "))
			{
				var val = parseWins.replace(" ", "");
				if (!val.isEmpty())
					winners.add(Integer.parseInt(val));
			}

			for (String possible : winsAndPoss[1].split(" "))
			{
				var val = possible.replace(" ", "");
				if (!val.isEmpty())
					possibleWinners.add(Integer.parseInt(val));
			}
		}

		private int getCardNum(String cardId)
		{
			var num = cardId.replace("Card", "").replace(" ", "");
			return Integer.parseInt(num);
		}

		int getWinningsTotal()
		{
			int total = 0;
			for (var poss : possibleWinners)
			{
				if (winners.contains(poss))
				{
					if (total == 0)
						total = 1;
					else
						total *= 2;
				}
			}

			return total;
		}

		int getWinningsCount()
		{
			int total = 0;
			for (var poss : possibleWinners)
			{
				if (winners.contains(poss))
					total++;
			}

			return total;
		}
	}

	final static String testInput =
		"""
			Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
			Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
			Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
			Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
			Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
			Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
			""";

	final static String input =
		"""
			Card   1: 66 90 67 76 55 13 91 31 95  4 | 82 98 69  8 15  2 32 24 99 56 46 65 60 72 58 68 54 22 26  5 74 25 84 73 61
			Card   2: 94 57 54 45  9 78 71 35 48 44 |  9 56 28 57 55 95 32 48 76 47 94 35 78 91 54 49 38 30 17 63 71  8 16 45 44
			Card   3: 44 10 67 65 62 43 47 13 26 95 | 67 55 65 68 26 77 41 43 94 13 62 27 66 81 29 10 69 95 31 47 70 72 44 23 17
			Card   4: 47 48 14 30 64 31 81 22 65 60 | 85 23 47 19 60 31 32 68 64 33 14 22 65 48 67 72 87 46 92 83 54 81 66 91 30
			Card   5: 43 66 44 76 67 27 52 26 83 39 | 37 83 99 29 48 60 62  2 31 27  4 65 84 89 45 95 78 21 47 44 76 34 96 30 74
			Card   6: 38 34 59 52 10 14 57 98 73 90 | 77 32 21 16 15 57 30 97 43 47 91 39 92 42 87 69 82 35 68 60 78 37 46 50  9
			Card   7: 94 57 27 45 70 34 83 30 51 17 | 89 30 44 29 17 41 70 87 83 45 51 60 27 48 33 43 88 34 67 54 64 94 85 57 26
			Card   8: 64 82 99 10 20 76 25 78 83 48 | 66 62 98 32 48  2 79 97 65 70 89 56 37 44 45 21 75 93 41  9  3 61 92 19 81
			Card   9: 93 49 20 77 14 86 89 28 54 82 | 61 67 64 46 28 89 82 25 49  5 92 37  4 41 15 83 44  6 60 36 77 12 68 50 71
			Card  10: 50 76 67 32 84 46 54 30 42 74 | 42 82 85  2 74 67 23 48 96 30 73 79 38 40  3 45 49 84 59 99 76 33 58 62 13
			Card  11: 55 15 77 31 37 23  2 43 51 35 | 46 93 81 92 48 68 84 75 44 79 26 33 82 59 71 10 65 21 16 40 53 30 80 52 63
			Card  12: 18 99 43 33 20 57 72 70 90 60 | 83  5 17  2 67 51 55 77  3 47 12 94 74 45 68 35 38 37 33 14 96 63 71 30 75
			Card  13: 98 24 76 47  1 29 71 45 82 75 | 23  9 40 65 28  4 34 97 53 30 77 55 59 61 76 35 54 12  2 58 46 72 86  1 17
			Card  14: 71 74 19 46 91 36 35 65 59 98 | 80 70 99 51 94 57 41 62 52 85  9  4  6 92  2 98 24 19 59 90 42 45 66 79 32
			Card  15: 79 46 69 60 94 86 18 12 54 88 | 12 53 29 62 27 55 80 22 32 51 66 54 30 23 56 94 68 98 87 21 93 17 49 10 74
			Card  16:  7 61 48 28 47  9 37 96 54 15 | 84 62 80 33 65  4 67 61 97 79 58 76 23 38 18 13 75 19 57 83 14 21 26 28 31
			Card  17: 18 83 31 73 94 46 56 62 19 78 | 25 86 68 43 93 28 78 71 10 23 50 12 47 35 64 85  4 54 99 14 40 53 83  6 39
			Card  18: 21 51 16 99 30 10 95 71 14 35 | 39 96 20  4 28 55 40 31 22 17 78 94 42 62 57 32 37  7 27 77 63 65 82 26 99
			Card  19: 90 45 35 36 13  5 87 42 82 34 | 10 17 97 69 43 91 16 72 45 81 19 76 29  1 96 49 15 20 70 51 59 56 14 22 63
			Card  20: 40 46 71 56  1 25 29 45 53 52 | 83 79  7 95 93 38 91 62 81 60 73 44 63 15 28 77 32 85 18 10 99 33  4 50  5
			Card  21: 51 99 84 13 87 11 34 16 96 88 | 51 59 87 12 34 16 96 84 26 99 69 97  1 21 62 11 81 56 64 88 77 49 50 58 13
			Card  22: 85 62 53 30 26 15 94 34 52 74 | 71 12 41 24 94 30 95 93 85 25 53 16 91 27 61 46 32 97 39 52 20 15 74 57 42
			Card  23: 45 15  9 83 58 69 20 60 90 99 | 97 89 48 23  5 60 86 59 42 74 66 17 51 28 15  9 92 69 73 62 49 65 33 30 90
			Card  24: 11 17 60 88 13 55 56 12 71 51 | 53  5  8 68 55 28 20 14 71 99 60 66 34  4 90 12 41 63 67 25 86 26  2 30 40
			Card  25: 91 50 44 43 69 68 77 28 53 25 | 14 35 63 94 58 19 70 47 33  8 27 78 12 46 38 50 25 57 13 74  2 45  4 98 95
			Card  26: 37  5 21 95 82 31 90  4 18 40 | 75 83 71 90 63 32  9 26 28 54 25 97  3  5 74 43 27 23 16  6 88 24 52 31 53
			Card  27: 74 41 60 91 15 83 62  9 64 40 | 97 44 26 34 71  9 40 94 62 20 33 95 91 43 24 74 57  2 64 41 39 35 86 85 32
			Card  28: 23 22 33  9 20 57 78 18 92 66 |  8 38 17 23 61  5 48 32 22 94 92 55 93 14 71 78 46 66 33 97 49 35 64 57 65
			Card  29: 61 14 28 80 47  3 86 93 36  7 | 82 22 32 19 79 84  1 39 65 88 95 66 76 56 38 67 11 68 40 91 93 31 94 23 70
			Card  30:  6 72  2 10 13 18 95 62 56 33 | 71 75 52 36 38 44 96  1 23 40 81 99 21 41 35 56 13 89 33 88 68 48 18 60 25
			Card  31: 79 76 82 11 92  3 61 68 53 91 | 92 56 74 63 38 72 94 19 34 12 51 97  7 53 29  4  9 48 57 87 88 49 44 76  2
			Card  32: 93 29 68 58 55 17 21 37  7 27 | 22 83 80 82 87 98 46 57 59 43 90 10 74 28 32  5 41 75 35 55 97 92 81 48 73
			Card  33: 55 25 32 46 51 57 59 74 11 44 | 31 43 88 42 10 48 95 54 50 82 68 99 38 24 62 86  3 32 21 75 35 27 81 80  5
			Card  34: 26 16 15 35 17 88 62 31 90 25 | 50 40 43 83 74 79  6 93 21 97 86 71 36 18 77 55 82 51 41 73 26 91 13  5 66
			Card  35: 30 70 76 69 67 35 25 51 73 41 |  1 80 14 59 11 52 54 99 61 72 63 31 34 78 17 66 75 85 55 92 32  5 82 97 40
			Card  36: 88 59 93 81 19 24 63 69 68 57 | 16 99 88 57 90 15 74 40 36 63 72 24 77 30 37 69 44 81 31 93 97 19 39 59 86
			Card  37: 44 73 40 12 72 91 79 11 48 69 | 76 48 43 46 75 73 62 45 72  6 67 13 12 66 71 25 11 35 41 31  1 91 44 40 79
			Card  38: 44 72 62 80 37 55 24 60 22 20 | 62 24 20 89 22 19  5  3 50 95 92 14 44 36 37 81 84 60 58 80 55 72 98 29 45
			Card  39: 83 53 42 66 39 81 10 15 30 78 | 16 70 17 95 11 91 51 84  2 63 35 28 32 68 23 46 75 19 98 43 36 12  8 55 97
			Card  40: 96 83 19 99 89 44 42 92 11 49 | 21 27 72 31 83 15 56 66 46 41 73 11 19  4 36 94 60 38 30 92 70 29 99 45 33
			Card  41: 25  9 93 73 14 99 84  3 66 71 | 93  8 19 51 37 63 78 68 43 27 81  2 33 54 64 57 92 50 26  7 41 56 76 88 11
			Card  42: 35 57 23  5 37  3 96 67 77 38 | 90 31 24 55 99 17 11 27 95 32 65 45 78 21 82 58 85 60 86 73 66 62 93 40 44
			Card  43: 94 24 14 17 52 31 86 37 23 67 | 14  5 23 17 45 57 71 42 76 33 19 13 31 94 20 29 48 98 27 37 51 47  2 67 59
			Card  44: 27  7 61 68 86 82 60 72 88 89 | 76 14 97 82 16  9 71  7 48 36 94 47 63 33 69 96 40 46 95 25 62 27 87 74 86
			Card  45:  1 96 24 71 78 29 63  3 64 56 | 84 68 54 49 31  9 55 40 70 98 48 36 72 21 75 89 39  5 87 86  3 62 78 16 97
			Card  46:  9 80 39  5 79 21  3 67 72 82 | 67  5 25 75 18 21 95 41 62 50 32 56 33  7 73 70 43 80 57 69 44 82 35 17 72
			Card  47: 53 60 98 80 89 70 14  8  9 32 | 29 44 21 63 80 76 88 51 39  8 95 10 68 58 65 77 66 19 78 50 18 67  6  4 62
			Card  48:  3 15 62 22 52 29 81 50 73 67 | 49  8 21 40 68 42 30 51 57 23  2 63 90 31 37 79 43 89 54 94  5 77 86 83 99
			Card  49: 52 56 40 51 15 41 59 33 96 19 | 60 74 95 24 70 81 59 55 61 53 69 84 79 72 17  7 90 98 15 47 39  8  9  1 21
			Card  50: 29 20 18 89  1  7 78 68 71 61 | 10 13 97 20  8  1 26 90 88 47  5 11 57 48 35  4 72 14 41 91 28 43 53 67 60
			Card  51:  5 98 48 56 86 16 19 84 70 58 | 14 57 42 43 26 53 37 80 10 64 73 45 35  8 55 92 44 54 69 49 32 46 65  1 39
			Card  52: 59 46 90 42 37 45 40 16 52 60 | 93 44 17 11 79 53 58 13 29 69 51 27 28 34 81 20  6 15 97 49 32 66 77 36 12
			Card  53: 96 31  6 18 54 43  3 79 37 89 | 32  1 83 75 40 85 10 91 77 84  7 13 56 68 81 60 78 80  3 24 29 49 20 37 12
			Card  54: 23 67 46 81 24 35 56 74 96 52 | 81 80 10 38 43 18 23 68 29 33 76 54 82 84 45 15 19 92  3 13 39 25 14  1 32
			Card  55: 60 89 83 18 76 67 27 44 57 28 | 77 11 83 60  7 16 41 28 44 27 76 57 18 81 10  6 39 12 67 65 58  5 86 85 89
			Card  56: 34 70 11 38 82 16 46 74 18 21 | 67 38 25 47  6 70 74 46 14 82 66 34 21 35  5 59 77 18  3 30 50 55 11 78 16
			Card  57: 49 55 93  8 33 14 50 71  3 41 | 41 81 98 69 79 71 50 67 10 25 88 93 32 49 60 40  3 75 34 23  8  9 55 14 33
			Card  58: 50 44 30  4  5 36 13 90  8 71 | 22 14 98 44 50 10  4 61 96  8 15 62 59  3 71 90 19 65 30 13 25 36 82 29  5
			Card  59:  4 38 29 79 86  8 93 30 78 50 |  8 16 30 66 29 34 79 78 93 24 15 52 14 95 76 54 38 50 86  4 48 87 89 35 51
			Card  60: 64 23 81 78  7 21 53  8  2 11 |  8 86 53 11 62 21 49 73 92 36  2 78 40  1 81 82 64 52  4 48 23 35  7 19 32
			Card  61: 82 68 74 65 21 64 48  7 50 22 | 74  5 14 97 72 68 25 44 88 50 90 82 20 64 16 49 31 37 60  7 21 22 53 48 65
			Card  62: 33 64  5 18 21  8 29 51 50 65 | 69 65  5 96 71 34 32 79  8 16 64 98 47 33 39 84 18 50 21 61 27 29 82 51 87
			Card  63: 38 25 54 51 24 28 68 16 37 76 | 10 38 69 60  4 29 68 12 37 46 51 14 94 35 61 76 79 17 25 63 31  1 40  9 67
			Card  64: 30 79 68 31 97 35 23 66 16  1 | 22 29 79 35 31 32 23 80 40 14 72  1 30 97 66 62 68 19 71 38 16  6 70 85 13
			Card  65: 30 43 56 72 65 99  4 62 47 44 | 36 56 93 44 68 35 33 30 91 64 81 47 11 79 73 24 50 99 12 52 46  3 48 43  4
			Card  66: 68 36 89  8 15 16 14 76 85 42 | 76 48 71 15 23 22 16 74 89 34 81 17 36 68  8 93 14  3 42 31 50 45 67 52 85
			Card  67: 98 25 51 35 84 80 87 45  6 92 | 37 82 60 68 56 12 46 41  8 15 83 77 71 74 49 61 65 28  1 93 95 33 21 24 67
			Card  68: 79 92 67 17 51 31  7 10 55 33 | 99 51 42 39 92 26  8 17 20 57 53 76 48 59 55 78 14 84 11 31 23 33 83 96  2
			Card  69:  1 33 66 73 78 44 37 13 32 34 |  1  5 66 91 94 35 72 89 74  9 44 45 21 37 16 11 33  3 34 27 46 71 55 68 32
			Card  70: 65 30 11 25 55 50 35 57  2 27 | 21 86 15 69 37 11 27 38 25 96 79 85 49 95 43 89 77  8  2 88 55 57 47 67 54
			Card  71: 60 45 42 75 94 51  6  4 49 82 | 32 64  2 62 18 63 43 73  5 58 16 15 38 44  9 39 87 79 12 57 54 28 22 41 66
			Card  72: 47 37 94 28 88 39 57 78 93 33 | 42 53 12 34 86 29  6 67 22 87 56 68 18  7 21 99  2 46 36 35 73 10 78  5 13
			Card  73: 97 50 10 80 41 36 76 16 18 88 | 91 66 27 38  6 82 49 86 78  9 63 41 24 60  2 30 94 36 42 72 43  8 35 71 61
			Card  74: 49 48  6 43 98 51 45  1  8 17 | 16 81 14 87 68 96 52 59 13 24 65 25 99 10 90 76 63  4 28 84 30 89 75  9 54
			Card  75: 21  3 83  9 65 38 17 50 64 62 | 91 38 17 35 19 78 41 27 74 11 12 57 31 60 77 55 37 23 93 26 96  7 18 79 46
			Card  76: 84 80 36 74 41 66 81 91 75 70 | 56 67  6 51 18 35 19 97 88 93 13 77 20  7 26 95 90 62 34 22 78 21 94 40 64
			Card  77: 39 94 72 35 45 27 65 42 32 17 | 69 19 11 83 67 14 25 86 49 43 61 80 73 12 41 71 68 47 20 40 88 76  1 10 93
			Card  78: 95 78 47 69 99 72 73 29 41 83 | 95  2 47 83 53 29 42 25 73 72 26 71 78 93 84 99 49 41 23 87 90 56 11 69 82
			Card  79: 55 81 93 35 98 16  2 36 29 40 |  2 41 32 89 65  9 42 33  5 55 87 56 40 85 83 72 11 16 82 86 91 52 71  4 75
			Card  80: 66 45 95 59 84 48 91 42 56 89 | 19 49 90 40 41 63 73 81 13  7 58 16  1 33 38 76 15 95 27 31 61 32 43  9  8
			Card  81: 85 15 80  4 58 83 32 66 53 13 | 99  1 11 98 87  4 35 60 21 76 80 13 42 53 66 18  5 39 47 32 83 62 38 15 24
			Card  82: 40 64 66 27 83 15 39 12 49  4 | 34 68  5 11 57 40 12 61 85 44 78 33 89 22 91 79 66 24 97 48 45 39 87 96 37
			Card  83: 95 12 33 99 17 40 88 19  2 31 | 68 91 10 60 23 80 22 41 84  2 17 90 12 99 31 11 19 95 16 79 46 86 53 88 47
			Card  84: 59 71 10 49  4 14 38 76 44 47 | 49 62 89 76  8 64 28 55 63 75 95 10 23 13  4 38 47  5 59 14 29 12 24 71 44
			Card  85: 63 82 23 43 74 77 48 81 62 67 | 82 27 99 63 15 34 43 80 57 41 64 32 18 19 62 67 74 87  6 48 23  2 22 81 77
			Card  86: 34 69 83 79 28 51 13 75 70 44 | 68 28 73 52 95  1 19 79 43 42 41 17 46 83 54 27 24 12 31 92 70 72 10  7 84
			Card  87: 21  2  9 61 29 65 74 58 10 17 | 65 95 91  4 14  9 59 46  2  8 21 15 36 58 20 97 17 61 38 39  6 29 74 42 10
			Card  88: 26 77 68 61 15 94 51 24 28 17 | 59  4  7 42 11 40  8  3 10 26 82 25 87 58 62 13 47 46 32 19 37 98 78 81 95
			Card  89: 19 11 96 61 46 83 90  4 92 40 | 92 83 29 76 64 70 79  4 93 54 87 59 95 12 40 46 96 89 11 19 15 61 90  6 22
			Card  90: 49 81 84 37  7 11 88 70 21 39 | 75 54 64 55 35 24 41 25 69 94 56 96 51 57 61 66 17 74 50 78 65 68  6 77 34
			Card  91: 43 84 86 78 15 80  4  5 37 61 | 25 20 29 46 97 12 45 27 31 58 38 51 68 52 39  1 30 40 79 73 32  9 74 47 16
			Card  92: 44 49 64  3 88 91 93 40 56 54 | 88  3 77 71 66 25 15 86 63 64  7 59 90 44 54 82 38  4 62 40 95 87 97 74 91
			Card  93: 84  4 19 88 69 42 94 73 72 36 | 15 49 38 26 31 24 73 92 14 88 85 95 93 36 80 19 29 75 35  1 11  7  9 60 77
			Card  94: 62 42 64 25 22 84 65 34  7 55 | 44 28 60 37 71 43 98  7 16  4 99 27 19 23 82 94  6  9 64 59 77 55 74 92 21
			Card  95:  1 73 41  6 31 80 45 84 47 29 | 49 78 59 90 81 29 75  7 57 13 36 39 92 37 70 12 60 86 87 17 84 63 74 71 46
			Card  96: 15  5 82 24 57 44  6 34 36 28 | 95 90 35 71 16  9 59 37 23 73 87 58 69 78 52 55  4 64 68 79  3 46 99 93 47
			Card  97: 81 14 57 71 97 66 48 61 95 83 | 19 47 18 81 49 73 67 40 30 28 54 14 84 52  2 64 66 79 70 36 99 41 48 60 72
			Card  98: 71 86  1 44 47 31 34  6 11 41 | 59 13 34 77 20 80 49  8 85 24 45 70 78 61 93 27 84 98 66 26 43  3 16 92 82
			Card  99: 94 81 40  4  6 27 78 52 28 86 | 65 61 51  7 46 56 80 16 24 14 59 43 11 34 39 40 81  2 73 75 63 25 77 93 49
			Card 100: 22 39 65 11 89 20 95 35 53  5 | 83 40 91 76  8  7 74 67 86 21 12 48 15  3 50 10 44 55 13 88 45 94 81 19 70
			Card 101: 25 45 85 81 99 95 35 72 60 31 | 48 75 58 42 88 70 34 26 93 56 97 64 28 61 52 68 67  3 24 80 63 53 23 50 10
			Card 102: 50 27 37 96  3 22 13 67 72 77 | 29 81 31 50 71 13 63 22 51  3 35 96 90 59 72 11 67  2 77 56 37 94 27 86 33
			Card 103: 70 36 61 83 12  3 67  8 72 25 | 29 87 53 30  8 91 61 66 76 33 36 16 93 81 26 67 98 12 35 89 21 58 24 48 47
			Card 104:  3 42 33 96 80 44 48 45 83 31 | 31  2 54 74 49 27 96 60 41 77 33 44  3 80 45 92 63 48 91 87 69 42 83 38 56
			Card 105: 17 86 91 68 83 27 66 69 13 78 | 71 95 67 28 83 10 59 17 89 92 34 91 79 19 63 24 11 21 15  5 49 75 97  6 41
			Card 106: 39 66 88 52 18 21 23 11 28 10 | 27 23 24 21  9 65 41 47 48 64 55 16 42 52 98 17 39  1 18 37 38 51 91 62 10
			Card 107: 10 73 51 30 54 53 85 87 24 62 | 85 62 73 24 41  1 21 90 30 46 66 80 87 54 50 38 51 53 10 43 59 76 11 33 37
			Card 108: 86 63 64 34 39 10 49 83 36 55 | 63  3 85 55 33 84 20 39 73 87 68 86 79 18 89  6  9 54 14 47 53 10 50 40 96
			Card 109: 87 21 40 83 30 84 81 76 45 42 | 92 23 65 42 64 25 82 80 63 12 67 74 40 72 84 39 99 95 81 30 73 45  9 32 66
			Card 110: 48 29 67 41 64 17 10 71 57 80 |  2 19 97 90 11 22 26 45 89 27 33 71 94 30 86 32 15 17 93 74 88  8 99 91 55
			Card 111: 14 23 33 84 31 34 51 78  3 53 |  7 30 33 86 50 98 34 93 10 36 17 32 20 79 63 35 62 11 28 47 67 91 53 31 46
			Card 112: 41 56 78  9 31 62 97 20 45 51 | 37 53 76 72 78 44  3 68 20 58  1 28 92 69 50 90 81 48 42 84 83 36 17 12 87
			Card 113: 42  2 14 49 70 19 25 15 39 67 | 91 60 85 42 14 43 63 62 39 31 77  2 89  1 87 50 13 86 12  3 15 30 40 16 23
			Card 114: 86 76 78 65  9  5 88 63 49 85 | 17 24  9 63 65 48 78 59 13 88 19 72 31 67 84 74 49 52 26 86 76  8 60 21  5
			Card 115: 63 73 95 71 61 55 49  5 72  3 | 83 76 42 85 23 54 52 99 79 80 60 32 67 31 30 66 97 12  9 15  3 87 19 35 98
			Card 116:  1 48 39 69 23 99 28  5 33 49 |  7 26 42 81 49  4 61 65  1 58  3 23 20 28 24 47 75 50 89 68 88 38 54 66 45
			Card 117: 78 90 29 34 30 85 58 37 21 79 | 34 37 54 88 90 63 13 35 55  8 75 76 87 23 29 47 95  3 14 48 58 79 38 25 91
			Card 118:  3 95 50 45 97 27 39 94  5 93 | 73 69 47 39 20 72 33 57 24 81 35 62 41 37 87 42 79 99 74 55 49 30 54 59  8
			Card 119: 58 43 47 41 28 36 86 46 88  2 | 28 93 11  4 14 77 68 78  7 79 92 75  2 86 59 58 57 47 22 20 27 51 81 42 48
			Card 120: 94 13 79 25 98  9 45 55 89 49 | 71 48 86 84 14 92 29 53 11  6  2 50 24  3 81 39 57 88 43 63 64 32 98 58 60
			Card 121: 74 85 72 79 55  9 88 81 49 82 | 61  6 15 75 80 62 18 27 44 46 66 17 30  4 36 92 65 50  3 38 57 10 37 39 32
			Card 122: 89  5 20 30 28 88 35 57 31 94 | 84 27  7 79 28 22 98 85 93 62 38  3 24 59 61 95 78  4 36 53 49 46 83 25 51
			Card 123:  1 86 10 20 15 78 14 71 74 38 | 88 28 59 73  6 65 47  9 19 97 42 11 18 66 50 89 39 67 21 60 80 33 30 34 45
			Card 124: 70 92 84 40 15 31 27 25 69 85 | 39  4 22 57 26 52 60 83 53 79  8 75 34 33 38  1 37 43 18 80 58 72 88 35 11
			Card 125: 30  6 47 27 76 78  9 10 16  5 | 76  9 65 75  5 30 57 14 16 49 70  7 90 33 88 92 10 17 78 47 25 27 32 20  6
			Card 126:  8 86 94 98  6 55 69 73 36 19 | 34 95 80 32 84 69 73 47 98 56 92  8 50 26  6 94  4 86 21  7 78 85 55 82 19
			Card 127: 10 36 55 88 43 11 34 38 19 24 | 38 34 93 41 88 94 62 56 18 36  3 43 37 58 23 44 50 65 90 71 61 11 25 98 46
			Card 128: 11  5 25 36 27 95 71 76 61 97 | 95 71 25 11  5 53 36 27 76 61  8 56 89 30 64 26 48 65 97 83 42 35 96 58 16
			Card 129: 66 95 46 45 64 41 22 87  5 19 | 41 27  9 77 19  5 37 90 64 97  7 85 46 45 66 25 21 95 87 22 60 15 84 83 79
			Card 130:  7 31 60 38 76 36  3 30 79 37 | 44 83 66  3 37 48  8 95  5 19 36 70 67 98 92 99 76 87 30 35 17 29 46 51  2
			Card 131: 94 96 36 85 59 51 12 23 46 74 | 77  2 88 50 93  3 41  4 38 31  1 10 69 54 40 76 95 17 78 30 87 72 27 92  7
			Card 132: 67 79  9  7 84 25 35 89 59 60 | 82 74  5 50 25 79 87  4  2 81  1 91 69 37 63 39 76 60 66 47 94 29 49 59 31
			Card 133: 74 62 93 36 51 59 71 68 10 38 | 38 36 34 74 20 39 16 62 68 70 63 53 71 59 37 99 80 13 94 23 10 18 75 93 51
			Card 134: 39 98 53 93 42 67 55 23 43 92 | 32 39 55 41 30 43 69 26 34 40 74 28 92 71 56 23 53 67 18 78 98  8 36 93 42
			Card 135: 19 42 49 63  2 28 55 80 20 79 | 98 55 70 38 97 67 87 76 72 75 79 13  5 40 22 28 88 49 43 14 63 81  2 42  9
			Card 136: 58 34 14 24 21 16  3 22 37 17 | 99 88 68 12 55 98 45 69 40 81 80 24 82 42 29 28 54 30 49 59 39 91  6 15 10
			Card 137:  3  5 14 68 13  8  9 22 47 55 | 83  1  8 18 93 99 97 80 51 61 69 39 76 33 28 17  4 45 89 75 58 81 73 71 20
			Card 138: 39 67 12 77 65 26 61 87 95 37 | 88 61 41 67 37 86 94 32 72 76 22  6 81 80 50 77 87 55 95  4 96 63 39 84 29
			Card 139: 57 53  1 29 60  6 13 90 81 36 | 66 48 15 80 67 93 96  8 62  7 78  5 35 11 84 91 46 63 61 31 58  3 37 85 41
			Card 140: 32 31 71 34 20 45 23 14 54 65 | 51 61 48 49 32 16  9 80 55 85 53 74 45 26 25 73 84 86 78 59 94 87 17 62 93
			Card 141: 56 92 14 27 83 48  2 20 21 81 | 18 86  1 30 70 87 26 73 63 13  7  3 45 39 94 47 29 61 82 67 36 48  5 69 66
			Card 142: 80 28 44 37 40 57 46  1 59 77 | 41  8 58 36 70 13 38 48 85 27 25 92 64 18 43 91 45 75 60 39 15 82 72 68 59
			Card 143: 63 97 18 25 52 54 27 47 12 92 | 51 74 46 10 28 44 49 54 24 17 29 80  7 11 57 89 91 31 69 35 88 47 32 14 58
			Card 144: 42 47 63 79 13 58 10 29 24 98 | 53 62 80 51 88 27 22 90 72 64 48 44 45 57 84 32 14 65 79 82 81 56 31 97 18
			Card 145: 71 59 46 50 33 41 91 60 10 42 | 56 80 76 63 12 15 48 27 74 59 69 72 36 61 95 34 50 77  4 43 22 88 39 44 23
			Card 146: 64 90 55 17  4 19 91 57 48 18 | 37 36 89  7 73 59 19 58 69 34 85 87 72  1 31  6 29  5 15 45 39 80 20 92 48
			Card 147: 93  8 67 36 23  1 91 27 56 11 | 38 53 72 86 62 74 54 41 24 29 18 95  5 83 12 61 80  4 17 13 20  9 98 70 35
			Card 148: 58 32 35 18 31  4 20 69 50 84 | 86 91 90  8 71 22 52 67 45 38 77 34 63 66 51 28 11 46 15 95 70  7 14 96 54
			Card 149: 16 67 90 91 75 54 32  2 20 98 | 52 75 90 55 32 16 68 19 91 24 49 45 69 54 98 93 76 39 67 47 20  2 92 70 48
			Card 150: 19 22 90 99 10  4 43 47 39 37 | 37 82 19 10 39 83 99 13 59 22 94 47 67 27 88 90 53 43  4 95  2 97 30 11 46
			Card 151: 89 83 76 54 10 16 20 14 53 55 | 75 33 48 12 25  1 73 43 60 69 35 45 77 40 94 96 28 61 83 37 22 52  2 99 47
			Card 152: 79 86 12 80 63 90 53 66 44 74 | 42 46 35 15 82 96 39 65 44 32 76 83 80 28 66 48 74 22 30 89 16 55 79 17 51
			Card 153: 88 32 87 77 93 47 33 91 76 17 |  5 89 39  2 93 23 49 12 35 38 45 47 24  1 78 86 60 14 40 75 63 88 90 22 36
			Card 154:  4 18 83 74 53 60 26 67  5 43 | 64 44 70  3 87 26 79 46 71 92 63 72 34 15 28 86 75 29 20 61 89 17  6 96 10
			Card 155: 77 76  8  1 35 49 21 55 72 63 |  6 97 79 59 93 76 91 68 14 46 23 81 39 49 50  8  4 47 31 37 86 26 22 63  1
			Card 156:  5 22 35 87 59 90 78 93 61 48 | 25 57 22 78 38 67 42 93  9 31 87 47 44  5 10 35 39 98 90 65 48 59 61 23 64
			Card 157: 23 34 11 19 99 44 30 66 28 25 | 13 34 19 11 69 30 22 46 74 51 66 99 58 25 29 63 90 91 23 80 44  9 88 28 35
			Card 158: 11 79 89 50 86 20  8 85 96 92 | 86 40 13  5 50 92 43 85 27 39 77 79 59 18 53 89 28 20 75 11 83 55 36 96 60
			Card 159: 70 79 61 17 65  9  7 87  1 91 | 42 34 70 46 89 60 43 40 87 99 73 16 94  5 35 18 91 32 38 61  7 39 98 84 69
			Card 160: 25  4 98 71 48 30 15 82 43 83 | 83 78 81 21 85 73 92 57 90 71 36  6 30 12 82 61 50 47 38 74 37 88 11 51 89
			Card 161: 70  8 82 32  7 65 21 72 74 87 | 76 21 59 19 58 87 97 77 36 62 88 69 48 68 51 25 64  5 85 15 61 56 52 34 67
			Card 162: 73 25 70 52 24 69 50 77 79 99 | 80 52 13 24 66 93 47 77 39 36 72  2 63 25 18 59 55 97 76 21  7 73 37 69 33
			Card 163: 87 77 10 26 79  6 67 58 46 49 | 73 33 87 97 27 39 82 23 79 60 28 25 66 98 70 55 40  3 19  5 58 30 74 52 31
			Card 164:  3 15  4 21 41 13 30 91 33 69 | 12 97 59 62 93 50 83  3  2 18 11 53 35 74  4 88 89 42 85 55  6 94 84 31 21
			Card 165: 68 70 46  1 56 85 86  8 71 65 | 66 25 77  2 61 46 28 85 27 37 48 24 80 95 53 76 40 68 12 41 51 74  4 93 20
			Card 166: 86  7 83 63  8 14 31 38 35 49 | 72 10 76 54 52 19 82 81 25 78 66  6 15 11 42 27 33 46 57 91 75 17 98 88 37
			Card 167:  1 21 19 11  5  3 52 91 24 25 | 37 50 34 91 23 18 43 78 97 76 84 74 72 83 81  8 85 28 67  7 14 44 68 61 35
			Card 168: 89 88  2 98 97 40 94 95  7 28 |  4 86 77 52 10 15 69 53 46 36 38 17 93 43 75 57 48 30 27 78 60 81 96 32 65
			Card 169: 56 12 15 19  6 72 29  8 46 77 | 19 45 14 20 18 29  5  1 54 95 84 12  6 50 44 63 73 15 46 56 72 16  8 32 77
			Card 170: 18 94 75 79  6 64 85  1 71 29 | 64 18  3 94 85  8 71  6 26 31 47 40 56 29 75  5 28 95 82 58 13  1 66 79 39
			Card 171: 88 58 47 35 33 31 34 71 91 59 | 92 11 73 71 34 13 74 35 60 26 50 95 54  3 23 76 18  7 88 83  9 16 33 43 59
			Card 172: 56 52 15 39 76 68 30 33 70 19 | 18 68 74 66 60  7 39 56 52 87 90 67  9 33 19 76 23  8 30 15 29 13 70 50 77
			Card 173: 93 87  6 11 64 71 81 49 48 21 | 48 66 17 49 21 64 11 71 23 43 93 79 35 87 29 81 37 36 32 88 31  6 82 92 84
			Card 174: 68  7 62 39  6 79 20 61 29 96 | 34 86  5 79 23 36 48 98 47  8 29 61 62 72  7 56 44 17 85 78 68 13 33 51 37
			Card 175: 96 45 30 33 12 22 82 46 69 52 | 82 20 22 69 96 73 12 54 52 46  4 75 86 37 33 76 45 80 68  6 25 15 30 71  7
			Card 176: 61 72  5 46 45 49 94 48 89 95 | 43 45 10 49 62  1 89 79 19 29 18 99 94  5 57 41 25 95 75 87 46 72 26 48 61
			Card 177: 93 36 28 85 57 24 27 17 19 68 | 19 28 53 82 20 27 68 73 86 76 48 17 13 41 24  7 34 36 72  8 90 14 45 57 35
			Card 178: 52 47 54  9 13 27 82 97 33  5 | 25  9 54 77 86 82 27 52 47 33 76 28 18 93 41  5 97 73 98 87 89 65  4 38 13
			Card 179: 38 31 71  4 86 44 36 50 78 21 |  1  3 94 65  4 39 67 88 92 42 81 21 17 78 69 41 87 63 10 86 74  8 75 43 36
			Card 180: 41 68 58 43 11  7  3 37 30 76 | 73 37 96 75 83  2 46 15 18 41 26 82  3 61 51 17 49 76 38 80 24 33 22 99 34
			Card 181: 36 46 17 50 81 39 49 23 53 29 | 12 30 90  9 16 32 72 84  7 51 71 66 25 89 40 87 60 96 83 97 21 64 34  6 44
			Card 182: 66 64 16 37 97 95 77 93 86 30 | 95 31 16  6  5 93 77 37  1 66 27  4 40 86 14 17 78 80 30 47  9 23 92 76 85
			Card 183: 23 90 41 96 54 79 46 85 55 45 | 28 61 65  7 46 66 52 19 97 98 96 23 99 12 17 16 35 56 29 33 54 60 44  6 37
			Card 184: 69 16 91 59 97 17 77 87 83 35 | 68  6 77 95 85 35 91 24  4  1 16 51 63 34 17 93 32 43  9 67 28 83 69 59 87
			Card 185: 86 93 46 12 27  7 50 25 77 83 | 67 61 18 93 19 37 86  6 26 73 36 95 11 23 50 53 32 77 49 56 96 13 20 25  2
			Card 186: 62 30 38 84 47 82 66 37 36 77 | 77 79 36  5 19 47 81 85 48 66 10 37 49 40 82 95 31 90 65 60 44  8 56 54  7
			Card 187: 26 23 34 78 49 43 97  8  4 24 | 12  4 35 77 99 87 73 38 34 15 50 92 55 21 71 28 10 90 95 22 93 44 59 96 51
			Card 188: 89 18 56 10 22 99 57 86 85 80 | 98 88 82 12 67 71 66 87 86 73 97 41 69 57 80 20  9 96  8 99 23 56 19 15 36
			Card 189:  9  7 72 87 55 23 79 86 89 37 | 99 35 98 72 62 70 44 41 66 84 11 80 50 51 33 12  8 32 68 95 53 39 73 43 17
			Card 190: 73 30 34 92 99  5 71 22 35 10 |  6 85 89 15 65  2 23 69 90 88 56 25 26 68 47 14 24 39 76 84 28 71 49  8  3
			Card 191: 88 95 57 69 60 82 68 70 87 40 | 30 51 75 84 11 67 45 81 35 85 50 24 70 93 13 17 76 79 97 72 98 58 38 25 34
			Card 192: 56 82 59 30 71  2 70 97  3 51 |  6 88 84 25  9 42 14 93 56 99 69 73 68 76  1 81 35 48 11 87 65 23 66 32 10
			Card 193: 89 62 23 74 44 55 92 87 50 91 | 61 77 54 52 39 48 67 82 66 57 30 72 18 35 46 96 81 38 69 32 17 47 27 53 25
			Card 194: 43 62 87 49 65 26 10 72 92 66 | 23 49 43 72 35 88 66 65 28 25 62 80 96 13 64 18 34 69 10 59 41 92  6 26 37
			Card 195: 23 34 36 91 62 49 81 10 60 21 | 52 60 28 21 49 38  4 10 83 23 13 36 81 68 20 34 31 62 27 14 44 47 67 35 91
			Card 196: 46 73 39 86 11 32 17  9 92 35 | 35 73 11 40 54 86  4 19 89 39 23  9 56 17 74 85  5 57 92 46 45 21 27 50 32
			Card 197: 67 84 19 32 66 39 37 52 94 64 | 61 17 59 69 90 52 66 55 79 38 12 37 84 16 67 95 36 32 64 94  1 56 51 39 83
			Card 198:  4 37 72 50 66 39 56 64  9 41 |  6 50 65 41 21 82 71 56 90 72  2 14 98 64 12 69 89 47 37  4  9 32 39 66 51
			Card 199:  7 40 49 60  2 57 78 55 23 25 | 98 23 78 40 55 60 77 25 34 12 43  7 37 75 69  3 57 30 64 42 49 22 71  2 83
			Card 200: 91 56 16 83 96 19 27 32 23 55 | 77 64 31 59 27 96  1  4 53 30 15 29 94 60 98  3 22 65 61  6 11 89 54 58 74
			Card 201: 20 37 11 66 35 13 83 60 56 63 | 63 82 54 25 56 53 35 22 11 60 40 41 13 20 83 98 29 36 28 78 92 66 37 33 16
			Card 202: 80 56 59 24 67 20 79 85 14 18 | 59 85 18 20 24 17  6 66 70 94 67 14 56 93 40 15  7  1 80 58 83 75 79 65 77
			Card 203: 90 55 42 62 95 51 54 40 47 29 | 95 34 86  1 68 51 63 30 84 11 37 87 50 49 16 22 78 45 36 58 31 35 59 21 65
			Card 204: 15 25 27 81 55 54  8 93 53 96 | 90 57 80 73  5 55 71 47 98 32 87 69 58 13 59 82 29  1 41 25 64 97 30 85 36
			Card 205: 50 88 26 77 58 16  6  1 25 62 | 47 67  1 24 17 52 22 39 54 15 14 60 42  6 88 98 58  2 93 51 13 80 36 92 86
			Card 206: 20 51 38 73 58 93 40 62 43 71 | 40  1 70 43 51 32 38 14 41 20 30 85 76 65 24 93 34 12  6 21 71 53 75 80 62
			Card 207: 10 92  3 99 67  2 83 71 25 17 |  2 88 70 39 18 92 22 99 83 47 17 52 42  5 38 40 77 10 24  7 76 35 71 12  3
			Card 208: 25 81 92 55 68 41 18 75 12 67 | 96  6 86 20 76 89 92 40 30 17 77 13 24 69 45 37 70 42 63 88 43  9 75 53  3
			Card 209: 35  7  5 56 57 51 75 66 78 85 | 52 23 86 45 59 14  1 16 95 13 56 38 69  8 49 22 72 35 40 26 43  7 15 44 96
			Card 210: 35 83  8 27  2 39 47 29 25 61 | 74 21 88 92 35 13 76 29 73 39 62  3 67 71 66 23 16  4 40  7 42 19 72 22 31
			Card 211: 38 58 71 14 20 92 85 63 90 34 | 54 93 16 39  1 62 13 51 46 77 42 24 15 41 94 71  3 29 43 19 12 30 78 50 80
			Card 212: 54 52 17 49  4 66 55 74 12 39 | 78 59 48 28 60 53 68 33 34 10 84 88 40 41 51 45 67 71 18 64 32 27  3 82  8
			Card 213: 53 70 23 28 63 52 88  2 98 29 |  7 26 82 72 14 84 60 78 29 51  1 11 93 89 80 12 55 90  6  2 67 71 35 41 32
			Card 214: 14 75 87 20 74 97 99 89 25 64 | 76 59 61 96 63 31  9 83 68  8 65 13 73 23 84 49 11 35 88 98 15 50 36 79 22
			Card 215: 33  3 95 82 18 59 74  8 40 62 | 80 28 78 57 81 87 53 86 51 91 32 11 10 99 97 39  1 36  4 14 22 68 21 55 92
			""";
}
