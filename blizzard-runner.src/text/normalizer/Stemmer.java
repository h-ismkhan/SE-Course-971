/*     */ package text.normalizer;
/*     */ 
/*     */ public class Stemmer
/*     */ {
/*     */   private String Clean(String str)
/*     */   {
/*  24 */     int last = str.length();
/*     */ 
/*  26 */     Character ch = new Character(str.charAt(0));
/*  27 */     String temp = "";
/*     */ 
/*  29 */     for (int i = 0; i < last; i++) {
/*  30 */       if (Character.isLetterOrDigit(str.charAt(i))) {
/*  31 */         temp = temp + str.charAt(i);
/*     */       }
/*     */     }
/*  34 */     return temp;
/*     */   }
/*     */ 
/*     */   private boolean hasSuffix(String word, String suffix, NewString stem)
/*     */   {
/*  39 */     String tmp = "";
/*     */ 
/*  41 */     if (word.length() <= suffix.length())
/*  42 */       return false;
/*  43 */     if ((suffix.length() > 1) && 
/*  44 */       (word.charAt(word.length() - 2) != suffix.charAt(suffix.length() - 2))) {
/*  45 */       return false;
/*     */     }
/*  47 */     stem.str = "";
/*     */ 
/*  49 */     for (int i = 0; i < word.length() - suffix.length(); i++)
/*  50 */       stem.str += word.charAt(i);
/*  51 */     tmp = stem.str;
/*     */ 
/*  53 */     for (int i = 0; i < suffix.length(); i++) {
/*  54 */       tmp = tmp + suffix.charAt(i);
/*     */     }
/*  56 */     if (tmp.compareTo(word) == 0) {
/*  57 */       return true;
/*     */     }
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean vowel(char ch, char prev) {
/*  63 */     switch (ch) { case 'a':
/*     */     case 'e':
/*     */     case 'i':
/*     */     case 'o':
/*     */     case 'u':
/*  65 */       return true;
/*     */     case 'y':
/*  68 */       switch (prev) { case 'a':
/*     */       case 'e':
/*     */       case 'i':
/*     */       case 'o':
/*     */       case 'u':
/*  70 */         return false;
/*     */       }
/*     */ 
/*  73 */       return true;
/*     */     }
/*     */ 
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */   private int measure(String stem)
/*     */   {
/*  84 */     int i = 0; int count = 0;
/*  85 */     int length = stem.length();
/*     */ 
/*  87 */     while (i < length) {
/*  88 */       for (; i < length; i++) {
/*  89 */         if (i > 0 ? 
/*  90 */           vowel(stem.charAt(i), stem.charAt(i - 1)) : 
/*  94 */           vowel(stem.charAt(i), 'a'))
/*     */         {
/*     */           break;
/*     */         }
/*     */       }
/*  99 */       for (i++; i < length; i++) {
/* 100 */         if (i > 0 ? 
/* 101 */           !vowel(stem.charAt(i), stem.charAt(i - 1)) : 
/* 105 */           !vowel(stem.charAt(i), '?')) {
/*     */           break;
/*     */         }
/*     */       }
/* 109 */       if (i < length) {
/* 110 */         count++;
/* 111 */         i++;
/*     */       }
/*     */     }
/*     */ 
/* 115 */     return count;
/*     */   }
/*     */ 
/*     */   private boolean containsVowel(String word)
/*     */   {
/* 120 */     for (int i = 0; i < word.length(); i++) {
/* 121 */       if (i > 0) {
/* 122 */         if (vowel(word.charAt(i), word.charAt(i - 1))) {
/* 123 */           return true;
/*     */         }
/*     */       }
/* 126 */       else if (vowel(word.charAt(0), 'a')) {
/* 127 */         return true;
/*     */       }
/*     */     }
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean cvc(String str) {
/* 134 */     int length = str.length();
/*     */ 
/* 136 */     if (length < 3) {
/* 137 */       return false;
/*     */     }
/* 139 */     if ((!vowel(str.charAt(length - 1), str.charAt(length - 2))) && 
/* 140 */       (str.charAt(length - 1) != 'w') && (str.charAt(length - 1) != 'x') && (str.charAt(length - 1) != 'y') && 
/* 141 */       (vowel(str.charAt(length - 2), str.charAt(length - 3))))
/*     */     {
/* 143 */       if (length == 3) {
/* 144 */         if (!vowel(str.charAt(0), '?')) {
/* 145 */           return true;
/*     */         }
/* 147 */         return false;
/*     */       }
/*     */ 
/* 150 */       if (!vowel(str.charAt(length - 3), str.charAt(length - 4))) {
/* 151 */         return true;
/*     */       }
/* 153 */       return false;
/*     */     }
/*     */ 
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */   private String step1(String str)
/*     */   {
/* 162 */     NewString stem = new NewString();
/*     */ 
/* 164 */     if (str.charAt(str.length() - 1) == 's') {
/* 165 */       if ((hasSuffix(str, "sses", stem)) || (hasSuffix(str, "ies", stem))) {
/* 166 */         String tmp = "";
/* 167 */         for (int i = 0; i < str.length() - 2; i++)
/* 168 */           tmp = tmp + str.charAt(i);
/* 169 */         str = tmp;
/*     */       }
/*     */       else {
/* 172 */         if ((str.length() == 1) && (str.charAt(str.length() - 1) == 's')) {
/* 173 */           str = "";
/* 174 */           return str;
/*     */         }
/* 176 */         if (str.charAt(str.length() - 2) != 's') {
/* 177 */           String tmp = "";
/* 178 */           for (int i = 0; i < str.length() - 1; i++)
/* 179 */             tmp = tmp + str.charAt(i);
/* 180 */           str = tmp;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 185 */     if (hasSuffix(str, "eed", stem)) {
/* 186 */       if (measure(stem.str) > 0) {
/* 187 */         String tmp = "";
/* 188 */         for (int i = 0; i < str.length() - 1; i++)
/* 189 */           tmp = tmp + str.charAt(i);
/* 190 */         str = tmp;
/*     */       }
/*     */ 
/*     */     }
/* 194 */     else if (((hasSuffix(str, "ed", stem)) || (hasSuffix(str, "ing", stem))) && 
/* 195 */       (containsVowel(stem.str)))
/*     */     {
/* 197 */       String tmp = "";
/* 198 */       for (int i = 0; i < stem.str.length(); i++)
/* 199 */         tmp = tmp + str.charAt(i);
/* 200 */       str = tmp;
/* 201 */       if (str.length() == 1) {
/* 202 */         return str;
/*     */       }
/* 204 */       if ((hasSuffix(str, "at", stem)) || (hasSuffix(str, "bl", stem)) || (hasSuffix(str, "iz", stem))) {
/* 205 */         str = str + "e";
/*     */       }
/*     */       else
/*     */       {
/* 209 */         int length = str.length();
/* 210 */         if ((str.charAt(length - 1) == str.charAt(length - 2)) && 
/* 211 */           (str.charAt(length - 1) != 'l') && (str.charAt(length - 1) != 's') && (str.charAt(length - 1) != 'z'))
/*     */         {
/* 213 */           tmp = "";
/* 214 */           for (int i = 0; i < str.length() - 1; i++)
/* 215 */             tmp = tmp + str.charAt(i);
/* 216 */           str = tmp;
/*     */         }
/* 219 */         else if ((measure(str) == 1) && 
/* 220 */           (cvc(str))) {
/* 221 */           str = str + "e";
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 228 */     if ((hasSuffix(str, "y", stem)) && 
/* 229 */       (containsVowel(stem.str))) {
/* 230 */       String tmp = "";
/* 231 */       for (int i = 0; i < str.length() - 1; i++)
/* 232 */         tmp = tmp + str.charAt(i);
/* 233 */       str = tmp + "i";
/*     */     }
/* 235 */     return str;
/*     */   }
/*     */ 
/*     */   private String step2(String str)
/*     */   {
/* 240 */     String[][] suffixes = { { "ational", "ate" }, 
/* 241 */       { "tional", "tion" }, 
/* 242 */       { "enci", "ence" }, 
/* 243 */       { "anci", "ance" }, 
/* 244 */       { "izer", "ize" }, 
/* 245 */       { "iser", "ize" }, 
/* 246 */       { "abli", "able" }, 
/* 247 */       { "alli", "al" }, 
/* 248 */       { "entli", "ent" }, 
/* 249 */       { "eli", "e" }, 
/* 250 */       { "ousli", "ous" }, 
/* 251 */       { "ization", "ize" }, 
/* 252 */       { "isation", "ize" }, 
/* 253 */       { "ation", "ate" }, 
/* 254 */       { "ator", "ate" }, 
/* 255 */       { "alism", "al" }, 
/* 256 */       { "iveness", "ive" }, 
/* 257 */       { "fulness", "ful" }, 
/* 258 */       { "ousness", "ous" }, 
/* 259 */       { "aliti", "al" }, 
/* 260 */       { "iviti", "ive" }, 
/* 261 */       { "biliti", "ble" } };
/* 262 */     NewString stem = new NewString();
/*     */ 
/* 265 */     for (int index = 0; index < suffixes.length; index++) {
/* 266 */       if ((hasSuffix(str, suffixes[index][0], stem)) && 
/* 267 */         (measure(stem.str) > 0)) {
/* 268 */         str = stem.str + suffixes[index][1];
/* 269 */         return str;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 274 */     return str;
/*     */   }
/*     */ 
/*     */   private String step3(String str)
/*     */   {
/* 279 */     String[][] suffixes = { { "icate", "ic" }, 
/* 280 */       { "ative", "" }, 
/* 281 */       { "alize", "al" }, 
/* 282 */       { "alise", "al" }, 
/* 283 */       { "iciti", "ic" }, 
/* 284 */       { "ical", "ic" }, 
/* 285 */       { "ful", "" }, 
/* 286 */       { "ness", "" }, 
/* 287 */       { "it", "" }, 
/* 288 */       { "for", "" }, 
/* 289 */       { "it", "" }, 
/* 290 */       { "which", "" }, { "he", "" }, { "she", "" }, { "my", "" }, { "you", "" }, { "me", "" } };
/*     */ 
/* 295 */     NewString stem = new NewString();
/*     */ 
/* 297 */     for (int index = 0; index < suffixes.length; index++) {
/* 298 */       if ((hasSuffix(str, suffixes[index][0], stem)) && 
/* 299 */         (measure(stem.str) > 0)) {
/* 300 */         str = stem.str + suffixes[index][1];
/* 301 */         return str;
/*     */       }
/*     */     }
/* 304 */     return str;
/*     */   }
/*     */ 
/*     */   private String step4(String str)
/*     */   {
/* 309 */     String[] suffixes = { "al", "ance", "ence", "er", "ic", "able", "ible", "ant", "ement", "ment", "ent", "sion", "tion", 
/* 310 */       "ou", "ism", "ate", "iti", "ous", "ive", "ize", "ise" };
/*     */ 
/* 312 */     NewString stem = new NewString();
/*     */ 
/* 314 */     for (int index = 0; index < suffixes.length; index++) {
/* 315 */       if (hasSuffix(str, suffixes[index], stem))
/*     */       {
/* 317 */         if (measure(stem.str) > 1) {
/* 318 */           str = stem.str;
/* 319 */           return str;
/*     */         }
/*     */       }
/*     */     }
/* 323 */     return str;
/*     */   }
/*     */ 
/*     */   private String step5(String str)
/*     */   {
/* 328 */     if (str.charAt(str.length() - 1) == 'e') {
/* 329 */       if (measure(str) > 1) {
/* 330 */         String tmp = "";
/* 331 */         for (int i = 0; i < str.length() - 1; i++)
/* 332 */           tmp = tmp + str.charAt(i);
/* 333 */         str = tmp;
/*     */       }
/* 336 */       else if (measure(str) == 1) {
/* 337 */         String stem = "";
/* 338 */         for (int i = 0; i < str.length() - 1; i++) {
/* 339 */           stem = stem + str.charAt(i);
/*     */         }
/* 341 */         if (!cvc(stem)) {
/* 342 */           str = stem;
/*     */         }
/*     */       }
/*     */     }
/* 346 */     if (str.length() == 1)
/* 347 */       return str;
/* 348 */     if ((str.charAt(str.length() - 1) == 'l') && (str.charAt(str.length() - 2) == 'l') && (measure(str) > 1) && 
/* 349 */       (measure(str) > 1)) {
/* 350 */       String tmp = "";
/* 351 */       for (int i = 0; i < str.length() - 1; i++)
/* 352 */         tmp = tmp + str.charAt(i);
/* 353 */       str = tmp;
/*     */     }
/* 355 */     return str;
/*     */   }
/*     */ 
/*     */   private String stripPrefixes(String str)
/*     */   {
/* 360 */     String[] prefixes = { "kilo", "micro", "milli", "intra", "ultra", "mega", "nano", "pico", "pseudo" };
/*     */ 
/* 362 */     int last = prefixes.length;
/* 363 */     for (int i = 0; i < last; i++) {
/* 364 */       if (str.startsWith(prefixes[i])) {
/* 365 */         String temp = "";
/* 366 */         for (int j = 0; j < str.length() - prefixes[i].length(); j++)
/* 367 */           temp = temp + str.charAt(j + prefixes[i].length());
/* 368 */         return temp;
/*     */       }
/*     */     }
/*     */ 
/* 372 */     return str;
/*     */   }
/*     */ 
/*     */   private String stripSuffixes(String str)
/*     */   {
/* 378 */     str = step1(str);
/* 379 */     if (str.length() >= 1)
/* 380 */       str = step2(str);
/* 381 */     if (str.length() >= 1)
/* 382 */       str = step3(str);
/* 383 */     if (str.length() >= 1)
/* 384 */       str = step4(str);
/* 385 */     if (str.length() >= 1) {
/* 386 */       str = step5(str);
/*     */     }
/* 388 */     return str;
/*     */   }
/*     */ 
/*     */   public String stripSuffixes(String str, boolean lightweight) {
/* 392 */     return step1(str);
/*     */   }
/*     */ 
/*     */   public String stripAffixes(String str)
/*     */   {
/* 399 */     str = str.toLowerCase();
/* 400 */     str = Clean(str);
/*     */ 
/* 402 */     if ((str != "") && (str.length() > 2)) {
/* 403 */       str = stripPrefixes(str);
/*     */ 
/* 405 */       if (str != "") {
/* 406 */         str = stripSuffixes(str);
/*     */       }
/*     */     }
/*     */ 
/* 410 */     return str;
/*     */   }
/*     */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     text.normalizer.Stemmer
 * JD-Core Version:    0.6.2
 */