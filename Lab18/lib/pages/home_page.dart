import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:lab18/services/news_service.dart';
import 'package:lab18/widgets/news_list.dart';

import '../models/news.dart';
import 'add_news_page.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  NewsService newsService = NewsService();
  late List<News> newsList;

  void _addNews() async {
    await Navigator.push(
        context, MaterialPageRoute(builder: (context) => const AddNewsPage()));
  }

  @override
  // void initState() async {
  //   // TODO: implement initState
  //   super.initState();
  //   newsList = await newsService.getNews();
  //   newsService.snapshots.listen((event) {
  //     setState(() {
  //       newsList = event.docs.map((doc) => News.fromJson(doc.data())).toList();
  //     });
  //   });
  // }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
          child: StreamBuilder(
              stream: newsService.snapshots,
              builder: (context, AsyncSnapshot<QuerySnapshot<Map<String, 
                  dynamic>>> snapshot) {
                if (snapshot.hasData) {
                  var news = snapshot.data!.docs
                      .map((doc) => News.fromJson(doc.data()))
                      .toList();
                  return NewsList(news: news);
                } else {
                  return const CircularProgressIndicator();
                }
              })),
      // child: FutureBuilder(future: newsService.getNews(), builder: (context, snapshot) {
      //   if (snapshot.hasData) {
      //     return NewsList(news: snapshot.data!);
      //   }
      //   return const CircularProgressIndicator();
      // }),

      floatingActionButton: FloatingActionButton(
        onPressed: _addNews,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
