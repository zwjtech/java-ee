package com.changwen.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * <b>function:</b>
 *
 * @author liucw on 2016/3/19.
 */
public class LuceneTest {

    /**
     * Lucene的使用主要体现在两个步骤：
     * 1 创建索引，通过IndexWriter对不同的文件进行索引的创建，并将其保存在索引相关文件存储的位置中。
     * 2 通过索引查寻关键字相关文档
     */
    @Test
    public void indexerCreate() throws IOException, ParseException {
        /*******************1 创建索引***************************************/
        long start = System.currentTimeMillis();

        //第一步，我们需要定义一个词法分析器。这里用标准分词器
        Analyzer analyzer = new StandardAnalyzer();

        //第二步，确定索引文件存储的位置，Lucene提供给我们两种方式：
        //1 本地文件存储
        Directory directory = FSDirectory.open(Paths.get("E:\\lucene"));
        //2 内存存储
        //Directory directory = new RAMDirectory();

        //第三步，创建IndexWriter，进行索引文件的写入。
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, iwc);

        //第四步，内容提取，进行索引的存储。
        File[] files = new File("E:\\lucene\\data").listFiles();

        //索引指定目录的所有文件,然后对每一个文件索引
        assert files != null;
        for (File f : files) {

            System.out.println("索引文件：" + f.getCanonicalPath());

            //获取文档，文档里再设置每个字段
            //申请了一个document对象，这个类似于数据库中的表中的一行。
            Document doc = new Document();

            //把文件存储起来，并存储“表明”为"contents".
            doc.add(new TextField("contents", new FileReader(f)));
            doc.add(new TextField("fileName", f.getName(), Field.Store.YES));
            doc.add(new TextField("fullPath", f.getCanonicalPath(), Field.Store.YES));

            //把Document对象加入到索引创建中。
            indexWriter.addDocument(doc);

            //关闭IndexWriter,提交创建内容
            indexWriter.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("索引：" + indexWriter.numDocs() + " 个文件 花费了" + (end - start) + " 毫秒");


        /************************************2/查找关键字********************************/
        //第一步，打开存储位置
        IndexReader reader = DirectoryReader.open(directory);

        //第二步，创建搜索器
        IndexSearcher is = new IndexSearcher(reader);

        //第三步，类似SQL，进行关键字查询
        QueryParser parser = new QueryParser("contents", analyzer);
        Query query = parser.parse("Zygmunt Saloni");
        long start2 = System.currentTimeMillis();
        TopDocs hits = is.search(query, 10);
        long end2 = System.currentTimeMillis();
        System.out.println("匹配 " + "Zygmunt Saloni" + " ，总共花费" + (end2 - start2) + "毫秒" + "查询到" + hits.totalHits + "个记录");
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);
            System.out.println(doc.get("fullPath"));
        }

        //第四步，关闭查询器等
        reader.close();
    }

}
