/*     */ package ch.gyselanimatioon.miepcraft;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfigurationOptions;
/*     */ 
/*     */ public class Yaml
/*     */ {
/*  11 */   private File file = null;
/*  12 */   private YamlConfiguration yaml = new YamlConfiguration();
/*     */   
/*     */   public Yaml(File file) {
/*  15 */     this.file = file;
/*  16 */     if (!file.exists()) {
/*     */       try {
/*  18 */         file.createNewFile();
/*     */       } catch (IOException e) {
/*  20 */         e.printStackTrace();
/*     */       }
/*     */     }
/*  23 */     load();
/*     */   }
/*     */   
/*     */   public Yaml(String path) {
/*  27 */     this.file = new File(path);
/*  28 */     if (!this.file.exists()) {
/*     */       try {
/*  30 */         this.file.createNewFile();
/*     */       } catch (IOException e) {
/*  32 */         e.printStackTrace();
/*     */       }
/*     */     }
/*  35 */     load();
/*     */   }
/*     */   
/*     */   private void load() {
/*     */     try {
/*  40 */       this.yaml.load(this.file);
/*     */     } catch (Exception e) {
/*  42 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void save()
/*     */   {
/*     */     try
/*     */     {
/*  51 */       this.yaml.save(this.file);
/*     */     } catch (Exception e) {
/*  53 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void delete() {
/*     */     try {
/*  59 */       this.file.delete();
/*     */     } catch (Exception e) {
/*  61 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getInteger(String s)
/*     */   {
/*  73 */     return this.yaml.getInt(s);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void reload()
/*     */   {
/*  80 */     save();
/*  81 */     load();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getString(String s)
/*     */   {
/*  92 */     return this.yaml.getString(s);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object get(String s)
/*     */   {
/* 103 */     return this.yaml.get(s);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getBoolean(String s)
/*     */   {
/* 114 */     return this.yaml.getBoolean(s);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void add(String s, Object o)
/*     */   {
/* 126 */     if (!contains(s)) {
/* 127 */       set(s, o);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addToStringList(String s, String o)
/*     */   {
/* 140 */     this.yaml.getStringList(s).add(o);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeFromStringList(String s, String o)
/*     */   {
/* 153 */     this.yaml.getStringList(s).remove(o);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<String> getStringList(String s)
/*     */   {
/* 166 */     return this.yaml.getStringList(s);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addToIntegerList(String s, int o)
/*     */   {
/* 180 */     this.yaml.getIntegerList(s).add(Integer.valueOf(o));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeFromIntegerList(String s, int o)
/*     */   {
/* 194 */     this.yaml.getIntegerList(s).remove(o);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<Integer> getIntegerList(String s)
/*     */   {
/* 207 */     return this.yaml.getIntegerList(s);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void createNewStringList(String s, List<String> list)
/*     */   {
/* 221 */     this.yaml.set(s, list);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void createNewIntegerList(String s, List<Integer> list)
/*     */   {
/* 235 */     this.yaml.set(s, list);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void remove(String s)
/*     */   {
/* 247 */     set(s, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean contains(String s)
/*     */   {
/* 260 */     return this.yaml.contains(s);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getDouble(String s)
/*     */   {
/* 273 */     return this.yaml.getDouble(s);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void set(String s, Object o)
/*     */   {
/* 287 */     this.yaml.set(s, o);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void increment(String s)
/*     */   {
/* 299 */     this.yaml.set(s, Integer.valueOf(getInteger(s) + 1));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void decrement(String s)
/*     */   {
/* 311 */     this.yaml.set(s, Integer.valueOf(getInteger(s) - 1));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void increment(String s, int i)
/*     */   {
/* 323 */     this.yaml.set(s, Integer.valueOf(getInteger(s) + i));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void decrement(String s, int i)
/*     */   {
/* 335 */     this.yaml.set(s, Integer.valueOf(getInteger(s) - i));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public YamlConfigurationOptions options()
/*     */   {
/* 346 */     return this.yaml.options();
/*     */   }
/*     */ }


/* Location:              C:\Users\phili\Desktop\Bungee\servers\lobby\Miepcraft.jar!\ch\gyselanimatioon\miepcraft\Yaml.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */