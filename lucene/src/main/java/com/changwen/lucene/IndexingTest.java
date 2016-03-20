package com.changwen.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

/**
 * Lucene是apache软件基金会4 jakarta项目组的一个子项目，
 * 是一个开放源代码的全文检索引擎工具包，但它不是一个完整的全文检索引擎，而是一个全文检索引擎的架构，
 * 提供了完整的查询引擎和索引引擎，部分文本分析引擎（英文与德文两种西方语言）
 */
public class IndexingTest {

	private String ids[]={"1","2","3"};
	private String citys[]={"qingdao","nanjing","shanghai"};
	private String descs[]={
			"Qingdao is a beautiful city.",
			"Nanjing is a city of culture.",
			"Shanghai is a bustling city."
	};

	private Directory dir;
	private IndexWriter writer;

	@Before
	public void setUp() throws Exception {
		dir=FSDirectory.open(Paths.get("E:\\lucene2"));
		Analyzer analyzer=new StandardAnalyzer(); // 标准分词器
		IndexWriterConfig iwc=new IndexWriterConfig(analyzer);

		//获取IndexWriter实例
		writer=new IndexWriter(dir, iwc);

		for(int i=0;i<ids.length;i++){
			Document doc=new Document();
			doc.add(new StringField("id", ids[i], Field.Store.YES));
			doc.add(new StringField("city",citys[i],Field.Store.YES));
			doc.add(new TextField("desc", descs[i], Field.Store.NO));
			writer.addDocument(doc); // 添加文档
		}
		writer.close();
	}



	/**
	 * 第一节：添加文档
	 * 测试写了几个文档
	 */
	@Test
	public void testIndexWriter()throws Exception{
		System.out.println("写入了"+writer.numDocs()+"个文档");
		writer.close();
	}

	/**
	 * 测试读取文档
	 * 每次测试都要在lucene2里删除里面的文件
	 */
	@Test
	public void testIndexReader()throws Exception{
		IndexReader reader=DirectoryReader.open(dir);
		System.out.println("最大文档数："+reader.maxDoc());
		System.out.println("实际文档数："+reader.numDocs());
		reader.close();
	}

	/**
	 * 第二节：删除文档，两种情况
	 * 测试删除 在合并前
	 */
	@Test
	public void testDeleteBeforeMerge()throws Exception{
		System.out.println("删除前："+writer.numDocs());  //9
		writer.deleteDocuments(new Term("id","1"));
		writer.commit();
		System.out.println("writer.maxDoc()："+writer.maxDoc()); //9
		System.out.println("writer.numDocs()："+writer.numDocs()); //6
		writer.close();
	}

	/**
	 * 测试删除 在合并后
	 */
	@Test
	public void testDeleteAfterMerge()throws Exception{
		System.out.println("删除前："+writer.numDocs());
		writer.deleteDocuments(new Term("id","1"));//3
		writer.forceMergeDeletes(); // 强制删除,这个不要顺便写
		writer.commit();
		System.out.println("writer.maxDoc()："+writer.maxDoc());//2
		System.out.println("writer.numDocs()："+writer.numDocs());//2
		writer.close();
	}

	/**第三节：修改文档
	 * 测试更新
	 * @throws Exception
	 */
	@Test
	public void testUpdate()throws Exception{
		Document doc=new Document();
		doc.add(new StringField("id", "1", Field.Store.YES));
		doc.add(new StringField("city","qingdao",Field.Store.YES));
		doc.add(new TextField("desc", "dsss is a city.", Field.Store.NO));
		writer.updateDocument(new Term("id","1"), doc);
		writer.close();
	}
}
