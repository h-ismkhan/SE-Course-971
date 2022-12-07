import java.util.Arrays;
import java.util.HashSet;

public class KeyWords_Java {
	String[] keywordslist = { "abstract", "assert", "boolean", "break", "byte", "byvalue", "case", "catch", "char",
			"class", "const", "continue", "default", "do", "do", "double", "else", "enum", "extends", "false", "final",
			"finally", "float", "for", "future", "generic", "goto", "if", "implements", "import", "inner", "instanceof",
			"int", "interface", "long", "native", "new", "null", "operator", "outer", "package", "private", "protected",
			"public", "rest", "return", "short", "static", "strictfp", "super", "switch", "switch", "this", "throw",
			"throws", "transient", "try", "var", "void", "volatile", "while", "TRUE" };

	String[] stoplist = { "'ll","\\","/","#", "(", ")", "?", "ll", "'ve", "-", "a's", "able", "about", "above", "abroad", "abst",
			"accordance", "according", "accordingly", "across", "act", "actually", "added", "adj", "adopted",
			"affected", "affecting", "affects", "after", "afterwards", "again", "against", "ago", "ah", "ahead",
			"ain't", "all", "allow", "allows", "almost", "alone", "along", "alongside", "already", "also", "although",
			"always", "am", "amid", "amidst", "among", "amongst", "an", "and", "announce", "another", "any", "anybody",
			"anyhow", "anymore", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "apparently", "appear",
			"appreciate", "appropriate", "approximately", "are", "aren", "aren't", "arent", "arise", "around", "as",
			"aside", "ask", "asking", "associated", "at", "auth", "available", "away", "awfully", "back", "backward",
			"backwards", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand",
			"begin", "beginning", "beginnings", "begins", "behind", "being", "believe", "below", "beside", "besides",
			"best", "better", "between", "beyond", "biol", "both", "brief", "briefly", "but", "by", "c'mon", "c's",
			"ca", "came", "can", "can't", "cannot", "cant", "caption", "cause", "causes", "certain", "certainly",
			"changes", "clearly", "co", "co.", "com", "come", "comes", "concerning", "consequently", "consider",
			"considering", "contain", "containing", "contains", "corresponding", "could", "couldn't", "couldnt",
			"course", "currently", "dare", "daren't", "date", "definitely", "described", "despite", "did", "didn't",
			"different", "directly", "do", "does", "doesn't", "doing", "don't", "done", "down", "downwards", "due",
			"during", "each", "ed", "edu", "effect", "eg", "eight", "eighty", "either", "else", "elsewhere", "end",
			"ending", "enough", "entirely", "especially", "et", "et-al", "etc", "even", "ever", "evermore", "every",
			"everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "fairly", "far",
			"farther", "few", "fewer", "ff", "fifth", "first", "five", "fix", "followed", "following", "follows", "for",
			"forever", "former", "formerly", "forth", "forward", "found", "four", "from", "further", "furthermore",
			"gave", "get", "gets", "getting", "give", "given", "gives", "giving", "go", "goes", "going", "gone", "got",
			"gotten", "greetings", "had", "hadn't", "half", "happens", "hardly", "has", "hasn't", "have", "haven't",
			"having", "he", "he'd", "he'll", "he's", "hed", "hello", "help", "hence", "her", "here", "here's",
			"hereafter", "hereby", "herein", "heres", "hereupon", "hers", "herself", "hes", "hi", "hid", "him",
			"himself", "his", "hither", "home", "hopefully", "how", "how's", "howbeit", "however", "hundred", "i'd",
			"i'll", "i'm", "i've", "id", "ie", "if", "ignored", "im", "immediate", "immediately", "importance",
			"important", "in", "inasmuch", "inc", "inc.", "indeed", "index", "indicate", "indicated", "indicates",
			"information", "inner", "inside", "insofar", "instead", "into", "invention", "inward", "is", "isn't", "it",
			"it'd", "it'll", "it's", "itd", "its", "itself", "just", "keep", "keeps", "kept", "keys", "kg", "km",
			"know", "known", "knows", "largely", "last", "lately", "later", "latter", "latterly", "least", "less",
			"lest", "let", "let's", "lets", "like", "liked", "likely", "likewise", "line", "little", "look", "looking",
			"looks", "low", "lower", "ltd", "made", "mainly", "make", "makes", "many", "may", "maybe", "mayn't", "me",
			"mean", "means", "meantime", "meanwhile", "merely", "mg", "might", "mightn't", "million", "mine", "minus",
			"miss", "ml", "more", "moreover", "most", "mostly", "mr", "mrs", "much", "mug", "must", "mustn't", "my",
			"myself", "na", "name", "namely", "nay", "nd", "near", "nearly", "necessarily", "necessary", "need",
			"needn't", "needs", "neither", "never", "neverf", "neverless", "nevertheless", "new", "next", "nine",
			"ninety", "no", "no-one", "nobody", "non", "none", "nonetheless", "noone", "nor", "normally", "nos", "not",
			"noted", "nothing", "notwithstanding", "novel", "now", "nowhere", "obtain", "obtained", "obviously", "of",
			"off", "often", "oh", "ok", "okay", "old", "omitted", "on", "once", "one", "one's", "ones", "only", "onto",
			"opposite", "or", "ord", "other", "others", "otherwise", "ought", "oughtn't", "our", "ours", "ourselves",
			"out", "outside", "over", "overall", "owing", "own", "page", "pages", "part", "particular", "particularly",
			"past", "per", "perhaps", "placed", "please", "plus", "poorly", "possible", "possibly", "potentially", "pp",
			"predominantly", "present", "presumably", "previously", "primarily", "probably", "promptly", "proud",
			"provided", "provides", "put", "que", "quickly", "quite", "qv", "ran", "rather", "rd", "re", "readily",
			"really", "reasonably", "recent", "recently", "ref", "refs", "regarding", "regardless", "regards",
			"related", "relatively", "research", "respectively", "resulted", "resulting", "results", "right", "round",
			"run", "said", "same", "saw", "say", "saying", "says", "sec", "second", "secondly", "section", "see",
			"seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious",
			"seriously", "seven", "several", "shall", "shan't", "she", "she'd", "she'll", "she's", "shed", "shes",
			"should", "shouldn't", "show", "showed", "shown", "showns", "shows", "significant", "significantly",
			"similar", "similarly", "since", "six", "slightly", "so", "some", "somebody", "someday", "somehow",
			"someone", "somethan", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry",
			"specifically", "specified", "specify", "specifying", "state", "states", "still", "stop", "strongly", "sub",
			"substantially", "successfully", "such", "sufficiently", "suggest", "sup", "sure", "t's", "take", "taken",
			"taking", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "that'll", "that's", "that've",
			"thats", "the", "The", "their", "theirs", "them", "themselves", "then", "thence", "there", "there'd",
			"there'll", "there're", "there's", "there've", "thereafter", "thereby", "thered", "therefore", "therein",
			"thereof", "therere", "theres", "thereto", "thereupon", "these", "they", "they'd", "they'll", "they're",
			"they've", "theyd", "theyre", "thing", "things", "think", "third", "thirty", "this", "thorough",
			"thoroughly", "those", "thou", "though", "thoughh", "thousand", "three", "throug", "through", "throughout",
			"thru", "thus", "til", "till", "tip", "to", "together", "too", "took", "toward", "towards", "tried",
			"tries", "truly", "try", "trying", "ts", "twice", "two", "un", "under", "underneath", "undoing",
			"unfortunately", "unless", "unlike", "unlikely", "until", "unto", "up", "upon", "ups", "upwards", "us",
			"use", "used", "useful", "usefully", "usefulness", "uses", "using", "usually", "value", "various", "versus",
			"very", "via", "viz", "vol", "vols", "vs", "want", "wants", "was", "wasn't", "way", "we", "we'd", "we'll",
			"we're", "we've", "wed", "welcome", "well", "went", "were", "weren't", "what", "what'll", "what's",
			"what've", "whatever", "whats", "when", "when's", "whence", "whenever", "where", "where's", "whereafter",
			"whereas", "whereby", "wherein", "wheres", "whereupon", "wherever", "whether", "which", "whichever",
			"while", "whilst", "whim", "whither", "who", "who'd", "who'll", "who's", "whod", "whoever", "whole", "whom",
			"whomever", "whos", "whose", "why", "why's", "widely", "will", "willing", "wish", "with", "within",
			"without", "won't", "wonder", "words", "world", "would", "wouldn't", "www", "yes", "yet", "you", "you'd",
			"you'll", "you're", "you've", "youd", "your", "youre", "yours", "yourself", "yourselves", "zero", "﻿I",
			"﻿a", "﻿able" };

	HashSet<String> keywords;
	HashSet<String> stopWords;
	
	KeyWords_Java() {
		keywords = new HashSet<String>(Arrays.asList(keywordslist));
		stopWords = new HashSet<String>(Arrays.asList(stoplist));
	}

	public Boolean isKeyWord(String x) {
		return keywords.contains(x);
	}
	public Boolean isStopWord(String x) {
		return stopWords.contains(x);
	}
}
