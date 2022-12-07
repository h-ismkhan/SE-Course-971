/*    */ package utility;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.HashMap;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ public class ItemSorter
/*    */ {
/*    */   public static List<Map.Entry<String, Integer>> sortHashMapInt(HashMap<String, Integer> wordMap)
/*    */   {
/* 15 */     List list = new LinkedList(
/* 16 */       wordMap.entrySet());
/* 17 */     list.sort(new Comparator()
/*    */     {
/*    */       public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
/*    */       {
/* 23 */         Integer v2 = (Integer)o2.getValue();
/* 24 */         Integer v1 = (Integer)o1.getValue();
/* 25 */         return v2.compareTo(v1);
/*    */       }
/*    */     });
/* 41 */     return list;
/*    */   }
/*    */ 
/*    */   public static List<Map.Entry<String, Double>> sortHashMapDouble(HashMap<String, Double> wordMap)
/*    */   {
/* 46 */     List list = new LinkedList(
/* 47 */       wordMap.entrySet());
/* 48 */     Collections.sort(list, new Comparator()
/*    */     {
/*    */       public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2)
/*    */       {
/* 54 */         Double v2 = (Double)o2.getValue();
/* 55 */         Double v1 = (Double)o1.getValue();
/* 56 */         return v2.compareTo(v1);
/*    */       }
/*    */     });
/* 60 */     return list;
/*    */   }
/*    */ 
/*    */   public static List<Map.Entry<Integer, Double>> sortHashMapIntDouble(HashMap<Integer, Double> wordMap)
/*    */   {
/* 65 */     List list = new LinkedList(
/* 66 */       wordMap.entrySet());
/* 67 */     Collections.sort(list, new Comparator()
/*    */     {
/*    */       public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2)
/*    */       {
/* 73 */         Double v2 = (Double)o2.getValue();
/* 74 */         Double v1 = (Double)o1.getValue();
/* 75 */         return v2.compareTo(v1);
/*    */       }
/*    */     });
/* 79 */     return list;
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     utility.ItemSorter
 * JD-Core Version:    0.6.2
 */