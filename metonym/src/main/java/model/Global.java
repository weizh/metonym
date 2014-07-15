package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Global {

	public static final String E_PERSON = "PERSON", E_NORP = "NORP", E_FAC = "FAC", E_ORG = "ORG", E_LOC = "LOC",
			E_PRODUCT = "PRODUCT", E_EVENT = "EVENT", E_WORK_OF_ART = "WORK_OF_ART", E_LAW = "LAW", E_LANGUAGE = "LANGUAGE",
			E_DATE = "DATE", E_TIME = "TIME", E_PERCENT = "PERCENT", E_MONEY = "MONEY", E_QUANTITY = "QUANTITY",
			E_ORDINAL = "ORDINAL", E_CARDINAL = "CARDINAL", E_GPE = "GPE";

	public static String[] EntityTypes = { E_PERSON, E_NORP, E_FAC, E_ORG, E_LOC, E_PRODUCT, E_EVENT, E_WORK_OF_ART, E_LAW,
			E_LANGUAGE, E_DATE, E_TIME, E_PERCENT, E_MONEY, E_QUANTITY, E_ORDINAL, E_CARDINAL, E_GPE };

	public static HashSet<String> EntityList;

	static {
		EntityList = new HashSet<String>(Arrays.asList(EntityTypes));
	}
	public static final String CSUF = "[CSUF]", P1SUF = "[P1SUF]", N1SUF = "[N1SUF]", P2SUF = "[P2SUF]", N2SUF = "[N2SUF]",
			P3SUF = "[P3SUF]", N3SUF = "[N3SUF]", P4SUF = "[P4SUF]", N4SUF = "[N4SUF]", P5SUF = "[P5SUF]", N5SUF = "[N5SUF]";

	public static final String P1FORM = "[P1FORM]", P2FORM = "[P2FORM]", P3FORM = "[P3FORM]", P4FORM = "[P4FORM]",
			P5FORM = "[P5FORM]", N1FORM = "[N1FORM]", N2FORM = "[N2FORM]", N3FORM = "[N3FORM]", N4FORM = "[N4FORM]",
			N5FORM = "[P5FORM]", CFORM = "[CFORM]";

	public static final String P1TYPE = "[P1TYPE]", P2TYPE = "[N2TYPE]", P3TYPE = "[N3TYPE]";

	public static final String P2WSEQ = "[P2WSEQ]", N2WSEQ = "[N2WSEQ]", P3WSEQ = "[P3WSEQ]", N3WSEQ = "[N3WSEQ]";

	public static final String CPREF = "[CPREF]", P1PREF = "[P1PREF]", P2PREF = "[P2PREF]", P3PREF = "[P3PREF]",
			P4PREF = "[P4PREF]", P5PREF = "[P5PREF]", N1PREF = "[N1PREF]", N2PREF = "[N2PREF]", N3PREF = "[N3PREF]",
			N4PREF = "[N4PREF]", N5PREF = "[N5PREF]";

	public static final String P1END = "[P1END]",P2END = "[P2END]",P3END = "[P3END]",N1END = "[N1END]",N2END = "[N2END]",N3END = "[N3END]";

	public static String P_CC = "CC", P_CD = "CD", P_DT = "DT", P_EX = "EX", P_FW = "FW", P_IN = "IN", P_JJ = "JJ",
			P_JJR = "JJR", P_JJS = "JJS", P_LS = "LS", P_MD = "MD", P_NN = "NN", P_NNS = "NNS", P_NNP = "NNP", P_NNPS = "NNPS",
			P_PDT = "PDT", P_POS = "POS", P_PRP = "PRP", P_PRP$ = "PRP$", P_RB = "RB", P_RBR = "RBR", P_RBS = "RBS", P_RP = "RP",
			P_SYM = "SYM", P_TO = "TO", P_UH = "UH", P_VB = "VB", P_VBD = "VBD", P_VBG = "VBG", P_VBN = "VBN", P_VBP = "VBP",
			P_VBZ = "VBZ", P_WDT = "WDT", P_WP = "WP", P_WP$ = "WP$", P_WRB = "WRB";

	public static String P1TOK = "[P1TOK]", P2TOK = "[P2TOK]", P3TOK = "[P3TOK]", P4TOK = "[P4TOK]", P5TOK = "[P5TOK]",
			N1TOK = "[N1TOK]", N2TOK = "[N2TOK]", N3TOK = "[N3TOK]", N4TOK = "[N4TOK]", N5TOK = "[N5TOK]", P1POS = "[P1POS]",
			P2POS = "[P2POS]", P3POS = "[P3POS]", P4POS = "[P4POS]", P5POS = "[P5POS]", N1POS = "[N1POS]", N2POS = "[N2POS]",
			N3POS = "[N3POS]", N4POS = "[N4POS]", N5POS = "[N5POS]", P1CAP = "[P1CAP]", P2CAP = "[P2CAP]", P3CAP = "[P3CAP]",
			P4CAP = "[P4CAP]", P5CAP = "[P5CAP]", N1CAP = "[N1CAP]", N2CAP = "[N2CAP]", N3CAP = "[N3CAP]", N4CAP = "[N4CAP]",
			N5CAP = "[N5CAP]", CCAP = "[CCAP]", CPOS = "[CPOS]", CTOK = "[CTOK]";

}
