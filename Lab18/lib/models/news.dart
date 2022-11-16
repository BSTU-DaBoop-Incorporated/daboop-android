import 'package:cloud_firestore/cloud_firestore.dart';

class News {
  final String title;
  final String text;
  final Timestamp? publishedAt;

  News(this.title, this.text, this.publishedAt);
  News.create(this.title, this.text) : publishedAt = null;
  
  factory News.fromJson(Map<String, dynamic> json) => News(
    json['title'],
    json['text'],
    json['publishedAt'],
  );
  
  Map<String, dynamic> toJson() => {
    'title': title,
    'text': text,
    'publishedAt': publishedAt,
  };
  
  Map<String, dynamic> toNewJson() => {
      'title': title,
      'text': text,
      'publishedAt': FieldValue.serverTimestamp(),

  };
  
}