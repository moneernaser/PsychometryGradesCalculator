package com.moonbeam.psychometrycalculator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PsychometryFormulas {

	static final int[] QUAN_SCORE_ARRAY = new int[] { 50, 52, 54, 56, 58, 60, 62, 64, 66, 68, 70, 73, 75, 78, 80, 83, 86, 89, 92, 94, 97, 99, 102, 105,
			107, 110, 113, 115, 118, 120, 123, 126, 128, 131, 134, 137, 139, 142, 145, 147, 150 };
	static final int[] VERBAL_SCORES_ARRAY = new int[] { 50, 51, 53, 54, 55, 57, 58, 59, 61, 62, 63, 64, 66, 67, 68, 70, 72, 73, 75, 78, 81, 84, 87,
			90, 93, 96, 99, 102, 105, 109, 112, 116, 120, 123, 127, 130, 134, 138, 142, 146, 150 };
	static final int[] ENG_SCORES_ARRAY = new int[] { 50, 51, 52, 53, 54, 56, 58, 60, 62, 64, 66, 68, 70, 72, 74, 76, 78, 80, 82, 84, 87, 90, 92, 95,
			98, 100, 102, 104, 107, 110, 112, 115, 117, 120, 122, 124, 127, 130, 132, 135, 138, 141, 144, 147, 150 };

	static final Map<Range, Range> GENERAL_SCORE_MAP = new HashMap<Range, Range>() {
		{
			put(new Range(50, 50), new Range(200, 200));
			put(new Range(51, 55), new Range(221, 248));
			put(new Range(56, 60), new Range(249, 276));
			put(new Range(61, 65), new Range(277, 304));
			put(new Range(66, 70), new Range(305, 333));
			put(new Range(71, 75), new Range(334, 361));
			put(new Range(76, 80), new Range(362, 389));
			put(new Range(81, 85), new Range(390, 418));
			put(new Range(86, 90), new Range(419, 446));
			put(new Range(91, 95), new Range(447, 474));
			put(new Range(96, 100), new Range(475, 503));
			put(new Range(101, 105), new Range(504, 531));
			put(new Range(106, 110), new Range(532, 559));
			put(new Range(111, 115), new Range(560, 587));
			put(new Range(116, 120), new Range(588, 616));
			put(new Range(121, 125), new Range(617, 644));
			put(new Range(126, 130), new Range(645, 672));
			put(new Range(131, 135), new Range(673, 701));
			put(new Range(136, 140), new Range(702, 729));
			put(new Range(141, 145), new Range(730, 761));
			put(new Range(146, 149), new Range(762, 795));
			put(new Range(150, 150), new Range(800, 800));
		}
	};

	public static int getSectionScore(Section section, int numOfSections, int correctAnswers) {
		int averageScore = correctAnswers * 2 / numOfSections;
		switch (section) {
		case QUAN:
			return QUAN_SCORE_ARRAY[averageScore];
		case VERBAL:
			return VERBAL_SCORES_ARRAY[averageScore];
		case ENG:
			return ENG_SCORES_ARRAY[averageScore];
		}
		throw new AssertionError();
	}

	public static Range getScoreRange(int weightedScore) {
		for (Map.Entry<Range, Range> scoreRange : GENERAL_SCORE_MAP.entrySet()) {
			if (scoreRange.getKey().contains(weightedScore)) {
				return scoreRange.getValue();
			}
		}
		throw new AssertionError();
	}

	public static Range getScoreSourceRange(int weightedScore) {
		for (Map.Entry<Range, Range> scoreRange : GENERAL_SCORE_MAP.entrySet()) {
			if (scoreRange.getKey().contains(weightedScore)) {
				return scoreRange.getKey();
			}
		}
		throw new AssertionError();
	}

	public static Range getMultiDomainScore(int mathScore, int verbalScore, int engScore) {
		return getScoreRange((2 * mathScore + 2 * verbalScore + engScore) / 5);
	}

	public static Range getHumanitiesWeightedScore(int mathScore, int verbalScore, int engScore) {
		return getScoreRange((mathScore + 3 * verbalScore + engScore) / 5);
	}

	public static Range getSciencesWeightedScore(int mathScore, int verbalScore, int engScore) {
		return getScoreRange((3 * mathScore + verbalScore + engScore) / 5);
	}

	public static int calculateWeightedGrade(int quanScore, int verbalScore, int engScore) {
		double rawAvg = (2 * quanScore + 2 * verbalScore + engScore) / 5;
		Range containingRange = PsychometryFormulas.getScoreSourceRange((int) rawAvg);
		Range scoreRange = getMultiDomainScore(quanScore, verbalScore, engScore);
		if (containingRange.getWidth() == 0) {
			return scoreRange.getFrom();
		} else {
			return (int) (scoreRange.getFrom() + (rawAvg - containingRange.getFrom()) / (containingRange.getWidth()) * scoreRange.getWidth());
		}

	}

	public static class Range implements Serializable {
		int from, to;

		public int getWidth() {
			return to - from;
		}

		public int getFrom() {
			return from;
		}

		public int getTo() {
			return to;
		}

		Range(int from, int to) {
			this.from = from;
			this.to = to;
			assert from >= to;
		}

		public boolean contains(int weightedScore) {
			return weightedScore >= from && weightedScore <= to;
		}

		@Override
		public String toString() {
			return "" + from + " - " + to;
		}
	}

	public enum Section {
		QUAN, VERBAL, ENG
	}
}
