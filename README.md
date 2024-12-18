# Lucene Search Engine Project

### **1. Project Overview**
- **Title**: Advanced Search Engine with Query Evaluation and Scoring Models
- **Description**: 
  This project implements a robust search engine capable of indexing and retrieving documents using various query formation strategies and scoring models. It supports:
  - **Field-specific BM25 Scoring**
  - **Vector Space Model (VSM)**
  - **Hybrid Scoring**
  - Complex query types like boolean queries, synonym expansion, and hybrid queries.
- The system is designed for evaluating information retrieval (IR) metrics like MAP scores using TREC_EVAL.

---

### **2. Features**
- Field-specific **BM25 Similarity** with tunable \( k_1 \) and \( b \) parameters for each field.
- Support for multiple **query types**, including:
  - Title-based
  - Description-based
  - Boolean queries
  - Synonym-expanded queries
  - Hybrid queries
- Integration with **Lucene** for indexing and retrieval.
- Custom analyzer for text processing (e.g., stemming, stop-word removal, synonym handling).
- Scoring models supported:
  - BM25
  - Vector Space Model (VSM)
  - Hybrid Scoring (BM25 + VSM)
- Multi-threaded query evaluation for performance optimization.
- Results stored in TREC-compatible format for evaluation.

---

### **3. Prerequisites**
- **Java 17** or later
- **Apache Lucene 9.0+**
- **JUnit 5** for testing
- **Maven** for dependency management
- **WordNet 3.0** 

**Optional**:
- SSD or sufficient RAM for optimal performance during indexing and querying.

---

### **4. Project Structure**
- `src/main/java/`: Contains `SearchApp.java` to start the searching process.
  - `com.group6.searchengine.data`: Contains the classes for storing intermediatory fields.
  - `com.group6.searchengine.parsers`: Handles parsing of datasets.
  - `com.group6.searchengine.indexer`: Handles indexing of datasets (e.g., FBIS, LATimes).
  - `com.group6.searchengine.query`: Contains query formation logic, analyzers, and scoring adjustments.
  - `com.group6.searchengine.search_engine`: Main classes for search and evaluation.
  - `com.group6.searchengine.analyzers`: Custom text analyzer for pre-processing.
- `src/test/java/`: Contains test cases for various modules.
- `resources/`:
  - `custom-analyzer-data/`: Synonym and stopword files for the custom analyzer.
- `assignment-2/`: Dataset files (e.g., FBIS, LATimes, FR94).
- `topics`: Topic files for generating queries.

---

### **5. Installation and Setup**
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```
2. Install dependencies using Maven:
   ```bash
   mvn install:install-file -Dfile=libs/jwi-2.4.0.jar -DgroupId=edu.mit -DartifactId=jwi -Dversion=2.4.0 -Dpackaging=jar
   ```
   ```bash
   mvn clean install
   ```
3. Modify the path to `TREC_EVAL` executable in `run_and_evaluate.sh`:
    ```bash
    TREC_EVAL="/path/to/trec_eval"
    ```

---

### **6. Usage**
#### **Indexing Documents and Query Evaluation**
1. Edit the `topics` file to add or modify topics for querying.
2. Place your datasets in the `assignment-2/` directory (you can get the datasets from [here](https://drive.google.com/file/d/17KpMCaE34eLvdiTINqj1lmxSBSu8BtDP/view)).
3. Run the bash script `run_and_evaluate.sh` to create an index and evaluate `TREC_EVAL` scores of all the query formation techniques:
   ```bash
   ./run_and_evaluate.sh
   ```
4. The index will be created in the `index/` folder. The scores generated by the search engine will be stored in the `result/` folder. The `TREC_EVAL` scores will be stored in `trec_eval_scores\` folder.

#### **Analyzer Evaluation**
1. To test the performance difference between `English_Analyzer` and `Custom_Analyzer`, you would need to change the code particularly at the following places:
   Change the following lines:
   In `src/main/java/com/group6/searchengine/query/QueryFormation.java`:
   ```java
   private Analyzer getQueryAnalyzer() {
        Map<String, Analyzer> fieldAnalyzers = new HashMap<>();
        fieldAnalyzers.put("title", new EnglishAnalyzer());
        fieldAnalyzers.put("abstract", new EnglishAnalyzer());
        fieldAnalyzers.put("text", new EnglishAnalyzer());
        fieldAnalyzers.put("author", new LowercaseAnalyzer());
        fieldAnalyzers.put("date", new DateAnalyzer());
        fieldAnalyzers.put("language", new LowercaseAnalyzer());
        fieldAnalyzers.put("region", new LowercaseAnalyzer());
        fieldAnalyzers.put("section", new EnglishAnalyzer());
        fieldAnalyzers.put("type", new EnglishAnalyzer());
        fieldAnalyzers.put("graphic", new EnglishAnalyzer());
        fieldAnalyzers.put("usDept", new EnglishAnalyzer());
        fieldAnalyzers.put("agency", new EnglishAnalyzer());
        fieldAnalyzers.put("action", new EnglishAnalyzer());
        fieldAnalyzers.put("supplementary", new EnglishAnalyzer());
        fieldAnalyzers.put("profile", new LowercaseAnalyzer());
        fieldAnalyzers.put("pub", new EnglishAnalyzer());
    
        return new PerFieldAnalyzerWrapper(
            new EnglishAnalyzer(),
            fieldAnalyzers
        );
    } 
   ```
   to
   ```java
   private Analyzer getQueryAnalyzer() {
        Map<String, Analyzer> fieldAnalyzers = new HashMap<>();
        fieldAnalyzers.put("title", new CustomAnalyzer());
        fieldAnalyzers.put("abstract", new CustomAnalyzer());
        fieldAnalyzers.put("text", new CustomAnalyzer());
        fieldAnalyzers.put("author", new LowercaseAnalyzer());
        fieldAnalyzers.put("date", new DateAnalyzer());
        fieldAnalyzers.put("language", new LowercaseAnalyzer());
        fieldAnalyzers.put("region", new LowercaseAnalyzer());
        fieldAnalyzers.put("section", new CustomAnalyzer());
        fieldAnalyzers.put("type", new CustomAnalyzer());
        fieldAnalyzers.put("graphic", new CustomAnalyzer());
        fieldAnalyzers.put("usDept", new CustomAnalyzer());
        fieldAnalyzers.put("agency", new CustomAnalyzer());
        fieldAnalyzers.put("action", new CustomAnalyzer());
        fieldAnalyzers.put("supplementary", new CustomAnalyzer());
        fieldAnalyzers.put("profile", new LowercaseAnalyzer());
        fieldAnalyzers.put("pub", new CustomAnalyzer());
    
        return new PerFieldAnalyzerWrapper(
            new CustomAnalyzer(),
            fieldAnalyzers
        );
    } 
   ```
   In `src/main/java/com/group6/searchengine/indexer/Indexer.java`:
   ```java
        this.titleAnalyzer = new EnglishAnalyzer();
        this.abstractAnalyzer = new EnglishAnalyzer();
        this.textAnalyzer = new EnglishAnalyzer();
        this.authorAnalyzer = new LowercaseAnalyzer();
        this.dateAnalyzer = new DateAnalyzer();
        this.languageAnalyzer = new LowercaseAnalyzer();
        this.regionAnalyzer = new LowercaseAnalyzer();
        this.sectionAnalyzer = new EnglishAnalyzer();
        this.typeAnalyzer = new EnglishAnalyzer();
        this.graphicAnalyzer = new EnglishAnalyzer();
        this.usDeptAnalyzer = new EnglishAnalyzer();
        this.agencyAnalyzer = new EnglishAnalyzer();
        this.actionAnalyzer = new EnglishAnalyzer();
        this.supplementaryAnalyzer = new EnglishAnalyzer();
        this.profileAnalyzer = new LowercaseAnalyzer();
        this.pubAnalyzer = new EnglishAnalyzer();
   ```
   to
    ```java
            this.titleAnalyzer = new CustomAnalyzer();
            this.abstractAnalyzer = new CustomAnalyzer();
            this.textAnalyzer = new CustomAnalyzer();
            this.authorAnalyzer = new LowercaseAnalyzer();
            this.dateAnalyzer = new DateAnalyzer();
            this.languageAnalyzer = new LowercaseAnalyzer();
            this.regionAnalyzer = new LowercaseAnalyzer();
            this.sectionAnalyzer = new CustomAnalyzer();
            this.typeAnalyzer = new CustomAnalyzer();
            this.graphicAnalyzer = new CustomAnalyzer();
            this.usDeptAnalyzer = new CustomAnalyzer();
            this.agencyAnalyzer = new CustomAnalyzer();
            this.actionAnalyzer = new CustomAnalyzer();
            this.supplementaryAnalyzer = new CustomAnalyzer();
            this.profileAnalyzer = new LowercaseAnalyzer();
            this.pubAnalyzer = new CustomAnalyzer();
    ```
   Note: Ensure that `CustomAnalyzer` is properly imported.
2. Run the bash script `run_and_evaluate.sh` to create an index and evaluate `TREC_EVAL` scores of all the query formation techniques:
   ```bash
   ./run_and_evaluate.sh
   ```

---

### **7. Performance Summary**

The performance summary based on intitial relevance judgement file `qrels.assignment2.part1` can be defined as follows:

| Query Formation Technique        | Retrieval Model | Custom Analyzer MAP | English Analyzer MAP | Best Performer         |
|----------------------------------|-----------------|----------------------|-----------------------|------------------------|
| **BEST_INFO_WORDS**              | BM25            | 0.1844               | 0.2095                | English Analyzer       |
|                                  | HYBRID          | 0.1841               | 0.2100                | English Analyzer       |
|                                  | VSM             | 0.1043               | 0.1205                | English Analyzer       |
| **BOOLEAN_QUERY_BUILDER**        | BM25            | 0.2748               | 0.2945                | English Analyzer       |
|                                  | HYBRID          | 0.2739               | 0.2949                | English Analyzer       |
|                                  | VSM             | 0.1378               | 0.1689                | English Analyzer       |
| **COMBINED_TITLE_DESCRIPTION**   | BM25            | 0.2524               | 0.2758                | English Analyzer       |
|                                  | HYBRID          | 0.2533               | 0.2760                | English Analyzer       |
|                                  | VSM             | 0.1382               | 0.1676                | English Analyzer       |
| **DESCRIPTION_BASED**            | BM25            | 0.2066               | 0.2281                | English Analyzer       |
|                                  | HYBRID          | 0.2085               | 0.2284                | English Analyzer       |
|                                  | VSM             | 0.1266               | 0.1555                | English Analyzer       |
| **HYBRID**                       | BM25            | 0.2377               | 0.2641                | English Analyzer       |
|                                  | HYBRID          | 0.2376               | 0.2632                | English Analyzer       |
|                                  | VSM             | 0.1368               | 0.1659                | English Analyzer       |
| **NARRATIVE_FOCUSED**            | BM25            | 0.1002               | 0.1108                | English Analyzer       |
|                                  | HYBRID          | 0.1018               | 0.1116                | English Analyzer       |
|                                  | VSM             | 0.0733               | 0.0863                | English Analyzer       |
| **SYNONYM_EXPANDED**             | BM25            | 0.1862               | 0.2006                | English Analyzer       |
|                                  | HYBRID          | 0.1898               | 0.2010                | English Analyzer       |
|                                  | VSM             | 0.1197               | 0.1455                | English Analyzer       |
| **TITLE_BASED**                  | BM25            | 0.2274               | 0.2396                | English Analyzer       |
|                                  | HYBRID          | 0.2223               | 0.2347                | English Analyzer       |
|                                  | VSM             | 0.1073               | 0.1248                | English Analyzer       |

---

#### **Insights from the Results**

1. **English Analyzer Consistently Outperforms the Custom Analyzer:**
   - Across all query formation techniques and retrieval models, the English Analyzer achieved higher MAP scores. 
   - The best overall result came from **BOOLEAN_QUERY_BUILDER** with **HYBRID scoring** using the English Analyzer (MAP: **0.2949**).

2. **Best Query Formation Technique:**
   - **BOOLEAN_QUERY_BUILDER** provided the best MAP scores across both analyzers and retrieval models. 
   - This demonstrates that this query formation approach is well-suited for both analyzers.

3. **Retrieval Model Performance:**
   - **BM25** and **HYBRID** retrieval models consistently outperformed **VSM** across all query formation techniques.
   - **HYBRID** scoring produced slightly better or competitive results when compared to BM25 alone, suggesting that combining retrieval models may yield better performance for some techniques.

4. **Weaker Performance with Custom Analyzer:**
   - The custom analyzer's results were consistently lower. This could be due to overly aggressive token filtering, less effective synonym handling, or suboptimal stopword removal.

---

### **8. Future Work**
- Implement additional scoring models (e.g., TF-IDF with custom weights).
- Add support for proximity queries.
- Optimize query expansion using advanced NLP techniques.

