package com.changwen.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * <b>function:</b>
 *
 * @author liucw on 2016/3/19.
 */
public class OtherSearchTest {

    private Integer ids[]={1,2,3};
    private String citys[]={"aingdao","banjing","changhai"};
    private String descs[]={
            "Qingdao is b beautiful city.",
            "Nanjing is c city of culture.",
            "Shanghai is d bustling city."
    };

    private Directory dir;
    private IndexReader reader;
    private IndexSearcher is;


    /**
     * 先要生成索引
     */
    @Test
    public void createIndexer() throws IOException {
        dir= FSDirectory.open(Paths.get("D:\\lucene5"));

        Analyzer analyzer=new StandardAnalyzer(); // 标准分词器
        IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
        //获取IndexWriter实例
        IndexWriter writer=new IndexWriter(dir, iwc);
        for(int i=0;i<ids.length;i++){
            Document doc=new Document();
            doc.add(new IntField("id", ids[i], Field.Store.YES));
            doc.add(new StringField("city",citys[i],Field.Store.YES));
            doc.add(new TextField("desc", descs[i], Field.Store.YES));
            writer.addDocument(doc); // 添加文档
        }
        writer.close();
    }


    @Before
    public void setUp() throws Exception {
        dir=FSDirectory.open(Paths.get("E:\\lucene5"));
        reader= DirectoryReader.open(dir);
        is=new IndexSearcher(reader);
    }

    @After
    public void tearDown() throws Exception {
        reader.close();
    }

    /**
     * 指定项范围搜索
     * @throws Exception
     */
    @Test
    public void testTermRangeQuery()throws Exception{
        TermRangeQuery query=new TermRangeQuery("desc", new BytesRef("b".getBytes()), new BytesRef("c".getBytes()), true, true);
        TopDocs hits=is.search(query, 10);
        for(ScoreDoc scoreDoc:hits.scoreDocs){
            Document doc=is.doc(scoreDoc.doc);
            System.out.println(doc.get("id"));
            System.out.println(doc.get("city"));
            System.out.println(doc.get("desc"));
        }
    }

    /**
     * 指定数字范围
     * @throws Exception
     */
    @Test
    public void testNumericRangeQuery()throws Exception{
        NumericRangeQuery<Integer> query=NumericRangeQuery.newIntRange("id", 1, 2, true, true);
        TopDocs hits=is.search(query, 10);
        for(ScoreDoc scoreDoc:hits.scoreDocs){
            Document doc=is.doc(scoreDoc.doc);
            System.out.println(doc.get("id"));
            System.out.println(doc.get("city"));
            System.out.println(doc.get("desc"));
        }
    }

    /**
     * 指定字符串开头搜索
     * @throws Exception
     */
    @Test
    public void testPrefixQuery()throws Exception{
        PrefixQuery query=new PrefixQuery(new Term("city","a"));
        TopDocs hits=is.search(query, 10);
        for(ScoreDoc scoreDoc:hits.scoreDocs){
            Document doc=is.doc(scoreDoc.doc);
            System.out.println(doc.get("id"));
            System.out.println(doc.get("city"));
            System.out.println(doc.get("desc"));
        }
    }

    /**
     * 多条件查询
     * @throws Exception
     */
    @Test
    public void testBooleanQuery()throws Exception{
        NumericRangeQuery<Integer> query1=NumericRangeQuery.newIntRange("id", 1, 2, true, true);
        PrefixQuery query2=new PrefixQuery(new Term("city","a"));
        BooleanQuery.Builder booleanQuery=new BooleanQuery.Builder();
        booleanQuery.add(query1,BooleanClause.Occur.MUST);
        booleanQuery.add(query2,BooleanClause.Occur.MUST);
        TopDocs hits=is.search(booleanQuery.build(), 10);
        for(ScoreDoc scoreDoc:hits.scoreDocs){
            Document doc=is.doc(scoreDoc.doc);
            System.out.println(doc.get("id"));
            System.out.println(doc.get("city"));
            System.out.println(doc.get("desc"));
        }
    }

}
