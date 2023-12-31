import java.util.ArrayList;
import java.util.HashMap;

/*
--- Day 7: Camel Cards ---

Your all-expenses-paid trip turns out to be a one-way, five-minute ride in an airship. (At least it's a cool airship!) It drops you off at the edge of a vast desert and descends back to Island Island.

"Did you bring the parts?"

You turn around to see an Elf completely covered in white clothing, wearing goggles, and riding a large camel.

"Did you bring the parts?" she asks again, louder this time. You aren't sure what parts she's looking for; you're here to figure out why the sand stopped.

"The parts! For the sand, yes! Come with me; I will show you." She beckons you onto the camel.

After riding a bit across the sands of Desert Island, you can see what look like very large rocks covering half of the horizon. The Elf explains that the rocks are all along the part of Desert Island that is directly above Island Island, making it hard to even get there. Normally, they use big machines to move the rocks and filter the sand, but the machines have broken down because Desert Island recently stopped receiving the parts they need to fix the machines.

You've already assumed it'll be your job to figure out why the parts stopped when she asks if you can help. You agree automatically.

Because the journey will take a few days, she offers to teach you the game of Camel Cards. Camel Cards is sort of similar to poker except it's designed to be easier to play while riding a camel.

In Camel Cards, you get a list of hands, and your goal is to order them based on the strength of each hand. A hand consists of five cards labeled one of A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2. The relative strength of each card follows this order, where A is the highest and 2 is the lowest.

Every hand is exactly one type. From strongest to weakest, they are:

Five of a kind, where all five cards have the same label: AAAAA
Four of a kind, where four cards have the same label and one card has a different label: AA8AA
Full house, where three cards have the same label, and the remaining two cards share a different label: 23332
Three of a kind, where three cards have the same label, and the remaining two cards are each different from any other card in the hand: TTT98
Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a third label: 23432
One pair, where two cards share one label, and the other three cards have a different label from the pair and each other: A23A4
High card, where all cards' labels are distinct: 23456
Hands are primarily ordered based on type; for example, every full house is stronger than any three of a kind.

If two hands have the same type, a second ordering rule takes effect. Start by comparing the first card in each hand. If these cards are different, the hand with the stronger first card is considered stronger. If the first card in each hand have the same label, however, then move on to considering the second card in each hand. If they differ, the hand with the higher second card wins; otherwise, continue with the third card in each hand, then the fourth, then the fifth.

So, 33332 and 2AAAA are both four of a kind hands, but 33332 is stronger because its first card is stronger. Similarly, 77888 and 77788 are both a full house, but 77888 is stronger because its third card is stronger (and both hands have the same first and second card).

To play Camel Cards, you are given a list of hands and their corresponding bid (your puzzle input). For example:

32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483
This example shows five hands; each hand is followed by its bid amount. Each hand wins an amount equal to its bid multiplied by its rank, where the weakest hand gets rank 1, the second-weakest hand gets rank 2, and so on up to the strongest hand. Because there are five hands in this example, the strongest hand will have rank 5 and its bid will be multiplied by 5.

So, the first step is to put the hands in order of strength:

32T3K is the only one pair and the other hands are all a stronger type, so it gets rank 1.
KK677 and KTJJT are both two pair. Their first cards both have the same label, but the second card of KK677 is stronger (K vs T), so KTJJT gets rank 2 and KK677 gets rank 3.
T55J5 and QQQJA are both three of a kind. QQQJA has a stronger first card, so it gets rank 5 and T55J5 gets rank 4.
Now, you can determine the total winnings of this set of hands by adding up the result of multiplying each hand's bid with its rank (765 * 1 + 220 * 2 + 28 * 3 + 684 * 4 + 483 * 5). So the total winnings in this example are 6440.

Find the rank of every hand in your set. What are the total winnings?

Your puzzle answer was 246795406.

--- Part Two ---

To make things a little more interesting, the Elf introduces one additional rule. Now, J cards are jokers - wildcards that can act like whatever card would make the hand the strongest type possible.

To balance this, J cards are now the weakest individual cards, weaker even than 2. The other cards stay in the same order: A, K, Q, T, 9, 8, 7, 6, 5, 4, 3, 2, J.

J cards can pretend to be whatever card is best for the purpose of determining hand type; for example, QJJQ2 is now considered four of a kind. However, for the purpose of breaking ties between two hands of the same type, J is always treated as J, not the card it's pretending to be: JKKK2 is weaker than QQQQ2 because J is weaker than Q.

Now, the above example goes very differently:

32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483
32T3K is still the only one pair; it doesn't contain any jokers, so its strength doesn't increase.
KK677 is now the only two pair, making it the second-weakest hand.
T55J5, KTJJT, and QQQJA are now all four of a kind! T55J5 gets rank 3, QQQJA gets rank 4, and KTJJT gets rank 5.
With the new joker rule, the total winnings in this example are 5905.

Using the new joker rule, find the rank of every hand in your set. What are the new total winnings?

Your puzzle answer was 249356515.

Both parts of this puzzle are complete! They provide two gold stars: **

*/

// did part 1 while watching a movie, so about 2 hours.
// did part 2 a couple hours starting around 1 am, then 2-3 hours in the morning the next day.  Most of that was spent finding the one edge case I was missing.  Oof.
public class Advent7
{
	static boolean part2 = false;
	static String GetCardTypes()
	{
		if (part2)
			return "AKQT98765432J";
		return "AKQJT98765432";
	}

	public static void main(String[] args)
	{
		var split = input.split("\n");

		var scoreHands = new ArrayList<Hand>();
		for (var input : split)
		{
			scoreHands.add(new Hand(input));
		}

		scoreHands.sort(Hand::compareTo);

		long score = 0;
		for (int x = 0; x < scoreHands.size(); x++)
		{
			 var increase = (x+1) * scoreHands.get(x).bid;
			 score += increase;
		}

		System.out.println("score: " + score);

		// part 2
		part2 = true;
		scoreHands.sort(Hand::compareTo);

		score = 0;
		for (int x = 0; x < scoreHands.size(); x++)
		{
			var hand = scoreHands.get(x);
			var increase = (x+1) * hand.bid;
			score += increase;

			System.out.println(" hand " + hand.hand + " : " + hand.upcastHand() + (hand.hand.contains("J") ? " *" : ""));
		}

		System.out.println("score part 2: " + score);
	}

	final static class Card implements Comparable<Card>
	{
		char card;
		Card(char input)
		{
			card = input;
		}

		@Override
		public int compareTo(Card inCard)
		{
			var idx = GetCardTypes().indexOf(card);
			var idx2 = GetCardTypes().indexOf(inCard.card);

			// smaller idx is > score value, so reverse the objects
			return Integer.compare(idx2, idx);
		}
	}

	final static class Hand implements Comparable<Hand>
	{
		String hand;
		long bid;
		int handTier;

		Hand(String input)
		{
			var split = input.split(" ");
			hand = split[0];
			bid = Integer.parseInt(split[1]);

			handTier = handTierType(hand);
		}

		Card[] Cards()
		{
			var list = new ArrayList<Card>();
			for (int x = 0; x < hand.length(); x++)
			{
				list.add(new Card(hand.charAt(x)));
			}

			return list.toArray(new Card[0]);
		}

		int handTierType(String hand)
		{
			HashMap<Character, Integer> counts = getCounts(hand);

			var map = counts.keySet().toArray(new Character[0]);

			// five of a kind
			if (map.length == 1)
				return 7;

			var first = counts.get(map[0]);
			if (map.length == 2)
			{
				// four of a kind
				if (first == 1 || first == 4)   // single card or 4 cards
					return 6;

				// full house
				return 5;
			}

			var second = counts.get(map[1]);
			var third = counts.get(map[2]);
			if (map.length == 3)
			{
				// three of a kind
				if (first == 3 || second == 3 || third == 3)
					return 4;

				// two pair
				return 3;
			}

			// one pair
			if (map.length == 4)
				return 2;

			// one card high
			return 1;
		}

		private static HashMap<Character, Integer> getCounts(String hand)
		{
			HashMap<Character, Integer> counts = new HashMap<>();
			for (int x = 0; x < hand.length(); x++)
			{
				var card = hand.charAt(x);
				int count = counts.getOrDefault(card, 0);
				count++;
 				counts.put(card, count);
			}
			return counts;
		}

		String upcastHand()
		{
			if (!hand.contains("J"))
				return hand;

			int lowestIdx = 999;
			for (char c : hand.toCharArray())
			{
				lowestIdx = Math.min(lowestIdx, GetCardTypes().indexOf(c));
			}

			var counts = getCounts(hand);
			char whichCharacter = GetCardTypes().charAt(lowestIdx);
			switch (handTierType(hand))
			{
				case 7:  // 5 of a kind                         update to 5 of a kind
					return "AAAAA";

				case 6:  // 4 of a kind                         update to 5 of a kind
					// 2JJJJ, J2JJJ, JJ2JJ, JJJ2J, JJJJ2
					// J2222, 2J222, 22J22, 222J2, 2222J
				case 5:  // full house                          update to 5 of a kind
					// 22JJJ, JJJ22, J2J2J
					// JJ222, 222JJ, 2J2J2
				case 1:  // high card
					// J2345, 2J345, 23J45, 234J5, 2345J        update to a pair
				default:
					break;  // return the common transform below

				case 4:  // 3 of a kind                         update to 4 of a kind
					// JJJ23, J2J3J, 2J3JJ, 2JJ3J, 2J3JJ, 23JJJ
					// 222J3, 2J223, 2J232, 22J23, 222J3, 2223J
					// 333J2, 3J332, 3J323, 33J32, 333J2, 3332J
					if (counts.get('J') == 1)
					{
						for (var which : counts.keySet())
						{
							if (counts.get(which) == 3)
								return hand.replace("J", "" + which);
						}
					}

				case 2:  // 1 pair
					// JJ234, J234J, J23J4, J2J34, 234JJ
					// J2234, 2J234, 22J34, 223J4, 2234J
					for (var which : counts.keySet())
					{
						if (counts.get(which) == 2 && which != 'J')
							return hand.replace("J", ""+which);
					}
					break;

				case 3:  // 2 pair
					// 22J44, 2J244, 224J4, 2244J
					if (counts.get('J') == 1)
						return hand.replace("J", ""+whichCharacter);    // full house

					// JJ244, 22J4J, 224JJ
					for (var which : counts.keySet())   // 4 of a kind
					{
						if (counts.get(which) == 2 && which != 'J')
							return hand.replace("J", ""+which);
					}
					break;
			}

			return hand.replace("J", ""+whichCharacter);
		}

		@Override
		public int compareTo(Hand inHand)
		{
			var handScore = handTierType(part2 ? upcastHand() : hand);
			var inHandScore = handTierType(part2 ? inHand.upcastHand() : inHand.hand);

			if (handScore > inHandScore)
				return 1;
			else if (inHandScore > handScore)
				return -1;

			// if equal main score, compare card scores
			var ourHand = Cards();
			var otherHand = inHand.Cards();
			for (int x = 0; x < Cards().length; x++)
			{
				var compVal = ourHand[x].compareTo(otherHand[x]);
				if (compVal != 0)
					return compVal;
			}

			return 0;
		}
	}

	static String jerTest =
		"""
            23456 100
            J3456 200
            2J456 300
            23J56 400
            234J6 500
            2345J 600
            """;


	static String input =
		"""
			TT999 460
			96T99 361
			K264Q 948
			K8TT3 68
			T5JT5 730
			88789 928
			262A6 561
			AA5AA 673
			2AT22 968
			9492K 597
			74745 793
			28JKK 149
			98395 390
			KK783 292
			43TK7 753
			QQ359 558
			46947 311
			44999 804
			454Q7 430
			744JJ 87
			2J62A 241
			K52QT 688
			77797 592
			TTTAJ 91
			922KK 874
			97277 193
			A3876 467
			53445 266
			882KK 300
			78944 636
			5A653 126
			59563 373
			877A7 582
			K555K 762
			KKQ8Q 287
			34488 713
			5KQ7Q 26
			AKAJK 128
			53537 852
			TT825 817
			78487 902
			6J626 941
			6J666 550
			K8A88 142
			89QA6 6
			QATQT 339
			75857 965
			9Q5T4 80
			K48Q2 648
			55529 184
			33J3J 11
			99992 765
			76667 534
			22237 901
			Q2AQJ 69
			Q7958 640
			9886A 447
			72TA9 486
			TTKK3 526
			65A66 964
			99922 352
			3QT47 516
			A744A 469
			AQQ49 887
			K4K4J 17
			QQ8J8 957
			KJ777 995
			94QQQ 43
			KTJT9 854
			JK328 214
			232TJ 159
			J2555 698
			6JTTT 107
			2962T 408
			7227J 409
			92652 733
			TKK3K 810
			736JJ 789
			77447 359
			74444 543
			Q598Q 562
			5J252 951
			9TT7J 627
			JJ888 763
			3QQ33 399
			73AAA 44
			8383J 441
			5KKJK 402
			A384A 424
			AQAK6 579
			77QQT 908
			A7JA7 917
			K2622 427
			5KJ47 548
			Q999Q 475
			3896Q 918
			9J636 650
			24226 576
			44747 737
			J2KKJ 893
			77477 487
			96862 883
			J5333 786
			J4334 316
			JKKKK 670
			J2873 209
			44544 505
			5KK5T 910
			7QQQ7 845
			Q272J 841
			A3AJA 119
			5T22T 601
			88QTJ 360
			AAAJA 166
			J45K2 691
			5A9J8 190
			33383 899
			Q88Q3 787
			95QQ5 567
			Q8KJ5 732
			77272 482
			A5T29 384
			85J55 823
			JKTKK 875
			74238 1
			92898 578
			2T965 997
			34982 639
			778Q7 318
			6KQQQ 30
			8JT83 611
			8T24T 760
			664T5 512
			7Q5Q6 671
			59599 934
			83499 740
			23AK5 926
			7KJJK 932
			J6AAA 707
			3Q4Q4 840
			4J445 615
			83J82 157
			55TT3 985
			3Q274 51
			T99TT 426
			4J47Q 616
			44464 407
			88853 741
			66669 189
			97853 333
			52Q28 136
			8K2A2 118
			JTTKK 74
			77878 834
			65732 556
			89A3T 693
			QJ656 536
			J3893 101
			5T487 88
			TQTTT 702
			4T23T 642
			8A27K 735
			T77T7 462
			J8779 218
			465T4 283
			T5T6J 231
			K279K 632
			K2449 888
			24255 381
			T345T 555
			A6777 23
			92T3Q 454
			8TTT8 904
			87788 222
			993KK 956
			AAAAT 105
			TTT6T 751
			95898 617
			J7TQ9 33
			QJ66Q 15
			JA9AA 93
			99AA8 326
			A6996 192
			Q5A24 684
			3A3A3 73
			KKATK 468
			TT44K 975
			5A6K4 236
			45554 115
			A8A46 708
			A45AA 657
			38899 239
			999K5 38
			AAA75 155
			93392 7
			JJJJ8 226
			A443A 63
			94KK9 418
			J3336 76
			A29KK 371
			26J22 191
			7Q3K8 121
			88288 605
			794A4 400
			28T43 726
			3233J 301
			464AA 393
			777T5 835
			9JQQ9 353
			66336 734
			785Q8 139
			8Q235 445
			JK4TT 915
			QQQ33 519
			A98TQ 590
			44998 86
			539A4 927
			882K8 278
			99957 168
			5J44J 891
			T43KT 322
			A8Q6T 828
			52J22 432
			66Q26 776
			882J9 909
			T5AAQ 435
			J7J24 363
			25225 382
			66226 113
			3J959 655
			86383 952
			9KJ76 979
			973K9 24
			278A7 18
			Q3KA7 141
			6QJ2Q 769
			TT444 669
			T3J9T 291
			555K6 89
			65QJ8 705
			74577 90
			TQT88 172
			63763 150
			6Q886 924
			JQT62 3
			66ATT 25
			8QA5A 416
			J4TT2 502
			JK7KK 377
			8J6T2 420
			TT94T 103
			J6K25 581
			J7743 414
			QQQKQ 398
			7A774 864
			7TTT4 559
			T2TJT 358
			T8JQJ 937
			AJ543 39
			7Q82J 22
			43393 338
			99962 882
			89T49 794
			7A887 560
			54334 35
			J4272 187
			7JA28 331
			T8888 439
			QQJ47 94
			J3TQ6 619
			J7J9Q 79
			JJAA9 242
			999KK 954
			4744Q 621
			T3A84 870
			9K692 366
			J9669 994
			39999 245
			554J5 463
			J844J 456
			37QTQ 575
			T2866 224
			KJKKJ 850
			JT6QT 586
			99772 289
			T2556 199
			88799 820
			7K4Q6 466
			8K8K8 976
			5887J 803
			964QT 720
			AA4AA 313
			44433 310
			2A222 449
			A39JQ 392
			93QK6 488
			A92JA 919
			A963A 321
			AA77A 127
			2J232 788
			87722 346
			TKAQ9 267
			95565 843
			37TT3 154
			66AA9 905
			34Q38 132
			66526 246
			Q6J66 422
			J6226 48
			48493 106
			3QQ45 889
			52525 790
			4J6KA 571
			AJATJ 464
			K3AAA 572
			55TT4 593
			QJ575 577
			TQ85K 55
			68222 589
			2JQ2Q 819
			5TT5T 759
			QQAQA 811
			43A3J 907
			T89A7 727
			J2777 797
			TTTT9 45
			27777 791
			JQ254 668
			K222K 304
			T888T 686
			2TJ5J 514
			J8A83 925
			TA774 419
			TQJ92 676
			8KAAJ 448
			AK4T3 223
			J7667 748
			A2A88 14
			66266 297
			K2365 472
			83866 227
			K8558 46
			58892 654
			8T898 433
			K7KJ8 757
			A3Q8Q 749
			QAQ6Q 724
			99J9T 710
			55858 867
			95A9A 57
			TKK6T 712
			64AA3 330
			5Q3T5 782
			46J72 369
			J2T56 884
			95859 210
			TTTT8 405
			QA2K6 554
			66T4J 137
			QKJQQ 124
			8J2A8 403
			8JQ8J 517
			22442 625
			63QQ6 261
			KKQQJ 736
			ATATA 978
			Q673A 939
			3AJ86 930
			QJJT2 662
			79979 859
			TJ222 743
			9A9J9 879
			5595J 477
			5KT49 295
			9998Q 646
			77Q77 513
			T5KQJ 959
			TATJ6 364
			6T7KA 779
			44K7K 20
			J63J3 591
			33233 368
			AAJ5A 354
			8Q732 269
			77J73 900
			QA8J7 687
			K66KJ 47
			Q23K8 825
			52A4T 169
			KK244 71
			9J799 653
			A46TQ 27
			44Q54 989
			K7623 293
			JTJTJ 722
			4J444 140
			935T4 276
			8QQQ4 205
			55Q5A 955
			95585 201
			TT44T 661
			38KK8 457
			4K6Q5 212
			2TK2T 244
			A66A4 814
			98TJJ 497
			2T79Q 446
			JA3TT 612
			A55AA 163
			TTTA3 481
			5AKAA 234
			22522 490
			QT6J8 294
			33KJK 237
			T6666 348
			9A384 700
			9896J 608
			26272 588
			74QQ7 225
			J2TT5 783
			KQTJ4 238
			T8885 444
			259K4 264
			54T44 986
			QQ88Q 421
			7777K 476
			J4874 680
			48K9Q 993
			23422 643
			44448 221
			KKK55 85
			A4TAA 308
			KAJ2Q 56
			84A8A 522
			JQQJQ 284
			64AK9 423
			9345Q 52
			86A43 824
			66J56 906
			55557 568
			44455 725
			6J7AQ 607
			QQQQ3 335
			33353 98
			KK9KJ 831
			3J59J 265
			KQQQK 281
			T2A7Q 913
			Q73Q3 633
			34483 387
			989AJ 229
			392TK 885
			666TT 383
			TQ623 672
			9Q9Q7 134
			3663J 860
			TJTJT 594
			68666 62
			KKK3K 437
			8J99K 483
			JJ4QK 711
			J99AT 410
			8J363 678
			TKTK5 452
			QQQQ6 963
			KQQ9J 606
			3K933 337
			T5AJ9 501
			333TQ 181
			49T92 81
			78A67 92
			8J48T 849
			J339A 953
			2436K 66
			2222J 967
			TQTQQ 156
			AAQA4 521
			55855 470
			2A85A 853
			JJJJJ 396
			66656 417
			36356 306
			66K66 916
			K6944 940
			6K675 385
			QQ55Q 947
			Q5555 878
			84889 704
			3TTT6 587
			77A77 29
			2T657 719
			TTTT2 525
			A8AAA 99
			832TJ 663
			578J7 406
			Q2Q63 243
			56T8Q 766
			22A26 355
			727QJ 599
			9K99J 778
			4J4J4 235
			J3QJ2 709
			69K8A 4
			366K6 754
			2K7K2 19
			JJQAJ 319
			T4TTT 378
			29292 570
			AAJ8A 170
			88528 260
			ATKAA 945
			T5324 412
			28844 546
			3J474 856
			K4AAK 656
			52527 262
			8TK44 628
			K5TK3 391
			94JQ9 897
			66AA2 167
			TJA73 977
			86J4K 549
			7Q77Q 176
			8KT2J 539
			Q5QQQ 65
			4Q88Q 207
			44438 198
			A8888 75
			K6A58 164
			Q22QQ 675
			KAQKK 496
			8848Q 637
			68573 881
			A8T65 846
			J66K5 240
			JAQQA 832
			3232J 818
			88788 379
			Q22J2 258
			75687 716
			J8T99 280
			KJ27J 674
			J595J 682
			74A22 508
			3ATA6 474
			83388 537
			5ATAT 774
			48888 827
			666KA 714
			7JAJA 775
			TK66K 96
			T2242 960
			99699 197
			QJQTK 651
			A8Q72 731
			KKJK8 161
			87542 112
			7AK35 344
			QQQ9Q 455
			KKQJK 219
			969KT 13
			93933 801
			4494K 944
			KQK9Q 949
			QK227 620
			99J76 204
			7953T 438
			4J64J 367
			QAQA7 631
			57544 492
			K6KJK 350
			8JQQ4 773
			6T253 375
			J55JJ 531
			TATTA 683
			J4939 752
			A3J2T 296
			A283T 861
			JQ35K 340
			J8946 933
			445K5 194
			TTJTT 950
			T5Q64 569
			286QA 992
			23Q25 461
			7J59T 652
			8AA94 356
			T2KJ6 551
			AA3AA 206
			226KK 981
			QJKTT 216
			3674Q 342
			66996 890
			9K7AT 302
			22A8A 851
			9A8T5 272
			6886J 179
			JAJ43 848
			JK7K7 813
			3QQQ5 830
			74462 111
			2849J 857
			474K7 110
			3J333 117
			6A666 323
			778K8 665
			95K95 609
			TTT5A 78
			592T5 873
			KT54K 987
			K282K 374
			53565 912
			7K977 806
			4996T 255
			Q49Q9 658
			TQ2K4 473
			5JTJ5 478
			66KK6 173
			QJ3QK 784
			95535 324
			QJJJQ 299
			56555 177
			484T8 603
			4QQQ4 217
			56QK8 596
			K784T 552
			9972A 767
			JT4TJ 327
			JTA8A 314
			A42QK 829
			79K54 772
			3AA3A 701
			AAA22 566
			K85KT 527
			KA385 998
			K9QT8 40
			78KTQ 458
			9Q74A 758
			QAAAQ 357
			28788 151
			22QA3 152
			T438Q 649
			555J3 388
			444Q4 211
			83383 892
			87333 182
			48T35 428
			T6T99 723
			563KK 565
			37437 41
			24337 183
			6TQ5Q 865
			JA525 808
			A924Q 715
			5J7J2 186
			2488Q 598
			872KT 325
			76A4Q 805
			898JJ 746
			66KK7 564
			5QAQQ 425
			99J99 248
			Q3933 336
			4T23A 666
			Q2AAT 973
			2J6J7 200
			KAKK3 315
			45Q4A 837
			4499A 520
			29424 131
			25J5J 257
			8AAJ8 802
			3933J 961
			34333 880
			7J3Q2 413
			T8833 877
			8799J 202
			77836 792
			88822 395
			99552 660
			A74A2 431
			778J7 515
			KK989 574
			49488 146
			7A49T 697
			3T38T 692
			34943 233
			KQKKK 894
			44A84 351
			999JJ 329
			33K3K 114
			75583 411
			74424 303
			9Q8KA 2
			44442 389
			24A2A 334
			Q2867 584
			66644 332
			6897J 626
			687K8 230
			J3352 138
			Q754K 8
			TJT33 999
			85249 271
			A22A2 298
			4TJ4T 855
			668Q2 822
			35334 376
			7JA8J 251
			8JT28 585
			J56T8 781
			J555J 256
			4JTK5 228
			99TA3 491
			7AA74 347
			88838 401
			38788 958
			QQ323 434
			65868 718
			5555T 836
			52TJ9 143
			ATJAT 943
			6996Q 624
			78TAJ 869
			AA2Q2 12
			75882 815
			8A9Q4 404
			3A24A 610
			3QQ2Q 83
			T4T88 67
			7QJQ9 494
			49244 914
			TTKTJ 450
			77333 996
			84484 286
			4QTTQ 866
			3K457 982
			A9257 171
			428K6 178
			A5AJ5 991
			AAA93 305
			33339 807
			54884 130
			KTTT3 484
			65Q55 158
			ATTTT 750
			KK2JT 328
			29K7A 946
			8TJ4J 614
			35T9K 988
			86348 274
			78A4A 838
			55TT2 341
			943AJ 518
			4J638 370
			4KKK4 109
			25K24 858
			49378 524
			J2QQ4 729
			AJJ95 795
			33265 125
			6652A 799
			Q664Q 220
			K5997 638
			JJ2AA 695
			2JQTT 942
			55455 320
			3QQJQ 429
			523TQ 506
			32J77 966
			89489 738
			J9585 175
			54255 785
			36656 442
			84J32 876
			3K3T2 145
			95559 645
			2Q478 135
			KJ973 273
			9TQ57 798
			444JK 545
			9AJ4T 208
			J6446 896
			A9959 277
			325J4 58
			A62T9 275
			72K89 644
			7A555 984
			9588J 254
			96J2J 349
			KKAAA 600
			K8T73 1000
			48488 279
			Q5QQJ 703
			442T2 116
			A5A75 252
			A566A 821
			9J594 215
			53353 196
			Q7JQQ 936
			8AAAK 768
			AJ364 717
			79743 529
			97457 268
			8T98J 980
			9977Q 465
			44333 630
			55775 613
			TT5TT 935
			KJJ5K 100
			87T86 706
			6T2JT 923
			AAT8T 903
			KQQAK 180
			63363 970
			747Q7 796
			J6866 872
			676J6 21
			K938T 647
			QQQ6J 203
			2J228 538
			53TJ5 480
			4446K 309
			72222 160
			6954Q 144
			7J7Q7 728
			37A82 685
			JKK3A 696
			9Q76J 493
			72J7T 938
			JJAAA 623
			239Q2 386
			79999 42
			99TTK 104
			Q3999 755
			64463 123
			6QK32 290
			696J4 983
			J699T 61
			K2AAT 742
			3333T 213
			8T2J2 72
			AQQQQ 510
			TJ85Q 770
			88868 485
			A3QA8 971
			7QQ59 886
			66Q6Q 898
			Q667Q 102
			772Q7 285
			KKKKT 108
			T4834 895
			9TAA6 362
			62222 64
			5JQ3Q 962
			96567 165
			9924Q 871
			7T76T 635
			77866 972
			885Q8 188
			555J5 509
			K377K 28
			66636 833
			33337 70
			26226 120
			5A555 32
			8294A 739
			9899K 259
			A848T 641
			88J88 528
			Q65Q6 31
			55J93 847
			KKQ6Q 500
			9J36J 921
			57QQQ 541
			4J295 489
			6J6J6 451
			62444 771
			J888A 929
			T768K 618
			5A4QJ 580
			A2QAA 50
			Q5Q52 10
			7QQQQ 97
			A7Q5K 148
			55535 800
			TT77T 372
			7J774 629
			TJAK6 249
			A444A 689
			QT24T 34
			4297A 690
			QQ9JQ 247
			93838 270
			A4Q24 380
			JQ6TJ 343
			7777T 312
			9JTT8 53
			27575 622
			KT725 9
			T7767 84
			85858 250
			KQQ55 232
			KK8K4 185
			3Q333 37
			2Q662 532
			KKKK8 263
			A5AQ3 16
			44337 540
			558J8 443
			8Q432 816
			363QQ 282
			Q6666 699
			85232 839
			K8A2Q 863
			Q9595 667
			65792 969
			QJQQA 535
			3K398 307
			888Q2 812
			8QA87 777
			93669 542
			TJ9TT 507
			33398 922
			6JQAJ 931
			52A55 162
			A4464 842
			K533K 122
			4QQ47 634
			KQTT4 82
			44T44 868
			9J7AJ 436
			JA6J4 553
			JJJ77 174
			AAJ45 761
			5566J 54
			Q3223 764
			QAKKJ 453
			6JQ33 253
			KKK4K 77
			79977 59
			77776 129
			Q4424 530
			A3448 498
			T66TT 583
			6J263 60
			56K97 153
			3A623 745
			TT658 511
			93K4T 499
			9A576 756
			69T66 862
			2K2Q9 5
			2A343 133
			7J776 974
			AQTAT 36
			7QA42 604
			KKK53 780
			TJ72A 602
			266QJ 288
			AJ35T 679
			94444 459
			7J777 397
			KK644 659
			655A6 95
			6986K 664
			TJ5QT 479
			8369J 503
			QJA2A 544
			Q4JQ4 826
			J7292 345
			K7767 557
			J6999 595
			66856 533
			9A9A9 744
			AJ333 504
			22223 844
			73667 394
			8J285 49
			K8888 317
			K4426 990
			ATT26 911
			AK3KA 547
			7A7A7 523
			6J669 920
			K9K92 495
			98899 809
			4KQ39 573
			QJJQA 365
			75775 195
			4297Q 147
			82K9T 694
			3K66J 681
			AAAA7 747
			A66AQ 471
			57T79 415
			37T48 563
			T33T3 440
			79422 721
			QJQQQ 677
			""";

	static String testInput = """
		32T3K 765
		T55J5 684
		KK677 28
		KTJJT 220
		QQQJA 483
		""";

}
