package com.changwen.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.nio.file.Paths;

//文档域加权
public class IndexingTest2 {

	private String ids[]={"1","2","3","4"};
	private String authors[]={"Jack","Marry","John","Json"};
	private String positions[]={"accounting","technician","salesperson","boss"};
	private String titles[]={"Java is a good language.","Java is a cross platform language","Java powerful","You should learn java"};
	private String contents[]={
			"If possible, use the same JRE major version at both index and search time.",
			"When upgrading to a different JRE major version, consider re-indexing. ",
			"Different JRE major versions may implement different versions of Unicode,",
			"For example: with Java 1.4, `LetterTokenizer` will split around the character U+02C6,"
	};

	private Directory dir;

	/**
	 * 获取IndexWriter实例
	 */
	private IndexWriter getWriter()throws Exception{
		Analyzer analyzer=new StandardAnalyzer(); // 标准分词器
		IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		return new IndexWriter(dir, iwc);
	}

	/**
	 * 生成索引
	 */
	@Test
	public void index()throws Exception{
		dir=FSDirectory.open(Paths.get("D:\\lucene3"));
		IndexWriter writer=getWriter();
		for(int i=0;i<ids.length;i++){
			Document doc=new Document();
			doc.add(new StringField("id", ids[i], Field.Store.YES));
			doc.add(new StringField("author",authors[i],Field.Store.YES));
			doc.add(new StringField("position",positions[i],Field.Store.YES));
			// 加权操作
			TextField field=new TextField("title", titles[i], Field.Store.YES);
			if("boss".equals(positions[i])){
				field.setBoost(1.5f);
			}
			doc.add(field);
			doc.add(new TextField("content", contents[i], Field.Store.NO));
			writer.addDocument(doc); // 添加文档
		}
		writer.close();
	}

	/**
	 * 查询
	 */
	@Test
	public void search()throws Exception{
		dir=FSDirectory.open(Paths.get("D:\\lucene3"));
		IndexReader reader=DirectoryReader.open(dir);
		IndexSearcher is=new IndexSearcher(reader);
		String searchField="title";
		String q="java";
		Term t=new Term(searchField,q);
		Query query=new TermQuery(t);
		TopDocs hits=is.search(query, 10);
		System.out.println("匹配 '"+q+"'，总共查询到"+hits.totalHits+"个文档");
		for(ScoreDoc scoreDoc:hits.scoreDocs){
			Document doc=is.doc(scoreDoc.doc);
			System.out.println(doc.get("author"));
		}
		reader.close();
	}

}
