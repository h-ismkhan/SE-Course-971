/*    */ package similarity;
/*    */ 
/*    */ public class Levensthein
/*    */ {
/*    */   public static int LevenshteinDistance(CharSequence lhs, CharSequence rhs)
/*    */   {
/*  6 */     int len0 = lhs.length() + 1;
/*  7 */     int len1 = rhs.length() + 1;
/*    */ 
/* 10 */     int[] cost = new int[len0];
/* 11 */     int[] newcost = new int[len0];
/*    */ 
/* 14 */     for (int i = 0; i < len0; i++) {
/* 15 */       cost[i] = i;
/*    */     }
/*    */ 
/* 20 */     for (int j = 1; j < len1; j++)
/*    */     {
/* 22 */       newcost[0] = j;
/*    */ 
/* 25 */       for (int i = 1; i < len0; i++)
/*    */       {
/* 27 */         int match = lhs.charAt(i - 1) == rhs.charAt(j - 1) ? 0 : 1;
/*    */ 
/* 30 */         int cost_replace = cost[(i - 1)] + match;
/* 31 */         int cost_insert = cost[i] + 1;
/* 32 */         int cost_delete = newcost[(i - 1)] + 1;
/*    */ 
/* 35 */         newcost[i] = Math.min(Math.min(cost_insert, cost_delete), 
/* 36 */           cost_replace);
/*    */       }
/*    */ 
/* 40 */       int[] swap = cost;
/* 41 */       cost = newcost;
/* 42 */       newcost = swap;
/*    */     }
/*    */ 
/* 46 */     return cost[(len0 - 1)];
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     similarity.Levensthein
 * JD-Core Version:    0.6.2
 */