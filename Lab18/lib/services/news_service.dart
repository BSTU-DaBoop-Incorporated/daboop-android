import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:flutter/foundation.dart';
import 'package:lab18/models/news.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

class NewsService  {
  final FirebaseFirestore _fireBase = FirebaseFirestore.instance;
  final String _collection = 'News';

  Future<News> addNews(String title, String text) async {
    final news = News.create(title, text);
    await _fireBase.collection(_collection).add(news.toNewJson
      ());

    return news;
  }


  Future<void> deleteNews(String uuid) async {
    await _fireBase.collection(_collection).doc(uuid).delete();
  }

  Future<List<News>> getNews() async {
    final snapshot = await _fireBase.collection(_collection).get();
    return snapshot.docs.map((doc) => News.fromJson(doc.data())).toList();
  }


  getNewsByKey(String uuid) async {
    final snapshot = await _fireBase.collection(_collection).doc(uuid).get();
    if (!snapshot.exists) {
      throw Exception('News not found');
    }
    return News.fromJson(snapshot.data()!);
  }

  // updateNews(id) async {
  //   await _fireBase.collection(_collection).doc(news.id).update(news.toNewJson());
  // }

  get snapshots {
    return _fireBase.collection(_collection).where("publishedAt", isGreaterThan:
            DateTime.now().subtract(const Duration(hours: 1))
             ).orderBy
      ("publishedAt")
        .snapshots();
  }

  NewsService();
  
}