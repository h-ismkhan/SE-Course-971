/*    */ package similarity;
/*    */ 
/*    */ public class CosineMeasure
/*    */ {
/*    */   public static double getCosineSimilarity(int[] list1, int[] list2)
/*    */   {
/*  6 */     double cosmeasure = 0.0D;
/*  7 */     double mode1 = getMode(list1);
/*  8 */     double mode2 = getMode(list2);
/*  9 */     double upper = 0.0D;
/* 10 */     for (int i = 0; i < list1.length; i++) {
/* 11 */       upper += list1[i] * list2[i];
/*    */     }
/* 13 */     if ((mode1 > 0.0D) && (mode2 > 0.0D)) {
/* 14 */       cosmeasure = upper / (mode1 * mode2);
/*    */     }
/* 16 */     return cosmeasure;
/*    */   }
/*    */ 
/*    */   protected static double getMode(int[] list) {
/* 20 */     double sum = 0.0D;
/* 21 */     int[] arrayOfInt = list; int j = list.length; for (int i = 0; i < j; i++) { int i = arrayOfInt[i];
/* 22 */       sum += i * i;
/*    */     }
/* 24 */     return Math.sqrt(sum);
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     similarity.CosineMeasure
 * JD-Core Version:    0.6.2
 */