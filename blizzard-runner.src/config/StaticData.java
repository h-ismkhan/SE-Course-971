/*    */ package config;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ 
/*    */ public class StaticData
/*    */ {
/*  8 */   public static String EXP_HOME = "C:/My MSc/ThesisWorks/Crowdsource_Knowledge_Base/SOCommentStudy/exploratory";
/*    */ 
/* 12 */   public static String RQ_HOME = EXP_HOME + 
/* 12 */     "/csv/java-post-all/dbresults/RQs";
/* 13 */   public static String FASTTEXT_DIR = "C:/My MSc/ThesisWorks/BigData_Code_Search/w2vec/fastText";
/* 14 */   public static String BAT_FILE_PATH = "C:/tmp/w2wSimBZ.bat";
/*    */ 
/* 16 */   public static String BRICK_EXP = System.getProperty("user.dir");
/*    */ 
/* 18 */   public static String JDK_SRC_PATH = "C:/Program Files/Java/jdk1.7.0_07/src";
/*    */ 
/* 20 */   public static String SSYSTEMS = "C:/My MSc/ThesisWorks/Crowdsource_Knowledge_Base/SOTraceQData/ssystems";
/* 21 */   static String Database_name = "m4cpbugsv2";
/*    */ 
/* 25 */   public static String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=" + 
/* 25 */     Database_name + ";integratedSecurity=true";
/* 26 */   public static String Driver_name = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
/*    */ 
/* 28 */   public static double SIGNIFICANCE_THRESHOLD = 0.001D;
/* 29 */   public static int DOI_TOPK_THRESHOLD = 5;
/*    */ 
/* 31 */   public static int MAX_PE_SUGGESTED_QUERY_LEN = 30;
/* 32 */   public static int MAX_NL_SUGGESTED_QUERY_LEN = 8;
/* 33 */   public static int MAX_ST_SUGGESTED_QUERY_LEN = 11;
/*    */ 
/* 36 */   public static int MAX_QUERY_LEN = 1024;
/* 37 */   public static int MAX_ST_ENTRY_LEN = 10;
/* 38 */   public static int MAX_ST_THRESHOLD = 10;
/*    */ 
/* 40 */   public static boolean DISCARD_TOO_GOOD = false;
/*    */ 
/* 55 */   public static int CROWD_API_OCC_THRESHOLD = 5;
/*    */ 
/*    */   public static Connection getDBConnection()
/*    */   {
/* 43 */     Connection connection = null;
/*    */     try {
/* 45 */       Class.forName(Driver_name).newInstance();
/* 46 */       connection = 
/* 47 */         DriverManager.getConnection(connectionString);
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/* 52 */     return connection;
/*    */   }
/*    */ }

/* Location:           G:\_tobedecided\Compressed\BLIZZARD-Replication-Package-ESEC-FSE2018-master\blizzard-runner.jar
 * Qualified Name:     config.StaticData
 * JD-Core Version:    0.6.2
 */