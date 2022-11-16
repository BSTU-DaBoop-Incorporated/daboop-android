import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../models/news.dart';
import 'news_tile.dart';

class NewsList extends StatelessWidget {
  final List<News> news;

  const NewsList({Key? key, required this.news}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: news.length,
      itemBuilder: (context, int index) {
        return NewsTile(news: news[index]);
      },
    );
  }
}
